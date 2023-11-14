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
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long contentId;
	
	@Length(min=0,max=30)
	private String contentTitle;
	
	@Length(min=0,max=100)
	private String contentUrl;
	
	@Length(min=0,max=100)
	private String contentDescription;
	
	private int contentOrder;
	
	private String contentType;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="lesson_id",foreignKey = @ForeignKey(name = "FK_LESSON_COURSECONTENT"))
	private Lesson lesson;
	
	
	
}
