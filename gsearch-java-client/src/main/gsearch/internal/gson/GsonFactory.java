
package gsearch.internal.gson;

import com.google.gson.*;

import gsearch.*;

public class GsonFactory
{
	static public Gson createGson()
	{
		GsonBuilder builder = new GsonBuilder();
		
		// builder.setFieldNamingPolicy(namingConvention)
		
//		builder.registerTypeAdapter(type, typeAdapter);
		
		Gson gson = builder.create();
		
		return gson;
	}
}
