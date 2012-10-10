package activerecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import activerecord.ActiveRecord;
import activerecord.interfaces.ActiveListInterface;
import activerecord.interfaces.ActiveSelectInterface;


class ActiveList<E extends ActiveRecord> implements ActiveListInterface<ActiveRecord>  
{
	
	private ArrayList<ActiveRecord> list = null;
	private ActiveSelect select = null;
	
	public ActiveList(ActiveTable fromTable) 
	{
		select = new ActiveSelect(fromTable);
	}
	
	/*
	 *  return the list member
	 *  if null, instantiate it by executing the select
	 */	
	private ArrayList<ActiveRecord> getList()
	{
		if ( list == null )
		{
			list = select.load();
		}
		return list;
	}
	

	// implement all the List methods, using the list member
	
	public void clear() 
	{
		getList().clear();
	}


	public boolean add(ActiveRecord object) 
	{
		return getList().add(object);
	}


	public void add(int location, ActiveRecord object) 
	{
		getList().add(location,object);
	}


	public boolean addAll(Collection<? extends ActiveRecord> arg0) 
	{
		return getList().addAll(arg0);
	}


	public boolean addAll(int arg0,
			Collection<? extends ActiveRecord> arg1) 
	{
		return getList().addAll(arg0, arg1);
	}


	public ActiveRecord get(int location) 
	{
		return getList().get(location);
	}


	public int indexOf(Object object) 
	{
		return getList().indexOf(object);
	}


	public boolean isEmpty() 
	{
		return getList().isEmpty();
	}


	public Iterator<ActiveRecord> iterator() 
	{
		return getList().iterator();
	}


	public int lastIndexOf(Object object) 
	{
		 return getList().lastIndexOf(object);
	}


	public ListIterator<ActiveRecord> listIterator() 
	{
		return getList().listIterator();
	}


	public ListIterator<ActiveRecord> listIterator(int location) 
	{
		return getList().listIterator(location);
	}


	public ActiveRecord remove(int location) 
	{
		return getList().remove(location);
	}


	public boolean remove(Object object) 
	{
		return getList().remove(object);
	}


	public boolean removeAll(Collection<?> arg0) 
	{
		return getList().removeAll(arg0);
	}


	public boolean retainAll(Collection<?> arg0) 
	{
		return getList().retainAll(arg0);
	}



	public int size() 
	{
		return getList().size();
	}


	public List<ActiveRecord> subList(int start, int end) 
	{
		return getList().subList(start, end);
	}


	public Object[] toArray() 
	{
		return getList().toArray();
	}


	public <T> T[] toArray(T[] array) 
	{
		return getList().toArray(array);
	}


	public boolean contains(Object object) 
	{
		return getList().contains(object);
	}


	public boolean containsAll(Collection<?> arg0) 
	{
		return getList().containsAll(arg0);
	}



	public ActiveRecord set(int location, ActiveRecord object) 
	{
		return getList().set(location, object);
	}
	
	
	public ActiveList<ActiveRecord> reload() 
	{
		this.list = select.load();
		return (ActiveList<ActiveRecord>) this;
	}


	public void deleteAll() 
	{
		for ( ActiveRecord record : getList() )
		{
			record.delete();
		}		
	}


	public void saveAll() 
	{
		for ( ActiveRecord record : getList() )
		{
			record.delete();
		}		
	}


	public ActiveList<ActiveRecord> where(String where) 
	{
		select.where(where);
		return (ActiveList<ActiveRecord>) this;
	}


	public ActiveList<ActiveRecord> order(String by) 
	{
		select.order(by);
		return (ActiveList<ActiveRecord>) this;
	}


	public ActiveList<ActiveRecord> limit(long limit) 
	{
		select.limit(limit);
		return (ActiveList<ActiveRecord>) this;
	}


	public ActiveList<ActiveRecord> load() 
	{
		return this.reload();
	}


	public boolean getDistinct()
	{
		return select.getDistinct();
	}


	public String getTableName() {
		return select.getTableName();
	}


	public String[] getCulomns() {
		return select.getCulomns();
	}


	public String getWhere() {
		return select.getWhere();
	}


	public String getGroup() {
		return select.getGroup();
	}


	public String getHaving() {
		return select.getHaving();
	}


	public String getOrder() 
	{
		return select.getOrder();
	}


	public String getLimit() {
		return select.getLimit();
	}

}
