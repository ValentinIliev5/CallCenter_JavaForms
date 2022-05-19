package project;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	
	JLabel fnameL=new JLabel("Name:");
	JLabel lnameL=new JLabel("Last name:");
	JLabel sexL=new JLabel("Sex:");
	JLabel ageL=new JLabel("Age:");
	JLabel salaryL=new JLabel("Salary:");
	
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField ageTF=new JTextField();
	JTextField salaryTF=new JTextField();
	
	String[] item= {"Man", "Woman"};
	JComboBox<String> sexCombo=new JComboBox<String>(item);
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
		upPanel.add(sexL);
		upPanel.add(sexCombo);
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
		
		
		
		//addBt.addActionListener(new AddAction());
		//deleteBt.addActionListener(new DeleteAction());
		//searchBt.addActionListener(new SearchAction());
		//refreshBt.addActionListener(new RefreshAction());
		
		//table.addMouseListener(new MouseAction());
				
		
		//refreshTable();
		//refreshComboPerson();
		
		this.setVisible(true);
		
	}
	
}
