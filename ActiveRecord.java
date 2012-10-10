package activerecord;

import activerecord.interfaces.ActiveRecordInterface;
import android.content.ContentValues;
import android.content.Context;

public class ActiveRecord implements ActiveRecordInterface
{
    private static final long NULL_ID = 0;
	
    private long id = NULL_ID;
    private static Context context = null;
    
    public static void setContext(Context newContext)
    {
    	context = newContext; 		
    }
    
   
	public static ActiveRecord find(long id)
    {
		ActiveList<ActiveRecord> list = where("id=" + id);
		ActiveRecord found = null;
    	if ( list.size() > 0 )
    	{
    		found = list.get(0);
    	}
    	return found;  	
    }
    
    public static ActiveList<ActiveRecord> where(String statement)
    {
    	return selfInstance().newList().where(statement);   	
    }
    
    public static ActiveList<ActiveRecord> order(String statement)
    {
    	return selfInstance().newList().order(statement);
    }
    
    public static ActiveList<ActiveRecord> limit(long limit)
    {
    	return selfInstance().newList().limit(limit);   	
    }
    
    
    private ActiveList<ActiveRecord> newList() 
    {
    	return new ActiveList<ActiveRecord>
    	( 
    			ActiveSchema.getInstance()
    					.getTable(context, this.getClass())
    	);
	}

	
	private static ActiveRecord selfInstance() 
	{
		
		try {
			return (ActiveRecord) selfClass().getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 		
	}
	
	private static Class<?> selfClass()
	{
		return new Object() {}.getClass().getEnclosingClass();
	}

	public void save()
    {
    	ActiveTable table = ActiveSchema.getInstance().getTable( context, this.getClass() );  
    	
    	if ( this.hasValidId() ) // case old record 
    	{
    		new ActiveSqlExecuter(table).updateRow(this);  // update the record
    	}
    	else   //  case new record
    	{
    		this.id = new ActiveSqlExecuter(table).addRow(this);  //  add new record to db and get the id
    	}
    }
    
    public void delete()
    {
    	ActiveTable table = ActiveSchema.getInstance().getTable( context, this.getClass() );
    	if ( this.hasValidId() )
    	{
    		if ( new ActiveSqlExecuter(table).deleteRow(this) )
    		{
    			this.id = NULL_ID;
    		}
    	}
    }

	private boolean hasValidId() 
	{
		return this.id > 0;
	}

	public long getId()
	{	
		return id;
	}

	public ContentValues getValues() 
	{
		ContentValues values = new ContentValues();
    	ActiveTable table = ActiveSchema.getInstance().getTable( context, this.getClass() );
    	ActiveColumn[] cols = table.getCols();
    	
    	for ( ActiveColumn col : cols )
    	{
    		values.put(	col.getName(),            	// key
    					col.getStringValue(this)	// value
    					);
    	}
		return values;
	}
}
