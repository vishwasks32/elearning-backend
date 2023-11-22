package com.cloudthat.elearningbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.cloudthat.elearningbackend.entity.Course;
import com.cloudthat.elearningbackend.entity.Enrollment;
import com.cloudthat.elearningbackend.entity.Lesson;
import com.cloudthat.elearningbackend.entity.Role;
import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.entity.UserProgress;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.UserProgressModel;
import com.cloudthat.elearningbackend.repository.CourseRepository;
import com.cloudthat.elearningbackend.repository.EnrollmentRepository;
import com.cloudthat.elearningbackend.repository.LessonRepository;
import com.cloudthat.elearningbackend.repository.UserProgressRepository;
import com.cloudthat.elearningbackend.repository.UserRepository;

@Service
public class UserProgressServiceImpl implements UserProgressService {
	
	@Autowired
	UserProgressRepository userProgressRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	private User getStudent(Long studentId) {
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
		
		return student;
	}
	
	private Course getCourse(Long courseId) {
		Course course;
		
		try {
			course = courseRepository.findById(courseId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
		
		return course;
	}
	// DTO methods
	protected UserProgressModel userProgressToUserProgressModel(UserProgress userProgress) {
		UserProgressModel userProgressModel = new UserProgressModel();
		userProgressModel.setCourseId(userProgress.getCourse().getCourseId());
		userProgressModel.setStudentId(userProgress.getStudent().getId());
		userProgressModel.setLastAccessedLesson(userProgress.getLastAccessedLesson().getLessonId());
		userProgressModel.setCompletionStatus(userProgress.getCompletionStatus());
		return userProgressModel;
	}
	
	protected UserProgress userProgressModelToUserProgress(UserProgressModel userProgressModel) {
		UserProgress userProgress = new UserProgress();
		
		User student = getStudent(userProgressModel.getStudentId());
		Course course = getCourse(userProgressModel.getCourseId());
		
		Enrollment	enrollment = enrollmentRepository.findByStudentAndCourse(student, course);
		if(Objects.isNull(enrollment)) {
			throw new ResourceAccessException("Student Not Enrolled in Course");
		}
		
		Lesson lesson;
		try {
			lesson = lessonRepository.findById(userProgressModel.getLastAccessedLesson()).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Lesson", "Id", userProgressModel.getLastAccessedLesson());
		}
		userProgress.setStudent(student);
		userProgress.setCourse(course);
		userProgress.setLastAccessedLesson(lesson);
		userProgress.setCompletionStatus(userProgressModel.getCompletionStatus());
		return userProgress;
		
	}
	
	@Override
	public UserProgressModel addProgress(UserProgressModel userProgressModel) {
		// TODO Auto-generated method stub
		UserProgress userProgress = userProgressRepository.save(userProgressModelToUserProgress(userProgressModel));
		return userProgressToUserProgressModel(userProgress);
	}

	@Override
	public UserProgressModel getUserCourseProgress(Long courseId, Long userId) {
		// TODO Auto-generated method stub
		
		User student = getStudent(userId);
		
		Course course = getCourse(courseId);
		
		UserProgress userProgressDB = userProgressRepository.findByStudentAndCourse(student, course);
		if(Objects.isNull(userProgressDB)) {
			throw new NoSuchElementException("User Progress Not Found");
		}
		
		return userProgressToUserProgressModel(userProgressDB);
	}

	@Override
	public UserProgressModel updateUserCourseProgress(Long courseId, Long userId, UserProgressModel userProgressModel) {
		// TODO Auto-generated method stub
		userProgressModel.setCourseId(courseId);
		userProgressModel.setStudentId(userId);
		
		
		User student = getStudent(userId);
		
		
		Course course = getCourse(courseId);
		
		
		Enrollment	enrollment = enrollmentRepository.findByStudentAndCourse(student, course);
		if(Objects.isNull(enrollment)) {
			throw new ResourceAccessException("Student Not Enrolled in Course");
		}
		
		Lesson lesson;
		try {
			lesson = lessonRepository.findById(userProgressModel.getLastAccessedLesson()).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Lesson", "Id", userProgressModel.getLastAccessedLesson());
		}
		
		
		UserProgress userProgressDB = userProgressRepository.findByStudentAndCourse(student, course);
		if(Objects.isNull(userProgressDB)) {
			throw new NoSuchElementException("User Progress Not Found");
		}
		
		userProgressDB.setLastAccessedLesson(lesson);
		userProgressDB.setCompletionStatus(userProgressModel.getCompletionStatus());
		
		userProgressRepository.save(userProgressDB);
		
		return userProgressToUserProgressModel(userProgressDB);
	}

	@Override
	public void deleteUserCourseProgress(Long courseId, Long userId) {
		// TODO Auto-generated method stub
		User student = getStudent(userId);
		Course course = getCourse(courseId);
		UserProgress userProgressDB = userProgressRepository.findByStudentAndCourse(student, course);
		userProgressRepository.delete(userProgressDB);
	}

	@Override
	public List<UserProgressModel> getAllPogressForUser(Long userId) {
		// TODO Auto-generated method stub
		User student = getStudent(userId);
		List<UserProgress> userProgresses = userProgressRepository.findAllByStudent(student);
		
		List<UserProgressModel> userProgressModels = new ArrayList<UserProgressModel>();
		for(UserProgress ua: userProgresses) {
			userProgressModels.add(userProgressToUserProgressModel(ua));
		}
		return userProgressModels;
	}

	@Override
	public List<UserProgressModel> getAllProgressForCourse(Long courseId) {
		// TODO Auto-generated method stub
		Course course = getCourse(courseId);
		List<UserProgress> userProgresses = userProgressRepository.findAllByCourse(course);
		
		List<UserProgressModel> userProgressModels = new ArrayList<UserProgressModel>();
		for(UserProgress ua: userProgresses) {
			userProgressModels.add(userProgressToUserProgressModel(ua));
		}
		return userProgressModels;
	}

}
