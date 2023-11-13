package org.example;

import java.util.*;

public class NotePositionMap {
    private final Map<Key, GroupId> notePositionMap;
    private final Map<GroupId, Set<Key>> reverseNotePositionMap;
    public NotePositionMap(){
        this.notePositionMap = new HashMap<>();
        this.reverseNotePositionMap = new HashMap<>();
    }

    public Map<Key, GroupId> getNotePositionMap() {
        return this.notePositionMap;
    }
    public void add(Key key, GroupId groupId){
        if(this.notePositionMap.get(key) == null){
            this.notePositionMap.put(key, groupId);
            if(this.reverseNotePositionMap.get(groupId) == null){
                Set<Key> newKeySet = new HashSet<>();
                newKeySet.add(key);
                this.reverseNotePositionMap.put(groupId, newKeySet);
            }
            else{
                this.reverseNotePositionMap.get(groupId).add(key);
            }
        }
    }
    public void merge(GroupId firstGroup, GroupId secondGroup){
        Set<Key> keySet = this.reverseNotePositionMap.get(secondGroup);
        for(Key key: keySet){
            this.notePositionMap.remove(key);
            this.notePositionMap.put(key, firstGroup);
            this.reverseNotePositionMap.get(firstGroup).add(key);
        }
        this.reverseNotePositionMap.remove(secondGroup);
    }
}
