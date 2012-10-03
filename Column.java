package activerecord;

import java.lang.reflect.Field;

import android.util.Log;

class Column 
{
	private String name;
	private SQLiteDataType type;
	private Field field;
	
	public Column(Field field) 
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
}
