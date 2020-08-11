///////////////////////////////// FILE HEADER /////////////////////////////////
//
// Title:           COVID19 Database
// This File:       DBConnection.java
// Files:           Editor.java, EqualityOperator.java, DBConnection.java, CommonMethos.java
//
// Private GitHub Repo:    https://github.com/Askarafshar/cs564-covid19DB
//
// Authors                    Email:
// Askar Safipour Afshar      safipourafsh@wisc.edu
// Elaheh Jabbarifard         jabbarifard@wisc.edu
// Kevin Walker               kwalker26@wisc.edu
//
// Lecturer's Name:    HIEN NGUYEN
// Course:             CS564, Summer 2020
//
/////////////////////////////////////////////////////////////////////////////
package swing;

import java.util.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class DBConnection {
	Connection conn;
	Statement stmt;
	ResultSet rst;

	private void open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String databasePrefix = "covid19";// Name of the database.
			String netID = "YOUR ID"; // User ID by which you connect to the db. Mine is 'askar' Like in ->mysql -u
										  // askar -p
			String hostName = "localhost";
			String databaseURL = "jdbc:mysql://" + hostName + "/" + databasePrefix + "?autoReconnect=true&useSSL=false";
			String password = "YOUR PASS"; // please enter your own password

			conn = DriverManager.getConnection(databaseURL, netID, password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	void close() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	ResultSet executeQuery(String query)// for select
	{
		try {
			open();
			rst = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rst;
	}

	/*
	 * Execute an update or delete statement. Returns: the number of entities
	 * updated or deleted
	 */
	int executeOther(String query)// for other query
	{
		int cnt = 0;
		try {
			open();
			cnt = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return cnt;
	}
	

}
