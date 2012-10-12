package activerecord.interfaces;

import java.util.List;


public interface ActiveListInterface<R extends ActiveRecordInterface> extends ActiveSelectInterface<R>, List<R> 
{
	public ActiveListInterface<R> reload();
	
	public void deleteAll();
	
	public void saveAll();
}
