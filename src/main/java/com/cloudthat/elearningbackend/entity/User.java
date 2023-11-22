package com.cloudthat.elearningbackend.entity;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.TrueFalseConverter;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Length(min = 0, max = 30)
	private String firstName;
	@Length(min = 0, max = 30)
	private String lastName;
	@Column(unique=true)
	@Length(min = 0, max = 30)
	private String emailId;
	@Length(min = 6, max = 128)
	private String password;
	@Length(min = 0, max = 10)
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Length(min = 0, max = 255)
	private String profilePicture;
	@Convert(converter = TrueFalseConverter.class)
	private Boolean isActive = false;
	@CreationTimestamp
	private Instant createdAt;
	@UpdateTimestamp
	private Instant modifiedAt;
	
	@OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}


