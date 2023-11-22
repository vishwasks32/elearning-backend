package com.cloudthat.elearningbackend.service;

import java.util.List;


import com.cloudthat.elearningbackend.model.TechnologyModel;


public interface TechnologyService {

	TechnologyModel addtechnology(TechnologyModel technologyModel);

	TechnologyModel getTechnology(Long techId);

	List<TechnologyModel> getAllTechnologies();

	void deleteTechnology(Long techId);

}
