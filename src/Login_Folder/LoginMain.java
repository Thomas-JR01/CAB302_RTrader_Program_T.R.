package Login_Folder;

import javax.swing.*;
import java.sql.SQLException;

public class LoginMain {

    public static void main(String[] args) throws SQLException {

        //Open GUI - Collect UserInput and Verify Data
        createGUI();

    }

    public static void createGUI() {
        LoginClient ui = new LoginClient();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
