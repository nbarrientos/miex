/* file name  : miex/sql/SQLHandler.java
 * authors    : Nacho Barrientos Arias <chipi@criptonita.com>
 * created    : 
 * copyright  : GPL
 *
 * modifications:
 *
 */
package es.uniovi.aic.miex.sql;

import java.sql.*;
import java.io.*;

import es.uniovi.aic.miex.config.ConfigFile;
import es.uniovi.aic.miex.datastr.MyCategories;

import java.util.ArrayList;

import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.trees.TypedDependency;

import es.uniovi.aic.miex.datastr.ExtendedTaggedWord;

import es.uniovi.aic.miex.tools.MD5;

/** 
 * Manages all SQL related stuff 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class SQLHandler
{

	/** 
	 * Creates a new handler and establishes the connection using a
	 * config file
	 * 
	 * @param cf A configfile storing database settings 
	 */
	public SQLHandler(ConfigFile cf)
	{
		this(cf.getStringSetting("DBHostname"), cf.getStringSetting("DBPort"),
					cf.getStringSetting("DBUser"), cf.getStringSetting("DBPassword"),
					cf.getStringSetting("DBName"));
	}

	/** 
	 * Creates a new handler and establishes the connection 
	 * 
	 * @param DBHostname FQDN for the database server
	 * @param DBPort Port to connect on
	 * @param DBUser User to log in with
	 * @param DBPassword This well-known secret string ;)
	 * @param DBName Database name
	 */
	public SQLHandler(String DBHostname, String DBPort, String DBUser, String DBPassword, String DBName)
	{
		String url = "jdbc:mysql://" + DBHostname + ":" + DBPort + "/" + DBName;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			theConn = DriverManager.getConnection(url,DBUser, DBPassword);			
		}
		catch (Exception e)
		{
      System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	/** 
	 * Injects a SQL script into the database (useful to fill
	 * the database with blank tables) 
   *
	 * @param path The path to the script.
	 */
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
      System.err.println(e.getMessage());
      System.exit(-1);
		}

	}

	/** 
	 * Queries the database for an existing collection
	 * 
	 * @param fileName The name of the file containing the collection
	 * @return -1 if it already exists, a new ID otherwise.
	 */
	public int addCollection(String fileName)
	{
		ResultSet rs;
		Statement stmt;
		String query;
		int out = -1;
	
		try
		{	
			stmt = this.createStatement();

			query = "SELECT name FROM collection WHERE name = '" + fileName + "'";

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
      System.err.println(e.getMessage());
      System.exit(-1);
		}
		
		return out;
	
	}

	/** 
	 * Adds a document into the database. 
	 * 
	 * @param docNumber The document ID (0...n)
	 * @param collectionNumber The collection ID (0....n) (UNIQUE)
	 * @param title The document's title
	 * @param isTrain True if it is DOCTRAIN, false otherwise
	 */
	public void addDocument(int docNumber, int collectionNumber, String title, boolean isTrain)
	{

    Statement stmt;
    String query;

    // Id 1 (doctype) = DOCTRAIN
    // Id 2 (doctype) = DOCTEST

		int docTypeID = 0;

		if(isTrain) docTypeID = 1;

		title = safeSQL(title);

    try
    {
      stmt = this.createStatement();

      query = "INSERT INTO document (document_id,collection_id,title,doctype_id) VALUES ('" +
							 docNumber + "','" + collectionNumber + "','" + title + "','" + docTypeID + "')"; 
																
      stmt.executeUpdate(query);
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
      System.exit(-1);
		}

	}

	/** 
	 * Adds a category to the DB (if needed)
	 * 
	 * @param catName Category's name 
	 * @return The (new) ID for this category
	 */
	private int addCategory(String catName)
	{
		Statement stmt;
		ResultSet rs;
		String query;

		int out = 0;

		catName = safeSQL(catName);

    try
    {
      stmt = this.createStatement();

      query = "SELECT category_id FROM category WHERE string = '" + catName.trim().toLowerCase() + "'";

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
      System.err.println(e.getMessage());
      System.exit(-1);
    }

		return out;

	}

  /** 
   * Adds a bunch of categories in a row and register that into document table 
   * 
   * @param docNumber as usual 
   * @param collectionNumber as usual
   * @param categories a MyCategories object containing the categories for this document
   */
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
				System.err.println(e.getMessage());
				System.exit(-1);
    	}
  	}
	}

  /** 
   * Adds a word to the DB (if needed)  
   * 
   * @param wordName the word itself
   * @return The (new) ID for this word.
   */
  private int addWord(String wordName)
  {
    Statement stmt;
    ResultSet rs;
    String query;

    int out = 0;

		wordName = safeSQL(wordName);

    try
    {
      stmt = this.createStatement();

      query = "SELECT word_id FROM word WHERE string = '" + wordName.trim().toLowerCase() + "'";

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
      System.err.println(e.getMessage());
      System.exit(-1);
    }

    return out;
  }

  /** 
   * Adds a property to the DB (if needed)
   * 
   * @param type An integer representing the sort of the property [0-3]
   * @param propName The property itself
   * @return The (new) ID for this property 
   */
  private int addProperty(int type, String propName)
  {
    Statement stmt;
    ResultSet rs;
    String query;

    int out = 0;

    try
    {
      stmt = this.createStatement();

			// Id 0 (proptype) = CLAUSE
			// Id 1 (proptype) = PHRASE
			// Id 2 (proptype) = WORD
			// Id 3 (proptype) = RELATIONSHIP

     	query = "SELECT property_id FROM property WHERE type_id='" + type + "' AND string = '" + 
							propName.trim().toUpperCase() + "'";

      rs = stmt.executeQuery(query);

      rs.last();

      if(rs.getRow() > 0) // 1
        return rs.getInt("property_id");
      else
      {
				query = "INSERT INTO property (type_id,string) VALUES ('" + type + "','" + propName.trim().toUpperCase() + "')";
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
      System.err.println(e.getMessage());
      System.exit(-1);
    }

    return out;
  }

  /** 
   * Adds a whole list of property to the DB (if needed) 
   * 
   * @param _list The list itself 
   * @return The (new) ID for this propertylist
   */
  private int addPropertyList(ArrayList<String> _list)
  {
    Statement stmt;
    ResultSet rs;
    String query;

    int out = 0;

		String plainCode = "";

		for(String foo: _list)
		{
			plainCode = plainCode + foo;
		}		

    try
    {
			String hashCode = MD5.gen(plainCode);

      stmt = this.createStatement();

     	query = "SELECT propertylist_id FROM propertylist WHERE hashCode='" + hashCode + "'";

      rs = stmt.executeQuery(query);

      rs.last();

      if(rs.getRow() > 0) // 1
        return rs.getInt("propertylist_id");
      else
      {
				query = "INSERT INTO propertylist (hashCode) VALUES ('" + hashCode + "')";
        stmt.executeUpdate(query);
      }

      rs.close();

      rs = stmt.getGeneratedKeys();

      if (rs.next())
      {
        out = rs.getInt(1);
      }

			// This list do not exist, so we need to insert it into the relationship table propproplist
			for(String foo: _list)
			{
				int prop_id = addProperty(1,foo);
															  //^	Id 1 (proptype) = PHRASE
	
				query = "INSERT INTO propproplist (list_id,prop_id) VALUES ('" + out + "','" + prop_id + "')";

				stmt.executeUpdate(query);
			}

      rs.close();

    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
      System.exit(-1);
    }

    return out;
  }



  /** 
   * Adds grammatical information for a single sentence to the database 
   * 
   * @param docNumber As usual
   * @param collectionNumber As usual
   * @param props A list of word-tag-extendedlist 
   * @param isFromTitle True if this sentence comes from document title, false if it comes from the body
   * @param normalized True if the list has been normalized, false otherwise
   */
  public void	
	addWordsAndTags(int docNumber, int collectionNumber, ArrayList<ExtendedTaggedWord> props, 
									boolean isFromTitle, boolean normalized)
  {

    Statement stmt;
		ResultSet rs;
    String query;

    int prop_ID, word_ID, list_ID, times, ft=0, nd=0;

		if(isFromTitle) ft = 1;
		if(normalized) nd = 1;

    for(ExtendedTaggedWord wordAndTag: props)
    {

      prop_ID = addProperty(2,wordAndTag.tag());
													//^ (word)
			word_ID = addWord(wordAndTag.word());
			
			list_ID = addPropertyList(wordAndTag.phrase());

      try
      {
        stmt = this.createStatement();

				query = "INSERT INTO wordpropdoc (word_ID,prop_ID,doc_id,col_id,list_id,fromTitle,normalized) VALUES ('" +
									word_ID + "','" + prop_ID + "','" + docNumber + "','" + collectionNumber + "','" + list_ID + 
									"','" + ft + "','" + nd + "')";

				stmt.executeUpdate(query);
      }
      catch (Exception e)
      {
     	 System.err.println(e.getMessage());
     	 System.exit(-1);
      }
    }
  }

  /** 
   * Adds information about the relationships among words of a single sentece to the database 
   * 
   * @param docNumber As usual 
   * @param collectionNumber As usual
   * @param deps A list of 3-uples with the desired information
   * @param isFromTitle True if this sentence comes from document title, false if it comes from the body
   * @param normalized True if the list has been normalized with Porter, false otherwise 
   */
  public void 
	addDependencies(int docNumber, int collectionNumber, ArrayList<TypedDependency> deps, 
									boolean isFromTitle, boolean normalized)
  {

    Statement stmt;
    ResultSet rs;
    String query;

    int prop_ID, master_ID, slave_ID, times, ft=0, nd=0;

    if(isFromTitle) ft = 1;
		if(normalized) nd = 1;

    for(TypedDependency dep: deps)
    {

			MapLabel masterLabel = (MapLabel)dep.gov().label();
			MapLabel slaveLabel = (MapLabel)dep.dep().label();
			
			String masterWord = masterLabel.toString("value");
			String slaveWord = slaveLabel.toString("value");
			String prop = dep.reln().toString();
			
      prop_ID = addProperty(3,prop);
	 											 // ^ (relationship)

      master_ID = addWord(masterWord);
			slave_ID = addWord(slaveWord);

      try
      {
        stmt = this.createStatement();

        query = "SELECT times FROM wordwordpropdoc WHERE masterWord_id='" + master_ID + "' AND slaveWord_id='" + 
								slave_ID + "' AND prop_id='" + prop_ID + "' AND doc_id='" + docNumber + "' AND col_id='" + 
								collectionNumber + "' AND fromTitle='" + ft + "' AND normalized='" + nd + "'";

        rs = stmt.executeQuery(query);

        rs.last();

        if(rs.getRow() > 0) // 1
        {
          times = rs.getInt("times"); times++;
          query = "UPDATE wordwordpropdoc SET times='" + times + "' WHERE masterWord_id='" + master_ID + 
									"' AND slaveWord_id='" + slave_ID + "' AND prop_id='" + prop_ID + "' AND doc_id='" + docNumber + 
									"' AND col_id='" + collectionNumber + "' AND fromTitle='" + ft + "' AND normalized='" + nd + "'";
        }
        else
        {
          query = "INSERT INTO wordwordpropdoc " +
									"(masterWord_ID,slaveWord_id,prop_ID,doc_id,col_id,times,fromTitle,normalized) VALUES ('" +
        					  master_ID + "','" + slave_ID + "','"  + prop_ID + "','" + docNumber + "','" + 
										collectionNumber + "','1','" + ft + "','" + nd + "')";
        }
        rs.close();

        stmt.executeUpdate(query);
      }
      catch (Exception e)
      {
	      System.err.println(e.getMessage());
				System.exit(-1);
      }
    }
  }


	private Statement createStatement() throws SQLException
	{
		return theConn.createStatement();
	}

	private String safeSQL(String orig)
	{
		// Escaping dangerous "'" which could break the SQL statement.
		if(orig.matches(".*'.*")) 
			return orig.replace("'","\\'");
		else
			return orig;
	}

	// Members
	
	private Connection theConn;
}	
