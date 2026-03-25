/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ListInterface;
import entity.Applicant;
import entity.Interview;
import entity.JobPosting;
import entity.Match;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 *
 * @author User
 */
public class ReportControl {
    private final MatchingControl matchingControl;
    private final ApplicantManagement applicantControl;
    

    public ReportControl(MatchingControl matchingControl, ApplicantManagement applicantControl) {
        this.matchingControl = matchingControl;
        this.applicantControl = applicantControl;
    }

    public void generateMatchReport(ListInterface<Match> showDetails) {
        ListInterface<Match> matches = matchingControl.getMatches();
        
        // Header
        System.out.println("\n===== MATCHING REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Matches: " + matches.getNumberOfEntries());
        
        // Summary Statistics
        printMatchSummary(matches);
        
        // Detailed View (if requested)
        if (matches.getNumberOfEntries() > 0) {
            printMatchDetails(matches);
        }
    }

    private void printMatchSummary(ListInterface<Match> matches) {
        int strong = 0, good = 0, weak = 0;
        
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            int score = matches.getEntry(i).getScore();
            if (score >= 75) strong++;
            else if (score >= 50) good++;
            else weak++;
        }
        
        System.out.println("\n[ MATCH STRENGTH SUMMARY ]");
        System.out.println("Strong (75+): " + strong + " candidates");
        System.out.println("Good (50-74): " + good + " candidates");
        System.out.println("Weak (<50): " + weak + " candidates");
        System.out.printf("Average Score: %.1f\n", calculateAverageScore(matches));
    }

    private void printMatchDetails(ListInterface<Match> matches) {
        System.out.println("\n[ DETAILED MATCH RESULTS ]");
        System.out.println("+-----------------+----------------------+----------------------+-------+---------+");
        System.out.println("| Applicant       | Job Title            | Key Skills           | Score | Strength|");
        System.out.println("+-----------------+----------------------+----------------------+-------+---------+");
        
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            Match match = matches.getEntry(i);
            System.out.printf("| %-15s | %-20s | %-20s | %5d | %-7s |\n",
                truncate(match.getApplicant().getName(), 15),
                truncate(match.getJob().getTitle(), 20),
                truncate(getTopSkills(match), 20),
                match.getScore(),
                getStrengthLabel(match.getScore()));
        }
        System.out.println("+-----------------+----------------------+----------------------+-------+---------+");
    }

    // Helper methods
    private String getStrengthLabel(int score) {
        if (score >= 75) return "Strong";
        if (score >= 50) return "Good";
        return "Weak";
    }

    private String getTopSkills(Match match) {
        // Get top 3 matching skills or all if less than 3
        String[] applicantSkills = match.getApplicant().getSkills().split(",");
        String[] jobSkills = match.getJob().getRequiredSkills().split(",");
        
        StringBuilder common = new StringBuilder();
        for (String aSkill : applicantSkills) {
            for (String jSkill : jobSkills) {
                if (aSkill.trim().equalsIgnoreCase(jSkill.trim())) {
                    if (common.length() > 0) common.append(", ");
                    common.append(aSkill.trim());
                    if (common.length() > 15) break; // Limit length
                }
            }
        }
        return common.length() > 0 ? common.toString() : "N/A";
    }

    private double calculateAverageScore(ListInterface<Match> matches) {
        if (matches.isEmpty()) return 0;
        double total = 0;
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            total += matches.getEntry(i).getScore();
        }
        return total / matches.getNumberOfEntries();
    }

    private String truncate(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength-3) + "..." : text;
    }
    
    
    private boolean isDateBeforeNow(String dateString) {
            try {
                Date interviewDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                return interviewDate.before(new Date());
            } catch (ParseException e) {
                return false; // fallback: treat as future (not completed)
            }
        }       
    
    
//----------------------------schedule report------------------------------------------
    
    public void generateInterviewReport(ListInterface<Interview> past, ListInterface<Interview> upcoming) {
       int totalScheduled = 0;
       int totalCompleted = 0;
       int totalSuccess = 0;

       ListInterface<String> skillList = new adt.LinkedList<>();

       // Go through past interviews
       for (int i = 1; i <= past.getNumberOfEntries(); i++) {
           Interview interview = past.getEntry(i);
           totalScheduled++;

           // Count completed (date in the past)
           if (isDateBeforeNow(interview.getDate())) {
               totalCompleted++;
           }

           // Count successful hires regardless of date
           if (interview.getApplicant().isSuccessful()) {        
               totalSuccess++;                            //past applicant not confirm hire, maybe sometime interviewed then 
           }                                              //forgot to inform applicant 

           collectSkills(interview.getApplicant(), skillList);
       }

       // Go through upcoming interviews
       for (int i = 1; i <= upcoming.getNumberOfEntries(); i++) {
           Interview interview = upcoming.getEntry(i);
           totalScheduled++;

           if (isDateBeforeNow(interview.getDate())) {
               totalCompleted++;
           }

           if (interview.getApplicant().isSuccessful()) {
               totalSuccess++;
           }

           collectSkills(interview.getApplicant(), skillList);
       }

       double successRate = (totalScheduled > 0)
               ? ((double) totalSuccess / totalScheduled) * 100 : 0;

       String topSkill = findTopSkill(skillList, past, upcoming);

       // Output
       System.out.println("\n===== INTERVIEW REPORT =====");
       System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
       System.out.println("\nTotal Scheduled: " + totalScheduled);
       System.out.println("Total Completed (past date): " + totalCompleted);
       System.out.println("Hired Applicants: " + totalSuccess);
       System.out.printf("Success Rate: %.2f%%\n", successRate);
       System.out.println("Top Skill in Demand: " + (topSkill.isEmpty() ? "N/A" : topSkill));
   }
    
    private void collectSkills(Applicant applicant, ListInterface<String> skillList) {
        String[] skills = applicant.getSkills().toLowerCase().split("\\s*,\\s*");

        for (String skill : skills) {
            boolean exists = false;

            for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                if (skillList.getEntry(i).equals(skill)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                skillList.add(skill);
            }
        }
    }
    
    private String findTopSkill(ListInterface<String> skillList,
                                ListInterface<Interview> past,
                                ListInterface<Interview> upcoming) {

        String topSkill = "";
        int topCount = 0;

        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            String skill = skillList.getEntry(i);
            int count = 0;

            // Count in past interviews
            for (int j = 1; j <= past.getNumberOfEntries(); j++) {
                Interview interview = past.getEntry(j);
                String[] appSkills = interview.getApplicant().getSkills().toLowerCase().split("\\s*,\\s*");
                for (String s : appSkills) {
                    if (s.equals(skill)) {
                        count++;
                        break;
                    }
                }
            }

            // Count in upcoming interviews
            for (int j = 1; j <= upcoming.getNumberOfEntries(); j++) {
                Interview interview = upcoming.getEntry(j);
                String[] appSkills = interview.getApplicant().getSkills().toLowerCase().split("\\s*,\\s*");
                for (String s : appSkills) {
                    if (s.equals(skill)) {
                        count++;
                        break;
                    }
                }
            }

            if (count > topCount) {
                topCount = count;
                topSkill = skill;
            }
        }

        return topSkill;
    }
    
    
//-----------------------------------------------------job report----------------------------------------------------------------
    public void generateJobSummaryGrid(ListInterface<JobPosting> jobList) {
        int total = jobList.getNumberOfEntries();
        if (total == 0) {
            System.out.println("No job postings available.");
            return;
        }

        System.out.println("===== Job Market Summary Report =====");
        System.out.printf("Total Job Postings: %d\n\n", total);

        // Job Title Distribution
        String[] titles = new String[total];
        int[] titleCounts = new int[total];
        int titleIndex = 0;

        // Location Distribution
        String[] locations = new String[total];
        int[] locationCounts = new int[total];
        int locationIndex = 0;

        int totalMinSalary = 0;
        int totalMaxSalary = 0;

        JobPosting mostMinSalaryJob = jobList.getEntry(1);
        JobPosting maxExpJob = jobList.getEntry(1);
        JobPosting minExpJob = jobList.getEntry(1);

        for (int i = 1; i <= total; i++) {
            JobPosting job = jobList.getEntry(i);
            String title = job.getTitle();
            String location = job.getLocation();

            // Count titles
            boolean foundTitle = false;
            for (int j = 0; j < titleIndex; j++) {
                if (titles[j].equalsIgnoreCase(title)) {
                    titleCounts[j]++;
                    foundTitle = true;
                    break;
                }
            }
            if (!foundTitle) {
                titles[titleIndex] = title;
                titleCounts[titleIndex] = 1;
                titleIndex++;
            }

            // Count locations
            boolean foundLocation = false;
            for (int j = 0; j < locationIndex; j++) {
                if (locations[j].equalsIgnoreCase(location)) {
                    locationCounts[j]++;
                    foundLocation = true;
                    break;
                }
            }
            if (!foundLocation) {
                locations[locationIndex] = location;
                locationCounts[locationIndex] = 1;
                locationIndex++;
            }

            totalMinSalary += job.getMinSalary();
            totalMaxSalary += job.getMaxSalary();

            if (job.getMinSalary() < mostMinSalaryJob.getMinSalary()) {
                mostMinSalaryJob = job;
            }
            if (job.getMinExperience() > maxExpJob.getMinExperience()) {
                maxExpJob = job;
            }
            if (job.getMinExperience() < minExpJob.getMinExperience()) {
                minExpJob = job;
            }
        }

        System.out.println("Job Title Distribution:");
        for (int i = 0; i < titleIndex; i++) {
            double perc = ((double) titleCounts[i] / total) * 100;
            System.out.printf(" - %s: %d (%.0f%%)\n", titles[i], titleCounts[i], perc);
        }

        System.out.println("\nLocation Distribution:");
        for (int i = 0; i < locationIndex; i++) {
            double perc = ((double) locationCounts[i] / total) * 100;
            System.out.printf(" - %s: %d (%.0f%%)\n", locations[i], locationCounts[i], perc);
        }

        double avgMin = (double) totalMinSalary / total;
        double avgMax = (double) totalMaxSalary / total;
        System.out.println("\nAverage Salary Range:");
        System.out.printf(" - Min Salary: RM %.2f\n", avgMin);
        System.out.printf(" - Max Salary: RM %.2f\n", avgMax);

        System.out.println("\nHighlights:");
        System.out.printf(" - Most Common Job Title: %s\n", titles[0]);
        System.out.printf(" - Job with Lowest Min Salary: %s (RM %d)\n",
            mostMinSalaryJob.getTitle(), mostMinSalaryJob.getMinSalary());
        System.out.printf(" - Highest Experience Required: %s (%d years)\n",
            maxExpJob.getTitle(), maxExpJob.getMinExperience());
        System.out.printf(" - Lowest Experience Required: %s (%d years)\n",
            minExpJob.getTitle(), minExpJob.getMinExperience());
    }
    

    
//---------------------------------------------applicant report--------------------------------------------------------

public void generateGeneralReport() {
        ListInterface<Applicant> applicants = applicantControl.getApplicantList();
        
        // Header
        System.out.println("\n===== APPLICANT GENERAL REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Applicants: " + applicants.getNumberOfEntries());
        
        // Summary Statistics
        printApplicantSummary(applicants);
        
        // Detailed View
        if (applicants.getNumberOfEntries() > 0) {
            printApplicantDetails(applicants);
        }
    }
    
    /**
     * Generates a report on applicants filtered by criteria
     */
    public void generateFilteredReport(String location, String jobType, String skill, 
                                      int minExperience, int maxSalary) {
        ListInterface<Applicant> allApplicants = applicantControl.getApplicantList();
        ListInterface<Applicant> filteredApplicants = new adt.LinkedList<>();
        
        // Apply filters
        for (int i = 1; i <= allApplicants.getNumberOfEntries(); i++) {
            Applicant applicant = allApplicants.getEntry(i);
            boolean matches = true;
            
            // Location filter
            if (!location.isEmpty() && 
                !applicant.getLocation().toLowerCase().contains(location.toLowerCase())) {
                matches = false;
            }
            
            // Job Type filter
            if (!jobType.isEmpty() && 
                !applicant.getDesiredJobType().toLowerCase().contains(jobType.toLowerCase())) {
                matches = false;
            }
            
            // Skill filter
            if (!skill.isEmpty() && 
                !applicant.getSkills().toLowerCase().contains(skill.toLowerCase())) {
                matches = false;
            }
            
            // Experience filter
            if (minExperience > 0 && applicant.getExperience() < minExperience) {
                matches = false;
            }
            
            // Expected Salary filter
            if (maxSalary > 0 && applicant.getExpectedSalary() > maxSalary) {
                matches = false;
            }
            
            if (matches) {
                filteredApplicants.add(applicant);
            }
        }
        
        // Header
        System.out.println("\n===== APPLICANT FILTERED REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nFilters Applied:");
        if (!location.isEmpty()) System.out.println("- Location: " + location);
        if (!jobType.isEmpty()) System.out.println("- Job Type: " + jobType);
        if (!skill.isEmpty()) System.out.println("- Skill: " + skill);
        if (minExperience > 0) System.out.println("- Min Experience: " + minExperience + " years");
        if (maxSalary > 0) System.out.println("- Max Expected Salary: RM" + maxSalary);
        
        System.out.println("\nTotal Matching Applicants: " + filteredApplicants.getNumberOfEntries());
        
        // If we have results, show details
        if (filteredApplicants.getNumberOfEntries() > 0) {
            printApplicantDetails(filteredApplicants);
        } else {
            System.out.println("\nNo applicants match the specified criteria.");
        }
    }
    
    /**
     * Generates a report on the top skills among applicants
     */
    public void generateSkillsReport() {
        ListInterface<Applicant> applicants = applicantControl.getApplicantList();
        ListInterface<String> skillList = new adt.LinkedList<>();
        ListInterface<Integer> skillCounts = new adt.LinkedList<>();
        
        // Extract all skills and count occurrences
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            String[] skills = applicants.getEntry(i).getSkills().toLowerCase().split("\\s*,\\s*");
            
            for (String skill : skills) {
                skill = skill.trim();
                if (skill.isEmpty()) continue;
                
                boolean found = false;
                for (int j = 1; j <= skillList.getNumberOfEntries(); j++) {
                    if (skillList.getEntry(j).equals(skill)) {
                        int count = skillCounts.getEntry(j);
                        skillCounts.replace(j, count + 1);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    skillList.add(skill);
                    skillCounts.add(1);
                }
            }
        }
        
        // Header
        System.out.println("\n===== APPLICANT SKILLS REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Applicants: " + applicants.getNumberOfEntries());
        System.out.println("Unique Skills: " + skillList.getNumberOfEntries());
        
        // Sort skills by count (bubble sort for simplicity)
        for (int i = 1; i <= skillList.getNumberOfEntries() - 1; i++) {
            for (int j = 1; j <= skillList.getNumberOfEntries() - i; j++) {
                if (skillCounts.getEntry(j) < skillCounts.getEntry(j + 1)) {
                    // Swap skill
                    String tempSkill = skillList.getEntry(j);
                    skillList.replace(j, skillList.getEntry(j + 1));
                    skillList.replace(j + 1, tempSkill);
                    
                    // Swap count
                    int tempCount = skillCounts.getEntry(j);
                    skillCounts.replace(j, skillCounts.getEntry(j + 1));
                    skillCounts.replace(j + 1, tempCount);
                }
            }
        }
        
        // Print top skills
        System.out.println("\n[ TOP SKILLS AMONG APPLICANTS ]");
        System.out.println("+----------------------+-------+----------------+");
        System.out.println("| Skill                | Count | % of Applicants|");
        System.out.println("+----------------------+-------+----------------+");
        
        int maxToShow = Math.min(10, skillList.getNumberOfEntries());
        for (int i = 1; i <= maxToShow; i++) {
            String skill = skillList.getEntry(i);
            int count = skillCounts.getEntry(i);
            double percentage = (double) count / applicants.getNumberOfEntries() * 100;
            
            System.out.printf("| %-20s | %5d | %14.1f%% |\n", 
                             truncate(skill, 20), count, percentage);
        }
        System.out.println("+----------------------+-------+----------------+");
    }
    
    /**
     * Generates a report on job type preferences among applicants
     */
    public void generateJobTypeReport() {
        ListInterface<Applicant> applicants = applicantControl.getApplicantList();
        ListInterface<String> jobTypes = new adt.LinkedList<>();
        ListInterface<Integer> jobTypeCounts = new adt.LinkedList<>();
        
        // Count job type preferences
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            String jobType = applicants.getEntry(i).getDesiredJobType().toLowerCase().trim();
            
            boolean found = false;
            for (int j = 1; j <= jobTypes.getNumberOfEntries(); j++) {
                if (jobTypes.getEntry(j).equals(jobType)) {
                    int count = jobTypeCounts.getEntry(j);
                    jobTypeCounts.replace(j, count + 1);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                jobTypes.add(jobType);
                jobTypeCounts.add(1);
            }
        }
        
        // Header
        System.out.println("\n===== JOB TYPE PREFERENCE REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Applicants: " + applicants.getNumberOfEntries());
        
        // Sort by count
        for (int i = 1; i <= jobTypes.getNumberOfEntries() - 1; i++) {
            for (int j = 1; j <= jobTypes.getNumberOfEntries() - i; j++) {
                if (jobTypeCounts.getEntry(j) < jobTypeCounts.getEntry(j + 1)) {
                    // Swap job type
                    String tempType = jobTypes.getEntry(j);
                    jobTypes.replace(j, jobTypes.getEntry(j + 1));
                    jobTypes.replace(j + 1, tempType);
                    
                    // Swap count
                    int tempCount = jobTypeCounts.getEntry(j);
                    jobTypeCounts.replace(j, jobTypeCounts.getEntry(j + 1));
                    jobTypeCounts.replace(j + 1, tempCount);
                }
            }
        }
        
        // Print job type distribution
        System.out.println("\n[ DESIRED JOB TYPE DISTRIBUTION ]");
        System.out.println("+----------------------+-------+----------------+");
        System.out.println("| Job Type             | Count | % of Applicants|");
        System.out.println("+----------------------+-------+----------------+");
        
        for (int i = 1; i <= jobTypes.getNumberOfEntries(); i++) {
            String jobType = jobTypes.getEntry(i);
            int count = jobTypeCounts.getEntry(i);
            double percentage = (double) count / applicants.getNumberOfEntries() * 100;
            
            System.out.printf("| %-20s | %5d | %14.1f%% |\n", 
                             truncate(jobType, 20), count, percentage);
        }
        System.out.println("+----------------------+-------+----------------+");
    }
    
    /**
     * Generates a report on geography distribution of applicants
     */
    public void generateLocationReport() {
        ListInterface<Applicant> applicants = applicantControl.getApplicantList();
        ListInterface<String> locations = new adt.LinkedList<>();
        ListInterface<Integer> locationCounts = new adt.LinkedList<>();
        
        // Count locations
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            String location = applicants.getEntry(i).getLocation().toLowerCase().trim();
            
            boolean found = false;
            for (int j = 1; j <= locations.getNumberOfEntries(); j++) {
                if (locations.getEntry(j).equals(location)) {
                    int count = locationCounts.getEntry(j);
                    locationCounts.replace(j, count + 1);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                locations.add(location);
                locationCounts.add(1);
            }
        }
        
        // Header
        System.out.println("\n===== APPLICANT LOCATION REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Applicants: " + applicants.getNumberOfEntries());
        System.out.println("Unique Locations: " + locations.getNumberOfEntries());
        
        // Sort by count
        for (int i = 1; i <= locations.getNumberOfEntries() - 1; i++) {
            for (int j = 1; j <= locations.getNumberOfEntries() - i; j++) {
                if (locationCounts.getEntry(j) < locationCounts.getEntry(j + 1)) {
                    // Swap location
                    String tempLoc = locations.getEntry(j);
                    locations.replace(j, locations.getEntry(j + 1));
                    locations.replace(j + 1, tempLoc);
                    
                    // Swap count
                    int tempCount = locationCounts.getEntry(j);
                    locationCounts.replace(j, locationCounts.getEntry(j + 1));
                    locationCounts.replace(j + 1, tempCount);
                }
            }
        }
        
        // Print location distribution
        System.out.println("\n[ GEOGRAPHICAL DISTRIBUTION ]");
        System.out.println("+----------------------+-------+----------------+");
        System.out.println("| Location             | Count | % of Applicants|");
        System.out.println("+----------------------+-------+----------------+");
        
        for (int i = 1; i <= locations.getNumberOfEntries(); i++) {
            String location = locations.getEntry(i);
            int count = locationCounts.getEntry(i);
            double percentage = (double) count / applicants.getNumberOfEntries() * 100;
            
            System.out.printf("| %-20s | %5d | %14.1f%% |\n", 
                             truncate(location, 20), count, percentage);
        }
        System.out.println("+----------------------+-------+----------------+");
    }
    
    /**
     * Generates a salary expectations report
     */
    public void generateSalaryReport() {
        ListInterface<Applicant> applicants = applicantControl.getApplicantList();
        
        if (applicants.isEmpty()) {
            System.out.println("\n===== SALARY EXPECTATION REPORT =====");
            System.out.println("No applicants in the system.");
            return;
        }
        
        // Calculate salary ranges
        int minSalary = Integer.MAX_VALUE;
        int maxSalary = 0;
        int totalSalary = 0;
        
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            int salary = applicants.getEntry(i).getExpectedSalary();
            minSalary = Math.min(minSalary, salary);
            maxSalary = Math.max(maxSalary, salary);
            totalSalary += salary;
        }
        
        double averageSalary = (double) totalSalary / applicants.getNumberOfEntries();
        
        // Define salary brackets
        int range = (maxSalary - minSalary) / 5 + 1; // +1 to avoid division by zero issues
        int[] bracketCounts = new int[6]; // 5 brackets + overflow
        
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            int salary = applicants.getEntry(i).getExpectedSalary();
            int bracket = Math.min(5, (salary - minSalary) / range);
            bracketCounts[bracket]++;
        }
        
        // Header
        System.out.println("\n===== SALARY EXPECTATION REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Applicants: " + applicants.getNumberOfEntries());
        System.out.printf("Average Expected Salary: RM%.2f\n", averageSalary);
        System.out.println("Minimum Salary: RM" + minSalary);
        System.out.println("Maximum Salary: RM" + maxSalary);
        
        // Print salary distribution
        System.out.println("\n[ SALARY RANGE DISTRIBUTION ]");
        System.out.println("+------------------------+-------+----------------+");
        System.out.println("| Salary Range (RM)      | Count | % of Applicants|");
        System.out.println("+------------------------+-------+----------------+");
        
        for (int i = 0; i < 5; i++) {
            int lowBound = minSalary + (i * range);
            int highBound = minSalary + ((i + 1) * range) - 1;
            if (i == 4) highBound = maxSalary; // Last bracket includes maximum
            
            double percentage = (double) bracketCounts[i] / applicants.getNumberOfEntries() * 100;
            
            System.out.printf("| RM%-21s | %5d | %14.1f%% |\n", 
                             lowBound + " - " + highBound, bracketCounts[i], percentage);
        }
        
        System.out.println("+------------------------+-------+----------------+");
    }
    
    /**
     * Generates an experience distribution report
     */
    public void generateExperienceReport() {
        ListInterface<Applicant> applicants = applicantControl.getApplicantList();
        
        if (applicants.isEmpty()) {
            System.out.println("\n===== EXPERIENCE DISTRIBUTION REPORT =====");
            System.out.println("No applicants in the system.");
            return;
        }
        
        // Calculate experience statistics
        int totalExp = 0;
        int maxExp = 0;
        int[] expRanges = new int[5]; // 0-1, 2-3, 4-5, 6-10, 11+
        
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            int exp = applicants.getEntry(i).getExperience();
            totalExp += exp;
            maxExp = Math.max(maxExp, exp);
            
            // Count in ranges
            if (exp <= 1) expRanges[0]++;
            else if (exp <= 3) expRanges[1]++;
            else if (exp <= 5) expRanges[2]++;
            else if (exp <= 10) expRanges[3]++;
            else expRanges[4]++;
        }
        
        double avgExp = (double) totalExp / applicants.getNumberOfEntries();
        
        // Header
        System.out.println("\n===== EXPERIENCE DISTRIBUTION REPORT =====");
        System.out.println("Generated: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("\nTotal Applicants: " + applicants.getNumberOfEntries());
        System.out.printf("Average Experience: %.1f years\n", avgExp);
        System.out.println("Maximum Experience: " + maxExp + " years");
        
        // Print experience distribution
        System.out.println("\n[ EXPERIENCE RANGE DISTRIBUTION ]");
        System.out.println("+------------------------+-------+----------------+");
        System.out.println("| Experience (years)     | Count | % of Applicants|");
        System.out.println("+------------------------+-------+----------------+");
        
        String[] rangeLabels = {"0-1 years", "2-3 years", "4-5 years", "6-10 years", "11+ years"};
        
        for (int i = 0; i < 5; i++) {
            double percentage = (double) expRanges[i] / applicants.getNumberOfEntries() * 100;
            System.out.printf("| %-22s | %5d | %14.1f%% |\n", 
                             rangeLabels[i], expRanges[i], percentage);
        }
        
        System.out.println("+------------------------+-------+----------------+");
    }
    
    // Helper methods
    private void printApplicantSummary(ListInterface<Applicant> applicants) {
        // Count by experience
        int beginners = 0, intermediate = 0, expert = 0;
        
        // Count by job type (simplified to most common types)
        int fulltime = 0, parttime = 0, internship = 0, contract = 0, other = 0;
        
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant app = applicants.getEntry(i);
            
            // Experience counts
            int exp = app.getExperience();
            if (exp < 2) beginners++;
            else if (exp < 5) intermediate++;
            else expert++;
            
            // Job type counts
            String jobType = app.getDesiredJobType().toLowerCase();
            if (jobType.contains("full") && jobType.contains("time")) fulltime++;
            else if (jobType.contains("part") && jobType.contains("time")) parttime++;
            else if (jobType.contains("intern")) internship++;
            else if (jobType.contains("contract")) contract++;
            else other++;
        }
        
        System.out.println("\n[ EXPERIENCE SUMMARY ]");
        System.out.println("Entry Level (0-1 years): " + beginners + " applicants");
        System.out.println("Intermediate (2-4 years): " + intermediate + " applicants");
        System.out.println("Expert (5+ years): " + expert + " applicants");
        
        System.out.println("\n[ JOB TYPE PREFERENCES ]");
        System.out.println("Full-time: " + fulltime + " applicants");
        System.out.println("Part-time: " + parttime + " applicants");
        System.out.println("Internship: " + internship + " applicants");
        System.out.println("Contract: " + contract + " applicants");
        System.out.println("Other: " + other + " applicants");
    }
    
    private void printApplicantDetails(ListInterface<Applicant> applicants) {
        System.out.println("\n[ DETAILED APPLICANT LISTING ]");
        System.out.println("+---------------+--------------+------------------+--------------------+-------+-------------+");
        System.out.println("| Name          | Location     | Job Type         | Skills             | Exp   | Salary(RM)  |");
        System.out.println("+---------------+--------------+------------------+--------------------+-------+-------------+");
        
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant app = applicants.getEntry(i);
            System.out.printf("| %-13s | %-12s | %-16s | %-18s | %5d | %11d |\n",
                             Atruncate(app.getName(), 13),
                             Atruncate(app.getLocation(), 12),
                             Atruncate(app.getDesiredJobType(), 16),
                             Atruncate(app.getSkills(), 18),
                             app.getExperience(),
                             app.getExpectedSalary());
        }
        
        System.out.println("+---------------+--------------+------------------+--------------------+-------+-------------+");
    }
    
    private String Atruncate(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength-3) + "..." : text;
    }

}