package activerecord.interfaces;

import android.content.ContentValues;

public interface ActiveRecordInterface
{
	
	public long getId();
	
	public void save();
	
	public void delete();
	
	public ContentValues getValues();

}
