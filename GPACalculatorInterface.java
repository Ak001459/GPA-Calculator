import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class GPACalculatorInterface extends JFrame implements ActionListener {
    // Login components
    JTextField userField;
    JPasswordField passField;
    JButton loginButton;

    // GPA Calculator components
    Frame frame;
    TextField nameField, idField, courseField, gradeField;
    Label resultLabel;

    public GPACalculatorInterface() {
        // Login UI
        setTitle("Login");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(new JLabel()); // filler
        add(loginButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (username.equals("admin") && password.equals("1234")) {
            dispose();
            launchGpaCalculator();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    public void launchGpaCalculator() {
        frame = new Frame("Student GPA Calculator");
        frame.setLayout(new GridLayout(6, 2));

        frame.add(new Label("Student Name:"));
        nameField = new TextField();
        frame.add(nameField);

        frame.add(new Label("Student ID:"));
        idField = new TextField();
        frame.add(idField);

        frame.add(new Label("Course:"));
        courseField = new TextField();
        frame.add(courseField);

        frame.add(new Label("Grade (0-100):"));
        gradeField = new TextField();
        frame.add(gradeField);

        Button calcBtn = new Button("Calculate GPA");
        calcBtn.addActionListener(this::calculateGPA);
        frame.add(calcBtn);

        Button saveBtn = new Button("Save to CSV");
        saveBtn.addActionListener(e -> saveToCSV());
        frame.add(saveBtn);

        resultLabel = new Label("GPA: ");
        frame.add(resultLabel);

        Button exitBtn = new Button("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        frame.add(exitBtn);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public void calculateGPA(ActionEvent e) {
        try {
            double grade = Double.parseDouble(gradeField.getText());
            double gpa = convertGradeToGPA(grade);
            resultLabel.setText("GPA: " + gpa);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input!");
        }
    }

    private double convertGradeToGPA(double grade) {
        if (grade >= 90) return 10.0;
        else if (grade >= 80) return 9.0;
        else if (grade >= 70) return 8.0;
        else if (grade >= 60) return 7.0;
        else if (grade >= 50) return 6.0;
        else if (grade >= 40) return 5.0;
        else return 0.0;
    }

    private void saveToCSV() {
        try (FileWriter writer = new FileWriter("students.csv", true)) {
            writer.write(nameField.getText() + "," + idField.getText() + "," +
                         courseField.getText() + "," + gradeField.getText() + "," + resultLabel.getText().split(": ")[1] + "\\n");
            resultLabel.setText("Saved to CSV!");
        } catch (IOException ex) {
            resultLabel.setText("Error saving!");
        }
    }

    public static void main(String[] args) {
        new GPACalculatorInterface();
    }
}
