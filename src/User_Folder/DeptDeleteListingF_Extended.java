package User_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DeptDeleteListingF_Extended {
    private JPanel rootpanel_dellisting;
    private JTextField listid_textbox;
    private JButton delete_button;


    public JPanel getRootPanel(){
        return rootpanel_dellisting;
    }

    public DeptDeleteListingF_Extended(Object[][] DeptListings) {
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get Text Input
                String ListingID = listid_textbox.getText();
                try {
                    Socket s = new Socket("localhost", 4999);

                    //Check ListingID with the department(x) listings
                    int Outcome = 0;
                    for (int i = 0; i < DeptListings.length; i++) {
                        if (DeptListings[i][0].equals(ListingID)) {
                            //Send "??" to server
                            Outcome = 1;
                            PrintWriter pr = new PrintWriter(s.getOutputStream());
                            pr.println("c_08," + ListingID); //ExCode,ListingID
                            pr.flush();
                            JOptionPane.showMessageDialog(null,("Listing " + ListingID + " Deleted."));
                        }
                    }

                    if (Outcome == 0) {
                        JOptionPane.showMessageDialog(null,("You don't have permission for this action."));
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
