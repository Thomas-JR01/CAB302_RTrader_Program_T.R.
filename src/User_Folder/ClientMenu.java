package User_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientMenu {
    private JButton userDetailsButton;
    private JButton currentListingsButton;
    private JButton listingsHistoryButton;
    private JButton departmentListingsButton;
    private JButton helpButton;
    private JPanel rootpanel_menu;

    public JPanel getRootPanel(){
        return rootpanel_menu;
    }

    public ClientMenu(String User, String Dept) {

        //"LISTINGS HISTORY" BUTTON ACTION
        listingsHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] listData = ListingsHistoryF.listing_historyGet();
                ListingsHistoryF ui = new ListingsHistoryF(listData);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        //"USER DETAILS" BUTTON ACTION
        userDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDetailsF ui = new UserDetailsF(UserDetailsF.DepartmentDetailsGet(User,Dept),UserDetailsF.UserDetailsGet(User,Dept),User);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        //"CURRENT LISTINGS" BUTTON ACTION
        currentListingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentListingsF ui = new CurrentListingsF(CurrentListingsF.listing_currentGet(),Dept);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        //"DEPARTMENT LISTINGS" BUTTON ACTION
        departmentListingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepartmentListingsF ui = new DepartmentListingsF(DepartmentListingsF.listing_departmentGet(Dept));
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        //"HELP" BUTTON ACTION
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpF ui = new HelpF(User);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });






    }






}
