package Admin_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DeleteAccountF_Extended {
    private JPanel rootpanel_DelAccount;
    private JTextField A_ID_Text;
    private JButton deleteButton;

    public JPanel getRootPanel(){
        return rootpanel_DelAccount;
    }

    public DeleteAccountF_Extended() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String A_ID = A_ID_Text.getText();
                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_18," + A_ID);
                    pr.flush();

                    JOptionPane.showMessageDialog(null,("Account Deleted"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,("Unable to Delete Account."));
                }
            }
        });
    }


}
