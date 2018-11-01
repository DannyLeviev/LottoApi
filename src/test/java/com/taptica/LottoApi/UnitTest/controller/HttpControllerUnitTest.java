package com.taptica.LottoApi.UnitTest.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.taptica.LottoApi.controller.HttpController;
import com.taptica.LottoApi.exceptions.InvalidLotterryNumberProvidedException;
import com.taptica.LottoApi.exceptions.TooManyGuessingTriesException;
import com.taptica.LottoApi.service.LottoServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(HttpController.class)
public class HttpControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LottoServiceImpl lottoServ;

	@Test
	public void testValidGuess() throws Exception {
		when(lottoServ.serve("111", "11")).thenReturn(true);
		this.mockMvc.perform(post("/111/?guessedNum=11")).andExpect(status().isOk());
		verify(lottoServ, times(1)).serve("111", "11");
	}

	@Test
	public void testTooManyGuesses() throws Exception {
		when(lottoServ.serve("111", "11")).thenThrow(TooManyGuessingTriesException.class);
		this.mockMvc.perform(post("/111/?guessedNum=11")).andExpect(status().isTooManyRequests());
	}
	
	@Test
	public void testOutOfRangeGuesses() throws Exception {
		when(lottoServ.serve("111", "111")).thenThrow(InvalidLotterryNumberProvidedException.class);
		this.mockMvc.perform(post("/111/?guessedNum=111")).andExpect(status().isBadRequest());
	}
	
}
