/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pupkaci.travianunitsender;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.pupkaci.traviancomponents.DateTimePicker;

/**
 * FXML Controller class
 *
 * @author molnaric
 */
public class SingleMovementCellController extends ListCell<SingleMovementData> {

    //elements
    @FXML
    private Node rootElement;

    //fields
    SingleMovementData lastItem;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label timeLeftField;
    @FXML
    private Label targetX;
    @FXML
    private Label targetY;
    @FXML
    private TextField sourceX;
    @FXML
    private TextField sourceY;
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
    private ChoiceBox  attackTypeBox;
    @FXML
    private TextField unitNumField9;
    @FXML
    private TextField unitNumField10;
@FXML
    private DateTimePicker departurePicker;
    private ArrayList<TextField> unitFields = new ArrayList<TextField>();

    @FXML
    private void onCloseClicked(MouseEvent event) {
        System.out.println("Removing !!!");
        MainSceneController.deleteItem(lastItem.id);
    }
    @FXML
    private void onDepartureChanged(ActionEvent event) {
        System.out.println("Departure date changed");
    }
    public SingleMovementCellController() {

        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SingleMovementCell.fxml"));
        fxmlLoader.setController(this);
        try {
            rootElement = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Failed to load FXML "+e.getCause().getMessage());
        }

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
        
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> 
        {
                //The task you want to do
            
                try
                    
                {
                    LocalDateTime fromDateTime = LocalDateTime.now();
                LocalDateTime toDateTime = lastItem.departureDate;

                LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

                long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
                tempDateTime = tempDateTime.plusYears( years );

                long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
                tempDateTime = tempDateTime.plusMonths( months );

                long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
                tempDateTime = tempDateTime.plusDays( days );


                long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
                tempDateTime = tempDateTime.plusHours( hours );

                long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
                tempDateTime = tempDateTime.plusMinutes( minutes );

                long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);
                hours += days*24 + months*30*24 + years*365*24;
                
                timeLeftField .setText(String.format("%02d", hours)+":"+String.format("%02d", minutes)+":"+String.format("%02d", seconds));
                }catch(Exception ex)
                {}
                
            }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }/*
        public void updateTime  ()
            {
                //The task you want to do
                LocalDateTime fromDateTime = LocalDateTime.now();
                LocalDateTime toDateTime = lastItem.departureDate;

                LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

                long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
                tempDateTime = tempDateTime.plusYears( years );

                long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
                tempDateTime = tempDateTime.plusMonths( months );

                long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
                tempDateTime = tempDateTime.plusDays( days );


                long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
                tempDateTime = tempDateTime.plusHours( hours );

                long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
                tempDateTime = tempDateTime.plusMinutes( minutes );

                long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);
                hours += days*24 + months*30*24 + years*365*24;
                
                timeLeftField .setText(String.format("%03d", hours)+":"+String.format("%02d", minutes)+":"+String.format("%02d", seconds));
            }*/
    @Override
    protected void updateItem(SingleMovementData item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;

            for (int i = 0; i < 10; i++) {
                unitFields.get(i).setText(item.unitAmounts.get(i));
            }
            targetX.setText(item.targetX);
            targetY.setText(item.targetY);
            sourceX.setText(item.sourceX);
            sourceY.setText(item.sourceY);
                    //update graphics here
            setGraphic(rootElement);
            attackTypeBox.setValue(item.movementType);
            departurePicker.setDateTimeValue(item.departureDate);
        }
    }

}
