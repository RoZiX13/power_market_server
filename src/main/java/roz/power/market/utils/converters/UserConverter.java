package roz.power.market.utils.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roz.power.market.dto.emailsDTO.EmailDTO;
import roz.power.market.dto.peopleDTO.UserDTOForRegistration;
import roz.power.market.dto.peopleDTO.UserDTOForUpdateLoginAndPassword;
import roz.power.market.models.mongo.Email;
import roz.power.market.models.postgres.User;

@Component
public class UserConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public User convertToPersonFromPersonDTOForRegistration(UserDTOForRegistration personDTOForRegistration) {
		return modelMapper.map(personDTOForRegistration, User.class);
	}
	
	public List<User> convertToListOfPeopleFormListOfPeopleDTOForRegistration(List<UserDTOForRegistration> listOfPeopleDTORegistraion) {
		List<User> listOfPerson = new ArrayList<>();
		listOfPeopleDTORegistraion.forEach(personDTOForRegistration ->
		    listOfPerson.add(convertToPersonFromPersonDTOForRegistration(personDTOForRegistration))
		);
		return listOfPerson;
	}
	
	public Email convertToEmailFromPersonDTO(UserDTOForUpdateLoginAndPassword personDTOForUpdateLoginAndPassword) {
		Email email = new Email();
		email.setMessage(personDTOForUpdateLoginAndPassword.getEnteredPassword());
		return email;
	}
	
	public User convertToPersonForRegistrationFromEmailDTO(EmailDTO emailDTO) {
		User user = modelMapper.map(emailDTO, User.class);
		user.setEmail(emailDTO.getEmail());
		return user;
	}

}
