package org.example;

import java.util.*;

public class Grouper {
    private final NotePositionMap notePositionMap;
    private final GroupMap groupMap;
    private Integer groupsCount;
    public Grouper(NotePositionMap notePositionMap, GroupMap groupMap){
        this.notePositionMap = notePositionMap;
        this.groupMap = groupMap;
        this.groupsCount = 0;
    }
    public Set<GroupId> getPossibleGroups(List<String> inputSplitString){
        Set<GroupId> possibleGroups = new HashSet<>();
        for(int i = 0; i < inputSplitString.size(); i++){
            Key key = new Key(inputSplitString.get(i), i);
            GroupId group = this.notePositionMap.getNotePositionMap().get(key);
            if(group != null){
                possibleGroups.add(group);
            }
        }
        return possibleGroups;
    }
    public void groupStrings(Set<GroupId> possibleGroups, String line, List<String> stringSplit){
        switch(possibleGroups.size()){
            case 0:
                this.groupsCount++;
                GroupId newGroup = new GroupId(this.groupsCount);
                this.groupMap.add(newGroup, line);
                for(int i = 0; i < stringSplit.size(); i++){
                    String note = stringSplit.get(i);
                    if(!note.equals("\"\"") && !note.equals("?")){
                        notePositionMap.add(new Key(stringSplit.get(i), i), newGroup);
                    }
                }
                break;
            case 1:
                GroupId groupToAdd = possibleGroups.iterator().next();
                groupMap.add(groupToAdd, line);
                for(int i = 0; i < stringSplit.size(); i++){
                    String note = stringSplit.get(i);
                    if(!note.equals("\"\"") && !note.equals("?")) {
                        notePositionMap.add(new Key(stringSplit.get(i), i), groupToAdd);
                    }
                }
                break;
            default:
                groupToAdd = possibleGroups.iterator().next();
                groupMap.add(groupToAdd, line);
                for(int i = 0; i < stringSplit.size(); i++){
                    String note = stringSplit.get(i);
                    if(!note.equals("\"\"") && !note.equals("?")) {
                        notePositionMap.add(new Key(stringSplit.get(i), i), groupToAdd);
                    }
                }
                possibleGroups.stream().skip(1).forEach(x -> {
                    groupMap.merge(groupToAdd, x);
                    notePositionMap.merge(groupToAdd, x);
                });
                break;
        }
    }
}
