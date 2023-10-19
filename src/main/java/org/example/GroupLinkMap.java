package org.example;


import java.util.HashMap;
import java.util.Map;

public class GroupLinkMap {
    private final Map<Key, GroupLink> groupLinkMap;
    private final Map<Integer, GroupLink> groupLink;
    public GroupLinkMap() {
        this.groupLinkMap = new HashMap<>();
        this.groupLink = new HashMap<>();
    }
    public Map<Key, GroupLink> getGroupLinkMap() {
        return groupLinkMap;
    }
    public void add(Key key, Integer group){
        if(this.groupLinkMap.get(key) == null){
            if(this.groupLink.get(group) == null){
                this.groupLink.put(group, new GroupLink(group));
            }
            this.groupLinkMap.put(key, groupLink.get(group));
        }

    }
    public void merge(Integer firstGroup, Integer secondGroup){
        groupLink.get(secondGroup).setGroup(firstGroup);
    }

}
