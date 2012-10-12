package activerecord;

import com.exr.xrxindusries.model.*;

public class ActiveConfig 
{

	public static final String DATABASE_NAME = "dwimpkey.db"; // *.db
	
	public static final int VERSION = 6; // must be > 0

	// list of the classes invited to the party
	public static Class<?>[] getActiveModels()
	{
		return new Class[] { 	Product.class, 
								Book.class	};
	}
}
