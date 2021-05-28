package Admin_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PasswordResetF_Extended {
    private JPanel rootpanel_PassReset;
    private JButton resetButton;
    private JTextField A_ID_Text;
    private JTextField Password_Text;

    public JPanel getRootPanel(){
        return rootpanel_PassReset;
    }

    public PasswordResetF_Extended() {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String A_ID = A_ID_Text.getText();
                String Password = Password_Text.getText();
                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_17," + A_ID + "," + Password);
                    pr.flush();

                    JOptionPane.showMessageDialog(null,("Reset Password"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,("Unable to Reset Password."));
                }
            }
        });
    }


}
