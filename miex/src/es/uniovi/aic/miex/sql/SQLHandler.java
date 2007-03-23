package es.uniovi.aic.miex.sql;

import java.sql.*;

import es.uniovi.aic.miex.config.ConfigFile;
import es.uniovi.aic.miex.datastr.MyCategories;

public class SQLHandler
{

	public SQLHandler(ConfigFile cf)
	{
		String url = "jdbc:mysql://" + cf.getStringSetting("BDHostname") 
									+ ":3306/" + cf.getStringSetting("BDName");
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
	
			theConn = DriverManager.getConnection(url,cf.getStringSetting("BDUser"), cf.getStringSetting("BDPassword"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public SQLHandler(String BDHostname,
										String BDUser,
										String BDPassword,
										String BDName)
	{
		String url = "jdbc:mysql://" + BDHostname + ":3306/" + BDName;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			theConn = DriverManager.getConnection(url,BDUser, BDPassword);			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// ---
	// ---- getNewCollectionID
  // ---
	
	public int getNewCollectionID(String fileName)
	{
		ResultSet rs;
		Statement stmt;
		String query;
		int out = -1;
	
		try
		{	
			stmt = this.createStatement();

			query = "SELECT name " + "FROM collection WHERE name = '" + fileName + "'";

			rs = stmt.executeQuery(query);

			rs.last();

			if(rs.getRow() > 0)
				return out;
			else
			{
				query = "INSERT INTO collection (name) VALUES ('" + fileName + "')";
				stmt.executeUpdate(query);
			}

			rs.close();

			rs = stmt.getGeneratedKeys();

			if (rs.next())
		 	{
        out = rs.getInt(1);
			} 
    	
			rs.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return out;
	
	}

	// ----
	// --- addDocument
	// ----

	public void addDocument(int docNumber, int collectionNumber, String title, boolean isTrain)
	{

    Statement stmt;
    String query;

    // Id 1 (doctype) = DOCTRAIN
    // Id 2 (doctype) = DOCTEST

		int docTypeID = 0;

		if(isTrain) docTypeID = 1;

    try
    {
      stmt = this.createStatement();

      query = "INSERT INTO document (document_id,collection_id,title,doctype_id) VALUES ('" +
							 docNumber + "','" + collectionNumber + "','" + title + "','" + docTypeID + "')"; 
																
      stmt.executeUpdate(query);
    }
    catch (Exception e)
    {
      e.printStackTrace();
		}

	}

	/// ----
	/// -- addCategory
	///	----

	private int addCategory(String catName)
	{
		Statement stmt;
		ResultSet rs;
		String query;

		int out = 0;

    try
    {
      stmt = this.createStatement();

      query = "SELECT category_id " + "FROM category WHERE string = '" + catName.trim().toLowerCase() + "'";

      rs = stmt.executeQuery(query);

      rs.last();

      if(rs.getRow() > 0) // 1
        return rs.getInt("category_id");
      else
      {
        query = "INSERT INTO category (string) VALUES ('" + catName.trim().toLowerCase() + "')";
        stmt.executeUpdate(query);
      }

      rs.close();

      rs = stmt.getGeneratedKeys();

      if (rs.next())
      {
        out = rs.getInt(1);
      }

      rs.close();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

		return out;

	}

	/// ---
	/// ---- addCategories
	/// ---
	
  public void addCategories(int docNumber, int collectionNumber, MyCategories categories)
  {

    Statement stmt;
    String query;

		int cat_ID;

		for(String cat: categories.toArray())
		{
			cat_ID = addCategory(cat);

    	try
	    {
  	    stmt = this.createStatement();

	      query = "INSERT INTO doccat (doc_id,col_id,cat_id) VALUES ('" +
               docNumber + "','" + collectionNumber + "','" + cat_ID + "')";

	      stmt.executeUpdate(query);
	    }
	    catch (Exception e)
	    {
		     e.printStackTrace();
    	}
  	}
	}

	private Statement createStatement() throws SQLException
	{
		return theConn.createStatement();
	}

	// Members
	Connection theConn;

}	
