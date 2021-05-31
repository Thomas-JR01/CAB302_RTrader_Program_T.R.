package Server_Folder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerClient {
    private JButton SC_B_Quit;
    private JPanel rootPanel;
    private JTable SC_Table;
    private JLabel SC_L_11;

    public JPanel getRootPanel(){
        return rootPanel;
    }

    public ServerClient(Object[][] Data) {
        //QUIT APPLICATION BUTTON FEATURE
        SC_B_Quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //CREATE/FILL TABLE
        createTable(Data);
    }

    public void createTable(Object[][] data){
        SC_Table.setModel(new DefaultTableModel(
                data,
                new String[]{"Buying/Selling","Resource","Credits","Qty.","Dept."}
        ));
    }

}
