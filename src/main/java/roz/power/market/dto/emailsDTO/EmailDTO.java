package roz.power.market.dto.emailsDTO;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class EmailDTO {

	private String email;
	private String message;
	private String code;
	
}
