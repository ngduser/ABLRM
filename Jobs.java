// File: Jobs.java
// Date: Feb 29, 2016
// Author: Nathan Denig
// Purpose: Create Job Threads for creature setJobs method

public class Jobs extends Thread{
    
    int index, creature_index;
    String name;
    double time;
    State status;
    Creature creature;
    
    @Override
    public void run(){
        creature.setJob(this);
        
    }
    
}
