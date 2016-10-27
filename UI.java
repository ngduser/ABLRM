package archbattlelord;

import java.io.File;
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
import javafx.stage.FileChooser;
 
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
        
        grid.setGridLinesVisible(true);
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
            //   searchGame(item_type, item_name);
        });
        
        sort_button.setOnAction((ActionEvent event) -> {
            String field= items_sort.getValue().toString();
          //  compareFields(field); 
        });
 
        select_button.setOnAction((ActionEvent event) -> {
            
             FileChooser file_chooser = new FileChooser();
                file_chooser.setTitle("Open Asset File");
                File file = file_chooser.showOpenDialog(stage);
            try {
               
                if (file != null) {
                    EventTasks.readFile(file);
                }
               
            } catch (Exception ex) {
                Logger.getLogger(ArchBattleLord.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        clear_button.setOnAction((ActionEvent event) -> {
     //      theCave= new Cave();
    //       showCave(theCave.toString());
        });
        
        show_button.setOnAction((ActionEvent e) -> {
     //       showCave(theCave.toString());
        }); 
      
    }
}