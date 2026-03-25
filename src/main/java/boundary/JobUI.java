/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.LinkedList;
import adt.ListInterface;
import control.JobManagement;
import control.ApplicantManagement;
import entity.JobPosting;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class JobUI {
    private final JobManagement control;
    private final ApplicantManagement aControl;
    private ListInterface<JobPosting> jobs = new LinkedList<>();
    private final Scanner scanner;

    public JobUI(JobManagement control,ApplicantManagement aControl, ListInterface<JobPosting> jobs ) {
        this.control = control;
        this.aControl = aControl;
        this.scanner = new Scanner(System.in);
        this.jobs =jobs;
    }

    public void showJobMenu() {
        int choice;
        do {
            System.out.println("\n===== JOB MANAGEMENT =====");
            System.out.println("1. Add Job");
            System.out.println("2. Filter Jobs by criteria");
            System.out.println("3. Update Details");
            System.out.println("4. Remove Job");
            System.out.println("5. View All Jobs");
            System.out.println("6. View All Applicants");
            System.out.println("7. Sort Applicants for a Job");
            System.out.println("8. Back to Main");
            System.out.print("Enter choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addJob(); break;
                case 2: filterJobs();break;
                case 3: updateJob(); break;
                case 4: removeJob(); break;
                case 5: control.displayAllJobs(); break;
                case 6: aControl.displayAllApplicants(); break;
                case 7: sortApplicantsForJob(); break;
                case 8: return;
                default: System.out.println("Invalid choice!");
            }
        } while (true);
    }
    
       private void sortApplicantsForJob() {
        System.out.println("\n=== Sorting Applicants ===");
        System.out.print("Enter job title : ");
        String jobTitle = scanner.nextLine();

        System.out.println("\n=== Applicants Matching for \"" + jobTitle + "\" ===");
        System.out.println("Sort by:");
        System.out.println("1. Experience (High to Low)");
        System.out.println("2. Expected Salary (Low to High)");
        System.out.println("3. Skill Match (Best Match First)");
        System.out.print("Enter sort choice: ");

        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        aControl.sortApplicantsForJob(jobTitle, sortChoice);
    }
    

    private void addJob() {
       System.out.print("Enter company name: ");
       String company = scanner.nextLine().trim();
       while (company.isEmpty()) {
           System.out.print("Company name cannot be empty. Enter again: ");
           company = scanner.nextLine().trim();
       }

       int minSalary;
       while (true) {
           System.out.print("Enter minimum salary (RM): ");
           String input = scanner.nextLine();
           try {
               minSalary = Integer.parseInt(input);
               break;
           } catch (NumberFormatException e) {
               System.out.println("Invalid salary. Please enter a number.");
           }
       }

       int maxSalary;
       while (true) {
           System.out.print("Enter maximum salary (RM): ");
           String input = scanner.nextLine();
           try {
               maxSalary = Integer.parseInt(input);
               if (maxSalary >= minSalary) break;
               else System.out.println("Maximum salary must be greater than or equal to minimum salary.");
           } catch (NumberFormatException e) {
               System.out.println("Invalid salary. Please enter a number.");
           }
       }

       System.out.print("Enter job title: ");
       String title = scanner.nextLine().trim();
       while (title.isEmpty()) {
           System.out.print("Job title cannot be empty. Enter again: ");
           title = scanner.nextLine().trim();
       }

       System.out.print("Enter required skills (comma-separated): ");
       String skills = scanner.nextLine().trim();
       while (skills.isEmpty()) {
           System.out.print("Required skills cannot be empty. Enter again: ");
           skills = scanner.nextLine().trim();
       }

       System.out.print("Enter priority skills (comma-separated): ");
       String prioritySkills = scanner.nextLine().trim();
       while (prioritySkills.isEmpty()) {
           System.out.print("Priority skills cannot be empty. Enter again: ");
           prioritySkills = scanner.nextLine().trim();
       }

       int exp;
       while (true) {
           System.out.print("Enter minimum experience (years): ");
           String input = scanner.nextLine();
           try {
               exp = Integer.parseInt(input);
               break;
           } catch (NumberFormatException e) {
               System.out.println("Invalid input. Enter a number.");
           }
       }

       System.out.print("Enter location (state, region): ");
       String location = scanner.nextLine().trim();
       while (location.isEmpty()) {
           System.out.print("Location cannot be empty. Enter again: ");
           location = scanner.nextLine().trim();
       }

       boolean success = control.addJob(title, skills, exp, location, prioritySkills, company, minSalary, maxSalary);
       System.out.println(success ? " Job added successfully!" : " Failed to add job");
   }

    
    private void updateJob() {
        for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
           System.out.println(i + ". " + jobs.getEntry(i));
       }

       int pos = -1;
       while (true) {
           System.out.print("Enter position to update (0 = none): ");
           try {
               pos = Integer.parseInt(scanner.nextLine());
               if (pos < 0 || pos > jobs.getNumberOfEntries()) {
                   System.out.println("Invalid position. Try again.");
                   continue;
               }
               break;
           } catch (NumberFormatException e) {
               System.out.println("Please enter a valid number.");
           }
       }

       if (pos == 0) return;

       JobPosting current = control.getJobAt(pos);
       if (current == null) {
           System.out.println("No job at that position");
           return;
       }

       System.out.println ("Enter new details (leave blank to keep current)");
       System.out.print("New Title [" + current.getTitle() + "]: ");
       String title = scanner.nextLine();
       if (title.isEmpty()) title = current.getTitle();

       System.out.print("New Required Skills [" + current.getRequiredSkills() + "]: ");
       String skills = scanner.nextLine();
       if (skills.isEmpty()) skills = current.getRequiredSkills();

       int exp = current.getMinExperience();
       while (true) {
           System.out.print("New Min Experience [" + exp + "]: ");
           String input = scanner.nextLine();
           if (input.isEmpty()) break;
           try {
               exp = Integer.parseInt(input);
               if (exp < 0) {
                   System.out.println("Experience cannot be negative.");
                   continue;
               }
               break;
           } catch (NumberFormatException e) {
               System.out.println("Please enter a valid number.");
           }
       }

       System.out.print("New Location [" + current.getLocation() + "]: ");
       String location = scanner.nextLine();
       if (location.isEmpty()) location = current.getLocation();

       System.out.print("New Priority Skills [" + current.getPrioritySkills() + "]: ");
       String priority = scanner.nextLine();
       priority = priority.isEmpty() ? current.getPrioritySkills() :priority;

       System.out.print("New Company [" + current.getCompany() + "]: ");
       String company = scanner.nextLine();
       if (company.isEmpty()) company = current.getCompany();

       int minSalary = current.getMinSalary();
       while (true) {
           System.out.print("New Min Salary [" + minSalary + "]: ");
           String input = scanner.nextLine();
           if (input.isEmpty()) break;
           try {
               minSalary = Integer.parseInt(input);
               if (minSalary < 0) {
                   System.out.println("Salary cannot be negative.");
                   continue;
               }
               break;
           } catch (NumberFormatException e) {
               System.out.println("Please enter a valid number.");
           }
       }

       int maxSalary = current.getMaxSalary();
       while (true) {
           System.out.print("New Max Salary [" + maxSalary + "]: ");
           String input = scanner.nextLine();
           if (input.isEmpty()) break;
           try {
               maxSalary = Integer.parseInt(input);
               if (maxSalary < minSalary) {
                   System.out.println("Max salary must be >= min salary.");
                   continue;
               }
               break;
           } catch (NumberFormatException e) {
               System.out.println("Please enter a valid number.");
           }
       }

       JobPosting updated = new JobPosting(title, skills, exp, location, priority, company, minSalary, maxSalary);
       control.updateJob(pos, updated);

       System.out.println("Job updated successfully.");
       for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
           System.out.println("\n"+i + ". " + jobs.getEntry(i));
       }
           }
    
    private void removeJob() {        
        for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
           System.out.println(i + ". " + jobs.getEntry(i));
       }
        System.out.print("Enter position to remove: ");
        int pos = scanner.nextInt(); scanner.nextLine();
        JobPosting job = control.getJobAt(pos);
        if (job == null) {
            System.out.println(" Invalid position.");
            return;
        }

        System.out.println("Are you sure you want to remove this job?");
        System.out.println("→ " + job.getTitle());
        System.out.print("Enter y to confirm: ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean success = control.removeJob(pos);
            System.out.println(success ? " Job removed successfully!" : " Failed to remove job.");
        } else {
            System.out.println(" Operation cancelled.");
        }
    }
    
    public void filterJobs() {
        System.out.println("\n=== Filter Jobs ===");
        System.out.println("1. Enter job title");
        System.out.println("2. Enter location");
        System.out.println("3. Enter required skill");
        System.out.println("4. Enter minimum experience");
        System.out.println("5. Enter company name");
        System.out.println("6. Enter minimum salary");
        System.out.println("7. Enter maximum salary");
        System.out.println("8. Enter details to Filter jobs ");
        System.out.print("Enter your choice to filter by [0 - default]: ");

        int filterChoice = scanner.nextInt();
        scanner.nextLine();

        String title = "", location = "", skill = "", company = "";
        int exp = 0, minSalary = 0, maxSalary = 0, sortOption = 0;

        switch (filterChoice) {
            case 1:
                System.out.print("Enter job title keyword: ");
                title = scanner.nextLine();
                sortOption = 3; // sort by title
                break;
            case 2:
                System.out.print("Enter location keyword: ");
                location = scanner.nextLine();
                sortOption = 2; // sort by location
                break;
            case 3:
                System.out.print("Enter required skill keyword: ");
                skill = scanner.nextLine();
                sortOption = 6;
                break;
            case 4:
                System.out.print("Enter minimum experience: ");
                exp = scanner.nextInt(); scanner.nextLine();
                sortOption = 4;
                break;
            case 5:
                System.out.print("Enter company name keyword: ");
                company = scanner.nextLine();
                break;
            case 6:
                System.out.print("Enter minimum salary: ");
                minSalary = scanner.nextInt(); scanner.nextLine();
                sortOption = 1; 
                break;
            case 7:
                System.out.print("Enter maximum salary: ");
                maxSalary = scanner.nextInt(); scanner.nextLine();
                sortOption = 5;
                break;
            case 8:
                System.out.print("Enter job title (or leave blank): ");
                title = scanner.nextLine();
                System.out.print("Enter location (or leave blank): ");
                location = scanner.nextLine();
                System.out.print("Enter required skill (or leave blank): ");
                skill = scanner.nextLine();
                System.out.print("Enter minimum experience (0 for any): ");
                try {
                    exp = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    exp = 0;
                }
                System.out.print("Enter company name (or leave blank): ");
                company = scanner.nextLine();
                System.out.print("Enter minimum salary (0 for any): ");
                try {
                    minSalary = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    minSalary = 0;
                }
                System.out.print("Enter maximum salary (0 for any): ");
                try {
                    maxSalary = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    maxSalary = 0;
                }

                // 自动决定排序方式：优先 salary > location > title（你也可以换规则）
                 if (minSalary > 0) {
                    sortOption = 1;
                } else if (maxSalary > 0) {
                    sortOption = 5;
                } else if (exp > 0) {
                    sortOption = 4;
                } else if (!skill.isEmpty()) {
                    sortOption = 6;
                } else if (!location.isEmpty()) {
                    sortOption = 2;
                } else if (!title.isEmpty()) {
                    sortOption = 3;
                }
                break;  
            default:
                System.out.println("Invalid option.");
                return;
        }

        control.filterAndSortJobs(title, location, skill, exp, company, minSalary, maxSalary, sortOption);
    }
}



