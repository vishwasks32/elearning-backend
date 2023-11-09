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
import com.cloudthat.elearningbackend.entity.Role;
import com.cloudthat.elearningbackend.entity.Technology;
import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.CourseDetails;
import com.cloudthat.elearningbackend.model.CourseModel;
import com.cloudthat.elearningbackend.model.EnrollmentModel;
import com.cloudthat.elearningbackend.model.StudentModel;
import com.cloudthat.elearningbackend.repository.CourseRepository;
import com.cloudthat.elearningbackend.repository.EnrollmentRepository;
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
	
	@Autowired
	EnrollmentRepository enrollmentRepository;

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

	
	@Override
	public EnrollmentModel enrollCourse(Long courseId, Long studentId) {
		Course courseDB;
		User studentDB;
		//Note: not validated if the userId is an Instructor
		try {
			// TODO Auto-generated method stub
			courseDB = courseRepository.findById(courseId).get();
			if(!courseDB.getIsActive()) {
				throw new NoSuchElementException("Course is Disabled");
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "Id", courseId);
		}
		
		
		try {
			// TODO Auto-generated method stub
			studentDB = userRepository.findById(studentId).get();
			if(!studentDB.getIsActive()) {
				throw new NoSuchElementException("Student is Disabled");
			}

		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Student", "Id", studentId);
		}
		
		if(studentDB.getRole() != Role.STUDENT) {
			throw new ResourceAccessException("The user cannot be enrolled a course");
		}
		
		Enrollment enroll = new Enrollment();
		enroll.setCourse(courseDB);
		enroll.setStudent(studentDB);
		
		try {
			enrollmentRepository.save(enroll);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException("Duplicate Enrollment");
		}
		return enrollmentToEnrollmentModel(enroll);
	}
	
	private EnrollmentModel enrollmentToEnrollmentModel(Enrollment enrollment) {
		EnrollmentModel enrollementModel = new EnrollmentModel();
		enrollementModel.setEnrollmentId(enrollment.getEnrollmentId());
		enrollementModel.setCourseId(enrollment.getCourse().getCourseId());
		enrollementModel.setStudentId(enrollment.getStudent().getId());
		enrollementModel.setRole(enrollment.getStudent().getRole());
		
		return enrollementModel;
	}


	@Override
	public List<StudentModel> getStudentsForCourse(Long courseId) {
		// TODO Auto-generated method stub
		Course course;
		try {
			course = courseRepository.findById(courseId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Course", "id", courseId);
		}
		List<Enrollment> enrollments = enrollmentRepository.findAllByCourse(course);
		List<StudentModel> students = new ArrayList<StudentModel>();
		for(Enrollment e: enrollments) {
			students.add(userToStudentModel(e.getStudent()));
		}
		return students;
	}
	
	private StudentModel userToStudentModel(User user) {
		StudentModel studentModel = new StudentModel();
		studentModel.setEmail(user.getEmailId());
		studentModel.setFirstName(user.getFirstName());
		studentModel.setId(user.getId());
		
		return studentModel;
	}


	@Override
	public List<CourseDetails> getCoursesForStudent(Long userId) {
		// TODO Auto-generated method stub
		User student;
		try {
			student = userRepository.findById(userId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("User", "id", userId);
		}
		List<CourseDetails> courses = new ArrayList<CourseDetails>();
		List<Enrollment> enrollments = enrollmentRepository.findAllByStudent(student);
		for(Enrollment e: enrollments) {
			courses.add(courseToCourseDetails(e.getCourse()));
		}
		return courses;
	}


	@Override
	public List<CourseDetails> getCoursesByTechnology(Long techId) {
		// TODO Auto-generated method stub
		Technology technology;
		try {
			technology = technologyRepository.findById(techId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Technology", "id", techId);
		}
		List<Course> courses = courseRepository.findAllByTechnology(technology);
		List<CourseDetails> coursesList = new ArrayList<CourseDetails>();
		
		for(Course c: courses) {
			coursesList.add(courseToCourseDetails(c));
		}
		return coursesList;
	}

}
