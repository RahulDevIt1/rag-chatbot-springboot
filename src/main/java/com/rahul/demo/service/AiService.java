package com.rahul.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AiService {

    public String generateAnswer(String context,String question){
        return "This is a dummy answer based on the context: "+ context + " and the question: "+ question;          
    }
}
