package project;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class EmployeePanel extends JPanel{

	Connection conn = null;
	PreparedStatement state=null;
	ResultSet result=null;
	int id=-1;
	
	JPanel upPanel=new JPanel();
	JPanel midPanel=new JPanel();
	JPanel downPanel=new JPanel();
	
	JLabel fnameL=new JLabel("First Name:");
	JLabel lnameL=new JLabel("Last name:");
	JLabel emailL=new JLabel("Email:");
	JLabel ageL=new JLabel("Age:");
	JLabel salaryL=new JLabel("Salary:");
	
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField emailTF=new JTextField();
	JTextField ageTF=new JTextField();
	
	JTextField salaryTF=new JTextField();
	
	JComboBox<String> personCombo=new JComboBox<String>();
	
	JButton addBt=new JButton("Add");
	JButton deleteBt=new JButton("Delete");
	JButton editBt=new JButton("Edit");
	JButton searchBt=new JButton("Search by ");
	JButton refreshBt=new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	
	public EmployeePanel() {
		this.setLayout(new GridLayout(3,1));
		
		//upPanel-----------------------------------------
		upPanel.setLayout(new GridLayout(5, 2));
		
		upPanel.add(fnameL);
		upPanel.add(fnameTF);
		upPanel.add(lnameL);
		upPanel.add(lnameTF);
		upPanel.add(emailL);
		upPanel.add(emailTF);
		upPanel.add(ageL);
		upPanel.add(ageTF);
		upPanel.add(salaryL);
		upPanel.add(salaryTF);
		
		this.add(upPanel);
		
		//midPanel------------------------------------
		
		midPanel.add(addBt);
		midPanel.add(deleteBt);
		midPanel.add(editBt);
		midPanel.add(searchBt);
		midPanel.add(refreshBt);
		midPanel.add(personCombo);
		
		this.add(midPanel);
		
		//downPanel-----------------------------------
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
		
		
		addBt.addActionListener(new AddAction());
		//deleteBt.addActionListener(new DeleteAction());
		//searchBt.addActionListener(new SearchAction());
		//refreshBt.addActionListener(new RefreshAction());
		
		//table.addMouseListener(new MouseAction());
				
		
		RefreshTable();
		//refreshComboPerson();
		
		this.setVisible(true);
		
	}
	
	public void RefreshTable()
	{
		String sql = "SELECT * FROM EMPLOYEES";
		
		try {
			
			conn = DBConnection.getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			
			table.setModel(new MyTableModel(result));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			conn = DBConnection.getConnection();
			
			String sql = "INSERT INTO EMPLOYEES(FNAME,LNAME,EMAIL,AGE,SALARY)"
					+ "VALUES(?,?,?,?,?)";
			
			try {
				state = conn.prepareStatement(sql);
				
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, emailTF.getText());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5, Float.parseFloat(salaryTF.getText()));
				
				state.execute();

				RefreshTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
	}
	
}
