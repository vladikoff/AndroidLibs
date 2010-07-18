
package gsearch;

public class Image
{
	private String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String u)
	{
		this.url = u;
	}
	
	public String toString()
	{
		return ToStringBuilder.build(this);
	}
	
}
