/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author User
 */
import control.ScheduleControl;
import control.MatchingControl;
import control.ApplicantManagement;
import control.JobManagement;
import java.util.Scanner;

public class ScheduleUI {
    private final ScheduleControl scheduleControl;
    private MatchingControl matchingControl;  // Changed to non-final since we initialize it later
    private final Scanner scanner;
    private final ApplicantManagement applicantControl;
    private final JobManagement jobControl;

    public ScheduleUI(ScheduleControl scheduleControl, JobManagement jobControl, ApplicantManagement applicantControl ) {
        this.scheduleControl = scheduleControl;
        this.scanner = new Scanner(System.in);
        this.jobControl = jobControl;
        this.applicantControl = applicantControl;
        
        // Initialize matching control with required dependencies
        initializeMatchingControl();
    }

    private void initializeMatchingControl() {
        
        // Initialize with empty match list and required controllers
        this.matchingControl = new MatchingControl(
            new adt.LinkedList<>(), 
            applicantControl,
            jobControl,
            this.scheduleControl  // Pass the scheduleControl reference
        );
    }

    public void showScheduleMenu() {
        System.out.println("\n===== INTERVIEW SCHEDULING =====");
        System.out.println("1. Schedule Interviews (scores > 50)");
        System.out.println("2. View Schedule");
        System.out.println("3. Hire Applicants");
        System.out.println("4. Back to Main");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                scheduleNewInterviews();
                showScheduleMenu();
                break;
            case 2:
                scheduleControl.displayFullSchedule();
                showScheduleMenu();
                break;
            case 3: 
                scheduleControl.hireApplicants(scanner);
                showScheduleMenu();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void scheduleNewInterviews() {
        // Find matches (this will now automatically exclude completed applicants)
        matchingControl.findMatches();
        
        // Get filtered matches (only qualified applicants without completed interviews)
        scheduleControl.scheduleNewInterviews(matchingControl.getMatches(), scanner);
    }
    
}
