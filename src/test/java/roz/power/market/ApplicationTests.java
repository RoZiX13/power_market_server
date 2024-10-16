package roz.power.market;

import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roz.power.market.crud.repository.services.postgres.UserService;

import java.util.Map;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void shouldAuth() {
		Map<String, String> map = userService.authorization("email", "email");
		Assert.notNull(map.get("token"));
	}

}
