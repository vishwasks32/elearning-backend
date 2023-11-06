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
	private long feedbackId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId", foreignKey=@ForeignKey(name="FK_USER_FEEDBACK"))
	private User userid;
	
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseId", foreignKey=@ForeignKey(name="FK_COURSE_FEEDBACK"))
	private Course courseid;
	
	@Length(min=0,max=255)
	private String feedbackContent;
	
	private int rating;
	
	@CreationTimestamp
	private Instant createdAt;
	
	
}
