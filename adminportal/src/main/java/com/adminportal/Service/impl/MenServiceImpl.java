package com.adminportal.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.Service.MenService;
import com.adminportal.domain.men;
import com.adminportal.repository.MenRepository;

@Service
public class MenServiceImpl implements MenService{

	@Autowired
	private MenRepository menrepo;
	
	@Override
	public men save(men men1) {
		return menrepo.save(men1); 
	}
	
	@Override
	public List<men> findAll() {
		return (List<men>) menrepo.findAll();
	}
		
}
