package Server_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerLoginClient {
    private JButton LC_Button;
    private JTextField LC_User;
    private JPasswordField LC_Pass;
    private JPanel rootPanel;

    public ServerLoginClient() {
        //Login Button Has Been Clicked
        LC_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Login Button Action
                String usernameinput = LC_User.getText();
                String passwordinput = LC_Pass.getText();

                String[] CollectedUserDetails = {usernameinput,passwordinput};
                String ActualUsername = "server"; String ActualPassword = "root";

                //Send User To Related Clients (Server(1,Server), Admin(X,Admin), User(X,X)) - Send With Those Details
                if ((CollectedUserDetails[0].equals(ActualUsername)) && (CollectedUserDetails[1].equals(ActualPassword))){
                    //Send User to Server Client
                    try {
                        ServerMain.mainSent();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public JPanel getRootPanel(){
        return rootPanel;
    }

    public static void main(String[] args) {
        //Open GUI - Collect UserInput and Verify Data
        createGUI();
    }

    public static void createGUI() {
        ServerLoginClient ui = new ServerLoginClient();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
