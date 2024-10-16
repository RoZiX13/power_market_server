package roz.power.market.crud.repository.services.mongo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import roz.power.market.crud.repository.repositories.mongo.EmailRepository;
import roz.power.market.models.mongo.Email;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class EmailService{

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public List<Email> getEmails(){
		return emailRepository.findAll();
	}

	@Transactional(readOnly = false)
	public Email findEmailByEmailAndCode(String email, String code){
		return emailRepository.findByEmailAndCode(email, code).stream()
				.findAny().orElseThrow(IllegalArgumentException::new);
	}

	@Transactional(readOnly = false)
	public void delete(Email email){
		emailRepository.deleteAllByEmail(email.getEmail());
	}

	@Transactional(readOnly = false)
	public void verification(Email email) {
		String code = getRandomString();
		Map<String, String> param = new HashMap<>();
		param.put("code", code);
		param.put("email", email.getEmail());

		email.setCode(code);
		email.setMessage(email.getMessage());

		sendMessage(email.getEmail(),
				generateEmailContent(
						"classpath:templates/modelMessageForEmail/modelMessageForUrlForGet.html",
						param
				)
		);
	}

	public void sendMessage(String toEmail, String message){
		MimeMessage mimeMessage =  emailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(toEmail);
			helper.setFrom(sender);
			helper.setText(message, true);
			emailSender.send(helper.getMimeMessage());
		}catch(MessagingException ex) {
			throw new IllegalArgumentException();
		}
	}

	public String generateEmailContent(String path,
									   Map<String, String> param) {
		String emailTemplate = getEmailTemplate(path);
		Context context = new Context();
		for (Map.Entry<String, String> entry:
				param.entrySet()){
			context.setVariable(entry.getKey(), entry.getValue());
			context.setVariable(entry.getKey(), entry.getValue());
		}
		return templateEngine.process(emailTemplate, context);
	}

	public String getEmailTemplate(String path) {
		Resource resource = resourceLoader.getResource(path);
		StringBuilder htmlTemplate = new StringBuilder();
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))){
			htmlTemplate.append(bufferedReader.readLine());
		}catch (Exception ex){
			ex.fillInStackTrace();
		}
		return htmlTemplate.toString();
	}

	String getRandomString() {
		long random = (long)(Math.random() * Math.pow(10, 16));
		return Long.toHexString(random);
	}

}
