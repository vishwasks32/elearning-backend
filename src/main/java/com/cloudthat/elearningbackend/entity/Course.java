package com.cloudthat.elearningbackend.entity;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.type.TrueFalseConverter;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	
	@Length(min = 3, max = 30)
	private String courseName;
	
	@Length(min = 3, max = 255)
	private String courseDescription;
	
	@Length(min = 0, max = 255)
	private String courseImage;
	
	@Convert(converter = TrueFalseConverter.class)
	private Boolean isActive = true;
	
	private Date startDate;
	private Date endDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "faculty_id", foreignKey = @ForeignKey(name = "FK_FACULTY_COURSE"))
	private User faculty;
	
	@CreationTimestamp
	private Instant createdAt;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="technology_id",foreignKey = @ForeignKey(name = "FK_TECH_COURSE"), nullable=false)
	private Technology technology;
	
    // One course can have many enrollments
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
	
}
