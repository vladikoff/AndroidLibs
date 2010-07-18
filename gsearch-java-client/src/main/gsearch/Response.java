
package gsearch;

import java.util.*;

public class Response
{
	private String json;
	private ResponseData responseData;
	private String responseDetails;
	private Integer responseStatus;
	
	public void setJson(String j)
	{
		this.json = j;
	}

	
	public ResponseData getResponseData()
	{
		return responseData;
	}


	public void setResponseData(ResponseData responseData)
	{
		this.responseData = responseData;
	}


	public String getJson()
	{
		return json;
	}

	public boolean isOK()
	{
		if (this.getResponseStatus() == null)
		{
			return false;
		}
		else
		{
			return this.getResponseStatus().intValue() == 200;
		}
	}
	
	public boolean isError()
	{
		return !isOK();
	}
	

	public String getResponseDetails()
	{
		return responseDetails;
	}


	public void setResponseDetails(String details)
	{
		this.responseDetails = details;
	}


	public Integer getResponseStatus()
	{
		return responseStatus;
	}


	public void setResponseStatus(Integer status)
	{
		this.responseStatus = status;
	}


	public static class ResponseData
	{
		private List<Result> results;
		
		public List<Result> getResults()
		{
			if (results == null)
			{
				results = new ArrayList<Result>();
			}
			return results;
		}

		public void setResults(List<Result> resultList)
		{
			this.results = resultList;
		}

	}
	
}
