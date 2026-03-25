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
import entity.Applicant;

public class ApplicantInitializer {

    public ListInterface<Applicant> initializeApplicants() {
        ListInterface<Applicant> applicantList = new LinkedList<>();

        applicantList.add(new Applicant("Alice Tan   ", "alice@email.com  ", 111122222,"Sales, Communication ", 2, "KL, Bangsar            ", "Sales Executive     ", 6000));
        applicantList.add(new Applicant("Bob Lee     ", "bob@email.com    ", 222233333,"Node.js, MongoDB     ", 1, "Penang, Bayan Lepas    ", "Backend Developer   ", 4800));
        applicantList.add(new Applicant("Charlie Wong", "charlie@email.com", 333344444,"HTML, CSS, JavaScript", 1, "Selangor, Shah Alam    ", "Web Developer       ", 4500));
        applicantList.add(new Applicant("Diana Lim   ", "diana@email.com  ", 444455555,"IT, C++              ", 3, "Johor, Larkin          ", "IT                  ", 5200));
        applicantList.add(new Applicant("Eugene Chan ", "eugene@email.com ", 555566666,"Csharp, Python       ", 4, "KL, Mont Kiara         ", "Software Engineer   ", 6800));
        applicantList.add(new Applicant("Farah Noor  ", "farah@email.com  ", 666677777,"Python, SQL          ", 2, "Cyberjaya, Tech Park   ", "Data Analyst        ", 2000));
        applicantList.add(new Applicant("Hui Min     ", "hui@email.com    ", 888899999,"IT, Java, Python     ", 2, "Terengganu, City Center", "Mobile App Developer", 8300));
        applicantList.add(new Applicant("Isaac Lim   ", "isaac@email.com  ", 999900000,"C, C++, Java, Dsa    ", 1, "Ipoh, Station 18       ", "Software Engineer   ", 5000));
        applicantList.add(new Applicant("Janice Tan  ", "janice@email.com ", 101010100,"Excel, Business Mgmt ", 3, "Melaka, Ayer Keroh     ", "Business Analyst    ", 1900));
        applicantList.add(new Applicant("Kobe Bryant", "Kobe@email.com   ", 195003869,"Leadership           ", 1, "Perak, Ayer Hitam      ", "Project manager     ", 1500));



        return applicantList;
    }   

}
