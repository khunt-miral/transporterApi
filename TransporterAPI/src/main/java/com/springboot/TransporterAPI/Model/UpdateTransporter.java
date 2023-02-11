package com.springboot.TransporterAPI.Model;

import com.springboot.TransporterAPI.Entity.Verified;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransporter {
	private String phoneNo;
	private String transporterLocation;
	private String transporterName;
	private String companyName;
	private String kyc;
	private Boolean companyApproved;
	private Boolean transporterApproved;
	private Boolean accountVerificationInProgress;
	//for verified_id
	private Verified verified;
}
