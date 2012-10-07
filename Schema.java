package activerecord;

import java.util.HashMap;

import android.content.Context;

/*
 * singleton
 */
public final class Schema 
{
	// single instance
	static private Schema instance = null;
	
	private HashMap<Class,ActiveTable> tables;
	
	// private constructor
	private Schema()
	{
		this.tables = new HashMap<Class,ActiveTable>();
	}
	
	
	/*
	 * search for the Table representation of the modelClass in the schema,
	 * if not found, one will be created.
	 * 
	 * @param modelClass
	 */
	public ActiveTable getTable(Context context, Class modelClass) 
	{
		ActiveTable table = tables.get(modelClass); // search for table for the class
		
		if ( table == null || !context.equals(table.getContext()) )  // if not found => create one
		{
			table = new ActiveTable(context, modelClass);
			tables.put(modelClass, table);
		}		
		return table;
	}

	/* 
	 * get the single instance of the class
	 */
	public static Schema getInstance() 
	{
		if ( instance == null )
		{
			instance = new Schema();
		}
		return instance;
	}	
}
