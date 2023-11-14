package com.cloudthat.elearningbackend.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cloudthat.elearningbackend.entity.Content;
import com.cloudthat.elearningbackend.entity.Lesson;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.ContentModel;
import com.cloudthat.elearningbackend.repository.ContentRepository;
import com.cloudthat.elearningbackend.repository.LessonRepository;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	ContentRepository contentRepository;
	
	@Autowired
	LessonRepository lessonRepository;

	@Override
	public ContentModel addContent(Long lessonId, ContentModel contentModel) {
		// TODO Auto-generated method stub
		if(Objects.isNull(contentModel.getLessonId()) || "".equals(contentModel.getLessonId())) {
			contentModel.setLessonId(lessonId);
		}
		Content newContent;
		try {
			newContent = contentRepository.save(contentModelToContent(contentModel));
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException("Error while saving content");
		}
		
		return contentToContentModel(newContent);
	}
	
	protected Content contentModelToContent(ContentModel contentModel) {
		Content content = new Content();
		content.setContentTitle(contentModel.getContentTitle());
		content.setContentDescription(contentModel.getContentDescription());
		content.setContentOrder(contentModel.getContentOrder());
		content.setContentUrl(contentModel.getContentUrl());
		content.setContentType(contentModel.getContentType());
		
		Lesson lesson; 
		try {
			lesson = lessonRepository.findById(contentModel.getLessonId()).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Lesson", "Id", contentModel.getLessonId());
		}
		
		content.setLesson(lesson);
		
		return content;
		
	}
	
	protected ContentModel contentToContentModel(Content content) {
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

	@Override
	public ContentModel getContentDetails(Long contentId) {
		// TODO Auto-generated method stub
		Content contentDetails;
		try {
			contentDetails = contentRepository.findById(contentId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Content", "Id", contentId);
		}
		return contentToContentModel(contentDetails);
	}

	@Override
	public ContentModel updateContent(Long contentId, ContentModel contentModel) {
		// TODO Auto-generated method stub
		Content contentDB;
		try {
			contentDB = contentRepository.findById(contentId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Content", "id", contentId);
		}
		
		if (Objects.nonNull(contentModel.getContentTitle()) && !"".equalsIgnoreCase(contentModel.getContentTitle())) {
			contentDB.setContentTitle(contentModel.getContentTitle());
		}
		
		if (Objects.nonNull(contentModel.getContentDescription()) && !"".equalsIgnoreCase(contentModel.getContentDescription())) {
			contentDB.setContentDescription(contentModel.getContentDescription());
		}
		
		if (Objects.nonNull(contentModel.getContentUrl()) && !"".equalsIgnoreCase(contentModel.getContentUrl())) {
			contentDB.setContentUrl(contentModel.getContentUrl());
		}
		
		if (Objects.nonNull(contentModel.getContentType()) && !"".equalsIgnoreCase(contentModel.getContentType())) {
			contentDB.setContentType(contentModel.getContentType());
		}
		
		try {
			contentRepository.save(contentDB);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException("Error saving the content");
		}
		return contentToContentModel(contentDB);
	}

	@Override
	public void deleteContent(Long contentId) {
		Content contentDB;
		try {
			// TODO Auto-generated method stub
			contentDB = contentRepository.findById(contentId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Content", "id", contentId);
		}
		contentRepository.delete(contentDB);
	}
}
