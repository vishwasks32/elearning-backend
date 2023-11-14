package com.cloudthat.elearningbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudthat.elearningbackend.model.ApiResponse;
import com.cloudthat.elearningbackend.model.ContentModel;
import com.cloudthat.elearningbackend.service.ContentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	@PostMapping("/lessons/{lessonId}/content")
	public ResponseEntity<ApiResponse> addContent(@PathVariable("lessonId") Long lessonId, @RequestBody ContentModel contentModel){
		log.info("Add a Content");
		ContentModel courseContent = contentService.addContent(lessonId, contentModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Course Content added Successfully", courseContent), HttpStatus.OK);
	}
	
	@GetMapping("/content/{contentId}")
	public ResponseEntity<ApiResponse> getContentDetails(@PathVariable("contentId") Long contentId){
		log.info("Retrieve a Content");
		ContentModel contentDetails = contentService.getContentDetails(contentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Course Content fetched Successfully", contentDetails), HttpStatus.OK);
	}
	
	@PutMapping("/content/{contentId}")
	public ResponseEntity<ApiResponse> updateContent(@PathVariable("contentId") Long contentId, @RequestBody ContentModel contentModel){
		log.info("Update a Content");
		ContentModel contentDetails = contentService.updateContent(contentId, contentModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Course Content updated Successfully", contentDetails), HttpStatus.OK);
	}
	
	@DeleteMapping("/content/{contentId}")
	public ResponseEntity<ApiResponse> deleteContent(@PathVariable("contentId") Long contentId){
		log.info("deleting a lesson");
		contentService.deleteContent(contentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Content deleted Successfully", true), HttpStatus.OK);
	}
}
