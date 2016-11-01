package archbattlelord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EventTasks {
    
    static Cave theCave = new Cave();
    
    static final void readFile(File file) throws FileNotFoundException{
        
        
        HashMap <Integer, Object> parent_map;
        parent_map = new HashMap<>();
        
        BufferedReader in;
        FileReader file_reader = new FileReader(file);
        in = new BufferedReader(file_reader);
        Scanner scan = new Scanner(in);
        
        scan.useDelimiter(System.getProperty("line.separator"));
            
        while(scan.hasNext()){
            String element= scan.next();
            Scanner element_scan= new Scanner(element);
            element_scan.useDelimiter(":");
            
            if (!"".equals(element)){
               
                char element_type= element.charAt(0);

                switch (element_type) {
                    case 'p': {
                        Party party = new Party();
                        
                        try {
                            element_scan.next();

                            party.addAttributes("index", String.valueOf(element_scan.next().trim()));
                            party.addAttributes("name", element_scan.next().trim());
                         }catch(NoSuchElementException ex){
                            System.out.println("Empty entry for Party!");
                        }    
                        
                        theCave.addChild(party);
                        break;
                    }
                        
                    case 'c': {
                        Creature creature = new Creature();
                 
                        try {
                            element_scan.next();

                            creature.addAttributes("index", String.valueOf(element_scan.next().trim()));
                            creature.addAttributes("type", element_scan.next().trim());
                            creature.addAttributes("name", element_scan.next().trim());
                            String parent = String.valueOf(element_scan.next().trim());
                            creature.addAttributes("parent", parent);
                            creature.addAttributes("empathy", String.valueOf(element_scan.next().trim()));
                            creature.addAttributes("fear", String.valueOf(element_scan.next().trim()));
                            creature.addAttributes("carrying", String.valueOf(element_scan.next().trim()));
                        
                        }catch(NoSuchElementException ex){
                            System.out.println("Empty entry for Creature!");
                        }        
                        
                        Party party = (Party) theCave.search(1, creature.attributes.get("parent"));
                        if (party != null) {}                        
                        else {
                            party = (Party)theCave.children.get(0);
                        }
                        
                        party.addChild(creature);
                        break;
                    }
                           
                    case 't': { 
                        Treasure treasure = new Treasure();
                    
                        try {    
                            element_scan.next();

                            treasure.addAttributes("index", String.valueOf(element_scan.next().trim()));
                            treasure.addAttributes("type", element_scan.next().trim());
                            String parent = String.valueOf(element_scan.next().trim());
                            treasure.addAttributes("parent", parent);
                            treasure.addAttributes("weight", String.valueOf(element_scan.next().trim()));
                            treasure.addAttributes("value", String.valueOf(element_scan.next().trim()));
                        
                        }catch(NoSuchElementException ex){
                            System.out.println("Empty entry for Treasure!");
                        }
                        
                        Creature creature = (Creature) theCave.search(2, treasure.attributes.get("parent"));
                        
                        if (creature != null) {}                        
                        else {
                            Party party = (Party) theCave.children.get(0);
                            creature = (Creature) party.children.get(0);
                        }
                        
                        System.out.println(creature);
                        creature.addChild(treasure);
                        break;
                    }

                    case 'a': {
                        Artifact artifact = new Artifact();
                       
                        try { 
                            element_scan.next();
                            artifact.addAttributes("index", String.valueOf(element_scan.next().trim()));
                            artifact.addAttributes("type", element_scan.next().trim());
                            String parent = String.valueOf(element_scan.next().trim());
                            artifact.addAttributes("parent", parent);
                            artifact.addAttributes("name", element_scan.next().trim());
                        
                        }catch(NoSuchElementException ex){
                            System.out.println("Empty entry for Artifact!");
                        }
                        
                        Creature creature = (Creature) theCave.search(2, artifact.attributes.get("parent"));
                        
                        if (creature != null) {}                        
                        else {
                            Party party = (Party) theCave.children.get(0);
                            creature = (Creature) party.children.get(1);
                        }
                        creature.addChild(artifact);
                        break;
                    }
                    
                    case 'j': {
                        Jobs job_add= new Jobs();
                        
                        try {
                            element_scan.next();
                            job_add.index= Integer.parseInt(element_scan.next().trim());
                            job_add.name= element_scan.next().trim();
                            job_add.creature_index= Integer.parseInt(element_scan.next().trim());
                            job_add.time= Double.parseDouble(element_scan.next().trim());
                            Creature creature= (Creature)parent_map.get(job_add.creature_index);
                            job_add.creature= creature;
                        
                        }catch(NoSuchElementException ex){
                            System.out.println("Empty entry for party_add!");
                        }

//		    Thread new_thread= new Thread(job_add);
//                    new_thread.start();
                        break;
                    }
                    
                    default:
                        break;
                }
            }
        }
        theCave.toString();
    }
}
