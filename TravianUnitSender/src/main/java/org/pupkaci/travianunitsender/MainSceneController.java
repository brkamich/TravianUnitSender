/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pupkaci.travianunitsender;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.pupkaci.traviancomponents.DateTimePicker;

/**
 * FXML Controller class
 *
 * @author molnaric
 */
public class MainSceneController implements Initializable {
    //fxml bindings
    @FXML
    private TextField sourceX;
    @FXML
    private TextField sourceY;
    @FXML
    private TextField targetX;
    @FXML
    private TextField targetY;
    @FXML
    private ChoiceBox  attackTypeBox;
    
    @FXML
    private TextField unitNumField1;
    @FXML
    private TextField unitNumField2;
    @FXML
    private TextField unitNumField3;
    @FXML
    private TextField unitNumField4;
    @FXML
    private TextField unitNumField5;
    @FXML
    private TextField unitNumField6;
    @FXML
    private TextField unitNumField7;
    @FXML
    private TextField unitNumField8;
    @FXML
    private TextField unitNumField9;
    @FXML
    private TextField unitNumField10;
    @FXML
    private DateTimePicker departurePicker;
    
    //others
    private ArrayList<TextField> unitFields = new ArrayList<TextField>();
    
    private static ArrayList<MovementCellData> movements = new ArrayList<>();
    private static MainSceneController singleton;
    @FXML
    private ListView mainList;
    
    private ObservableList observableList = FXCollections.observableArrayList();
    public Stage stage;
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton = this;
        Callback<ListView<MovementCellData>, ListCell<MovementCellData>> factory = new Callback<ListView<MovementCellData>, ListCell<MovementCellData>>(){
            @Override
            public ListCell<MovementCellData> call(ListView<MovementCellData> p) {
                 
                ListCell<MovementCellData> cell = new MovementListCell();
                return cell;
            }
        };
        mainList.setCellFactory(factory);
        mainList.setItems(observableList);
        

        unitFields.add(unitNumField1);
        unitFields.add(unitNumField2);
        unitFields.add(unitNumField3);
        unitFields.add(unitNumField4);
        unitFields.add(unitNumField5);
        unitFields.add(unitNumField6);
        unitFields.add(unitNumField7);
        unitFields.add(unitNumField8);
        unitFields.add(unitNumField9);
        unitFields.add(unitNumField10);
        
        ArrayList<TextField> targets = new ArrayList<>();
        targets.add(targetX);
        targets.add(targetY);
        targets.add(sourceX);
        targets.add(sourceY);
        //make field numeric only
        for(int i =0;i < 4;i++)
        {
            TextField f = targets.get(i);
            f.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        f.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    System.out.println("Value:'"+newValue+"'");
                    
                }
            });
        }
        for(int i =0;i < 10;i++)
        {
            TextField f = unitFields.get(i);
            f.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        f.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    System.out.println("Value:'"+newValue+"'");
                    
                }
            });
            f.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if (!newPropertyValue)
                    {
                        String txt = f.textProperty().get();
                        if("".equals(txt) || txt == null)
                        {
                            System.out.println("Text empty");
                            f.setText("0");
                        }
                        
                    }
                }
            });
            f.setText("0");
        }
        LocalDateTime now = LocalDateTime.now();
        departurePicker.setDateTimeValue(now.plusHours(2));
    }    
    public static void deleteItem(UUID id)
    {
        for(int i = 0;i < movements.size();i++)
        {
            MovementCellData mov  = movements.get(i);
            boolean matched = false;
            for(int t = 0;t < mov.movements.size();t++)
            {
                SingleMovementData sub = mov.movements.get(t);
                if(sub.id.equals(id))
                {
                    matched = true;
                    mov.remove(id,t);
                    break;
                }
            }
            if(matched)
            {
                if (mov.movements.size()==0) {
                    MovementCellData dataToRemove = movements.get(i);
                    movements.remove(dataToRemove);
                    singleton.observableList.remove(dataToRemove);
                }
                singleton.mainList.refresh();
                break;
            }
        }
    }
    
    @FXML
    private void onSignOutClick(ActionEvent event) {
        MainApp.signOut();
    }
    @FXML
    private void onMovementAddClick(ActionEvent event) {
        System.out.println("Clicked add movement");
        try
        {
            Integer.parseInt(targetX.getText());  
            Integer.parseInt(targetY.getText());
            Integer.parseInt(sourceX.getText());
            Integer.parseInt(sourceY.getText());
        }catch(Exception ex)
        {
            System.out.println("Failed to parse");
            return;
        }
        SingleMovementData newItem  = new SingleMovementData(); 
        for(int i = 0;i < 10;i++)
        {
            
            newItem.setUnitAmount(i,unitFields.get(i).getText());
        }     
        newItem.departureDate = departurePicker.getDateTimeValue();
        newItem.creationDate =  LocalDateTime.now();
        newItem.targetX = targetX.getText();
        newItem.targetY = targetY.getText();
        newItem.sourceX = sourceX.getText();
        newItem.sourceY = sourceY.getText();
        newItem.movementType = (String)attackTypeBox.getValue();
        MovementCellData cell = GetCell(newItem);
        cell.addMovement(newItem);
        mainList.refresh();
    }
    
    @FXML
    private void onResetBtnClick(ActionEvent event) {
        System.out.println("Clicked reset movement");
        
        for(int i =0;i < 10;i++)
        {
            TextField f = unitFields.get(i);
            f.setText("0");
        }
        LocalDateTime now = LocalDateTime.now();
        departurePicker.setDateTimeValue(now.plusHours(2));
        attackTypeBox.setValue("Normal");
    }
    @FXML
    private void onAboutClick(ActionEvent event) {
        System.out.println("Clicked about");
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AboutDialog.fxml"));
            Parent parent = fxmlLoader.load();
            AboutDialogController dialogController = fxmlLoader.<AboutDialogController>getController();
            //dialogController.setAppMainObservableList(tvObservableList);
            Scene scene = new Scene(parent, 600, 400);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }catch(Exception ex)
        {
            
        }
    }
    
    @FXML 
    private void onImportClick(ActionEvent event) {
        System.out.println("Clicked import");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Project JSON Files", "*.json")
       );
        File selectedFile = fileChooser.showOpenDialog(MainApp.stage);
        System.out.println("Opened file "+selectedFile.getName());
    }
    @FXML 
    private void onExportClick(ActionEvent event) {
        System.out.println("Clicked export");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("untitledProject1.json");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Project JSON Files", "*.json")
       );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+"/Documents"));
        File selectedFile = fileChooser.showSaveDialog(MainApp.stage);
        System.out.println("Opened file "+selectedFile.getName());
    }
    private MovementCellData GetCell(SingleMovementData newItem) {
        //temp
        
        for(int i = 0;i < movements.size();i++)
        {
            MovementCellData mov  = movements.get(i);
            if(mov.targetX.equals(newItem.targetX) && mov.targetY.equals(newItem.targetY))
            {
                return mov;
            }
        }
        
        MovementCellData data = new MovementCellData(newItem.targetX,newItem.targetY);
        movements.add(data);
        observableList.add(data);
        return data;
    }
}
