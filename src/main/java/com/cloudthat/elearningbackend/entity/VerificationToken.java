package com.cloudthat.elearningbackend.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
	// Expiration time as 10 min
		private static final int EXPIRATION_TIME = 10;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String token;
		
		@OneToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="FK_USER_VERIFYTOKEN"))
		private User user;
		private Date expirationTime;

		public VerificationToken(String token, User user) {
			super();
			this.token = token;
			this.user = user;
			this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
		}

		private Date calculateExpirationDate(int expirationTime) {
			// TODO Auto-generated method stub
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(new Date().getTime());
			calendar.add(Calendar.MINUTE, expirationTime);
			return new Date(calendar.getTime().getTime());
		}

		public VerificationToken(String token) {
			super();
			this.token = token;
			this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
		}
		
		
		
		
}
