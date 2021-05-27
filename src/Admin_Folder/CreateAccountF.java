package Admin_Folder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CreateAccountF {
    private JPanel rootpanel_AccountC;
    private JTextField Username_Text;
    private JTextField Password_Text;
    private JComboBox Department_Combo;
    private JButton createButton;

    public JPanel getRootPanel(){
        return rootpanel_AccountC;
    }

    public CreateAccountF(Object[] Data) {
        createComboBox(Data);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String User = Username_Text.getText();
                String Pass = Password_Text.getText();
                String Dept = (String)Department_Combo.getSelectedItem();

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_14," + User + "," + Pass + "," + Dept); //ExCode,Username,Password,Department
                    pr.flush();

                    //Get Requested Data (Error or Sucsess 0/1)
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    String result = bf.readLine();

                    if (result.equals("0")) {
                        JOptionPane.showMessageDialog(null,("Created New Account"));
                    } else if (result.equals("1")) {
                        JOptionPane.showMessageDialog(null,("Unable to Create Account."));
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,("Unable to Create Account."));
                }
            }
        });
    }

    public void createComboBox(Object[] data){
        for (int i = 0; i < data.length; i++) {
            Department_Combo.addItem(data[i]);
        }
    }

    public static Object[] departmentGet() {
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_13");
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Get Count First
            int DeptCount = Integer.parseInt(bf.readLine());

            //Start Getting and Structuring Data
            Object[] DepartmentList = new Object[DeptCount];
            for (int i = 0; i < DeptCount; i++) {
                DepartmentList[i] = bf.readLine();
            }

            return DepartmentList;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }


}
