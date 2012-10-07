package activerecord.interfaces;

import java.util.List;

public interface ActiveSchemaInterface< T extends ActiveTableInterface >
{
	// public static ActiveSchemaInterface getInstance();
	
	public T[] getTables();
	
	public String getDatabaseName();
	
	public int getVersion();
}
