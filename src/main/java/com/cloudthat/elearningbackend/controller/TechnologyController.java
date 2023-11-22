package com.cloudthat.elearningbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudthat.elearningbackend.model.ApiResponse;
import com.cloudthat.elearningbackend.model.TechnologyModel;
import com.cloudthat.elearningbackend.service.TechnologyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class TechnologyController {
	
	@Autowired
	TechnologyService technologyService;
	
	@PostMapping("technologies")
	public ResponseEntity<ApiResponse> addTechnology(@RequestBody TechnologyModel technologyModel){
		log.info("Adding Technology");
		TechnologyModel techModel = technologyService.addtechnology(technologyModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Technology added Successfully", techModel), HttpStatus.OK);
	}
	
	@GetMapping("/technologies/{techId}")
	public ResponseEntity<ApiResponse> getTechnology(@PathVariable("techId") Long techId){
		log.info("Retriving Technology");
		TechnologyModel techModel = technologyService.getTechnology(techId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Technology fetched Successfully", techModel), HttpStatus.OK);
	}
	
	@GetMapping("/technologies")
	public ResponseEntity<ApiResponse> getAllTechnologies(){
		log.info("Retriving Technology List");
		List<TechnologyModel> technologies = technologyService.getAllTechnologies();
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Technologies List fetched Successfully", technologies), HttpStatus.OK);
	}
	
	@DeleteMapping("/technologies/{techId}")
	public ResponseEntity<ApiResponse> deleteTechnology(@PathVariable("techId") Long techId){
		log.info("Deleting Technology");
		technologyService.deleteTechnology(techId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Technology deleted Successfully", true), HttpStatus.OK);
	}
	
	
	
}
