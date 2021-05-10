package com.onetrade.store.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.onetrade.store.entity.TradeBean;
import com.onetrade.store.repository.TradeRepository;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {
	
	@InjectMocks
	private TradeService service;
	
	private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyy");
	
	@Mock
	private TradeRepository mockRepo;
	
	@Test
	public void testInvalidVersion() throws Exception {
		
		Optional<TradeBean> existingTrade = Optional.of(getBean1());
		Mockito.when(mockRepo.findById(Mockito.anyString())).thenReturn(existingTrade);
		
		try {
			service.add(getBean());
		}catch(Exception e) {
			Assert.assertEquals("Invalid trade details for id: T1", e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testInvalidMatuarityDate() throws Exception {
		
		try {
			service.add(getBean2());
		}catch(Exception e) {
			Assert.assertEquals("Invalid matuarity date for id: T2", e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testOverrideTrade() throws Exception {
		
		Optional<TradeBean> existingTrade = Optional.of(getBean());
		Mockito.when(mockRepo.findById(Mockito.anyString())).thenReturn(existingTrade);
		
		try {
			service.add(getBean());
		}catch(Exception e) {
			Assert.assertEquals("Invalid trade details for id: T1", e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testAddTradeSuccess() throws Exception {
		service.add(getBean());
		Mockito.verify(mockRepo,Mockito.times(1)).save(Mockito.any(TradeBean.class));
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
	
	//invalid version
	private TradeBean getBean1() {
		TradeBean bean = new TradeBean();
		bean.setTradeId("T1");
		bean.setBookingId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setCreatedDate(LocalDate.parse("10-05-2021",FORMATTER));
		bean.setMatuarityDate(LocalDate.parse("10-05-2023",FORMATTER));
		bean.setVersion(2);
		
		return bean;
	}
	
	// invalid matuarity date
	private TradeBean getBean2() {
		TradeBean bean = new TradeBean();
		bean.setTradeId("T2");
		bean.setBookingId("B2");
		bean.setCounterPartyId("CP-2");
		bean.setCreatedDate(LocalDate.parse("10-05-2021", FORMATTER));
		bean.setMatuarityDate(LocalDate.parse("10-05-2020", FORMATTER));
		bean.setVersion(2);

		return bean;
	}
	

}
