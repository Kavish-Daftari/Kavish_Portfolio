/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.HibernateUtil;
import Model.AbstractPerson;
import Model.Student;
import Model.Vaccine;
import View.Students.VaccinePanel;
import java.awt.CardLayout;
import java.time.LocalDate;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author zhengbochao
 */
public class VaccinePanelController {
    
    private JPanel container;
    private Student student;
    private Vaccine targetVaccine;
    private VaccinePanel panel;
    private StudentsPanelController prevController;

    public VaccinePanelController(JPanel container, Student student, StudentsPanelController prevController) {
        this.container = container;
        this.student = student;
        this.prevController = prevController;
        panel = new VaccinePanel();
        initPanel();
        panel.getBackButton().addActionListener(l->goBack());
        panel.getUpdateButton().addActionListener(l->toChangeVaccineInfo());
        panel.submitButton.addActionListener(l->submitChange());
        
    }
    
    public void toChangeVaccineInfo() {
        int selectedRow = panel.getVaccineTable().getSelectedRow();

        if (selectedRow < 0) {
            return;
        }
        String vaccineType = (String) panel.getVaccineTable().getValueAt(selectedRow, 0);
        Vaccine selectedVaccine = student.findVaccineByType(vaccineType);
        panel.currDoseTextField.setText(Integer.toString(selectedVaccine.getCurrDose()));
        panel.newDoseDateTextField.setText(selectedVaccine.getLastDoseDate().toString());
        targetVaccine = selectedVaccine;
    }
    
    private void initPanel() {
        panel.getVaccineTable().setAutoCreateRowSorter(true);
        DefaultTableModel model = (DefaultTableModel) panel.getVaccineTable().getModel();
        model.setRowCount(0);
        for (Vaccine vaccine: student.getVaccineList()) {
            Object[] row = new Object[6];
            row[0] = vaccine.getType();
            row[1] = vaccine.getCurrDose() == vaccine.getTotalDose() ? "Yes" : "No";
            row[2] = vaccine.getCurrDose();
            row[3] = vaccine.getTotalDose();
            row[4] = vaccine.getLastDoseDate().toString();
            row[5] = vaccine.getCurrDose() == vaccine.getTotalDose() ? "" :vaccine.getLastDoseDate().plusDays(vaccine.getTimeInterval()).toString();
            model.addRow(row);
        }
    }
    
    private void goBack() {
        container.remove(panel);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }

    public JPanel getContainer() {
        return container;
    }

    public void setContainer(JPanel container) {
        this.container = container;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public VaccinePanel getPanel() {
        return panel;
    }

    public void setPanel(VaccinePanel panel) {
        this.panel = panel;
    }

    public StudentsPanelController getPrevController() {
        return prevController;
    }

    public void setPrevController(StudentsPanelController prevController) {
        this.prevController = prevController;
    }

    private void submitChange() {
        targetVaccine.setCurrDose(Integer.parseInt(panel.currDoseTextField.getText()));
        targetVaccine.setLastDoseDate(LocalDate.parse(panel.newDoseDateTextField.getText()));
        // update vaccine
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.saveOrUpdate(targetVaccine);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        initPanel();
    }
    
    
    
}
