package com.cloudthat.elearningbackend.entity;

import org.hibernate.validator.constraints.Length;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long contentId;
	
	@Length(min=0,max=30)
	private String contentTitle;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="course_id", foreignKey = @ForeignKey(name = "FK_COURSE_COURSECONTENT"))
	private Course courseId;
	
	@Length(min=0,max=100)
	private String courseUrl;
	
	@Length(min=0,max=100)
	private String contentDescription;
	
	
	private int contentOrder;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lesson_id",foreignKey = @ForeignKey(name = "FK_LESSON_COURSECONTENT"))
	private Lessons lessonId;
	
	
	
}
