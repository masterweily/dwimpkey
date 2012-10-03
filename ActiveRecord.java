package activerecord;

import android.content.ContentValues;
import android.content.Context;

public class ActiveRecord 
{
    
    private long id = 0;
    private static Context context = null;
    
    public static void setContext(Context newContext)
    {
    	context = newContext;
    }
    
    public void save()
    {
    	Schema s = Schema.getInstance();
    	
    	Table table = s.getTable(context, this.getClass() );
    	if ( this.hasValidId() )
    	{
    		table.updateRow(this);
    	}
    	else
    	{
    		this.id = table.newRow(this);
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
