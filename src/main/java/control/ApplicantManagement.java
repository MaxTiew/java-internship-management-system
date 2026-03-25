/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedList;
import adt.ListInterface;
import adt.Sorter;
import entity.Applicant;
import entity.JobPosting;

/**
 *
 * @author User
 */
public class ApplicantManagement {
    private ListInterface<Applicant> applicants = new LinkedList<>();

    public ApplicantManagement(ListInterface<Applicant> list) {
        this.applicants = list;
    }

    public boolean createApplicant(String name, String email, int phone, 
                                 String skills, int exp, String location, String jobType, int expectedSalary) {
        Applicant newApplicant = new Applicant(name, email, phone, skills, exp, location, jobType, expectedSalary);
        applicants.add(newApplicant);
        return true;
    }

    public boolean removeApplicant(int position) {
        if (position < 1 || position > applicants.getNumberOfEntries()) {
            System.out.println("Invalid position.");
            return false;
        }
        Applicant removed = applicants.remove(position);
        System.out.println(" Removed: " + removed);
        return true;
    }

    public boolean updateApplicant(int position, Applicant newApplicant) {
        if (applicants.replace(position, newApplicant)) {
            System.out.println(" Applicant updated at position " + position);
            return true;
        } else {
            System.out.println(" Failed to update. Invalid position.");
            return false;
        }
    }

    public Applicant getApplicant(int position) {
         if (position < 1 || position > applicants.getNumberOfEntries()) {
            return null;
        }
        return applicants.getEntry(position);
    }
    

    public void displayAllApplicants() {
        System.out.println("\n===== ALL APPLICANTS =====");
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            System.out.println(i + ". " + applicants.getEntry(i));
        }
    }
    
    // Find applicant by name (linear search)
    public Applicant findApplicantByName(String name) {
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant a = applicants.getEntry(i);
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    // Filter applicants by skill keyword
    public void filterBySkill(String skillKeyword) {
        boolean found = false;
        System.out.println("\nApplicants with skill: " + skillKeyword);

        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant a = applicants.getEntry(i);
            if (a.getSkills().toLowerCase().contains(skillKeyword.toLowerCase())) {
                System.out.println(a);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No applicants found with that skill.");
        }
    }
     
      
    public void sortApplicantsForJob(String jobTitle, int sortChoice) {
        ListInterface<Applicant> filtered = new LinkedList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant a = applicants.getEntry(i);
            if (a.getDesiredJobType().toLowerCase().contains(jobTitle.toLowerCase())) {
                filtered.add(a);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No applicants match the job title.");
            return;
        }

        switch (sortChoice) {
            case 1:
                Sorter.sortApplicantsByExperienceDescending(filtered);
                break;
            case 2:
                Sorter.sortApplicantsByExpectedSalaryAscending(filtered);
                break;
            case 3:
                String[] requiredSkills = jobTitle.toLowerCase().split(" ");
                Sorter.sortApplicantsBySkillMatch(filtered, requiredSkills);
                break;
            default:
                System.out.println("Invalid sort choice.");
                return;
        }

        System.out.println("\n--- Sorted Applicants for '" + jobTitle + "' ---");
        for (int i = 1; i <= filtered.getNumberOfEntries(); i++) {
            System.out.println(i + ". " + filtered.getEntry(i));
        }
    }
        
        
    public void clearAll() {
        applicants.clear();
        System.out.println(" All applicants removed.");
    }

    
    public int getTotalApplicants() {
        return applicants.getNumberOfEntries();
    }


    public boolean isEmpty() {
        return applicants.isEmpty();
    }
    
    public ListInterface<Applicant> getApplicantList() {
        return applicants;
    }
    
 
}