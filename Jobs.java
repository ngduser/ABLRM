

public class Jobs extends Thread {
    
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
