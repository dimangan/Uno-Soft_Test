package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

public class GroupMap {
    private final Map<GroupId, Set<String>> groupMap;
    public GroupMap() {
        this.groupMap = new HashMap<>();
    }
    public Map<GroupId, Set<String>> getGroupMap() {
        return this.groupMap;
    }
    public void add(GroupId groupId, String string){
        if(this.groupMap.get(groupId) != null){
            this.groupMap.get(groupId).add(string);
        }
        else{
            Set<String> newStringSet = new HashSet<>();
            newStringSet.add(string);
            this.groupMap.put(groupId, newStringSet);
        }
    }
    public void merge(GroupId firstGroup, GroupId secondGroup){
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
        for(GroupId group: this.groupMap.keySet()){
            printWriter.println("Группа номер " + groupId + ":");
            for(String string: this.groupMap.get(group)){
                printWriter.println(string);
            }
            groupId++;
            printWriter.println();
        }
        printWriter.close();
    }
}
