/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import adt.LinkedList;
import adt.ListInterface;
import entity.Applicant;
import entity.JobPosting;

/**
 *
 * @author User
 */

public class SearchControl {
    private ListInterface<Applicant> applicantList = new LinkedList<>();
    private ListInterface<JobPosting> jobList = new LinkedList<>();


    public SearchControl(ListInterface<Applicant> applicantList, ListInterface<JobPosting> jobList) {
        this.applicantList = applicantList;
        this.jobList = jobList;
    }

    public ListInterface<Applicant> searchApplicants(String keyword) {
        ListInterface<Applicant> results = new adt.LinkedList<>();
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant applicant = applicantList.getEntry(i);
            if (applicant.getName().toLowerCase().contains(keyword.toLowerCase())
                    || applicant.getSkills().toLowerCase().contains(keyword.toLowerCase())
                    || applicant.getLocation().toLowerCase().contains(keyword.toLowerCase())
                    || applicant.getDesiredJobType().toLowerCase().contains(keyword.toLowerCase())
                    || String.valueOf(applicant.getExpectedSalary()).contains(keyword)
                    || String.valueOf(applicant.getPhone()).contains(keyword)
                    || String.valueOf(applicant.getExperience()).contains(keyword)) {
                results.add(applicant);
            }
        }
        return results;
    }
    

    public ListInterface<JobPosting> searchJobs(String keyword) {
        ListInterface<JobPosting> results = new adt.LinkedList<>();
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            JobPosting job = jobList.getEntry(i);
            if (job.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || job.getRequiredSkills().toLowerCase().contains(keyword.toLowerCase())
                    || job.getPrioritySkills().toLowerCase().contains(keyword.toLowerCase())
                    || job.getLocation().toLowerCase().contains(keyword.toLowerCase())
                    || job.getCompany().toLowerCase().contains(keyword.toLowerCase())
                    || String.valueOf(job.getMinSalary()).contains(keyword)
                    || String.valueOf(job.getMaxSalary()).contains(keyword)
                    || String.valueOf(job.getMinExperience()).contains(keyword)) {
                results.add(job);
            }
        }
        return results;
    }
    
}
