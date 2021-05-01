package User_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HelpF {
    private JPanel rootpanel_help;
    private JTextArea message_textbox;
    private JButton send_button;

    public JPanel getRootPanel(){
        return rootpanel_help;
    }

    public HelpF(String UserID) {
        send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageText = message_textbox.getText();

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_06," + UserID + "," + messageText); //ExCode,UserID,Message
                    pr.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }



}
