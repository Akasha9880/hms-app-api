package com.hmsapp;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class A {
    public static void main(String[] args){
       //METHOD ONE FOR ENCRYPTING PASSWORD
        //PasswordEncoder e = new BCryptPasswordEncoder();
        //System.out.println(e.encode("testing"));

        //METHOD TWO OF ENCRYPTING PASSWORD

        String encodedPwd = BCrypt.hashpw("testing", BCrypt.gensalt(12));
    System.out.println(encodedPwd);
    }
}
