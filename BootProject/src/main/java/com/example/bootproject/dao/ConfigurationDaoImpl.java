package com.example.bootproject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.bootproject.model.Configuration;

public class ConfigurationDaoImpl {

	@Autowired
	ConfigurationDao configurationDao;
	
	public void save(Configuration configuration) {
		configurationDao.save(configuration);
	}
	
	public List<Configuration> findall() {
		return (List<Configuration>) configurationDao.findAll();
	}
	
	public Configuration getOne(Integer id) {
		return configurationDao.findOne(id);
	}
	
	public void  delete(Integer id) {
		configurationDao.delete(id);
	}
}
