/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import control.SearchControl;
import entity.Applicant;
import entity.JobPosting;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class SearchUI {
    private final SearchControl searchControl;

    public SearchUI(SearchControl searchControl) {
        this.searchControl = searchControl;
    }

    public void start(Scanner scanner) {
        System.out.print("\nEnter search keyword: ");
        String keyword = scanner.nextLine();

        ListInterface<Applicant> applicantResults = searchControl.searchApplicants(keyword);
        ListInterface<JobPosting> jobResults = searchControl.searchJobs(keyword);

        System.out.println("\n=== Applicant Search Results ===");
        if (applicantResults.isEmpty()) {
            System.out.println("No matching applicants found.");
        } else {
            for (int i = 1; i <= applicantResults.getNumberOfEntries(); i++) {
                System.out.println(applicantResults.getEntry(i));
            }
        }

        System.out.println("\n=== Job Posting Search Results ===");
        if (jobResults.isEmpty()) {
            System.out.println("No matching job postings found.");
        } else {
            for (int i = 1; i <= jobResults.getNumberOfEntries(); i++) {
                System.out.println(jobResults.getEntry(i));
            }
        }
    }
}