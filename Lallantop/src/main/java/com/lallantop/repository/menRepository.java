package com.lallantop.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lallantop.domain.men;


public interface menRepository extends CrudRepository<men,Long>{
	List<men> findAllByBaapCat(String baapCat);
}
