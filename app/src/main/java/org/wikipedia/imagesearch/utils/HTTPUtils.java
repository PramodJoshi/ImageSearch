package org.wikipedia.imagesearch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HTTPUtils {

	private static final String REQUEST_METHOD = "GET";
	
	/**
	 * Ensure the class cannot be instantiated
	 */
	private HTTPUtils() {
		
	}

	static String performRESTRequest(URL url) throws IOException {
		String json = null;
		HttpURLConnection conn = null;
        StringBuffer response = new StringBuffer();
        String line;
        BufferedReader reader = null;
        
        try {
    		conn = (HttpURLConnection) url.openConnection();
    	
    		conn.setConnectTimeout(8000);
    		conn.setReadTimeout(8000);
		    conn.setRequestMethod(REQUEST_METHOD);
		    
		    // Start the query
		    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    conn.connect();
		    
		    while ((line = reader.readLine()) != null) {
		    	response = response.append(line);
            }
		    
		    json = response.toString();
        } finally {
        	Utils.closeBufferedReader(reader);
        	Utils.closeHttpConnection(conn);
        }
        
        return json;
    }
}
