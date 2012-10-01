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
@Model(version=1) // version must be > 0, incremented manualy to apply changes in the class to the db schema
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














