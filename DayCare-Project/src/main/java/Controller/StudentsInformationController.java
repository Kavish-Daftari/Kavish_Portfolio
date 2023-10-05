/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AbstractPerson;
import Model.School;
import Model.Student;
import View.Students.StudentInformationPanel;
import View.Students.StudentsPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class StudentsInformationController {

    private JPanel container;
    private School school;
    private AbstractPerson student;
    private StudentsPanelController prevController;
    
    private StudentInformationPanel panel;
    
    public JButton backButton;
    public JButton saveButton;
    public JButton updateButton;

    public StudentsInformationController(JPanel container, AbstractPerson student,School school, StudentsPanelController prevController) {
        this.container = container;
        this.school = school;
        this.student = student;
        
        this.prevController = prevController;
        
        panel = new StudentInformationPanel(container, student, school);
        
        this.backButton = panel.backButton;
        this.saveButton = panel.getSaveBtn();
        this.updateButton = panel.getUpdateBtn();
        
        backButton.addActionListener(l -> goBack());      
        saveButton.addActionListener(l -> save());
        updateButton.addActionListener(l -> setEditable());
        
    }
    
    public StudentInformationPanel getStudentInformationPanel() {
        return panel;
    }
    
    private void goBack() {
        container.remove(panel);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
        Component[] componentArray = container.getComponents();
        Component component = componentArray[componentArray.length - 1];
        StudentsPanel spanel = (StudentsPanel) component;
        prevController.populateTable();
    }

    private void save() {
        List<JTextField> tfList = new ArrayList<JTextField>(Arrays.asList(panel.getFirstNameTf(), panel.getLastNameTf(), panel.getAgeTf(), panel.getIdTf(), panel.getRegistrationTf1(), panel.getParentNameTf(), panel.getParentEmailTf(), panel.getGpaTf()));

        String id = panel.getIdTf().getText();
        String age = panel.getAgeTf().getText();
        String studentFirstName = panel.getFirstNameTf().getText();
        String studentLastName = panel.getLastNameTf().getText();
        String regDate = panel.getRegistrationTf1().getText();
        String gpa = panel.getGpaTf().getText();
        String parentName = panel.getParentNameTf().getText();
        String parentEmail = panel.getParentEmailTf().getText();

        if (studentFirstName.equals("") || age.equals("") || id.equals("") || studentLastName.equals("") || regDate.equals("") || gpa.equals("") || parentName.equals("") || parentEmail.equals("")) {
            return;
        }

        // String studentString = id + "," + age + "," + studentFirstName + "," + studentLastName + "," + regDate + "," + gpa + "," + parentName + "," + parentEmail;
        try {
            int studentId = Integer.parseInt(id);
            AbstractPerson s = school.findStudentById(studentId);

            if (s != null) {
                Student student = (Student) s;
                student.setId(studentId);
                student.setFirstName(studentFirstName);
                student.setLastName(studentLastName);
                student.setLastRegDate(LocalDate.parse(regDate));
                student.setParentName(parentName);
                student.setParentEmail(parentEmail);
                student.setAge(Integer.parseInt(age));
                student.setGPA(Double.parseDouble(gpa));

                tfList.forEach(tf -> tf.setEditable(false));
                saveButton.setEnabled(false);
                populateStudentInfo();
            }

        } catch (Exception e) {
            return;
        }
    }
    
    private void populateStudentInfo() {
        panel.getFirstNameTf().setText(student.getFirstName());
        panel.getLastNameTf().setText(student.getLastName());
        panel.getAgeTf().setText(String.valueOf(student.getAge()));
        panel.getIdTf().setText(String.valueOf(student.getId()));

        Student s = (Student) student;
        panel.getRegistrationTf1().setText(s.getLastRegDate().toString());
        panel.getParentNameTf().setText(s.getParentName());
        panel.getParentEmailTf().setText(s.getParentEmail());
        panel.getGpaTf().setText(String.valueOf(s.getGPA()));
    }

    private void setEditable() {
        List<JTextField> tfList = new ArrayList<JTextField>(Arrays.asList(panel.getFirstNameTf(), panel.getLastNameTf(), panel.getAgeTf(), panel.getIdTf(), panel.getRegistrationTf1(), panel.getParentNameTf(), panel.getParentEmailTf(), panel.getGpaTf()));
        tfList.forEach(tf -> tf.setEditable(true));
        saveButton.setEnabled(true);
    }
    
    
    
}
