package org.example;


import java.io.*;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        if(args.length == 1){
            Reader reader = new Reader(Path.of(args[0]));
            List<List<String>> allSplitLines;
            long timeStart = Instant.now().getEpochSecond();
            try{
                allSplitLines = reader.readAllLines();
                Grouper grouper = new Grouper(allSplitLines);
                grouper.makeGroups();
                grouper.printGroupsInFile();
            }
            catch (IOException e){
                System.out.println("Something went wrong:(");
            }
            long seconds = Instant.now().getEpochSecond() - timeStart;
            System.out.println("Processing time in seconds: " + seconds);
        }
        else{
            System.out.println("Wrong amount of arguments");
        }
    }
}
