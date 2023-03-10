package com.springboot.FirebaseUserApi;

import com.google.firebase.auth.FirebaseAuthException;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirebaseController {

  @Autowired public Service service;

  @GetMapping("/phoneNo/{number}")
  public String getUserByEmail(@PathVariable String number)
      throws InterruptedException, ExecutionException, FirebaseAuthException {
    // returning uid as a response
    return service.getByEmail(number);
  }
}
