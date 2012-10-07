package activerecord;

/*
 * the grammar rules for the package 
 */
class Grammar
{
	
	/*
	 * evaluate suitable name for a database table, currently lame pluralization and simple lower case
	 * @param recordName 
	 * 
	 * Examples:
	 * 
	 * String 		=> strings
	 * RecordName 	=> recordnames
	 * Hash			=> hashs
	 * HushPuppy	=> hushpuppys
	 */
	public static String toTableName(String recordName)
	{
		return pluralize( recordName.toLowerCase() );
	}

	/*
	 * evaluate suitable name for a foreign key of a field.
	 * @param fieldName
	 *
	 * Examples:
	 * 
	 * fieldName => fieldName_id
	 */
	public static String toForeignKey(String fieldName)
	{
		return fieldName + "_id";
	}
	
	/*
	 * evaluate the plural form of an English singular formed word,
	 * currently just put 's' in the end of the String
	 */
	private static String pluralize(String singular) 
	{
		return singular + "s";
	}
}
