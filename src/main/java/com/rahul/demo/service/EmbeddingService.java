package com.rahul.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    public List<Double> generateEmbedding(String text){
        List<Double> vector= new ArrayList<>();

        for(int i =0; i<50; i++){
            vector.add(Math.random());
        }
        return vector;
    }

}
