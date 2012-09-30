dwimpkey
========

<b>AKA - </b> Dude, where is my primary 

<h1>PRELIMINAR DEVELOPMENT STAGE, NOT READY FOR USE!</h1>

Active Record implementation for android apps

<h3>Getting Started</h3>

Download, install and configure the library in your project

1. download the files
2. add the filse to your project as a package
3. thats it

<h3>Documentation</h3>

<h5>Create a Model Class</h5>

<code>
<pre>
@Model(version=1)
public final class Product extends ActiveRecord 
{	
	@Database 
	public int 	price;	
	
	@Database 
	public String 	name;
	
	public String review;
}
</pre>
</code>
