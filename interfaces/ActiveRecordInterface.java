package activerecord.interfaces;

import android.content.ContentValues;

public interface ActiveRecordInterface
{
	public ContentValues getValues();
	
	// Basic CRUD
	
	public long getId();
	
	public void save();
	
	public void delete();
	
	// basic queries
	
	public ActiveRecordInterface find(long id);
	
	

}
