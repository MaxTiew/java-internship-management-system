/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author User
 */
public class InterviewSlot {
    private final String date;
    private final String time;
    private boolean isAvailable;
    
    public InterviewSlot(String date, String time) {
        this.date = date;
        this.time = time;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getDate() { return date; }
    public String getTime() { return time; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
