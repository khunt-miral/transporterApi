package com.springboot.TransporterAPI.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.springboot.TransporterAPI.Entity.Verified;
import com.springboot.TransporterAPI.Response.GetResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransporterServiceImpl implements TransporterService {


	@Autowired
	private TransporterDao transporterdao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TransporterCreateResponse addTransporter(PostTransporter postTransporter) {
		log.info("addTransporter service is started");

		String temp="";
		Transporter transporter = new Transporter();
		Verified verified = new Verified();
		TransporterCreateResponse response = new TransporterCreateResponse();

		Optional<Transporter> t = transporterdao.findByPhoneNo(postTransporter.getPhoneNo());
		if (t.isPresent()) {
			response.setTransporterId(t.get().getTransporterId());
			response.setPhoneNo(t.get().getPhoneNo());
			//response.setTransporterName(t.get().getTransporterName());
			//getting transporterName from verification_data table as getName()
			response.setTransporterName(t.get().getVerified().getName());
			//response.setTransporterLocation(t.get().getTransporterLocation());
			//getting transporterLocation from verification_data table as getAddress()
			response.setTransporterLocation(t.get().getVerified().getAddress());
			response.setCompanyName(t.get().getCompanyName());
			response.setKyc(t.get().getKyc());
			response.setTransporterApproved(t.get().isTransporterApproved());
			response.setCompanyApproved(t.get().isCompanyApproved());
			response.setAccountVerificationInProgress(t.get().isAccountVerificationInProgress());
			response.setMessage(CommonConstants.accountExist);
			response.setTimestamp(t.get().getTimestamp());
			return response;
		}

		temp="transporter:"+UUID.randomUUID();
		transporter.setTransporterId(temp);
		response.setTransporterId(temp);
		//setting refrence_id same as transporter_id
		verified.setReference_id(temp);

		temp=postTransporter.getPhoneNo();
		transporter.setPhoneNo(temp);
		response.setPhoneNo(temp);

/*		temp=postTransporter.getTransporterName();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp.trim());
			response.setTransporterName(temp.trim());
		}*/

		//TransporterName Passed from either verification_data table or from transporter table depending on
		//condition that is the transporter verified or not.
		if(postTransporter.getVerified().getName() != null){
			log.info("adding name from verified");
			temp=postTransporter.getVerified().getName();
		}
		else{
			log.info("adding name from trasnporter");
			temp=postTransporter.getTransporterName();
		}
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp);
			response.setTransporterName(temp);
		}

		temp=postTransporter.getCompanyName();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setCompanyName(temp.trim());
			response.setCompanyName(temp.trim());
		}

/*		temp=postTransporter.getTransporterLocation();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp.trim());
			response.setTransporterLocation(temp.trim());
		}*/

		//TransporterLocation Passed from either verification_data table or from transporter table depending on
		//condition that is the transporter verified or not.
		if(postTransporter.getVerified().getAddress() != null){
			log.info("adding location from verified");
			temp=postTransporter.getVerified().getAddress();
		}
		else{
			log.info("adding location from trasnporter");
			temp=postTransporter.getTransporterLocation();
		}
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp);
			response.setTransporterLocation(temp);
		}
		//for posting verification will delete this later.
		verified.setAddress(postTransporter.getVerified().getAddress());
		verified.setName(postTransporter.getVerified().getName());

		//joinscolumn refrence_id with verified_id
		transporter.setVerified(verified);
		response.setVerified(verified);

		temp=postTransporter.getKyc();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setKyc(temp.trim());
			response.setKyc(temp.trim());
		}

		transporter.setTransporterApproved(false);
		response.setTransporterApproved(false);

		transporter.setCompanyApproved(false);
		response.setCompanyApproved(false);

		transporter.setAccountVerificationInProgress(false);
		response.setAccountVerificationInProgress(false);

		transporterdao.save(transporter);
		log.info("transporter is saved to the database");

		response.setStatus(CommonConstants.pending);
		response.setMessage(CommonConstants.approveRequest);

		log.info("addTransporter response is returned");
		response.setTimestamp(transporter.getTimestamp());
		return response;
	}

	// Using getOneVerified instead of getOneTransporter for getting proper response after joining verification_data.
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public GetResponse getOneVerified(String transporterId) {
		GetResponse S = transporterdao.getOneVerified(transporterId);
		return S;
	}
/*	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public Transporter getOneTransporter(String transporterId) {
		log.info("getOneTransporter service is started");
		Optional<Transporter> S = transporterdao.findById(transporterId);
		if(S.isEmpty()) {
			throw new EntityNotFoundException(Transporter.class,"id",transporterId);
		}
		log.info("getOneTransporter response is returned");
		return S.get();
	}*/

	//Converted List<Transporter> to List<GetResponse> for proper response.
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public List<GetResponse> getTransporters(Boolean transporterApproved, Boolean companyApproved, String phoneNo, Integer pageNo){
		log.info("getTransporters service is started");
		//List<Transporter> list = null;
		List<GetResponse> list = null;
		if(pageNo == null) {
			pageNo = 0;
		}

		Pageable page = PageRequest.of(pageNo, 15, Sort.Direction.DESC, "timestamp");
		if((companyApproved != null) && (transporterApproved != null)) {
			list = transporterdao.findByTransporterCompanyApproved(transporterApproved, companyApproved, page);
			//Collections.reverse(list);
			return list;
		}

		if(transporterApproved != null) {
			list = transporterdao.findByTransporterApproved(transporterApproved, page);
			//Collections.reverse(list);
			return list;
		}
		if(companyApproved != null) {
			list = transporterdao.findByCompanyApproved(companyApproved, page);
			//Collections.reverse(list);
			return list;
		}
		if(phoneNo != null) {
			String validate = "^[6-9]\\d{9}$";
			Pattern pattern = Pattern.compile(validate);
			Matcher m = pattern.matcher(phoneNo);
			
			if(m.matches()) {
				if(transporterdao.findByPhoneNo(phoneNo).isPresent()) {
					list = List.of(transporterdao.findByPhoneNoVerified(phoneNo).get());
					return list;
				}
				else {
					throw new EntityNotFoundException(Transporter.class, "phoneNo", phoneNo.toString());
				}
			}
			else {
				//throw new MethodArgumentNotValidException(null, null);
				throw new BusinessException("Invalid mobile number");
			}
		}

		list = transporterdao.getVerified(page);
		//Collections.reverse(list);

		log.info("getTransporters response is returned");
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TransporterUpdateResponse updateTransporter(String transporterId, UpdateTransporter updateTransporter) {
		log.info("updateTransporter service is started");

		TransporterUpdateResponse updateResponse = new TransporterUpdateResponse();

		Optional<Transporter> T = transporterdao.findById(transporterId);
		if(T.isEmpty())
			throw new EntityNotFoundException(Transporter.class, "id", transporterId.toString());

		String temp="";
		Transporter transporter = T.get();

		if (updateTransporter.getPhoneNo() != null) {			
			throw new BusinessException("Phone no can't be updated");
		}

		temp=updateTransporter.getTransporterName();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp.trim());
		}

		temp=updateTransporter.getTransporterLocation();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp.trim());
		}

		temp=updateTransporter.getCompanyName();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setCompanyName(temp.trim());
		}

		if (updateTransporter.getKyc() != null) {
			transporter.setKyc(updateTransporter.getKyc());
		}

		if (updateTransporter.getTransporterApproved() != null) {
			transporter.setTransporterApproved(updateTransporter.getTransporterApproved());
		}

		if (updateTransporter.getCompanyApproved() != null) {
			transporter.setCompanyApproved(updateTransporter.getCompanyApproved());
		}

		if(updateTransporter.getAccountVerificationInProgress() != null) {
			transporter.setAccountVerificationInProgress(updateTransporter.getAccountVerificationInProgress());
		}

		transporterdao.save(transporter);
		log.info("transporter is updated in the database");

		updateResponse.setTransporterId(transporter.getTransporterId());
		updateResponse.setPhoneNo(transporter.getPhoneNo());
		updateResponse.setTransporterName(transporter.getTransporterName());
		updateResponse.setTransporterLocation(transporter.getTransporterLocation());
		updateResponse.setCompanyName(transporter.getCompanyName());
		updateResponse.setKyc(transporter.getKyc());
		updateResponse.setTransporterApproved(transporter.isTransporterApproved());
		updateResponse.setCompanyApproved(transporter.isCompanyApproved());
		updateResponse.setAccountVerificationInProgress(transporter.isAccountVerificationInProgress());
		updateResponse.setStatus(CommonConstants.success);
		updateResponse.setMessage(CommonConstants.updateSuccess);
		updateResponse.setTimestamp(transporter.getTimestamp());

		log.info("updateTransporter response is returned");
		return updateResponse;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteTransporter(String transporterId) {
		log.info("deleteTransporter service is started");
		Optional<Transporter> T = transporterdao.findById(transporterId);
		if(T.isEmpty())
			throw new EntityNotFoundException(Transporter.class, "id", transporterId.toString());

		transporterdao.deleteById(transporterId);
		log.info("transporter is deleted in the database");
	}

	//Used to get data of verifiation_table.
	@Override
	public List<Verified> getVerification(){

		List<Verified> actual = null;
		actual = transporterdao.getVerification();
		return actual;
	}

}
