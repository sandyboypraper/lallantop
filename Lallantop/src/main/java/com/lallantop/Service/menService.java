package com.lallantop.Service;

import java.util.List;
import java.util.Optional;

import com.lallantop.domain.men;

public interface menService {

	public List<men> findAlllist();
	
	public men findOne(Long id);
	
	public  List<men> findByBaapCat(String BaapCat);
	
}
