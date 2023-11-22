package com.cloudthat.elearningbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudthat.elearningbackend.model.ApiResponse;
import com.cloudthat.elearningbackend.model.FeedbackModel;
import com.cloudthat.elearningbackend.service.FeedbackService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class FeedbackController {
	
	@Autowired
	FeedbackService feedbackService;
	
	@PostMapping("feedback")
	public ResponseEntity<ApiResponse> addFeedback(@RequestBody FeedbackModel feedbackModel){
		log.info("Add a feedback");
		FeedbackModel feedback = feedbackService.addFeedback(feedbackModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Feedback added successfully", feedback), HttpStatus.OK);
	}
	
	@GetMapping("/feedback/{feedbackId}")
	public ResponseEntity<ApiResponse> getFeedback(@PathVariable("feedbackId") Long feedbackId){
		log.info("Retrieve a feedback");
		FeedbackModel feedback = feedbackService.getFeedback(feedbackId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Feedback retrieved successfully", feedback), HttpStatus.OK);
	}
	
	@GetMapping("/courses/{courseId}/feedback")
	public ResponseEntity<ApiResponse> getCourseFeedbacks(@PathVariable("courseId") Long courseId){
		log.info("Retrieve a feedback");
		List<FeedbackModel> feedbacks = feedbackService.getCourseFeedbacks(courseId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Feedback retrieved successfully", feedbacks), HttpStatus.OK);
	}
	
	@GetMapping("/user/faculty/{facultyId}/feedback")
	public ResponseEntity<ApiResponse> getFacultyFeedbacks(@PathVariable("facultyId") Long facultyId){
		log.info("Retrieve a feedback");
		List<FeedbackModel> feedbacks = feedbackService.getFacultyFeedbacks(facultyId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Feedback retrieved successfully", feedbacks), HttpStatus.OK);
	}
	
	@GetMapping("/user/student/{studentId}/feedback")
	public ResponseEntity<ApiResponse> getStudentFeedbacks(@PathVariable("studentId") Long studentId){
		log.info("Retrieve a feedback");
		List<FeedbackModel> feedbacks = feedbackService.getStudentFeedbacks(studentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Feedback retrieved successfully", feedbacks), HttpStatus.OK);
	}
}
