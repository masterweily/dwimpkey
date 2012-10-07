dwimpkey
========

<b>AKA - </b> Dude, where is my primary key?

<h1>PRELIMINAR DEVELOPMENT STAGE, NOT READY FOR USE!</h1>

Active Record implementation for android apps

<h3>Getting Started</h3>

Download, install and configure the library in your project

1. download the files
2. add the filse to your project as a package
3. thats it

<h3>Documentation</h3>

<h5>Declare a Model Class</h5>

Here is how to generate a table named 'products' with auto incremented primary key named 'id'

```java
@ActiveModel(version=1) // version must be > 0, incremented manualy to apply changes in the class to the db schema
public final class Product extends ActiveRecord  
{	
	@Database
	public String 	name;  // generates dataase field as slqlite TEXT

	@Database
	public double 	price;   // generates dataase field as slqlite REAL	
	
	@Database
	public int barcode;   // generates dataase field as slqlite INTEGER
	
	public String review;  // no database field for this one
}
```
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
Product product = Product.find(1); // find row where id=1
product.price = 199.9;  // update a value in the object
product.save();   //  save the updated instance back to the database
```

<h5>Delete</h5>
```java
Product.find(1).delete(); // find row where id=1 and delete it
Product.delete(2);        // delete row where id=1 if exists
Product.deleteAll();      // delete all rows in produts table
```

<h5>Select Query</h5>

<b>Select All Rows</b>

```java
Product[] products = Product.all().toArray();
// or 
ActiveList<Product> products = Product.all();
```
generates:
```sql
SELECT * FROM products;
```

<b>Chained Select</b>
```java
Product[] products = 	 Product.where("price",">",50)
								.where("price","<",200)
								.order("price", "ASC")
								.order("name", "DSC")
								.toArray();
// or
ActiveList<Product> products = 	 Product.where("price",">",50)
										.where("price","<",200)
										.order("price", "ASC")
										.order("name", "DESC");
```					
generates:
```sql
SELECT * FROM products 
WHERE 	products.price > 50 
AND 	products.price < 200
ORDER	products.price ASC, products.name DESC
```

more clauses:  

```java
.join()
```
```java
.limit()
```
```java
.offset()
```

<h5>Relations</h5>

<b>One To One</b>

Decleration:

```java
@ActiveModel(version=1)
public final class Costumer extends ActiveRecord  
{	
	@Database
	public String 	name;
	
	@ActiveField
	public Acount bill; 	// holds a foreign key named bill_id 
}

public final class Acount extends ActiveRecord  
{	
	@Database
	public String 	name;
	
	@ActiveRelation(as="bill")
	public ActiveList<Costumer> costumer;    // now related to a forien key named costumers.bill_id
}
```
Usage:

```java
Costumer costumer = Costumer.find(1);
int acount_id = costumer.acount.getId();  // get the costumer's acount id with no database query
Acount acount = costumer.acount.get();  // get the costumers's acount, with a select query.
Acount acount2 = costumer.acount.get(); // no query this time, the data is allready loaded.

Acount acount3 = Acount.find(1);
int id = acount3.costumer.getId();  // this one will generate a query, because acounts doesnt hold a foreign key
int id = acount3.costumer.get().getId(); // this one is better, couse now the costumer is fully loaded
```

<b>One To Many</b>

decleration:

```java
@ActiveModel(version=1)
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
resturant.employees.add(employee);  // save an employee in the database to belong to the resturant
```

<b>Many To Many</b>

decleration:

```java
@ActiveModel(version=1)
public final class SourceCode extends ActiveRecord
{
	@ActiveRelation  // no need for (as="souresCode"), it's the default
	public HasMany<Programmer> contributers;
}

@ActiveModel(version=1)
public final class Programmer extends ActiveRecord
{
	@ActiveRelation(as="contributer")
	public ActiveList<SourceCode> sourceCodes;
}
```
<h5>Scopes</h5>

declaration:  

```java
@ActiveModel(version=1)
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
		return Page.where("active", "=", true).order("position", "ASC");
	}
}
```
usage:

```java
ActiveList<Page> allPublicPages = Page.publicList();  // simple use of the scope
ActiveList<Page> somePublicPages = Page.publicList().limit(10);  // chain scope with other clauses
ActiveList<Page> categoryPublicPages = category.pages.publicList(); // chain scope with relative query
```














