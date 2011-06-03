package pd.fiit.subtitlenet;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/** class for easy creating xml-rpc structures */
public final class XmlRpc {
	private Document xmlDoc;
	private Element root;
	private static final Logger logger = Logger.getLogger(XmlRpc.class.getName());
	
	/** just a constructor */
	XmlRpc(String apiMethod) {
        try {
			createXmlDoc(apiMethod);
		} catch (ParserConfigurationException e) {
			logger.severe("could not create xml-rpc document.");
		}
	}

	/** creates valid blank xml-rpc request */
	private void createXmlDoc(String apiMethod)	throws ParserConfigurationException {
		DocumentBuilder docBuilder;
		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDoc = docBuilder.newDocument();
		
		Element methodCall = xmlDoc.createElement("methodCall");
		xmlDoc.appendChild(methodCall);
   
		Element methodName = createChild(methodCall, "methodName");
		Text text = xmlDoc.createTextNode(apiMethod);
		methodName.appendChild(text);
		
		root = createChild(methodCall, "params");
	}
	
	/** parses xml to string for further manipulation */
	public String xmlToString() {
		Transformer trans; String xmlString = "";
		
		try {
			trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.VERSION, "1.0");
			trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(xmlDoc);
			
			trans.transform(source, result);
			xmlString = sw.toString();
			
		} catch (TransformerConfigurationException e) {
			if (logger.isLoggable(Level.WARNING))
				logger.warning("could not transform xml-rpc to string.");
		} catch (TransformerFactoryConfigurationError e) {
			if (logger.isLoggable(Level.WARNING))
				logger.warning("could not transform xml-rpc to string.");
		} catch (TransformerException e) {
			if (logger.isLoggable(Level.WARNING))
				logger.log(Level.WARNING, "could not transform xml-rpc to string.", e);
		}	
		
		return xmlString;
	}
	
	/** creates child to given parent and returns the child */
	private Element createChild(Element parent, String childName) {
		Element child = xmlDoc.createElement(childName);
		parent.appendChild(child);
		return child;
	}
	
	/** adds param to xml (according to xml-rpc "param" tag syntax) */
	Element addParam() {
        Element param = createChild(root, "param");
        Element value = createChild(param, "value");
        return value;
	}
	
	/** adds array according to xml-rpc syntax specification */
	List<Element> createArray(Element parent, int size) {
        Element array = createChild(parent, "array");
        Element data = createChild(array, "data");
        List<Element> values = new ArrayList<Element>();
        
        for (int i=0; i<size; i++) {
        	Element value = createChild(data, "value");
        	values.add(value);
        }
        
		return values;
	}
	
	/** adds struct to xml, lists must have same number of elements */
	Element createStruct(Element parent) {
		Element struct = createChild(parent, "struct");
		return struct;
	}
	
	/** every struct can have unlimited count of members, this adds one to chosen struct */
	void addMember(Element struct, String memberName, String memberType, String memberValue) {
		Element member = createChild(struct, "member");
		
		Element name = createChild(member, "name");
		Text text = xmlDoc.createTextNode(memberName);
        name.appendChild(text);
        
        Element value = createChild(member, "value");
        setValue(value, memberType, memberValue);
	}
	
	/** adds <type> tags and content to param */
	void setValue(Element parent, String paramType, String paramValue) {
		Element type = createChild(parent, paramType);
        Text text = xmlDoc.createTextNode(paramValue);
        type.appendChild(text);
	}
}
