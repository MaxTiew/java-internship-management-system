/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author User
 */

public class Match {
    private final Applicant applicant;
    private final JobPosting job;
    private final int score;

    public Match(Applicant applicant, JobPosting job, int score) {
        this.applicant = applicant;
        this.job = job;
        this.score = score;
    }

    public int getScore() { return score; }
    public Applicant getApplicant() { return applicant; }
    public JobPosting getJob() { return job; }

    @Override
    public String toString() {
        return "Match Found:\nName: " + applicant.getName()+ "\n" 
                + "Email: " + applicant.getEmail() + "\n"
                + "Phone: " + applicant.getPhone() + "\n"
                + "\n" 
                + "Job Title: " + job.getTitle()+ "\n"
                + "Experience(Years): " + applicant.getExperience()+ "\n"
                + "Expected Salary: " + applicant.getExpectedSalary() + "\n"
                + "Location: " + applicant.getLocation() + "\n\n"
                + "   -  Score:  " + score + "  -\n"
                + "--------------------------------\n" ;
    }
}
