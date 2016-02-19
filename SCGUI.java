// File: SCGUI.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Create GUI, select data files, instantiate and populate classes

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
    final JButton select_button, search_button, show_button, clear_button;
    final JComboBox <String> items_combo;
    final JTextField search_field;
    private JTextArea text_area;
    
    //Creates GUI and calls addDataFile method to choose data file
    SCGUI(){
        theCave= new Cave();
        
        setTitle ("Sorcerer's Cave Program");
        setSize (700, 200);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible (true);
	
        
        select_button= new JButton("Select");
        show_button= new JButton("Show");
        search_button= new JButton("Search");
        clear_button= new JButton("Clear Data");

	text_area= new JTextArea();
        search_field= new JTextField(10);
        items_combo= new JComboBox<>();
        items_combo.addItem("Name");
        items_combo.addItem("Type");
        items_combo.addItem("Index");
        
	JLabel search_label= new JLabel ("Search");
        JPanel panel= new JPanel();
        JScrollPane scroll_pane= new JScrollPane(text_area);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll_pane, BorderLayout.CENTER);
        
        panel.add(clear_button);
        panel.add(select_button);
        panel.add(show_button);
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
        Scanner scan= new Scanner(file);
        scan.useDelimiter(System.getProperty("line.separator"));
            
        while(scan.hasNext()){
            
            String element= scan.next();
            
            if (!"".equals(element)){
               
                char element_type= element.charAt(0);

                if (element_type== 'p'){
                    addParty(element);
                }
                else if(element_type== 'c'){
                    addCreature(element);
                }
                else if(element_type== 't'){
                    addTreasure(element);
                }
                else if(element_type== 'a'){
                    addArtifact(element);
                }
            }
        }     
    }
    
    //Creates party object
    final void addParty(String element){
        Scanner scan= new Scanner(element);
        scan.useDelimiter(":");
        
        Party party_add= new Party();
        
        scan.next();
        party_add.index= Integer.parseInt(scan.next().trim());
        party_add.name= scan.next().trim();
        
        theCave.parties.add(party_add);       
    }
    
    //Creates creature object
    final void addCreature(String element){
        Scanner scan= new Scanner(element);
        scan.useDelimiter(":");
        
        Creature creature_add= new Creature();
        
        scan.next();
        creature_add.index= Integer.parseInt(scan.next().trim());
        creature_add.type= scan.next().trim();
        creature_add.name= scan.next().trim();
        creature_add.party= Integer.parseInt(scan.next().trim());
        creature_add.empathy= Integer.parseInt(scan.next().trim());
        creature_add.fear= Integer.parseInt(scan.next().trim());
        creature_add.carrying= Double.parseDouble(scan.next().trim());
        
        theCave.parties.stream().filter((party) -> (party.index == creature_add.party)).forEach((party) -> {
            party.creatures.add(creature_add);
        });
    }
    
    //Creates treasure object
    final void addTreasure(String element){
        Scanner scan= new Scanner(element);
        scan.useDelimiter(":");
        
        Treasure treasure_add= new Treasure();
        
        scan.next();
        treasure_add.index= Integer.parseInt(scan.next().trim());
        treasure_add.type= scan.next().trim();
        treasure_add.creature= Integer.parseInt(scan.next().trim());
        treasure_add.weight= Double.parseDouble(scan.next().trim());
        treasure_add.value= Double.parseDouble(scan.next().trim());
        
        
        theCave.parties.stream().forEach((party) -> {
            party.creatures.stream().filter((creature) -> (creature.index == treasure_add.creature)).forEach((creature) -> {
                creature.treasures.add(treasure_add);
            });
        });    
    }
    
    //Creates artifact object
    final void addArtifact(String element){
        Scanner scan= new Scanner(element);
        scan.useDelimiter(":");
        
        Artifact artifact_add= new Artifact();
        
        scan.next();
        artifact_add.index= Integer.parseInt(scan.next().trim());
        artifact_add.type= scan.next().trim();
        artifact_add.creature= Integer.parseInt(scan.next().trim());
        artifact_add.name= scan.next().trim();
        
        theCave.parties.stream().forEach((party) -> {
            party.creatures.stream().filter((creature) -> (creature.index == artifact_add.creature)).forEach((creature) -> {
                creature.artifacts.add(artifact_add);
            });
        });    
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
}
