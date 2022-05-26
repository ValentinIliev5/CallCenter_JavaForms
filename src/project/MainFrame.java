package project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{

	EmployeePanel employeePanel = new EmployeePanel(); //done
	
	JPanel clientPanel = new ClientsPanel(); //done
	
	JPanel callsPanel = new CallsPanel(); //done
	
	JPanel queryPanel = new QueryPanel();
	
	JTabbedPane tab = new JTabbedPane();
	
	public MainFrame()
	{
		this.setSize(450,650);
		this.setTitle("Call Center");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tab.add(employeePanel, "Employees");
		
		tab.add(clientPanel,"Clients");
		tab.add(callsPanel, "Calls");
		tab.add(queryPanel,"Query");
		this.add(tab);
		
		this.setVisible(true);
	}
}
