package com.adminportal.Service;

import java.util.List;

import com.adminportal.domain.men;

public interface MenService {
	men save(men men1);
	
	List<men> findAll();
}
