
package gsearch;

public class PhoneNumber
{
	private String type;
	private String number;
	
	public String getType()
	{
		return type;
	}

	public void setType(String t)
	{
		this.type = t;
	}



	public String getNumber()
	{
		return number;
	}



	public void setNumber(String n)
	{
		this.number = n;
	}



	public boolean isMain()
	{
		return "main".equalsIgnoreCase(this.getType());
	}
	
	public boolean isFax()
	{
		return "fax".equalsIgnoreCase(this.getType());
	}
	
	public boolean isMobile()
	{
		return "mobile".equalsIgnoreCase(this.getType());
	}
	
	
	public boolean isData()
	{
		return "data".equalsIgnoreCase(this.getType());
	}
	
	
	public String toString()
	{
		return ToStringBuilder.build(this);
	}
	
}
