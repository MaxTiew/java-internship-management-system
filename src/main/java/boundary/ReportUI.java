/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import control.ReportControl;
import control.ScheduleControl;
import entity.Interview;
import entity.JobPosting;
import entity.Match;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ReportUI {
    private final ReportControl reportControl;
    private final ScheduleControl scheduleControl;
    private final Scanner scanner;

    public ReportUI(ReportControl reportControl, ScheduleControl scheduleControl) {
        this.reportControl = reportControl;
        this.scheduleControl = scheduleControl;  //system expect 2 parameter then main should have 2 instance
        this.scanner = new Scanner(System.in);
    }
    

    public void showReportMenu(ListInterface<Match> matches, ListInterface<Interview> pastInterviews,ListInterface<JobPosting> jobList, Scanner scanner) {
        while (true) {
            System.out.println("\n===== REPORT MENU =====");
            System.out.println("1. View Match Report");
            System.out.println("2. View Schedule Report");
            System.out.println("3. View Job Report");
            System.out.println("4. View Applicant Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    reportControl.generateMatchReport(matches);
                    break;
                
                case 2:
                    reportControl.generateInterviewReport(
                        scheduleControl.getPastInterviews(),
                        scheduleControl.getNewInterviews()  // add this getter
                    );
                    break;
                
                case 3:
                    reportControl.generateJobSummaryGrid(jobList);
                    break;    
                case 4:
                    showApplicantReportMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    
    

    
    public void showApplicantReportMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n===== APPLICANT REPORT MENU =====");
            System.out.println("1. General Applicant Report");
            System.out.println("2. Filtered Applicant Report");
            System.out.println("3. Skills Distribution Report");
            System.out.println("4. Job Type Preference Report");
            System.out.println("5. Geographical Distribution Report");
            System.out.println("6. Salary Expectations Report");
            System.out.println("7. Experience Distribution Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    reportControl.generateGeneralReport();
                    break;
                    
                case 2:
                    showFilteredReportMenu();
                    break;
                    
                case 3:
                    reportControl.generateSkillsReport();
                    break;
                    
                case 4:
                    reportControl.generateJobTypeReport();
                    break;
                    
                case 5:
                    reportControl.generateLocationReport();
                    break;
                    
                case 6:
                    reportControl.generateSalaryReport();
                    break;
                    
                case 7:
                    reportControl.generateExperienceReport();
                    break;
                    
                case 0:
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running && choice >= 1 && choice <= 7) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    private void showFilteredReportMenu() {
        System.out.println("\n===== FILTERED APPLICANT REPORT =====");
        System.out.println("Enter filter criteria (leave blank to ignore):");
        
        System.out.print("Location contains: ");
        String location = scanner.nextLine().trim();
        
        System.out.print("Job Type contains: ");
        String jobType = scanner.nextLine().trim();
        
        System.out.print("Skills contains: ");
        String skills = scanner.nextLine().trim();
        
        System.out.print("Minimum Experience (years, 0 for any): ");
        int minExp = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Maximum Expected Salary (RM, 0 for any): ");
        int maxSalary = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        reportControl.generateFilteredReport(location, jobType, skills, minExp, maxSalary);
    }
}

