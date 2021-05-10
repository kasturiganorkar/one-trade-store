package com.onetrade.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onetrade.store.entity.TradeBean;
import com.onetrade.store.service.TradeService;

@RestController
@RequestMapping(path = "/trade")
public class TradeController {
	
	@Autowired
	private TradeService service;
	
	@PostMapping(path = "/add")
	public void addTrade(@RequestBody TradeBean trade) throws Exception {
		service.add(trade);
	}
	
	@GetMapping(path = "/getAll")
	public List<TradeBean> getAllTrades() {
		return service.getAllTrades();
	}

}
