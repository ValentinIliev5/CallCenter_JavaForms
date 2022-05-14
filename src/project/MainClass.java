package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainClass {

	public static void main(String[] args) {
		
		Connection conn=null;
		PreparedStatement state=null;
		ResultSet result=null;
		
		
		conn=DBConnection.getConnection();
		
		String sql="select id from employee";
		
		String item ="";
		try {
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			while(result.next())
			{
				item = result.getObject(1).toString();
				System.out.println(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
