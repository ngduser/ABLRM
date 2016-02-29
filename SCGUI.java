// File: SCGUI.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Create GUI, select data files, instantiate and populate classes


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SCGUI extends JFrame{
    
    private Cave theCave;
    final JButton select_button, search_button, show_button, clear_button, sort_button;
    final JComboBox <String> items_combo, items_sort;
    final JTextField search_field;
    JTextArea text_area;
    
    static final long serialVersionUID= 89733127L;
	
    //Creates GUI and calls addDataFile method to choose data file
    SCGUI(){
        theCave= new Cave();
        
        setTitle ("Sorcerer's Cave Program");
        setSize (1000, 200);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible (true);

	text_area= new JTextArea();
	select_button= new JButton("Select Data File");
        show_button= new JButton("Show");
        search_button= new JButton("Search");
        clear_button= new JButton("Clear Data");
        sort_button= new JButton("Sort");
        
        search_field= new JTextField(10);
        items_combo= new JComboBox<>();
        items_combo.addItem("Name");
        items_combo.addItem("Type");
        items_combo.addItem("Index");
        
        items_sort= new JComboBox<>();
        items_sort.addItem("Creature Empathy");
        items_sort.addItem("Creature Fear");
        items_sort.addItem("Creature Carrying");
        items_sort.addItem("Treasure Weight");
        items_sort.addItem("Treasure Value");
        
        JLabel search_label= new JLabel ("Search");
        JPanel panel= new JPanel();
        JScrollPane scroll_pane= new JScrollPane(text_area);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll_pane, BorderLayout.CENTER);
        
        panel.add(clear_button);
        panel.add(select_button);
        panel.add(show_button);
        panel.add(items_sort);
        panel.add(sort_button);
        panel.add(items_combo);
        panel.add(search_label);
        panel.add(search_field);
        panel.add(search_button);

        add(panel, BorderLayout.PAGE_START);
        addActions(); 
        validate();
        
        addDataFile();
    }
    
    //Add Actionlistner and call appropriate method
    final void addActions(){
        search_button.addActionListener((ActionEvent e) -> {
            String item_type= (String)items_combo.getSelectedItem();
            String item_name= (String)search_field.getText();
            searchGame(item_type, item_name);
        });

	sort_button.addActionListener((ActionEvent e) ->{
            String field= (String)items_sort.getSelectedItem();
            compareFields(field); 
        });
         
        select_button.addActionListener((ActionEvent e) -> {
            addDataFile();
        });
        
        clear_button.addActionListener((ActionEvent e) -> {
            theCave= new Cave();
            showCave(theCave.toString());
        });
        
        show_button.addActionListener((ActionEvent e) -> {
            showCave(theCave.toString());
        });  
    }
    
    //Attaches data file and sends it to readFile() for parsing
    private void addDataFile(){
        
        JFileChooser file_chooser= new JFileChooser(".");
        file_chooser.setDialogTitle("Open Asset File");
        if (file_chooser.showOpenDialog(this)== JFileChooser.APPROVE_OPTION){
            file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            File file= file_chooser.getSelectedFile();

            try {
                readFile(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SCGUI.class.getName()).log(Level.SEVERE, null, ex);   
            }
            showCave(theCave.toString());
        }
    }
    
    //Displays string called with in text area
    private void showCave(String data_output){
        text_area.setLineWrap(true);
        text_area.setText(data_output);
       
        setSize (700, 700);
        revalidate();
    }
    
    //Determines which (if any) method to send each line for object creation
    final void readFile(File file) throws FileNotFoundException{
        
        HashMap<Integer, Object> parent_map= new HashMap<>();
        
        Scanner scan= new Scanner(file);
        scan.useDelimiter(System.getProperty("line.separator"));
            
        while(scan.hasNext()){
            
            String element= scan.next();
            Scanner element_scan= new Scanner(element);
            element_scan.useDelimiter(":");
            
            if (!"".equals(element)){
               
                char element_type= element.charAt(0);

                if (element_type== 'p'){
                    Party party_add= new Party();
        
                    element_scan.next();
                    party_add.index= Integer.parseInt(element_scan.next().trim());
                    party_add.name= element_scan.next().trim();
                    
                    parent_map.put(party_add.index, party_add);
                    theCave.parties.add(party_add);   
                }
                else if(element_type== 'c'){
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
                    
                }
                else if(element_type== 't'){
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
                }
                else if(element_type== 'a'){
                    Artifact artifact_add= new Artifact();
        
                    element_scan.next();
                    artifact_add.index= Integer.parseInt(element_scan.next().trim());
                    artifact_add.type= element_scan.next().trim();
                    artifact_add.creature= Integer.parseInt(element_scan.next().trim());
                    artifact_add.name= element_scan.next().trim();

                    Creature creature= (Creature)parent_map.get(artifact_add.creature);
                    creature.artifacts.add(artifact_add);
                    
                    parent_map.put(artifact_add.index, artifact_add);     
                }
            }
        }     
    }
    
    //Searches based on parameters
    final void searchGame(String item_type, String item_name){
        
        String display="No asset with that search combination found!";
        
        if ("Name".equals(item_type)){
            System.out.println("NAME");
            for (Party party : theCave.parties) {
                if (party.name == null ? item_name == null : party.name.equals(item_name)){
                     display= party.printFields();
                     break;
                }
                for (Creature creature : party.creatures){
                    if (creature.name == null ? item_name == null : creature.name.equals(item_name)){
                        display= creature.printFields();
                        break;
                    }
                
                    for (Treasure treasure : creature.treasures){
                        if (treasure.type == null ? item_name == null : treasure.type.equals(item_name)){
                            display= treasure.printFields();
                            break;
                        }
                    
                    }
                }
            }
        }
        
        if ("Type".equals(item_type)){
            for (Party party : theCave.parties) {
                for (Creature creature : party.creatures){
                    if (creature.type == null ? item_name == null : creature.type.equals(item_name)){
                         display= creature.printFields();
                         break;
                    }
                    for (Treasure treasure : creature.treasures){
                        if (treasure.type == null ? item_name == null : treasure.type.equals(item_name)){
                            display= treasure.printFields();
                            break;
                        }
                    }
                    for (Artifact artifact : creature.artifacts){
                        if (artifact.type == null ? item_name == null : artifact.type.equals(item_name)){
                            display= artifact.printFields();
                            break;
                        }
                    }
                }
            }
        }
        
        if ("Index".equals(item_type)){
            int index= Integer.getInteger(item_type);
            for (Party party : theCave.parties) {
                if (party.index== index){
                     display= party.printFields();
                     break;
                }
                for (Creature creature : party.creatures){
             
                    if (creature.index== index){
                         display= creature.printFields();
                         break;
                    }
                    for (Treasure treasure : creature.treasures){
                        if (treasure.index== index){
                            display= treasure.printFields();
                            break;
                        }
                    }
                    for (Artifact artifact : creature.artifacts){
                        if (artifact.index== index){
                            display= artifact.printFields();
                            break;
                        }
                    }
                }
            }
        }
        showCave(display);
    }
    
    void compareFields(String field){
        String sort= "No applicable assets entered yet";
        
        if ("Creature Fear".equals(field)){
            sort= "--FEAR Ratings--\n";

            for (Party party : theCave.parties){
                sort= sort+ "Party- "+party+"\n";                    

                TreeMap<Integer, String> field_tree= new TreeMap<>();

                for (Creature creature : party.creatures){
                    field_tree.put(creature.fear, creature.toString());
                }

                for (int key: field_tree.keySet()){
                    sort= sort+ "   "+ field_tree.get(key);
                    sort= sort      + "- "+ key+ "\n";
                }
            }             
        }
        else if ("Creature Empathy".equals(field)){
            sort= "--Empathy Ratings--\n";

            for (Party party : theCave.parties){
                sort= sort+ "Party- "+party+"\n";                    

                TreeMap<Integer, String> field_tree= new TreeMap<>();

                for (Creature creature : party.creatures){
                    field_tree.put(creature.empathy, creature.toString());
                }

                for (int key: field_tree.keySet()){
                    sort= sort+ "   "+ field_tree.get(key);
                    sort= sort      + "- "+ key+ "\n";
                }
            }   
        }
        else if ("Creature Carrying".equals(field)){
            sort= "--Carrying Ratings--\n";

            for (Party party : theCave.parties){
                sort= sort+ "Party- "+party+"\n";                    

                TreeMap<Double, String> field_tree= new TreeMap<>();

                for (Creature creature : party.creatures){
                    field_tree.put(creature.carrying, creature.toString());
                }

                for (Double key: field_tree.keySet()){
                    sort= sort+ "   "+ field_tree.get(key);
                    sort= sort      + "- "+ key+ "\n";
                }
            }   
        }
        else if ("Treasure Weight".equals(field)){
            sort= "--Weight Ratings--\n";

            for (Party party : theCave.parties){
                for (Creature creature : party.creatures){
                    sort= sort+ "Creature- "+creature+"\n";                    
                    TreeMap<Double, String> field_tree= new TreeMap<>();
                    
                    for (Treasure treasure : creature.treasures){
                        field_tree.put(treasure.weight, treasure.toString());
                    }
                    
                    for (Double key: field_tree.keySet()){
                        sort= sort+ "   "+ field_tree.get(key);
                        sort= sort      + "- "+ key+ "\n";
                    }
                
                }
            }   
        }
        else if ("Treasure Value".equals(field)){
            sort= "--Value Ratings--\n";

            for (Party party : theCave.parties){
                for (Creature creature : party.creatures){
                    sort= sort+ "Creature- "+creature+"\n";                    
                    TreeMap<Double, String> field_tree= new TreeMap<>();
                    
                    for (Treasure treasure : creature.treasures){
                        field_tree.put(treasure.value, treasure.toString());
                    }
                    
                    for (Double key: field_tree.keySet()){
                        sort= sort+ "   "+ field_tree.get(key);
                        sort= sort      + "- "+ key+ "\n";
                    }
                
                }
            }   
        }
        
        showCave(sort);
        
    }
} 
       
    
               
                    
}
