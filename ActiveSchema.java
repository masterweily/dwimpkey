package activerecord;

import java.util.Collection;
import java.util.HashMap;

import activerecord.interfaces.ActiveSchemaInterface;
import android.content.Context;
import android.util.Log;

public class ActiveSchema implements ActiveSchemaInterface<ActiveTable<?>> 
{

	// single instance
	static private ActiveSchema instance = null;
		
	private HashMap<Class<?>,ActiveTable<?>> tables;
		
	// private constructor
	private ActiveSchema()
	{
		this.tables = new HashMap<Class<?>,ActiveTable<?>>();
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
	public ActiveTable<?> getTable(Context context, Class<?> modelClass) 
	{
		ActiveTable<?> table = confirmTableInstance(context, modelClass);				
		return table;
	}

	private ActiveTable<?> confirmTableInstance(Context context, Class<?> modelClass) 
	{
		ActiveTable<?> table = tables.get(modelClass); // search for table for the class		
		if ( table == null )  // if not found => create one
		{
			table = new ActiveTable<ActiveRecord>(context, modelClass);
			tables.put(modelClass, table);
		}
		if (!context.equals(table.getContext()))
		{
			table.setContext(context);
		}
		return table;
	}

	public Collection<ActiveTable<?>> getTables() 
	{
	    buildSchema();
	    Collection<ActiveTable<?>> values = tables.values();
	    
	    return values;	    
	}

	private void buildSchema()
	{
		tables.clear();
		Context context = ActiveRecord.getContext();
		
		for ( Class<?> modelClass : modelClassesList() )
		{
			tables.put(modelClass, new ActiveTable<ActiveRecord>(context, modelClass));
			Log.d("Schema" , "Class name: " + modelClass.getName());
		}			
	}

	private Class<?>[] modelClassesList() 
	{
		return ActiveConfig.getActiveModels();		
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
