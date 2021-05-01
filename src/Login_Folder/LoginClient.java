package Login_Folder;

import Server_Folder.ServerClient;
import User_Folder.UserMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.net.*;
import java.io.*;

public class LoginClient {
    private JPanel rootPanel;
    private JLabel label1;
    private JButton LC_Button;
    private JLabel label2;
    private JLabel label4;
    private JLabel label3;
    private JPasswordField LC_Pass;
    private JTextField LC_User;


    public JPanel getRootPanel(){
        return rootPanel;
    }

    //Main method & form actions
    public LoginClient() {

        //Login Button Has Been Clicked
        LC_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Login Button Action
                String usernameinput = LC_User.getText();
                String passwordinput = LC_Pass.getText();

                String[] CollectedUserDetails = {usernameinput,passwordinput};

                //CONNECTION TO SERVER CLIENT - REQUESTING AUTHENTICATION FOR "USER DETAILS" & THE OTHER RELEVANT DETAILS
                try {
                    Socket s = new Socket("localhost", 4999);

                    //Send "??" to server
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println("c_01," + usernameinput + "," + passwordinput);
                    pr.flush();


                    //Get Requested Data
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);

                    String[] UserDetails = {"ID","Dept"};
                    UserDetails[0] = bf.readLine();
                    UserDetails[1] = bf.readLine();

                    //Send User To Related Clients Admin(X,Admin), User(X,X)) - Send With Those Details
                    if (UserDetails[1].equals("Admin")) {
                        //Send User to Admin Client
                        System.out.println("Made It Here 2");
                    } else if (!(UserDetails[0].equals("0"))){
                        UserMain.createGUI(UserDetails[0],UserDetails[1]);
                    } else {
                        //Username|Password Incorrect - Not Found in the DB (Send PopUp Message)
                        JOptionPane.showMessageDialog(null,("The Username or Password you entered was incorrect.\nPlease Try Again"));
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });

    }



}
