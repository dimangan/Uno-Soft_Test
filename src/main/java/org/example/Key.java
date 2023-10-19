package org.example;

public class Key {
    private final String noteKey;
    private final int indexKey;
    private int hash = 0;
    public Key(String noteKey, int indexKey) {
        this.noteKey = noteKey;
        this.indexKey = indexKey;
    }
    @Override
    public int hashCode() {
        int h = hash;
        String value = noteKey.concat(Integer.toString(indexKey));
        if(h == 0 && value.length() > 0){
            char[] val = value.toCharArray();
            for (int i = 0; i < value.length(); i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Key key){
            return noteKey.equals(key.noteKey) && indexKey == key.indexKey;
        }
        return false;
    }
}
