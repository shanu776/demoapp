package com.example.bootproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bootproject.model.OrderHistory;

public interface OrderHistoryDao extends CrudRepository<OrderHistory, Integer>{

}
