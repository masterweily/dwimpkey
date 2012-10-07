package activerecord.interfaces;

import java.util.List;

public interface ActiveSqlExecuterInterface < R extends ActiveRecordInterface, 
												S extends ActiveSelectInterface,
												T extends ActiveTableInterface 		>
{
	List<R> 	select		( S select );
	
	R			find		( T table, long id );
	
	long		addRow		( R record );
	
	void 		updateRow   ( R record );
	
	boolean		deleteRow	( R record );
	
}
