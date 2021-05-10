package com.onetrade.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onetrade.store.entity.TradeBean;

@Repository
public interface TradeRepository extends JpaRepository<TradeBean, String> {

}
