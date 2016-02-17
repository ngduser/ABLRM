
// File: Party.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Party class holds Creature list and formats 
// output while holding attribute fields


import java.util.ArrayList;


//Party is second level holding Creature
public class Party {
    int index;
    String name;
    private String data_output;
    
    ArrayList<Creature> creatures;
    
    Party(){
        creatures= new ArrayList<>();
        index= 0;
        name= null;
    }
    
    public String printCreatures(){
        data_output= "";
        creatures.stream().forEach((creature) -> {
            data_output= data_output+ "\n          *****CREATURE*****\n\n                 "+
                    creature+ "\n"+ creature.printItems()+ "\n";
        });
        return data_output;
    }
    
    //Displays all attribute fields
    public String printFields(){
        String fields= "NAME- "+ name+ "\nINDEX- "+ index;
        return fields;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
}
