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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import project.EmployeePanel.AddAction;
import project.EmployeePanel.DeleteAction;
import project.EmployeePanel.MouseAction;
import project.EmployeePanel.SearchAction;
import project.EmployeePanel.UpdateAction;


public class CallsPanel extends JPanel{
	Connection conn = null;
	PreparedStatement state=null;
	ResultSet result=null;
	int id=-1;
	
	JPanel upPanel=new JPanel();
	JPanel midPanel=new JPanel();
	JPanel downPanel=new JPanel();
	
	JPanel midPanel1= new JPanel();
	JPanel midPanel2= new JPanel();
	
	JLabel dateL=new JLabel("Date:");
	JLabel startL=new JLabel("Start:");
	JLabel endL=new JLabel("End:");
	JLabel employeeL=new JLabel("Employee:");
	JLabel clientL=new JLabel("Client:");
	
	JTextField dateTF=new JTextField();
	JTextField startTF=new JTextField();
	JTextField endTF=new JTextField();
	JTextField searchTF=new JTextField();
	
	String[] items = {"Date","Start","End","Employee","Client"};
	
	JComboBox<String> employeesCombo = new JComboBox(GetEmployeePhones().toArray());
	JComboBox<String> clientsCombo = new JComboBox(GetClientPhones().toArray());
	JComboBox<String> queryCombo=new JComboBox<String>(items);
	JButton addBt=new JButton("Add");
	JButton deleteBt=new JButton("Delete");
	JButton editBt=new JButton("Edit");
	JButton searchBt=new JButton("Search by ");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
	
	public CallsPanel()
	{
		this.setLayout(new GridLayout(3,1));
		
		upPanel.setLayout(new GridLayout(6, 2));
		
		upPanel.add(dateL);
		upPanel.add(dateTF);
		upPanel.add(startL);
		upPanel.add(startTF);
		upPanel.add(endL);
		upPanel.add(endTF);
		upPanel.add(employeeL);
		upPanel.add(employeesCombo);
		upPanel.add(clientL);
		upPanel.add(clientsCombo);
		
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
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
		
		
		addBt.addActionListener(new AddAction());
		deleteBt.addActionListener(new DeleteAction());
		searchBt.addActionListener(new SearchAction());
		//editBt.addActionListener(new UpdateAction());
		
		table.addMouseListener(new MouseAction());
				
		
		RefreshTable();
		
		this.setVisible(true);
	}
	
	public int GetEmployeeID(String phoneNumber)
	{
		String sql = "SELECT ID FROM EMPLOYEES WHERE PHONE_NUMBER='"+phoneNumber+"'";
		int employeeID =-1;
		
		try {
			state = conn.prepareStatement(sql);
			conn = DBConnection.getConnection();
			result = state.executeQuery();
			while(result.next())
			{
				employeeID=result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeID;
	}
	
	public int GetClientID(String phoneNumber)
	{
		String sql = "SELECT ID FROM CLIENTS WHERE PHONE_NUMBER='"+phoneNumber+"'";
		int clientID =-1;
		
		try {
			state = conn.prepareStatement(sql);
			conn = DBConnection.getConnection();
			result = state.executeQuery();
			while(result.next())
			{
				clientID=result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientID;
	}
	
	public ArrayList<String> GetEmployeePhones()
	{
		ArrayList<String> employeePhones = new ArrayList<String>() ;
		String sql = "SELECT PHONE_NUMBER FROM EMPLOYEES ";
		
		try {
			conn = DBConnection.getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			while(result.next())
			{
				employeePhones.add(result.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeePhones;
	}
	
	public int GetLastCallID()
	{
		int callID=-1;
		String sql = "SELECT ID FROM CALLS";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.last();
			callID = result.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return callID;
	}
	
	public ArrayList<String> GetClientPhones()
	{
		ArrayList<String> clientPhones = new ArrayList<String>() ;
		String sql = "SELECT PHONE_NUMBER  FROM CLIENTS ";
		
		try {
			conn = DBConnection.getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			while(result.next())
			{
				clientPhones.add(result.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientPhones;
	}
	
	public void ClearTextFields()
	{
		dateTF.setText("");
		startTF.setText("");
		endTF.setText("");
		searchTF.setText("");
	}
	
	public void RefreshTable()
	{
		String sql = "SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
				+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
				+ "ON CC.CALL_ID = C.ID\r\n"
				+ "LEFT JOIN CLIENTS CLI\r\n"
				+ "ON CLI.ID = CC.CLIENT_ID\r\n"
				+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
				+ "ON CE.CALL_ID = C.ID\r\n"
				+ "LEFT JOIN EMPLOYEES EMP "
				+ "ON EMP.ID = CE.EMPLOYEE_ID";
		
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
			
			String sql = "INSERT INTO CALLS(DATE_OF_CALL,START_TIME,END_TIME) "
					+ "VALUES(?,?,?)";
			int empID = GetEmployeeID(employeesCombo.getSelectedItem().toString());
			int cliID =GetClientID(clientsCombo.getSelectedItem().toString());
			
			
			try {
				state = conn.prepareStatement(sql);
				
				
				java.util.Date date = formatter.parse(dateTF.getText());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				state.setDate(1, sqlDate);
				state.setTime(2, java.sql.Time.valueOf(startTF.getText()));
				state.setTime(3, java.sql.Time.valueOf(endTF.getText()));
				state.execute();

				sql = "INSERT INTO CALLS_EMPLOYEES(CALL_ID,EMPLOYEE_ID) "
						+ "VALUES(" + GetLastCallID() + ", " + empID +")";
				
				state = conn.prepareStatement(sql);
				state.execute();
				
				sql = "INSERT INTO CALLS_CLIENTS(CALL_ID,CLIENT_ID) "
						+ "VALUES(" + GetLastCallID() + ", " + cliID +")";
				
				state = conn.prepareStatement(sql);
				state.execute();
				
				RefreshTable();
				ClearTextFields();
				
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				 //TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
	}
	
	class MouseAction implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			dateTF.setText(table.getValueAt(row, 1).toString());
			startTF.setText(table.getValueAt(row, 2).toString());
			endTF.setText(table.getValueAt(row, 3).toString());
			employeesCombo.getModel().setSelectedItem(table.getValueAt(row, 4).toString());
			clientsCombo.getModel().setSelectedItem(table.getValueAt(row, 5).toString());
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
	
	class DeleteAction implements ActionListener
	{
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		conn = DBConnection.getConnection();
		
		String sql = "DELETE FROM CALLS_EMPLOYEES WHERE CALL_ID=" +id;
		
		try {
			state=conn.prepareStatement(sql);
			state.execute();
			
			sql = "DELETE FROM CALLS_CLIENTS WHERE CALL_ID=" +id;
			state=conn.prepareStatement(sql);
			state.execute();
			
			sql = "DELETE FROM CALLS WHERE ID=" +id;
			state=conn.prepareStatement(sql);
			state.execute();
			
			} 
		catch (SQLException e1) 
			{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
		RefreshTable();
		ClearTextFields();
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
				//"Date","Start","End","Employee","Client"
				conn = DBConnection.getConnection();
				String sql = "";
				String choice = queryCombo.getSelectedItem().toString();
				
				switch(choice)
				{
				case "Date": sql = "SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
						+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
						+ "ON CC.CALL_ID = C.ID\r\n"
						+ "LEFT JOIN CLIENTS CLI\r\n"
						+ "ON CLI.ID = CC.CLIENT_ID\r\n"
						+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
						+ "ON CE.CALL_ID = C.ID\r\n"
						+ "LEFT JOIN EMPLOYEES EMP "
						+ "ON EMP.ID = CE.EMPLOYEE_ID "
						+ "WHERE C.DATE_OF_CALL= ?";
					try {
						state = conn.prepareStatement(sql);
						java.util.Date date = formatter.parse(searchTF.getText());
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());
						state.setDate(1, sqlDate);
						result = state.executeQuery();
						table.setModel(new MyTableModel(result));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "Start" :
					sql = "SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
							+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
							+ "ON CC.CALL_ID = C.ID\r\n"
							+ "LEFT JOIN CLIENTS CLI\r\n"
							+ "ON CLI.ID = CC.CLIENT_ID\r\n"
							+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
							+ "ON CE.CALL_ID = C.ID\r\n"
							+ "LEFT JOIN EMPLOYEES EMP "
							+ "ON EMP.ID = CE.EMPLOYEE_ID "
							+ "WHERE C.START_TIME= ?";
					try {
						state = conn.prepareStatement(sql);
						state.setTime(1, java.sql.Time.valueOf(searchTF.getText()));
						result = state.executeQuery();
						table.setModel(new MyTableModel(result));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "End":
					sql = "SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
							+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
							+ "ON CC.CALL_ID = C.ID\r\n"
							+ "LEFT JOIN CLIENTS CLI\r\n"
							+ "ON CLI.ID = CC.CLIENT_ID\r\n"
							+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
							+ "ON CE.CALL_ID = C.ID\r\n"
							+ "LEFT JOIN EMPLOYEES EMP "
							+ "ON EMP.ID = CE.EMPLOYEE_ID "
							+ "WHERE C.END_TIME= ?";
					try {
						state = conn.prepareStatement(sql);
						state.setTime(1, java.sql.Time.valueOf(searchTF.getText()));
						result = state.executeQuery();
						table.setModel(new MyTableModel(result));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "Employee":
					sql = "SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
						+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
						+ "ON CC.CALL_ID = C.ID\r\n"
						+ "LEFT JOIN CLIENTS CLI\r\n"
						+ "ON CLI.ID = CC.CLIENT_ID\r\n"
						+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
						+ "ON CE.CALL_ID = C.ID\r\n"
						+ "LEFT JOIN EMPLOYEES EMP "
						+ "ON EMP.ID = CE.EMPLOYEE_ID "
						+ "WHERE EMP.PHONE_NUMBER= ?";
					try {
						state = conn.prepareStatement(sql);
						state.setString(1, searchTF.getText());
						result = state.executeQuery();
						table.setModel(new MyTableModel(result));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "Client":
					sql = "SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
							+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
							+ "ON CC.CALL_ID = C.ID\r\n"
							+ "LEFT JOIN CLIENTS CLI\r\n"
							+ "ON CLI.ID = CC.CLIENT_ID\r\n"
							+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
							+ "ON CE.CALL_ID = C.ID\r\n"
							+ "LEFT JOIN EMPLOYEES EMP "
							+ "ON EMP.ID = CE.EMPLOYEE_ID "
							+ "WHERE CLI.PHONE_NUMBER= ?";
						try {
							state = conn.prepareStatement(sql);
							state.setString(1, searchTF.getText());
							result = state.executeQuery();
							table.setModel(new MyTableModel(result));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					break;
				}
			}
			
		}
		
	}
}

