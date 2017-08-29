/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iboxbd.broadband.statistics.model;

/**
 *
 * @author User
 */
public class TimeCounting {
    private int time;
    private boolean status;

    public TimeCounting(int time, boolean status) {
        this.time = time;
        this.status = status;
    }

    
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
