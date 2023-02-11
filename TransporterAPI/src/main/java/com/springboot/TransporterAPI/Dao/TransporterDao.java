package com.springboot.TransporterAPI.Dao;

import java.util.List;
import java.util.Optional;

import com.springboot.TransporterAPI.Entity.Verified;
import com.springboot.TransporterAPI.Response.GetResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.TransporterAPI.Entity.Transporter;

@Repository
public interface TransporterDao extends JpaRepository<Transporter, String>  {
	//@Query("select t from Transporter t")
	//public List<Transporter> getAll(Pageable pageable);

	//Verification Table Changes
	// Create GetResponse interface to get the proper response while getting and implemented getVerified insetad of getAll method.
	@Query("select t from Transporter t")
	public List<GetResponse> getVerified(Pageable pageable);

	//For getting a single transporter.
	@Query("select t from Transporter t where t.transporterId = :transporterId")
	public GetResponse getOneVerified(String transporterId);

	@Query("select v from Verified v")
	public List<Verified> getVerification();

	@Query("select t from Transporter t where t.phoneNo = :phoneNo")
	public Optional<Transporter> findByPhoneNo(String phoneNo);

	@Query("select t from Transporter t where t.phoneNo = :phoneNo")
	public Optional<GetResponse> findByPhoneNoVerified(String phoneNo);

	@Query("select t from Transporter t where t.transporterApproved = :transporterApproved")
	public List<GetResponse> findByTransporterApproved(Boolean transporterApproved, Pageable pageable);

	@Query("select t from Transporter t where t.companyApproved = :companyApproved")
	public List<GetResponse> findByCompanyApproved(Boolean companyApproved, Pageable pageable);

	@Query("select t from Transporter t where t.transporterApproved = :transporterApproved AND t.companyApproved = :companyApproved")
	public List<GetResponse> findByTransporterCompanyApproved(Boolean transporterApproved, Boolean companyApproved, Pageable pageable);

}
