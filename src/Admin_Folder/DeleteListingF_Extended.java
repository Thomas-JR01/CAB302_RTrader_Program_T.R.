package Admin_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DeleteListingF_Extended {
    private JPanel rootpanel_DelListing;
    private JTextField ListingID_Text;
    private JButton deleteButton;

    public JPanel getRootPanel(){
        return rootpanel_DelListing;
    }

    public DeleteListingF_Extended() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String L_ID = ListingID_Text.getText();
                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_08," + L_ID);
                    pr.flush();

                    JOptionPane.showMessageDialog(null,("Deleted Listing"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,("Unable to Delete Listing."));
                }
            }
        });
    }


}
