package Admin_Folder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HelpMessagesF {
    private JPanel rootpanel_help;
    private JTable HelpMsgTable;

    public JPanel getRootPanel(){
        return rootpanel_help;
    }

    public HelpMessagesF(Object[][] Data) {
        //CREATE&FILL TABLE
        createTable(Data);
    }

    public void createTable(Object[][] data){
        HelpMsgTable.setModel(new DefaultTableModel(
                data,
                new String[]{"User","Message","Date"}
        ));
    }

    public static Object[][] messagesGet() {
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_11");
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Start Getting and Structuring Data
            Object[][] MessageData = new Object[10][3];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 3; j++) {
                    MessageData[i][j] = bf.readLine();
                }
            }

            return MessageData;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

}
