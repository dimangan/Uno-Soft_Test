package org.example;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Grouper {
    private final GroupLinkMap groupLinkMap;
    private final GroupMap groupMap;
    private Integer groupsCount;
    public Grouper(GroupLinkMap groupLinkMap, GroupMap groupMap){
        this.groupLinkMap = groupLinkMap;
        this.groupMap = groupMap;
        this.groupsCount = 0;
    }
    public Set<Integer> getPossibleGroups(List<String> inputSplitString){
        Set<Integer> possibleGroups = new TreeSet<>();
        for(int i = 0; i < inputSplitString.size(); i++){
            Key key = new Key(inputSplitString.get(i), i);
            GroupLink group = groupLinkMap.getGroupLinkMap().get(key);
            if(group != null){
                possibleGroups.add(group.getGroup());
            }
        }
        return possibleGroups;
    }
    public void groupStrings(Set<Integer> possibleGroups, String line, List<String> stringSplit){
        switch(possibleGroups.size()){
            case 0:
                this.groupMap.add(++this.groupsCount, line);
                for(int i = 0; i < stringSplit.size(); i++){
                    String note = stringSplit.get(i);
                    if(!note.equals("\"\"")){
                        groupLinkMap.add(new Key(stringSplit.get(i), i), groupsCount);
                    }
                }
                break;
            case 1:
                Integer groupToAdd = possibleGroups.iterator().next();
                groupMap.add(groupToAdd, line);
                for(int i = 0; i < stringSplit.size(); i++){
                    String note = stringSplit.get(i);
                    if(!note.equals("\"\"")) {
                        groupLinkMap.add(new Key(stringSplit.get(i), i), groupToAdd);
                    }
                }
                break;
            default:
                groupToAdd = possibleGroups.iterator().next();
                groupMap.add(groupToAdd, line);
                for(int i = 0; i < stringSplit.size(); i++){
                    String note = stringSplit.get(i);
                    if(!note.equals("\"\"")) {
                        groupLinkMap.add(new Key(stringSplit.get(i), i), groupToAdd);
                    }
                }
                possibleGroups.stream().skip(1).forEach(x -> {
                    groupLinkMap.merge(groupToAdd, x);
                    groupMap.merge(groupToAdd, x);
                });
                break;
        }
    }


}
