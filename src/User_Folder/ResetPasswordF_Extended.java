package User_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ResetPasswordF_Extended {
    private JPanel rootpanel_resetpass;
    private JButton reset_button;
    private JPasswordField current_passfield;
    private JPasswordField new_passfield;


    public JPanel getRootPanel(){
        return rootpanel_resetpass;
    }

    public ResetPasswordF_Extended(String UserID) {
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get Text Input
                String curPass = current_passfield.getText();
                String newPass = new_passfield.getText();

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_07," + UserID + "," + curPass + "," + newPass); //ExCode,UserID,CurrentPassword,NewPassword
                    pr.flush();

                    //Get Requested Data
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    int Outcome = Integer.parseInt(bf.readLine());

                    //Tell User 0=Incorrect 1=Changed
                    if (Outcome == 0) {
                        JOptionPane.showMessageDialog(null,("The Password you entered was incorrect.\nPlease Try Again"));
                    } else if (Outcome == 1) {
                        JOptionPane.showMessageDialog(null,("Your Password was changed."));
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
