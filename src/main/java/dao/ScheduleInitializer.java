/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import adt.LinkedList;
import adt.ListInterface;
import entity.Applicant;
import entity.Interview;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleInitializer {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListInterface<Interview> initializePastInterviews(ListInterface<Applicant> applicants ) {
        ListInterface<Interview> pastInterviews = new LinkedList<>();

        // Get yesterday's date
        String pastDate = LocalDate.now().minusDays(1).format(DATE_FORMAT);
        
        // Create some past interviews (completed)
        pastInterviews.add(new Interview( applicants.getEntry(1), // Alice Tan 
            "Online",
            pastDate,
            "9:00 AM"
        ));
        
        pastInterviews.add(new Interview(applicants.getEntry(2), // Bob Lee
            "Physical",
            pastDate,
            "10:00 AM"
        ));
        
        return pastInterviews;
    }
}