/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pupkaci.travianunitsender;

import java.util.ArrayList;
import java.util.UUID;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author molnaric
 */
public class MovementCellData {
    public String targetX,targetY;
    public ArrayList<SingleMovementData> movements = new ArrayList<>();
    
    public MovementCellData(String targetX,String targetY)
    {
        this.targetX = targetX;
        this.targetY = targetY;
    }       


    void addMovement(SingleMovementData newItem) {
        movements.add(newItem);
    }

    void remove(UUID id, int t) {
        
        movements.remove(t);
    }

    
}
