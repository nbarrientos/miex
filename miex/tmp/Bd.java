import java.sql.*;
import java.io.*;

public class Bd
{

	public static void main(String[] argv)
	{
		String url = "jdbc:mysql://192.168.0.1:3306/miex";

		try
		{
		Class.forName("com.mysql.jdbc.Driver");

		Connection theConn = DriverManager.getConnection(url, "pfc", "pfc0");

		Statement stmt = theConn.createStatement();


//		FileReader input = new FileReader("/home/nacho/devel/pfc/svn/trunk/miex/share/sql/skeleton.sql");
		FileReader input = new FileReader("skeleton.sql");
		BufferedReader bufRead = new BufferedReader(input);

		String line;

		line = bufRead.readLine();

		while(line != null)
		{
			if(line.length() > 0 && line.charAt(0) != '-')
				stmt.addBatch(line);
			line = bufRead.readLine();
		}

		stmt.executeBatch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
}
