/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author zhengbochao
 */
@Entity
@Table(name = "VACCINE")
public class Vaccine {
    @Id
    private int Id;
    private int studentId;
    private String type;
    private int timeInterval;
    private LocalDate lastDoseDate;
    private int currDose;
    private int totalDose;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }
    
    

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getLastDoseDate() {
        return lastDoseDate;
    }

    public void setLastDoseDate(LocalDate lastDoseDate) {
        this.lastDoseDate = lastDoseDate;
    }

    public int getTotalDose() {
        return totalDose;
    }

    public void setTotalDose(int totalDose) {
        this.totalDose = totalDose;
    }

    public int getCurrDose() {
        return currDose;
    }

    public void setCurrDose(int currDose) {
        this.currDose = currDose;
    }
    
    public Vaccine() {
        
    }
    
    public Vaccine(String csv) {

        String[] parameters = csv.split(",");

        try {
            this.Id = Integer.parseInt(parameters[0]);
            this.studentId = Integer.parseInt(parameters[1]);
            this.type = parameters[2];
            this.timeInterval = Integer.parseInt(parameters[3]);
            this.lastDoseDate = LocalDate.parse(parameters[4]);
            this.currDose = Integer.parseInt(parameters[5]);
            this.totalDose = Integer.parseInt(parameters[6]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Unable to parse vaccine info");
        }
    }
}
