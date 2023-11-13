package org.example;

public class GroupId {
    private final int group;
    public GroupId(int group){
        this.group = group;
    }
    @Override
    public int hashCode() {
        return group;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GroupId groupId){
            return this.group == groupId.group;
        }
        return false;
    }
}
