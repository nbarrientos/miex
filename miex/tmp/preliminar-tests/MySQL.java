import java.sql.*;

class MySQL
{

	public static void main(String args[])
	{
			try 
			{

			Statement stmt;
      ResultSet rs;

      //Register the JDBC driver for MySQL.
      Class.forName("com.mysql.jdbc.Driver");

      //Define URL of database server for
      // database named JunkDB on the localhost
      // with the default port number 3306.
      String url = "jdbc:mysql://zoidberg.criptonita.com:3306/miex";

      //Get a connection to the database for a
      // user named auser with the password
      // drowssap, which is password spelled
      // backwards.
      Connection con = DriverManager.getConnection(url,"pfc", "pfc0");

      //Display URL and connection information
      System.out.println("URL: " + url);
      System.out.println("Connection: " + con);

      //Get a Statement object
      stmt = con.createStatement();

			stmt.execute("DROP TABLE IF EXISTS myTable");

			stmt.executeUpdate("CREATE TABLE myTable(test_id int," + "test_val char(15) not null)");

			stmt.executeUpdate("INSERT INTO myTable(test_id, " + "test_val) VALUES(1,'One')");
			stmt.executeUpdate("INSERT INTO myTable(test_id, " + "test_val) VALUES(2,'Two')");

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery("SELECT * " + "from myTable ORDER BY test_id");

			System.out.println("Display all results:");

      while(rs.next())
			{
      	int theInt= rs.getInt("test_id");
        String str = rs.getString("test_val");

        System.out.println("\ttest_id= " + theInt + "\tstr = " + str);
      }

			}
			catch(Exception e)
			{
				e.printStackTrace();
      }//end catch 

	} // main

} // class
