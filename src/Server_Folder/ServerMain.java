package Server_Folder;

import javax.swing.*;
import java.sql.*;
import java.io.*;

public class ServerMain {

//  public static void main(String[] args) throws IOException { mainSent(); }

    public static  void mainSent() throws IOException {
        // Open Main "Server Client" Form/GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                try {
                    createGUI();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        ProgramExLibrary.ExLibrary();
    }

    public static void createGUI() throws SQLException {
        Object[][] listData = Server_DBConnect.DB_Connect();
        ServerClient ui = new ServerClient(listData);
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
