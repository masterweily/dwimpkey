package activerecord;

class SQLiteDataType 
{
	private String name;	
	
	public SQLiteDataType(String javaType) 
	{
		if ( javaType.equals("double") || javaType.equals("float") )
		{
			name = "REAL";
		}
		else if ( javaType.equals("String") )
		{
			name = "TEXT";
		}
		else
		{
			name = "INTEGER";
		}
	}

	public String toString()
	{
		return name;
	}
}
