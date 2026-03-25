/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author User
 */

import adt.LinkedList;
import adt.ListInterface;
import entity.Applicant;
import entity.JobPosting;
import entity.Match;

public class MatchingControl {
    private ListInterface<Match> matches = new LinkedList<>();
    private final ApplicantManagement applicantControl;
    private final JobManagement jobControl;
    private final ScheduleControl scheduleControl;
    
    public MatchingControl(ListInterface<Match> matchList, 
                         ApplicantManagement applicantControl,
                         JobManagement jobControl,
                         ScheduleControl scheduleControl) {
        this.matches = matchList;
        this.applicantControl = applicantControl;
        this.jobControl = jobControl;
        this.scheduleControl = scheduleControl;
    }

    public void findMatches() {
        matches.clear(); //this to clear old matches for new matches
        
        // Get all applicants and jobs
        // Get all applicants and jobs
        int applicantCount = applicantControl.getApplicantList().getNumberOfEntries();
        int jobCount = jobControl.getJobList().getNumberOfEntries();
        
        for (int i = 1; i <= applicantCount; i++) {
            Applicant applicant = applicantControl.getApplicantList().getEntry(i);
            
            // Skip if applicant has completed interview or is already hired
            if (scheduleControl.hasCompletedInterview(applicant) || applicant.isSuccessful()) {
                continue;
            }
            
            for (int j = 1; j <= jobCount; j++) {
                JobPosting job = jobControl.getJobList().getEntry(j);
                int score = calculateMatchScore(applicant, job);
                if (score > 0) { 
                    matches.add(new Match(applicant, job, score));
                }
            }
        }
        sortMatches(); // Implement sorting using ADT operations
    }
   

    private int calculateMatchScore(Applicant applicant, JobPosting job) {
        int score = 0;
        boolean hasSkillMatch = false;

        String[] applicantSkills = applicant.getSkills().toLowerCase().split(", ");
        String[] jobSkills = job.getRequiredSkills().toLowerCase().split(", ");
        String[] prioritySkills = job.getPrioritySkills().toLowerCase().split(", ");

        for (String aSkill : applicantSkills) {
            for (String jSkill : jobSkills) {
                if (aSkill.trim().equals(jSkill.trim())) {
                    hasSkillMatch = true;
                    score += 20; // Base skill match

                    // Check priority skills
                    for (String pSkill : prioritySkills) {
                        if (aSkill.trim().equals(pSkill.trim())) {
                            score += 20; // Priority bonus
                            break;
                        }
                    }
                }
            }
        }

        if (!hasSkillMatch) return 0;

        // Experience calculation
        if (applicant.getExperience() >= job.getMinExperience()) {
            score += 10;
            if (applicant.getExperience() >= job.getMinExperience() + 5) {
                score += 30; // Experience bonus
            }
        }

        // Location matching
        String[] applicantLocParts = applicant.getLocation().split(",");
        String applicantState = applicantLocParts[0].trim();
        String applicantArea = applicantLocParts.length > 1 ? applicantLocParts[1].trim() : "";

        if (applicantState.equalsIgnoreCase(job.getState())) {
            score += 10;
            if (!applicantArea.isEmpty() && applicantArea.equalsIgnoreCase(job.getArea())) {
                score += 10; // Local area bonus
            }
        }

        // Job type preference
        if (applicant.getDesiredJobType().equalsIgnoreCase(job.getTitle())) {
            score += 20;
        }
        
        if (applicant.getExpectedSalary() <= job.getMaxSalary()) {   //expected >= job.getMinSalary() && (spare)
            score += 10; 
        }
//System.out.println("DEBUG: " + applicant.getName() + " matched with job " + job.getTitle() + " → Score: " + score); FOR TESTING
        return score;
    }

    public void displayAllMatches() {
        if (matches.isEmpty()) {
            System.out.println("No matches available.");
            return;
        }

        System.out.println("\n===== ALL MATCHES =====");
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            System.out.println(matches.getEntry(i));
        }
    }

    private void sortMatches() {
        // Implement bubble sort or similar using only ListInterface methods
        int n = matches.getNumberOfEntries(); //count how many matches inside the list
        for (int i = 0; i < n-1; i++) {  
            for (int j = 1; j <= n-i-1; j++) { //position
                Match m1 = matches.getEntry(j);
                Match m2 = matches.getEntry(j+1);
                if (m1.getScore() < m2.getScore()) {
                    matches.replace(j, m2);
                    matches.replace(j+1, m1);
                }
            }
        }
    }
    
    public ListInterface<Match> getMatches() { //above matcheslist is private, so scheduleUI cannot access, add this to make it accessable
        return matches;                        //keeping matches safely encapsulated inside the class.
    }
}