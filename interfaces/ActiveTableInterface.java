package activerecord.interfaces;

import android.content.Context;
import android.database.Cursor;

public interface ActiveTableInterface<R extends ActiveRecordInterface> 
{
	
	public Class<?> getModelClass();
	
	public String getName();
	
	public R parseCursor(Cursor cursor);
	
	public Context getContext();
	
	public void setContext(Context context);
		
}

