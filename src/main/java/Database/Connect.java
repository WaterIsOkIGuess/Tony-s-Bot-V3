package Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.Connection;
import java.sql.Date;


public class Connect {
	
	 
	
	public Connect(String currentUserString, String currentUser) {

		
		String search;
		Statement pst = null;
		ResultSet result = null;
		int idNumbers = 0;
		int BronzePoints = 0;
		String checkUser = null;
		boolean checkUserBool = false;
		
		
		try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/discordbotprofiles?useSSL=true", "username", "qwe"))
					
			 {
			
			search = "SELECT * FROM users";
					//WHERE ID=\"" + currentUser + "\"";
			pst = conn.prepareStatement(search);
			result = pst.executeQuery(search);
	     
			while (result.next())
		      {
				
		        int id = result.getInt("ListID");
		        idNumbers = id + 1;
		        String firstName = result.getString("ID");
		        checkUser = result.getString("ID");
		        
		        if (checkUser.equalsIgnoreCase(currentUser)) {
		        	checkUserBool = true;
		        	BronzePoints = result.getInt("BRONZE") + 1;
		        }
		        
		       
		        //String lastName = result.getString("last_name");
		        //Date dateCreated = result.getDate("date_created");
		        //boolean isAdmin = result.getBoolean("is_admin");
		        //int numPoints = result.getInt("num_points");
		        
		        // print the results
		        //System.out.format("%s, %s \n", id, firstName);
		      }
			 
			 //System.out.println(currentUser);
			 //System.out.println(checkUser);
			 //System.out.println(idNumbers);
			 
			
            if (conn != null && checkUserBool == false) {
                System.out.println("Connected to the database!");
                
                Statement stmt = null;
                stmt = conn.createStatement();
                
                String sql = "INSERT INTO users (NAME, ID, LVLSTAT, LVL, BRONZE) " +
                             "VALUES (\"" + currentUserString + "\", \"" + currentUser + "\", 0, 0, 0)";

                stmt.executeUpdate(sql);
                System.out.println("Inserted records into the table...");
                
            } else {
            	Statement stmt = null;
            	stmt = (Statement) conn.createStatement();
            	String query1 = "update users set NAME='" + currentUserString + "' " + "where ID=" + currentUser;
            	String query2 = "update users set BRONZE='" + BronzePoints + "' " + "where ID=" + currentUser;
            	stmt.executeUpdate(query1);
            	stmt.executeUpdate(query2);
                //System.out.println("User Updated");
            }
            
            pst.close();
            
            

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
     }
}
