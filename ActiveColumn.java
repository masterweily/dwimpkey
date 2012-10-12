package activerecord;

import java.lang.reflect.Field;

import android.database.Cursor;
import android.util.Log;

class ActiveColumn 
{
	private String name;
	private String javaType;
	private SQLiteDataType sqlType;
	private Field field;
	
	public ActiveColumn(Field field) 
	{
		this.field = field;
		name = field.getName();
		javaType = field.getType().getSimpleName();
		sqlType = new SQLiteDataType( javaType );		
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return sqlType.toString();
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
			
		if ( javaType.equals("int") )
		{
			field.set( record, cursor.getInt(index) );
		}
		else if ( javaType.equals("long") )
		{
			field.set(record, cursor.getLong(index));	
		}
		else if ( javaType.equals("double") )
		{
			field.set(record, cursor.getDouble(index));	
		}
		else if ( javaType.equals("float") )
		{
			field.set(record, cursor.getFloat(index));	
		}
		else if ( javaType.equals("String") )
		{
			field.set(record, cursor.getString(index));	
		}		
	}

	
}
