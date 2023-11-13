package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Reader {
    BufferedReader bf;
    public Reader(Path path){
        try{
            this.bf = Files.newBufferedReader(path);
        }
        catch(IOException e){
            System.out.println("Файл не найден!");
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
        List<String> splitStringList = new ArrayList<>();
        for(String note: inputString.split(";")){
            if(note.length() != 0){
                splitStringList.add(note);
            }
            else{
                splitStringList.add("?");
            }
        }
        return splitStringList;
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
            if(this.bf != null){
                ready = bf.ready();
            }
            else{
                ready = false;
            }
        }
        catch(IOException e){
            ready = false;
        }
        return ready;
    }
    public void close(){
        try {
            if(this.bf != null){
                this.bf.close();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
