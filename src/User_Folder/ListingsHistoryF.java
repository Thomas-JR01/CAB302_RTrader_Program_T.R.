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

public class ListingsHistoryF {

    private JTable historyTable;
    private JPanel rootpanel_history;

    public JPanel getRootPanel(){
        return rootpanel_history;
    }

    public ListingsHistoryF(Object[][] Data) {
        //CREATE&FILL TABLE
        createTable(Data);
    }

    public void createTable(Object[][] data){

        historyTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Buying/Selling","Resource","Credits","Qty.","Dept.","Date"}
        ));
    }

    public static Object[][] listing_historyGet(){
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_02");
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
