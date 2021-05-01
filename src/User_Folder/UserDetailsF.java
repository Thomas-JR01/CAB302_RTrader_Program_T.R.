package User_Folder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserDetailsF {
    private JPanel rootpanel_userdetails;
    private JTable deptresources_table;
    private JLabel username_label;
    private JLabel dept_label;
    private JLabel credits_label;
    private JButton resetpassword_button;


    public JPanel getRootPanel(){
        return rootpanel_userdetails;
    }

    public UserDetailsF(Object[][] Data, String[] LabelData, String ID) {
        populateForm(Data,LabelData);
        resetpassword_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetPasswordF_Extended ui = new ResetPasswordF_Extended(ID);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public void populateForm(Object[][] data, String[] labeldata){

        username_label.setText(labeldata[2]);
        dept_label.setText("Department: " + labeldata[1]);
        credits_label.setText("Credits: " + labeldata[0]);

        deptresources_table.setModel(new DefaultTableModel(
                data,
                new String[]{"Resource","Qty."}
        ));
    }

    public static Object[][] DepartmentDetailsGet(String User, String Dept){
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_03," + User + "," + Dept); //ExCode,UserID,Department
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Structure DeptResources Data (Object[SizeUnknown][2])
            //Get List Count & Username/DeptCredits
            int ListCount = Integer.parseInt(bf.readLine());
            String[] UserDeptCred = (bf.readLine()).split(",");
            String Username = UserDeptCred[0]; String DepartmentCredits = UserDeptCred[1];

            //Start Getting and Structuring Data
            Object[][] ListingData = new Object[ListCount][2];
            for (int i = 0; i < ListCount; i++) {
                for (int j = 0; j < 2; j++) {
                    ListingData[i][j] = bf.readLine();
                }
            }

            return ListingData;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    public static String[] UserDetailsGet(String User, String Dept){
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_03," + User + "," + Dept); //ExCode,UserID,Department
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Structure DeptResources Data (Object[SizeUnknown][2])
            //Get Username/DeptCredits
            int temp = Integer.parseInt(bf.readLine());
            String[] UserDeptCred = (bf.readLine()).split(",");

            String[] UserData = {UserDeptCred[0],Dept,UserDeptCred[1]};

            return UserData;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }


}
