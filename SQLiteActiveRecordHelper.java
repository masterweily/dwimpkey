package activerecord;

import java.lang.annotation.Annotation;

import activerecord.annotations.Model;

import android.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteActiveRecordHelper extends SQLiteOpenHelper 
{
	private Class model;
	
	public SQLiteActiveRecordHelper(Context context, Class model) 
	{	
		super(	context, 
				getDbName(),  
				null, // factory 
				version(model) 
				);		
		this.model = model;
	}

	private static int version(Class model) 
	{
		Annotation[] annotations = model.getAnnotations();
		for(Annotation annotation : annotations)
		{
			if ( annotation instanceof Model )
			{
				int version = ((Model) annotation).version();
				if ( version > 0 )
				{
					return version;
				}
			}
		}
		return 1;
	}

	private static String getDbName() 
	{
		return "AppName".toLowerCase(); // TODO
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		Table table = new Table(model);
		table.create(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Table table = new Table(model);
		table.upgrade(db);
	}
	

}
