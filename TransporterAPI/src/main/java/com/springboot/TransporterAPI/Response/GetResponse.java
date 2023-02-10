package com.springboot.TransporterAPI.Response;
import java.sql.Timestamp;

public interface GetResponse {
        //This is the getResponse interface class to implement the transporter response correctly from the joined table verification_data.
        String getTransporterId();
        String getTransporterName();
        String getTransporterLocation();
        String getPhoneNo();
        String getCompanyName();
        String getKyc();
        String getCompanyApproved();
        String getTransporterApproved();
        String getAccountVerificationInProgress();
        Timestamp getTimestamp();
}
