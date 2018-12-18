
package org.pupkaci.travianunitsender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class MovementListCell extends ListCell<MovementCellData> {
    
    //FXML
    @FXML
    private Label numMovementsLabel;
    
    @FXML
    private Label targetLabel;
    
    @FXML
    private Pane rootPane  ;
    
    @FXML
    private TitledPane titledPane;
   
    @FXML
    private ListView movementList;
    @FXML
    private StackPane stackPane;
    
    //
    private final int ExpandedHeight = 200;
    private final int CollapsedHeight = 25;
    private final int SingleSubCellHeight = 172;
    @FXML
    private Node rootElement;

    
    
    
    MovementCellData lastItem;
    
    private ObservableList observableList = FXCollections.observableArrayList();
    
    private ArrayList<UUID> existingCellUUIDs = new ArrayList<>();
    private Boolean isNowExpanded = true;
    public MovementListCell() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MovementListCell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            rootElement = fxmlLoader.load();
        }
        catch (IOException e)
        {
            System.out.println("Failed to load FXML");
        }
        
        titledPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            this.isNowExpanded = isNowExpanded;
            updateRootPaneHeight();
            
        });
        
        Callback<ListView<SingleMovementData>, ListCell<SingleMovementData>> factory = new Callback<ListView<SingleMovementData>, ListCell<SingleMovementData>>(){
            @Override
            public ListCell<SingleMovementData> call(ListView<SingleMovementData> p) {
                 
                ListCell<SingleMovementData> cell = new SingleMovementCellController();
                return cell;
            }
        };
        movementList.setCellFactory(factory);
        movementList.setItems(observableList);
        
    }

    @Override
    protected void updateItem(MovementCellData item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            if(item == null) 
            {
                System.out.println("Item is null");
            }
            numMovementsLabel.setText(""+item.movements.size());
            for(int i = 0;i < item.movements.size();i++)
            {
                SingleMovementData cellData = item.movements.get(i);
                boolean c = false;
                for(int ii = 0;ii < existingCellUUIDs.size();ii++)
                {
                    if(existingCellUUIDs.get(ii).equals(cellData.id))
                    {
                        c=true;
                        break;
                    }
                }
                if(!c)
                {
                    existingCellUUIDs.add(cellData.id);
                    System.out.println("New iD:"+item.movements.get(i).id);
                    observableList.add(cellData);
                }
            }
            updateRootPaneHeight();
            
            targetLabel.setText("("+item.targetX+","+item.targetY+")");
            setGraphic(rootElement);
        }
    }

    private void updateRootPaneHeight() {
        
        if(lastItem==null)return;
        if (isNowExpanded) {
                int h = SingleSubCellHeight*lastItem.movements.size()-0;
                stackPane.setPrefHeight(h);
                movementList.setPrefHeight(h);
                System.out.println("Height set to "+h);
            }else
                
            {
                //rootPane.setPrefHeight(CollapsedHeight);
            }
    }
}