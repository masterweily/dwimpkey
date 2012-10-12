package activerecord.interfaces;

import java.util.List;

public interface ActiveSelectInterface<R extends ActiveRecordInterface> 
{
	
	public ActiveSelectInterface<R> 		where	(String where);
	
	public ActiveSelectInterface<R> 		order	(String by);
	
	public ActiveSelectInterface<R> 		limit	(long limit);
	
	public List<R>							load	();
	
	
	public boolean getDistinct();

	public String getTableName();

	public String[] getCulomns();

	public String getWhere();

	public String getGroup();

	public String getHaving();

	public String getOrder();

	public String getLimit();
		
}
