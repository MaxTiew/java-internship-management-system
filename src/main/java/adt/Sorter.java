/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import entity.JobPosting;
import entity.Applicant;

/**
 *
 * @author User
 */
public class Sorter {

    public static void sortByMinSalary(ListInterface<JobPosting> list) {
        insertionSortByMinSalary(list);
    }

    public static void sortByMaxSalary(ListInterface<JobPosting> list) {
        insertionSortByMaxSalary(list);
    }

    public static void sortByExperience(ListInterface<JobPosting> list) {
        selectionSortByExperience(list);
    }

    public static void sortByLocation(ListInterface<JobPosting> list) {
        selectionSortByLocation(list);
    }

    public static void sortByTitle(ListInterface<JobPosting> list) {
        quickSortByTitle(list);
    }

    public static void sortByRequiredSkill(ListInterface<JobPosting> list) {
        selectionSortByRequiredSkill(list);
    }
        // Applicant sorting
    public static void sortApplicantsByExperienceDescending(ListInterface<Applicant> list) {
        selectionSortByExperienceDescending(list);
    }

    public static void sortApplicantsByExpectedSalaryAscending(ListInterface<Applicant> list) {
        insertionSortByExpectedSalaryAscending(list);
    }

    public static void sortApplicantsBySkillMatch(ListInterface<Applicant> list, String[] requiredSkills) {
        insertionSortBySkillMatch(list, requiredSkills);
    }

    public static void insertionSortByMinSalary(ListInterface<JobPosting> list) {
        for (int i = 2; i <= list.getNumberOfEntries(); i++) {
            JobPosting key = list.getEntry(i);
            int j = i - 1;
            while (j >= 1 && list.getEntry(j).getMinSalary() > key.getMinSalary()) {
                list.replace(j + 1, list.getEntry(j));
                j--;
            }
            list.replace(j + 1, key);
        }
    }

    public static void insertionSortByMaxSalary(ListInterface<JobPosting> list) {
        for (int i = 2; i <= list.getNumberOfEntries(); i++) {
            JobPosting key = list.getEntry(i);
            int j = i - 1;
            while (j >= 1 && list.getEntry(j).getMaxSalary() < key.getMaxSalary()) {
                list.replace(j + 1, list.getEntry(j));
                j--;
            }
            list.replace(j + 1, key);
        }
    }

    public static void selectionSortByExperience(ListInterface<JobPosting> list) {
        int n = list.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= n; j++) {
                if (list.getEntry(j).getMinExperience() < list.getEntry(minIndex).getMinExperience()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                JobPosting temp = list.getEntry(i);
                list.replace(i, list.getEntry(minIndex));
                list.replace(minIndex, temp);
            }
        }
    }

    public static void selectionSortByLocation(ListInterface<JobPosting> list) {
        int n = list.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= n; j++) {
                if (list.getEntry(j).getLocation().compareToIgnoreCase(list.getEntry(minIndex).getLocation()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                JobPosting temp = list.getEntry(i);
                list.replace(i, list.getEntry(minIndex));
                list.replace(minIndex, temp);
            }
        }
    }

    public static void selectionSortByRequiredSkill(ListInterface<JobPosting> list) {
        int n = list.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= n; j++) {
                String skillJ = list.getEntry(j).getRequiredSkills().split(",")[0].trim().toLowerCase();
                String skillMin = list.getEntry(minIndex).getRequiredSkills().split(",")[0].trim().toLowerCase();
                if (skillJ.compareTo(skillMin) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                JobPosting temp = list.getEntry(i);
                list.replace(i, list.getEntry(minIndex));
                list.replace(minIndex, temp);
            }
        }
    }

    public static void quickSortByTitle(ListInterface<JobPosting> list) {
        quickSort(list, 1, list.getNumberOfEntries());
    }

    private static void quickSort(ListInterface<JobPosting> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    private static int partition(ListInterface<JobPosting> list, int low, int high) {
        JobPosting pivot = list.getEntry(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.getEntry(j).getTitle().compareToIgnoreCase(pivot.getTitle()) <= 0) {
                i++;
                JobPosting temp = list.getEntry(i);
                list.replace(i, list.getEntry(j));
                list.replace(j, temp);
            }
        }
        JobPosting temp = list.getEntry(i + 1);
        list.replace(i + 1, list.getEntry(high));
        list.replace(high, temp);
        return i + 1;
    }


    // Applicant sorting implementations
    public static void selectionSortByExperienceDescending(ListInterface<Applicant> list) {
        int n = list.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            int maxIndex = i;
            for (int j = i + 1; j <= n; j++) {
                if (list.getEntry(j).getExperience() > list.getEntry(maxIndex).getExperience()) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                Applicant temp = list.getEntry(i);
                list.replace(i, list.getEntry(maxIndex));
                list.replace(maxIndex, temp);
            }
        }
    }

    public static void insertionSortByExpectedSalaryAscending(ListInterface<Applicant> list) {
        for (int i = 2; i <= list.getNumberOfEntries(); i++) {
            Applicant key = list.getEntry(i);
            int j = i - 1;
            while (j >= 1 && list.getEntry(j).getExpectedSalary() > key.getExpectedSalary()) {
                list.replace(j + 1, list.getEntry(j));
                j--;
            }
            list.replace(j + 1, key);
        }
    }

    public static void insertionSortBySkillMatch(ListInterface<Applicant> list, String[] requiredSkills) {
        for (int i = 2; i <= list.getNumberOfEntries(); i++) {
            Applicant key = list.getEntry(i);
            int keyMatch = countMatchingSkills(key.getSkills(), requiredSkills);
            int j = i - 1;
            while (j >= 1 && countMatchingSkills(list.getEntry(j).getSkills(), requiredSkills) < keyMatch) {
                list.replace(j + 1, list.getEntry(j));
                j--;
            }
            list.replace(j + 1, key);
        }
    }

    private static int countMatchingSkills(String skills, String[] requiredSkills) {
        int count = 0;
        for (String r : requiredSkills) {
            for (String s : skills.split(",")) {
                if (s.trim().equalsIgnoreCase(r.trim())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
