package roz.power.market.dto.peopleDTO;

import lombok.Data;

@Data
public class UserDTOForUpdateLoginAndPassword {
	
	private String email;
	private String enteredLogin;
	private String newPassword;
	private String enteredPassword;
	
}
