package com.springboot.FirebaseUserApi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



@Configuration
public class FirebaseConfig {

    @Value("${FIREBASE_URL}")
    private String firebaseUrl;
    FileInputStream refreshToken = new FileInputStream(firebaseUrl);

    FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(refreshToken))
            .setDatabaseUrl("https://liveasytransporterapp-default-rtdb.firebaseio.com")
            .build();

    public FirebaseConfig() throws IOException {
        FirebaseApp.initializeApp(options);
    }


}

