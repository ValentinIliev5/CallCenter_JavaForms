package project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{

	EmployeePanel employeePanel = new EmployeePanel();
	JPanel clientPanel = new JPanel();
	JPanel callsPanel = new JPanel();
	
	JTabbedPane tab = new JTabbedPane();
	
	public MainFrame()
	{
		this.setSize(450,650);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tab.add(employeePanel, "Employees");
		tab.add(clientPanel,"Clients");
		tab.add(callsPanel, "Calls");
		
		this.add(tab);
		
		this.setVisible(true);
	}
}
