package User_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ActionListingF_Extended {
    private JPanel rootpanel_action;
    private JButton action_button;
    private JTextField listingID_text;


    public JPanel getRootPanel(){
        return rootpanel_action;
    }

    public ActionListingF_Extended(String Dept) {
        action_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ListingID = listingID_text.getText();

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_09," + Dept + "," + ListingID); //ExCode,Dept,ListingID
                    pr.flush();

                    //Get Requested Data
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    int Outcome = Integer.parseInt(bf.readLine());

                    //Tell User 0=Incorrect 1=Changed
                    if (Outcome == 0) {
                        JOptionPane.showMessageDialog(null,("Unable to action listing."));
                    } else if (Outcome == 1) {
                        JOptionPane.showMessageDialog(null,("Listing was actioned."));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
