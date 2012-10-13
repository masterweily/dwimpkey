package activerecord;

import com.exr.xrxindusries.model.*;

public class ActiveConfig 
{
	/*
	 * Database Name
	 * 
	 * should be manually incremented any time you apply changes in the models
	 * that should affect the database schema
	 * 
	 * Recommended format:  "*.db"
	 * 
	 */
	public static final String DATABASE_NAME = "dwimpkey.db"; 
	
	/*
	 * Database Version
	 * 
	 * should be manually incremented after any set of changes in 
	 * the model classes decelerations that should effect the database schema
	 * 
	 * must be > 0
	 * 
	 */
	public static final int VERSION = 6;  

	
	/*
	 * list all of your models that will behave as ActiveRecord
	 * 
	 * all of them must extend ActiveRecord as well
	 */	
	public static Class<?>[] getActiveModels()
	{
		return new Class[] { 	Product.class, 
								Book.class		};
	}
}
