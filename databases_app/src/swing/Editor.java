package swing;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
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
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;

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
	 * @throws SQLException 
	 */
	public Editor() throws SQLException {
		
		//builds connection
		ArrayList c = new ArrayList();
		c = CommonMethods.getCounties("Wisconsin");
		System.out.println(c);
		
		String d = CommonMethods.getStateName("Dane");
		System.out.println(d);
		
		// instantiate all compoenents
		setTitle("Covid-19 Database");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 456);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		JPanel UserPanel = new JPanel();
		contentPanel.add(UserPanel, "USER");
		UserPanel.setLayout(null);
		
		JButton btnEditorLogin = new JButton("Editor Login");
		btnEditorLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEditorLogin.setBounds(0, 123, 99, 23);
		UserPanel.add(btnEditorLogin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 158, 631, 249);
		UserPanel.add(scrollPane);
		
		JList userList = new JList();
		scrollPane.setViewportView(userList);
		userList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton btnCounty = new JButton("County");
		btnCounty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCounty.setBounds(82, 0, 115, 26);
		UserPanel.add(btnCounty);
		
		JButton btnCases = new JButton("Cases");
		btnCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCases.setBounds(229, 0, 115, 26);
		UserPanel.add(btnCases);
		
		JButton btnRace = new JButton("Race");
		btnRace.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRace.setBounds(375, 0, 115, 26);
		UserPanel.add(btnRace);
		
		JButton btnSocioeconomic = new JButton("Socioeconomic");
		btnSocioeconomic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSocioeconomic.setBounds(519, 0, 115, 26);
		UserPanel.add(btnSocioeconomic);
		
		JLabel lblFilterBy = new JLabel("Filter by:");
		lblFilterBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFilterBy.setBounds(0, 5, 64, 16);
		UserPanel.add(lblFilterBy);
		
		JPanel filterPanel = new JPanel();
		filterPanel.setBounds(0, 30, 634, 88);
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
		stateDropdown.setModel(new DefaultComboBoxModel(new String[] {"", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"}));
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
		lblCases.setBounds(334, 12, 55, 16);
		casesFilter.add(lblCases);
		
		JLabel lblDeaths = new JLabel("Deaths");
		lblDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDeaths.setBounds(334, 60, 55, 16);
		casesFilter.add(lblDeaths);
		
		JComboBox caseST_comboMM = new JComboBox();
		caseST_comboMM.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		caseST_comboMM.setToolTipText("");
		caseST_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseST_comboMM.setBounds(85, 8, 50, 25);
		casesFilter.add(caseST_comboMM);
		
		JComboBox caseST_comboDD = new JComboBox();
		caseST_comboDD.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		caseST_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseST_comboDD.setBounds(141, 8, 50, 25);
		casesFilter.add(caseST_comboDD);
		
		JComboBox caseST_comboYYYY = new JComboBox();
		caseST_comboYYYY.setModel(new DefaultComboBoxModel(new String[] {"", "2020"}));
		caseST_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseST_comboYYYY.setBounds(196, 8, 75, 25);
		casesFilter.add(caseST_comboYYYY);
		
		JComboBox caseEND_comboMM = new JComboBox();
		caseEND_comboMM.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		caseEND_comboMM.setToolTipText("");
		caseEND_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseEND_comboMM.setBounds(85, 56, 50, 25);
		casesFilter.add(caseEND_comboMM);
		
		JComboBox caseEND_comboDD = new JComboBox();
		caseEND_comboDD.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		caseEND_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseEND_comboDD.setBounds(141, 56, 50, 25);
		casesFilter.add(caseEND_comboDD);
		
		JComboBox caseEND_comboYYYY = new JComboBox();
		caseEND_comboYYYY.setModel(new DefaultComboBoxModel(new String[] {"", "2020"}));
		caseEND_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		caseEND_comboYYYY.setBounds(196, 56, 75, 25);
		casesFilter.add(caseEND_comboYYYY);
		
		JComboBox case_caseRelation = new JComboBox();
		case_caseRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		case_caseRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_caseRelation.setBounds(381, 8, 63, 25);
		casesFilter.add(case_caseRelation);
		
		case_numCase = new JTextField();
		case_numCase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_numCase.setColumns(10);
		case_numCase.setBounds(456, 11, 114, 20);
		casesFilter.add(case_numCase);
		
		JComboBox case_deathRelation = new JComboBox();
		case_deathRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		case_deathRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_deathRelation.setBounds(381, 56, 63, 25);
		casesFilter.add(case_deathRelation);
		
		case_numDeaths = new JTextField();
		case_numDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		case_numDeaths.setColumns(10);
		case_numDeaths.setBounds(456, 59, 114, 20);
		casesFilter.add(case_numDeaths);
		
		JPanel raceFilter = new JPanel();
		filterPanel.add(raceFilter, "RACE");
		raceFilter.setLayout(null);
		
		JComboBox race_blkRelation = new JComboBox();
		race_blkRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_blkRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_blkRelation.setBounds(153, 0, 63, 25);
		raceFilter.add(race_blkRelation);
		
		race_blkPercent = new JTextField();
		race_blkPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_blkPercent.setColumns(10);
		race_blkPercent.setBounds(222, 3, 63, 20);
		raceFilter.add(race_blkPercent);
		
		JComboBox race_amerInRelation = new JComboBox();
		race_amerInRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_amerInRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_amerInRelation.setBounds(153, 33, 63, 25);
		raceFilter.add(race_amerInRelation);
		
		race_amerInPercent = new JTextField();
		race_amerInPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_amerInPercent.setColumns(10);
		race_amerInPercent.setBounds(222, 37, 63, 20);
		raceFilter.add(race_amerInPercent);
		
		JComboBox race_asiRelation = new JComboBox();
		race_asiRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_asiRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_asiRelation.setBounds(153, 63, 63, 25);
		raceFilter.add(race_asiRelation);
		
		race_asiPercent = new JTextField();
		race_asiPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_asiPercent.setColumns(10);
		race_asiPercent.setBounds(222, 66, 63, 20);
		raceFilter.add(race_asiPercent);
		
		JComboBox race_hawRelation = new JComboBox();
		race_hawRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_hawRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hawRelation.setBounds(478, 0, 63, 25);
		raceFilter.add(race_hawRelation);
		
		race_hawPercent = new JTextField();
		race_hawPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hawPercent.setColumns(10);
		race_hawPercent.setBounds(553, 3, 63, 20);
		raceFilter.add(race_hawPercent);
		
		JComboBox race_hispRelation = new JComboBox();
		race_hispRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_hispRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hispRelation.setBounds(478, 33, 63, 25);
		raceFilter.add(race_hispRelation);
		
		race_hispPercent = new JTextField();
		race_hispPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_hispPercent.setColumns(10);
		race_hispPercent.setBounds(553, 36, 63, 20);
		raceFilter.add(race_hispPercent);
		
		JComboBox race_whiRelation = new JComboBox();
		race_whiRelation.setModel(new DefaultComboBoxModel(EqualityOperator.values()));
		race_whiRelation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_whiRelation.setBounds(478, 63, 63, 25);
		raceFilter.add(race_whiRelation);
		
		race_whiPercent = new JTextField();
		race_whiPercent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		race_whiPercent.setColumns(10);
		race_whiPercent.setBounds(553, 66, 63, 20);
		raceFilter.add(race_whiPercent);
		
		JLabel lblBlack = new JLabel("Black");
		lblBlack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBlack.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBlack.setBounds(0, 4, 141, 16);
		raceFilter.add(lblBlack);
		
		JLabel lblAlaskan = new JLabel("American Indian/Alaskan");
		lblAlaskan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAlaskan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlaskan.setBounds(0, 37, 141, 16);
		raceFilter.add(lblAlaskan);
		
		JLabel lblAsian = new JLabel("Asian");
		lblAsian.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAsian.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAsian.setBounds(0, 67, 141, 16);
		raceFilter.add(lblAsian);
		
		JLabel lblNativeHawaiian = new JLabel("Native Hawaiian");
		lblNativeHawaiian.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNativeHawaiian.setBounds(362, 4, 90, 16);
		raceFilter.add(lblNativeHawaiian);
		
		JLabel lblHispanic = new JLabel("Hispanic");
		lblHispanic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHispanic.setBounds(362, 37, 55, 16);
		raceFilter.add(lblHispanic);
		
		JLabel lblNonhispanicWhite = new JLabel("Non-Hispanic White");
		lblNonhispanicWhite.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNonhispanicWhite.setBounds(362, 67, 111, 16);
		raceFilter.add(lblNonhispanicWhite);
		
		JLabel label = new JLabel("%");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(287, 4, 13, 16);
		raceFilter.add(label);
		
		JLabel label_1 = new JLabel("%");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(287, 67, 13, 16);
		raceFilter.add(label_1);
		
		JLabel label_2 = new JLabel("%");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(287, 37, 13, 16);
		raceFilter.add(label_2);
		
		JLabel label_3 = new JLabel("%");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(616, 4, 18, 16);
		raceFilter.add(label_3);
		
		JLabel label_4 = new JLabel("%");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(616, 37, 18, 16);
		raceFilter.add(label_4);
		
		JLabel label_5 = new JLabel("%");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(616, 67, 18, 16);
		raceFilter.add(label_5);
		
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
		
		JButton btnApplyFilter = new JButton("Apply Filter");
		btnApplyFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApplyFilter.setBounds(536, 121, 98, 26);
		UserPanel.add(btnApplyFilter);
		
		JButton btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnClearFilter.setBounds(426, 121, 98, 26);
		UserPanel.add(btnClearFilter);
		
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
		btnDeleteEntry.setBounds(0, 384, 105, 23);
		EditorPanel.add(btnDeleteEntry);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogout.setBounds(529, 384, 105, 23);
		EditorPanel.add(btnLogout);
		
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setBounds(0, 157, 634, 216);
		EditorPanel.add(scrollTable);
		
		// this list will display all database entries for a given county
		JList editorList = new JList();
		editorList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollTable.setViewportView(editorList);
		editorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblSelectedCounty = new JLabel("Selected County:");
		lblSelectedCounty.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectedCounty.setBounds(0, 3, 95, 14);
		EditorPanel.add(lblSelectedCounty);
		
		JButton btnModifyEntry = new JButton("Modify Entry");
		btnModifyEntry.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnModifyEntry.setBounds(266, 385, 105, 23);
		EditorPanel.add(btnModifyEntry);
		
		JPanel updatePanel = new JPanel();
		updatePanel.setBounds(0, 23, 634, 133);
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
		new_comboMM.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		new_comboMM.setToolTipText("");
		new_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new_comboMM.setBounds(154, 18, 50, 25);
		newEntryPanel.add(new_comboMM);
		
		JComboBox new_comboDD = new JComboBox();
		new_comboDD.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		new_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new_comboDD.setBounds(210, 18, 50, 25);
		newEntryPanel.add(new_comboDD);
		
		JComboBox new_comboYYYY = new JComboBox();
		new_comboYYYY.setModel(new DefaultComboBoxModel(new String[] {"", "2020"}));
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
		mod_comboMM.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mod_comboMM.setToolTipText("");
		mod_comboMM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mod_comboMM.setBounds(440, 6, 50, 25);
		modifyEntryPanel.add(mod_comboMM);
		
		JComboBox mod_comboDD = new JComboBox();
		mod_comboDD.setModel(new DefaultComboBoxModel(new String[] {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		mod_comboDD.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mod_comboDD.setBounds(495, 6, 50, 25);
		modifyEntryPanel.add(mod_comboDD);
		
		JComboBox mod_comboYYYY = new JComboBox();
		mod_comboYYYY.setModel(new DefaultComboBoxModel(new String[] {"", "2020"}));
		mod_comboYYYY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mod_comboYYYY.setBounds(550, 6, 75, 25);
		modifyEntryPanel.add(mod_comboYYYY);
		
		
		//------------------------------------------------------------
		//------------------- ACTIONS --------------------------------
		//------------------------------------------------------------
		
		//------------------- EDITOR VIEW ----------------------------
		// go to user screen on logout
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)contentPanel.getLayout();
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
				
				CardLayout c = (CardLayout)updatePanel.getLayout();
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
			}
		});
		// open new tab within editor screen
		btnDiscardChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)updatePanel.getLayout();
				c.show(updatePanel, "NEW");
			}
		});
		// inserts a new record into the db
		btnAddEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// grab inputs to the add fields and use those to enter
				// a new tuple in the database
			}
		});
		// removes a record
		btnDeleteEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// get selected entry in the table and delete it from
				// the database, then refresh the table
			}
		});
		
		//------------------- USER VIEW ----------------------------
		// go to editor screen on login button
		btnEditorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)contentPanel.getLayout();
				c.show(contentPanel, "EDITOR");
			}
		});
		// open county filter
		btnCounty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)filterPanel.getLayout();
				c.show(filterPanel, "COUNTY");
			}
		});
		// open case filter
		btnCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)filterPanel.getLayout();
				c.show(filterPanel, "CASES");
			}
		});
		// open race filter
		btnRace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)filterPanel.getLayout();
				c.show(filterPanel, "RACE");
			}
		});
		// open socioeconomic filter
		btnSocioeconomic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)filterPanel.getLayout();
				c.show(filterPanel, "SOCIO");
			}
		});
		// apply current filters
		btnApplyFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				// this button press should cause the program to fetch the current
				// entries into the current filter screen. it should then use these
				// arguments to construct a query to send to the database. and lastly
				// update the table to reflect the given query
			}
		});
		// remove current filters
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				// this should cause the program to reset the filter fields and 
				// get the unfiltered version of the database table
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
