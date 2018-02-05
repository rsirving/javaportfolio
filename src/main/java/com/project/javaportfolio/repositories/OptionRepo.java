package com.project.javaportfolio.repositories;
import com.project.javaportfolio.models.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository 												
public interface OptionRepo extends CrudRepository<Option,Long>{
	List<Option> findAll();

	public default void saveOption(Option option){
		option.setActive(true);
		save(option);
	}
}
