package com.cloudthat.elearningbackend.service;

import java.util.List;

import com.cloudthat.elearningbackend.model.UserProgressModel;

public interface UserProgressService {

	UserProgressModel addProgress(UserProgressModel userProgressModel);

	UserProgressModel getUserCourseProgress(Long courseId, Long userId);

	UserProgressModel updateUserCourseProgress(Long courseId, Long userId, UserProgressModel userProgressModel);

	void deleteUserCourseProgress(Long courseId, Long userId);

	List<UserProgressModel> getAllPogressForUser(Long userId);

	List<UserProgressModel> getAllProgressForCourse(Long courseId);


}
