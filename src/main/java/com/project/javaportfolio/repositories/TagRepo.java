package com.project.javaportfolio.repositories;
import com.project.javaportfolio.models.*;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository 												
public interface TagRepo extends CrudRepository<Tag,Long>{
	// Query methods go here.
	
	// Example:
	// public YourModelHere findByName(String name);
	List<Tag> findByNameContaining(String search);
	Tag findById(Long id);
}
