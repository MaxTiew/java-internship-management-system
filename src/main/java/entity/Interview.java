/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author User
 */
public class Interview {
    private final Applicant applicant;
    private final String mode; // "Online" or "Physical"
    private String date;
    private final String time;
    private boolean successful = false;

    public Interview(Applicant applicant, String mode, String date, String time) {
        this.applicant = applicant;
        this.mode = mode;
        this.date = date;
        this.time = time;
    }

    // Getters
    public Applicant getApplicant() { return applicant; }
    public String getMode() { return mode; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public boolean isSuccessful() {return successful; }

    @Override
    public String toString() {
        return "Applicant: " + applicant.getName() + 
               "\nMode: " + mode + 
               "\nDate: " + date + 
               "\nTime: " + time + 
               "\n-----------------------------";
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

}

