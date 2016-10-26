package archbattlelord;
// File: Treasure.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Treasure class holds attribute fields


//Game asset holds attributes
public class Treasure {
    String type; 
    int index, creature;
    double weight, value;
    
    Treasure(){
        type= null;
        index=0;
        creature=0;
        weight=0;
        value=0;
    }
    //Displays attributes
    public String printFields(){
        String fields= "INDEX- "+ index+ "\nTYPE- "+ type+ "\nWEIGHT- "+ weight+
                "\nCREATURE- "+ creature+ "\nValue- "+ value;
        return fields;
    }
    
    @Override
    public String toString(){
        return type;
    }
            
     
    
}
