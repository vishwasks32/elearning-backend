package com.cloudthat.elearningbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cloudthat.elearningbackend.entity.Technology;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.TechnologyModel;
import com.cloudthat.elearningbackend.repository.TechnologyRepository;

@Service
public class TechnologyServiceImpl implements TechnologyService{
	
	@Autowired
	TechnologyRepository technologyRepository;

	@Override
	public TechnologyModel addtechnology(TechnologyModel technologyModel){
		// TODO Auto-generated method stub
		
		Technology newtech = new Technology();
		newtech.setTitle(technologyModel.getTechnologyTitle());
		Technology technology;
		try {
			technology = technologyRepository.save(newtech);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException("Cannot create new technology");
		}	
		
		return technologyToTechnologyModel(technology);
	}
	
	private TechnologyModel technologyToTechnologyModel(Technology technology) {
		TechnologyModel techModel = new TechnologyModel();
		techModel.setTechnologyId(technology.getId());
		techModel.setTechnologyTitle(technology.getTitle());
		
		return techModel;
	}

	@Override
	public TechnologyModel getTechnology(Long techId) {
		Technology tech;
		try {
			// TODO Auto-generated method stub
			tech = technologyRepository.findById(techId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Technology", "Id", techId);
		}
		return technologyToTechnologyModel(tech);
	}

	@Override
	public List<TechnologyModel> getAllTechnologies() {
		// TODO Auto-generated method stub
		List<Technology> techs = technologyRepository.findAll();
		List<TechnologyModel> technologies = new ArrayList<TechnologyModel>();
		for(Technology t: techs) {
			technologies.add(technologyToTechnologyModel(t));
		}
		return technologies;
	}

	@Override
	public void deleteTechnology(Long techId) {
		// TODO Auto-generated method stub
		Technology tech;
		try {
			// TODO Auto-generated method stub
			tech = technologyRepository.findById(techId).get();
			technologyRepository.delete(tech);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Technology", "Id", techId);
		}
	}
	
	
	
	

}
