package covid19;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDemo {

	 static final String databasePrefix ="covid19";//Name of your database. 
	 static final String netID ="askar"; // User ID by which you connect to the db. Like mysql -u askar -p 
	 static final String hostName ="localhost"; 
	 static final String databaseURL ="jdbc:mysql://"+hostName+"/"+databasePrefix+"?autoReconnect=true&useSSL=false";
	 static final String password="YOUR OWN PASSWORD"; // please enter your own password
    
    public static void main(String args[]) {
                
         Connection connection = null;
                  
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("databaseURL"+ databaseURL);
            connection = DriverManager.getConnection(databaseURL, netID, password);
            System.out.println("Successfully connected to the database");
         }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        finally {
            try {
            connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
