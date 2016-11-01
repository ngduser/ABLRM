package archbattlelord;
// File: Cave.java
// Date: Jan 25, 2016

import java.util.Stack;

// Author: Nathan Denig
// Purpose: Cave class holds Party list and formats output 
// while holding attribute fields

public class Cave extends Asset {
    public Party party_orphan;
    public Creature creature_orphan;
    public Treasure treasure_orphan;
    public Artifact artifact_orphan;
    
    public Cave() {
        party_orphan = new Party();
        children.add(party_orphan);
        
        creature_orphan = new Creature();
        party_orphan.children.add(creature_orphan);
        
        treasure_orphan = new Treasure();
        creature_orphan.children.add(treasure_orphan);
        
        artifact_orphan = new Artifact();
        creature_orphan.children.add(artifact_orphan);
    }
    
    public void addChild(Party child) {
        children.add(child);
    }
}
