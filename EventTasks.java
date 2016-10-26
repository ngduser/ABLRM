package archbattlelord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import javafx.stage.FileChooser;

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
                    case 'p':   { System.out.println("ONE");
                        Party party_add= new Party();
                        element_scan.next();
                        party_add.index= Integer.parseInt(element_scan.next().trim());
                        party_add.name= element_scan.next().trim();
                        parent_map.put(party_add.index, party_add);
                        theCave.parties.add(party_add);
                        break;
                    }
                        
                    case 'c': {
                        Creature creature_add= new Creature();
                        element_scan.next();
                        creature_add.index= Integer.parseInt(element_scan.next().trim());
                        creature_add.type= element_scan.next().trim();
                        creature_add.name= element_scan.next().trim();
                        creature_add.party= Integer.parseInt(element_scan.next().trim());
                        creature_add.empathy= Integer.parseInt(element_scan.next().trim());
                        creature_add.fear= Integer.parseInt(element_scan.next().trim());
                        creature_add.carrying= Double.parseDouble(element_scan.next().trim());
                        Party party= (Party)parent_map.get(creature_add.party);
                        party.creatures.add(creature_add);
                        parent_map.put(creature_add.index, creature_add);
                        break;
                    }
                        
                    case 't': { 
                        Treasure treasure_add= new Treasure();
                        element_scan.next();
                        treasure_add.index= Integer.parseInt(element_scan.next().trim());
                        treasure_add.type= element_scan.next().trim();
                        treasure_add.creature= Integer.parseInt(element_scan.next().trim());
                        treasure_add.weight= Double.parseDouble(element_scan.next().trim());
                        treasure_add.value= Double.parseDouble(element_scan.next().trim());
                        Creature creature= (Creature)parent_map.get(treasure_add.creature);
                        creature.treasures.add(treasure_add);
                        parent_map.put(treasure_add.index, treasure_add);
                        break;
                    }

                    case 'a': {
                        Artifact artifact_add= new Artifact();
                        element_scan.next();
                        artifact_add.index= Integer.parseInt(element_scan.next().trim());
                        artifact_add.type= element_scan.next().trim();
                        artifact_add.creature= Integer.parseInt(element_scan.next().trim());
                        artifact_add.name= element_scan.next().trim();
                        Creature creature= (Creature)parent_map.get(artifact_add.creature);
                        creature.artifacts.add(artifact_add);
                        parent_map.put(artifact_add.index, artifact_add);
                        break;
                    }
                    
                    case 'j': {
                        Jobs job_add= new Jobs();
                        element_scan.next();
                        job_add.index= Integer.parseInt(element_scan.next().trim());
                        job_add.name= element_scan.next().trim();
                        job_add.creature_index= Integer.parseInt(element_scan.next().trim());
                        job_add.time= Double.parseDouble(element_scan.next().trim());
                        Creature creature= (Creature)parent_map.get(job_add.creature_index);
                        job_add.creature= creature;

//		    Thread new_thread= new Thread(job_add);
//                    new_thread.start();
                        break;
                    }
                    
                    default:
                        break;
                }
            }
        }     
    }
}
