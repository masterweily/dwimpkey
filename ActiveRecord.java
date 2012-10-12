package activerecord;

import activerecord.interfaces.ActiveRecordInterface;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ActiveRecord implements ActiveRecordInterface
{
    private static final long NULL_ID = 0;
	
    private long id = NULL_ID;
    private static Context context = null;
    
    public ActiveRecord()
    {
    	super();
    }
    
    public ActiveRecord(Cursor cursor)
    {
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
   
	private long findId(Cursor cursor) 
	{
		int index = cursor.getColumnIndex("id");
		return cursor.getInt(index);
	}

	public static void setContext(Context newContext)
    {
    	context = newContext; 		
    }
    
   
     
//    private ActiveList<ActiveRecord> newList() 
//    {
//    	return new ActiveList<ActiveRecord>
//    	( 
//    			ActiveSchema.getInstance()
//    					.getTable(context, this.getClass())
//    	);
//	}

	
//	private static ActiveRecord selfInstance() 
//	{
//		
//		try {
//			return (ActiveRecord) selfClass().getConstructor().newInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null; 		
//	}
//	
//	private static Class<?> selfClass()
//	{
//		return new Object() {}.getClass().getEnclosingClass();
//	}

	public void save()
    {
    	ActiveTable<?> table = ActiveSchema.getInstance().getTable( context, this.getClass() ); 
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
    	ActiveTable<?> table = ActiveSchema.getInstance().getTable( context, this.getClass() );
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
}
