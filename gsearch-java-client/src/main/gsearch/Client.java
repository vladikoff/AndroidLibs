
package gsearch;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import com.google.gson.*;

import gsearch.internal.gson.*;
import gsearch.internal.http.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.*;

//
//
//   http://code.google.com/apis/ajaxsearch/documentation/#fonje
//
//
//   News search examples:
//
//      http://ajax.googleapis.com/ajax/services/search/news?v=1.0&geo=Portland+Oregon
//
//      http://ajax.googleapis.com/ajax/services/search/news?v=1.0&geo=97202
//
//      http://ajax.googleapis.com/ajax/services/search/news?v=1.0&geo=Singapore
//
//   Local search example:
//
//      http://ajax.googleapis.com/ajax/services/search/local?v=1.0&q=coffee&sll=45.521694,-122.691806&mrt=localonly
//
//   Web search example:
//
//      http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=google+gson
//
//   Book search example:
//
//     http://ajax.googleapis.com/ajax/services/search/books?v=1.0&q=economy
//
//   Image search example:
//
//      http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=lolcats
//
//   Video search example:
//
//      http://ajax.googleapis.com/ajax/services/search/video?v=1.0&q=lolcats&orderBy=order-by-date
//
//   Blog search example:
//
//      http://ajax.googleapis.com/ajax/services/search/blogs?v=1.0&q=lolcats
//
//

public class Client
{
	private HttpClient httpClient;
	private static final String NEWS_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/news";
	private static final String LOCAL_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/local";
	private static final String WEB_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/web";
	private static final String BOOK_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/books";
	private static final String IMAGE_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/images";
	private static final String VIDEO_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/videos";
	private static final String BLOG_SEARCH_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/blogs";
	
	private boolean compressionEnabled = false;
	
	public Client()
	{
		this(new DefaultHttpClient());
	}
	
	public Client(HttpClient hClient)
	{
		this.httpClient = hClient;
	

		//
		//  this user agent string has been crafted this way
		//  so that Google's service will return gzip compressed responses
		//  when Accept-Encoding: gzip is present in the request.
		//
		
		setUserAgent("Mozilla/5.0 (Java) Gecko/20081007 gsearch-java-client");
		
		setConnectionTimeout( 10 * 1000 );
		setSocketTimeout(25 * 1000);
		
		
	}
	
	public void setUserAgent(String ua)
	{
		this.httpClient.getParams().setParameter(AllClientPNames.USER_AGENT, ua);
	}
	
	public void setConnectionTimeout(int milliseconds)
	{
		httpClient.getParams().setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, milliseconds);
	}
	
	public void setSocketTimeout(int milliseconds)
	{
		httpClient.getParams().setIntParameter(AllClientPNames.SO_TIMEOUT, milliseconds);
	}
	
	
	
	protected Response sendSearchRequest(String url, Map<String, String> params)
	{
		
		if (params.get("v") == null)
		{
			params.put("v", "1.0");
		}
		
		String json = sendHttpRequest("GET", url, params);
		
		Response r = fromJson(json);
		
		r.setJson(json);
		
		return r;
	}
	
	
	protected Response fromJson(String json)
	{
		Gson gson = GsonFactory.createGson();
		
		Response r = gson.fromJson(json, Response.class);
		
		return r;
	}
	
	protected String sendHttpRequest(String httpMethod, String url, Map<String, String> params)
	{
		HttpClient c = getHttpClient();
		
		HttpUriRequest request = null;
		
		
		if ("GET".equalsIgnoreCase(httpMethod))
		{


			String queryString = buildQueryString(params);
			
			url = url + queryString;
			
			System.out.println(url);
			
			request = new HttpGet(url);
			
		}
		else
		{
			throw new RuntimeException("unsupported method: " + httpMethod);
		}
	
		HttpResponse response = null;
		HttpEntity entity = null;
		
		try
		{
			response = c.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK)
			{
				throw new RuntimeException("unexpected HTTP response status code = " + statusCode);
			}
			
			entity = response.getEntity();
			
			return EntityUtils.toString(entity);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			// todo : 
		}
		
	}
	
	private String buildQueryString(Map<String, String> params)
	{
		StringBuffer query = new StringBuffer();
		
		if (params.size() > 0)
		{
			query.append("?");
			
			for (String key : params.keySet())
			{
				query.append(key);
				query.append("=");
				query.append(encodeParameter(params.get(key)));
				query.append("&");
			}
			
			if (query.charAt(query.length() - 1) == '&')
			{
				query.deleteCharAt(query.length() - 1);
			}
		}			
		
		return query.toString();
	}

	protected String decode(String s)
	{
		try
		{
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	protected String encodeParameter(String s)
	{
		try
		{
			return URLEncoder.encode(s, "UTF-8");
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	protected HttpClient getHttpClient()
	{
		
		if (this.httpClient instanceof DefaultHttpClient)
		{
			DefaultHttpClient defaultClient = (DefaultHttpClient) httpClient;
			
			defaultClient.removeRequestInterceptorByClass(GzipRequestInterceptor.class);
			defaultClient.removeResponseInterceptorByClass(GzipResponseInterceptor.class);
			
			if (this.isCompressionEnabled())
			{
				defaultClient.addRequestInterceptor(GzipRequestInterceptor.getInstance());
				defaultClient.addResponseInterceptor(GzipResponseInterceptor.getInstance());
			}
			
		}

		return this.httpClient;
	}
	
	/**
	 * 
	 * @param location  use "city, state" (example: "Miami, FL") or zip code  ("97202") or country ("Singapore")
	 * 
	 * @return
	 * 
	 */
	public List<Result> searchNewsByLocation(String location)
	{
		return searchNews(null, location, null);
	}
	
	/**
	 * 
	 * @param query  may be null
	 * @param location  use "city, state" (example: "Miami, FL") or zip code  ("97202") or country ("Singapore")
	 * @param topic may be null
	 * 
	 * @return
	 * 
	 */
	public List<Result> searchNews(String query, String location, NewsTopic topic)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();

		if ( (query != null) && (query.trim().length() > 0) )
		{
			params.put("q", query);
		}
		
		if (location != null)
		{
			params.put("geo", location);
		}
		
		if (topic != null)
		{
			params.put("topic", topic.getCode());
		}
		
		Response r = sendNewsSearchRequest(params);

		return r.getResponseData().getResults();
	}


	protected Response sendNewsSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(NEWS_SEARCH_ENDPOINT, params);
	}
	
	protected Response sendLocalSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(LOCAL_SEARCH_ENDPOINT, params);
	}
	
	protected Response sendWebSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(WEB_SEARCH_ENDPOINT, params);
	}

	protected Response sendBookSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(BOOK_SEARCH_ENDPOINT, params);
	}

	protected Response sendImageSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(IMAGE_SEARCH_ENDPOINT, params);
	}

	public boolean isCompressionEnabled()
	{
		return compressionEnabled;
	}

	public void setCompressionEnabled(boolean b)
	{
		this.compressionEnabled = b;
	}

	
	
	/**
	 * 
	 * 
	 *   send HTTP GET
	 *   
	 *   This method can be used to retrieve images  (JPEG, PNG, GIF)
	 *   or any other file type
	 *   
	 *   @return byte array
	 *  
	 */
	public byte[] getBytesFromUrl(String url)
	{
		try
		{
			HttpGet get = new HttpGet(url);
			
			HttpResponse response = this.getHttpClient().execute(get);
			
			HttpEntity entity = response.getEntity();
			
			if (entity == null)
			{
				throw new RuntimeException("response body was empty");
			}
			
			return EntityUtils.toByteArray(entity);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 
	 * send HTTP GET
	 * 
	 */
	public String get(String url) 
	{
		try
		{
			HttpGet get = new HttpGet(url);
			
			HttpResponse response = this.getHttpClient().execute(get);
			
			HttpEntity entity = response.getEntity();
			
			if (entity == null)
			{
				throw new RuntimeException("response body was empty");
			}
			
			return EntityUtils.toString(entity);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public List<Result> searchNews(NewsTopic topic)
	{
		return searchNews(null, null, topic);
	}

	public List<Result> searchWeb(String query)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("q", query);
		
		Response r = sendWebSearchRequest(params);
		
		return r.getResponseData().getResults();
	}
	

	public List<Result> searchBooks(String query)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("q", query);
		
		Response r = sendBookSearchRequest(params);
		
		return r.getResponseData().getResults();
	}
	

	public List<Result> searchImages(String query)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("q", query);
		
		Response r = sendImageSearchRequest(params);
		
		return r.getResponseData().getResults();
	}
	

	public List<Result> searchLocal(double lat, double lon, String query)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("sll", lat + "," + lon);
		params.put("mrt", "localonly");
		
		if (query != null)
		{
			params.put("q", query);
		}
		
		Response r = sendLocalSearchRequest(params);
		
		return r.getResponseData().getResults();
		
	}
	
	public List<Result> searchVideos(String query, OrderBy order)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("q", query);
		
		if (order == null)
		{
			order = OrderBy.RELEVANCE;
		}
		
		params.put("orderBy", order.getValue());
		
		
		Response r = sendVideoSearchRequest(params);
		
		return r.getResponseData().getResults();
	}

	protected Response sendVideoSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(VIDEO_SEARCH_ENDPOINT, params);
	}

	public List<Result> searchBlogs(String query)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("q", query);
		
		Response r = sendBlogSearchRequest(params);
		
		return r.getResponseData().getResults();
	}

	protected Response sendBlogSearchRequest(Map<String, String> params)
	{
		return sendSearchRequest(BLOG_SEARCH_ENDPOINT, params);
	}
	
}
