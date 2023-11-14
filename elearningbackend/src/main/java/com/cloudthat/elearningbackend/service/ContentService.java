package com.cloudthat.elearningbackend.service;

import com.cloudthat.elearningbackend.model.ContentModel;

public interface ContentService {

	ContentModel addContent(Long lessonId, ContentModel contentModel);

	ContentModel getContentDetails(Long contentId);

	ContentModel updateContent(Long lessonId, ContentModel contentModel);

	void deleteContent(Long contentId);


}
