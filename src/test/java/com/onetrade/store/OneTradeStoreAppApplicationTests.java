package com.onetrade.store;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetrade.store.entity.TradeBean;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OneTradeStoreAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OneTradeStoreAppApplicationTests {
	
	private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyy");
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();


	@Test
	public void testAddTradeAndUpdate() throws Exception{
		
		String URL_PREFIX = "http://localhost:" + port;
		ResponseEntity<String> response = restTemplate.postForEntity(URL_PREFIX + "/onetradestore/trade/add", getBean(), String.class, new HashMap<>());
		HttpStatus status = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, status);
		
		ResponseEntity<String> getResponse = restTemplate.getForEntity(URL_PREFIX + "/onetradestore/trade/getAll", String.class);
		HttpStatus getStatus = getResponse.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, getStatus);
		
		ObjectMapper mapper = new ObjectMapper();
		List<TradeBean> list = mapper.readValue(getResponse.getBody(), new TypeReference<List<TradeBean>>(){});
		
		Assert.assertEquals(1, list.size());
		TradeBean bean = list.get(0);
		Assert.assertEquals("T1", bean.getTradeId());
		Assert.assertEquals(1, bean.getVersion());
		Assert.assertEquals("B1", bean.getBookingId());
		Assert.assertEquals("CP-1", bean.getCounterPartyId());
		Assert.assertTrue(bean.getCreatedDate().equals(LocalDate.parse("10-05-2021",FORMATTER)));
		Assert.assertTrue(bean.getMatuarityDate().equals(LocalDate.parse("10-05-2023",FORMATTER)));
		
		response = restTemplate.postForEntity(URL_PREFIX + "/onetradestore/trade/add", getBean3(), String.class, new HashMap<>());
		status = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, status);
		
		getResponse = restTemplate.getForEntity(URL_PREFIX + "/onetradestore/trade/getAll", String.class);
		getStatus = getResponse.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, getStatus);
		
		List<TradeBean> updatedlist = mapper.readValue(getResponse.getBody(), new TypeReference<List<TradeBean>>(){});
		
		Assert.assertEquals(1, updatedlist.size());
		TradeBean updatedBean = updatedlist.get(0);
		Assert.assertEquals("T1", updatedBean.getTradeId());
		Assert.assertEquals(3, updatedBean.getVersion());
		Assert.assertEquals("B11", updatedBean.getBookingId());
		Assert.assertEquals("CP-11", updatedBean.getCounterPartyId());
		Assert.assertTrue(updatedBean.getCreatedDate().equals(LocalDate.parse("10-05-2021",FORMATTER)));
		Assert.assertTrue(updatedBean.getMatuarityDate().equals(LocalDate.parse("10-05-2024",FORMATTER)));
		
	}
	
	
	private TradeBean getBean() {
		TradeBean bean = new TradeBean();
		bean.setTradeId("T1");
		bean.setBookingId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setCreatedDate(LocalDate.parse("10-05-2021",FORMATTER));
		bean.setMatuarityDate(LocalDate.parse("10-05-2023",FORMATTER));
		bean.setVersion(1);
		
		return bean;
	}
	
	private TradeBean getBean3() {
		TradeBean bean = new TradeBean();
		bean.setTradeId("T1");
		bean.setBookingId("B11");
		bean.setCounterPartyId("CP-11");
		bean.setCreatedDate(LocalDate.parse("10-05-2021",FORMATTER));
		bean.setMatuarityDate(LocalDate.parse("10-05-2024",FORMATTER));
		bean.setVersion(3);
		
		return bean;
	}


}
