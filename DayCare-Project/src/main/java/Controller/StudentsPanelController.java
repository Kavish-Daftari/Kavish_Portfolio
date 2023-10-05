/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helper.ImageHelper;
import Model.AbstractPerson;
import Model.Classroom;
import Model.School;
import Model.Student;
import Model.Teacher;
import View.Students.AddStudentPanel;
import View.Students.StudentInformationPanel;
import View.Students.StudentsPanel;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class StudentsPanelController {

    private JPanel container;
    private School school;

    private StudentsPanel panel;

    public JButton addStudentButton;
    public JButton backButton;
    public JButton deleteButton;
    public JButton viewStudentButton;
    private JTable studentsTable;
    public JButton vaccineButton;

    private Map<JButton, String> buttonIconMap = new HashMap<>();

    public StudentsPanelController(JPanel container, School school) {
        this.container = container;
        this.school = school;

        this.panel = new StudentsPanel();

        this.addStudentButton = panel.addStudentButton;
        this.backButton = panel.backButton;
        this.deleteButton = panel.deleteButton;
        this.viewStudentButton = panel.viewStudentButton;
        this.vaccineButton = panel.vaccineButton;

        this.studentsTable = panel.studentsTable;

        backButton.addActionListener(l -> {
            goBack();
        });

        viewStudentButton.addActionListener(l -> {
            viewStudentDetails();
        });

        deleteButton.addActionListener(l -> {
            deleteStudent();
        });

        addStudentButton.addActionListener(l -> {
            addStudent();
        });
        
        vaccineButton.addActionListener(l -> showStudentVaccineInfo());

    }

    public JPanel getStudentPanel() {
        setIcons();
        populateTable();
        return panel;
    }

    private void setIcons() {
        ImageHelper imageHelper = new ImageHelper();

        buttonIconMap.put(vaccineButton, "/icons/immunization.png");

        for (Map.Entry<JButton, String> button : buttonIconMap.entrySet()) {
            imageHelper.scaleAndSetButtonIcon(button.getValue(), button.getKey(), 60, 60);
        }
    }

    public void populateTable() {
        studentsTable.setAutoCreateRowSorter(true);
        DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
        model.setRowCount(0);

        for (AbstractPerson student : school.getStudentList()) {
            Object[] row = new Object[6];
            Student s = (Student) student;
            row[0] = student.getId();
            row[1] = student.getAge();
            row[2] = student.getFirstName();
            row[3] = student.getLastName();
            row[4] = s.getGPA();
            row[5] = s.getTeacherId();
            model.addRow(row);
        }
    }

    private void goBack() {
        school.addAllStudentToCSV();
        container.remove(panel);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }

    private void viewStudentDetails() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        int studentId = (int) studentsTable.getValueAt(selectedRow, 0);
        AbstractPerson selectedStudent = school.findStudentById(studentId);
        if (selectedStudent != null) {
            StudentsInformationController sic = new StudentsInformationController(container, selectedStudent, school, this);            
            container.add(sic.getStudentInformationPanel());
            CardLayout layout = (CardLayout) container.getLayout();
            layout.next(container);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        int studentId = (int) studentsTable.getValueAt(selectedRow, 0);
        int teacherId = (int) studentsTable.getValueAt(selectedRow, 5);

        try {
            school.deleteStudentById(studentId);

            for (Classroom room : school.getClassroomList()) {
                for (Teacher t : room.getTeacherList()) {
                    if (t.getId() == teacherId) {
                        t.deleteStudentById(studentId);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        populateTable();
        JOptionPane.showMessageDialog(panel,
                "Student deleted.",
                "Success",
                JOptionPane.OK_OPTION);
    }

    private void addStudent() {
        AddStudentController asc = new AddStudentController(container, school, this);        
        CardLayout layout = (CardLayout) container.getLayout();
        container.add(asc.getAddStudentsPanel());
        layout.next(container);
    }

    private void showStudentVaccineInfo() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }
        int studentId = (int) studentsTable.getValueAt(selectedRow, 0);
        AbstractPerson selectedStudent = school.findStudentById(studentId);
        VaccinePanelController vaccinePanelController = new VaccinePanelController(container, (Student) selectedStudent, this);
        CardLayout layout = (CardLayout) container.getLayout();
        container.add(vaccinePanelController.getPanel());
        layout.next(container);
    }
}
