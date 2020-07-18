package swing;

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
import java.awt.event.ActionListener;

public class Editor extends JFrame {

	private JPanel contentPanel;
	private JTextField txtMm;
	private JTextField txtDd;
	private JTextField txtYyyy;
	private JTextField txtNewCases;
	private JTextField txtNewDeaths;
	private JTextField txtMm_mod;
	private JTextField txtDd_mod;
	private JTextField txtYyyy_mod;
	private JTextField txtNewCases_mod;
	private JTextField txtNewDeaths_mod;
	private JLabel lblModifedDeaths;
	private final Action action = new SwingAction();

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
	 */
	public Editor() {
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 155, 634, 252);
		UserPanel.add(scrollPane);
		
		JList userList = new JList();
		scrollPane.setViewportView(userList);
		userList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton btnEditorLogin = new JButton("Editor Login");
		btnEditorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)contentPanel.getLayout();
				c.show(contentPanel, "EDITOR");
			}
		});
		btnEditorLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEditorLogin.setBounds(535, 0, 99, 23);
		UserPanel.add(btnEditorLogin);
		
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
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)contentPanel.getLayout();
				c.show(contentPanel, "USER");
			}
		});
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
		
		JPanel modifyEntryPanel = new JPanel();
		updatePanel.add(modifyEntryPanel, "name_153368032374500");
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
		lblModifiedDate.setBounds(315, 11, 150, 14);
		modifyEntryPanel.add(lblModifiedDate);
		
		JLabel lblModifiedCases = new JLabel("Modified Number of Cases");
		lblModifiedCases.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModifiedCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModifiedCases.setBounds(315, 36, 150, 14);
		modifyEntryPanel.add(lblModifiedCases);
		
		lblModifedDeaths = new JLabel("Modifed Number of Deaths");
		lblModifedDeaths.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModifedDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModifedDeaths.setBounds(315, 61, 150, 14);
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
		
		txtMm_mod = new JTextField();
		txtMm_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMm_mod.setText("MM");
		txtMm_mod.setBounds(475, 8, 27, 20);
		modifyEntryPanel.add(txtMm_mod);
		txtMm_mod.setColumns(10);
		
		txtDd_mod = new JTextField();
		txtDd_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDd_mod.setText("DD");
		txtDd_mod.setBounds(512, 8, 27, 20);
		modifyEntryPanel.add(txtDd_mod);
		txtDd_mod.setColumns(10);
		
		txtYyyy_mod = new JTextField();
		txtYyyy_mod.setText("YYYY");
		txtYyyy_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYyyy_mod.setBounds(549, 8, 40, 20);
		modifyEntryPanel.add(txtYyyy_mod);
		txtYyyy_mod.setColumns(10);
		
		txtNewCases_mod = new JTextField();
		txtNewCases_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNewCases_mod.setText("New Cases");
		txtNewCases_mod.setBounds(475, 34, 86, 20);
		modifyEntryPanel.add(txtNewCases_mod);
		txtNewCases_mod.setColumns(10);
		
		txtNewDeaths_mod = new JTextField();
		txtNewDeaths_mod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNewDeaths_mod.setText("New Deaths");
		txtNewDeaths_mod.setBounds(475, 59, 86, 20);
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
		
		JPanel newEntryPanel = new JPanel();
		updatePanel.add(newEntryPanel, "name_152709467644400");
		newEntryPanel.setLayout(null);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(44, 23, 100, 14);
		newEntryPanel.add(lblDate);
		
		JLabel lblNumberOfCases = new JLabel("Number of Cases:");
		lblNumberOfCases.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfCases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfCases.setBounds(33, 56, 111, 14);
		newEntryPanel.add(lblNumberOfCases);
		
		JLabel lblNumberOfDeaths = new JLabel("Number of Deaths:");
		lblNumberOfDeaths.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfDeaths.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfDeaths.setBounds(33, 92, 111, 14);
		newEntryPanel.add(lblNumberOfDeaths);
		
		txtMm = new JTextField();
		txtMm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMm.setText("MM");
		txtMm.setBounds(154, 21, 25, 20);
		newEntryPanel.add(txtMm);
		txtMm.setColumns(10);
		
		txtDd = new JTextField();
		txtDd.setText("DD");
		txtDd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDd.setColumns(10);
		txtDd.setBounds(189, 21, 25, 20);
		newEntryPanel.add(txtDd);
		
		txtYyyy = new JTextField();
		txtYyyy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYyyy.setText("YYYY");
		txtYyyy.setBounds(224, 21, 40, 20);
		newEntryPanel.add(txtYyyy);
		txtYyyy.setColumns(10);
		
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
