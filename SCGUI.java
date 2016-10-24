// File: SCGUI.java
// Date: Jan 25, 2016
// Author: Nathan Denig
// Purpose: Create GUI, select data files, instantiate and populate classes


import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
 
public class UI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception{
        
        GridPane grid = new GridPane();
        grid.setMinHeight(400);
        grid.setMinWidth(600);
        
        Button select_button = new Button("Select Asset File");
        Button search_button = new Button("Search for Asset");
        Button show_button = new Button("Show Selection");
        Button clear_button = new Button("Clear Selection");
        Button sort_button = new Button("Sort");
        
        GridPane.setConstraints(select_button, 1, 1);
        GridPane.setConstraints(search_button, 3, 2);
        GridPane.setConstraints(show_button, 2, 1);
        GridPane.setConstraints(clear_button, 3, 1);
        
     //   grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(10);
        
        Label tableArea = new Label("Tasks");
        tableArea.setMinWidth(600);
        
        TextField search_field = new TextField();
        search_field.setPromptText("Asset Name");
        
        GridPane.setConstraints(tableArea, 1, 1, 3, 1);
        GridPane.setConstraints(search_field, 2, 2);
        
        ComboBox items_combo = new ComboBox();
        items_combo.setPromptText("Select Asset Type");
        GridPane.setConstraints(items_combo, 1, 2);
        items_combo.getItems().addAll("Name", "Type", "Index");
        
        ComboBox items_sort = new ComboBox();
        items_sort.setPromptText("Select Sort Type");
        GridPane.setConstraints(items_sort, 1, 3);
        items_sort.getItems().addAll("Empathy", "Fear", "Weight", "Carrying", "Value");
        
        grid.getChildren().addAll(tableArea, search_field, items_combo, items_sort, 
                select_button, search_button, show_button, clear_button);
        
        Scene scene = new Scene(grid, 600, 400);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("ArchBattleLord");
        stage.setAlwaysOnTop(true);
        stage.setResizable(true);
        stage.show();
        
        search_button.setOnAction((ActionEvent event) -> {
            String item_type= items_combo.getValue().toString();
            String item_name= search_field.getText();
            
            System.out.println(item_type);
               searchGame(item_type, item_name);
        });
        
        sort_button.setOnAction((ActionEvent event) -> {
            String field= items_sort.getValue().toString();
            compareFields(field); 
        });
 
        select_button.setOnAction((ActionEvent event) -> {
            try {
                addDataFile();
            } catch (InterruptedException ex) {
                Logger.getLogger(ArchBattleLord.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        clear_button.setOnAction((ActionEvent event) -> {
            theCave= new Cave();
            showCave(theCave.toString());
        });
        
        show_button.setOnAction((ActionEvent e) -> {
            showCave(theCave.toString());
        }); 


    }
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
		else if(element_type== 'j'){
                    Jobs job_add= new Jobs();
        
                    element_scan.next();
                    job_add.index= Integer.parseInt(element_scan.next().trim());
                    job_add.name= element_scan.next().trim();
                    job_add.creature_index= Integer.parseInt(element_scan.next().trim());
                    job_add.time= Double.parseDouble(element_scan.next().trim());
                    
                    Creature creature= (Creature)parent_map.get(job_add.creature_index);
                    job_add.creature= creature;

		    Thread new_thread= new Thread(job_add);
                    new_thread.start();
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
