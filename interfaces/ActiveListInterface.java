package activerecord.interfaces;

import java.util.List;


public interface ActiveListInterface<E extends ActiveRecordInterface> extends ActiveSelectInterface, List<E> 
{
	public ActiveListInterface reload();
	
	public void deleteAll();
	
	public void saveAll();
}
