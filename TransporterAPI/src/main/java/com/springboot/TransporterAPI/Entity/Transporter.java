package com.springboot.TransporterAPI.Entity;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "Transporter")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transporter {
	@Id
	private String transporterId;
	@Column(unique=true)
	@NotBlank(message = "Phone no. cannot be blank!")
	//	"^[6-9]\\d{9}$", "(^$|[0-9]{10})"
	@Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number") 
	private String phoneNo;
	private String transporterName;
	private String companyName;
	private String transporterLocation;
	private String kyc;
	private boolean companyApproved;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean transporterApproved;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean accountVerificationInProgress;
	
	@CreationTimestamp
	public Timestamp timestamp;

	//Creating verified_id for joining transporter table with verification table.
	//Cascade helps to post verified table while posting transporter.
	//Cascade helps to delete the verified tables entity in case transporter is deleted, Also does the same for update.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="verifiedId")
	private Verified verified;
}
