/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Controller.LandingPageController;
import Model.School;
import View.LandingPage;
import javax.swing.JPanel;

/**
 *
 * @author zhengbochao
 */
public class Application {
    
    LandingPageController landingPageController;
    
    public Application() {
        LandingPage landingPage = new LandingPage();
        this.landingPageController = new LandingPageController(landingPage);
        /* Create and display the form */
        landingPage.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        Application app = new Application();
    }
}
