
package gsearch.test;

import java.util.*;

import gsearch.*;
import junit.framework.TestCase;

public class ClientTest extends TestCase
{

	static
	{
		System.getProperties().put("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "trace");
	}
	
	public Client getClient()
	{
		Client c = new Client();
		
		c.setCompressionEnabled(false);
		
		return c;
	}
	
	public void testNewsByZipCode() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchNewsByLocation("97202");

		assertValidResults(results);
	}
	
	public void testNewsByCityState() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchNewsByLocation("Seattle, WA");
		
		assertValidResults(results);
		
	}

	public void testNewsByTopic() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchNews(NewsTopic.WORLD);
		
		assertValidResults(results);
		
	}

	public void testNewsByZipAndTopic() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchNews(null, "97202", NewsTopic.HEADLINES);
		
		assertValidResults(results);
		
	}

	public void testNewsByCityAndTopic() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchNews(null, "Boston, MA", NewsTopic.BUSINESS);
		
		assertValidResults(results);
		
	}

	public void testLocalQuery() throws Exception
	{
		Client c = getClient();
		
        // PGE Park in Portland Oregon:   45.521694,-122.691806

		double lat = 45.521694;
		
		double lon = -122.691806;
		
		List<Result> results = c.searchLocal(lat, lon, "coffee");
		
		assertTrue(results.size() > 0);
		
		assertValidResults(results);
		
	}
	
	public void testNewsQuery() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchNews("Yankees", "New York, NY", null);
		
		assertValidResults(results);
		
	}
	
	public void testWebSearch() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchWeb("google ajax search");
		
		assertValidResults(results);
		
	}

	public void testBookSearch() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchBooks("economy");
		
		assertValidResults(results);
		
	}

	public void testImageSearch() throws Exception
	{
		Client c = getClient();
		
		List<Result> results = c.searchImages("lolcats");
		
		assertValidResults(results);
		
	}

	private void assertValidResults(List<Result> results)
	{
		assertNotNull(results);
		
		assertTrue(results.size() > 0);
		
		for (Result r : results)
		{
			assertValid(r);
		}
		
	}

	private void assertValid(Result r)
	{
		System.out.println("--------------------------------------------------------");
		System.out.println(r);
		
		assertNotNull(r.getTitle());
		assertNotNull(r.getTitleNoFormatting());
		
	}
	
}
