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

public class CurrentListingsF {
    private JPanel rootpanel_currentlistings;
    private JButton action_button;
    private JButton create_button;
    private JTable currentlisting_table;

    public JPanel getRootPanel(){
        return rootpanel_currentlistings;
    }

    public CurrentListingsF(Object[][] Data, String Dept) {
        //CREATE&FILL TABLE
        createTable(Data);
        action_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionListingF_Extended ui = new ActionListingF_Extended(Dept);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateListingF_Extended ui = new CreateListingF_Extended(Dept);
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

        currentlisting_table.setModel(new DefaultTableModel(
                data,
                new String[]{"ID","Buying/Selling","Resource","Credits","Qty.","Dept."}
        ));
    }

    public static Object[][] listing_currentGet(){
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_04");
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Structure Listing Data (Object[SizeUnknown][6])
            //Get List Count than Skip 5 Empty Lines
            int ListCount = (Integer.parseInt(bf.readLine()))-1;
            for (int i = 0; i < 5; i++) {
                String temp = bf.readLine();
            }
            //Start Getting and Structuring Data
            Object[][] ListingData = new Object[ListCount][6];
            for (int i = 0; i < ListCount; i++) {
                for (int j = 0; j < 6; j++) {
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
