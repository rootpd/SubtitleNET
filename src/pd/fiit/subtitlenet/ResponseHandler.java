package pd.fiit.subtitlenet;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class ResponseHandler {
	private static final Logger logger = Logger.getLogger(SearchHandler.class.getName());
	private Document xmlDoc = null;
	private String response = null;
	
	public ResponseHandler(String response) {
		setResponse(response);
	}
	
	public void setResponse(String response) {
		this.response = response;
		responseToXml();
	}
	
	/** gets value of given xml-rpc response variable */
	public String getVariableValue(String variable) {
		NodeList dataNodes = xmlDoc.getElementsByTagName("member");
		
		for (int i=0; i<dataNodes.getLength(); i++) {
			Element node = (Element) dataNodes.item(i);
			String nodeName = node.getElementsByTagName("name").item(0).getTextContent();
			if (nodeName.equals(variable)) {
				Element value = (Element) node.getElementsByTagName("value").item(0);
				node.getParentNode().removeChild(node);
				return value.getElementsByTagName("string").item(0).getTextContent();
			}
		}
		
		return null;
	}
	
	/** converts string from server response to DOMxml */
	private void responseToXml() {
		DocumentBuilder docBuilder = null;
		
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(response)); // necessary for parsing the string
			xmlDoc = docBuilder.parse( is );
		} catch (ParserConfigurationException e1) {
			logger.severe("cannot parse server response to xml.");
		} catch (SAXException e2) {
			logger.severe("cannot parse server response to xml.");
		} catch (IOException e3) {
			logger.severe("cannot parse server response to xml.");
		}
	}
}
