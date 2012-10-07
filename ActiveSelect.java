package activerecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import activerecord.interfaces.ActiveSelectInterface;


public class ActiveSelect implements ActiveSelectInterface 
{
		
	private ActiveTable fromTable;
	
	private ArrayList<String> wheres;
	
	private ArrayList<String> orders;
	
	private String	limit;
	

	public ActiveSelect(ActiveTable fromTable)
	{
		this.fromTable = fromTable;
		
		this.wheres 		= new ArrayList<String>();
		this.orders 		= new ArrayList<String>();
	}
	
	
	public ActiveSelect where(String newWhere) 
	{
		wheres.add(newWhere);
		return this;
	}


	public ActiveSelect order(String newOrder) 
	{
		orders.add(newOrder);
		return this;
	}

	public ActiveSelect limit(long limit) 
	{
		this.limit =  "" + limit;
		return this;
	}

	
	public ArrayList<ActiveRecord> load() 
	{
		return new ActiveSqlExecuter(fromTable).select(this);	
	}


	public boolean getDistinct() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getTableName() 
	{
		return fromTable.getTableName();
	}

	public String[] getCulomns() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getWhere() 
	{
		String SPLITER = " AND ";
		return wheres.isEmpty() ? null : join(wheres, SPLITER);	
	}


	public String getGroup() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getHaving() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getOrder() 
	{
		String SPLITER = ", ";
		return wheres.isEmpty() ? null : join(wheres, SPLITER);
	}


	public String getLimit() 
	{
		return limit;
	}


	
	public static String join(Collection s, String delimiter) 
	{
	    StringBuffer buffer = new StringBuffer();
	    Iterator iter = s.iterator();
	    while (iter.hasNext()) {
	        buffer.append(iter.next());
	        if (iter.hasNext()) {
	            buffer.append(delimiter);
	        }
	    }
	    return buffer.toString();
	}






	
	
	
	
}
