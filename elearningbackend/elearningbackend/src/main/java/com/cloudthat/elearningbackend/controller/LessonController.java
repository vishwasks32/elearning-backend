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

import com.cloudthat.elearningbackend.model.LessonModel;
import com.cloudthat.elearningbackend.service.LessonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class LessonController {
	
	@Autowired
	LessonService lessonService;
	
	// We first create the lesson and then add the content
	@PostMapping("/courses/{courseId}/lessons")
	public ResponseEntity<ApiResponse> addLesson(@PathVariable("courseId") Long courseId, @RequestBody LessonModel lessonModel){
		log.info("Add a lesson");
		LessonModel lesson = lessonService.addLesson(courseId, lessonModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Lesson added Successfully", lesson), HttpStatus.OK);
	}
	
	@GetMapping("/courses/{courseId}/lessons")
	public ResponseEntity<ApiResponse> getLessonsForCourse(@PathVariable("courseId") Long courseId){
		log.info("Retriving List of Lessons for a course");
		List<LessonModel> lessons = lessonService.getLessonsForCourse(courseId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Lesson List fetched Successfully", lessons), HttpStatus.OK);
	}
	
	@GetMapping("/lessons/{lessonId}")
	public ResponseEntity<ApiResponse> getLesson(@PathVariable("lessonId") Long lessonId){
		log.info("Getting a lesson");
		LessonModel lesson = lessonService.getLesson(lessonId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Lesson details fetched Successfully", lesson), HttpStatus.OK);
	}
	
	@DeleteMapping("/lessons/{lessonId}")
	public ResponseEntity<ApiResponse> deleteLesson(@PathVariable("lessonId") Long lessonId){
		log.info("deleting a lesson");
		lessonService.deleteLesson(lessonId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Lesson deleted Successfully", true), HttpStatus.OK);
	}
		
}
