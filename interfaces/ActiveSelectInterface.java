package activerecord.interfaces;

import java.util.List;

public interface ActiveSelectInterface 
{
	
	public ActiveSelectInterface 		where	(String where);
	
	public ActiveSelectInterface 		order	(String by);
	
	public ActiveSelectInterface 		limit	(long limit);
	
	public List							load	();
	
	
	public boolean getDistinct();

	public String getTableName();

	public String[] getCulomns();

	public String getWhere();

	public String getGroup();

	public String getHaving();

	public String getOrder();

	public String getLimit();
		
}
