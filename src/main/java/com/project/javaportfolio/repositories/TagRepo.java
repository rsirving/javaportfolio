package com.project.javaportfolio.repositories;
import com.project.javaportfolio.models.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository 												
public interface TagRepo extends CrudRepository<Tag,Long>{
	List<Tag> findByNameContaining(String search);
	Tag findById(Long id);
}
