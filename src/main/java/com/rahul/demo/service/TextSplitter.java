package com.rahul.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TextSplitter {

    public List<String> splitText(String text){
        List<String> chunks = new ArrayList<>();

        int chunkSize= 500;
        for(int i= 0; i<text.length(); i+= chunkSize){
            chunks.add(text.substring(i,Math.min(text.length(),i+chunkSize)));
        }
        return chunks;
    }


}
