package Database;

	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.OutputStream;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;
	import java.util.Properties;
	import java.sql.Connection;

public class createTable {

	

		public createTable(String currentUser) {
			
			final String SQL_CREATE = "CREATE TABLE USERS"
		            + "("
		            + " NAME varchar(100) NOT NULL,"
		            + " SALARY numeric(15, 2) NOT NULL"
		            + ")";
			
			try (Connection conn = DriverManager.getConnection(
	                "jdbc:mysql://localhost/", "username", "password"); 
				
				PreparedStatement preparedStatement = conn.prepareStatement(SQL_CREATE)) {

		            preparedStatement.execute();

	            if (conn != null) {
	                System.out.println("Connected to the database!");
	            } else {
	                System.out.println("Failed to make connection!");
	            }
	            
	            

	        } catch (SQLException e) {
	            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			
	     }
	}

	

