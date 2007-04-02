package es.uniovi.aic.miex.sql;

import java.sql.*;
import java.io.*;

import es.uniovi.aic.miex.config.ConfigFile;
import es.uniovi.aic.miex.datastr.MyCategories;

import java.util.ArrayList;

import edu.stanford.nlp.ling.TaggedWord;

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

	public void executeSQLFile(String path)
	{
		/* BE CAREFUL WITH SQL FILE FORMAT, ONE SQL STATEMENT PER LINE!! */
		Statement stmt;
		String line;

		try
		{
			stmt = this.createStatement();

			FileReader input = new FileReader(path);
			BufferedReader bufRead = new BufferedReader(input);

			line = bufRead.readLine();

			while(line != null)
			{
				if(line.length() > 0 && !(line.matches("^-+.*")))
					stmt.addBatch(line);
				
				line = bufRead.readLine();
			}

			bufRead.close();

			stmt.executeBatch();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
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

  /// ----
  /// -- addWord
  /// ----

  private int addWord(String wordName)
  {
    Statement stmt;
    ResultSet rs;
    String query;

    int out = 0;

    try
    {
      stmt = this.createStatement();

      query = "SELECT word_id " + "FROM word WHERE string = '" + wordName.trim().toLowerCase() + "'";

      rs = stmt.executeQuery(query);

      rs.last();

      if(rs.getRow() > 0) // 1
        return rs.getInt("word_id");
      else
      {
        query = "INSERT INTO word (string) VALUES ('" + wordName.trim().toLowerCase() + "')";
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

  /// ----
  /// -- addProperty
  /// ----

  private int addProperty(boolean isGrammatical, String propName)
  {
    Statement stmt;
    ResultSet rs;
    String query;

    int out = 0;

    try
    {
      stmt = this.createStatement();

			// Id 1 (proptype) = GRAMMATICAL
			// Id 2 (proptype) = RELATIONSHIP

			if(isGrammatical)
      	query = "SELECT property_id " + "FROM property WHERE type_id='1' AND string = '" + propName.trim().toUpperCase() + "'";
			else
				query = "SELECT property_id " + "FROM property WHERE type_id='2' AND string = '" + propName.trim().toUpperCase() + "'";

      rs = stmt.executeQuery(query);

      rs.last();

      if(rs.getRow() > 0) // 1
        return rs.getInt("property_id");
      else
      {
				if(isGrammatical)
	        query = "INSERT INTO property (type_id,string) VALUES ('1','" + propName.trim().toUpperCase() + "')";
				else
					query = "INSERT INTO property (type_id,string) VALUES ('2','" + propName.trim().toUpperCase() + "')";
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
  /// ---- addWordsAndTags
  /// ---

  public void 
	addWordsAndTags(int docNumber, int collectionNumber, ArrayList<TaggedWord> props, boolean isFromTitle, boolean normalized)
  {

    Statement stmt;
		ResultSet rs;
    String query;

    int prop_ID, word_ID, times, ft=0, nd=0;

		if(isFromTitle) ft = 1;
		if(normalized) nd = 1;

    for(TaggedWord wordAndTag: props)
    {

      prop_ID = addProperty(true,wordAndTag.tag());
			word_ID = addWord(wordAndTag.word());
			
      try
      {
        stmt = this.createStatement();

				query = "SELECT times " + "FROM wordpropdoc WHERE word_id='" + word_ID + "' AND prop_id='" + prop_ID +
								"' AND doc_id='" + docNumber + "' AND col_id='" + collectionNumber + "' AND fromTitle='" + 
								ft + "' AND normalized='" + nd + "'";

	      rs = stmt.executeQuery(query);

	      rs.last();

	      if(rs.getRow() > 0) // 1
				{ 
	        times = rs.getInt("times"); times++;
					query = "UPDATE wordpropdoc SET times='" + times + "' WHERE word_id='" + word_ID + "' AND prop_id='" + prop_ID +
									"' AND doc_id='" + docNumber + "' AND col_id='" + collectionNumber + "' AND fromTitle='" + 
									ft + "' AND normalized='" + nd + "'";
				}
	      else
	      { 
//					System.out.println("insertando tupla: (" + word_ID + "," + prop_ID + "," + docNumber + "," + collectionNumber + "," + ft + ")");
					query = "INSERT INTO wordpropdoc (word_ID,prop_ID,doc_id,col_id,times,fromTitle,normalized) VALUES ('" +
									word_ID + "','" + prop_ID + "','" + docNumber + "','" + collectionNumber + "','1','" + ft + "','" + nd + "')";
  	    }

				rs.close();

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
