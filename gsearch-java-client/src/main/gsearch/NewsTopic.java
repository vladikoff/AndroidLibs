package gsearch;

public enum NewsTopic
{
	HEADLINES("h"),
	WORLD("w"),
	BUSINESS("b"),
	NATION("n"),
	SCIENCE_AND_TECHNOLOGY("t"),
	ELECTIONS("el"),
	POLITICS("p"),
	ENTERTAINMENT("e"),
	SPORTS("s"),
	HEALTH("m"),
	POPULAR("po"),
	AFRICA("af"),
	RECOMMENDED("pe");
	
	private String code;
	
	NewsTopic(String code)
	{
		this.code = code;
	}
	
	public String getCode()
	{
		return this.code;
	}
}
