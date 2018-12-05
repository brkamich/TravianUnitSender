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
    public final UUID id;
    public ArrayList<String> unitAmounts = new ArrayList<>();
    LocalDateTime departureDate ;
    LocalDateTime creationDate;
    public String targetX;
    public String targetY;
    public String sourceX;
    public String sourceY;
    String movementType;
    
    
    
    public SingleMovementData()
    {
        this. id = UUID.randomUUID();
    }
    
    void setUnitAmount(int i, String text) {
        unitAmounts.add(text);
    }
}
