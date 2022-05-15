package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.Part;


public class User {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			
			// Provide the correct details: DBServer/DBName, user name, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gb_users_client","root", ""); 
			//For testing
			System.out.print("Successfully connected!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	//inserting data
	public String insertUser(String firstName, String lastName, String accountNo, String address, String phone) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting...";
			}
			
			
			// create a prepared statement
			String query = " insert into user( `firstName`, `lastName`, `accountNo`, `address`, `phone`)"
					+ " values( ?, ?, ?, ?, ? )";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, lastName);
			preparedStmt.setString(3, accountNo);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, phone);
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String user = readUser();
			output = "{\"status\":\"success\", \"data\": \"" + user + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
		
	}
	
	
	//reading all users
	public String readUser() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading...";
			}
			
			
			// Prepare the html table to be displayed
			output = "<table border='1' class='table table-bordered'>"
					+ "<tr>"
					+ "<th>First Name</th>"
					+ "<th>Second Name</th>" 
					+ "<th>Account No</th>"
					+ "<th>Address</th>" 
					+ "<th>Phone</th>" 
					+ "<th>Update</th>"
					+ "<th>Delete</th>"
					+ "</tr>";

			String query = "select * from user";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			// iterate through the rows in the result set
			while (rs.next()) {
			String userId = Integer.toString(rs.getInt("userId"));
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String accountNo = rs.getString("accountNo");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
			
				
				// Add into the html table
			output += "<tr>";
		
				output += "<td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + accountNo + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				
				
				 //action buttons
			output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-userid='" + userId + "'></td>"	
				
				 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='" + userId + "'></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the user...";
			System.err.println(e.getMessage());
		}
		return output;	
	}
		
	//updating users
	public String updateUser(int userId, String firstName, String lastName, String accountNo, String address, String phone){
		String output = "";
	    try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating...";
			}
			
			// create a prepared statement
			String query = "UPDATE user SET firstName=?,lastName=?,accountNo=?,address=?,phone=? WHERE userId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, lastName);
			preparedStmt.setString(3, accountNo);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, phone);
			preparedStmt.setInt(6, userId);
			
			
			
			// execute the statement
			 preparedStmt.execute();
			 con.close();
			 String user = readUser();
			 output = "{\"status\":\"success\", \"data\": \"" + user + "\"}";
		} catch (Exception e) {
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}"; 
			 System.err.println(e.getMessage());
		}
		return output;
	}
	
	//delete user
	public String deleteUser(int userId) { 
	    String output = ""; 
	    try { 
		    Connection con = connect(); 
		    if (con == null) {
		    	   return "Error while connecting to the database for deleting..."; 
		      } 
		       
		     // create a prepared statement
		     String query = "delete from user where userId=?"; 
		     PreparedStatement preparedStmt = con.prepareStatement(query); 
		     
		     // binding values
		     preparedStmt.setInt(1, userId); 
		     
		    // execute the statement
		    preparedStmt.execute(); 
		    con.close(); 
		    
		    String user = readUser();
		    output = "{\"status\":\"success\", \"data\": \"" + user + "\"}";
		    } 
	    catch (Exception e) { 
	            output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}";  
	            System.err.println(e.getMessage()); 
	        } 
	        return output; 
	 }
	
	
	//read most relevant user details
	
		public String readOneUser()
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
			 return "Error while connecting to the database for reading..."; 
			 }
		 // Prepare the view table to be displayed
		 output = "<table border='1'>"
					
					+ "<tr>"
					+ "<th>User Id</th>"
					+ "<th>First Name</th>"
					+ "<th>Last Name</th>" 
					+ "<th>Account No</th>"
					+ "<th>Address</th>" 
					+ "<th>Phone</th>"  
					+ "<th>Action</th>"
					+ "</tr>";

		 String query = "select * from user where UserId= (Select max(UserId) from user)";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
			 
				String userId = rs.getString("userId");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String accountNo = rs.getString("accountNo");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				
				
		 // Add into the html table
				output += "<tr><td>" + userId + "</td>";
				output += "<td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + accountNo + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				
		 // buttons
				output += "<td>" + "<input name='btnUpdate' type='button' value='Update' class='btn btn-success'>"
						+ " <input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>" + "</td></tr>"
					
					 +"<input name='userId' type='hidden' value='" + userId + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the items.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 } 
		



		
		
}
