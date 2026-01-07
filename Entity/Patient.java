package Entity;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Patient {
    private String name;
    private String phone;
    private String problem;
    private String doctor;
    private int age;
    private boolean isMale;
    private File file;
    private FileWriter fwriter;

    public Patient(String name, String phone, int age, boolean isMale, String problem, String doctor) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.isMale = isMale;
        this.problem = problem;
        this.doctor = doctor;
    }

    public void insertAppointment() {
        try {
            file = new File("Data/appointments.txt");
            if (!file.exists()) {
                file.getParentFile().mkdirs(); 
                file.createNewFile(); 
            }
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm a, dd/MM/yyyy");
            String timeAndDate = now.format(dtf);
            
            fwriter = new FileWriter(file, true);
            fwriter.write("Appointment Date: " + timeAndDate + "\n");
            fwriter.write("========================================================\n");
            fwriter.write("Name: " + name + "\n");
            fwriter.write("Phone: " + phone + "\n");
            fwriter.write("Age: " + age + "\n");
            if (isMale == true) {
                fwriter.write("Gender: Male\n");
            } else {
                fwriter.write("Gender: Female\n");
            }

            fwriter.write("Assigned Doctor: " + doctor + "\n");
            fwriter.write("Health Problem: " + problem + "\n");
            fwriter.write("--------------------------------------------------------\n");
            
            fwriter.flush(); 
            fwriter.close(); 
        } 
        catch(IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error writing to file!");
        }
    }
}