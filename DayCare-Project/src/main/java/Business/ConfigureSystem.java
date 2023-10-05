/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DAO.HibernateUtil;
import Model.RatioRule;
import Model.School;
import Model.Student;
import Model.StudentFactory;
import Model.Vaccine;
import Model.util.FileUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;


public class ConfigureSystem {

    public static School configure() {
        List<String> CSVList = Arrays.asList(FileUtil.readLinesFromFile("Student.txt"));
//        List<String> VaccineList = Arrays.asList(FileUtil.readLinesFromFile("Vaccine.txt"));
        List<Vaccine> vaccines = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vaccines = session.createQuery("from Vaccine", Vaccine.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        List<Vaccine> vaccines = new ArrayList<>();
//        for (String s : VaccineList) {
//            vaccines.add(new Vaccine(s));
//        }
        List<String> RatioList = Arrays.asList(FileUtil.readLinesFromFile("Ratio.txt"));
        List<RatioRule> ratioRules = new ArrayList<>();
        for (String s : RatioList) {
            ratioRules.add(new RatioRule((s)));
        }
        School neu = new School();
        for (RatioRule r : ratioRules) {
            neu.addRatioRule(r);
        }
        for (String s : CSVList) {
            Student student = StudentFactory.getInstance().getObject(s);
            for (Vaccine vaccine : vaccines) {
                if (vaccine.getStudentId() == student.getId()) {
                    student.addVaccine(vaccine);
                }
            }
            neu.addStudent(student);
            neu.addAllStudentToCSV();
        }
        return neu;
    }
}
