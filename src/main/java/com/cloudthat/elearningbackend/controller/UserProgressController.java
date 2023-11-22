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
import com.cloudthat.elearningbackend.model.UserProgressModel;
import com.cloudthat.elearningbackend.service.UserProgressService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class UserProgressController {
	
	@Autowired
	UserProgressService userProgressService;
	
	@PostMapping("progress")
	public ResponseEntity<ApiResponse> addProgress(@RequestBody UserProgressModel userProgressModel){
		log.info("Add a user progress");
		UserProgressModel userProgress = userProgressService.addProgress(userProgressModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added user progress successfully", userProgress), HttpStatus.OK);
	}
	
	@GetMapping("/course/{courseId}/user/{userId}/progress")
	public ResponseEntity<ApiResponse> getUserCourseProgress(@PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId){
		log.info("Retrieve a user progress");
		UserProgressModel userProgress = userProgressService.getUserCourseProgress(courseId,userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Retrieved user progress successfully", userProgress), HttpStatus.OK);
	}
	
	@PutMapping("/course/{courseId}/user/{userId}/progress")
	public ResponseEntity<ApiResponse> updateUserCourseProgress(@PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId, @RequestBody UserProgressModel userProgressModel){
		log.info("Update a user progress");
		UserProgressModel userProgress = userProgressService.updateUserCourseProgress(courseId,userId, userProgressModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Updated  a user progress successfully", userProgress), HttpStatus.OK);
	}
	
	@DeleteMapping("/course/{courseId}/user/{userId}/progress")
	public ResponseEntity<ApiResponse> deleteUserCourseProgress(@PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId){
		log.info("Delete a user progress");
		userProgressService.deleteUserCourseProgress(courseId,userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted  a user progress successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/progress")
	public ResponseEntity<ApiResponse> getAllPogressForUser(@PathVariable("userId") Long userId){
		log.info("Retrieve a user progress");
		List<UserProgressModel> userProgress = userProgressService.getAllPogressForUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Retrieved user progress successfully", userProgress), HttpStatus.OK);
	}
	
	@GetMapping("/course/{courseId}/progress")
	public ResponseEntity<ApiResponse> getAllProgressForCourse(@PathVariable("courseId") Long courseId){
		log.info("Retrieve a user progress");
		List<UserProgressModel> userProgress = userProgressService.getAllProgressForCourse(courseId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Retrieved user progress successfully", userProgress), HttpStatus.OK);
	}
}
