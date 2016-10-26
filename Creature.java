package archbattlelord;
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
    
    public synchronized void setJob(Jobs job){
       /*
        
        buildGUI();
        
        System.out.println(job.creature+ " starting job- "+ job.name);
        
        long sleep= (long) (job.time* 1000);
        
            try {
                progress_bar.setValue(0);
                Thread.sleep(sleep/4);
                progress_bar.setValue(25);
                Thread.sleep(sleep/4);
                progress_bar.setValue(50);
                Thread.sleep(sleep/4);
                progress_bar.setValue(75);
                Thread.sleep(sleep/4);
               
            } catch (InterruptedException ex) {
                Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
            }

	    progress_bar.setValue(99);
            System.out.println(job.creature+ " finished job- "+ job.name);
                frame.dispose();
        */
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

 /*   private void buildGUI(){
        progress_bar= new JProgressBar();
    
        panel= new JPanel(); 
        panel.add(progress_bar);
        
        
        int max= (100);
        progress_bar.setMinimum(0);
        progress_bar.setMaximum(max);
        
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame. setSize(200, 100);   
        frame.setVisible(true);
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
*/
}
