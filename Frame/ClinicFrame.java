package Frame;
import Entity.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class ClinicFrame extends JFrame implements ActionListener 
{
    private JPanel panel;
    private JLabel labelTitle, lName, lPhone, lAge, lGender, lProblem, lDoctor;
    private JTextField tfName, tfPhone, tfAge;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup bgGender;
    private JComboBox<String> cbDoctor;
    private JTextArea taProblem, taDisplay;
    private JScrollPane scroll1, scroll2;
    private JButton btnSubmit, btnClear, btnExit;

    public ClinicFrame() 
    {
        super("Clinic Management System");
        super.setBounds(200, 50, 850, 800);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 250));

        labelTitle = new JLabel("Patient Registration Form");
        labelTitle.setBounds(250, 10, 400, 40);
        labelTitle.setFont(new Font("Cambria", Font.BOLD, 26));
        panel.add(labelTitle);

        lName = new JLabel("Name:"); lName.setBounds(50, 80, 100, 30); panel.add(lName);
        tfName = new JTextField(); tfName.setBounds(150, 80, 200, 30); panel.add(tfName);

        lPhone = new JLabel("Phone:"); lPhone.setBounds(50, 120, 100, 30); panel.add(lPhone);
        tfPhone = new JTextField(); tfPhone.setBounds(150, 120, 200, 30); panel.add(tfPhone);

        lAge = new JLabel("Age:"); lAge.setBounds(50, 160, 100, 30); panel.add(lAge);
        tfAge = new JTextField(); tfAge.setBounds(150, 160, 200, 30); panel.add(tfAge);

        lGender = new JLabel("Gender:"); lGender.setBounds(50, 200, 100, 30); panel.add(lGender);
        rbMale = new JRadioButton("Male"); rbMale.setBounds(150, 200, 80, 30); panel.add(rbMale);
        rbFemale = new JRadioButton("Female"); rbFemale.setBounds(240, 200, 100, 30); panel.add(rbFemale);
        bgGender = new ButtonGroup(); bgGender.add(rbMale); bgGender.add(rbFemale);

        lDoctor = new JLabel("Doctor:"); lDoctor.setBounds(50, 240, 100, 30); panel.add(lDoctor);
        String[] doctors = {"", "Dr. Kamal (General)", "Dr. Nita (Dentist)", "Dr. Rahul (Cardio)", "Dr. Sofia (Skin)", "Dr. Ahmed (Medicine)"};
        cbDoctor = new JComboBox<>(doctors);
        cbDoctor.setBounds(150, 240, 200, 30);
        panel.add(cbDoctor);

        lProblem = new JLabel("Health Problem:"); lProblem.setBounds(50, 280, 150, 30); panel.add(lProblem);
        taProblem = new JTextArea();
        scroll1 = new JScrollPane(taProblem);
        scroll1.setBounds(50, 310, 300, 80);
        panel.add(scroll1);

        btnSubmit = new JButton("Submit"); btnSubmit.setBounds(50, 420, 100, 40); btnSubmit.addActionListener(this); panel.add(btnSubmit);
        btnClear = new JButton("Clear"); btnClear.setBounds(160, 420, 100, 40); btnClear.addActionListener(this); panel.add(btnClear);
        btnExit = new JButton("Exit"); btnExit.setBounds(270, 420, 100, 40); btnExit.addActionListener(this); panel.add(btnExit);

        taDisplay = new JTextArea();
        scroll2 = new JScrollPane(taDisplay);
        scroll2.setBounds(400, 80, 400, 600);
        taDisplay.setEditable(false);
        panel.add(scroll2);

        super.add(panel);
        updateDisplay();
    }

    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource() == btnExit) System.exit(0);
        
        if (ae.getSource() == btnClear) 
        {
            tfName.setText(""); tfPhone.setText(""); tfAge.setText("");
            taProblem.setText(""); cbDoctor.setSelectedIndex(0);
            bgGender.clearSelection();
        }

        if (ae.getSource() == btnSubmit) 
        {
            try 
            {
                String name = tfName.getText();
                String phone = tfPhone.getText();
                int age = Integer.parseInt(tfAge.getText()); 
                String problem = taProblem.getText();
                String doc = cbDoctor.getSelectedItem().toString();
                boolean isMale = rbMale.isSelected();

                if (name.isEmpty() || phone.isEmpty() || problem.isEmpty() || doc.isEmpty() || (!rbMale.isSelected() && !rbFemale.isSelected())) 
                {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!");
                } 
                else 
                {
                    Patient p = new Patient(name, phone, age, isMale, problem, doc); 
                    p.insertAppointment();
                    JOptionPane.showMessageDialog(this, "Appointment Saved!");
                    updateDisplay();
                }
            } 
            catch (NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for Age!");
            }
        }
    }

    private void updateDisplay() 
    {
        try 
        {
            File file = new File("Data/appointments.txt");
            if (file.exists()) 
            {
                taDisplay.setText("");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    taDisplay.append(line + "\n");
                }
                br.close();
            }
        } 
        catch(IOException e) { e.printStackTrace(); }
    }
}