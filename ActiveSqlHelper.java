package activerecord;

import java.util.Collection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ActiveSqlHelper extends SQLiteOpenHelper 
{
	
	private static ActiveSqlHelper instance;
	
	static ActiveSqlHelper getInstance( ActiveTable<?> table )
	{
		if ( instance == null )
		{
			instance = new ActiveSqlHelper(table);
		}
		return instance;
	}
	
	
	private ActiveSqlHelper(ActiveTable<?> table)
	{
		super(		table.getContext(),     						// context
					ActiveSchema.getInstance().getDatabaseName(),   // db name
					null, 											// factory
					ActiveSchema.getInstance().getVersion()			// version	
				);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{		
		for ( ActiveTable<?> table : getTables() )
		{		
			String sql = createTableSqlString(table);
			tryExecuteSql(db, sql);
		}
		//db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{		
		for ( ActiveTable<?> table : getTables() )
		{
			String sql = updateTableSqlString(table);
			tryExecuteSql(db, sql);
		}
		this.onCreate(db);
		//db.close();
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
		Log.d("xrx-sql", "sql exectue success: " + sql);		
	}
	
	private String createTableSqlString(ActiveTable<?> table) 
	{
		String sql = 	"CREATE TABLE " + table.getName() + 
				"(id INTEGER PRIMARY KEY";

		for ( ActiveColumn col : table.getCols() )
		{
			sql += ", " + col.getName() + " " + col.getType();
		}

		sql += ");";

		return sql;
	}
	
	private Collection<ActiveTable<?>> getTables() 
	{
		return ActiveSchema.getInstance().getTables();
	}

	private String updateTableSqlString(ActiveTable<?> table) 
	{
		String sql = "DROP TABLE IF EXISTS " + table.getName() + ";"; 
		return sql;
	}

}
