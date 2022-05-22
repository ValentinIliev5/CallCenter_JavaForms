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


public class ClientsPanel extends JPanel{

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
	JLabel phoneNumberL=new JLabel("Phone Number:");
	
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField emailTF=new JTextField();
	JTextField phoneNumberTF=new JTextField();
	JTextField searchTF = new JTextField();
	
	String[] items = {"First Name","Last Name","Email","Phone number"};
	JComboBox<String> queryCombo=new JComboBox<String>(items);
	
	
	JButton addBt=new JButton("Add");
	JButton deleteBt=new JButton("Delete");
	JButton editBt=new JButton("Edit");
	JButton searchBt=new JButton("Search by ");
	JButton refreshBt=new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	
	public ClientsPanel() {
		this.setLayout(new GridLayout(3,1));
		
		//upPanel-----------------------------------------
		upPanel.setLayout(new GridLayout(4, 2));
		
		upPanel.add(fnameL);
		upPanel.add(fnameTF);
		upPanel.add(lnameL);
		upPanel.add(lnameTF);
		upPanel.add(emailL);
		upPanel.add(emailTF);
		upPanel.add(phoneNumberL);
		upPanel.add(phoneNumberTF);
		
		this.add(upPanel);
		
		//midPanel------------------------------------
		midPanel.setLayout(new GridLayout(2,1));
		midPanel2.setLayout(new GridLayout(3,1));
		midPanel1.add(addBt);
		midPanel1.add(deleteBt);
		midPanel1.add(editBt);
		midPanel2.add(searchBt);
		midPanel2.add(queryCombo);
		midPanel2.add(searchTF);
		midPanel.add(midPanel1);
		midPanel.add(midPanel2);
		this.add(midPanel);
		
		//downPanel-----------------------------------
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
		
		
		addBt.addActionListener(new AddAction());
		editBt.addActionListener(new UpdateAction());
		deleteBt.addActionListener(new DeleteAction());
		searchBt.addActionListener(new SearchAction());
		
		table.addMouseListener(new MouseAction());
				
		
		RefreshTable();
		//refreshComboPerson();
		
		this.setVisible(true);
		
	}
	public void ClearTextFields()
	{
		fnameTF.setText("");
		lnameTF.setText("");
		emailTF.setText("");
		phoneNumberTF.setText("");
	}
	public void RefreshTable()
	{
		String sql = "SELECT * FROM CLIENTS";
		
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
	
	class MouseAction implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			
			fnameTF.setText(table.getValueAt(row, 1).toString());
			lnameTF.setText(table.getValueAt(row, 2).toString());
			emailTF.setText(table.getValueAt(row, 3).toString());
			phoneNumberTF.setText(table.getValueAt(row, 4).toString());
			
			
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
			
			String sql = "INSERT INTO CLIENTS(FNAME,LNAME,EMAIL,PHONE_NUMBER)"
					+ "VALUES(?,?,?,?)";
			
			try {
				state = conn.prepareStatement(sql);
				
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, emailTF.getText());
				state.setString(4, phoneNumberTF.getText());
				state.execute();

				RefreshTable();
				
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
	}
	class SearchAction implements ActionListener
	{
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
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
				case "First Name" : sql = "SELECT * FROM CLIENTS"
					+ " WHERE FNAME LIKE ?";
				break;
			
				case "Last Name" : sql = "SELECT * FROM CLIENTS"
					+ " WHERE LNAME LIKE ?";
				break;
			
				case "Email" : sql = "SELECT * FROM CLIENTS"
					+ " WHERE EMAIL LIKE ?";
				break;
			
				case "Phone number" : sql = "SELECT * FROM CLIENTS"
					+ " WHERE PHONE_NUMBER LIKE ?";
				break;
			
				}
			
				try 
				{
					state = conn.prepareStatement(sql);
					state.setString(1, searchTF.getText());
				
					result = state.executeQuery();
					table.setModel(new MyTableModel(result));
				} catch (SQLException e1) 
				{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				} catch (Exception e1) 
				{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
				}
			}
		
	}
	class UpdateAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			
			String sql = "UPDATE CLIENTS "
					+ "SET FNAME = ? , LNAME = ? , EMAIL = ? ,"
					+ "PHONE_NUMBER = ? "
					+ "WHERE ID = ?";
			
			
			try {
				state = conn.prepareStatement(sql);
				
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, emailTF.getText());
				state.setString(4, phoneNumberTF.getText());
				state.setInt(5, id);
				
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
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			
			String sql = "DELETE FROM CLIENTS WHERE ID = ?";
			
			try {
				state= conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RefreshTable();
			
		}
		
	}
}
