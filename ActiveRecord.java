package activerecord;

import activerecord.interfaces.ActiveRecordInterface;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ActiveRecord implements ActiveRecordInterface
{
    private static final long NULL_ID = 0;
	
    private long id = NULL_ID;
    private static Context context = null;
    
    public ActiveRecord()
    {
    	super();
    }
    
	private long findId(Cursor cursor) 
	{
		int index = cursor.getColumnIndex("id");
		return cursor.getInt(index);
	}

	public static void setContext(Context newContext)
    {
    	context = newContext; 		
    }
    

	public void save()
    {
    	ActiveTable<?> table = getTable(); 
    	ActiveSqlExecuter executer = new ActiveSqlExecuter(table);
    	
    	if ( this.hasValidId() ) // case old record 
    	{
    		executer.updateRow(this);  // update the record
    	}
    	else   //  case new record
    	{
    		this.id = executer.addRow(this);  //  add new record to db and get the id
    	}
    }
    
    public void delete()
    {
    	ActiveTable<?> table = getTable();
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
	
	private ActiveTable<?> getTable()
	{
		return ActiveSchema.getInstance().getTable( context, this.getClass() );
	}
	
	private ActiveColumn[] getCols()
	{
		return getTable().getCols();
	}

	public ContentValues getValues() 
	{
		ContentValues values = new ContentValues();
    	
    	for ( ActiveColumn col : getCols() )
    	{
    		values.put(	col.getName(),            	// key
    					col.getStringValue(this)	// value
    					);
    	}
		return values;
	}

	public static Context getContext() {
		return context;
	}
	
	public void parseCursor(Cursor cursor)
	{
		Log.d( "cols", ""+getCols().length );
		for ( ActiveColumn col : getCols() )
    	{
			
    		try 
    		{
				col.trySetValueFromCursor( this, cursor );
			} 
    		catch (Exception e) 
    		{
				e.printStackTrace();
			} 
    	}
    	id = findId(cursor);
	}
	
	public String toString()
	{
		String str = "id: " + this.id;
		
		for ( ActiveColumn col : getCols() )
    	{
    		str +=	", " 	+ 	col.getName() + ": "
    						+	col.getStringValue(this);	
    	}
		return str;		
	}

	public ActiveRecordInterface find(long id) 
	{
		ActiveTable<?> table = getTable(); 
    	ActiveSqlExecuter executer = new ActiveSqlExecuter(table);
    	executer.find(this, id);
    	return this;
	}
}
