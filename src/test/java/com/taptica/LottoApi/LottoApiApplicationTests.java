package com.taptica.LottoApi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LottoApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void delayBeforeTest() throws Exception {
		TimeUnit.SECONDS.sleep(2);
	}

	@Before
	public void delayAfterTest() throws Exception {
		TimeUnit.SECONDS.sleep(2);
	}

	@Test
	public void test_single_valid_request() throws Exception {
		mockMvc.perform(post("/111/?guessedNum=11")).andExpect(status().isOk());
	}

	@Test
	public void test_too_many_guesses() throws Exception {
		for (int i = 0; i < 2; i++) {
			mockMvc.perform(post("/222/?guessedNum=" + (20 + i))).andExpect(status().isOk());
		}
		mockMvc.perform(post("/222/?guessedNum=25")).andExpect(status().isTooManyRequests());
	}

	
	@Test
	public void test_out_of_range_guesses() throws Exception {
		mockMvc.perform(post("/333/?guessedNum=333")).andExpect(status().isBadRequest());
	}

	
}
