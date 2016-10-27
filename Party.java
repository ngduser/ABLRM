package archbattlelord;
// File: Party.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Party class holds Creature list and formats 
// output while holding attribute fields


public class Party extends Asset {
    
    public void addChild(Creature child) {
        children.add(child);
    }
    
}
