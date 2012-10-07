package activerecord;

import java.util.ArrayList;

import activerecord.interfaces.ActiveSqlExecuterInterface;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;


public class ActiveSqlExecuter extends SQLiteOpenHelper implements ActiveSqlExecuterInterface<ActiveRecord,ActiveSelect,ActiveTable> 
{

	private ActiveTable table;

	public ActiveSqlExecuter(ActiveTable table) 
	{
		super(		table.getContext(),     						// context
					ActiveSchema.getInstance().getDatabaseName(),   // db name
					null, 											// factory
					ActiveSchema.getInstance().getVersion()			// version	
					);
		this.table = table;
	}

	public ArrayList<ActiveRecord> select(ActiveSelect select) 
	{
		String sql = SQLiteQueryBuilder.buildQueryString( 
				select.getDistinct(),  		    // distinct 
				select.getTableName(), 			// table name
				select.getCulomns(), 			// columns
				select.getWhere(), 				// where
				select.getGroup(), 			    // group by
				select.getHaving(), 			// having
				select.getOrder(),				// orderBy 
				select.getLimit()				// limit
				);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<ActiveRecord> records = new ArrayList<ActiveRecord>();
		
		while ( cursor.moveToNext() )
		{
			records.add( table.parseCursor(cursor) );
		}
		
		cursor.close();
		db.close();
		
		return records;
	}

	public ActiveRecord find(ActiveTable table, long id) 
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
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(sql, null);
		
		ActiveRecord record = null;
		
		if ( cursor.moveToNext() )
		{
			record =  table.parseCursor(cursor);
		}
		cursor.close();
		db.close();
		
		return record;
	}

	public long addRow(ActiveRecord record)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = record.getValues();
		long id = -1;
		try
		{
			id = db.insertOrThrow( table.getTableName(), null, values);
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

	public void updateRow(ActiveRecord row)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = row.getValues();
		long id = row.getId();
		try
		{
			db.update(table.getTableName(), values, "id=" + id, null);
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

	public boolean deleteRow(ActiveRecord row) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		long id = row.getId();
		boolean success;
		try
		{
			db.delete(table.getTableName(), "id=" + id, null);
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

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		ActiveTable[] tables = ActiveSchema.getInstance().getTables();
		
		for ( ActiveTable table : tables )
		{
			String sql = table.createTableSqlString();
			tryExecuteSql(db, sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		ActiveTable[] tables = ActiveSchema.getInstance().getTables();
		
		for ( ActiveTable table : tables )
		{
			String sql = table.updateTableSqlString();
			tryExecuteSql(db, sql);
		}
		this.onCreate(db);
	}

	
	private void tryExecuteSql(SQLiteDatabase db, String sql) 
	{
		try
		{
			db.execSQL(sql);
		}
		catch (SQLiteException ex)
		{
			Log.e("xrx-sql", ex.getMessage());
		}
		finally
		{
			db.close();
		}
		Log.d("xrx-sql", "sql exectue success");		
	}
}
