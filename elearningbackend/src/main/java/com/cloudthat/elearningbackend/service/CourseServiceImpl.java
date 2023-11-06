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
import com.cloudthat.elearningbackend.entity.Role;
import com.cloudthat.elearningbackend.entity.Technology;
import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.CourseDetails;
import com.cloudthat.elearningbackend.model.CourseModel;
import com.cloudthat.elearningbackend.repository.CourseRepository;
import com.cloudthat.elearningbackend.repository.TechnologyRepository;
import com.cloudthat.elearningbackend.repository.UserRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TechnologyRepository technologyRepository;

	@Override
	public CourseDetails registerCourse(CourseModel courseModel) {
		// TODO Auto-generated method stub
		
		// get the faculty
		User faculty = userRepository.findById(courseModel.getFacultyId()).get();
		Technology technology = technologyRepository.findById(courseModel.getTechnologyId()).get();
		
		if(faculty == null) {
			throw new ResourceNotFoundException("User","id", courseModel.getFacultyId());
		} else if(faculty.getRole() != Role.INSTRUCTOR) {
			throw new ResourceAccessException("The user cannot create a course");
		}
		Course savedCourse = null;
		Course course = new Course();
		course.setCourseName(courseModel.getCourseName());
		course.setCourseDescription(courseModel.getCourseDescription());
		course.setCourseImage(courseModel.getCourseImage());
		course.setStartDate(courseModel.getStartDate());
		course.setEndDate(courseModel.getEndDate());
		course.setTechnology(technology);
		course.setFaculty(faculty);
		
		try {
			savedCourse = courseRepository.save(course);
		}  catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException(null);
		}
		return courseToCourseDetails(savedCourse);
	}
	

	@Override
	public List<CourseDetails> getAllCourses() {
		// TODO Auto-generated method stub
		List<CourseDetails> coursesDetails = new ArrayList<CourseDetails>();
		List<Course> courses = courseRepository.findAll();
		
		for(Course c: courses) {
			if(c.getIsActive()) {
				coursesDetails.add(courseToCourseDetails(c));
			}
		}
		return coursesDetails;
	}

	@Override
	public CourseDetails getCourseDetails(Long courseId) {
		// TODO Auto-generated method stub
		Course course;
		try {
			course = courseRepository.findById(courseId).get();
			if(!course.getIsActive()) {
				throw new NoSuchElementException("Course is Disabled");
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
		return courseToCourseDetails(course);
	}
	
	@Override
	public CourseDetails updateCourseDetails(Long courseId, CourseModel courseModel) {
		// TODO Auto-generated method stub
		Course courseDB;
		
		try {
			courseDB = courseRepository.findById(courseId).get();
			if(!courseDB.getIsActive()) {
				throw new NoSuchElementException("Course is Disabled");
			}
			
			// Note: we will not update Name of Course and Faculty ID
			if (Objects.nonNull(courseModel.getCourseDescription()) && !"".equalsIgnoreCase(courseModel.getCourseDescription())) {
				courseDB.setCourseDescription(courseModel.getCourseDescription());
			}
			
			if (Objects.nonNull(courseModel.getCourseImage()) && !"".equalsIgnoreCase(courseModel.getCourseImage())) {
				courseDB.setCourseImage(courseModel.getCourseImage());
			}
			
			if (Objects.nonNull(courseModel.getStartDate())) {
				courseDB.setStartDate(courseModel.getStartDate());
			}
			
			if (Objects.nonNull(courseModel.getEndDate())) {
				courseDB.setEndDate(courseModel.getEndDate());
			}
			
			if (Objects.nonNull(courseModel.getTechnologyId())) {
				Technology tech = technologyRepository.findById(courseModel.getTechnologyId()).get();
				courseDB.setTechnology(tech);
			}
			
			courseRepository.save(courseDB);
			
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
		return courseToCourseDetails(courseDB);
	}
	
	
	private CourseDetails courseToCourseDetails(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getCourseId());
		courseDetails.setCourseName(course.getCourseName());
		courseDetails.setCourseDescription(course.getCourseDescription());
		courseDetails.setCourseImage(course.getCourseImage());
		courseDetails.setStartDate(course.getStartDate());
		courseDetails.setEndDate(course.getEndDate());
		courseDetails.setFacultyId(course.getFaculty().getId());
		courseDetails.setTechnologyId(course.getTechnology().getId());
		return courseDetails;
	}


	@Override
	public void disableCourse(Long courseId) {
		// TODO Auto-generated method stub
		Course courseDB;
		
		try {
			courseDB = courseRepository.findById(courseId).get();
			courseDB.setIsActive(false);
			courseRepository.save(courseDB);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
	}





}
