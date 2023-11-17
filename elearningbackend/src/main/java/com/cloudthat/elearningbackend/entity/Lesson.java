package com.cloudthat.elearningbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.hibernate.validator.constraints.Length;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lesson {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long lessonId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="course_id" , foreignKey = @ForeignKey(name = "FK_COURSE_LESSONS"))
	private Course course;
	
	@Length(min=0,max=30)
	@NotNull
	private String lessonTitle;
	
	@NotNull
	private int lessonOrder;
}
