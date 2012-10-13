package activerecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.exr.xrxindusries.model.Book;

import activerecord.ActiveRecord;
import activerecord.interfaces.ActiveListInterface;


public class ActiveList<R extends ActiveRecord> implements ActiveListInterface<R>  
{
	
	private ArrayList<R> list = null;
	private ActiveSelect<R> select = null;
	
	
	public ActiveList(ActiveTable<R> fromTable) 
	{
		select = new ActiveSelect<R>(fromTable);
	}
	
	public ActiveList(Class<R> modelClass) 
	{
		this( (ActiveTable<R>) ActiveSchema
									.getInstance()
										.getTable(	ActiveRecord
												.getContext(), 
									modelClass) );
	}

	/*
	 *  return the list member
	 *  if null, instantiate it by executing the select
	 */	
	private ArrayList<R> getList()
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


	public boolean add(R object) 
	{
		return getList().add(object);
	}


	public void add(int location, R object) 
	{
		getList().add(location,object);
	}

	public R get(int location) 
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


	public Iterator<R> iterator() 
	{
		return getList().iterator();
	}


	public int lastIndexOf(Object object) 
	{
		 return getList().lastIndexOf(object);
	}


	public ListIterator<R> listIterator() 
	{
		return getList().listIterator();
	}


	public ListIterator<R> listIterator(int location) 
	{
		return getList().listIterator(location);
	}


	public R remove(int location) 
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


	public List<R> subList(int start, int end) 
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



	public R set(int location, R object) 
	{
		return getList().set(location, object);
	}
	
	
	public ActiveList<R> reload() 
	{
		this.list = select.load();
		return this;
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
			record.save();
		}		
	}


	public ActiveList<R> where(String where) 
	{
		select.where(where);
		return this;
	}


	public ActiveList<R> order(String by) 
	{
		select.order(by);
		return this;
	}


	public ActiveList<R> limit(long limit) 
	{
		select.limit(limit);
		return this;
	}


	public ActiveList<R> load() 
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

	public boolean addAll(Collection<? extends R> collection) 
	{
		return list.addAll(collection);
	}

	public boolean addAll(int index, Collection<? extends R> collection) 
	{
		return list.addAll(index, collection);
	}

	public String toString()
	{
		String str = "list: ";
		for ( R record : this )
		{
			str += "\n{" + record + "}"; 
		}
		return str;
	}

	

}
