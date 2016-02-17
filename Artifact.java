// File: Artifact.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Artifact class holds attribute fields


//Create artifact game token
public class Artifact {
    String type, name;
    int index, creature;
    
    Artifact(){
        type= null;
        name= null;
        index=0;
        creature= 0;
    }
    
    //Build String with fields	
    public String printFields(){
        String fields= "NAME- "+ name+ "\nINDEX- "+ index+ "\nTYPE- "+ type+ 
                "\nCREATURE- "+ creature;
        return fields;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
