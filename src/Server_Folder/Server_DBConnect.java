package Server_Folder;

import java.sql.*;

public class Server_DBConnect {

    public static Object[][] DB_Connect() throws SQLException {
        //Connect to Maria Database
        Connection connection = null;
        String url = "jdbc:mariadb://127.0.0.1:3306/rtraderdb";
        String user = "root";
        String pwd = "cab302";

        connection = DriverManager.getConnection(url, user, pwd);
        System.out.println("Connected to database successfully...");

        //Pulls Count of listings from DB
        Statement stmt1 = connection.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM listings WHERE ActiveStatus = 0");
        rs1.next(); int listcount = (rs1.getInt("COUNT(*)"));

        Object[][] ListingData = new Object[listcount][5];

        //Pulls Data From DB and put in Object Array & make sure they are active (ActiveStatus = 0)
        Statement stmt2 = connection.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM listings WHERE ActiveStatus = 0");
        int i = 0;
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
            Date temp = rs2.getDate("CDate");

            i++;
        }

        return ListingData;
    }



}

