/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author User
 */
public class JobPosting {
    private final String title;
    private final String requiredSkills;
    private int minExperience;
    private final String location;
    private final String prioritySkills;
    private final String company;
    private int minSalary;
    private int maxSalary;

    public JobPosting(String title, String requiredSkills, int minExperience, String location, String prioritySkills, String company, int minSalary, int maxSalary) {
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.minExperience = minExperience;
        this.location = location;
        this.prioritySkills = prioritySkills;
            
        this.company = company;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        }

    public String getTitle() { return title; }
    public String getRequiredSkills() { return requiredSkills; }
    public int getMinExperience() { return minExperience; }
    public String getLocation() { return location; }
    public String getPrioritySkills() { return prioritySkills; }
    public String getCompany() { return company; }
    public int getMinSalary() { return minSalary; }
    public int getMaxSalary() { return maxSalary; }

    
    public String getState() {
        String[] parts = location.split(",");
        return parts.length > 0 ? parts[0].trim() : "";
    }

    public String getArea() {
        String[] parts = location.split(",");
        return parts.length > 1 ? parts[1].trim() : "";
    }

    
    public void setMinSalary(int minSalary) { this.minSalary = minSalary; }
    public void setMaxSalary(int maxSalary) { this.maxSalary = maxSalary; }
    public void setMinExperience(int exp) { this.minExperience = exp; }
    
    @Override
    public String toString() {
        return "Job: " + title + "| Priority Skills :" + prioritySkills 
                + " | Required Skills: " + requiredSkills + " | Min Experience: " + minExperience + " years" 
                + " | Salary: RM" + minSalary + " - RM" + maxSalary +
                    " | Location: " + location;
    }
    
}


