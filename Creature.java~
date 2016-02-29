// File: Creature.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Creature class holds Artifact and Treasure lists
// and formats output while holding attribute fields

import java.util.ArrayList;

//Creature holds artifact and treasure lists
public class Creature {
    ArrayList <Artifact> artifacts;
    ArrayList <Treasure> treasures;
    
    int index, party, empathy, fear;
    String type, name;
    private String data_output;
    double carrying;
    
    //Default empty values
    Creature(){
        artifacts= new ArrayList<>();
        treasures= new ArrayList<>();
        
        index=0;
        empathy= 0;
        fear=0;
        carrying=0;
        
        type=null;
        name=null;
    }
    
    public String printItems(){
        data_output= "";
        
        data_output= data_output+ "\n                 -----ARTIFACTS-----\n\n";
        artifacts.stream().forEach((artifact) -> {
            data_output= data_output+ "                       "+ artifact+ "\n";
        });
        
       
        data_output= data_output+ "\n                 -----TREASURES-----\n\n";
        treasures.stream().forEach((treasure) -> {
            data_output= data_output+"                       "+ treasure+ "\n";
        });
        
        data_output= data_output+ "\n";
        return data_output;
    }
    
    public String printFields(){
        String fields= "NAME- "+ name+ "\nINDEX- "+ index+ "\nPARTY- "+ party+ "\nEMPATHY- "
                +empathy+ "\nFEAR- "+ fear+ "\nCARRYING- "+ carrying;
        return fields;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
