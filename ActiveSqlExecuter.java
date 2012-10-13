package activerecord;


import java.util.ArrayList;
import java.util.Collection;

import activerecord.interfaces.ActiveRecordInterface;
import activerecord.interfaces.ActiveSqlExecuterInterface;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;


public class ActiveSqlExecuter<R extends ActiveRecord, S extends ActiveSelect<R>> 
			implements ActiveSqlExecuterInterface<R,S> 
{

	private ActiveTable<R> table;
	

	public ActiveSqlExecuter(ActiveTable<R> fromTable) 
	{
		table = fromTable;
	}

	public ArrayList<R> select(S activeSelect) 
	{
		String sql = SQLiteQueryBuilder.buildQueryString( 
				activeSelect.getDistinct(),  		    // distinct 
				activeSelect.getTableName(), 			// table name
				activeSelect.getCulomns(), 			// columns
				activeSelect.getWhere(), 				// where
				activeSelect.getGroup(), 			    // group by
				activeSelect.getHaving(), 			// having
				activeSelect.getOrder(),				// orderBy 
				activeSelect.getLimit()				// limit
				);
		Log.d("SELECT sql", sql);
		
		SQLiteDatabase db = ActiveSqlHelper.getInstance(table).getWritableDatabase();
		
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<R> records = new ArrayList<R>();
		
		while ( cursor.moveToNext() )
		{
			records.add( table.parseCursor(cursor) );
		}
		
		cursor.close();
		db.close();
		
		return records;
	}

	public R find(long id) 
	{
		String sql = SQLiteQueryBuilder.buildQueryString( 
						false,  		// distinct 
						null, 			// table name
						null, 			// columns
						"id=" + id,    	// where
						null, 			// group by
						null, 			// having
						null,			// orderBy 
						"1"			    // limit
						);
		
		SQLiteDatabase db = ActiveSqlHelper.getInstance(table).getWritableDatabase();
		
		Cursor cursor = db.rawQuery(sql, null);
		
		R record = null;
		
		if ( cursor.moveToNext() )
		{
			record =  (R) table.parseCursor(cursor);
		}
		cursor.close();
		db.close();
		
		return record;
	}

	public long addRow(ActiveRecord activeRecord)
	{
		SQLiteDatabase db = ActiveSqlHelper.getInstance(table).getWritableDatabase();
		ContentValues values = activeRecord.getValues();
		long id = -1;
		try
		{
			id = db.insertOrThrow( table.getName(), null, values);
		}
		catch (SQLiteException ex)
		{
			Log.e("xrx-sql", ex.getMessage() + "--- in SQLiteActiveRecordHelper.newRow()");
		}
		finally
		{
			db.close();
		}
		return id;
	}

	public void updateRow(ActiveRecord activeRecord)
	{
		SQLiteDatabase db = ActiveSqlHelper.getInstance(table).getWritableDatabase();
		ContentValues values = activeRecord.getValues();
		long id = activeRecord.getId();
		try
		{
			db.update(table.getName(), values, "id=" + id, null);
		}
		catch (SQLiteException ex)
		{
			Log.e("xrx-sql", ex.getMessage());
			throw ex;
		}
		finally
		{
			db.close();
		}
	}

	public boolean deleteRow(ActiveRecord activeRecord) 
	{
		SQLiteDatabase db = ActiveSqlHelper.getInstance(table).getWritableDatabase();
		long id = activeRecord.getId();
		boolean success;
		try
		{
			db.delete(table.getName(), "id=" + id, null);
			success = true;
		}
		catch (SQLiteException ex)
		{
			Log.e("xrx-sql", ex.getMessage());
			success = false;
			throw ex;
		}
		finally
		{
			db.close();
		}	
		return success;
	}

	
}
