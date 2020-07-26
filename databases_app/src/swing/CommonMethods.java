package swing;

import java.util.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

class CommonMethods {
	static void createAllTables() {
		DBConnection dbc = new DBConnection();
		dbc.executeOther(
				"CREATE TABLE IF NOT EXISTS county (name VARCHAR(255), state VARCHAR(255), total_deaths BIGINT, population BIGINT, PRIMARY KEY (name, state))");

		dbc.executeOther(
				"CREATE TABLE IF NOT EXISTS cases (case_id BIGINT, occurred_at DATE, daily_deaths BIGINT, confirmed BIGINT, name VARCHAR(255), state VARCHAR(255), PRIMARY KEY (case_id))");

		dbc.executeOther(
				"CREATE TABLE IF NOT EXISTS socioeconomic (socio_id BIGINT, income VARCHAR(255), life_expectancy BIGINT, primary_physicians BIGINT, mental_health_providers BIGINT, uninsured BIGINT, PRIMARY KEY (socio_id));");

		dbc.executeOther(
				"CREATE TABLE IF NOT EXISTS race (race_id BIGINT, name VARCHAR(255), state VARCHAR(255), black VARCHAR(255), alaska_native VARCHAR(255), asian VARCHAR(255), native_hawaiian VARCHAR(255), hispanic VARCHAR(255), nonHispanic_white VARCHAR(255), PRIMARY KEY (race_id))");

		dbc.executeOther(
				"CREATE TABLE IF NOT EXISTS hold (socio_id BIGINT, name VARCHAR(255), state VARCHAR(255), PRIMARY KEY (socio_id, name, state))");

	}

	static String getStateName(String county) {
		String state = "";
		try {
			DBConnection dbc = new DBConnection();
			ResultSet rst = dbc.executeQuery("SELECT state FROM county WHERE name='" + county + "'");
			rst.next();
			state = rst.getString(1);
			dbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return state;
	}

	static ArrayList getCounties(String state) {

		// add the table to the frame
		ArrayList counties = new ArrayList();
		try {
			DBConnection dbc = new DBConnection();
			ResultSet rst = dbc.executeQuery("SELECT name FROM county WHERE state='" + state + "'");
			ResultSetMetaData metaData = rst.getMetaData();
			String columnNames = metaData.getColumnName(1);

			int columns = metaData.getColumnCount();
			while (rst.next()) {
				for (int i = 1; i <= columns; i++) {
					counties.add(rst.getObject(i));
				}
			}
			dbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return counties;
	}

	/**
	 * 
	 * @param ResutlSet 
	 * @return TableModel
	 */
	public static TableModel resultSetToTableModel(ResultSet rst) {
		try {
			ResultSetMetaData metaData = rst.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			Vector columnNames = new Vector();
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames.addElement(metaData.getColumnLabel(column + 1));
			}
			Vector rows = new Vector();
			while (rst.next()) {
				Vector newRow = new Vector();
				for (int i = 1; i <= numberOfColumns; i++) {
					newRow.addElement(rst.getObject(i));
				}
				rows.addElement(newRow);
			}
			return new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}