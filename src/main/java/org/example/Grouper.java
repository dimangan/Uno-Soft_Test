package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


public class Grouper {
    private final List<Map<String, Integer>> listOfNotes;
    private final Map<Integer, Set<Integer>> lineGroupId;
    private final List<List<String>> allSplitLines;

    public Grouper(List<List<String>> allSplitLines){
        this.listOfNotes = new ArrayList<>();
        this.lineGroupId = new HashMap<>();
        this.allSplitLines = allSplitLines;
    }
    public void makeGroups(){
        for(int lineNum = 0; lineNum < this.allSplitLines.size(); lineNum++){
            List<String> splitLine = this.allSplitLines.get(lineNum);
            Integer currentGroupId = this.lineGroupId.size();
            Set<Integer> possibleGroups = new HashSet<>() {
                @Override
                public boolean add(Integer integer) {
                    if (integer != null) {
                        return super.add(integer);
                    }
                    else return false;
                }
            };
            for(int i = 0; i < splitLine.size(); i++){
                String note = splitLine.get(i);
                if(this.listOfNotes.size() <= i){
                    Map<String, Integer> newMap = new HashMap<>();
                    newMap.put(note, currentGroupId);
                    this.listOfNotes.add(newMap);
                }
                else if(!note.equals("")){
                    possibleGroups.add(this.listOfNotes.get(i).get(note));
                }
            }
            switch (possibleGroups.size()) {
                case 0 -> addLine(splitLine, currentGroupId);
                case 1 -> {
                    currentGroupId = possibleGroups.iterator().next();
                    addLine(splitLine, currentGroupId);
                }
                default -> {
                    currentGroupId = Collections.min(possibleGroups);
                    final Integer finalCurrentGroupId = currentGroupId;
                    addLine(splitLine, currentGroupId);
                    possibleGroups.remove(currentGroupId);
                    possibleGroups.forEach(groupId -> {
                        Set<Integer> newLineSet = this.lineGroupId.get(groupId);
                        newLineSet.forEach(lineId -> {
                            addLine(this.allSplitLines.get(lineId), finalCurrentGroupId);
                            this.lineGroupId.get(finalCurrentGroupId).add(lineId);
                        });
                        this.lineGroupId.get(groupId).clear();
                    });
                }
            }
            if(this.lineGroupId.get(currentGroupId) == null){
                Set<Integer> newLineIdSet = new HashSet<>();
                newLineIdSet.add(lineNum);
                this.lineGroupId.put(currentGroupId,newLineIdSet);
            }
            else{
                this.lineGroupId.get(currentGroupId).add(lineNum);
            }

        }
    }
    private void addLine(List<String> splitLine, Integer groupId){
        for(int i = 0; i < splitLine.size(); i++){
            String note = splitLine.get(i);
            this.listOfNotes.get(i).put(note, groupId);
        }
    }
    public void printGroupsInFile() throws IOException {
        Path outputPath = Path.of("groupedStrings.txt").toAbsolutePath();
        File file = new File(outputPath.toString());
        if(file.createNewFile()){
            System.out.println("Created new output file: " + outputPath);
        }
        PrintWriter printWriter = new PrintWriter(file);
        List<Set<Integer>> sortedGroupMap = this.lineGroupId.values().stream().filter(lineId -> lineId.size() > 1)
                .sorted((lineId1, lineId2) -> lineId2.size() - lineId1.size())
                .toList();
        printWriter.println("Количество групп:" + sortedGroupMap.size() + "\n");
        System.out.println("Amount of groups: " + sortedGroupMap.size());
        for(int i = 0; i < sortedGroupMap.size(); i++){
            printWriter.println("Группа номер " + (i + 1) + ":");
            for(Integer lineId: sortedGroupMap.get(i)){
                printWriter.println(this.allSplitLines.get(lineId).stream().map(note -> "\"" + note + "\"")
                        .collect(Collectors.joining(";")));
            }
            printWriter.println();
        }
        System.out.println("Выходной файл находится по пути:");
        System.out.println(outputPath);
        printWriter.close();
    }
}
