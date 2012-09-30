package activerecord;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import activerecord.annotations.Database;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

class Table 
{
	private String name;
	private Column[] cols;
	
	public Table(Class modelClass) 
	{
		name = modelClass.getSimpleName().toLowerCase() + "s"; // pluralize
		initCols(modelClass);
	}

	private void initCols(Class modelClass) 
	{
		Field[] fields = getDatabaseFields(modelClass);
		
		this.cols = new Column[fields.length];
		
		for ( int i = 0 ; i < fields.length ; i ++ )
		{
			this.cols[i] = new Column(fields[i]);
		}
	}

	private Field[] getDatabaseFields(Class modelClass) 
	{
		
		Field[] allFields = modelClass.getDeclaredFields();
		
		ArrayList<Field> databaseFields = new ArrayList<Field>();
		
		for ( Field field : allFields )
		{
			
			if ( isDatabaseField(field) )
			{
				databaseFields.add(field);
			}
		}		
		Field[] output = new Field[databaseFields.size()];
		for ( int i = 0 ; i < output.length ; i ++ )
		{
			output[i] = databaseFields.get(i);
		}
		return output;
	}
	
	private boolean isDatabaseField(Field field) 
	{
		Log.d("field", field.getName());
		
		Annotation [] annotations = field.getAnnotations();
		
		Log.d("annotations", ""+annotations.length);
		for(Annotation annotation : annotations)
		{
			if ( annotation instanceof Database )
			{
				return true;
			}
		}
		return false;
	}

	public void create(SQLiteDatabase db) 
	{
		 
		String sql = this.sqlCreateTableString();
		Log.d("create table string", sql);
		
		try
		{
			db.execSQL(sql);
		}
		catch (SQLiteException ex)
		{
			Log.e("create table exception", "ex.getMessage");
		}
		Log.d("create table", "done");
		
	}
	
	public void upgrade(SQLiteDatabase db) 
	{
//		String sql = this.sqlUpgradeTableString();
//		
//		Log.d("create sql", sql);
//		try
//		{
//			db.execSQL(sql);
//		}
//		catch (SQLiteException ex)
//		{
//			Log.e("upgrade table exception", "ex.getMessage");
//		}
		
	}

	private String sqlUpgradeTableString() {
		// TODO Auto-generated method stub
		return null;
	}

	private String sqlCreateTableString() 
	{
		String sql = 	"CREATE TABLE " + this.name + 
						"(id INTEGER PRIMARY KEY";
		
		for ( Column col : this.cols )
		{
			sql += ", " + col.getName() + " " + col.getType();
		}
		
		sql += ");";
					
		return sql;
	}

	

}
