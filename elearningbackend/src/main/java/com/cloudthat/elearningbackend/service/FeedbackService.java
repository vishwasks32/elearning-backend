package com.cloudthat.elearningbackend.service;

import java.util.List;

import com.cloudthat.elearningbackend.model.FeedbackModel;

public interface FeedbackService {

	FeedbackModel addFeedback(FeedbackModel feedbackModel);

	FeedbackModel getFeedback(Long feedbackId);

	List<FeedbackModel> getCourseFeedbacks(Long courseId);

	List<FeedbackModel> getFacultyFeedbacks(Long facultyId);

	List<FeedbackModel> getStudentFeedbacks(Long studentId);

}
