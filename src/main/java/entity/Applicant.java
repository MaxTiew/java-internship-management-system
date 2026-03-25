/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity;
/**
 *
 * @author User
 */
public class Applicant {
    private final String name;
    private final String email;
    private final int phone;
    private final String skills;
    private final int experience;
    private final String homeLocation;
    private final String desiredJobType; 
    private final int expectedSalary;
    
    private boolean successful = false;


    public Applicant(String name, String email, int phone, String skills, int experience, String homeLocation, String desiredJobType, int expectedSalary) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.skills = skills;
        this.experience = experience;
        this.homeLocation = homeLocation;
        this.desiredJobType = desiredJobType;  
        this.expectedSalary = expectedSalary;
        
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getPhone() { return phone; }
    public String getSkills() { return skills; }
    public int getExperience() { return experience; }
    public String getLocation() { return homeLocation; }
    public String getDesiredJobType() { return desiredJobType; } 
    public int getExpectedSalary() { return expectedSalary; }
    public boolean isSuccessful() { return successful; }


    @Override
    public String toString() {
        return "Applicant: " + name + "| email: " + email + "| phone: " + phone + " | Skills: " + skills + 
               " | Experience: " + experience + " years | Location: " + homeLocation + 
               " | Desired Job Type: " + desiredJobType + " | Expected Salary: RM" + expectedSalary;
    }
    
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}