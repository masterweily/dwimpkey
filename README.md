dwimpkey
========

<b>AKA - </b> Dude, where is my primary key?

<h1>PRELIMINAR DEVELOPMENT STAGE, NOT READY FOR USE!</h1>

Active Record implementation for android apps

<h3>Getting Started</h3>

Download, install and configure the library in your project

1. <b>download</b> all the files in the repository
2. <b>install</b> the library by adding the downloaded files to your project as a package
3. <b>configure</b> the library by edditing the ActiveConfig.java file:

```java
public class ActiveConfig 
{
	/*
	 * ActiveField Name
	 * 
	 * should be manually incremented any time you apply changes in the models
	 * that should affect the ActiveField schema
	 * 
	 * Recommended format:  "*.db"
	 * 
	 */
	public static final String ActiveField_NAME = "dwimpkey.db"; 
	
	/*
	 * ActiveField Version
	 * 
	 * should be manually incremented after any set of changes in 
	 * the model classes decelerations that should effect the ActiveField schema
	 * 
	 * must be > 0
	 * 
	 */
	public static final int VERSION = 6;  

	
	/*
	 * list all of your models that will behave as ActiveRecord
	 * 
	 * all of them must extend ActiveRecord as well
	 */	
	public static Class<?>[] getActiveModels()
	{
		return new Class[] { 	Product.class, 
								Book.class		};
	}
}
```

<h3>Documentation</h3>

<h5>Declare a Model Class</h5>

Here is how to generate a table named 'products' with auto incremented primary key named 'id'

```java
public final class Product extends ActiveRecord  
{	

	public Product()			// THIS CONSTRUCTOR MUST BE IMPLEMENTED
	{
		super();
	}
	
	@ActiveField
	public String 	name;  		// generates dataase field as slqlite TEXT

	@ActiveField
	public double 	price;   	// generates dataase field as slqlite REAL	
	
	@ActiveField
	public int barcode;   		// generates dataase field as slqlite INTEGER
	
	public String review;  		// no database field for this one
}
```
<b>Currentlly suported types:</b>

* int
* long
* short
* double
* float
* String


<h5>Create Row</h5>

```java
Product product = new Product(); // create new instance
product.name = "Computer";
product.barcode = 1234;
product.price = 99.9;
product.save();   //  save the new instance to the database
```

<h5>Find and Update Row</h5>

```java
Product product = new Product.find(1); // find row where id=1
product.price = 199.9;  // update a value in the object
product.save();   //  save the updated object back to the database
```

<h5>Delete</h5>
```java
new Product.find(1).delete(); // find row where id=1 and delete it frome the database
```

<h5>Select Query</h5>

<b>Select All Rows</b>

```java
Product[] products = new ActiveList<Product>(Product.class).toArray();
// or 
ActiveList<Product> products = new ActiveList<Product>(Product.class);
```
generates:
```sql
SELECT * FROM products;
```

<b>Chained Select</b>
```java

ActiveList<Product> products = 	 new ActiveList<Product>(Product.class)
										.where("price>50")
										.where("price<200")
										.name("name<>'Bad Product'")
										.order("price ASC")
										.order("name DESC")
										.limit(10);
```					
generates:
```sql
SELECT * 	FROM products 
WHERE 		price > 50 
AND 		price < 200
AND			name<>'Bad Product'
ORDER_BY	price ASC, name DESC
LIMIT		10;
```


<h5>Relations - NOT IMPLEMENTED YET</h5>

<b>One To One</b>

Decleration:

```java
public final class Costumer extends ActiveRecord  
{	
	@ActiveField
	public String 	name;
	
	@ActiveField
	public Acount bill; 	// holds a foreign key named bill_id 
}

public final class Acount extends ActiveRecord  
{	
	@ActiveField
	public String 	name;
	
	@ActiveRelation(as="bill")
	public ActiveList<Costumer> costumer;    // now related to a forien key named: 'bill_id' in table 'costumers' 
}
```
Usage:

```java
Costumer costumer = new Costumer.find(1);
int acount_id = costumer.acount.getId();  // get the costumer's acount id with no ActiveField query
Acount acount = costumer.acount.get();  // get the costumers's acount, with a select query.
Acount acount2 = costumer.acount.get(); // no query this time, the data is allready loaded.

Acount acount3 = new Acount.find(1);
int id_1 = acount3.costumer.getId();  // this one will generate a query, because acounts doesnt hold a foreign key
int id_2 = acount3.costumer.get().getId(); // this one is better, couse now the costumer is fully loaded
```

<b>One To Many</b>

decleration:

```java

public final class Resturant extends ActiveRecord
{
	@ActiveRelation(as="workPlace")
	public ActiveList<Employee> employees;
}

@ActiveModel(version=1)
public final class Employee extends ActiveRecord
{
	@ActiveField
	public Resturant workPlace;
}
```

usage:

```java
ActiveList<Employee> employees = resturant.employees.get();
```

```java
resturant.employees.add(employee);  // add an employee to the list of the resturant employees
resturant.employees.saveAll();		// update the database about all the changes in that list
```

<b>Many To Many</b>

decleration:

```java

public final class SourceCode extends ActiveRecord
{
	@ActiveRelation  // no need for (as="souresCode"), as it's the default
	public ActiveList<Programmer> contributers;
}

public final class Programmer extends ActiveRecord
{
	@ActiveRelation(as="contributer")
	public ActiveList<SourceCode> sourceCodes;
}
```
<h5>Scopes</h5>

declaration:  

```java
public final class Page extends ActiveRecord
{
	@ActiveField
	public Category category;  
	
	@ActiveField
	public boolean active;
	
	@ActiveField
	public int position;
	
	public static ActiveList<Page> publicList()
	{
		return new ActiveList<Page>(Page.class).where("active=1").order("position ASC");
	}
}
```
usage:

```java
ActiveList<Page> allPublicPages = Page.publicList();  // simple use of the scope
ActiveList<Page> somePublicPages = Page.publicList().limit(10);  // chain scope with other clauses
```
<b>need to change implementation to make this possible:</b>
```java
ActiveList<Page> categoryPublicPages = category.pages.publicList(); // chain scope with relative query
```














