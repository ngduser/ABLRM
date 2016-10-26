package archbattlelord;
// File: Cave.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Cave class holds Party list and formats output 
// while holding attribute fields
import java.util.ArrayList;

//Cave is top level class for game assets
public class Cave {
    ArrayList<Party> parties;
    private String data_output;

    public Cave() {
        this.parties = new ArrayList<>();
        data_output= "Choose a data file first";
    }
    
    //Starts depth first string building traversal
    private String printParties(){
        
        data_output="";
        parties.stream().forEach((party) -> {
            data_output= data_output+ "+++++PARTY+++++\n\n           "+party+ "\n"
                    + party.printCreatures()+ "\n";
            
        });
        
        return data_output;
    }
    
    //toString for cave outputs entire structure.
    @Override
    public String toString(){
        return printParties();
    }
    
    
}
