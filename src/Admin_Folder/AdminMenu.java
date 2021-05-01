package Admin_Folder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu {
    private JPanel rootpanel_menu;
    private JButton manageAccountsButton;
    private JButton manageResourcesButton;
    private JButton createDepartmentButton;
    private JButton createAccountButton;
    private JButton helpMessagesButton;


    public JPanel getRootPanel(){
        return rootpanel_menu;
    }

    //Main method & form actions
    public AdminMenu() {

        helpMessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        createDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        manageResourcesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        manageAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


}
