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
import dao.ApplicantInitializer;
import dao.ScheduleInitializer;
import entity.Applicant;
import entity.Interview;
import entity.InterviewSlot;
import entity.Match;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ScheduleControl {
    ScheduleInitializer scheduleInitializer = new ScheduleInitializer();
    ApplicantInitializer applicantInitializer = new ApplicantInitializer();
    ListInterface<Applicant> applicants = applicantInitializer.initializeApplicants();
    
    private final ListInterface<Interview> pastInterviews = scheduleInitializer.initializePastInterviews(applicants);
    private final ListInterface<Interview> newInterviews = new LinkedList<>();
    private ListInterface<InterviewSlot> onlineSlots ;
    private ListInterface<InterviewSlot> physicalSlots;
    
    private String currentScheduleDate;

    
    public ScheduleControl() {
        initializeSlots();
    }

    
    private void initializeSlots() {
        onlineSlots = new LinkedList<>();
        physicalSlots = new LinkedList<>();

        String[] onlineTimes = {"9:00 AM", "11:00 AM", "2:00 PM", "4:00 PM"};
        String[] physicalTimes = {"8:00 AM", "10:00 AM", "1:00 PM", "3:00 PM"};

        for (String time : onlineTimes) onlineSlots.add(new InterviewSlot("", time));
        for (String time : physicalTimes) physicalSlots.add(new InterviewSlot("", time));
    }

    public void scheduleNewInterviews(ListInterface<Match> matches, Scanner scanner) {
        initializeSlots(); // Reset slot availability
        ListInterface<Match> newApplicants = filterNewApplicants(matches);

        if (newApplicants.isEmpty()) {
            System.out.println("No new qualified applicants found!");
            return;
        }

        showQualifiedApplicants(newApplicants);
        System.out.print("\nDo you want to schedule these applicants? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Scheduling cancelled.");
            return;
        }
        getValidScheduleDate(scanner);

        for (int i = 1; i <= newApplicants.getNumberOfEntries(); i++) {
            boolean scheduled = false;
            while (!scheduled) {
                scheduled = scheduleSingleInterview(newApplicants.getEntry(i).getApplicant(), scanner);
            }
        }

        confirmFinalSchedule(scanner);
    }

    
    private ListInterface<Match> filterNewApplicants(ListInterface<Match> matches) {
        ListInterface<Match> newApplicants = new LinkedList<>();

        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            Match match = matches.getEntry(i);
            Applicant applicant = match.getApplicant();
            boolean isScheduled = isScheduledInAnyInterview(applicant);
            if (match.getScore() > 50 &&
                !hasCompletedInterview(applicant) &&
                !applicant.isSuccessful()&&    
                !isScheduled) {
                newApplicants.add(match);
            }
        }
        return newApplicants;
    }
    
    private boolean isScheduledInAnyInterview(Applicant applicant) {
        for (int i = 1; i <= pastInterviews.getNumberOfEntries(); i++) {
            if (pastInterviews.getEntry(i).getApplicant().equals(applicant)) {
                return true;
            }
        }

        for (int i = 1; i <= newInterviews.getNumberOfEntries(); i++) {
            if (newInterviews.getEntry(i).getApplicant().equals(applicant)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCompletedInterview(Applicant applicant) {
        // Check if applicant has any completed interview (past date)
        for (int i = 1; i <= pastInterviews.getNumberOfEntries(); i++) {
            Interview interview = pastInterviews.getEntry(i);
            if (interview.getApplicant().equals(applicant) && 
                isPastDate(interview.getDate())) {
                return true;
            }
        }
        return false;
    }

    private void showQualifiedApplicants(ListInterface<Match> matches) {
        System.out.println("\n======= QUALIFIED APPLICANTS  (Score > 50) =======");
        System.out.println("+-----------------+---------------------------+-------+");
        System.out.println("| Name            | Desired Job               | Score |");
        System.out.println("+-----------------+---------------------------+-------+");

        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            Match match = matches.getEntry(i);
            Applicant applicant = match.getApplicant();

            System.out.printf("| %-15s | %-25s | %5d |\n",
                    applicant.getName(),
                    applicant.getDesiredJobType(),
                    match.getScore()
                    );
        }

        System.out.println("+-----------------+---------------------------+-------+");
    }

    private void getValidScheduleDate(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print("\nEnter schedule date (dd/mm/yyyy): ");
            String dateStr = scanner.nextLine();

            try {
                LocalDate inputDate = LocalDate.parse(dateStr, formatter);
                LocalDate today = LocalDate.now();

                if (inputDate.isBefore(today)) {
                    System.out.println("Cannot schedule interviews in the past!");
                    continue;
                }

                if (isDateUsed(dateStr)) {
                    System.out.println("This date has already been used for interviews.");
                    continue;
                }

                currentScheduleDate = dateStr;
                return;

            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/mm/yyyy");
            }
        }
        
    }

    private boolean isDateUsed(String date) {
        // Check past interviews
        for (int i = 1; i <= pastInterviews.getNumberOfEntries(); i++) {
            if (pastInterviews.getEntry(i).getDate().equals(date)) {
                return true;
            }
        }

        // Check new interviews
        for (int i = 1; i <= newInterviews.getNumberOfEntries(); i++) {
            if (newInterviews.getEntry(i).getDate().equals(date)) {
                return true;
            }
        }

        return false;
    }

    private boolean scheduleSingleInterview(Applicant applicant, Scanner scanner) {
        // Check if applicant already has completed interview
        if (hasCompletedInterview(applicant)) {
            System.out.println("!! " + applicant.getName() + " already has a completed interview !!");
            System.out.println("Cannot schedule again. Please select another applicant.");
            return false;
        }
    
        System.out.println("\n-> Scheduling interview for: " + applicant.getName() + " <-");
        System.out.println("Choose interview mode:");
        System.out.println("1. Online");
        System.out.println("2. Physical");
        System.out.print("Enter choice (1-2): ");
        int modeChoice = getIntInput(scanner, 1, 2);

        ListInterface<InterviewSlot> slots = (modeChoice == 1) ? onlineSlots : physicalSlots;

        System.out.println("\nAvailable Slots:");
        for (int i = 1; i <= slots.getNumberOfEntries(); i++) {
            System.out.println(i + ". " + slots.getEntry(i).getTime());
        }

        System.out.print("Enter slot number to book (1-4): ");
        int slotChoice = getIntInput(scanner, 1, 4);

        InterviewSlot selectedSlot = slots.getEntry(slotChoice);
        selectedSlot.setAvailable(false);

        newInterviews.add(new Interview(
                applicant,  
                (modeChoice == 1) ? "Online" : "Physical",
                currentScheduleDate,
                selectedSlot.getTime()
        ));

        System.out.println(" Interview scheduled successfully for " + selectedSlot.getTime());       
        System.out.println("----------------------------------------------------");
        return true;
    }

    private void confirmFinalSchedule(Scanner scanner) {
        boolean confirmed = false;

        while (!confirmed) {
            displayFullSchedule();

            System.out.print("\nConfirm schedule? (Y/N): ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("N")) {
                for (int i = 0; i < 5; i++) {
                    System.out.println();
                }

                // Show list of reschedulable applicants first
                System.out.println("Applicants available for rescheduling:");
                for (int i = 1; i <= newInterviews.getNumberOfEntries(); i++) {
                    System.out.println(i + ". " + newInterviews.getEntry(i).getApplicant().getName());
                }

                rescheduleApplicant(scanner);
                continue;
            }

            if (confirm.equalsIgnoreCase("Y")) {
                 System.out.print("Confirm date " + currentScheduleDate + "? (Y/N): ");  
                 String confirmDate = scanner.nextLine().trim();
                 
                 if(!confirmDate.equalsIgnoreCase("Y")){
                 String newDate;
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                while (true) {
                    System.out.print("Enter new schedule date (dd/mm/yyyy): ");
                    newDate = scanner.nextLine();

                    try {
                        LocalDate inputDate = LocalDate.parse(newDate, formatter);
                        if (inputDate.isBefore(LocalDate.now())) {
                            System.out.println("️ Cannot reschedule to a past date. Please choose a future date.");
                            continue;
                        }

                        if (isDateUsed(newDate)) {
                            System.out.println("️ This date has already been used. Please choose a different date.");
                            continue;
                        }

                        break; // Valid date
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Use dd/mm/yyyy.");
               }
            }

                // Update schedule date
                currentScheduleDate = newDate;
                for (int i = 1; i <= newInterviews.getNumberOfEntries(); i++) {
                    Interview interview = newInterviews.getEntry(i);
                    interview.setDate(newDate);
                }

                    System.out.println(" Interview date updated to: " + currentScheduleDate);
                    continue;
                 }

            // Finalize schedule
            while (!newInterviews.isEmpty()) {
                pastInterviews.add(newInterviews.remove(1));
            }

            System.out.println(" Schedule confirmed successfully! Returning to schedule menu...");
            confirmed = true;
            }
        } 
    } 
        

private void rescheduleApplicant(Scanner scanner) {
    System.out.print("\nEnter applicant full name to reschedule (or 'cancel' to go back): ");
    String name = scanner.nextLine().trim();
    
    if (name.equalsIgnoreCase("cancel")) {
        return;
    }

    // Find the interview to reschedule
    for (int i = 1; i <= newInterviews.getNumberOfEntries(); i++) {
        Interview interview = newInterviews.getEntry(i);
        if (interview.getApplicant().getName().trim().equalsIgnoreCase(name)) {
            // Free the booked slot
            releaseSlot(interview.getTime(), interview.getMode());

            // Remove the old interview
            newInterviews.remove(i);

            // Schedule new interview
            System.out.println("\nRescheduling interview for: " + name);
            boolean scheduled = false;
            while (!scheduled) {
                scheduled = scheduleSingleInterview(interview.getApplicant(), scanner);
            }
            return; // Exit after successful rescheduling
        }
    }

    System.out.println("\n* Applicant '" + name + "' not found in upcoming interviews *");
    System.out.println("* Please enter the exact name as shown in the schedule *");
}

    private void releaseSlot(String time, String mode) {
        ListInterface<InterviewSlot> slots = mode.equals("Online") ? onlineSlots : physicalSlots;
        for (int i = 1; i <= slots.getNumberOfEntries(); i++) {
            InterviewSlot slot = slots.getEntry(i);
            if (slot.getTime().equals(time)) {
                slot.setAvailable(true);
                break;
            }
        }
    }

    public void displayFullSchedule() {
        System.out.println("\n===== FULL INTERVIEW SCHEDULE =====");

        if (pastInterviews.isEmpty() && newInterviews.isEmpty()) {
            System.out.println("No interviews scheduled yet.");
            return;
        }

        displayInterviewGroup(pastInterviews, "Completed Interviews");
        displayInterviewGroup(newInterviews, "Upcoming Interviews");
    }

    
    private void displayInterviewGroup(ListInterface<Interview> interviews, String title) {
        if (interviews.isEmpty()) return;

        System.out.println("\n" + title + ":");
        String currentDate = "";

        for (int i = 1; i <= interviews.getNumberOfEntries(); i++) {
            Interview interview = interviews.getEntry(i);

            if (!interview.getDate().equals(currentDate)) {
                currentDate = interview.getDate();
                System.out.println("\nDate: " + currentDate);
System.out.println("+-----------------+------------+----------+------------+");
System.out.println("| Applicant       | Mode       | Time     | Status     |");
System.out.println("+-----------------+------------+----------+------------+");
            }

        String status = interview.getApplicant().isSuccessful() ? "HIRED" : 
                    (isPastDate(interview.getDate()) ? "COMPLETED" : "UPCOMING");
            System.out.printf("| %-15s | %-10s | %-8s | %-10s\n",
                    interview.getApplicant().getName(),
                    interview.getMode(),
                    interview.getTime(),
                    status);
        }

        System.out.println("+-----------------+------------+----------+------------");
    }
    
    private boolean isPastDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate interviewDate = LocalDate.parse(dateStr, formatter);
            return interviewDate.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
    
    private int getIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) return input;
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    
    // Optional Getters (for reporting)
    public ListInterface<Interview> getPastInterviews() {
        return pastInterviews;
    }

    
    public String getCurrentScheduleDate() {
        return currentScheduleDate;
    }
  
    
    public void hireApplicants(Scanner scanner) {
        if (pastInterviews.isEmpty()) {
            System.out.println("No completed interviews available to hire.");
            return;
        }

        System.out.println("\n===== HIRE APPLICANTS =====");
        boolean hasDisplay = false; // before  displaying the applicant list, filter out those who are already hired
        
        for (int i = 1; i <= pastInterviews.getNumberOfEntries(); i++) {
            Interview interview = pastInterviews.getEntry(i);

            if (!interview.getApplicant().isSuccessful()) {
                hasDisplay = true;
                System.out.printf("%d. %s (%s at %s, Date: %s)\n",
                    i,
                    interview.getApplicant().getName(),
                    interview.getMode(),
                    interview.getTime(),
                    interview.getDate()
                );
            }
        }

        
        if (!hasDisplay) {
            System.out.println("All applicants have already been hired.");
            return;
        }

        System.out.print("Enter number(s) of applicants to hire (comma-separated) [0= default] : ");
        String input = scanner.nextLine();
        String[] parts = input.split(",");

        ListInterface<Integer> indicesToRemove = new adt.LinkedList<>();

        for (String part : parts) {
            try {
                int index = Integer.parseInt(part.trim());
                if (index >= 1 && index <= pastInterviews.getNumberOfEntries()) {                    
                    Interview hired = pastInterviews.getEntry(index);
                    hired.getApplicant().setSuccessful(true);
                    // Do NOT remove them from the list
                } else {
                    System.out.println("Invalid index: " + part.trim());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + part.trim());
            }
        }

        // Delete hired applicants from pastInterviews (must remove in reverse order)
        for (int i = indicesToRemove.getNumberOfEntries(); i >= 1; i--) {
            int removeIndex = indicesToRemove.getEntry(i);
            pastInterviews.remove(removeIndex);
        }

        System.out.println(" Selected applicants marked as hired");
    }
    

    public ListInterface<Interview> getNewInterviews() {
        return newInterviews;
    }

}
