package activerecord.interfaces;

import java.util.List;

public interface ActiveSqlExecuterInterface< R extends ActiveRecordInterface, 
											   S extends ActiveSelectInterface<R>	>
{
	List<R> 				select		( S select );
	
	R						find		( long id );
	
	long					addRow		( R record );
	
	void 					updateRow   ( R record );
	
	boolean					deleteRow	( R record );
	
}
