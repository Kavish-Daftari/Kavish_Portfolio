/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Vaccine;
import View.Students.ChangeVaccinePanel;
import java.awt.CardLayout;
import java.awt.PopupMenu;
import javax.swing.JPanel;

/**
 *
 * @author zhengbochao
 */
public class ChangeVaccinePanelController {
    private JPanel container;
    private ChangeVaccinePanel panel;
    private VaccinePanelController prevController;
    private Vaccine vaccine;

    public ChangeVaccinePanelController(JPanel container, VaccinePanelController prevController, Vaccine vaccine) {
        this.container = container;
        this.panel = new ChangeVaccinePanel();
        this.prevController = prevController;
        this.vaccine = vaccine;
        panel.currDoseTextField.setText(Integer.toString(vaccine.getCurrDose()));
        panel.newDoseDateTextField.setText(vaccine.getLastDoseDate().toString());
        panel.backButton.addActionListener(l->goBack());
    }
    
    
    
    private void goBack() {
        container.remove(panel);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }

    public ChangeVaccinePanel getPanel() {
        return panel;
    }
    
    
}
