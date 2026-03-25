/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.LinkedList;
import adt.ListInterface;
import control.ApplicantManagement;
import control.JobManagement;
import control.MatchingControl;
import control.ReportControl;
import control.ScheduleControl;
import control.SearchControl;
import dao.ApplicantInitializer;
import dao.JobInitializer;
import entity.Applicant;
import entity.JobPosting;
import entity.Match;

import java.util.Scanner;


public class InternShipSystem {
    public static void main(String[] args) {
        // Initialize ADTs (linkedlist)   //need to import them because linkedlist <T> is just generic,dunno T is which one
        ListInterface<Applicant> applicantList = new ApplicantInitializer().initializeApplicants();
        ListInterface<JobPosting> jobList = new JobInitializer().initializeJobs();
        ListInterface<Match> matchList = new LinkedList<>();     

        
        // Initialize Controls
        ApplicantManagement applicantControl = new ApplicantManagement(applicantList);
        JobManagement jobControl = new JobManagement(jobList);
        SearchControl searchControl = new SearchControl(applicantList, jobList);       
        ScheduleControl scheduleControl = new ScheduleControl();       
        MatchingControl matchingControl = new MatchingControl(matchList, applicantControl, jobControl, scheduleControl);
        ReportControl reportControl = new ReportControl(matchingControl, applicantControl);
               
        // Initialize UIs
        SearchUI searchUI = new SearchUI(searchControl);
        ReportUI reportUI = new ReportUI(reportControl, scheduleControl);
        JobUI jobUI = new JobUI(jobControl,applicantControl, jobList);
        ApplicantUI applicantUI = new ApplicantUI(applicantControl, jobControl, applicantList, searchUI, jobUI);
        ScheduleUI scheduleUI = new ScheduleUI(scheduleControl,jobControl,applicantControl );
        

        
        
        // Start application     
        
        Scanner scanner = new Scanner(System.in);
             while (true) {          
                    System.out.println("\n\n======= INTERNSHIP APPLICANTION SYSTEM =======");
                    System.out.println("\n******** MAIN MENU ********");
                    System.out.println("* 1. Applicant Management  *") ;
                    System.out.println("* 2. Job Management        *");
                    System.out.println("* 3. View Matches          *");
                    System.out.println("* 4. Interview Scheduling  *");
                    System.out.println("* 5. Report                *");       
                    System.out.println("* 6. Exit                  *");
                    System.out.println("****************************");
                    System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            
        switch (choice) {
                case 1: applicantUI.showApplicantMenu();
                break;
                
                case 2: jobUI.showJobMenu();
                break;
                
                
                case 3: 
                    matchingControl.findMatches();
                    matchingControl.displayAllMatches();
                    break;
                                    
                case 4: 
                    scheduleUI.showScheduleMenu();
                    break;
                    
                case 5: 
                    reportUI.showReportMenu(matchingControl.getMatches(), scheduleControl.getPastInterviews(),jobControl.getJobList(), scanner);
                    break;
                        
                case 6: System.exit(0);
                default: System.out.println("Invalid choice!");
        }
             } 
    }
}
