
package gsearch;

public enum OrderBy
{
	RELEVANCE("order-by-relevance"), DATE("order-by-date");
	
	private String value;
	
	OrderBy(String val) 
	{
		value = val;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
}
