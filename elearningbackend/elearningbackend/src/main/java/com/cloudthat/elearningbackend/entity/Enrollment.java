package com.cloudthat.elearningbackend.entity;

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
public class Enrollment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long enrollmentId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="student_id", foreignKey= @ForeignKey(name="FK_USER_ENROLLMENT"))
	private User student;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="course_id",foreignKey= @ForeignKey(name="FK_COURSE_ENROLLMENT"))
	private Course course;
}
