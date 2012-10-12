package activerecord;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import activerecord.annotations.ActiveField;
import activerecord.interfaces.ActiveTableInterface;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


/*
 * represent a database schema of a single table, suitable to a ActiveRecord class
 */
class ActiveTable<R extends ActiveRecord> implements ActiveTableInterface<R>
{
	private String name;
	private ActiveColumn[] cols;
	private Context context;
	private Class<?> modelClass;
	
	/*
	 * constructor
	 * 
	 * @param modelClass Class, better be a subclass of ActiveRecord
	 * 
	 * generate database Table map (schema) for the modelClass
	 *   
	 */
	public ActiveTable(Context context, Class<?> modelClass) 
	{	
		this.name = nameFromClass(modelClass);
		this.context = context;
		this.modelClass = modelClass;
		initCols(modelClass);
	}

	private static String nameFromClass(Class<?> modelClass) 
	{
		String className = modelClass.getSimpleName();
		return ActiveGrammar.toTableName(className);
	}

	private void initCols(Class<?> modelClass) 
	{
		Field[] fields = getDatabaseFields(modelClass);
		
		this.cols = new ActiveColumn[fields.length];
		
		for ( int i = 0 ; i < fields.length ; i ++ )
		{
			this.cols[i] = new ActiveColumn(fields[i]);
		}
	}

	private Field[] getDatabaseFields(Class<?> modelClass) 
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
			if ( annotation instanceof ActiveField )
			{
				return true;
			}
		}
		return false;
	}
	

	public Context getContext() 
	{	
		return context;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public String getName() 
	{
		return name;
	}


	public R parseCursor(Cursor cursor) 
	{
		R newModel = null;
		try 
		{
			newModel = (R) modelClass.getConstructor(Cursor.class).newInstance(cursor);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return newModel;
	}

	public ActiveColumn[] getCols() {
		return cols;
	}

	public void setContext(Context context) 
	{
		this.context = context;
		
	}

	
	

}
