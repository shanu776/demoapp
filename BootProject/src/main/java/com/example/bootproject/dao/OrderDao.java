package com.example.bootproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bootproject.model.OrderItems;

public interface OrderDao extends CrudRepository<OrderItems, Integer>{

}
