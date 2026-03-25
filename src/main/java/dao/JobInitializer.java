/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import adt.ListInterface;
import adt.LinkedList;
import entity.JobPosting;

public class JobInitializer {

    public ListInterface<JobPosting> initializeJobs() {
        ListInterface<JobPosting> jobs = new LinkedList<>();

        jobs.add(new JobPosting("Software Engineer ", "Java, C++, Dsa       ", 2, "KL, Bangsar          ", "Java      ", "TechNova   ", 2000, 4000));
        jobs.add(new JobPosting("Data Analyst      ", "Python, SQL          ", 1, "Penang, Bayan Lepas  ", "Python    ", "DataNest   ", 4500, 6000));
        jobs.add(new JobPosting("Web Developer     ", "HTML, CSS, JavaScript", 1, "Selangor, Shah Alam  ", "JavaScript", "Webify     " , 2500, 4200));
        jobs.add(new JobPosting("UX Designer       ", "Figma, UX Research   ", 3, "JB, Larkin           ", "Figma     ", "DesignSuite", 2800, 4000));
        jobs.add(new JobPosting("Developer Engineer", "AWS, C, Csharp       ", 4, "KL, Mont Kiara       ", "AWS       ", "CloudStack ", 6000, 8000));
        jobs.add(new JobPosting("IT Manager        ", "Leadership           ", 5, "PJ, Damansara        ", "          ", "Nike       ", 7000, 9000));    

        return jobs;
    }
}

