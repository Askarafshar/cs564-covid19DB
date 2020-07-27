package swing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.JCheckBox;

public class Editor extends JFrame {

	private JPanel contentPanel;
	private JTextField txtNewCases;
	private JTextField txtNewDeaths;
	private JTextField txtNewCases_mod;
	private JTextField txtNewDeaths_mod;
	private JLabel lblModifedDeaths;
	private final Action action = new SwingAction();
	private JTextField nameFilter;
	private JTextField deathFilter;
	private JTextField popFilter;
	private JTextField case_numCase;
	private JTextField case_numDeaths;
	private JTextField race_blkPercent;
	private JTextField race_amerInPercent;
	private JTextField race_asiPercent;
	private JTextField race_hawPercent;
	private JTextField race_hispPercent;
	private JTextField race_whiPercent;
	private JTextField soc_incomeText;
	private JTextField soc_lifeText;
	private JTextField soc_uninText;
	private JTextField soc_mentalText;
	private JTextField soc_primaryText;
	private JTable userTable;
	private JTable editorTable;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor frame = new Editor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public Editor() throws SQLException {
		// instantiate all compoenents
		setTitle("Covid-19 Database");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));

		JPanel UserPanel = new JPanel();
		contentPanel.add(UserPanel, "USER");
		UserPanel.setLayout(null);

		JButton btnEditorLogin = new JButton("Editor Login");
		btnEditorLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEditorLogin.setBounds(10, 123, 99, 23);
		UserPanel.add(btnEditorLogin);

		JButton btnCounty = new JButton("County");
		btnCounty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCounty.setBounds(103, 0, 115, 26);
		UserPanel.add(btnCounty);

		JButton btnCases = new JButton("Cases");
		btnCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCases.setBounds(350, 0, 115, 26);
		UserPanel.add(btnCases);

		JButton btnRace = new JButton("Race");
		btnRace.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRace.setBounds(594, 0, 115, 26);
		UserPanel.add(btnRace);

		JButton btnSocioeconomic = new JButton("Socioeconomic");
		btnSocioeconomic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSocioeconomic.setBounds(847, 0, 115, 26);
		UserPanel.add(btnSocioeconomic);

		JLabel lblFilterBy = new JLabel("Filter by:");
		lblFilterBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFilterBy.setBounds(0, 5, 64, 16);
		UserPanel.add(lblFilterBy);

		JPanel filterPanel = new JPanel();
		filterPanel.setBounds(0, 30, 974, 88);
		UserPanel.add(filterPanel);
		filterPanel.setLayout(new CardLayout(0, 0));

		JPanel countyFilter = new JPanel();
		filterPanel.add(countyFilter, "COUNTY");
		countyFilter.setLayout(null);

		JLabel lblCountyName = new JLabel("County Name");
		lblCountyName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCountyName.setBounds(12, 12, 75, 16);
		countyFilter.add(lblCountyName);

		JLabel lblState = new JLabel("State");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblState.setHorizontalAlignment(SwingConstants.RIGHT);
		lblState.setBounds(12, 40, 75, 16);
		countyFilter.add(lblState);

		JLabel lblNewLabel = new JLabel("Deaths");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(308, 12, 55, 16);
		countyFilter.add(lblNewLabel);

		JLabel lblPopulation = new JLabel("Population");
		lblPopulation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPopulation.setBounds(308, 40, 75, 16);
		countyFilter.add(lblPopulation);

		nameFilter = new JTextField();
		nameFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameFilter.setBounds(94, 10, 196, 20);
		countyFilter.add(nameFilter);
		nameFilter.setColumns(10);

		JComboBox stateDropdown = new JComboBox();
		stateDropdown.setModel(new DefaultComboBoxModel(new String[] { "", "Alabama", "Alaska", "Arizona", "Arkansas",
				"California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
				"Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
				"Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
				"New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
				"Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
				"Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" }));
		stateDropdown.setFont(new Font("Tahoma", Font.PLAIN, 12));
		stateDropdown.setBounds(94, 36, 196, 25);
		countyFilter.add(stateDropdown);

		JComboBox deathRelation = new JComboBox();
		deathRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		deathRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		deathRelation.setBounds(368, 8, 63, 25);
		countyFilter.add(deathRelation);

		JComboBox popRelation = new JComboBox();
		popRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		popRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		popRelation.setBounds(368, 36, 63, 25);
		countyFilter.add(popRelation);

		deathFilter = new JTextField();
		deathFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		deathFilter.setBounds(443, 10, 114, 20);
		countyFilter.add(deathFilter);
		deathFilter.setColumns(10);

		popFilter = new JTextField();
		popFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		popFilter.setColumns(10);
		popFilter.setBounds(443, 38, 114, 20);
		countyFilter.add(popFilter);
		
		JLabel lblSelectFieldsTo = new JLabel("Select fields to be returned:");
		lblSelectFieldsTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectFieldsTo.setBounds(575, 12, 155, 16);
		countyFilter.add(lblSelectFieldsTo);
		
		JCheckBox retName_cnty = new JCheckBox("County Name");
		retName_cnty.setSelected(true);
		retName_cnty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retName_cnty.setBounds(744, 9, 112, 24);
		countyFilter.add(retName_cnty);
		
		JCheckBox returnState_cnty = new JCheckBox("State");
		returnState_cnty.setSelected(true);
		returnState_cnty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnState_cnty.setBounds(744, 32, 112, 24);
		countyFilter.add(returnState_cnty);
		
		JCheckBox returnDeaths_cnty = new JCheckBox("Total Deaths");
		returnDeaths_cnty.setSelected(true);
		returnDeaths_cnty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnDeaths_cnty.setBounds(744, 56, 112, 24);
		countyFilter.add(returnDeaths_cnty);
		
		JCheckBox returnPop_cnty = new JCheckBox("Population");
		returnPop_cnty.setSelected(true);
		returnPop_cnty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnPop_cnty.setBounds(860, 8, 112, 24);
		countyFilter.add(returnPop_cnty);

		JPanel casesFilter = new JPanel();
		filterPanel.add(casesFilter, "CASES");
		casesFilter.setLayout(null);

		JLabel lblStartingDate = new JLabel("Start Date");
		lblStartingDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStartingDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStartingDate.setBounds(12, 12, 63, 16);
		casesFilter.add(lblStartingDate);

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEndDate.setBounds(12, 60, 63, 16);
		casesFilter.add(lblEndDate);

		JLabel lblDatesAreMmddyyyy = new JLabel("Dates are MM/DD/YYYY");
		lblDatesAreMmddyyyy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDatesAreMmddyyyy.setBounds(12, 37, 132, 16);
		casesFilter.add(lblDatesAreMmddyyyy);

		JLabel lblCases = new JLabel("Cases");
		lblCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCases.setBounds(279, 12, 55, 16);
		casesFilter.add(lblCases);

		JLabel lblDeaths = new JLabel("Deaths");
		lblDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDeaths.setBounds(279, 37, 55, 16);
		casesFilter.add(lblDeaths);

		JComboBox caseST_comboMM = new JComboBox();
		caseST_comboMM.setModel(new DefaultComboBoxModel(
				new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		caseST_comboMM.setToolTipText("");
		caseST_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseST_comboMM.setBounds(85, 8, 50, 25);
		casesFilter.add(caseST_comboMM);

		JComboBox caseST_comboDD = new JComboBox();
		caseST_comboDD.setModel(new DefaultComboBoxModel(new String[] { "", "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
				"25", "26", "27", "28", "29", "30", "31" }));
		caseST_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseST_comboDD.setBounds(141, 8, 50, 25);
		casesFilter.add(caseST_comboDD);

		JComboBox caseST_comboYYYY = new JComboBox();
		caseST_comboYYYY.setModel(new DefaultComboBoxModel(new String[] { "", "2020" }));
		caseST_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseST_comboYYYY.setBounds(196, 8, 75, 25);
		casesFilter.add(caseST_comboYYYY);

		JComboBox caseEND_comboMM = new JComboBox();
		caseEND_comboMM.setModel(new DefaultComboBoxModel(
				new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		caseEND_comboMM.setToolTipText("");
		caseEND_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseEND_comboMM.setBounds(85, 56, 50, 25);
		casesFilter.add(caseEND_comboMM);

		JComboBox caseEND_comboDD = new JComboBox();
		caseEND_comboDD.setModel(new DefaultComboBoxModel(new String[] { "", "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
				"25", "26", "27", "28", "29", "30", "31" }));
		caseEND_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseEND_comboDD.setBounds(141, 56, 50, 25);
		casesFilter.add(caseEND_comboDD);

		JComboBox caseEND_comboYYYY = new JComboBox();
		caseEND_comboYYYY.setModel(new DefaultComboBoxModel(new String[] { "", "2020" }));
		caseEND_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseEND_comboYYYY.setBounds(196, 56, 75, 25);
		casesFilter.add(caseEND_comboYYYY);

		JComboBox case_caseRelation = new JComboBox();
		case_caseRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		case_caseRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_caseRelation.setBounds(320, 8, 63, 25);

		casesFilter.add(case_caseRelation);

		case_numCase = new JTextField();
		case_numCase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_numCase.setColumns(10);
		case_numCase.setBounds(390, 11, 114, 20);

		casesFilter.add(case_numCase);

		JComboBox case_deathRelation = new JComboBox();
		case_deathRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		case_deathRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_deathRelation.setBounds(320, 34, 63, 25);

		casesFilter.add(case_deathRelation);

		case_numDeaths = new JTextField();
		case_numDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_numDeaths.setColumns(10);
		case_numDeaths.setBounds(390, 36, 114, 20);

		casesFilter.add(case_numDeaths);
		
		JLabel lblSelectFieldsTo_1 = new JLabel("Select fields to be returned:");
		lblSelectFieldsTo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectFieldsTo_1.setBounds(578, 4, 155, 16);
		casesFilter.add(lblSelectFieldsTo_1);
		
		JCheckBox returnDeaths_cases = new JCheckBox("Deaths");
		returnDeaths_cases.setSelected(true);
		returnDeaths_cases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnDeaths_cases.setBounds(747, 48, 112, 24);
		casesFilter.add(returnDeaths_cases);
		
		JCheckBox retDate_cases = new JCheckBox("Date");
		retDate_cases.setSelected(true);
		retDate_cases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retDate_cases.setBounds(747, 24, 112, 24);
		casesFilter.add(retDate_cases);
		
		JCheckBox retID_cases = new JCheckBox("Case ID");
		retID_cases.setSelected(true);
		retID_cases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retID_cases.setBounds(747, 1, 112, 24);
		casesFilter.add(retID_cases);
		
		JCheckBox returnCases_cases = new JCheckBox("New Cases");
		returnCases_cases.setSelected(true);
		returnCases_cases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnCases_cases.setBounds(863, 0, 112, 24);
		casesFilter.add(returnCases_cases);
		
		JCheckBox retName_cases = new JCheckBox("County Name");
		retName_cases.setSelected(true);
		retName_cases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retName_cases.setBounds(863, 24, 112, 24);
		casesFilter.add(retName_cases);
		
		JCheckBox retState_cases = new JCheckBox("State");
		retState_cases.setSelected(true);
		retState_cases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retState_cases.setBounds(863, 48, 112, 24);
		casesFilter.add(retState_cases);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(361, 60, 143, 20);
		casesFilter.add(textField);
		
		JComboBox stateDropdown_1 = new JComboBox();
		stateDropdown_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		stateDropdown_1.setBounds(544, 33, 196, 25);
		casesFilter.add(stateDropdown_1);
		
		JLabel lblState_1 = new JLabel("State");
		lblState_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblState_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblState_1.setBounds(498, 37, 43, 16);
		casesFilter.add(lblState_1);
		
		JLabel lblCountyName_1 = new JLabel("County Name");
		lblCountyName_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCountyName_1.setBounds(279, 62, 75, 16);
		casesFilter.add(lblCountyName_1);

		JPanel raceFilter = new JPanel();
		filterPanel.add(raceFilter, "RACE");
		raceFilter.setLayout(null);

		JComboBox race_blkRelation = new JComboBox();
		race_blkRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_blkRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_blkRelation.setBounds(139, 1, 63, 25);
		raceFilter.add(race_blkRelation);

		race_blkPercent = new JTextField();
		race_blkPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_blkPercent.setColumns(10);
		race_blkPercent.setBounds(208, 4, 63, 20);
		raceFilter.add(race_blkPercent);

		JComboBox race_amerInRelation = new JComboBox();
		race_amerInRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_amerInRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_amerInRelation.setBounds(139, 34, 63, 25);
		raceFilter.add(race_amerInRelation);

		race_amerInPercent = new JTextField();
		race_amerInPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_amerInPercent.setColumns(10);
		race_amerInPercent.setBounds(208, 38, 63, 20);
		raceFilter.add(race_amerInPercent);

		JComboBox race_asiRelation = new JComboBox();
		race_asiRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_asiRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_asiRelation.setBounds(139, 64, 63, 25);
		raceFilter.add(race_asiRelation);

		race_asiPercent = new JTextField();
		race_asiPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_asiPercent.setColumns(10);
		race_asiPercent.setBounds(208, 67, 63, 20);
		raceFilter.add(race_asiPercent);

		JComboBox race_hawRelation = new JComboBox();
		race_hawRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_hawRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hawRelation.setBounds(414, 1, 63, 25);
		raceFilter.add(race_hawRelation);

		race_hawPercent = new JTextField();
		race_hawPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hawPercent.setColumns(10);
		race_hawPercent.setBounds(489, 4, 63, 20);
		raceFilter.add(race_hawPercent);

		JComboBox race_hispRelation = new JComboBox();
		race_hispRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_hispRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hispRelation.setBounds(414, 34, 63, 25);
		raceFilter.add(race_hispRelation);

		race_hispPercent = new JTextField();
		race_hispPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hispPercent.setColumns(10);
		race_hispPercent.setBounds(489, 37, 63, 20);
		raceFilter.add(race_hispPercent);

		JComboBox race_whiRelation = new JComboBox();
		race_whiRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_whiRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_whiRelation.setBounds(414, 64, 63, 25);
		raceFilter.add(race_whiRelation);

		race_whiPercent = new JTextField();
		race_whiPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_whiPercent.setColumns(10);
		race_whiPercent.setBounds(489, 67, 63, 20);
		raceFilter.add(race_whiPercent);

		JLabel lblBlack = new JLabel("Black");
		lblBlack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBlack.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBlack.setBounds(0, 10, 135, 16);
		raceFilter.add(lblBlack);

		JLabel lblAlaskan = new JLabel("American Indian/Alaskan");
		lblAlaskan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAlaskan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlaskan.setBounds(0, 43, 135, 16);
		raceFilter.add(lblAlaskan);

		JLabel lblAsian = new JLabel("Asian");
		lblAsian.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAsian.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAsian.setBounds(0, 73, 135, 16);
		raceFilter.add(lblAsian);

		JLabel lblNativeHawaiian = new JLabel("Native Hawaiian");
		lblNativeHawaiian.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNativeHawaiian.setBounds(298, 5, 90, 16);
		raceFilter.add(lblNativeHawaiian);

		JLabel lblHispanic = new JLabel("Hispanic");
		lblHispanic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHispanic.setBounds(298, 38, 55, 16);
		raceFilter.add(lblHispanic);

		JLabel lblNonhispanicWhite = new JLabel("Non-Hispanic White");
		lblNonhispanicWhite.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNonhispanicWhite.setBounds(298, 68, 111, 16);
		raceFilter.add(lblNonhispanicWhite);

		JLabel label = new JLabel("%");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(273, 5, 13, 16);
		raceFilter.add(label);

		JLabel label_1 = new JLabel("%");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(273, 68, 13, 16);
		raceFilter.add(label_1);

		JLabel label_2 = new JLabel("%");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(273, 38, 13, 16);
		raceFilter.add(label_2);

		JLabel label_3 = new JLabel("%");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(552, 5, 18, 16);
		raceFilter.add(label_3);

		JLabel label_4 = new JLabel("%");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(552, 38, 18, 16);
		raceFilter.add(label_4);

		JLabel label_5 = new JLabel("%");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(552, 68, 18, 16);
		raceFilter.add(label_5);
		
		JLabel lblSelectFieldsTo_1_1 = new JLabel("Select fields to be returned:");
		lblSelectFieldsTo_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectFieldsTo_1_1.setBounds(569, 4, 155, 16);
		raceFilter.add(lblSelectFieldsTo_1_1);
		
		JCheckBox retBlack_race = new JCheckBox("Black");
		retBlack_race.setSelected(true);
		retBlack_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retBlack_race.setBounds(738, 1, 112, 24);
		raceFilter.add(retBlack_race);
		
		JCheckBox retNative_race = new JCheckBox("Native/Alaskan");
		retNative_race.setSelected(true);
		retNative_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retNative_race.setBounds(738, 24, 112, 24);
		raceFilter.add(retNative_race);
		
		JCheckBox returnAsian_race = new JCheckBox("Asian");
		returnAsian_race.setSelected(true);
		returnAsian_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnAsian_race.setBounds(738, 48, 112, 24);
		raceFilter.add(returnAsian_race);
		
		JCheckBox retnHWhite_race = new JCheckBox("White");
		retnHWhite_race.setSelected(true);
		retnHWhite_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retnHWhite_race.setBounds(854, 48, 112, 24);
		raceFilter.add(retnHWhite_race);
		
		JCheckBox retHispanic_race = new JCheckBox("Hispanic");
		retHispanic_race.setSelected(true);
		retHispanic_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retHispanic_race.setBounds(854, 24, 112, 24);
		raceFilter.add(retHispanic_race);
		
		JCheckBox returnHaw_race = new JCheckBox("Hawaiian");
		returnHaw_race.setSelected(true);
		returnHaw_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnHaw_race.setBounds(854, 0, 112, 24);
		raceFilter.add(returnHaw_race);
		
		JCheckBox retCounty_race = new JCheckBox("County Name");
		retCounty_race.setSelected(true);
		retCounty_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retCounty_race.setBounds(622, 24, 112, 24);
		raceFilter.add(retCounty_race);
		
		JCheckBox retState_race = new JCheckBox("State");
		retState_race.setSelected(true);
		retState_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retState_race.setBounds(622, 45, 112, 24);
		raceFilter.add(retState_race);
		
		JCheckBox retID_race = new JCheckBox("Race ID");
		retID_race.setSelected(true);
		retID_race.setFont(new Font("Tahoma", Font.PLAIN, 12));
		retID_race.setBounds(622, 65, 112, 24);
		raceFilter.add(retID_race);

		JPanel socioFilter = new JPanel();
		filterPanel.add(socioFilter, "SOCIO");
		socioFilter.setLayout(null);

		soc_incomeText = new JTextField();
		soc_incomeText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_incomeText.setColumns(10);
		soc_incomeText.setBounds(195, 3, 100, 20);
		socioFilter.add(soc_incomeText);

		JComboBox soc_incomeRelation = new JComboBox();
		soc_incomeRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		soc_incomeRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_incomeRelation.setBounds(120, 0, 63, 25);
		socioFilter.add(soc_incomeRelation);

		soc_lifeText = new JTextField();
		soc_lifeText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_lifeText.setColumns(10);
		soc_lifeText.setBounds(195, 33, 100, 20);
		socioFilter.add(soc_lifeText);

		JComboBox soc_lifeRelation = new JComboBox();
		soc_lifeRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		soc_lifeRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_lifeRelation.setBounds(120, 30, 63, 25);
		socioFilter.add(soc_lifeRelation);

		soc_uninText = new JTextField();
		soc_uninText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_uninText.setColumns(10);
		soc_uninText.setBounds(195, 65, 100, 20);
		socioFilter.add(soc_uninText);

		JComboBox soc_uninRelation = new JComboBox();
		soc_uninRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		soc_uninRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_uninRelation.setBounds(120, 62, 63, 25);
		socioFilter.add(soc_uninRelation);

		soc_mentalText = new JTextField();
		soc_mentalText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_mentalText.setColumns(10);
		soc_mentalText.setBounds(519, 9, 100, 20);
		socioFilter.add(soc_mentalText);

		JComboBox soc_mentalRelation = new JComboBox();
		soc_mentalRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		soc_mentalRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_mentalRelation.setBounds(444, 6, 63, 25);
		socioFilter.add(soc_mentalRelation);

		soc_primaryText = new JTextField();
		soc_primaryText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_primaryText.setColumns(10);
		soc_primaryText.setBounds(519, 44, 100, 20);
		socioFilter.add(soc_primaryText);

		JComboBox soc_primaryRelation = new JComboBox();
		soc_primaryRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		soc_primaryRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		soc_primaryRelation.setBounds(444, 41, 63, 25);
		socioFilter.add(soc_primaryRelation);

		JLabel lblHouseholdIncome = new JLabel("Household Income");
		lblHouseholdIncome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHouseholdIncome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHouseholdIncome.setBounds(12, 4, 102, 15);
		socioFilter.add(lblHouseholdIncome);

		JLabel lblLifeExpectancy = new JLabel("Life Expectancy");
		lblLifeExpectancy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLifeExpectancy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLifeExpectancy.setBounds(12, 34, 102, 15);
		socioFilter.add(lblLifeExpectancy);

		JLabel lblPrimaryPhysicians = new JLabel("Primary Care Physicians");
		lblPrimaryPhysicians.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrimaryPhysicians.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrimaryPhysicians.setBounds(313, 46, 128, 15);
		socioFilter.add(lblPrimaryPhysicians);

		JLabel lblMentalHealthProviders = new JLabel("Mental Health Providers");
		lblMentalHealthProviders.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMentalHealthProviders.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMentalHealthProviders.setBounds(313, 15, 128, 15);
		socioFilter.add(lblMentalHealthProviders);

		JLabel lblUninsured = new JLabel("Uninsured");
		lblUninsured.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUninsured.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUninsured.setBounds(12, 65, 102, 16);
		socioFilter.add(lblUninsured);

		JLabel label_6 = new JLabel("$");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_6.setBounds(186, 4, 55, 16);
		socioFilter.add(label_6);
		
		JLabel lblSelectFieldsTo_1_1_1 = new JLabel("<html>Select fields to be returned:</html>");
		lblSelectFieldsTo_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectFieldsTo_1_1_1.setBounds(633, 3, 99, 61);
		socioFilter.add(lblSelectFieldsTo_1_1_1);
		
		JCheckBox returnID_socio = new JCheckBox("Socio ID");
		returnID_socio.setSelected(true);
		returnID_socio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnID_socio.setBounds(746, 4, 112, 24);
		socioFilter.add(returnID_socio);
		
		JCheckBox returnIncome_socio = new JCheckBox("Income");
		returnIncome_socio.setSelected(true);
		returnIncome_socio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnIncome_socio.setBounds(746, 27, 112, 24);
		socioFilter.add(returnIncome_socio);
		
		JCheckBox returnLife_socio = new JCheckBox("Life Expenctancy");
		returnLife_socio.setSelected(true);
		returnLife_socio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		returnLife_socio.setBounds(746, 51, 112, 24);
		socioFilter.add(returnLife_socio);
		
		JCheckBox returnUninsured_socio = new JCheckBox("Uninsured");
		returnUninsured_socio.setSelected(true);
		returnUninsured_socio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnUninsured_socio.setBounds(862, 51, 112, 24);
		socioFilter.add(returnUninsured_socio);
		
		JCheckBox returnMental_socio = new JCheckBox("Mental Providers");
		returnMental_socio.setSelected(true);
		returnMental_socio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		returnMental_socio.setBounds(862, 27, 112, 24);
		socioFilter.add(returnMental_socio);
		
		JCheckBox returnPhysicians_socio = new JCheckBox("Physicians");
		returnPhysicians_socio.setSelected(true);
		returnPhysicians_socio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnPhysicians_socio.setBounds(862, 3, 112, 24);
		socioFilter.add(returnPhysicians_socio);

		JButton btnApplyFilter = new JButton("Apply Filter");
		btnApplyFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApplyFilter.setBounds(864, 121, 98, 26);
		UserPanel.add(btnApplyFilter);

		JButton btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnClearFilter.setBounds(754, 121, 98, 26);
		UserPanel.add(btnClearFilter);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 158, 974, 493);
		UserPanel.add(scrollPane);

		userTable = new JTable();
		scrollPane.setViewportView(userTable);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//TableColumnManager tcm = new TableColumnManager(userTable);
		//tcm.hideColumn("E");
		
		JCheckBox enableCounty = new JCheckBox("");
		enableCounty.setBounds(78, 0, 21, 24);
		UserPanel.add(enableCounty);
		
		JCheckBox enableCases = new JCheckBox("");
		enableCases.setBounds(328, 0, 21, 24);
		UserPanel.add(enableCases);
		
		JCheckBox enableRace = new JCheckBox("");
		enableRace.setBounds(567, 0, 21, 24);
		UserPanel.add(enableRace);
		
		JCheckBox enableSocio = new JCheckBox("");
		enableSocio.setBounds(818, 2, 21, 24);
		UserPanel.add(enableSocio);

		JPanel EditorPanel = new JPanel();
		contentPanel.add(EditorPanel, "EDITOR");
		EditorPanel.setLayout(null);

		JComboBox countyList = new JComboBox();
		countyList.setMaximumRowCount(1000);
		countyList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		countyList.setBounds(113, 0, 250, 20);
		EditorPanel.add(countyList);

		JButton btnDeleteEntry = new JButton("Delete Entry");
		btnDeleteEntry.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDeleteEntry.setBounds(0, 628, 105, 23);
		EditorPanel.add(btnDeleteEntry);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogout.setBounds(869, 628, 105, 23);
		EditorPanel.add(btnLogout);

		JLabel lblSelectedCounty = new JLabel("Selected County:");
		lblSelectedCounty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectedCounty.setBounds(0, 3, 95, 14);
		EditorPanel.add(lblSelectedCounty);

		JButton btnModifyEntry = new JButton("Modify Entry");
		btnModifyEntry.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnModifyEntry.setBounds(461, 628, 105, 23);
		EditorPanel.add(btnModifyEntry);

		JPanel updatePanel = new JPanel();
		updatePanel.setBounds(0, 23, 974, 133);
		EditorPanel.add(updatePanel);
		updatePanel.setLayout(new CardLayout(0, 0));

		JPanel newEntryPanel = new JPanel();
		updatePanel.add(newEntryPanel, "NEW");
		newEntryPanel.setLayout(null);

		JLabel lblDate = new JLabel("Date (MM/DD/YYYY):");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(24, 23, 120, 14);
		newEntryPanel.add(lblDate);

		JLabel lblNumberOfCases = new JLabel("Number of Cases:");
		lblNumberOfCases.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfCases.setBounds(24, 56, 120, 14);
		newEntryPanel.add(lblNumberOfCases);

		JLabel lblNumberOfDeaths = new JLabel("Number of Deaths:");
		lblNumberOfDeaths.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfDeaths.setBounds(24, 92, 120, 14);
		newEntryPanel.add(lblNumberOfDeaths);

		txtNewCases = new JTextField();
		txtNewCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNewCases.setText("New Cases");
		txtNewCases.setBounds(154, 54, 86, 20);
		newEntryPanel.add(txtNewCases);
		txtNewCases.setColumns(10);

		txtNewDeaths = new JTextField();
		txtNewDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNewDeaths.setText("New Deaths");
		txtNewDeaths.setBounds(154, 90, 86, 20);
		newEntryPanel.add(txtNewDeaths);
		txtNewDeaths.setColumns(10);

		JButton btnAddEntry = new JButton("Add Entry");
		btnAddEntry.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddEntry.setBounds(439, 53, 105, 23);
		newEntryPanel.add(btnAddEntry);

		JComboBox new_comboMM = new JComboBox();
		new_comboMM.setModel(new DefaultComboBoxModel(
				new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		new_comboMM.setToolTipText("");
		new_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new_comboMM.setBounds(154, 18, 50, 25);
		newEntryPanel.add(new_comboMM);

		JComboBox new_comboDD = new JComboBox();
		new_comboDD.setModel(new DefaultComboBoxModel(new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
				"26", "27", "28", "29", "30", "31" }));
		new_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new_comboDD.setBounds(210, 18, 50, 25);
		newEntryPanel.add(new_comboDD);

		JComboBox new_comboYYYY = new JComboBox();
		new_comboYYYY.setModel(new DefaultComboBoxModel(new String[] { "", "2020" }));
		new_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new_comboYYYY.setBounds(265, 18, 75, 25);
		newEntryPanel.add(new_comboYYYY);

		JPanel modifyEntryPanel = new JPanel();
		updatePanel.add(modifyEntryPanel, "MODIFY");
		modifyEntryPanel.setLayout(null);

		JLabel lblOldDate = new JLabel("Old Date");
		lblOldDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOldDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOldDate.setBounds(0, 11, 122, 14);
		modifyEntryPanel.add(lblOldDate);

		JLabel lblOldCases = new JLabel("Old Number of Cases");
		lblOldCases.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOldCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOldCases.setBounds(0, 36, 122, 14);
		modifyEntryPanel.add(lblOldCases);

		JLabel lblOldDeaths = new JLabel("Old Number of Deaths");
		lblOldDeaths.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOldDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOldDeaths.setBounds(0, 61, 122, 14);
		modifyEntryPanel.add(lblOldDeaths);

		JLabel lblModifiedDate = new JLabel("Modified Date");
		lblModifiedDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModifiedDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModifiedDate.setBounds(287, 11, 150, 14);
		modifyEntryPanel.add(lblModifiedDate);

		JLabel lblModifiedCases = new JLabel("Modified Number of Cases");
		lblModifiedCases.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModifiedCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModifiedCases.setBounds(287, 36, 150, 14);
		modifyEntryPanel.add(lblModifiedCases);

		lblModifedDeaths = new JLabel("Modifed Number of Deaths");
		lblModifedDeaths.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModifedDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModifedDeaths.setBounds(287, 61, 150, 14);
		modifyEntryPanel.add(lblModifedDeaths);

		JLabel lblPrevdate = new JLabel("prevDate");
		lblPrevdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrevdate.setBounds(132, 11, 100, 14);
		modifyEntryPanel.add(lblPrevdate);

		JLabel lblPrevdeaths = new JLabel("prevDeaths");
		lblPrevdeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrevdeaths.setBounds(132, 61, 100, 14);
		modifyEntryPanel.add(lblPrevdeaths);

		JLabel lblPrevcases = new JLabel("prevCases");
		lblPrevcases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrevcases.setBounds(132, 36, 100, 14);
		modifyEntryPanel.add(lblPrevcases);

		txtNewCases_mod = new JTextField();
		txtNewCases_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNewCases_mod.setText("New Cases");
		txtNewCases_mod.setBounds(440, 34, 86, 20);
		modifyEntryPanel.add(txtNewCases_mod);
		txtNewCases_mod.setColumns(10);

		txtNewDeaths_mod = new JTextField();
		txtNewDeaths_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNewDeaths_mod.setText("New Deaths");
		txtNewDeaths_mod.setBounds(440, 59, 86, 20);
		modifyEntryPanel.add(txtNewDeaths_mod);
		txtNewDeaths_mod.setColumns(10);

		JButton btnDiscardChanges = new JButton("Discard Changes");
		btnDiscardChanges.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDiscardChanges.setBounds(113, 99, 122, 23);
		modifyEntryPanel.add(btnDiscardChanges);

		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSaveChanges.setBounds(351, 100, 120, 23);
		modifyEntryPanel.add(btnSaveChanges);

		JComboBox mod_comboMM = new JComboBox();
		mod_comboMM.setModel(new DefaultComboBoxModel(
				new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		mod_comboMM.setToolTipText("");
		mod_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mod_comboMM.setBounds(440, 6, 50, 25);
		modifyEntryPanel.add(mod_comboMM);

		JComboBox mod_comboDD = new JComboBox();
		mod_comboDD.setModel(new DefaultComboBoxModel(new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
				"26", "27", "28", "29", "30", "31" }));
		mod_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mod_comboDD.setBounds(495, 6, 50, 25);
		modifyEntryPanel.add(mod_comboDD);

		JComboBox mod_comboYYYY = new JComboBox();
		mod_comboYYYY.setModel(new DefaultComboBoxModel(new String[] { "", "2020" }));
		mod_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mod_comboYYYY.setBounds(550, 6, 75, 25);
		modifyEntryPanel.add(mod_comboYYYY);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 157, 974, 459);
		EditorPanel.add(scrollPane_1);

		editorTable = new JTable();
		scrollPane_1.setViewportView(editorTable);

		// ------------------------------------------------------------
		// ------------------- ACTIONS --------------------------------
		// ------------------------------------------------------------

		// ------------------- EDITOR VIEW ----------------------------
		// go to user screen on logout
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) contentPanel.getLayout();
				c.show(contentPanel, "USER");
			}
		});
		// open modify tab within editor screen
		btnModifyEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// need to grab the selected record from the table and populate
				// the previous fields on the modify screen with its current
				// data

				CardLayout c = (CardLayout) updatePanel.getLayout();
				c.show(updatePanel, "MODIFY");
			}
		});
		// save modified tuple
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// update the previously selected tuple to have the attribute
				// values as set in the new fields for modify
				// then update table
				int row = cases.getSelectedRow();
				String cell = cases.getModel().getValueAt(row,0).toString();
				try {
					String query = "update cases set name ='"+countyList.getSelectedItem().toString()+"', occurred_at = '"+mod_comboMM.getSelectedItem().toString()+ mod_comboDD.getSelectedItem().toString()+ mod_comboYYYY.getSelectedItem().toString()+"', confirmed = '"+txtNewCases_mod.getText()+"', daily_deaths = '"+txtNewDeaths_mod.getText()+"' where case_id ="+cell;
					DBConnection connection = new DBConnection();
					PreparedStatement pst = connection.preparedStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Record Updated!"); 
				}catch (Exception eUpdate) {
					JOptionPane.showMessageDialog(null, eUpdate);
				}	
			}
		});
		// open new tab within editor screen
		btnDiscardChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) updatePanel.getLayout();
				c.show(updatePanel, "NEW");
			}
		});
		// inserts a new record into the db
		btnAddEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// grab inputs to the add fields and use those to enter
				// a new tuple in the database
				try {
					String query = "insert into cases (occurred_at, daily_deaths, confirmed, name) values (?,?,?,?)";
					DBConnection connection = new DBConnection();
					PreparedStatement pst = connection.preparedStatement(query);
					String occDay = new_comboDD.getSelectedItem().toString();
					String occMonth = new_comboMM.getSelectedItem().toString();
					String occYear = new_comboYYYY.getSelectedItem().toString();
					String occValue = occDay+occMonth+occYear;
					String countyValue = countyList.getSelectedItem().toString();
					pst.setString(1, occValue);
					pst.setString(2, txtNewDeaths.getText());
					pst.setString(3, txtNewCases.getText());
					pst.setString(4, countyValue);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Record Inserted!"); 	
					txtNewDeaths.setText("");  
					txtNewCases.setText(""); 
					updateTable();//we need to create it
					pst.close();
					
				}catch (Exception eInsert) {
					JOptionPane.showMessageDialog(null, eInsert);
				}
			}
		});
		// removes a record
		btnDeleteEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// get selected entry in the table and delete it from
				// the database, then refresh the table
				int row = cases.getSelectedRow();
				String cell = cases.getModel().getValueAt(row,0).toString();
				String query = "delete from case where case_id =" + cell;
				try {
					DBConnection connection = new DBConnection();
					PreparedStatement pst = connection.preparedStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Record Deleted!"); 
					updateTable();//we need to create it
					pst.close();
					
				}catch (Exception eDelete) {
					JOptionPane.showMessageDialog(null, eDelete); 
				}
			}
		});

		// ------------------- USER VIEW ----------------------------
		// go to editor screen on login button
		btnEditorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) contentPanel.getLayout();
				c.show(contentPanel, "EDITOR");
			}
		});
		// open county filter
		btnCounty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) filterPanel.getLayout();
				c.show(filterPanel, "COUNTY");
			}
		});
		// open case filter
		btnCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) filterPanel.getLayout();
				c.show(filterPanel, "CASES");
			}
		});
		// open race filter
		btnRace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) filterPanel.getLayout();
				c.show(filterPanel, "RACE");
			}
		});
		// open socioeconomic filter
		btnSocioeconomic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) filterPanel.getLayout();
				c.show(filterPanel, "SOCIO");
			}
		});
		// apply current filters
		btnApplyFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DBConnection dbc = new DBConnection();
				// TODO
				// this button press should cause the program to fetch the current
				// entries into the current filter screen. it should then use these
				// arguments to construct a query to send to the database. and lastly
				// update the table to reflect the given query
				
				//which tables are enabled
				
				
				//get the filterPane component that is visible
				Component[] c = filterPanel.getComponents();
				boolean[] visComp = new boolean[4];
				for (int i = 0; i < c.length; i++) {
					visComp[i] = c[i].isVisible();
				}
				String[] filters = { "County", "Cases", "Race", "Socioeconomic" };
				String toFilter = new String();
				for (int i = 0; i < visComp.length; i++) {
					if (visComp[i] == true)
						toFilter = filters[i];
				}//visible component is tableToFilter
				
				if (enableCounty.isSelected() & enableCases.isSelected()) {
					String name = nameFilter.getText();
					String state = String.valueOf(stateDropdown.getSelectedItem());
					String pop = popFilter.getText();
					String popR = String.valueOf(popRelation.getSelectedItem());
					String death = deathFilter.getText();
					String deathR = String.valueOf(deathRelation.getSelectedItem());
					String numCase = case_numCase.getText();
					String numCaseR = String.valueOf(case_caseRelation.getSelectedItem());
					String numDeaths = case_numDeaths.getText();
					String numDeathsR = String.valueOf(case_deathRelation.getSelectedItem());
					String stMM = String.valueOf(caseST_comboMM.getSelectedItem());
					String stDD = String.valueOf(caseST_comboDD.getSelectedItem());
					String stYY = String.valueOf(caseST_comboYYYY.getSelectedItem());
					String enMM = String.valueOf(caseEND_comboMM.getSelectedItem());
					String enDD = String.valueOf(caseEND_comboDD.getSelectedItem());
					String enYY = String.valueOf(caseEND_comboYYYY.getSelectedItem());
					
				
					String query = null;
					if(name.isEmpty() & state.isEmpty() & !pop.isEmpty()
							& death.isEmpty() & !numDeaths.isEmpty() & !retName_cases.isSelected() 
							& !retState_cases.isSelected() & !retID_cases.isSelected()) {
						query = ("SELECT c.name,c.state,c.total_deaths, c.population, ca.occurred_at,ca.daily_deaths,ca.confirmed"
								+ " FROM county c, cases ca WHERE c.name=ca.name AND"
								+ " c.population"+popR+""+pop+" AND ca.daily_deaths"+numDeathsR+""+numDeaths+"");
					}else if(name.isEmpty() & state.isEmpty() & !pop.isEmpty() & !numDeaths.isEmpty() & death.isEmpty()) {
						query = ("SELECT * FROM county c, cases ca WHERE c.name=ca.name AND"
								+ " c.population"+popR+""+pop+" AND ca.daily_deaths"+numDeathsR+""+numDeaths+"");
					}else {
						query = "SELECT * FROM county c, cases ca WHERE c.name=ca.name";
					}
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}

				if (enableCounty.isSelected() & !enableCases.isSelected() & 
					!enableRace.isSelected() & !enableSocio.isSelected()) {
										
					String name = nameFilter.getText();
					String state = String.valueOf(stateDropdown.getSelectedItem());
					String pop = popFilter.getText();
					String popR = String.valueOf(popRelation.getSelectedItem());
					String death = deathFilter.getText();
					String deathR = String.valueOf(deathRelation.getSelectedItem());
					
					String retName = retName_cnty.getText().toLowerCase();
					String retState = returnState_cnty.getText().toLowerCase();
					String retPop = returnPop_cnty.getText().toLowerCase();
					String retDeaths = returnDeaths_cnty.getText().toLowerCase();
					
					String query = null;
					if(name.isEmpty() & !state.isEmpty() & !pop.isEmpty() 
							& !death.isEmpty() & !returnPop_cnty.isSelected() ) {
						System.out.print("before Query!");
						query = ("SELECT name, state,total_deaths  FROM county WHERE total_deaths"+deathR+""+death+" AND state='"+state+"'AND population"+popR+""+pop+"");
						System.out.print("After Query!");
					}else if(!name.isEmpty() & !state.isEmpty() & !pop.isEmpty() & !death.isEmpty()) {
						query = ("SELECT * FROM county WHERE name='"+name+"' AND state='"+state+"'"
								+" AND population"+popR+""+pop+" AND total_deaths"+deathR+""+death+"");
					}else if(name.isEmpty() & !state.isEmpty() & !pop.isEmpty() & !death.isEmpty()) {
						query = ("SELECT * FROM county WHERE state='"+state+"'"
								+" AND population"+popR+""+pop+" AND total_deaths"+deathR+""+death+"");
					}else if(!name.isEmpty() & state.isEmpty() & !pop.isEmpty() & !death.isEmpty()) {
						query = ("SELECT * FROM county WHERE name='"+name+"' AND "
								+" AND population"+popR+""+pop+" AND total_deaths"+deathR+""+death+"");
					}else if(!name.isEmpty() & !state.isEmpty() & pop.isEmpty() & !death.isEmpty()) {
						query = ("SELECT * FROM county WHERE name='"+name+"' AND state='"+state+"'"
								+" AND total_deaths"+deathR+""+death+"");
					}else if(!name.isEmpty() & !state.isEmpty() & !pop.isEmpty() & death.isEmpty()) {
						query = ("SELECT * FROM county WHERE name='"+name+"' AND state='"+state+"'"
								+" AND population"+popR+""+pop+" ");
					}else if(!name.isEmpty() & !state.isEmpty() & pop.isEmpty() & death.isEmpty()) {
						query = ("SELECT * FROM county WHERE state='"+state+"' AND name='"+name+"'");
					}else if(!name.isEmpty() & state.isEmpty() & pop.isEmpty() & !death.isEmpty()) {
						query = ("SELECT * FROM county WHERE name='"+name+"' AND total_deaths"+deathR+"'"+death+"'");
					}else if(!name.isEmpty() & state.isEmpty() & pop.isEmpty() & death.isEmpty()) {
						query = ("SELECT * FROM county WHERE name='"+name+"'");
					}else if(name.isEmpty() & !state.isEmpty() & pop.isEmpty() & death.isEmpty()) {
						query = ("SELECT * FROM county WHERE state='"+state+"'");
					}else if(name.isEmpty() & state.isEmpty() & !pop.isEmpty() & death.isEmpty()) {
						query = ("SELECT * FROM county WHERE population"+popR+""+pop+"");
					}else if(name.isEmpty() & state.isEmpty() & pop.isEmpty() & !death.isEmpty()) {
						query = ("SELECT * FROM county WHERE total_deaths"+deathR+""+death+"");
					}else {
						query = "SELECT * FROM county";
					}
					
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
				if (!enableCounty.isSelected() & enableCases.isSelected() & 
					!enableRace.isSelected() & !enableSocio.isSelected()) {
					String numCase = case_numCase.getText();
					String numCaseR = String.valueOf(case_caseRelation.getSelectedItem());
					String numDeaths = case_numDeaths.getText();
					String numDeathsR = String.valueOf(case_deathRelation.getSelectedItem());
					String stMM = String.valueOf(caseST_comboMM.getSelectedItem());
					String stDD = String.valueOf(caseST_comboDD.getSelectedItem());
					String stYY = String.valueOf(caseST_comboYYYY.getSelectedItem());
					String enMM = String.valueOf(caseEND_comboMM.getSelectedItem());
					String enDD = String.valueOf(caseEND_comboDD.getSelectedItem());
					String enYY = String.valueOf(caseEND_comboYYYY.getSelectedItem());
					
					String query = null;
					//(!numCase.isEmpty() & !numDeaths.isEmpty() & !stYY.isEmpty() & !enYY.isEmpty())
					if(!numCase.isEmpty() & !numDeaths.isEmpty() & !stYY.isEmpty()) {
						query = ("SELECT * FROM cases WHERE confirmed"+numCaseR+" "+numCase+" AND daily_deaths"+numDeathsR+""+numDeaths+""
								+" AND occurred_at > "+stYY+"-"+stMM+"-"+stDD+"");
						//AND occurred_at > "+stYY+"-"+stMM+"-"+stDD+" AND occurred_at < "+enYY+"-"+enMM+"-"+enDD+"
						// AND occurred_at BETWEEN "+stYY+"-"+stMM+"-"+stDD+" AND "+enYY+"-"+enMM+"-"+enDD+"
						//one of these two should work fine, but they donen't, 
					}else if(!numCase.isEmpty() & !numDeaths.isEmpty() & stYY.isEmpty() & enYY.isEmpty()) {
						query = ("SELECT * FROM cases WHERE confirmed"+numCaseR+" "+numCase+" AND daily_deaths"+numDeathsR+""+numDeaths+"");
					}else if(numCase.isEmpty() & !numDeaths.isEmpty() & stYY.isEmpty() & enYY.isEmpty()) {
						query = ("SELECT * FROM cases WHERE daily_deaths"+numDeathsR+""+numDeaths+"");
					}else if(!numCase.isEmpty() & numDeaths.isEmpty() & stYY.isEmpty() & enYY.isEmpty()) {
						query = ("SELECT * FROM cases WHERE confirmed"+numCaseR+" "+numCase+"");
					}else {
						query = "SELECT * FROM cases";
					}
									
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
				if (!enableCounty.isSelected() & !enableCases.isSelected() & 
					enableRace.isSelected() & !enableSocio.isSelected()) {
					String blk = race_blkPercent.getText();
					String blkR = String.valueOf(race_blkRelation.getSelectedItem());
					String amerIn = race_amerInPercent.getText();
					String amerInR = String.valueOf(race_amerInRelation.getSelectedItem());
					String asi = race_asiPercent.getText();
					String asiR = String.valueOf(race_asiRelation.getSelectedItem());
					String haw = race_hawPercent.getText();
					String hawR = String.valueOf(race_hawRelation.getSelectedItem());
					String hisp = race_hispPercent.getText();
					String hispR = String.valueOf(race_hispRelation.getSelectedItem());
					String whi = race_whiPercent.getText();
					String whiR = String.valueOf(race_whiRelation.getSelectedItem());
					
					String query = null;
					if(!blk.isEmpty() & !amerIn.isEmpty() & !asi.isEmpty() & !haw.isEmpty() & !hisp.isEmpty() & !whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+" AND alaska_native"+amerInR+""+amerIn+""
								+ " AND asian"+asiR+""+asi+" AND native_hawaiian"+hawR+""+haw+" AND hispanic"+hispR+""+hisp+" AND nonHispanic_white"+whiR+""+whi+"");
					}else if(blk.isEmpty() & !amerIn.isEmpty() & !asi.isEmpty() & !haw.isEmpty() & !hisp.isEmpty() & !whi.isEmpty()){
						query = ("SELECT * FROM race WHERE alaska_native"+amerInR+""+amerIn+""
								+ " AND asian"+asiR+""+asi+" AND native_hawaiian"+hawR+""+haw+" AND hispanic"+hispR+""+hisp+" AND nonHispanic_white"+whiR+""+whi+"");
					}else if(!blk.isEmpty() & amerIn.isEmpty() & !asi.isEmpty() & !haw.isEmpty() & !hisp.isEmpty() & !whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+"AND asian"+asiR+""+asi+""
								+ " AND native_hawaiian"+hawR+""+haw+" AND hispanic"+hispR+""+hisp+" AND nonHispanic_white"+whiR+""+whi+"");
					}else if(!blk.isEmpty() & !amerIn.isEmpty() & asi.isEmpty() & !haw.isEmpty() & !hisp.isEmpty() & !whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+" AND alaska_native"+amerInR+""+amerIn+""
								+ " AND native_hawaiian"+hawR+""+haw+" AND hispanic"+hispR+""+hisp+" AND nonHispanic_white"+whiR+""+whi+"");
					}else if(!blk.isEmpty() & !amerIn.isEmpty() & !asi.isEmpty() & haw.isEmpty() & !hisp.isEmpty() & !whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+" AND alaska_native"+amerInR+""+amerIn+""
								+ " AND asian"+asiR+""+asi+" AND hispanic"+hispR+""+hisp+" AND nonHispanic_white"+whiR+""+whi+"");
					}else if(!blk.isEmpty() & !amerIn.isEmpty() & !asi.isEmpty() & !haw.isEmpty() & hisp.isEmpty() & !whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+" AND alaska_native"+amerInR+""+amerIn+""
								+ " AND asian"+asiR+""+asi+" AND native_hawaiian"+hawR+""+haw+" AND nonHispanic_white"+whiR+""+whi+"");
					}else if(!blk.isEmpty() & !amerIn.isEmpty() & !asi.isEmpty() & !haw.isEmpty() & !hisp.isEmpty() & whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+" AND alaska_native"+amerInR+""+amerIn+""
								+ " AND asian"+asiR+""+asi+" AND native_hawaiian"+hawR+""+haw+" AND hispanic"+hispR+""+hisp+"");
					}else if(!blk.isEmpty() & amerIn.isEmpty() & asi.isEmpty() & haw.isEmpty() & hisp.isEmpty() & whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE black"+blkR+" "+blk+"");
					}else if(blk.isEmpty() & !amerIn.isEmpty() & asi.isEmpty() & haw.isEmpty() & hisp.isEmpty() & whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE alaska_native"+amerInR+""+amerIn+"");
					}else if(blk.isEmpty() & amerIn.isEmpty() & !asi.isEmpty() & haw.isEmpty() & hisp.isEmpty() & whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE asian"+asiR+""+asi+"");
					}else if(blk.isEmpty() & amerIn.isEmpty() & asi.isEmpty() & !haw.isEmpty() & hisp.isEmpty() & whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE native_hawaiian"+hawR+""+haw+"");
					}else if(blk.isEmpty() & amerIn.isEmpty() & asi.isEmpty() & haw.isEmpty() & !hisp.isEmpty() & whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE hispanic"+hispR+""+hisp+"");
					}else if(blk.isEmpty() & amerIn.isEmpty() & asi.isEmpty() & haw.isEmpty() & hisp.isEmpty() & !whi.isEmpty()) {
						query = ("SELECT * FROM race WHERE nonHispanic_white"+whiR+""+whi+"");
					}
					else {
						query = ("SELECT * FROM race");
					}
					
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
					
				}
				if (!enableCounty.isSelected() & !enableCases.isSelected() & 
						!enableRace.isSelected() & enableSocio.isSelected()) {
					String income = soc_incomeText.getText();
					String incomeR = String.valueOf(soc_incomeRelation.getSelectedItem());
					String life_exp = soc_lifeText.getText();
					String life_expR = String.valueOf(soc_lifeRelation.getSelectedItem());
					String unin = soc_uninText.getText();
					String uninR = String.valueOf(soc_uninRelation.getSelectedItem());
					String mental = soc_mentalText.getText();
					String mentalR = String.valueOf(soc_mentalRelation.getSelectedItem());
					String primary = soc_primaryText.getText();
					String primaryR = String.valueOf(soc_primaryRelation.getSelectedItem());
					
					String query = null;
					if(!income.isEmpty() & !life_exp.isEmpty() & !unin.isEmpty() & !mental.isEmpty() & !primary.isEmpty()) {
						query = ("SELECT * FROM socioeconomic WHERE income"+incomeR+" "+income+" AND life_expectancy"+life_expR+""+life_exp+""
								+ " AND uninsured"+uninR+""+unin+" AND mental_health_providers"+mentalR+""+mental+" AND primary_physicians"+primaryR+""+primary+"");
					}else if(!income.isEmpty() & life_exp.isEmpty() & unin.isEmpty() & mental.isEmpty() & primary.isEmpty()) {
						query = ("SELECT * FROM socioeconomic WHERE income"+incomeR+""+income+"");
					}else if(income.isEmpty() & !life_exp.isEmpty() & unin.isEmpty() & mental.isEmpty() & primary.isEmpty()) {
						query = ("SELECT * FROM socioeconomic WHERE life_expectancy"+life_expR+"'"+life_exp+"'");
					}else if(income.isEmpty() & life_exp.isEmpty() & !unin.isEmpty() & mental.isEmpty() & primary.isEmpty()) {
						query = ("SELECT * FROM socioeconomic WHERE uninsured"+uninR+""+unin+"");
					}else if(income.isEmpty() & life_exp.isEmpty() & unin.isEmpty() & !mental.isEmpty() & primary.isEmpty()) {
						query = ("SELECT * FROM socioeconomic WHERE mental_health_providers"+mentalR+""+mental+"");
					}else if(income.isEmpty() & life_exp.isEmpty() & unin.isEmpty() & mental.isEmpty() & !primary.isEmpty()) {
						query = ("SELECT * FROM socioeconomic WHERE primary_physicians"+primaryR+""+primary+"");
					}else {
						query = "SELECT * FROM socioeconomic";
						}

					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
			}
		});

		// remove current filters
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBConnection dbc = new DBConnection();
				// TODO
				// this should cause the program to reset the filter fields and
				// get the un-filtered version of the database table
				
				// finding what component is visible
				Component[] c = filterPanel.getComponents();
				boolean[] visComp = new boolean[4];
				for (int i = 0; i < c.length; i++) {
					visComp[i] = c[i].isVisible();
				}
				String[] filters = { "County", "Cases", "Race", "Socioeconomic" };
				String toFilter = new String();
				for (int i = 0; i < visComp.length; i++) {
					if (visComp[i] == true)
						toFilter = filters[i];
				}// tableToFilter is the visible filter component. 

				if (toFilter == "County") {
					nameFilter.setText("");
					deathFilter.setText("");
					popFilter.setText("");
					stateDropdown.setSelectedIndex(0);
					popRelation.setSelectedIndex(0);
					deathRelation.setSelectedIndex(0);
					returnPop_cnty.setSelected(true);
					String query = "SELECT * FROM county";
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
				if (toFilter == "Cases") {
					case_numCase.setText("");
					case_numDeaths.setText("");
					
					String query = "SELECT * FROM cases";
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
				if (toFilter == "Race") {
					race_blkPercent.setText("");
					race_amerInPercent.setText("");
					race_asiPercent.setText("");
					race_hawPercent.setText("");
					race_hispPercent.setText("");
					race_whiPercent.setText("");
					String query = "SELECT * FROM race";
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
				if (toFilter == "Socioeconomic") {
					soc_incomeText.setText("");
					soc_lifeText.setText("");
					soc_uninText.setText("");
					soc_mentalText.setText("");
					soc_primaryText.setText("");
					
					String query = "SELECT * FROM socioeconomic";
					userTable.setModel(CommonMethods.resultSetToTableModel(dbc.executeQuery(query)));
				}
			}
		});

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}
}
