package User_Folder;

import javax.swing.*;

public class UserMain {
    public static void main(String[] args) {
        //Testing ONLY
        //String User = "4"; String Dept = "A";
        //createGUI(User,Dept);
    }

    public static void createGUI(String User, String Dept) {
        ClientMenu ui = new ClientMenu(User,Dept);
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
