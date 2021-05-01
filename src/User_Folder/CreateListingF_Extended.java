package User_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CreateListingF_Extended {
    private JPanel rootpanel_createlisting;
    private JButton createlisting_button;
    private JTextField credits_text;
    private JComboBox bs_cbox;
    private JTextField qty_text;
    private JTextField resource_text;


    public JPanel getRootPanel(){
        return rootpanel_createlisting;
    }

    public CreateListingF_Extended(String Dept) {
        createlisting_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Credits = credits_text.getText();
                Object BS_Status = bs_cbox.getSelectedItem();
                String Qty = qty_text.getText();
                String Item = resource_text.getText();
                String SendMsg = "c_10," + Dept + "," +  BS_Status + "," +  Item + "," +  Qty + "," +  Credits;

                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println(SendMsg); //ExCode,Dept,BS_Status,Item,Qty,Credits
                    pr.flush();

                    //Get Requested Data
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    int Outcome = Integer.parseInt(bf.readLine());

                    //Tell User 0=Incorrect 1=Changed
                    if (Outcome == 0) {
                        JOptionPane.showMessageDialog(null,("Unable to create listing."));
                    } else if (Outcome == 1) {
                        JOptionPane.showMessageDialog(null,("Listing was created."));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
    }
}
