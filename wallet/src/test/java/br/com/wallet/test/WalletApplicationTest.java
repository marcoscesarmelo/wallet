package br.com.wallet.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Base64;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.wallet.WalletApplication;

@SpringBootTest(classes = WalletApplication.class)
public class WalletApplicationTest extends AbstractTestNGSpringContextTests {

	private long currentTime = System.currentTimeMillis();
	private final String username = "user-" + currentTime;
	private final String password = "pwd-" + currentTime;
	private final String basicToken = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	String body = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\" }";
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void registrationTest() throws Exception {		
		MvcResult result = mockMvc.perform(post("/register")
				.content(body).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}

	@Test
	public void listTimeline() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/operation/timeline/1")
				.header(HttpHeaders.AUTHORIZATION, basicToken)
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void paymentAccountNotFound() throws Exception {
		MvcResult result = mockMvc
				.perform(put("/payment/1")
				.header(HttpHeaders.AUTHORIZATION, basicToken)
				.header("amount", 100)
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}	

}
