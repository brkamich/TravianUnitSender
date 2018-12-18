/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pupkaci.travianunitsender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import javafx.fxml.FXML;
import org.pupkaci.traviancomponents.DateTimePicker;

/**
 *
 * @author molnaric
 */
public class SingleMovementData {
    public UUID id;
    public ArrayList<String> unitAmounts = new ArrayList<>();
    LocalDateTime departureDate ;
    LocalDateTime creationDate;
    public String targetX;
    public String targetY;
    public String sourceX;
    public String sourceY;
    String movementType;
    boolean IsHistory;
    
    
    public SingleMovementData(boolean isHistory)
    {
        IsHistory = isHistory;
        AssignID();
    }

    SingleMovementData(SingleMovementData sub,boolean isHistory) {
        AssignID();
        IsHistory = isHistory;
        departureDate = sub.departureDate;
        creationDate = sub.creationDate;
        targetX = sub.targetX;
        targetY = sub.targetY;
        sourceX = sub.sourceX;
        sourceY = sub.sourceY;
        movementType = sub.movementType;
        for(String amt :sub. unitAmounts)
        {
            unitAmounts.add(amt.toString());
        }
    }
    
    void setUnitAmount(int i, String text) {
        unitAmounts.add(text);
    }

    private void AssignID() {
        this. id = UUID.randomUUID();
        System.out.println("Assigned new ID : "+id);
    }
}
