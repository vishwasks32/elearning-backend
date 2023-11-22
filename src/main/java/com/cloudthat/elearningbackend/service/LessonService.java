package com.cloudthat.elearningbackend.service;

import java.util.List;

import com.cloudthat.elearningbackend.model.LessonModel;

public interface LessonService {

	LessonModel addLesson(Long courseId, LessonModel lessonModel);

	List<LessonModel> getLessonsForCourse(Long courseId);

	LessonModel getLesson(Long lessonId);

	void deleteLesson(Long lessonId);

}
