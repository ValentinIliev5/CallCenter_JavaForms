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
	
	JPanel midPanel1= new JPanel();
	JPanel midPanel2= new JPanel();
	
	JLabel fnameL=new JLabel("First Name:");
	JLabel lnameL=new JLabel("Last name:");
	JLabel emailL=new JLabel("Email:");
	JLabel ageL=new JLabel("Age:");
	JLabel salaryL=new JLabel("Salary:");
	JLabel phoneNumberL=new JLabel("Phone Number:");
	
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField emailTF=new JTextField();
	JTextField ageTF=new JTextField();
	JTextField salaryTF=new JTextField();
	JTextField phoneNumberTF=new JTextField();
	JTextField searchTF=new JTextField();
	
	String[] items = {"First Name","Last Name","Email","Age","Salary","Phone number"};
	JComboBox<String> queryCombo=new JComboBox<String>(items);
	JButton addBt=new JButton("Add");
	JButton deleteBt=new JButton("Delete");
	JButton editBt=new JButton("Edit");
	JButton searchBt=new JButton("Search by ");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	
	public EmployeePanel() {
		this.setLayout(new GridLayout(3,1));
		
		//upPanel-----------------------------------------
		upPanel.setLayout(new GridLayout(6, 2));
		
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
		upPanel.add(phoneNumberL);
		upPanel.add(phoneNumberTF);
		
		this.add(upPanel);
		
		//midPanel------------------------------------
		midPanel.setLayout(new GridLayout(2,1));
		midPanel1.add(addBt);
		midPanel1.add(deleteBt);
		midPanel1.add(editBt);
		midPanel2.add(searchBt);
		midPanel2.add(queryCombo);
		midPanel2.add(searchTF);
		
		midPanel2.setLayout(new GridLayout(3,1));
		
		midPanel.add(midPanel1);
		midPanel.add(midPanel2);
		
		
		this.add(midPanel);
		
		//downPanel-----------------------------------
		myScroll.setPreferredSize(new Dimension(400, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
		
		
		addBt.addActionListener(new AddAction());
		deleteBt.addActionListener(new DeleteAction());
		searchBt.addActionListener(new SearchAction());
		editBt.addActionListener(new UpdateAction());
		
		table.addMouseListener(new MouseAction());
				
		
		RefreshTable();
		
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
	
	public void ClearTextFields()
	{
		fnameTF.setText("");
		lnameTF.setText("");
		emailTF.setText("");
		ageTF.setText("");
		salaryTF.setText("");
		phoneNumberTF.setText("");
	}
	class MouseAction implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			
			fnameTF.setText(table.getValueAt(row, 1).toString());
			lnameTF.setText(table.getValueAt(row, 2).toString());
			emailTF.setText(table.getValueAt(row, 3).toString());
			ageTF.setText(table.getValueAt(row, 4).toString());
			salaryTF.setText(table.getValueAt(row, 5).toString());
			phoneNumberTF.setText(table.getValueAt(row, 6).toString());
					
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			conn = DBConnection.getConnection();
			
			String sql = "INSERT INTO EMPLOYEES(FNAME,LNAME,EMAIL,AGE,SALARY,PHONE_NUMBER)"
					+ "VALUES(?,?,?,?,?,?)";
			
			try {
				state = conn.prepareStatement(sql);
				
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, emailTF.getText());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5, Float.parseFloat(salaryTF.getText()));
				state.setString(6, phoneNumberTF.getText());
				state.execute();

				RefreshTable();
				ClearTextFields();
				
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
	}
	class UpdateAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			
			String sql = "UPDATE EMPLOYEES "
					+ "SET FNAME = ? , LNAME = ? , EMAIL = ? ,"
					+ "AGE = ? , SALARY = ? ,"
					+ "PHONE_NUMBER = ? "
					+ "WHERE ID = ?";
			
			
			try {
				state = conn.prepareStatement(sql);
				
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, emailTF.getText());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5,Float.parseFloat(salaryTF.getText()));
				state.setString(6, phoneNumberTF.getText());
				state.setInt(7, id);
				
				state.execute();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RefreshTable();
			ClearTextFields();
		}
		
	}
	class DeleteAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			conn = DBConnection.getConnection();
			
			String sql = "DELETE FROM EMPLOYEES WHERE ID = ?";
			
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RefreshTable();
		}
		
	}
	class SearchAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(searchTF.getText().isEmpty() || searchTF.getText().isBlank())
			{
				RefreshTable();
			}
			else 
			{
				conn = DBConnection.getConnection();
				String sql = "";
				String choice = queryCombo.getSelectedItem().toString();
				switch(choice) 
				{
					//"First Name","Last Name","Email","Age","Salary","Phone number"
					case "First Name" : sql = "SELECT * FROM EMPLOYEES"
					+ " WHERE FNAME = ?";
					break;
			
					case "Last Name" : sql = "SELECT * FROM EMPLOYEES"
					+ " WHERE LNAME = ?";
					break;
			
					case "Email" : sql = "SELECT * FROM EMPLOYEES"
					+ " WHERE EMAIL = ?";
					break;
			
					case "Age" : sql = "SELECT * FROM EMPLOYEES"
					+ " WHERE AGE = ?";
					
					break;
			
					case "Salary" : sql = "SELECT * FROM EMPLOYEES"
					+ " WHERE SALARY >= ?";
					break;
			
					case "Phone number" : sql = "SELECT * FROM EMPLOYEES"
					+ " WHERE PHONE_NUMBER = ?";
					break;
				}
			
				try {
					state = conn.prepareStatement(sql);
					if(choice.equals("Age"))
					{
						state.setInt(1, Integer.parseInt(searchTF.getText()));
					}
					if(choice.equals("Salary"))
					{
						state.setFloat(1, Float.parseFloat(searchTF.getText()));
					
					}
					if(!choice.equals("Age")&& !choice.equals("Salary"))
					{
						state.setString(1, searchTF.getText());
					}
					result = state.executeQuery();
					table.setModel(new MyTableModel(result));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
			
		
	}
	}

