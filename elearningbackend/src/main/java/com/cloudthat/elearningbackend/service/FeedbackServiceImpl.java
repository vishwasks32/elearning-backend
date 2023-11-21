package com.cloudthat.elearningbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Enrollment;
import com.cloudthat.elearningbackend.entity.Feedback;
import com.cloudthat.elearningbackend.entity.Role;
import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.FeedbackModel;
import com.cloudthat.elearningbackend.repository.CourseRepository;
import com.cloudthat.elearningbackend.repository.EnrollmentRepository;
import com.cloudthat.elearningbackend.repository.FeedbackRepository;
import com.cloudthat.elearningbackend.repository.UserRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	protected FeedbackModel feedbackToFeedbackModel(Feedback feedback) {
		FeedbackModel feedbackModel = new FeedbackModel();
		feedbackModel.setId(feedback.getId());
		feedbackModel.setCourseId(feedback.getCourse().getCourseId());
		feedbackModel.setContent(feedback.getContent());
		feedbackModel.setFacultyId(feedback.getFaculty().getId());
		feedbackModel.setRating(feedback.getRating());
		feedbackModel.setStudentId(feedback.getStudent().getId());
		return feedbackModel;
		
	}
	
	protected Feedback feedbackModelToFeedback(FeedbackModel feedbackModel) {
		Feedback feedback = new Feedback();
		feedback.setContent(feedbackModel.getContent());
		feedback.setRating(feedbackModel.getRating());
		
		Course course;
		try {
			course = courseRepository.findById(feedbackModel.getCourseId()).get();
		
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", feedbackModel.getCourseId());
		}
		feedback.setCourse(course);
		
		User faculty;
		
		try {
			faculty = userRepository.findById(feedbackModel.getFacultyId()).get();
			if(faculty.getRole() != Role.INSTRUCTOR) {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Faculty", "Id", feedbackModel.getFacultyId());
		}
		feedback.setFaculty(faculty);
		
		User student;
		
		try {
			student = userRepository.findById(feedbackModel.getStudentId()).get();
			if(student.getRole() != Role.STUDENT) {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Student", "Id", feedbackModel.getStudentId());
		}
		feedback.setStudent(student);
		
		// check if faculty for course match
		if(feedbackModel.getFacultyId() != course.getFaculty().getId()) {
			throw new DataIntegrityViolationException("Faculty Id does not match with course");
		}
		
		// check if student is enrolled in course
		Enrollment enrollment;
		enrollment = enrollmentRepository.findByStudentAndCourse(student, course);
		if(Objects.isNull(enrollment)) {
			throw new ResourceAccessException("Student not Enrolled in course");
		}
		
		//Check if feedback already exists
		Feedback feedbackDB;
		feedbackDB = feedbackRepository.findByStudentAndCourse(student,course);
		if(Objects.nonNull(feedbackDB)) {
			throw new DataIntegrityViolationException("Feedback already submitted");
		}
		
		return feedback;
	}

	@Override
	public FeedbackModel addFeedback(FeedbackModel feedbackModel) {		
		Feedback feedback;
		feedback = feedbackRepository.save(feedbackModelToFeedback(feedbackModel));
		return feedbackToFeedbackModel(feedback);
	}

	@Override
	public FeedbackModel getFeedback(Long feedbackId) {
		// TODO Auto-generated method stub
		Feedback feedbackDB;
		
		try {
			feedbackDB = feedbackRepository.findById(feedbackId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Feedback", "Id", feedbackId);
		}
		return feedbackToFeedbackModel(feedbackDB);
	}

	@Override
	public List<FeedbackModel> getCourseFeedbacks(Long courseId) {
		// TODO Auto-generated method stub
		Course course;
		try {
			course = courseRepository.findById(courseId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
		
		List<Feedback> feedbacks = feedbackRepository.findByCourse(course);
		List<FeedbackModel> feedbackList = new ArrayList<FeedbackModel>();
		for(Feedback fb: feedbacks) {
			feedbackList.add(feedbackToFeedbackModel(fb));
		}
		return feedbackList;
	}

	@Override
	public List<FeedbackModel> getFacultyFeedbacks(Long facultyId) {
		// TODO Auto-generated method stub
		User faculty;
		
		try {
			faculty = userRepository.findById(facultyId).get();
			if(faculty.getRole() != Role.INSTRUCTOR) {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Faculty", "Id", facultyId);
		}
		
		List<Feedback> feedbacks = feedbackRepository.findByFaculty(faculty);
		List<FeedbackModel> feedbackList = new ArrayList<FeedbackModel>();
		for(Feedback fb: feedbacks) {
			feedbackList.add(feedbackToFeedbackModel(fb));
		}
		return feedbackList;
	}

	@Override
	public List<FeedbackModel> getStudentFeedbacks(Long studentId) {
		// TODO Auto-generated method stub
		User student;
		
		try {
			student = userRepository.findById(studentId).get();
			if(student.getRole() != Role.STUDENT) {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Student", "Id", studentId);
		}
		List<Feedback> feedbacks = feedbackRepository.findByStudent(student);
		List<FeedbackModel> feedbackList = new ArrayList<FeedbackModel>();
		for(Feedback fb: feedbacks) {
			feedbackList.add(feedbackToFeedbackModel(fb));
		}
		return feedbackList;
	}

}
