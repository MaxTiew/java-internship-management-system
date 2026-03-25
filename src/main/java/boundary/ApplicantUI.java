/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
package boundary;

import adt.LinkedList;
import adt.ListInterface;
import control.ApplicantManagement;
import control.JobManagement;
import entity.Applicant;
import java.util.Scanner;

public class ApplicantUI {
    private final ApplicantManagement manager;
    private final JobManagement jManager;
    private ListInterface<Applicant> applicants = new LinkedList<>();
    private final SearchUI searchUI;
    private final JobUI jobUI;
    private final Scanner scanner;

    public ApplicantUI(ApplicantManagement manager, JobManagement jManager, ListInterface<Applicant> applicants, SearchUI searchUI, JobUI jobUI) {
        this.manager = manager;
        this.jManager = jManager;
        this.applicants = applicants;
        this.searchUI = searchUI;
        this.jobUI = jobUI;
        this.scanner = new Scanner(System.in);
    }

    public void showApplicantMenu() {
        int choice;
        do {
            System.out.println("\n===== APPLICANT MANAGEMENT =====");
            System.out.println("1. Add Applicant");
            System.out.println("2. Filter Jobs for Applicant");
            System.out.println("3. Search Jobs and Applicants");
            System.out.println("4. Update Details");
            System.out.println("5. Remove Applicant");
            System.out.println("6. View All Jobs");
            System.out.println("7. Back to Main");
            System.out.print("Enter choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addApplicant(); break;             
                case 2: jobUI.filterJobs(); break; 
                case 3: searchUI.start(scanner);
                            break;   
                case 4: updateApplicant(); break;
                case 5: removeApplicant(); break;
                case 6: jManager.displayAllJobs(); break; 
                case 7: return;
                default: System.out.println("Invalid choice!");
            }
        } while (true);
    }

    private void addApplicant() {
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter applicant name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            }
        }
        
        String email;
        while (true) {
            System.out.print("Email (must include @gmail.com): ");
            email = scanner.nextLine();
            if (email.contains("@gmail.com")) break;
            System.out.println("Invalid email format.");
        }
         
        int phone;
        while (true) {
            System.out.print("Phone (+60): ");
            String phoneStr = scanner.nextLine();
            if (phoneStr.matches("\\d{9}")) {
                phone = Integer.parseInt(phoneStr);
                break;
            }
            System.out.println("Invalid phone number.");
        }
        
        String skills = "";
        while (skills.isEmpty()) {
            System.out.print("Enter applicant skills (comma-separated): ");
            skills = scanner.nextLine().trim();
            if (skills.isEmpty()) {
                System.out.println("Skills cannot be empty. Please try again.");
            }
        }

        int exp;
        while (true) {
            System.out.print("Experience (years): ");
            String expStr = scanner.nextLine();
            try {
                exp = Integer.parseInt(expStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        String location = "";
        while (location.isEmpty()) {
            System.out.print("Enter location (State, Area): ");
            location = scanner.nextLine().trim();
            if (location.isEmpty()) {
                System.out.println("Location cannot be empty. Please try again.");
            }
        }

        System.out.print("Desired Job Type: ");
        String jobType = scanner.nextLine();      
        
        int expectedSalary;
        while (true) {
            System.out.print("Enter your expected salary (RM): ");
            String salaryStr = scanner.nextLine();
            try {
                expectedSalary = Integer.parseInt(salaryStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        boolean success = manager.createApplicant(name, email, phone, skills, exp, location, jobType, expectedSalary);
        System.out.println(success ? " Applicant added!" : " Failed to add applicant");
    }

private void updateApplicant() {
    for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
        System.out.println(i + ". " + applicants.getEntry(i));
    }

    System.out.print("Enter position to update (0 for none) : ");
    int pos = scanner.nextInt(); scanner.nextLine();

    Applicant current = manager.getApplicant(pos);
    if (current == null) {
        System.out.println(" No applicant at that position");
        return;
    }

    System.out.println("Current: " + current);
    System.out.println("Enter new details (leave blank to keep current):");

    System.out.print("Name (" + current.getName() + "): ");
    String name = scanner.nextLine();
    name = name.isEmpty() ? current.getName() : name;

    String email;
    while (true) {
        System.out.print("Email (" + current.getEmail() + "): ");
        email = scanner.nextLine();
        if (email.isEmpty()) {
            email = current.getEmail();
            break;
        } else if (email.contains("@gmail.com")) break;
        else System.out.println("Email must include @gmail.com.");
    }

    int phone;
    while (true) {
        System.out.print("Phone (" + current.getPhone() + "): ");
        String phoneStr = scanner.nextLine();
        if (phoneStr.isEmpty()) {
            phone = current.getPhone();
            break;
        } else if (phoneStr.matches("\\d{9}")) {
            phone = Integer.parseInt(phoneStr);
            break;
        } else {
            System.out.println("Phone must be 9 digits.");
        }
    }

    System.out.print("Skills (" + current.getSkills() + "): ");
    String skills = scanner.nextLine();
    skills = skills.isEmpty() ? current.getSkills() : skills;

    int exp;
    while (true) {
        System.out.print("Experience (" + current.getExperience() + "): ");
        String expStr = scanner.nextLine();
        if (expStr.isEmpty()) {
            exp = current.getExperience();
            break;
        }
        try {
            exp = Integer.parseInt(expStr);
            break;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    System.out.print("Location (" + current.getLocation() + "): ");
    String loc = scanner.nextLine();
    loc = loc.isEmpty() ? current.getLocation() : loc;

    System.out.print("Job Type (" + current.getDesiredJobType() + "): ");
    String job = scanner.nextLine();
    job = job.isEmpty() ? current.getDesiredJobType() : job;

    int expSalary;
    while (true) {
        System.out.print("Expected Salary (RM " + current.getExpectedSalary() + "): ");
        String salaryStr = scanner.nextLine();
        if (salaryStr.isEmpty()) {
            expSalary = current.getExpectedSalary();
            break;
        }
        try {
            expSalary = Integer.parseInt(salaryStr);
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    Applicant updated = new Applicant(name, email, phone, skills, exp, loc, job, expSalary);
    boolean success = manager.updateApplicant(pos, updated);
    for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
        System.out.println("\n"+ i + ". " + applicants.getEntry(i));
    }
    System.out.println(success ? " \nApplicant updated" : " \nUpdate failed");
}

    private void removeApplicant() {
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
        System.out.println(i + ". " + applicants.getEntry(i));
        }
            System.out.print("Enter position to remove: ");
            int pos = scanner.nextInt(); 
            scanner.nextLine();
        Applicant toRemove = manager.getApplicant(pos);
        if (toRemove == null) {
            System.out.println("Invalid position.");
            return;
        }

        System.out.println("Are you sure you want to remove this applicant?");
        System.out.println("-> " + toRemove.getName());
        System.out.print("Enter Y to confirm: ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean success = manager.removeApplicant(pos);
            System.out.println(success ? " Applicant removed." : " Removal failed.");
        } else {
            System.out.println(" Operation cancelled.");
        }
      }

        private void filterJobsForApplicant() {
        System.out.println("\n=== Filter Jobs As Applicant ===");

        System.out.print("Enter preferred location [state, area] (or leave blank): ");
        String location = scanner.nextLine();

        System.out.print("Enter company keyword (or leave blank): ");
        String company = scanner.nextLine();

        System.out.print("Enter desired job title (or leave blank): ");
        String jobType = scanner.nextLine();

        System.out.print("Enter minimum expected salary (0 for any): ");
        int minSalary = 0;
        try {
            minSalary = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using 0 as default.");
        }

        System.out.print("Enter maximum expected salary (0 for any): ");
        int maxSalary = 0;
        try {
            maxSalary = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using 0 as default.");
        }
        
        System.out.print("Enter skill keyword (or leave blank): ");
        String skill = scanner.nextLine();


    }
}