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
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId", foreignKey= @ForeignKey(name="FK_USER_ENROLLMENT"))
	private User userId;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseId",foreignKey= @ForeignKey(name="FK_COURSE_ENROLLMENT"))
	private Course courseId;
}
