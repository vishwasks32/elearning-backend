package com.cloudthat.elearningbackend.entity;

import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Options {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String optionText;
	private String optionImage;
	@Convert(converter = TrueFalseConverter.class)
	private Boolean isCorrect = false;
	@ManyToOne(cascade = CascadeType.DETACH)
	private Question question;
}
