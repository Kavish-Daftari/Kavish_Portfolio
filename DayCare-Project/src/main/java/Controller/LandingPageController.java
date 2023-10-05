/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Business.ConfigureSystem;
import Helper.ImageHelper;
import Model.School;
import View.LandingPage;
import View.Teachers.TeachersPanel;
import java.awt.CardLayout;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author zhengbochao
 */
public class LandingPageController {
    private School school;
    private LandingPage landingPage;
    private JPanel mainContainer;
//    private JPanel landingPanel;
    private Map<JButton, String> buttonIconMap = new HashMap<>();
    
    public LandingPageController(LandingPage landingPage) {
        // set basic view
        this.landingPage = landingPage;
        school = ConfigureSystem.configure();  
        landingPage.pack();
        landingPage.setLocationRelativeTo(null);
        this.mainContainer = landingPage.getMainComponent();
//        this.landingPanel = landingPage.getLandingPanel();
        // deal with components
        buttonIconMap.put(landingPage.getStudentsButton(), "/icons/student.png");
        buttonIconMap.put(landingPage.getTeachersButton(), "/icons/teacher.png");
        buttonIconMap.put(landingPage.getClassroomsButton(), "/icons/classroom.png");

        ImageHelper imageHelper = new ImageHelper();
        for (Map.Entry<JButton, String> button : buttonIconMap.entrySet()) {
            imageHelper.scaleAndSetButtonIcon(button.getValue(), button.getKey(), 60, 60);
        }
        setWelcomeImage();
        landingPage.getStudentsButton().addActionListener(l->goToStudentPanel());
        landingPage.getTeachersButton().addActionListener(l->goToTeacherPanel());
        landingPage.getClassroomsButton().addActionListener(l->goToClassroomPanel());
    }

    
    private ImageIcon createImageIcon(String path, String description) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    private void setWelcomeImage() {

        ImageHelper imageHelper = new ImageHelper();
        // imageHelper.sca
        // imageHelper.scaleAndSetLabelIcon("/icons/day.png", daycareLabel, 100, 100, "Daycare logo");
        // this.setLocationRelativeTo(null);

        ImageIcon imageIcon = createImageIcon("/icons/daycare.jpg", "Daycare logo");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        landingPage.getWelcomeImageLabel().setIcon(imageIcon);
        // daycareLabel.setVerticalTextPosition(SwingConstants.RIGHT);
        // daycareLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    }
    
    private void goToStudentPanel() {
        JPanel landingPanel = landingPage.getLandingPanel();
        StudentsPanelController studentsPanelController = new StudentsPanelController(landingPanel, school);
        CardLayout layout = (CardLayout) landingPanel.getLayout();
        landingPanel.add(studentsPanelController.getStudentPanel());
        layout.next(landingPanel);
    }
    
    private void goToTeacherPanel() {
        JPanel landingPanel = landingPage.getLandingPanel();
        TeachersPanel teachersPanel = new TeachersPanel(landingPanel, school);
        CardLayout layout = (CardLayout) landingPanel.getLayout();
        landingPanel.add(teachersPanel);
        layout.next(landingPanel);
    }
    
    private void goToClassroomPanel() {
        JPanel landingPanel = landingPage.getLandingPanel();
        ClassroomsPanelController classroomsPanelController = new ClassroomsPanelController(landingPanel, school);
        CardLayout layout = (CardLayout) landingPanel.getLayout();
        landingPanel.add(classroomsPanelController.getClassroomsPanel());
        layout.next(landingPanel);
    }
    
    
    
}
