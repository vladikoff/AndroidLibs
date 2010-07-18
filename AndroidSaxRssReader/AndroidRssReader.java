package com.exercise.AndroidRssReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AndroidRssReader extends ListActivity {
	
	private List<String> item = new ArrayList<String>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
			URL rssUrl = new URL("http://feeds.feedburner.com/Android-er?format=xml");
			SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance();
			SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
			XMLReader myXMLReader = mySAXParser.getXMLReader();
			RSSHandler myRSSHandler = new RSSHandler();
			myXMLReader.setContentHandler(myRSSHandler);
			InputSource myInputSource = new InputSource(rssUrl.openStream());
			myXMLReader.parse(myInputSource);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		ArrayAdapter<String> itemList = new ArrayAdapter<String>(this, R.layout.rsslist, item);
		setListAdapter(itemList);
    }
    
    private class RSSHandler extends DefaultHandler
    {
    	final int stateUnknown = 0;
    	final int stateTitle = 1;
    	int state = stateUnknown;
    	
		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (localName.equalsIgnoreCase("title"))
			{
				state = stateTitle;
			}
			else
			{
				state = stateUnknown;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			state = stateUnknown;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			String strCharacters = new String(ch, start, length);
			if (state == stateTitle)
			{
				item.add(strCharacters);
				
			}
		}
    	
    }
}