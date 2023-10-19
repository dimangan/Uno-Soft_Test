package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupMap {
    private final Map<Integer, List<String>> groupMap;
    public GroupMap() {
        this.groupMap = new HashMap<>();
    }
    public Map<Integer, List<String>> getGroupMap() {
        return this.groupMap;
    }
    public void add(Integer key, String string){
        if(this.groupMap.get(key) != null){
            this.groupMap.get(key).add(string);
        }
        else{
            List<String> newGroup = new ArrayList<>();
            newGroup.add(string);
            this.groupMap.put(key, newGroup);
        }
    }
    public void merge(Integer firstGroup, Integer secondGroup){
        this.groupMap.get(firstGroup).addAll(this.groupMap.get(secondGroup));
        this.groupMap.remove(secondGroup);
    }
    public void printGroupsInFile() throws IOException {
        Path outputPath = Path.of("groupedStrings.txt").toAbsolutePath();
        System.out.println("Выходной файл находится по пути:");
        System.out.println(outputPath);
        File file = new File(outputPath.toString());
        file.createNewFile();
        PrintWriter printWriter = new PrintWriter(file);
        int groupId = 1;
        printWriter.println("Количество групп:" + this.groupMap.size() + "\n");
        for(Integer key: this.groupMap.keySet()){
            printWriter.println("Группа номер " + groupId + ":");
            for(String string: this.groupMap.get(key)){
                printWriter.println(string);
            }
            groupId++;
            printWriter.println();
        }
        printWriter.close();
    }
}
