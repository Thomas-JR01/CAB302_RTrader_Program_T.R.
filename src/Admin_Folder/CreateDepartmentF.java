package Admin_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CreateDepartmentF {

    private JPanel rootpanel_DeptC;
    private JTextField DeptName_text;
    private JButton createButton;

    public JPanel getRootPanel(){
        return rootpanel_DeptC;
    }

    public CreateDepartmentF() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String DeptName = DeptName_text.getText();

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_12," + DeptName); //ExCode,DeptName
                    pr.flush();

                    JOptionPane.showMessageDialog(null,("Created New Department"));

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,("Unable to Create Department."));
                }
            }
        });
    }


}
