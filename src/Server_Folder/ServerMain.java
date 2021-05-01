package Server_Folder;

import javax.swing.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import java.time.LocalDate;

public class ServerMain {


    public static void main(String[] args) throws IOException {
        System.out.println(LocalDate.now());
        mainSent();
    }


    public static  void mainSent() throws IOException {
        // Test Output
        System.out.println("Successfully Executed Main Method");

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
