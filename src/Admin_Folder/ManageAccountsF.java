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

public class ManageAccountsF {
    private JPanel rootpanel_MAccounts;
    private JTable Accounts_table;
    private JButton resetPasswordButton;
    private JButton deleteAccountButton;

    public JPanel getRootPanel(){
        return rootpanel_MAccounts;
    }

    public static Object[][] accounts_Get() {
        try {
            Socket s = new Socket("localhost", 4999);

            //Send "??" to server
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("c_16");
            pr.flush();

            //Get Requested Data
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //Structure Account Data (Object[SizeUnknown][3])
            //Get List Count than Skip 2 Empty Lines
            int ListCount = (Integer.parseInt(bf.readLine()))-1;
            for (int i = 0; i < 2; i++) {
                String temp = bf.readLine();
            }
            //Start Getting and Structuring Data
            Object[][] AccountData = new Object[ListCount][3];
            for (int i = 0; i < ListCount; i++) {
                for (int j = 0; j < 3; j++) {
                    AccountData[i][j] = bf.readLine();
                }
            }

            return AccountData;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    public void createTable(Object[][] data){
        Accounts_table.setModel(new DefaultTableModel(
                data,
                new String[]{"ID","Username","Department"}
        ));
    }

    public ManageAccountsF(Object[][] data) {
        createTable(data);
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordResetF_Extended ui = new PasswordResetF_Extended();
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteAccountF_Extended ui = new DeleteAccountF_Extended();
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
}
