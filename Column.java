package activerecord;

import java.lang.reflect.Field;

import android.util.Log;

class Column 
{
	private String name;
	private SQLiteDataType type;
	
	public Column(Field field) 
	{
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
}
