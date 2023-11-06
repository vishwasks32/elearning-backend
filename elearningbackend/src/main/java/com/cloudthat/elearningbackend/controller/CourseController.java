package com.cloudthat.elearningbackend.controller;

import java.util.List;

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
import com.cloudthat.elearningbackend.model.CourseDetails;
import com.cloudthat.elearningbackend.model.CourseModel;

import com.cloudthat.elearningbackend.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@PostMapping("addCourse")
	public ResponseEntity<ApiResponse> addCourse(@RequestBody CourseModel courseModel){
		CourseDetails courseDetails = courseService.registerCourse(courseModel);
		log.info("New course Created");
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Course created Successfully", courseDetails), HttpStatus.OK);
	}
	
	@GetMapping("courses")
	public ResponseEntity<ApiResponse> getAllCourses(){
		List<CourseDetails> courses = courseService.getAllCourses();
		log.info("Fetched All Courses");
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Courses fetched Successfully", courses), HttpStatus.OK);
	}
	
	@GetMapping("/courses/{courseId}")
	public ResponseEntity<ApiResponse> getCourseDetails(@PathVariable("courseId") Long courseId){
		CourseDetails course = courseService.getCourseDetails(courseId);
		log.info("Fetched Course Details");
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Courses fetched Successfully", course), HttpStatus.OK);
	}
	
	@PutMapping("/courses/{courseId}")
	public ResponseEntity<ApiResponse> updateCourseDetails(@PathVariable("courseId") Long courseId, @RequestBody CourseModel courseModel){
		CourseDetails course = courseService.updateCourseDetails(courseId, courseModel);
		log.info("Fetched Course Details");
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Courses updated Successfully", course), HttpStatus.OK);
	}
	
	@DeleteMapping("/courses/{courseId}")
	public ResponseEntity<ApiResponse> disableCourse(@PathVariable("courseId") Long courseId){
		courseService.disableCourse(courseId);
		log.info("Deleted Course");
		return new ResponseEntity<ApiResponse>(new ApiResponse("Course Deleted Successfully", true), HttpStatus.OK);
	}
}
