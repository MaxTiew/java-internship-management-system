/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import adt.Sorter;
import adt.LinkedList;
import adt.ListInterface;
import entity.JobPosting;



/**
 *
 * @author User
 */
public class JobManagement {
    private ListInterface<JobPosting> jobs = new LinkedList<>();   
    
    public JobManagement(ListInterface<JobPosting> jobList) {
        this.jobs = jobList;
    }
    
    public boolean addJob(String title, String requiredSkills, int minExperience,String location, String prioritySkills, String company, int minSalary, int maxSalary) {        
        JobPosting newJob = new JobPosting(title, requiredSkills, minExperience, location, prioritySkills, company, minSalary, maxSalary);
        jobs.add(newJob);
        return true;
    }
    
    
    // Display all job postings
    public void displayAllJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No jobs found.");
            return;
        }

        System.out.println("\n===== ALL JOBS =====");
        for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
            System.out.println(i + ". " + jobs.getEntry(i));
        }
    }

    // Remove job by position
    public boolean removeJob(int position) {
        if (position < 1 || position > jobs.getNumberOfEntries()) {
            System.out.println(" Invalid position.");
            return false;
        }

        JobPosting removed = jobs.remove(position);
        System.out.println(" Removed: " + removed);
        return true;
    }

    // Update a job posting at a specific position
    public boolean updateJob(int position, JobPosting updatedJob) {
        if (jobs.replace(position, updatedJob)) {
            System.out.println(" Job updated at position " + position);
            return true;
        } else {
            System.out.println(" Failed to update. Invalid position.");
            return false;
        }
    }

    // Get job by position
    public JobPosting getJobAt(int position) {
        if (position < 1 || position > jobs.getNumberOfEntries()) {
            return null;
        }
        return jobs.getEntry(position);
    }

    // Search job by title (returns first match position or -1 if not found)
    public int findJobByTitle(String title) {
        for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
            JobPosting job = jobs.getEntry(i);
            if (job.getTitle().equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1;
    }

    
    public void filterAndSortJobs(String titleKeyword, String locationKeyword, String skillKeyword, 
                         int minExperience, String companyKeyword, int minSalary, int maxSalary, int sortOption) {
        ListInterface<JobPosting> filtered = new LinkedList<>();
        for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
            JobPosting job = jobs.getEntry(i);
            boolean matches = true;

            if (!companyKeyword.isEmpty() && !job.getCompany().toLowerCase().contains(companyKeyword.toLowerCase())) matches = false;
            if (!titleKeyword.isEmpty() && !job.getTitle().toLowerCase().contains(titleKeyword.toLowerCase())) matches = false;
            if (!locationKeyword.isEmpty() && !job.getLocation().toLowerCase().contains(locationKeyword.toLowerCase())) matches = false;
            if (!skillKeyword.isEmpty()) {
                boolean skillFound = false;
                String[] skills = job.getRequiredSkills().toLowerCase().split(",");
                for (String skill : skills) {
                    if (skill.trim().contains(skillKeyword.toLowerCase())) {
                        skillFound = true;
                        break;
                    }
                }
                if (!skillFound) matches = false;
            }
            if (minExperience > 0 && job.getMinExperience() < minExperience) matches = false;
            if ((minSalary > 0 && job.getMaxSalary() < minSalary) || (maxSalary > 0 && job.getMinSalary() > maxSalary)) matches = false;

            if (matches) filtered.add(job);
        }

        if (filtered.isEmpty()) {
            System.out.println("No jobs match your criteria.");
            return;
        }

        switch (sortOption) {
            case 1: Sorter.sortByMinSalary(filtered); break;
            case 2: Sorter.sortByLocation(filtered); break;
            case 3: Sorter.sortByTitle(filtered); break;
            case 4: Sorter.sortByExperience(filtered); break;
            case 5: Sorter.sortByMaxSalary(filtered); break;
            case 6: Sorter.sortByRequiredSkill(filtered); break;
            default: System.out.println("Invalid sort option. Showing unsorted results.");
        }

        System.out.println("\n===== SORTED & FILTERED JOB RESULTS =====");
        for (int i = 1; i <= filtered.getNumberOfEntries(); i++) {
            System.out.println(filtered.getEntry(i));
        }
    }


    // Get total job postings
    public int getTotalJobs() {
        return jobs.getNumberOfEntries();
    }

    // Check if job list is empty
    public boolean isEmpty() {
        return jobs.isEmpty();
    }

    // Clear all job postings
    public void clearJobs() {
        jobs.clear();
        System.out.println(" All jobs removed.");
    }
    
    public ListInterface<JobPosting> getJobList() {
        return jobs;
    }
}

    
    

