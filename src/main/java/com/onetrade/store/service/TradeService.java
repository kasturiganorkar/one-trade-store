package com.onetrade.store.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onetrade.store.entity.TradeBean;
import com.onetrade.store.repository.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	private TradeRepository repository;
	
	public void add(TradeBean trade) throws Exception {
		Optional<TradeBean> existingTrade = repository.findById(trade.getTradeId());
		if(existingTrade.isPresent()) {
			TradeBean existingTradeData = existingTrade.get();
			if(!isValidVersion(trade.getVersion(), existingTradeData.getVersion())) {
				throw new Exception("Invalid trade details for id: " + trade.getTradeId());
			}
		}
		if(!isValidMatuarityDate(trade.getMatuarityDate())) {
			throw new Exception("Invalid matuarity date for id: " + trade.getTradeId());
		}
		repository.save(trade);
		
	}
	
	private boolean isValidVersion(int requestVersion,int existingVersion) {
		return (requestVersion >= existingVersion ? true : false);
	}
	
	private boolean isValidMatuarityDate(LocalDate date) {
		return (date.compareTo(LocalDate.now()) < 0 ? false : true);
		
	}
	
	public List<TradeBean> getAllTrades(){
		return repository.findAll();
	}

}
