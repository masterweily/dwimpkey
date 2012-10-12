package activerecord.interfaces;

import java.util.Collection;


public interface ActiveSchemaInterface< T extends ActiveTableInterface<?> >
{
	// public static ActiveSchemaInterface getInstance();
	
	public Collection<T> getTables();
	
	public String getDatabaseName();
	
	public int getVersion();
}
