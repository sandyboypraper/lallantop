package com.lallantop.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.menService;
import com.lallantop.domain.men;
import com.lallantop.repository.menRepository;

@Service
public class menServiceImpl implements menService {

	@Autowired
	private menRepository menrepo;
	
	@Override
	public List<men> findAlllist()
	{
		return (List<men>) menrepo.findAll();
	}
	
	@Override
	public men findOne(Long id)
	{
		Optional<men> m =  menrepo.findById(id);
		return m.get();
	}
	
	@Override
	public List<men> findByBaapCat(String baapCat)
	{
		return menrepo.findAllByBaapCat(baapCat);
	}
}
