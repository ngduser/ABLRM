
package archbattlelord;
import java.lang.Thread.State;

// File: Jobs.java
// Date: Feb 29, 2016
// Author: Nathan Denig
// Purpose: Create Job Runnable for creature setJobs threads

public class Jobs implements Runnable{
    
    int index, creature_index;
    String name;
    double time;
    State status;
    Creature creature;
    
    @Override
    public void run(){
      //  creature.setJob(this);
        
    }
    
}
