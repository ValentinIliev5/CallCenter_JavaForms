package project;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import project.CallsPanel.SearchAction;

public class QueryPanel extends JPanel {

	Connection conn = null;
	PreparedStatement state=null;
	ResultSet result=null;
	int id=-1;
	
	JPanel upPanel=new JPanel();
	JPanel midPanel=new JPanel();
	JPanel downPanel=new JPanel();
	
	JLabel dateL=new JLabel("Date:");
	JLabel employeeL=new JLabel("Employee:");
	
	JTextField dateTF=new JTextField();
	
	JComboBox<String> employeesCombo = new JComboBox(GetEmployeePhones().toArray());
	
	
	JButton searchBt=new JButton("Search");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
	
	public QueryPanel() 
	{
		this.setLayout(new GridLayout(3,1));
		upPanel.setLayout(new GridLayout(2, 2));
		upPanel.add(dateL);
		upPanel.add(dateTF);
		upPanel.add(employeeL);
		upPanel.add(employeesCombo);
		
		this.add(upPanel);
		
		searchBt.addActionListener(new SearchAction());
		midPanel.setLayout(new GridLayout(2,1));
		midPanel.add(searchBt);
		this.add(midPanel);
		
		myScroll.setPreferredSize(new Dimension(400, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
	}
	class SearchAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			String sql ="SELECT C.ID, C.DATE_OF_CALL,C.START_TIME,C.END_TIME, CLI.PHONE_NUMBER AS CLIENT, EMP.PHONE_NUMBER AS EMPLOYEE FROM CALLS C\r\n"
					+ "LEFT JOIN CALLS_CLIENTS CC \r\n"
					+ "ON CC.CALL_ID = C.ID\r\n"
					+ "LEFT JOIN CLIENTS CLI\r\n"
					+ "ON CLI.ID = CC.CLIENT_ID\r\n"
					+ "LEFT JOIN CALLS_EMPLOYEES CE\r\n"
					+ "ON CE.CALL_ID = C.ID\r\n"
					+ "LEFT JOIN EMPLOYEES EMP \r\n"
					+ "ON EMP.ID = CE.EMPLOYEE_ID\r\n"
					+ "WHERE EMP.PHONE_NUMBER =" + employeesCombo.getSelectedItem().toString()
					+ " AND C.DATE_OF_CALL = ?";
			
			try {
				state = conn.prepareStatement(sql);
				
				java.sql.Date sqlDate = new java.sql.Date(
						formatter.parse(dateTF.getText())
						.getTime());
				
				sqlDate.setMonth(Integer.parseInt(dateTF.getText().split("-")[1])-1);
				
				state.setDate(1, sqlDate);
				result = state.executeQuery();
				table.setModel(new MyTableModel(result));
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
}
