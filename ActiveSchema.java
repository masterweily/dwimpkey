package activerecord;

import java.util.HashMap;

import activerecord.interfaces.ActiveSchemaInterface;
import android.content.Context;

public class ActiveSchema implements ActiveSchemaInterface<ActiveTable> 
{

	// single instance
	static private ActiveSchema instance = null;
		
	private HashMap<Class,ActiveTable> tables;
		
	// private constructor
	private ActiveSchema()
	{
		this.tables = new HashMap<Class,ActiveTable>();
	}
	
	public static ActiveSchema getInstance() 
	{
		if ( instance == null )
		{
			instance = new ActiveSchema();
		}
		return instance;
	}
	
	/*
	 * search for the Table representation of the modelClass in the schema,
	 * if not found, one will be created.
	 * 
	 * @param modelClass
	 */
	public ActiveTable getTable(Context context, Class modelClass) 
	{
		ActiveTable table = confirmTableInstance(context, modelClass);				
		return table;
	}

	private ActiveTable confirmTableInstance(Context context, Class modelClass) 
	{
		ActiveTable table = tables.get(modelClass); // search for table for the class		
		if ( table == null || !context.equals(table.getContext()) )  // if not found => create one
		{
			table = new ActiveTable(context, modelClass);
			tables.put(modelClass, table);
		}		
		return table;
	}

	public ActiveTable[] getTables() 
	{
	    buildSchema();
	    return (ActiveTable[]) tables.values().toArray();	    
	}

	private void buildSchema()
	{
		// TODO Auto-generated method stub
		
	}

	public String getDatabaseName() 
	{
		return ActiveConfig.DATABASE_NAME;
	}

	public int getVersion() 
	{
		return ActiveConfig.VERSION;
	}

}
