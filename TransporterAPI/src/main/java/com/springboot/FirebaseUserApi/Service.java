package com.springboot.FirebaseUserApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;



@org.springframework.stereotype.Service
public class Service {

    //getting uid by phone number
    public String getByEmail(String email) throws  FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
           return userRecord.getUid();
    }
}
