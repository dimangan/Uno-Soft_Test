package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Reader {
    BufferedReader bf;
    public Reader(Path path){
        try{
            this.bf = Files.newBufferedReader(path);
        }
        catch(IOException e){
            this.bf = null;
        }
    }
    public String readLine(){
        String line;
        try{
            line = bf.readLine();
        }
        catch(IOException e){
            line = null;
        }
        return line;
    }
    private List<String> splitString(String inputString){
        return Arrays.asList(inputString.split(";"));
    }
    public List<String> isCorrect(String inputString){
        List<String> correctSplitString = splitString(inputString);
        for(String note: correctSplitString){
            if(note.split("\"").length > 2){
                correctSplitString = null;
                break;
            }
        }
        return correctSplitString;
    }
    public boolean ready(){
        boolean ready;
        try{
            ready = bf.ready();
        }
        catch(IOException e){
            ready = false;
        }
        return ready;
    }
}
