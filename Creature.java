package archbattlelord;
// File: Creature.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Creature class holds Artifact and Treasure lists
// and formats output while holding attribute fields

//Creature holds artifact and treasure lists

public class Creature extends Asset {
    
    public void addChild(Artifact child) {
        children.add(child);
    }
    
    public void addChild(Treasure child) {
        children.add(child);
    }
    
}