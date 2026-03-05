import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Frame extends JFrame implements MouseListener, ActionListener {
    JPanel panel;
    JLabel label, ulabel, phlabel, tlabel, rlabel, ipanel, jclabel, olabel, datelabel;
    JTextField jt1, phfield;
    JRadioButton jr1, jr2;
    ButtonGroup bt1;
    JComboBox<String> jc1, monthBox, dayBox;
    JButton btn1;
    JCheckBox saveContactBox;
    ImageIcon ic1;

    Color c1, c2, c3;
    Font f1, f2;

    public Frame() {
        super("Package Tour Agency Booking");
        setBounds(30, 20, 1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 980, 560);
        c1 = new Color( 52, 73, 94);
        panel.setBackground(c1);

        label = new JLabel("PACKAGE TOUR BOOKING");
        label.setBounds(30, 20, 600, 50);
        f1 = new Font("Cambria", Font.ITALIC, 50);
        c2 = new Color(0, 128, 235);
        label.setBackground(c2);
        c3 = new Color(255, 255, 255);
        label.setForeground(c3);
        label.setOpaque(true);
        label.setFont(f1);
        panel.add(label);

        ulabel = new JLabel("User Name : ");
        ulabel.setBounds(30, 100, 100, 30);
        ulabel.setBackground(c3);
        ulabel.setOpaque(true);
        panel.add(ulabel);

        jt1 = new JTextField();
        jt1.setBounds(140, 100, 150, 30);
        panel.add(jt1);

        phlabel = new JLabel("Phone Number : ");
        phlabel.setBounds(30, 138, 110, 30);
        phlabel.setBackground(c3);
        phlabel.setOpaque(true);
        panel.add(phlabel);

        phfield = new JTextField();
        phfield.setBounds(145, 138, 150, 30);
        panel.add(phfield);

        rlabel = new JLabel("Gender : ");
        rlabel.setBounds(30, 175, 100, 30);
        rlabel.setBackground(c3);
        rlabel.setOpaque(true);
        panel.add(rlabel);

        jr1 = new JRadioButton("Female");
        jr1.setBounds(140, 175, 80, 30);
        jr1.setOpaque(true);
        panel.add(jr1);

        jr2 = new JRadioButton("Male");
        jr2.setBounds(230, 175, 80, 30);
        jr2.setOpaque(true);
        panel.add(jr2);

        bt1 = new ButtonGroup();
        bt1.add(jr1);
        bt1.add(jr2);

        tlabel = new JLabel("Tickets : ");
        tlabel.setBounds(30, 215, 100, 30);
        tlabel.setBackground(c3);
        tlabel.setOpaque(true);
        panel.add(tlabel);

        String[] tickets = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        jc1 = new JComboBox<>(tickets);
        jc1.setBounds(140, 215, 70, 30);
        panel.add(jc1);

        datelabel = new JLabel("Travel Date : ");
        datelabel.setBounds(30, 255, 100, 30);
        datelabel.setBackground(c3);
        datelabel.setOpaque(true);
        panel.add(datelabel);

        String[] months = { "January", "February", "March", "April", "May", "June",
                            "July", "August", "September", "October", "November", "December" };
        monthBox = new JComboBox<>(months);
        monthBox.setBounds(140, 255, 100, 30);
        panel.add(monthBox);

        String[] days = new String[30];
        for (int i = 0; i < 30; i++) {
            days[i] = Integer.toString(i + 1);
        }
        dayBox = new JComboBox<>(days);
        dayBox.setBounds(250, 255, 60, 30);
        panel.add(dayBox);

        saveContactBox = new JCheckBox("Save contact for future");
        saveContactBox.setBounds(30, 295, 180, 30);
        saveContactBox.setOpaque(true);
        panel.add(saveContactBox);

        btn1 = new JButton("Submit");
        btn1.setBounds(30, 335, 120, 30);
        btn1.addActionListener(this);
        btn1.addMouseListener(this);
        panel.add(btn1);

        olabel = new JLabel("Output will appear here");
        olabel.setBounds(30, 375, 500, 120);
        olabel.setBackground(c3);
        olabel.setOpaque(true);
        olabel.addMouseListener(this);
        panel.add(olabel);

        ic1 = new ImageIcon("Logo.png");
        ipanel = new JLabel(ic1);
        ipanel.setBounds(550, 15, 400, 400);
        panel.add(ipanel);

        super.add(panel);
    }

    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == olabel) {
            olabel.setText("Output label clicked!");
        }
    }

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}

    public void mouseEntered(MouseEvent me){
        if (me.getSource() == btn1){
            btn1.setBackground(Color.BLUE);
            btn1.setForeground(Color.WHITE);
        }
    }

    public void mouseExited(MouseEvent me){
        if (me.getSource() == btn1){
            btn1.setBackground(UIManager.getColor("Button.background"));
            btn1.setForeground(Color.BLACK);
        }
    }

    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == btn1) {
            String username = jt1.getText().trim();
            String selectedTicketsStr = (String) jc1.getSelectedItem();
            String selectedMonth = (String) monthBox.getSelectedItem();
            String selectedDay = (String) dayBox.getSelectedItem();

            int numberOfTickets = 0;
            try {
                numberOfTickets = Integer.parseInt(selectedTicketsStr);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing number of tickets: " + e.getMessage());
            }

            int costPerTicket = 1000; 
            int totalCost = numberOfTickets * costPerTicket;
			JOptionPane.showMessageDialog(this, "Booking request submitted!");
            olabel.setText("User: " + username + "  Tickets: " + selectedTicketsStr + "  Date: " + selectedDay + " " + selectedMonth + "  Total: " + totalCost + " BDT");

            try {
                File file = new File("./Data/ticketdata.txt");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                try (
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);
                ) {
                    pw.println("Username: " + username);
                    pw.println("Tickets Booked: " + selectedTicketsStr);
                    pw.println("Travel Date: " + selectedDay + " " + selectedMonth);
                    pw.println("Total Cost: " + totalCost + " BDT"); 
                    pw.println("---------------------------------------");
                }

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error writing to file!");
            }
        }
    }
}