package com.springboot.TransporterAPI.Services;

import java.util.List;

import com.springboot.TransporterAPI.Entity.Verified;
import com.springboot.TransporterAPI.Response.GetResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

public interface TransporterService {

	public TransporterCreateResponse addTransporter(PostTransporter postTransporter);

	//public List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, String phoneNo, Integer pageNo);

	//Used List<GetResponse> for correct response instead of List<Transporter>
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<GetResponse> getTransporters(Boolean transporterApproved, Boolean companyApproved, String phoneNo, Integer pageNo);

	//Giving details of a single transporter by passing transporterId. Using this instad of getOneTransporter
	public GetResponse getOneVerified(String transporterId);

	public TransporterUpdateResponse updateTransporter(String transporterId, UpdateTransporter updatetransporter);

	public void deleteTransporter(String transporterId);

	//public Transporter getOneTransporter(String transporterId);

	//To fetch verification_data details
	public List<Verified> getVerification();

}
