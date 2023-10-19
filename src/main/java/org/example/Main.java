package org.example;


import java.io.*;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        if(args.length == 1){
            Path path = Path.of(args[0]);
            long timeStart = Instant.now().getEpochSecond();
            GroupMap groupMap = new GroupMap();
            GroupLinkMap groupLinkMap = new GroupLinkMap();
            Grouper grouper = new Grouper(groupLinkMap, groupMap);
            Reader reader = new Reader(path);
            while(reader.ready()){
                String line = reader.readLine();
                List<String> stringSplit = reader.isCorrect(line);
                if(stringSplit != null){
                    Set<Integer> possibleGroups = grouper.getPossibleGroups(stringSplit);
                    grouper.groupStrings(possibleGroups, line, stringSplit);
                }
            }
            groupMap.getGroupMap().entrySet().removeIf(entry -> groupMap.getGroupMap().get(entry.getKey()).size() < 2);
            long seconds = Instant.now().getEpochSecond() - timeStart;
            System.out.println("Amount of groups: " + groupMap.getGroupMap().size());
            System.out.println("Processing time in seconds: " + seconds);
            try{
                groupMap.printGroupsInFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Wrong amount of arguments");
        }
    }
}
