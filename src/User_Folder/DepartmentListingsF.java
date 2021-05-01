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

public class DepartmentListingsF {
    private JPanel rootpanel_deptlistings;
    private JButton delete_button;
    private JTable deptlisting_table;

    public JPanel getRootPanel(){
        return rootpanel_deptlistings;
    }

    public DepartmentListingsF(Object[][] Data) {
        //CREATE&FILL TABLE
        createTable(Data);
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeptDeleteListingF_Extended ui = new DeptDeleteListingF_Extended(Data);
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

    public void createTable(Object[][] data){

        deptlisting_table.setModel(new DefaultTableModel(
                data,
                new String[]{"ID","Buying/Selling","Resource","Credits","Qty."}
        ));
    }

    public static Object[][] listing_departmentGet(String DeptIn){
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_05," + DeptIn);
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Structure Listing Data (Object[SizeUnknown][5])
            //Get List Count than Skip 4 Empty Lines
            int ListCount = (Integer.parseInt(bf.readLine()))-1;
            for (int i = 0; i < 4; i++) {
                String temp = bf.readLine();
            }
            //Start Getting and Structuring Data
            Object[][] ListingData = new Object[ListCount][5];
            for (int i = 0; i < ListCount; i++) {
                for (int j = 0; j < 5; j++) {
                    ListingData[i][j] = bf.readLine();
                }
            }

            return ListingData;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }


}
