package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


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
    public List<List<String>> readAllLines() throws IOException{
        List<List<String>> allSplitLines = new ArrayList<>();
        Set<String> allUniqLines = new HashSet<>();
        while(bf.ready()){
            allUniqLines.add(bf.readLine());
        }
        bf.close();
        for(String uniqLine: allUniqLines){
            List<String> splitLineList = splitLine(uniqLine);
            if(!splitLineList.isEmpty()){
                allSplitLines.add(splitLineList);
            }
        }
        return allSplitLines;
    }
    private List<String> splitLine(String inputLine){
        List<String> splitLineList = new ArrayList<>();
        for(String note: inputLine.split(";")){
            if(!note.equals("")){
                note = note.substring(1, note.length() - 1);
            }
            splitLineList.add(note);
        }
        return splitLineList;
    }
}
