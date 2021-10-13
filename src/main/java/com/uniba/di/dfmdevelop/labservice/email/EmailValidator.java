package com.uniba.di.dfmdevelop.labservice.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        /*
            1) A-Z characters allowed
            2) a-z characters allowed
            3) 0-9 numbers allowed
            4) Additionally email may contain only dot(.), dash(-) and underscore(_)
            5) Rest all characters are not allowed
         */
        String regex = "^[A-Za-z0-9+_.-]+@^[A-Za-z0-9+_.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}