package com.cloudthat.elearningbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Content;
import com.cloudthat.elearningbackend.entity.Lesson;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.ContentModel;
import com.cloudthat.elearningbackend.model.LessonModel;
import com.cloudthat.elearningbackend.repository.ContentRepository;
import com.cloudthat.elearningbackend.repository.CourseRepository;
import com.cloudthat.elearningbackend.repository.LessonRepository;


@Service
public class LessonServiceImpl implements LessonService {

	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ContentRepository contentRepository;
	
	
	@Override
	public LessonModel addLesson(Long courseId, LessonModel lessonModel) {
		// TODO Auto-generated method stub
		if(Objects.isNull(lessonModel.getCourseId()) || "".equals(lessonModel.getCourseId())) {
			lessonModel.setCourseId(courseId);
		}
		Lesson lesson = lessonModelToLesson(lessonModel);
		
		Lesson newLesson = lessonRepository.save(lesson);
		return lessonToLessonModel(newLesson);
	}
	

	@Override
	public List<LessonModel> getLessonsForCourse(Long courseId) {
		Course course;
		try {
			// TODO Auto-generated method stub
			course = courseRepository.findById(courseId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
		
		List<Lesson> lessons = lessonRepository.findAllByCourse(course);
		List<LessonModel> lessonsList = new ArrayList<LessonModel>();
		
		for(Lesson l: lessons) {
			lessonsList.add(lessonToLessonModel(l));
		}
		return lessonsList;
	}
	

	@Override
	public LessonModel getLesson(Long lessonId) {
		Lesson lesson;
		try {
			// TODO Auto-generated method stub
			lesson = lessonRepository.findById(lessonId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Lesson", "Id", lessonId);
		}
		return lessonToLessonModel(lesson);
	}

	@Override
	public void deleteLesson(Long lessonId) {
		Lesson lesson ;
		try {
			// TODO Auto-generated method stub
			lesson = lessonRepository.findById(lessonId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Lesson", "Id", lessonId);
		}
		lessonRepository.delete(lesson);
	}
	

	protected Lesson lessonModelToLesson(LessonModel lessonModel) {
		Lesson lesson = new Lesson();
		lesson.setLessonTitle(lessonModel.getLessonTitle());
		lesson.setLessonOrder(lessonModel.getLessonOrder());
		
		Course course;
		try {
			course = courseRepository.findById(lessonModel.getCourseId()).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", lessonModel.getCourseId());
		}
		lesson.setCourse(course);
		
		return lesson;
	}
	
	protected LessonModel lessonToLessonModel(Lesson lesson) {
		LessonModel lessonModel = new LessonModel();
		lessonModel.setLessonId(lesson.getLessonId());
		lessonModel.setCourseId(lesson.getCourse().getCourseId());
		lessonModel.setLessonTitle(lesson.getLessonTitle());
		lessonModel.setLessonOrder(lesson.getLessonOrder());
		
		List<ContentModel> courseContentsList = new ArrayList<ContentModel>();
		List<Content> contents = contentRepository.findAllByLesson(lesson);
		
		for(Content c: contents) {
			courseContentsList.add(courseContentToCourseContentModel(c));
		}
		
		lessonModel.setCourseContents(courseContentsList);
		
		return lessonModel;
	}
	
	protected ContentModel courseContentToCourseContentModel(Content content) {
		ContentModel contentModel = new ContentModel();
		contentModel.setContentId(content.getContentId());
		contentModel.setContentDescription(content.getContentDescription());
		contentModel.setContentTitle(content.getContentTitle());
		contentModel.setContentOrder(content.getContentOrder());
		contentModel.setContentUrl(content.getContentUrl());
		contentModel.setLessonId(content.getLesson().getLessonId());
		contentModel.setContentType(content.getContentType());
		return contentModel;
	}
}
