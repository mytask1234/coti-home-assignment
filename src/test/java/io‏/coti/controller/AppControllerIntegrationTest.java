package io‏.coti.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io‏.coti.CotiApplication;
import io‏.coti.dto.NumberDto;
import io‏.coti.repository.ArrayElementRepository;

@SpringBootTest(classes=CotiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AppControllerIntegrationTest {

	private static final String INSERT_ENDPOINT = "/coti/api/v1/insert";
	private static final String DELETE_ENDPOINT = "/coti/api/v1/delete/";
	private static final String RETURN_ENDPOINT = "/coti/api/v1/return?";

	@Autowired
    private ArrayElementRepository arrayElementRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/****************************** test insert **********************************************************/

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	public void testInsertNumbers() throws Exception {
		
		int n1 = 1;
		int n2 = 2;
		int n3 = 3;
		int n5 = 5;
		
		//--------------------------------------------------------------
		
		insertNumber(n1, n1, n1);
		
		testNumberValuesInDb(n1);
		
		//--------------------------------------------------------------
		
		insertNumber(n2, n1+n2, (n1+n2) / n2);
		
		testNumberValuesInDb(n1, n2);
		
		//--------------------------------------------------------------
		
		insertNumber(n3, n1+n2+n3, (n1+n2+n3) / n3);
		
		testNumberValuesInDb(n1, n2, n3);
		
		//--------------------------------------------------------------
		
		insertNumber(n5, n1+n2+n3+n5, (n1+n2+n3+n5) / n5);
		
		testNumberValuesInDb(n1, n2, n3, n5);
	}
	
	/****************************** test flow **********************************************************/
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	public void testFlow1InsertReturnDeleteReturn() throws Exception {
		
		int n1 = 4;
		int n2 = 5;
		int n3 = 123;
		
		//----------------- insert ---------------------------------------------
		
		insertNumber(n1, n1, 1);
		insertNumber(n2, n1+n2, 1);
		insertNumber(n3, n1+n2+n3, 1);
		
		testNumberValuesInDb(n1, n2, n3);
		
		//----------------- return ---------------------------------------------
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=0,1,2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]").value(4))
				.andExpect(jsonPath("$[1]").value(5))
				.andExpect(jsonPath("$[2]").value(123))
				.andDo(print())
				.andReturn();
		
		//----------------- delete ---------------------------------------------
		
		mockMvc.perform(delete(DELETE_ENDPOINT+"1")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value("1"))
				.andDo(print());
		
		testNumberValuesInDb(n1, n3, n2);
		
		//----------------- return ---------------------------------------------
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=0,1,2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]").value(4))
				.andExpect(jsonPath("$[1]").value(-1))
				.andExpect(jsonPath("$[2]").value(123))
				.andDo(print())
				.andReturn();
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	public void testFlow2InsertReturnDeleteReturn_deleteLastIndex() throws Exception {
		
		int n1 = 4;
		int n2 = 5;
		int n3 = 123;
		
		//----------------- insert ---------------------------------------------
		
		insertNumber(n1, n1, 1);
		insertNumber(n2, n1+n2, 1);
		insertNumber(n3, n1+n2+n3, 1);
		
		testNumberValuesInDb(n1, n2, n3);
		
		//----------------- return ---------------------------------------------
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=0,2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0]").value(4))
				.andExpect(jsonPath("$[1]").value(123))
				.andDo(print())
				.andReturn();
		
		//----------------- delete ---------------------------------------------
		
		mockMvc.perform(delete(DELETE_ENDPOINT+"2")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value("2"))
				.andDo(print());
		
		testNumberValuesInDb(n1, n2, n3);
		
		//----------------- return ---------------------------------------------
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=0,1,2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]").value(4))
				.andExpect(jsonPath("$[1]").value(5))
				.andExpect(jsonPath("$[2]").value(-1))
				.andDo(print())
				.andReturn();
		
		//----------------- return ---------------------------------------------
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]").value(-1))
				.andDo(print())
				.andReturn();
	}
	
	/****************************** test delete **********************************************************/
	
	@Test
	public void testDeleteNumberByIndexThatNotExist_thenResponseStatusNotFound() throws Exception {
		
		mockMvc.perform(delete(DELETE_ENDPOINT+"20")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
		
		mockMvc.perform(delete(DELETE_ENDPOINT+"0")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
		
		mockMvc.perform(delete(DELETE_ENDPOINT)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
	}
	
	@Test
	public void testDeleteNumberByIndexThatNotValid_thenResponseStatusBadRequest() throws Exception {
		
		mockMvc.perform(delete(DELETE_ENDPOINT+"-9")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
		
		mockMvc.perform(delete(DELETE_ENDPOINT+"abc")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	/****************************** test return **********************************************************/
	
	@Test
	public void testReturnWhenIndexNotFound_thenResponseStatusNotFound() throws Exception {
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=0,1,2")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
	}
	
	@Test
	public void testReturnWhenIndexNotValid_thenResponseStatusBadRequest() throws Exception {
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
		
		mockMvc.perform(get(RETURN_ENDPOINT)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
		
		mockMvc.perform(get(RETURN_ENDPOINT+"indexes=zxzx,1")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	/****************************** private methods **********************************************************/
	
	private void insertNumber(int number, int expectedOutput1, int expectedOutput2) throws Exception {
		
		NumberDto numberDto = new NumberDto();
		
		numberDto.setNumber(number);
		
		mockMvc.perform(post(INSERT_ENDPOINT)
				.content(objectMapper.writeValueAsString(numberDto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.output1").value(expectedOutput1))
				.andExpect(jsonPath("$.output2").value(expectedOutput2))
				.andDo(print());
	}
	
	private void testNumberValuesInDb(Integer... numbers) {
		
		List<Integer> expectedNumberValuesInDb = Arrays.asList(numbers);
		
		List<Integer> allNumbers = arrayElementRepository.findAll().stream().map(e -> e.getNumberValue()).collect(Collectors.toList());
				
		assertEquals(expectedNumberValuesInDb, allNumbers);
	}
}
