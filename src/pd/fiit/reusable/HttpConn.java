package pd.fiit.reusable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/** HTTP communication, GET and POST requests and downloading to file, more to be added in the future */
// TODO: nicer handling, prevent returning strings
public class HttpConn {
	private static final Logger logger = Logger.getLogger(HttpConn.class.getName());
	
	/** downloads file to chosen destination, uses stream instead of writer, binary files supported */
	public static void HttpDownloadFile(String host, String fileName) throws IOException {
        try {
        	FileOutputStream fos = new FileOutputStream(fileName);
        	BufferedInputStream in = new BufferedInputStream(new URL(host).openStream());
		    byte data[] = new byte[1024];
		    
		    int count;
		    while((count = in.read(data,0,1024)) != -1)
		    {
		    	fos.write(data, 0, count);
		    }

			in.close();
		    fos.close();
		    
		} catch (MalformedURLException e1) {
			logger.severe("could not connect to server, malformed URL");
			if (logger.isLoggable(Level.WARNING))
				logger.log(Level.WARNING, "", e1);
			
		} 
	}
	
	/** makes GET request to server and saves server answer */
	public static String HttpGet(String host) {
		StringBuffer sAnswer = new StringBuffer();
        
        try { // text,html
        	URL url = new URL(host);
        	URLConnection conn = url.openConnection();
        	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0) Gecko/20100101 Firefox/4.0");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         
            String inputLine;        
            while ((inputLine = in.readLine()) != null) 
            sAnswer.append(inputLine+"\n");
            in.close();
        } catch (IOException e) { // first failed, trying gzip
        	
        	try {
        		URL url = new URL(host);
        		URLConnection conn = url.openConnection();
            	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0) Gecko/20100101 Firefox/4.0");
                
	        	GZIPInputStream zip = new GZIPInputStream(conn.getInputStream());
		        while (true) {
		          int c;
		          c = zip.read();
		          if (c == -1)
		            break;
		          sAnswer.append((char) c);
		        }
        	} catch (IOException ee) { // failed anyway, throwing exception
        		logger.severe("cannot connect to server, please check your connection.");
        	}
        	
       }
        
        return sAnswer.toString(); // bad practice; however, need string for xml anyway
    }
	
	/** posts data to server and saves the server answer, fully modifiable HTTP header 
	 * @throws UnknownHostException */
	public static String HttpPost(String host, String page, String postData) throws UnknownHostException {
		InetAddress addr = InetAddress.getByName(host);
		Socket socket = null;;
		
		try {
			socket = new Socket(addr, 80);
		} catch (IOException e1) {
			logger.severe("could not create socket.");
		}
		
		int postDataLength = postData.length();
		
		//printstream because of parsing string header
		//http header used because of past problems with POST requests in java, coded months ago
		PrintStream wr = null;
		
		try {
			wr = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			logger.warning("could not parse http header into stream.");
		} catch (NullPointerException ee) {
			logger.warning("can not send request to API server.");
		}
		
		wr.print("POST /" + page + " HTTP/1.1\r\n" +
				 "Host: " + host + "\r\n" +
				 "User-Agent: SubtitleNet\r\n" +
				 "Keep-Alive: 300\r\n" +
				 "Connection: keep-alive\r\n" +
				 "Content-Type: text/xml\r\n" + 
				 "Content-Length: " + postDataLength + "\r\n\r\n" + 
				 postData + "\r\n\r\n");
		
		wr.flush(); 
		
		BufferedReader rd = null;
		String line; StringBuffer response = new StringBuffer();
		
		try {
			rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((line = rd.readLine()) != null) 
				response.append(line + "\n");
		} catch (IOException e1) {
			logger.severe("can not read server response.");
		} finally {
			wr.close(); 
		}
		
		return response.toString();
    }
}
