package Admin_Folder;

import Login_Folder.LoginClient;

import javax.swing.*;

public class AdminMain {

    public static void main(String[] args) {
        createGUI();
    }

    public static void createGUI() {
        AdminMenu ui = new AdminMenu();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
