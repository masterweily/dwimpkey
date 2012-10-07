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
    
    public static void setContext(Context newContext)
    {
    	context = newContext;
    }
    
    public void save()
    {
    	ActiveTable table = Schema.getInstance().getTable( context, this.getClass() );
    	if ( this.hasValidId() ) // case old record 
    	{
    		table.updateRow(this);  // update the record
    	}
    	else   //  case new record
    	{
    		this.id = table.newRow(this);  //  add new record to db and get the id
    	}
    }
    
    public void delete()
    {
    	ActiveTable table = Schema.getInstance().getTable( context, this.getClass() );
    	if ( this.hasValidId() )
    	{
    		if ( table.deleteRow(this) )
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

	public ContentValues getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
