package com.example.bootproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bootproject.model.Items;

public interface ItemsDao extends CrudRepository<Items, Integer>{

}
