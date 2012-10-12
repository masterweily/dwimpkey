package activerecord;

import java.lang.reflect.Field;

import android.database.Cursor;
import android.util.Log;

class ActiveColumn 
{
	private String name;
	private SQLiteDataType type;
	private Field field;
	
	public ActiveColumn(Field field) 
	{
		this.field = field;
		name = field.getName();
		type = new SQLiteDataType( field.getType().getSimpleName() );		
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return type.toString();
	}

	public String getStringValue(ActiveRecord row) 
	{
		String value = "";
		try 
		{
			
			value = "" + field.get(row);
		} 
		catch (IllegalArgumentException e) 
		{
			Log.e("xrx-e", "illigal argument");
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			Log.e("xrx-e", "illigal access");
			e.printStackTrace();
		}
		return value;
	}

	

	public void trySetValueFromCursor(ActiveRecord record, Cursor cursor) throws IllegalArgumentException, IllegalAccessException 
	{
		int index = cursor.getColumnIndex(name);
		
		String type = field.getType().getSimpleName();
			
		if ( type == "int" )
		{
			field.set( record, cursor.getInt(index) );
		}
		else if ( type == "long" )
		{
			field.set(record, cursor.getLong(index));	
		}
		else if ( type == "double" )
		{
			field.set(record, cursor.getDouble(index));	
		}
		else if ( type == "float" )
		{
			field.set(record, cursor.getFloat(index));	
		}
		else if ( type == "String" )
		{
			field.set(record, cursor.getString(index));	
		}		
	}

	
}
