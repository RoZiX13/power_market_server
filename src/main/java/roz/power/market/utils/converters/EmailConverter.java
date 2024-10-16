package roz.power.market.utils.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roz.power.market.dto.emailsDTO.EmailDTO;
import roz.power.market.models.mongo.Email;

@Component
public class EmailConverter {

	@Autowired
	private final ModelMapper modelMapper;

	public EmailConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Email convertToEmailFromEmailDTO(EmailDTO emailDTO) {
		return modelMapper.map(emailDTO, Email.class);
	}
	
	public EmailDTO convertToEmailDTO(Email email) {
		return modelMapper.map(email, EmailDTO.class);
	}
	
	public List<EmailDTO> convertToListOfEmailsDTO(List<Email> emails){
		List<EmailDTO> emailsDTO = new ArrayList<>();
		
		for(Email email:
				emails) {
			emailsDTO.add(convertToEmailDTO(email));
		}
			
		return emailsDTO;
	}
	
}
