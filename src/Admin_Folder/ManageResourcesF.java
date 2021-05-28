package Admin_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManageResourcesF {
    private JPanel rootpanel_resources;
    private JButton assignButton;
    private JTextField ResourceName_Text;
    private JSpinner ResourceQty_Spin;
    private JSpinner Credits_Spin;
    private JComboBox Dept_Combo;

    public JPanel getRootPanel(){
        return rootpanel_resources;
    }

    public ManageResourcesF(Object[] Data) {
        createComboBox(Data);
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Resource = ResourceName_Text.getText();
                String Dept = (String)Dept_Combo.getSelectedItem();
                Object ResourceQty = ResourceQty_Spin.getValue();
                Object Credits = Credits_Spin.getValue();

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_15," + Dept + "," + Credits + "," + Resource + "," + ResourceQty); //ExCode,Department,Credit_Qty,Resources_Name,Resources_Qty
                    pr.flush();

                    JOptionPane.showMessageDialog(null,("Assigned Department Resources"));

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,("Unable to Assign Department Resources."));
                }
            }
        });
    }

    public void createComboBox(Object[] data){
        for (int i = 0; i < data.length; i++) {
            Dept_Combo.addItem(data[i]);
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
