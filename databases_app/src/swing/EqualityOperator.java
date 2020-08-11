///////////////////////////////// FILE HEADER /////////////////////////////////
//
// Title:           COVID19 Database
// This File:       EqualityOperator.java
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

public enum EqualityOperator {
	First(""), Second("="), Third(">"), Fourth("<"), Fifth(">="), Sixth("<=");
	private final String display;
	private EqualityOperator(String s) {
		display = s;
	}
	@Override
	public String toString() {
		return display;
	}
}
