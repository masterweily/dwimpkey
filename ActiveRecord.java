package activerecord;

import android.content.ContentValues;
import android.content.Context;

public class ActiveRecord 
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
    	Table table = Schema.getInstance().getTable( context, this.getClass() );
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
    	Table table = Schema.getInstance().getTable( context, this.getClass() );
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
}
