package com.cloudthat.elearningbackend.entity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feedback {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User student;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User faculty;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Course course;
	
	@Length(min=0,max=255)
	private String content;
	
	private int rating;
	
	@CreationTimestamp
	private Instant createdAt;
	
	
}
