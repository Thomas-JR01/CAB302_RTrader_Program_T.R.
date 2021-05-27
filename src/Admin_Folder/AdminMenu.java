package Admin_Folder;

import User_Folder.ListingsHistoryF;

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
                Object[][] msgData = HelpMessagesF.messagesGet();
                HelpMessagesF ui = new HelpMessagesF(msgData);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] DeptData = CreateAccountF.departmentGet();
                CreateAccountF ui = new CreateAccountF(DeptData);
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        createDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateDepartmentF ui = new CreateDepartmentF();
                JPanel root = ui.getRootPanel();
                JFrame frame = new JFrame("X Corporation - Asset Trading Platform");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
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
