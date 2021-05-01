package Server_Folder;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

public class ProgramExLibrary extends Thread{

    String url = "jdbc:mariadb://127.0.0.1:3306/rtraderdb"; String user = "root"; String pwd = "cab302";

    public static void ExLibrary() {
        ProgramExLibrary thread = new ProgramExLibrary();
        thread.start();
    }

    public void run() {

        //Basic Connection Established
        ServerSocket ss = null;
        String RequestStr = "";
        //Forever Loop
        try {
            ss = new ServerSocket(4999);

            while (true) {
                Socket s = ss.accept();
                System.out.println("Client Connected...");

                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                RequestStr = bf.readLine();
                System.out.println("Client : " + RequestStr);


                //Library of Commands: ---------------------------------------------
                Object[][] Output = null;
                String[] RequestSplit = RequestStr.split(",");
                String Req = RequestSplit[0];

                //make switch case https://www.w3schools.com/java/java_switch.asp
                if (Req.equals("c_01")) {
                    Output = LoginEx(RequestStr);
                } else if (Req.equals("c_02")) {
                    Output = ClientHistoryEx(RequestStr);
                } else if (Req.equals("c_03")) {
                    Output = UserDeptDetailsEx(RequestStr);
                } else if (Req.equals("c_04")) {
                    Output = CurrentListingsEx(RequestStr);
                } else if (Req.equals("c_05")) {
                    Output = DepartmentListingsEx(RequestStr);
                } else if (Req.equals("c_06")) {
                    ClientHelpEx(RequestStr);
                } else if (Req.equals("c_07")) {
                    Output = ClientResetPasswordEx(RequestStr);
                } else if (Req.equals("c_08")) {
                    DeptDeleteListingEx(RequestStr);
                } else if (Req.equals("c_09")) {
                    Output = ActionListingEx(RequestStr);
                } else if (Req.equals("c_10")) {
                    Output = CreateListingEx(RequestStr);
                }




                //Send Back Requested Data
                PrintWriter pr = new PrintWriter(s.getOutputStream());
                int os1 = Output.length;
                int os2 = Output[0].length;
                for (int i = 0; i < os1; i++) {
                    for (int j = 0; j < os2; j++) {
                        pr.println(Output[i][j]);
                        pr.flush();
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    //------------------------------------------------------------------------------------------------------------------

    public Object[][] LoginEx(String Request) throws SQLException {
        //Request: 'ExCode,Username,Password' | Authenticate the user, then give back ID and Dept
        //Break Down Request
        String[] UserInputDetails = Request.split(",");
        String testUser = UserInputDetails[1]; String testPass = UserInputDetails[2];

        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls User data from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM users");

        //Information we want to collect
        int UserID = 0;
        String UserDept = "0";

        while (rs1.next()){
            //Find the user that has been entered.
            String dbUser = rs1.getString("Username");
            if (testUser.equals(dbUser)) {
                //User has been found now check password
                String dbPass = rs1.getString("Password");
                if (testPass.equals(dbPass)) {
                    //Password matches Username --> now collect UserID & Department
                    UserID = rs1.getInt("UserID");
                    UserDept = rs1.getString("Department");
                    break;
                }
            }
        }

        //Return User Details (ID,Department)
        Object[][] UserDetails = {{Integer.toString(UserID),UserDept}};
        return UserDetails;
    }


    public Object[][] ClientHistoryEx(String Request) throws SQLException {
        //Request: 'ExCode' | Give all Non-Active Listings and their information

        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls Count of listings from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM listings WHERE ActiveStatus = 1");
        rs1.next(); int listcount = (rs1.getInt("COUNT(*)"));

        Object[][] ListingData = new Object[listcount+1][6];

        //ListingData[0][0] = Count of Lists + 1
        ListingData[0][0] = Integer.toString((listcount+1));

        //Pulls Data From DB and put in Object Array & make sure they are not active (ActiveStatus = 1)
        Statement stmt2 = connection.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM listings WHERE ActiveStatus = 1");
        int i = 1;
        while (rs2.next()){
            //Is listing buying or selling (0 = Buy|1 = Sell), then place in Array - ListingData
            int BS = rs2.getInt("BS_Status");
            if (BS == 0){
                ListingData[i][0] = "Buying";
            } else if (BS == 1){
                ListingData[i][0] = "Selling";
            }
            //Place in Array - ListingData
            ListingData[i][1] = rs2.getString("Resource");
            ListingData[i][2] = rs2.getInt("Credits");
            ListingData[i][3] = rs2.getInt("Quantity");
            ListingData[i][4] = rs2.getString("Department");
            ListingData[i][5] = rs2.getDate("CDate");

            i++;
        }

        //Return ListingData (BS_Status,Resource,Credits,Quantity,Department)
        return ListingData;
    }


    public Object[][] UserDeptDetailsEx(String Request) throws SQLException {
        //Request: 'ExCode,UserID,Department' | Give back username and dept credit no. & list of departments items/qty
        //Break Down Request
        String[] ReqDetails = Request.split(",");
        //User Details
        String UserID = ReqDetails[1]; String Department = ReqDetails[2];

        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls Count of items of dept from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM departmentsinfo WHERE NOT Item = 'Credits' AND Department = '" + Department + "'");
        rs1.next(); int listcount = (rs1.getInt("COUNT(*)"));

        Object[][] DepartmentData = new Object[listcount+1][2];

        //Pull & Place Department Items&Qty From DB and Place in DepartmentData[1+][0&1]
        Statement stmt2 = connection.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM departmentsinfo WHERE NOT Item = 'Credits' AND Department = '" + Department + "'");
        int i = 1;
        while (rs2.next()){
            //Place in Array - ListingData
            DepartmentData[i][0] = rs2.getString("Item");
            DepartmentData[i][1] = Integer.toString(rs2.getInt("Quantity"));
            i++;
        }

        //Pull & Place Username+DeptCredits From DB and place in DepartmentData[0][0&1]
        //Get DeptCredits
        Statement stmt3 = connection.createStatement();
        ResultSet rs3 = stmt3.executeQuery("SELECT * FROM departmentsinfo WHERE Item = 'Credits' AND Department = '" + Department + "'");
        rs3.next(); String credtemp = Integer.toString(rs3.getInt("Quantity"));
        //Get Username
        Statement stmt4 = connection.createStatement();
        ResultSet rs4 = stmt4.executeQuery("SELECT * FROM users WHERE UserID = '" + UserID + "'");
        rs4.next(); String usertemp = rs4.getString("Username");
        DepartmentData[0][0] = Integer.toString(listcount); DepartmentData[0][1] = (credtemp + "," + usertemp);


        //Return DepartmentData ((0)Username,Dept.Credits|(1+)DeptItems,Qty)
        return DepartmentData;
    }


    public Object[][] CurrentListingsEx(String Request) throws SQLException {
        //Request: 'ExCode' | Give ActiveListings (ID,Buy/Sell,Resource,Credits,Quantity,Department)
        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls Count of listings from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM listings WHERE ActiveStatus = 0");
        rs1.next(); int listcount = (rs1.getInt("COUNT(*)"))+1;

        Object[][] ListingData = new Object[listcount][6];

        //Pulls Data From DB and put in Object Array & make sure they are active (ActiveStatus = 0)
        Statement stmt2 = connection.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM listings WHERE ActiveStatus = 0");
        ListingData[0][0] = Integer.toString(listcount);
        int i = 1;
        while (rs2.next()){
            //Place in Array - ListingData
            ListingData[i][0] = Integer.toString(rs2.getInt("ListingID"));
            //Is listing buying or selling (0 = Buy|1 = Sell), then place in Array - ListingData
            int BS = rs2.getInt("BS_Status");
            if (BS == 0){
                ListingData[i][1] = "Buying";
            } else if (BS == 1){
                ListingData[i][1] = "Selling";
            }
            ListingData[i][2] = rs2.getString("Resource");
            ListingData[i][3] = Integer.toString(rs2.getInt("Credits"));
            ListingData[i][4] = Integer.toString(rs2.getInt("Quantity"));
            ListingData[i][5] = rs2.getString("Department");
            i++;
        }

        //Return ListingData (ID,Buy/Sell,Resource,Credits,Quantity,Department)
        return ListingData;
    }


    public Object[][] DepartmentListingsEx(String Request) throws SQLException {
        //Request: 'ExCode,Dept.' | Give Department X ActiveListings (ID,Buy/Sell,Resource,Credits,Quantity)
        //Break Down Request
        String[] ReqDetails = Request.split(","); String Department = ReqDetails[1];
        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls Count of listings from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM listings WHERE ActiveStatus = 0 AND Department = '" + Department + "'");
        rs1.next(); int listcount = (rs1.getInt("COUNT(*)"))+1;

        Object[][] ListingData = new Object[listcount][5];

        //Pulls Data From DB and put in Object Array & make sure they are active (ActiveStatus = 0)
        Statement stmt2 = connection.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM listings WHERE ActiveStatus = 0 AND Department = '" + Department + "'");
        ListingData[0][0] = Integer.toString(listcount);
        int i = 1;
        while (rs2.next()){
            //Place in Array - ListingData
            ListingData[i][0] = Integer.toString(rs2.getInt("ListingID"));
            //Is listing buying or selling (0 = Buy|1 = Sell), then place in Array - ListingData
            int BS = rs2.getInt("BS_Status");
            if (BS == 0){
                ListingData[i][1] = "Buying";
            } else if (BS == 1){
                ListingData[i][1] = "Selling";
            }
            ListingData[i][2] = rs2.getString("Resource");
            ListingData[i][3] = Integer.toString(rs2.getInt("Credits"));
            ListingData[i][4] = Integer.toString(rs2.getInt("Quantity"));
            i++;
        }

        //Return ListingData (ID,Buy/Sell,Resource,Credits,Quantity)
        return ListingData;
    }


    public void ClientHelpEx(String Request) throws SQLException {
        //Request: 'ExCode,UserID,Message' | Paste in 'Helpmsg' DB (Message,User,Date)
        //Break Down Request
        String[] ReqDetails = Request.split(","); String UserID = ReqDetails[1]; String UserMessage = ReqDetails[2];
        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls Username from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM users WHERE UserID = '" + UserID + "';");
        rs1.next(); String Username = rs1.getString("Username");

        //Place Username,Message,Current_Date in "helpmsg' db
        Statement stmt2 = connection.createStatement();
        stmt2.executeQuery("INSERT INTO helpmsg (Username,Message,C_Date) VALUES ('" + Username + "','" + UserMessage + "','" + LocalDate.now() + "');");
        connection.commit();
    }


    public Object[][] ClientResetPasswordEx(String Request) throws SQLException {
        //Request: 'ExCode,UserID,CurrentPassword,NewPassword' | Give Confirmation (0/1) | Change UserID Password --> NewPassword
        //Break Down Request
        String[] ReqDetails = Request.split(","); String UserID = ReqDetails[1]; String CurrentPassword = ReqDetails[2]; String NewPassword = ReqDetails[3];
        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Pulls Users Password from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM users WHERE UserID = '" + UserID + "';");
        rs1.next(); String DBCurrentPassword = rs1.getString("Password");

        //Check DBPassword = CurrentPassword Input
        String OpConfirmation = "0"; //'0' incorrect password
        if (DBCurrentPassword.equals(CurrentPassword)) {
            //Change Users Password to NewPassword in DB
            Statement stmt2 = connection.createStatement();
            stmt2.executeQuery("UPDATE users SET Password = '" + NewPassword + "' WHERE UserID = " + UserID + ";");
            connection.commit();
            OpConfirmation = "1";
        }

        //Return Confirmation Process took place | 0 = false 1 = true
        Object[][] OpConfirmationO = {{OpConfirmation}};
        return OpConfirmationO;
    }


    public void DeptDeleteListingEx(String Request) throws SQLException {
        //Request: 'ExCode,ListingID' | Change ListingID ActiveStatus --> 1(NotActive)
        //Break Down Request
        String[] ReqDetails = Request.split(","); String ListingID = ReqDetails[1];
        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Return Resources Back to Department
        //Pull Listing Info
        Statement stmt2 = connection.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM listings WHERE ListingID = " + ListingID + ";"); rs2.next();
        String ListingStatus = Integer.toString(rs2.getInt("ActiveStatus"));
        String ListingDept = rs2.getString("Department");
        String ListingBS = Integer.toString(rs2.getInt("BS_Status"));
        String ListingResource = rs2.getString("Resource");
        int ListingCredits = rs2.getInt("Credits"); //CreditsPerItem
        int ListingQty = rs2.getInt("Quantity");


        if (ListingStatus.equals("0")) {
            //Buy(0) or Sell(1)
            String sqlGiveListerQuery = "";
            if (ListingBS.equals("0")) {
                //Get Listing Departments Data (Credits&Resources)
                Statement stmt4 = connection.createStatement();
                ResultSet rs4 = stmt4.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + ListingDept + "' AND Item = 'Credits';");
                rs4.next(); int ListDeptCredits = rs4.getInt("Quantity");
                sqlGiveListerQuery = "UPDATE departmentsinfo SET Quantity = " + (ListDeptCredits + (ListingCredits * ListingQty)) + " WHERE Department = '" + ListingDept + "' AND Item = 'Credits';";
            } else if (ListingBS.equals("1")) {
                //Get Listing Departments Data (Credits&Resources)
                Statement stmt5 = connection.createStatement();
                ResultSet rs5 = stmt5.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + ListingDept + "' AND Item = '" + ListingResource + "';");
                rs5.next(); int ListDeptItemQty = rs5.getInt("Quantity");
                sqlGiveListerQuery = "UPDATE departmentsinfo SET Quantity = " + (ListDeptItemQty + ListingQty) + " WHERE Department = '" + ListingDept + "' AND Item = '" + ListingResource + "';";
            }
            //Change ListingID to not active & Give back credits or resources
            Statement stmt1 = connection.createStatement();
            stmt1.executeQuery("UPDATE listings SET ActiveStatus = 1 WHERE ListingID = " + ListingID + ";");
            stmt1.executeQuery(sqlGiveListerQuery);
            connection.commit();
        }


    }

    public Object[][] ActionListingEx(String Request) throws SQLException {
        //Request: 'ExCode,Dept,ListingID' | Give Confirmation (0/1)
        //Change: ListingID ActiveStatus --> 1(NotActive)| Buying(UserSelling)(Dept. -Resource(X) & +Credits) OR Selling(UserBuying)(Dept. +Resource(X) & -Credits)
        //Break Down Request
        String[] ReqDetails = Request.split(","); String Department = ReqDetails[1]; String ListingID = ReqDetails[2];
        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Run Check (Listing is Active(0))&(Department != ListingID.Dept.)&(Buying or Selling)
        //Pull Listing Info
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM listings WHERE ListingID = " + ListingID + ";"); rs1.next();
        String ListingStatus = Integer.toString(rs1.getInt("ActiveStatus"));
        String ListingDept = rs1.getString("Department");
        String ListingBS = Integer.toString(rs1.getInt("BS_Status"));
        String ListingResource = rs1.getString("Resource");
        int ListingCredits = rs1.getInt("Credits"); //CreditsPerItem
        int ListingQty = rs1.getInt("Quantity");

        //Run Check
        String OpConfirmation = "0"; //'0' invalid information
        String sqlCreditsQuery = "SELECT COUNT(*) FROM listings"; //p
        String sqlResourceQuery = "SELECT COUNT(*) FROM listings"; //p
        String sqlActiveQuery = "SELECT COUNT(*) FROM listings"; //p
        String sqlGiveListerQuery = "SELECT COUNT(*) FROM listings"; //p (Give the original lister credits or items)

        //CHECK WHERE ERROR
        System.out.println("Made It Here ActionListingEx --> 3");
        System.out.println(ListingStatus + "\n" + ListingDept + Department);

        if ((ListingStatus.equals("0")) && (!(ListingDept.equals(Department)))) {
            //Get Department Data (Credits&Resources)
            Statement stmt2 = connection.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + Department + "' AND Item = 'Credits';");
            rs2.next(); int DeptCredits = rs2.getInt("Quantity");
            int DeptItemQty = 0;
            try {
                Statement stmt3 = connection.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + Department + "' AND Item = '" + ListingResource + "';");
                rs3.next(); DeptItemQty = rs3.getInt("Quantity");
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            //Get Listing Departments Data (Credits&Resources)
            Statement stmt4 = connection.createStatement();
            ResultSet rs4 = stmt4.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + ListingDept + "' AND Item = 'Credits';");
            rs4.next(); int ListDeptCredits = rs4.getInt("Quantity");
            int ListDeptItemQty = 0;
            try {
                Statement stmt5 = connection.createStatement();
                ResultSet rs5 = stmt5.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + ListingDept + "' AND Item = '" + ListingResource + "';");
                rs5.next(); ListDeptItemQty = rs5.getInt("Quantity");
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            //CHECK WHERE ERROR
            System.out.println("Made It Here ActionListingEx --> 4");

            //Buy(0) or Sell(1)
            if (ListingBS.equals("0")) {
                //-Resource(X) & +Credits | Check Department Can Afford
                //Check Dept Has the Resources --> Take Resources & Give Credits
                if (DeptItemQty >= ListingQty) {
                    sqlActiveQuery = "UPDATE listings SET ActiveStatus = 1 WHERE ListingID = " + ListingID + ";";
                    sqlResourceQuery = "UPDATE departmentsinfo SET Quantity = " + (DeptItemQty - ListingQty) + " WHERE Department = '" + Department + "' AND Item = '" + ListingResource + "';";
                    sqlCreditsQuery = "UPDATE departmentsinfo SET Quantity = " + (DeptCredits + (ListingCredits * ListingQty)) + " WHERE Department = '" + Department + "' AND Item = 'Credits';";
                    //Give the original lister credits //ListingDept
                    if (ListDeptItemQty != 0) { //Add Resource QTY
                        sqlGiveListerQuery = "UPDATE departmentsinfo SET Quantity = " + (ListDeptItemQty + ListingQty) + " WHERE Department = '" + ListingDept + "' AND Item = '" + ListingResource + "';";
                    } else { //Insert Resource QTY
                        sqlGiveListerQuery = "INSERT INTO departmentsinfo (Department,Item,Quantity) VALUES ('" + ListingDept + "','" + ListingResource + "'," + ListingQty + ");";
                    }
                    OpConfirmation = "1";

                    //CHECK WHERE ERROR
                    System.out.println("Made It Here ActionListingEx --> 5.1");
                }
            } else if (ListingBS.equals("1")) {
                //+Resource(X) & -Credits | Check Department Can Afford
                //Check Dept Has the Credits --> Take Credits
                if (DeptCredits >= (ListingCredits*ListingQty)) {
                    sqlActiveQuery = "UPDATE listings SET ActiveStatus = 1 WHERE ListingID = " + ListingID + ";";
                    sqlCreditsQuery = "UPDATE departmentsinfo SET Quantity = " + (DeptCredits - (ListingCredits * ListingQty)) + " WHERE Department = '" + Department + "' AND Item = 'Credits';";
                    if (DeptItemQty != 0) { //Add Resource QTY
                        sqlResourceQuery = "UPDATE departmentsinfo SET Quantity = " + (DeptItemQty + ListingQty) + " WHERE Department = '" + Department + "' AND Item = '" + ListingResource + "';";
                    } else { //Insert Resource QTY
                        sqlResourceQuery = "INSERT INTO departmentsinfo (Department,Item,Quantity) VALUES ('" + Department + "','" + ListingResource + "'," + ListingQty + ");";
                    }
                    //Give the original lister credits //ListingDept
                    sqlGiveListerQuery = "UPDATE departmentsinfo SET Quantity = " + (ListDeptCredits + (ListingCredits * ListingQty)) + " WHERE Department = '" + ListingDept + "' AND Item = 'Credits';";
                    OpConfirmation = "1";

                    //CHECK WHERE ERROR
                    System.out.println("Made It Here ActionListingEx --> 5.2");
                }
            }
            //Run Update Query (Credits) | Run Insert/Update Query (Resource) | Run Update Query (Listing_ActiveStatus) | Run Give Original Lister Query
            //Get Listing Departments Data (Credits&Resources)
            Statement stmt6 = connection.createStatement();
            stmt6.executeQuery(sqlCreditsQuery);
            stmt6.executeQuery(sqlResourceQuery);
            stmt6.executeQuery(sqlActiveQuery);
            stmt6.executeQuery(sqlGiveListerQuery);
            connection.commit();

            //CHECK WHERE ERROR
            System.out.println("Made It Here ActionListingEx --> 6");
        }

        //Return Confirmation Process took place | 0 = false 1 = true
        Object[][] OpConfirmationO = {{OpConfirmation}};

        //CHECK WHERE ERROR
        System.out.println("Made It Here ActionListingEx --> 7");

        return OpConfirmationO;
    }


    public Object[][] CreateListingEx(String Request) throws SQLException {
        //Request: 'ExCode,Dept,BS_Status,Resource,Qty,Credits' | Give Confirmation (0/1)
        //Add: Listing --> ListingID(x+1),BS_Status(0/1),Resource,Credits,Quantity,Department,CDate,ActiveStatus(0)
        //Break Down Request
        String[] ReqDetails = Request.split(",");
        String Department = ReqDetails[1]; String Resource = ReqDetails[3]; String Quantity = ReqDetails[4]; String Credits = ReqDetails[5]; String BS_Status = "";
        if (ReqDetails[2].equals("Buying")) { BS_Status = "0"; } else { BS_Status = "1"; } //Buy=0,Sell=1

        //Connect & Get DB Data
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, pwd);

        //Check that the Department has the required resources/credits to list then take them
        //Get Departments Data (Credits&Resources)
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + Department + "' AND Item = 'Credits';");
        rs1.next(); int DeptTotalCredits = rs1.getInt("Quantity");
        int DeptTotalItemQty = 0;
        try {
            Statement stmt2 = connection.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM departmentsinfo WHERE Department = '" + Department + "' AND Item = '" + Resource + "';");
            rs2.next(); DeptTotalItemQty = rs2.getInt("Quantity");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        //Query To Change Department Details
        String sqlDeptQuery = "";
        //Operation Status 0 = False
        String OpConfirmation = "0";

        //Buy(0) or Sell(1)
        if (BS_Status.equals("0")) {
            //Check Department has enough credits
            //Check it here
            if (DeptTotalCredits >= Integer.parseInt(Credits)) {
                sqlDeptQuery = "UPDATE departmentsinfo SET Quantity = " + (DeptTotalCredits - (Integer.parseInt(Quantity) * Integer.parseInt(Credits))) + " WHERE Department = '" + Department + "' AND Item = 'Credits';";
                OpConfirmation = "1";
            }
        } else if (BS_Status.equals("1")) {
            //Check Department has enough x resources
            if (DeptTotalCredits >= Integer.parseInt(Credits)) {
                sqlDeptQuery = "UPDATE departmentsinfo SET Quantity = " + (DeptTotalItemQty - Integer.parseInt(Quantity)) + " WHERE Department = '" + Department + "' AND Item = '" + Resource + "';";
                OpConfirmation = "1";
            }
        }

        //Create the listing
        String sqlListingQuery = "";
        if (OpConfirmation.equals("1")) {
            sqlListingQuery = "INSERT INTO listings (BS_Status,Resource,Credits,Quantity,Department,CDate,ActiveStatus) VALUES (" + BS_Status + ",'" + Resource + "'," + Credits + "," + Quantity + ",'" + Department + "','" + LocalDate.now() + "',0);";
        }

        //Enters Info to DB
        Statement stmt3 = connection.createStatement();
        stmt3.executeQuery(sqlDeptQuery);
        stmt3.executeQuery(sqlListingQuery);
        connection.commit();


        //Return Confirmation Process took place | 0 = false 1 = true
        Object[][] OpConfirmationO = {{OpConfirmation}};
        return OpConfirmationO;
    }


















}
