package com.mxgraph.smartml.model;

import java.awt.SystemColor;
import java.io.File;    
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.mxgraph.smartml.view.MainFrame;

public class XMLParser {
	
	public static Document doc = null;
	public static Transformer transformer = null;
	public static MainFrame mf = null;

	
	public static void initializeXMLfiles() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		sb.append("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n");
		
		sb.append("<xs:simpleType name=\"Service\">\n");
		sb.append("<xs:restriction base=\"xs:string\">\n");		
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");		

		sb.append("<xs:simpleType name=\"Capability\">\n");
		sb.append("<xs:restriction base=\"xs:string\">\n");		
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");	
		
		sb.append("<xs:simpleType name=\"Boolean_type\">\n");
		sb.append("<xs:restriction base=\"xs:boolean\">\n");	
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");	
		
		sb.append("<xs:simpleType name=\"Integer_type\">\n");
		sb.append("<xs:restriction base=\"xs:integer\">\n");			
		sb.append("<xs:minInclusive value=\"0\"/>\n");				
		sb.append("<xs:maxInclusive value=\"30\"/>\n");			
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");	
		
		sb.append("<xs:element id=\"theory\" name=\"theory\">\n");	
		sb.append("<xs:complexType>\n");			
		sb.append("<xs:choice maxOccurs=\"unbounded\">\n");			
		sb.append("<xs:element minOccurs=\"0\" ref=\"provides\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" ref=\"requires\"/>\n");			 		   
		sb.append("</xs:choice>\n");				      
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");

		sb.append("<xs:element name=\"repository\">\n");	
		sb.append("<xs:complexType>\n");			
		sb.append("<xs:choice maxOccurs=\"unbounded\">\n");			
		sb.append("<xs:element minOccurs=\"0\" ref=\"provides\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" ref=\"requires\"/>\n");	
		sb.append("<xs:element minOccurs=\"0\" ref=\"task_definition\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" ref=\"exogenous_event_definition\"/>\n");			
		sb.append("<xs:element minOccurs=\"0\" ref=\"formula_definition\"/>\n");			
		sb.append("</xs:choice>\n");				      
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");		

		sb.append("<xs:simpleType name=\"task_repository\">\n");
		sb.append("<xs:restriction base=\"xs:string\">\n");		
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");		
		
		sb.append("<xs:simpleType name=\"exogenous_event_repository\">\n");
		sb.append("<xs:restriction base=\"xs:string\">\n");		
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");
		
		sb.append("<xs:simpleType name=\"formulae_repository\">\n");
		sb.append("<xs:restriction base=\"xs:string\">\n");		
		sb.append("</xs:restriction>\n");				
		sb.append("</xs:simpleType>\n");

		sb.append("<xs:element name=\"task_definition\">\n");	
		sb.append("<xs:complexType>\n");
		sb.append("<xs:choice maxOccurs=\"unbounded\">\n");			
		sb.append("<xs:element minOccurs=\"0\" name=\"argument\" type=\"xs:string\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" name=\"precondition\" type=\"xs:string\"/>\n");	
		sb.append("<xs:element minOccurs=\"0\" name=\"supposed-effect\" type=\"xs:string\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" name=\"automatic-effect\" type=\"xs:string\"/>\n");
		sb.append("</xs:choice>\n");			
		sb.append("<xs:attribute name=\"name\" type=\"xs:string\"/>\n");	
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");

		sb.append("<xs:element name=\"exogenous_event_definition\">\n");	
		sb.append("<xs:complexType>\n");
		sb.append("<xs:choice maxOccurs=\"unbounded\">\n");	
		sb.append("<xs:element minOccurs=\"0\" name=\"argument\" type=\"xs:string\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" name=\"effect\" type=\"xs:string\"/>\n");		
		sb.append("</xs:choice>\n");			
		sb.append("<xs:attribute name=\"name\" type=\"xs:string\"/>\n");	
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");
		
		sb.append("<xs:element name=\"formula_definition\">\n");	
		sb.append("<xs:complexType>\n");
		sb.append("<xs:choice maxOccurs=\"unbounded\">\n");	
		sb.append("<xs:element minOccurs=\"0\" name=\"argument\" type=\"xs:string\"/>\n");			  
		sb.append("<xs:element minOccurs=\"0\" name=\"content\" type=\"xs:string\"/>\n");		
		sb.append("</xs:choice>\n");			
		sb.append("<xs:attribute name=\"name\" type=\"xs:string\"/>\n");	
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");
		
		sb.append("<xs:element id=\"predefined_term-provides\" name=\"provides\">\n");	
		sb.append("<xs:complexType>\n");
		sb.append("<xs:sequence>\n");
		sb.append("<xs:element name=\"service\" type=\"Service\"/>\n");
		sb.append("<xs:element name=\"capability\" type=\"Capability\"/>\n");
		sb.append("</xs:sequence>\n");
		sb.append("<xs:attribute name=\"value\" type=\"Boolean_type\"/>\n");	
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");
		
		sb.append("<xs:element id=\"predefined_term-requires\" name=\"requires\">\n");	
		sb.append("<xs:complexType>\n");
		sb.append("<xs:sequence>\n");
		sb.append("<xs:element name=\"task\" type=\"task_repository\"/>\n");
		sb.append("<xs:element name=\"capability\" type=\"Capability\"/>\n");
		sb.append("</xs:sequence>\n");
		sb.append("<xs:attribute name=\"value\" type=\"Boolean_type\"/>\n");	
		sb.append("</xs:complexType>\n");
		sb.append("</xs:element>\n");
		
		sb.append("</xs:schema>");
	
		scriviFile(Constants.getXSD_name_file(), sb);
		
		sb = new StringBuffer();
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		sb.append("<repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"new_schema.xsd\">\n");
		sb.append("</repository>");		
		
		scriviFile(Constants.getXML_name_file(), sb);

	}
	
	
	/** Metodo che restituisce un oggetto di tipo Document,
	 * che rappresenta l'albero del documento XSD **/
	public static Document connect(){ 
		try {
			 
			File fXmlFile = new File(Constants.getXSD_name_file());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setIgnoringComments(true);
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		 
			doc.getDocumentElement().normalize();
			
		
		    } catch (Exception e) {
				e.printStackTrace();
			    }
		
		return doc;
	}
	
	/** Metodo che restituisce un oggetto di tipo Document,
	 * che rappresenta l'albero del documento XML passatogli come parametro di input 
	**/
	public static Document connect(String file){
		try{
			File xmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setIgnoringComments(true);
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return doc;
	}
	
	/** Metodo che restituisce un oggetto di tipo Transformer 
	 * sul documento xml/xsd passatogli come parametro di input,
	 * per "confermare" le modifiche eseguite su tale file. **/
	public static Transformer confirm(String file){
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1"); //// PER INDENTARE!!
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		    
			StreamResult result = new StreamResult(file);
			DOMSource source = new DOMSource(doc);
			
			transformer.transform(source, result);	
			
			Constants.setDomTheoryModified(true);

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return transformer;
	}
	
	
/*---------------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------ GETTERS ------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------------------*/	

    /** 
	 * Method for retrieving all the elements names from the XSD file
	**/
	public static Vector<String> getAllSchemaNames(boolean lowerCase){
		Vector<String> allNames = new Vector<String>();
		try{
			
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getChildNodes();
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType() == Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(!lowerCase)					
						allNames.add(eN.getAttribute("name"));
					else
						allNames.add(eN.getAttribute("name").toLowerCase());
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return allNames;
	
	}
	
	/** 
	 * Method for retrieving all the elements names from the 'repository.xml' file
	**/
	public static Vector<String> getAllRepositoryNames(boolean lowerCase){
		Vector<String> allNames = new Vector<String>();
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getChildNodes();
			for(int index=0;index<rChilds.getLength();index++){
				Node rC = rChilds.item(index);
				if(rC.getNodeType()==Element.ELEMENT_NODE){
					Element eC = (Element)rC;
					if(!lowerCase)					
						allNames.add(eC.getAttribute("name"));
					else
						allNames.add(eC.getAttribute("name").toLowerCase());
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return allNames;
	}
	
	
	/** 
	 * Method for retrieving the list of existing services from the XSD file.
	 * If 'lowerCase' is equal to TRUE, each service is provided in lower case format (useful for comparisons). 
	**/	
	public static Vector<String> getServices(boolean lowerCase){
		
		Vector<String> services_vector = new Vector<String>();
		
		Document doc = XMLParser.connect();
		
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for(int temp=0; temp<nList.getLength();temp++){
			
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
			
				if (eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && eElement.getAttribute("name").equalsIgnoreCase("service")){
					
					NodeList participantsList = eElement.getElementsByTagName("xs:enumeration");
					
					for (int x = 0; x < participantsList.getLength(); x++) {
						
						Element pElement = (Element)participantsList.item(x);
						if(lowerCase==false)
							services_vector.addElement(pElement.getAttribute("value"));
						else
							services_vector.addElement(pElement.getAttribute("value").toLowerCase());
					}
				}
				
			}
		}
		return services_vector;
		
	}	
	
	/** 
	 * Method for retrieving the list of existing providers from the XSD file.
	 * It returns a Vector of Vectors of Strings. Each component of the main Vector is a Vector of String. 
	 * The first component of the internal vector is the name of the provider, while the other components are the 
	 * services associated to the provider.
	**/
	public static Vector<Vector<String>> getProviders(boolean lowerCase){  
		
		Vector<String> roles = new Vector<String>();  
		Vector<Vector<String>> res = new Vector<Vector<String>>();
		String app_role="";
		
		Document doc = XMLParser.connect();
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
								
				NodeList childs_eElement = eElement.getElementsByTagName("xs:restriction");
				
				for(int index=0; index<childs_eElement.getLength(); index++){
					
					Node nchild_eElement = (Element) childs_eElement.item(index);
					
					if(nchild_eElement.getNodeType() == Node.ELEMENT_NODE){
						
						Element echild_eElement = (Element) nchild_eElement;
						
						if(eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && echild_eElement.getAttribute("base").equalsIgnoreCase("service")){							
							Node roles_node = echild_eElement.getParentNode();							
							if(roles_node.getNodeType() == Node.ELEMENT_NODE){
								
								Element roles_eElement = (Element) roles_node;
								app_role = roles_eElement.getAttribute("name");
								if(!lowerCase) roles.add(app_role);
								else roles.add(app_role.toLowerCase());
								
								Vector<String> r = new Vector<String>();
								if(!lowerCase) 
									r.add(0, roles_eElement.getAttribute("name"));
								else
									r.add(0, roles_eElement.getAttribute("name").toLowerCase());
								
								for(int y=0; y<roles.size();y++){
									
									if(roles_eElement.getAttribute("name").equalsIgnoreCase(roles.get(y))){
										NodeList figliList = roles_eElement.getElementsByTagName("xs:enumeration");
										for (int  in= 0; in<figliList.getLength(); in++) {
											
											Element figliElement = (Element)figliList.item(in);

											if(!lowerCase) 
												r.addElement(figliElement.getAttribute("value"));
											else
												r.addElement(figliElement.getAttribute("value").toLowerCase());
										}
										
									}
									
									
								}
								res.addElement(r);
									 
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		return res;
	}
	
	/** 
	 * Method for retrieving the list of names of the existing providers from the XSD file.
	 * It returns a Vector of Strings. Each component of the main Vector is a String corresponding to the name of the provider.
	 * If 'lowerCase' is equal to TRUE, each provider is returned in lower case format (useful for comparisons).
	**/
	public static Vector<String> getProvidersNames(boolean lowerCase){  
		
		Vector<String> providers_vector = new Vector<String>();  
		
		Document doc = XMLParser.connect();
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
								
				NodeList childs_eElement = eElement.getElementsByTagName("xs:restriction");
				
				for(int index=0; index<childs_eElement.getLength(); index++){
					
					Node nchild_eElement = (Element) childs_eElement.item(index);
					
					if(nchild_eElement.getNodeType() == Node.ELEMENT_NODE){
						
						Element echild_eElement = (Element) nchild_eElement;
						
						if(eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && echild_eElement.getAttribute("base").equalsIgnoreCase("service")){							
							Node roles_node = echild_eElement.getParentNode();							
							if(roles_node.getNodeType() == Node.ELEMENT_NODE){								
								Element roles_eElement = (Element) roles_node;
								if(lowerCase==false)
									providers_vector.addElement(roles_eElement.getAttribute("name"));
								else
									providers_vector.addElement(roles_eElement.getAttribute("name").toLowerCase());
									 
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		return providers_vector;
	}
	
	/**
	 *  Method for retrieving the list of capabilities from the XSD file.
	 *  If 'lowerCase' is equal to TRUE, each capability is returned in lower case format (useful for comparisons). 
	**/ 
	public static Vector<String> getCapabilities(boolean lowerCase){
		
		Vector<String> capabilities_vector = new Vector<String>();
		
		Document doc = XMLParser.connect();
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for(int temp=0; temp<nList.getLength();temp++){
			
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element) nNode;
				
				if (eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && eElement.getAttribute("name").equalsIgnoreCase("Capability")){
					
					NodeList capabilitiesList = eElement.getElementsByTagName("xs:enumeration");
					
					for (int x = 0; x < capabilitiesList.getLength(); x++) {
						
						Element cElement = (Element)capabilitiesList.item(x);
						
						if(lowerCase==false)
							capabilities_vector.addElement(cElement.getAttribute("value"));
						else
							capabilities_vector.addElement(cElement.getAttribute("value").toLowerCase());						
					}
			
				}
		
		
			}
			
		}
		return capabilities_vector;
		
	}
	
	/** 
	 * Method for retrieving the list of associations between services and capabilities (it basically returns the list of 'provides' predicates).
	 * It returns a Vector of Vectors of Strings. Each component of the main Vector is a Vector of String. 
	 * The first component of each internal vector is the name of a service, while the second component is the 
	 * capability provided by the service.
	**/
	public static Vector<Vector<String>> getAssociationsBetweenServicesAndCapabilities(boolean lowerCase){
		
		Vector<Vector<String>> services_capabilities_vector = new Vector<Vector<String>>();
		System.out.println(Constants.getXML_name_file());
		Document doc = XMLParser.connect(Constants.getXML_name_file());
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for(int temp=0; temp<nList.getLength();temp++){
			
			Node nNode = nList.item(temp);
			
			Vector provides_vector = new Vector();
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element) nNode;
				
				if (eElement.getNodeName().equalsIgnoreCase("provides") && eElement.getAttribute("value").equalsIgnoreCase("true")){
										
					NodeList nodeList = eElement.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
					    Node childNode = nodeList.item(j);
					    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					        if(!lowerCase)	
					        	provides_vector.addElement(childNode.getFirstChild().getNodeValue());
					        else
					        	provides_vector.addElement(childNode.getFirstChild().getNodeValue().toLowerCase());
					    }
					}

					services_capabilities_vector.addElement(provides_vector);
			
				}
		
		
			}
			
		}
		return services_capabilities_vector;
	}
	
	/** 
	 * Method for retrieving the list of capabilities provided by a specific service.
	 * It returns a Vector of Strings, representing the capabilities provided by the service.
	**/
	public static Vector<String> getCapabilitiesProvidedByService(String service){
		
		Vector<String> service_capabilities_vector = new Vector<String>();
		
		Document doc = XMLParser.connect(Constants.getXML_name_file());
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for(int temp=0; temp<nList.getLength();temp++){
			
			Node nNode = nList.item(temp);
			
			Vector provides_vector = new Vector();
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element) nNode;
				
				if (eElement.getNodeName().equalsIgnoreCase("provides") && eElement.getAttribute("value").equalsIgnoreCase("true")){
					
					boolean isMyCapability=false;					
					
					NodeList nodeList = eElement.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
					    Node childNode = nodeList.item(j);
					    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					    	if(childNode.getNodeName().equalsIgnoreCase("service") && childNode.getFirstChild().getNodeValue().equalsIgnoreCase(service))
					        	isMyCapability=true;
					    	if(childNode.getNodeName().equalsIgnoreCase("capability") && isMyCapability)
					    		service_capabilities_vector.addElement(childNode.getFirstChild().getNodeValue());
					    }
					}

			
				}

			}
			
		}
		return service_capabilities_vector;
	}
	
	/** 
	 * Method for retrieving the list of associations between tasks and capabilities (it basically returns the list of 'requires' predicates).
	 * It returns a Vector of Vectors of Strings. Each component of the main Vector is a Vector of String. 
	 * The first component of each internal vector is the name of a task, while the second component is the 
	 * capability required by the task.
	**/
	public static Vector<Vector<String>> getAssociationsBetweenTasksAndCapabilities(boolean lowerCase){
		
		Vector<Vector<String>> tasks_capabilities_vector = new Vector<Vector<String>>();
		
		Document doc = XMLParser.connect(Constants.getXML_name_file());
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for(int temp=0; temp<nList.getLength();temp++){
			
			Node nNode = nList.item(temp);
			
			Vector requires_vector = new Vector();
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element) nNode;
				
				if (eElement.getNodeName().equalsIgnoreCase("requires") && eElement.getAttribute("value").equalsIgnoreCase("true")){
										
					NodeList nodeList = eElement.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
					    Node childNode = nodeList.item(j);
					    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					        if(!lowerCase)	
					        	requires_vector.addElement(childNode.getFirstChild().getNodeValue());
					        else
					        	requires_vector.addElement(childNode.getFirstChild().getNodeValue().toLowerCase());
					    }
					}
					tasks_capabilities_vector.addElement(requires_vector);
				}
			}
		}

		return tasks_capabilities_vector;
	}
	
	
	/** 
	 * Method for retrieving the list of capabilities required by a specific task.
	 * It returns a Vector of Strings, representing the capabilities required by the task.
	**/
	public static Vector<String> getCapabilitiesRequiredByTask(String task){
		
		Vector<String> task_capabilities_vector = new Vector<String>();
		
		Document doc = XMLParser.connect(Constants.getXML_name_file());
		Element root = doc.getDocumentElement();
		NodeList nList = root.getChildNodes();
		
		for(int temp=0; temp<nList.getLength();temp++){
			
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element) nNode;
				
				if (eElement.getNodeName().equalsIgnoreCase("requires") && eElement.getAttribute("value").equalsIgnoreCase("true")){
					
					boolean isMyCapability=false;					
					
					NodeList nodeList = eElement.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
					    Node childNode = nodeList.item(j);
					    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					    	if(childNode.getNodeName().equalsIgnoreCase("task") && childNode.getFirstChild().getNodeValue().equalsIgnoreCase(task))
					        	isMyCapability=true;
					    	if(childNode.getNodeName().equalsIgnoreCase("capability") && isMyCapability)
					    		task_capabilities_vector.addElement(childNode.getFirstChild().getNodeValue());
					    }
					}

			
				}
		
		
			}
			
		}
		return task_capabilities_vector;
	}
	


	/** Method for retrieving the list of user-defined data types from the XSD file.
	 * It returns a Vector of Vectors of Strings. Each component of the main Vector is a Vector of String. 
	 * The first component of each internal vector is the name of the data type, while the other components are the 
	 * data objects associated with the specific data type, e.g., [data_type_name,data_obj1,data_obj2...] 
	**/	
	public static Vector<Vector<String>> getUserDataTypes(boolean lowerCase){
		
		Vector<Vector<String>> dataTypes_vector = new Vector<Vector<String>>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList nChilds = root.getChildNodes();
		for(int index=0;index<nChilds.getLength();index++){
			Node nChild = nChilds.item(index);
			if(nChild.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)nChild;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("Integer_type") && !eChild.getAttribute("name").equalsIgnoreCase("Boolean_type")){
					Vector<String> dtp = new Vector<String>();
					if(!lowerCase)
						dtp.add(eChild.getAttribute("name"));
					else
						dtp.add(eChild.getAttribute("name").toLowerCase());
					
					NodeList objList = eChild.getElementsByTagName("xs:enumeration");
					for(int index1=0;index1<objList.getLength();index1++){
						Node objNode = objList.item(index1);
						if(objNode.getNodeType() == Element.ELEMENT_NODE){
							Element objElement = (Element)objNode;
							if(!lowerCase)
								dtp.add(objElement.getAttribute("value"));
							else
								dtp.add(objElement.getAttribute("value").toLowerCase());
						}
					}
					dataTypes_vector.add(dtp);				
				}
			}
		}
		return dataTypes_vector;
	}
	
	/** 
	 * Method for retrieving from the XSD file the names of the user-defined data types.
	 * It returns a Vector of Strings. Each component of Vector is a String with the name of the user defined data type.
	**/	
	public static Vector<String> getUserDataTypeNames(){
		
		Vector<String> data_type_names_vector = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList nChilds = root.getChildNodes();
		for(int index=0;index<nChilds.getLength();index++){
			Node nChild = nChilds.item(index);
			if(nChild.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)nChild;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("Integer_type") && !eChild.getAttribute("name").equalsIgnoreCase("Boolean_type")){
					data_type_names_vector.add(eChild.getAttribute("name"));
				}
		}
		}
		return data_type_names_vector;
	}
	
	/** Method for retrieving the maximum and minimum bounds of an Integer data type.
	 * It returns a Vector of Strings. The first and the second component of the vector represent respectively the minimum and maximum bound.
	**/	
	public static Vector<String> getIntegerType(){
			
			Vector<String> intBounds = new Vector<String>();
			
			Document doc = connect();
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			for(int index=0; index<nList.getLength(); index++){
				Node nNode = nList.item(index);
				if(nNode.getNodeType() == Element.ELEMENT_NODE){
					Element eNode = (Element) nNode;
					if(eNode.getNodeName().equalsIgnoreCase("xs:simpleType") && eNode.getAttribute("name").equalsIgnoreCase("Integer_type")){
						Node intNode = eNode;
						NodeList nL = intNode.getChildNodes();
						for(int index1=0;index1<nL.getLength();index1++){
							Node nRestr = nL.item(index1);
							NodeList values = nRestr.getChildNodes();
							for(int index2=0;index2<values.getLength();index2++){
								Node v = values.item(index2);
								if(v.getNodeType()==Element.ELEMENT_NODE){
									Element eV = (Element)v;
									intBounds.add(eV.getAttribute("value"));
								}
							}
						}
						
						
					}
				
				}
			}
			return intBounds;
	}
	
	/** 
	 * It returns a method containing the data objects corresponding to the data type passed as input argument.
	 * This method works also for particular data types like Service, Capability and any of the Provider. 
	 * For example, if the data type in input is a Boolean_Type, the method returns a Vector with two components (one storing the TRUE value, 
	 * the second one storing the FALSE value). 
	 * If "SRVC == true", then (if type=="Service") the Service 'SRVC' (representing the service that will be assigned to execute a task at run time)	will be added 
	 * to the list of available services.
	**/		
	public static Vector<String> getDataObjectsOf(String type, boolean SRVC){
	Vector<String> objs = new Vector<String>();
		
		if(type.equalsIgnoreCase("Boolean_type")){
			objs.add("false");
			objs.add("true");
		}
		else if(type.equalsIgnoreCase("Integer_type")){
			int minBound = Integer.parseInt(getIntegerType().get(0));
			int maxBound = Integer.parseInt(getIntegerType().get(1));
			for(int i=minBound;i<=maxBound;i++){
				objs.add(String.valueOf(i));
			}
			
		}
		else if(type.equalsIgnoreCase("service")){ 
			objs.addAll(getServices(false));
			if(SRVC)
			objs.add("SRVC");
		}
		else{ //dovrebbe entrare qui solo in caso di "role_type","location_type" e "status_type"
			Document doc = connect();
			Element root  = doc.getDocumentElement();
			NodeList rChilds = root.getChildNodes();
			for(int index=0;index<rChilds.getLength();index++){
				Node rNode = rChilds.item(index);
				if(rNode.getNodeType() == Element.ELEMENT_NODE){
					Element eNode = (Element)rNode;
					if(eNode.getAttribute("type").equalsIgnoreCase("role") && eNode.getAttribute("name").equalsIgnoreCase(type)){ // se sono role_type
						NodeList elements = eNode.getElementsByTagName("xs:enumeration");
						for(int index1=0;index1<elements.getLength();index1++){
							Node el = elements.item(index1);
							if(el.getNodeType() == Element.ELEMENT_NODE){
								Element eEl = (Element)el;
								objs.add(eEl.getAttribute("value"));
								
							}
						}
						objs.add("SRVC");
					}
					else if (eNode.getAttribute("name").equalsIgnoreCase(type)){ // non sono role_type
						NodeList els = eNode.getElementsByTagName("xs:enumeration");
						for(int index2=0;index2<els.getLength();index2++){
							Node em = els.item(index2);
							if(em.getNodeType() == Element.ELEMENT_NODE){
								Element eE = (Element)em;
								objs.add(eE.getAttribute("value"));
							}
						}
					}
				}
			
			}
		}
		 
		return objs;
	}
	
/*----------------------------------------------------------------------- Atomic Terms -------------------------------------------------------------------*/
	
	/** 
	 * Method for retrieving the name of all (static or dynamic) atomic terms from the XSD file 
	**/
	public static Vector<String> getAllAtomicTermsName(boolean lowerCase){
		Vector<String> at_terms = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)child;
				if(eChild.getAttribute("id").contains("dynamic_term-") || eChild.getAttribute("id").contains("static_term-")){
					if(!lowerCase)
						at_terms.add(eChild.getAttribute("name"));
					else
						at_terms.add(eChild.getAttribute("name").toLowerCase());
				}
			}
		}
		return at_terms;
	}
	
	/** 
	 * Method for retrieving the name of all dynamic atomic terms from the XSD file 
	**/
	public static Vector<String> getAllDynamicTermsName(){
		Vector<String> at_terms = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)child;
				if(eChild.getAttribute("id").contains("dynamic_term-")){
					at_terms.add(eChild.getAttribute("name"));
				}
			}
		}
		return at_terms;
	}
	
	/** 
	 * Method for retrieving the name of all static atomic terms from the XSD file 
	**/
	public static Vector<String> getAllStaticTermsName(){
		Vector<String> at_terms = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)child;
				if(eChild.getAttribute("id").contains("static_term-")){
					at_terms.add(eChild.getAttribute("name"));
				}
			}
		}
		return at_terms;
	}
	
	/** 
	 * Method for retrieving:
	 * - if the atomic term is Static/Dynamic (first component of the vector)
	 * - if the atomic term is Dynamic ---> it returns the value of "relevant" (TRUE or FALSE if it is relevant or not. This value is recorded in the second component of the vector).
	 * If the atomic term is a "static_term", then the information describing if it is "relevant" or not is not returned (therefore, the vector contains only the first component)
	**/
	public static Vector<String> getIfAtomicTermIsRelevant(String at_name){
		Vector<String> detOfAt = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getChildNodes();
			for(int index=0;index<rChilds.getLength();index++){
				Node child = rChilds.item(index);
				if(child.getNodeType() == Element.ELEMENT_NODE){
					Element eChild = (Element)child;
					if(eChild.getAttribute("name").equalsIgnoreCase(at_name) && (eChild.getAttribute("id").contains("dynamic_term-") || eChild.getAttribute("id").contains("static_term-"))){
						NodeList attr_list = eChild.getElementsByTagName("xs:attribute");
						// DYNAMIC/STATIC
						detOfAt.add(eChild.getAttribute("id"));
						if(eChild.getAttribute("id").contains("dynamic_term-")){ // if dynamic_term --> retrieve the "relevant" element
							for(int index2=0;index2<attr_list.getLength();index2++){
								Node attr = attr_list.item(index2);
								if(attr.getNodeType() == Element.ELEMENT_NODE){
									Element eAttr = (Element)attr;
									if(eAttr.getAttribute("name").equalsIgnoreCase("relevant")){
										detOfAt.add(eAttr.getAttribute("default"));
									}
								}
							}
						}
						else{ // if the term is "static", the element "relevant" is not returned.
						}
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return detOfAt;
	}
	
	/** 
	 * Method for retrieving the names and the types of parameters passed as input to a specific atomic term. 
	 * It returns a Vector of Vectors of Strings. Each internal component of the main vector is a vector containing 
	 * the name (first component of the internal vector) and the type (second component of the internal vector) of 
	 * an input parameter of the atomic term. Basically, to each input parameter of an atomic term corresponds an internal vector indicating 
	 * its name and its type.
    **/
	public static Vector<Vector<String>> getAtomicTermParametersNameAndType(String name_at){
		Vector<Vector<String>> inParams = new Vector<Vector<String>>();
		Vector<String> app_vec;
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getChildNodes();
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType() == Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(name_at) && (eN.getAttribute("id").contains("dynamic_term-") || eN.getAttribute("id").contains("static_term-"))){
						NodeList seq = eN.getElementsByTagName("xs:sequence");
						for(int index1=0;index1<seq.getLength();index1++){
							Node s = seq.item(index1);
							if(s.getNodeType() == Element.ELEMENT_NODE){
								Element eS = (Element)s;
								NodeList arg_lis = eS.getChildNodes();
								for(int index2=0;index2<arg_lis.getLength();index2++){
									Node arg = arg_lis.item(index2);
									if(arg.getNodeType() == Element.ELEMENT_NODE){
										Element eArg = (Element)arg;
										app_vec = new Vector<String>();
										app_vec.add(eArg.getAttribute("name"));
										app_vec.add(eArg.getAttribute("type"));
										
										inParams.add(app_vec);
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return inParams;
	}
	
	/** 
	 * Method for retrieving the return type of a specific atomic term. 
	**/
	public static String getAtomicTermReturnType(String at,boolean lowerCase){

		String ret_value = new String();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rChilds = root.getElementsByTagName("xs:element");
		for(int index=0;index<rChilds.getLength();index++){
			Node rChild = rChilds.item(index);
			if(rChild.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)rChild;
				if(eChild.getAttribute("name").equalsIgnoreCase(at) && (eChild.getAttribute("id").contains("dynamic_term-") || eChild.getAttribute("id").contains("static_term-"))){
					if(eChild.getAttribute("name").equalsIgnoreCase(at)){
						NodeList seqNode = eChild.getElementsByTagName("xs:sequence");
						
						NodeList outParams = eChild.getElementsByTagName("xs:attribute");
						for(int index3=0;index3<outParams.getLength();index3++){
							Node outV = outParams.item(index3);
							if(outV.getNodeType() == Element.ELEMENT_NODE){
								Element eOut = (Element)outV;
								if(eOut.getAttribute("name").equalsIgnoreCase("value")){
									if(!lowerCase)
										ret_value = eOut.getAttribute("type");
									else
										ret_value = eOut.getAttribute("type").toLowerCase();
								}
							}
						}
					}
				}
			}
		}
		return ret_value;	
	}
	
	/** 
	 * Method for retrieving the return value and the types of parameters passed as input to a specific atomic term. 
	 * It returns a Vector of Vectors of Strings. The first component of the main vector is a vector containing the types of input parameters, 
	 * while the second component of the main vector is a vector with a single component, corresponding to the type of the return value returned by the atomic term.
    **/
	public static Vector<Vector<String>> getAtomicTermParametersTypesAndReturnType(String at){
		Vector<Vector<String>> details = new Vector<Vector<String>>();
		Vector<String>  input_params = new Vector<String>();
		Vector<String>  ret_values = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rChilds = root.getElementsByTagName("xs:element");
		for(int index=0;index<rChilds.getLength();index++){
			Node rChild = rChilds.item(index);
			if(rChild.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)rChild;
				if(eChild.getAttribute("id").contains("dynamic_term-") || eChild.getAttribute("id").contains("static_term-")){
					if(eChild.getAttribute("name").equalsIgnoreCase(at)){
						NodeList seqNode = eChild.getElementsByTagName("xs:sequence");
						for(int index1=0;index1<seqNode.getLength();index1++){
							Node sequence = seqNode.item(index1);
							if(sequence.getNodeType() == Element.ELEMENT_NODE){
								Element eSequence = (Element)sequence;
								NodeList inputParams = eSequence.getElementsByTagName("xs:element");
								for(int index2=0;index2<inputParams.getLength();index2++){
									Node par = inputParams.item(index2);
									if(par.getNodeType() == Element.ELEMENT_NODE){
										Element ePar = (Element)par;
										//System.out.println(ePar.getAttribute("type")); // OK
										input_params.add(ePar.getAttribute("type"));
									}
								}
							}
						}
						//System.out.println(input_params); //OK
						details.add(input_params);
						
						NodeList outParams = eChild.getElementsByTagName("xs:attribute");
						for(int index3=0;index3<outParams.getLength();index3++){
							Node outV = outParams.item(index3);
							if(outV.getNodeType() == Element.ELEMENT_NODE){
								Element eOut = (Element)outV;
								if(eOut.getAttribute("name").equalsIgnoreCase("value")){
									ret_values.add(eOut.getAttribute("type"));
								}
							}
						}
						details.add(ret_values);
					}
				}
			}
		}

		return details;	
	}
	
	
	/** 
	 * Method for returning the name of all the admissible data types (including Service, Capability and single Providers) 
	 * that can be associated to each argument of any atomic term.
	**/ 
	public static Vector<String> getRelevantTypesForArgumentsOfAtomicTerms(){
		
		Vector<String> allTypes = new Vector<String>();
		String app = null;
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element) child;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && 
						(eChild.getAttribute("name").equalsIgnoreCase("service") || eChild.getAttribute("name").equalsIgnoreCase("capability") || 
								(eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("Integer_type") && !eChild.getAttribute("name").equalsIgnoreCase("Boolean_type") ||
										(!eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("task_repository") && !eChild.getAttribute("name").equalsIgnoreCase("exogenous_event_repository") && !eChild.getAttribute("name").equalsIgnoreCase("formulae_repository"))))){
					app=eChild.getAttribute("name");
					allTypes.add(app);
				}
			}
		}
		return allTypes;
	}
	
	/** 
	 * Method for returning the name of all the admissible data types (including Service, Capability and single Providers) 
	 * that can be associated to the return type of any atomic term.
	**/ 
	public static Vector<String> getRelevantTypesForReturnValueOfAtomicTerms(){
		
		Vector<String> allTypes = new Vector<String>();
		String app = null;
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element) child;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && 
						(eChild.getAttribute("name").equalsIgnoreCase("service") || eChild.getAttribute("name").equalsIgnoreCase("capability") || eChild.getAttribute("name").contains("_type") ||
										(!eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("task_repository") && !eChild.getAttribute("name").equalsIgnoreCase("exogenous_event_repository") && !eChild.getAttribute("name").equalsIgnoreCase("formulae_repository")))){
					app=eChild.getAttribute("name");
					allTypes.add(app);
				}
			}
		}
		return allTypes;
		
	}
	
	
/*----------------------------------------------------------------------- Tasks -------------------------------------------------------------------*/
	
	/** 
	 * Method for retrieving the list of task names from the XSD file.
	 * If 'lowerCase' is equal to TRUE, each task is provided in lower case format (useful for comparisons). 
	**/	
	public static Vector<String> getTaskNames(boolean lowerCase){
		Vector<String> tsks = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rChilds = root.getElementsByTagName("xs:simpleType");
		for(int index=0;index<rChilds.getLength();index++){
			Node nC = rChilds.item(index);
			if(nC.getNodeType()==Element.ELEMENT_NODE){
				Element eC = (Element)nC;
				if(eC.getAttribute("name").equalsIgnoreCase("task_repository")){
					NodeList tList = eC.getElementsByTagName("xs:enumeration");
					for(int index1=0;index1<tList.getLength();index1++){
						Node t = tList.item(index1);
						if(t.getNodeType()==Element.ELEMENT_NODE){
							Element eT = (Element)t;
							
							if(!lowerCase)
								tsks.add(eT.getAttribute("value"));
							else
								tsks.add(eT.getAttribute("value").toLowerCase());
						}
					}
				}
			}
		}
		return tsks;
	}
	
	/** 
	 * Method for returning the name of all the admissible data types (including Service, Capability and single Providers) 
	 * that can be associated to each argument of any task.
	**/ 
	public static Vector<String> getRelevantTypesForArgumentsOfTasks(){
		
		Vector<String> allTypes = new Vector<String>();
		String app = null;
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element) child;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && 
						(eChild.getAttribute("name").equalsIgnoreCase("service") || eChild.getAttribute("name").equalsIgnoreCase("capability") || 
								(eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("Integer_type") && !eChild.getAttribute("name").equalsIgnoreCase("Boolean_type") ||
										(!eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("task_repository") && !eChild.getAttribute("name").equalsIgnoreCase("exogenous_event_repository") && !eChild.getAttribute("name").equalsIgnoreCase("formulae_repository"))))){
					app=eChild.getAttribute("name");
					allTypes.add(app);
				}
			}
		}
		return allTypes;
	}
	
	/** 
	 * Method returning the input parameters of a specific task; specifically, a Vector of Vectors of Strings.
	 * Each internal vector corresponds to a single parameter associated to a specific task. The first component of 
	 * each internal vector is the name of the parameter, while the second component is its type. 
	**/	
	public static Vector<Vector<String>> getInputParametersOfTask(String taskname,boolean lowerCase){
		Vector<Vector<String>> inArgs = new Vector<Vector<String>>();
		Vector<String> app_vec = null;
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType()==Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(taskname)){ 
						NodeList args = eN.getElementsByTagName("argument"); // argomenti in input al task
						for(int index1=0;index1<args.getLength();index1++){
							app_vec = new Vector<String>();
							Node argm = args.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								String[] tokens = arg_string.split(" - ");
								for(int y=0;y<tokens.length;y++){
									String app = tokens[y];
									if(!lowerCase)
										app_vec.add(app);
									else
										app_vec.add(app.toLowerCase());
								}
								inArgs.add(app_vec);
							}
							
						}
						
					}
				}
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return inArgs;
		
	}
	
	/** 
	 * Method returning the types of the input parameters of a specific task; specifically, a Vector of Strings.
	**/	
	public static Vector<String> getInputParameterTypesOfTask(String taskname){
		Vector <String> inArgs = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType()==Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(taskname)){ 
						NodeList args = eN.getElementsByTagName("argument"); // argomenti in input al task
						for(int index1=0;index1<args.getLength();index1++){
							Node argm = args.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								String[] tokens = arg_string.split(" - ");
								for(int y=1;y<tokens.length;y++){
									String app = tokens[y];
									inArgs.add(app);
									
								}
								
							}
							
						}
						
					}
				}
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return inArgs;
		
	}
	
	/** 
	 * Method returning the names of the input parameters of a specific task; specifically, a Vector of Strings.
	**/	
	public static Vector<String> getInputParameterNamesOfTask(String taskname){
		Vector <String> inArgs = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType()==Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(taskname)){ 
						NodeList args = eN.getElementsByTagName("argument"); // argomenti in input al task
						for(int index1=0;index1<args.getLength();index1++){
							Node argm = args.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								String[] tokens = arg_string.split(" - ");
								for(int y=0;y<tokens.length-1;y++){
									String app = tokens[y];
									inArgs.add(app);
								}
							}
						}
					}
				}
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return inArgs;
	}
	
	/** 
	 * Method returning a Vector of Vectors of Strings with, respectively:
	 * - the list of task preconditions (first internal vector);
	 * - the list of supposed effects (second internal vector);
	 * - the list of automatic effects (third internal vector);
	**/
	public static Vector<Vector<String>> getTaskDetails(String task_name){
		Vector<Vector<String>> details = new Vector<Vector<String>>();
		
		Vector<String> preconditions = new Vector<String>();
		Vector<String> supp_effects = new Vector<String>();
		Vector<String> auto_effects = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node nChild = rChilds.item(index);
				if(nChild.getNodeType()==Element.ELEMENT_NODE){
					Element eChild = (Element)nChild;
					if(eChild.getAttribute("name").equalsIgnoreCase(task_name)){
						
						NodeList precs = eChild.getElementsByTagName("precondition"); // precondizioni del task
						for(int index2=0;index2<precs.getLength();index2++){
							Node pr = precs.item(index2);
							if(pr.getNodeType()== Element.ELEMENT_NODE){
								Element ePr = (Element)pr;
								preconditions.add(ePr.getTextContent());
							}
						}
						//System.out.println(preconditions);
						details.add(preconditions);
						
						NodeList s_eff = eChild.getElementsByTagName("supposed-effect"); // supposed-effects del task
						for(int index3=0;index3<s_eff.getLength();index3++){
							Node sEf = s_eff.item(index3);
							if(sEf.getNodeType()==Element.ELEMENT_NODE){
								Element eSEf = (Element)sEf;
								supp_effects.add(eSEf.getTextContent());
							}
						}
						//System.out.println(supp_effects);
						details.add(supp_effects);
						
						NodeList a_eff = eChild.getElementsByTagName("automatic-effect"); // automatic-effects del task
						for(int index4=0;index4<a_eff.getLength();index4++){
							Node aEf = a_eff.item(index4);
							if(aEf.getNodeType()== Element.ELEMENT_NODE){
								Element eAEf = (Element)aEf;
								auto_effects.add(eAEf.getTextContent());
							}
						}
						//System.out.println(auto_effects);
						details.add(auto_effects);
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		return details;
	}
	
	/** 
	 * Method returning a Vector of Strings with the preconditions of a specific task.
	 * Each String is a task precondition.
	**/
	public static Vector<String> getTaskPreconditions(String task_name){
		
		Vector<String> preconditions = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node nChild = rChilds.item(index);
				if(nChild.getNodeType()==Element.ELEMENT_NODE){
					Element eChild = (Element)nChild;
					if(eChild.getAttribute("name").equalsIgnoreCase(task_name)){
						
						NodeList precs = eChild.getElementsByTagName("precondition"); // precondizioni del task
						for(int index2=0;index2<precs.getLength();index2++){
							Node pr = precs.item(index2);
							if(pr.getNodeType()== Element.ELEMENT_NODE){
								Element ePr = (Element)pr;
								preconditions.add(ePr.getTextContent());
							}
						}
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		return preconditions;
	}
	
	/** 
	 * Method returning a Vector of Strings with the supposed and automatic effects of a specific task.
	 * Each String is a supposed or automatic effect.
	**/
	public static Vector<String> getTaskEffects(String task_name){
		
		Vector<String> effects_vector = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node nChild = rChilds.item(index);
				if(nChild.getNodeType()==Element.ELEMENT_NODE){
					Element eChild = (Element)nChild;
					if(eChild.getAttribute("name").equalsIgnoreCase(task_name)){
						
						NodeList s_eff = eChild.getElementsByTagName("supposed-effect"); // supposed-effects del task
						for(int index3=0;index3<s_eff.getLength();index3++){
							Node sEf = s_eff.item(index3);
							if(sEf.getNodeType()==Element.ELEMENT_NODE){
								Element eSEf = (Element)sEf;
								if (!eSEf.getTextContent().isEmpty())
									effects_vector.add(eSEf.getTextContent());
							}
						}
						
						NodeList a_eff = eChild.getElementsByTagName("automatic-effect"); // supposed-effects del task
						for(int index3=0;index3<a_eff.getLength();index3++){
							Node sEf = a_eff.item(index3);
							if(sEf.getNodeType()==Element.ELEMENT_NODE){
								Element eSEf = (Element)sEf;
								if (!eSEf.getTextContent().isEmpty())
									effects_vector.add(eSEf.getTextContent());
							}
						}
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		return effects_vector;
	}
	
	/** 
	 * Method returning a Vector of Strings with the supposed effects of a specific task.
	 * Each String is a supposed effect.
	**/
	public static Vector<String> getTaskSupposedEffects(String task_name,boolean lowerCase){
		
		Vector<String> supp_effects = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node nChild = rChilds.item(index);
				if(nChild.getNodeType()==Element.ELEMENT_NODE){
					Element eChild = (Element)nChild;
					if(eChild.getAttribute("name").equalsIgnoreCase(task_name)){
						
						NodeList s_eff = eChild.getElementsByTagName("supposed-effect"); // supposed-effects del task
						for(int index3=0;index3<s_eff.getLength();index3++){
							Node sEf = s_eff.item(index3);
							if(sEf.getNodeType()==Element.ELEMENT_NODE){
								Element eSEf = (Element)sEf;
								if (!eSEf.getTextContent().isEmpty())
									if(!lowerCase)
										supp_effects.add(eSEf.getTextContent());
									else
										supp_effects.add(eSEf.getTextContent().toLowerCase());
							}
						}
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		return supp_effects;
	}
	
	/** 
	 * Method returning a Vector of Strings with the automatic effects of a specific task.
	 * Each String is an automatic effect.
	**/
	public static Vector<String> getTaskAutomaticEffects(String task_name, boolean lowerCase){

		Vector<String> auto_effects = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("task_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node nChild = rChilds.item(index);
				if(nChild.getNodeType()==Element.ELEMENT_NODE){
					Element eChild = (Element)nChild;
					if(eChild.getAttribute("name").equalsIgnoreCase(task_name)){
						
						NodeList a_eff = eChild.getElementsByTagName("automatic-effect"); // automatic-effects del task
						for(int index4=0;index4<a_eff.getLength();index4++){
							Node aEf = a_eff.item(index4);
							if(aEf.getNodeType()== Element.ELEMENT_NODE){
								Element eAEf = (Element)aEf;
								if (!eAEf.getTextContent().isEmpty())
									if(!lowerCase)
										auto_effects.add(eAEf.getTextContent());
									else
										auto_effects.add(eAEf.getTextContent().toLowerCase());
							}
						}
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		return auto_effects;
	}
	
/*----------------------------------------------------------------------- Exogenous Events -------------------------------------------------------------------*/
	
	/** 
	 * Method returning all the names of the existing exogenous events (it collects the from the XSD file)
	**/
	public static Vector<String> getExogenousEventsName(){
		Vector<String> ex_evts = new Vector<String>();
		
		Document doc = connect(Constants.getXSD_name_file());
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getElementsByTagName("xs:simpleType");
		for(int index=0;index<rootChilds.getLength();index++){
			Node n = rootChilds.item(index);
			if(n.getNodeType() == Element.ELEMENT_NODE){
				Element eN = (Element)n;
				if(eN.getAttribute("name").equalsIgnoreCase("exogenous_event_repository")){
					NodeList eList = eN.getElementsByTagName("xs:enumeration");
					for(int index1=0;index1<eList.getLength();index1++){
						Node evEx = eList.item(index1);
						if(evEx.getNodeType() == Element.ELEMENT_NODE){
							Element evEx_elem = (Element)evEx;
							ex_evts.add(evEx_elem.getAttribute("value"));
						}
					}
				}
			}
		}
		return ex_evts;
	}
	
	/** 
	 * Method for returning the name of all the admissible data types (including Service, Capability and single Providers) 
	 * that can be associated to the arguments of any exogenous event.
	**/ 
	public static Vector<String> getRelevantTypesForArgumentsOfExogenousEvents(){
		
		Vector<String> allTypes = new Vector<String>();
		String app = null;
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element) child;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && 
						(eChild.getAttribute("name").equalsIgnoreCase("service") || eChild.getAttribute("name").equalsIgnoreCase("capability") || 
								(eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("Integer_type") && !eChild.getAttribute("name").equalsIgnoreCase("Boolean_type") ||
										(!eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("task_repository") && !eChild.getAttribute("name").equalsIgnoreCase("exogenous_event_repository") && !eChild.getAttribute("name").equalsIgnoreCase("formulae_repository"))))){
					app=eChild.getAttribute("name");
					allTypes.add(app);
				}
			}
		}
		return allTypes;
	}
	
	/** 
	 * Method returning the input parameters of a specific exogenous event; specifically, a Vector of Vectors of Strings.
	 * Each internal vector corresponds to a single parameter associated to a specific exogenous event. The first component of 
	 * each internal vector is the name of the parameter, while the second component is its type. 
	**/	
	public static Vector<Vector<String>> getInputParametersOfExogenousEvent(String eventname){
		Vector<Vector<String>> inArgs = new Vector<Vector<String>>();
		Vector<String> app_vec = null;
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("exogenous_event_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType()==Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(eventname)){ 
						NodeList args = eN.getElementsByTagName("argument"); // argomenti in input al task
						for(int index1=0;index1<args.getLength();index1++){
							app_vec = new Vector<String>();
							Node argm = args.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								String[] tokens = arg_string.split(" - ");
								for(int y=0;y<tokens.length;y++){
									String app = tokens[y];
									app_vec.add(app);
									
								}
								inArgs.add(app_vec);
							}	
						}	
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return inArgs;
		
	}
	
	/** 
	 * Method returning the effects of a specific exogenous event.
	**/
	public static Vector<String> getExogenousEventEffects(String ev){
		Vector<String> all_eff = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList event_list = root.getElementsByTagName("exogenous_event_definition");
			for(int index=0;index<event_list.getLength();index++){
				Node evnt = event_list.item(index);
				if(evnt.getNodeType() == Element.ELEMENT_NODE){
					Element eEvent = (Element)evnt;
					if(eEvent.getAttribute("name").equalsIgnoreCase(ev)){
						NodeList efcts = eEvent.getElementsByTagName("effect");
						for(int index1=0;index1<efcts.getLength();index1++){
							Node ef = efcts.item(index1);
							if(ef.getNodeType() == Element.ELEMENT_NODE){
								Element eEf = (Element)ef;
								all_eff.add(eEf.getTextContent());
							}
						}
					}
				}
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return all_eff;
	}
	

/*----------------------------------------------------------------------- Complex Formulae -------------------------------------------------------------------*/

	
	/** 
	 * Method which returns the names of the complex formulae as defined in the XSD file (under the "formulae_repository" element).
	**/
	public static Vector<String> getFormulae(){
		Vector<String> formulae = new Vector<String>();
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("xs:simpleType");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType() == Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase("formulae_repository")){
						NodeList fList = eN.getElementsByTagName("xs:enumeration");
						for(int index1=0;index1<fList.getLength();index1++){
							Node f = fList.item(index1);
							if(f.getNodeType() == Element.ELEMENT_NODE){
								Element eF = (Element)f;
								formulae.add(eF.getAttribute("value"));
							}
						}
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return formulae;		
	}
	
	/** 
	 * Method that returns a Vector containing all possible data types (taken from the XSD file) which can be used to define the arguments of a formula.
	**/
	public static Vector<String> getRelevantTypesForArgumentsOfFormulae(){
		
		Vector<String> allTypes = new Vector<String>();
		String app = null;
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element) child;
				if(eChild.getNodeName().equalsIgnoreCase("xs:simpleType") && 
						(eChild.getAttribute("name").equalsIgnoreCase("service") || eChild.getAttribute("name").equalsIgnoreCase("capability") || 
								(eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("Integer_type") && !eChild.getAttribute("name").equalsIgnoreCase("Boolean_type") ||
										(!eChild.getAttribute("name").contains("_type") && !eChild.getAttribute("name").equalsIgnoreCase("task_repository") && !eChild.getAttribute("name").equalsIgnoreCase("exogenous_event_repository") && !eChild.getAttribute("name").equalsIgnoreCase("formulae_repository"))))){
					app=eChild.getAttribute("name");
					allTypes.add(app);
				}
			}
		}
		return allTypes;
	}
	
	
	/** 
	 * Method returning the input parameters of a specific formula; specifically, a Vector of Vectors of Strings.
	 * Each internal vector corresponds to a single parameter associated to a specific formula. The first component of 
	 * each internal vector is the name of the parameter, while the second component is its type. 
	**/	
	public static Vector<Vector<String>> getInputParametersOfFormula(String formula){
		Vector<Vector<String>> inArgs = new Vector<Vector<String>>();
		Vector<String> app_vec = null;
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("formula_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType()==Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(formula)){ 
						NodeList args = eN.getElementsByTagName("argument");
						for(int index1=0;index1<args.getLength();index1++){
							app_vec = new Vector<String>();
							Node argm = args.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								String[] tokens = arg_string.split(" - ");
								for(int y=0;y<tokens.length;y++){
									String app = tokens[y];
									app_vec.add(app);
									
								}
								inArgs.add(app_vec);
							}	
						}	
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return inArgs;
		
	}
	
	/** 
	 * Method for retrieving the return value and the types of parameters passed as input to a specific formula. 
	 * It returns a Vector of Vectors of Strings. The first component of the main vector is a vector containing the types of input parameters, 
	 * while the second component of the main vector is a vector with a single component, corresponding to the type of the return value returned 
	 * by the formula (that is a Boolean_type).
    **/

	public static Vector<Vector<String>> getInputParametersTypesAndReturnTypeOfFormula(String formula){
		Vector<Vector<String>> inArgs = new Vector<Vector<String>>();
		Vector<String> app_vec = null;
		
		Vector return_type_vector = new Vector();
		
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList rChilds = root.getElementsByTagName("formula_definition");
			for(int index=0;index<rChilds.getLength();index++){
				Node n = rChilds.item(index);
				if(n.getNodeType()==Element.ELEMENT_NODE){
					Element eN = (Element)n;
					if(eN.getAttribute("name").equalsIgnoreCase(formula)){ 
						NodeList args = eN.getElementsByTagName("argument");
						app_vec = new Vector<String>();
						for(int index1=0;index1<args.getLength();index1++){
							
							Node argm = args.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								String[] tokens = arg_string.split(" - ");
								app_vec.add(tokens[1]);							
							}	
						}	
						inArgs.add(app_vec);
					}
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		Vector return_type = new Vector();
		return_type.addElement("Boolean_type");
		inArgs.add(return_type);
		
		return inArgs;
		
	}
	
	/** 
	 * Method returning the content of a formula passed as input.
	**/	
	public static String getFormulaContent(String formula){
		
		String content = new String("");
		try{
			Document doc = connect(Constants.getXML_name_file());
			Element root = doc.getDocumentElement();
			NodeList event_list = root.getElementsByTagName("formula_definition");
			for(int index=0;index<event_list.getLength();index++){
				Node evnt = event_list.item(index);
				if(evnt.getNodeType() == Element.ELEMENT_NODE){
					Element eEvent = (Element)evnt;
					if(eEvent.getAttribute("name").equalsIgnoreCase(formula)){
						NodeList efcts = eEvent.getElementsByTagName("content");
						for(int index1=0;index1<efcts.getLength();index1++){
							Node c = efcts.item(index1);
							if(c.getNodeType() == Element.ELEMENT_NODE){
								Element eC = (Element)c;
								content = eC.getTextContent();
							}
						}
					}
				}
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return content;
	}

	
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------- SETTERS -------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	/** 
	 * Method for adding a service to the XSD file.
	**/ 	
	public static void addService(String service){ 	
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			for(int index=0;index<nList.getLength();index++){
				Node nNode = nList.item(index);
				if(nNode.getNodeType() == Element.ELEMENT_NODE){
					Element eElem = (Element) nNode;
									
					if(eElem.getNodeName().equalsIgnoreCase("xs:simpleType") && eElem.getAttribute("name").equalsIgnoreCase("service")){
						
						NodeList el_childs = eElem.getElementsByTagName("xs:restriction");
				
						Element el_child = (Element) el_childs.item(0);
						Element el_part = (Element) doc.createElement("xs:enumeration");
						el_part.setAttributeNode(doc.createAttribute("value"));
						el_part.setAttribute("value", service);

						el_child.appendChild(el_part);
								
					}
					
				}
			} 
			confirm(Constants.getXSD_name_file());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * Method for removing a service from the XSD file.
	**/ 	
	public static void removeService(String service){ 		

		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();  //figli della radice
			for(int index=0;index<nList.getLength();index++){
				Node nNode = nList.item(index);
				if(nNode.getNodeType() == Element.ELEMENT_NODE){
					Element eElem = (Element) nNode;
					if(eElem.getAttribute("name").equalsIgnoreCase("service")){
						
						NodeList participantsList = eElem.getElementsByTagName("xs:enumeration");
						
						for (int x = 0; x < participantsList.getLength(); x++) {
							
							Element pElement = (Element)participantsList.item(x);
							Node pElementNode = (Node)participantsList.item(x);
							
							if(pElement.getAttribute("value").equalsIgnoreCase(service))
								pElement.getParentNode().removeChild(pElement);
							
						}
						
					}
					
				}
			} // chiude for index=0 --> ho eliminato tutto Participant_type
			confirm(Constants.getXSD_name_file());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * Method for adding a new provider (with a list of associated services) to the XSD file. 
	**/ 
	public static void addProvider(String new_Provider, Vector new_Provider_Services_vector) {
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();  
			 			
			Element el = (Element) doc.createElement("xs:simpleType");
			el.setAttributeNode(doc.createAttribute("name"));
			el.setAttribute("name",new_Provider);
			
			Element el_child = (Element) doc.createElement("xs:restriction");
			el_child.setAttributeNode(doc.createAttribute("base"));
			el_child.setAttribute("base", "Service");
				
			el.appendChild(el_child);
				
			for(int x=0;x<new_Provider_Services_vector.size();x++){
					
				Element el_part = (Element) doc.createElement("xs:enumeration");
				el_part.setAttributeNode(doc.createAttribute("value"));
				el_part.setAttribute("value", (String) new_Provider_Services_vector.elementAt(x));
				el_child.appendChild(el_part);
			}
				
				root.appendChild(el);
						
			confirm(Constants.getXSD_name_file());

			}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Method for editing a provider of the XSD file.
	 * Basically, it removes the 'original_Provider' from the XML Schema, and creates a 'new_Provider' together with a list of associated services.
	**/ 
	public static void setProvider(String original_Provider, String new_Provider, Vector new_Provider_Services_vector) {
		
		removeProvider(original_Provider);
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();  
			 			
			Element el = (Element) doc.createElement("xs:simpleType");
			el.setAttributeNode(doc.createAttribute("name"));
			el.setAttribute("name",new_Provider);
			
			Element el_child = (Element) doc.createElement("xs:restriction");
			el_child.setAttributeNode(doc.createAttribute("base"));
			el_child.setAttribute("base", "Service");
				
			el.appendChild(el_child);
				
			for(int x=0;x<new_Provider_Services_vector.size();x++){
					
				Element el_part = (Element) doc.createElement("xs:enumeration");
				el_part.setAttributeNode(doc.createAttribute("value"));
				el_part.setAttribute("value", (String) new_Provider_Services_vector.elementAt(x));
				el_child.appendChild(el_part);
			}
				
				root.appendChild(el);
						
			confirm(Constants.getXSD_name_file());

			}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Method for removing a provider from the XSD file. 
	**/ 	
	 public static void removeProvider(String provider) {
		
		try{
			Document doc = connect(Constants.getXSD_name_file());
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes(); 
			
			for(int index=0;index<nList.getLength();index++){
				Node nNode = nList.item(index);
				if(nNode.getNodeType() == Element.ELEMENT_NODE){
					Element eElem = (Element) nNode;					
					if(eElem.getNodeName().equalsIgnoreCase("xs:simpleType") && eElem.getAttribute("name").equalsIgnoreCase(provider))  {
						   root.removeChild(eElem);
						   confirm(Constants.getXSD_name_file());
				}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	  }
	 
		/** 
		 * Method for removing a service from any provider that includes it. 
		**/ 	
		public static void removeServiceFROMProviders(String service) {
			
			Vector<String> providers_vector = new Vector<String>();  
			
			Document doc = XMLParser.connect();
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
									
					NodeList childs_eElement = eElement.getElementsByTagName("xs:restriction");
					
					for(int index=0; index<childs_eElement.getLength(); index++){
						
						Node nchild_eElement = (Element) childs_eElement.item(index);
						
						if(nchild_eElement.getNodeType() == Node.ELEMENT_NODE){
							
							Element echild_eElement = (Element) nchild_eElement;
							
							if(eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && echild_eElement.getAttribute("base").equalsIgnoreCase("service")){							
								Node roles_node = echild_eElement.getParentNode();							
								if(roles_node.getNodeType() == Node.ELEMENT_NODE){								
									Element roles_eElement = (Element) roles_node;
									
									NodeList participantsList = roles_eElement.getElementsByTagName("xs:enumeration");
									
									for (int x = 0; x < participantsList.getLength(); x++) {
										
										Element pElement = (Element)participantsList.item(x);
										Node pElementNode = (Node)participantsList.item(x);
										
										if(pElement.getAttribute("value").equalsIgnoreCase(service))
											pElement.getParentNode().removeChild(pElement);
										
									}
										 
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			confirm(Constants.getXSD_name_file());
	
		}
		
   /** 
	 * Method for substituting an 'old_service' (from any provider containing it) with a 'new_service'. 
	**/ 	
		public static void substituteAnOldServiceWithANewServiceFromAnyProvider(String old_service, String new_service) {
			
			Vector<String> providers_vector = new Vector<String>();  
			
			Document doc = XMLParser.connect();
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
									
					NodeList childs_eElement = eElement.getElementsByTagName("xs:restriction");
					
					for(int index=0; index<childs_eElement.getLength(); index++){
						
						Node nchild_eElement = (Element) childs_eElement.item(index);
						
						if(nchild_eElement.getNodeType() == Node.ELEMENT_NODE){
							
							Element echild_eElement = (Element) nchild_eElement;
							
							if(eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && echild_eElement.getAttribute("base").equalsIgnoreCase("service")){							
								Node roles_node = echild_eElement.getParentNode();							
								if(roles_node.getNodeType() == Node.ELEMENT_NODE){								
									Element roles_eElement = (Element) roles_node;
									
									NodeList participantsList = roles_eElement.getElementsByTagName("xs:enumeration");
									
									for (int x = 0; x < participantsList.getLength(); x++) {
										
										Element pElement = (Element)participantsList.item(x);
										Node pElementNode = (Node)participantsList.item(x);
										
										if(pElement.getAttribute("value").equalsIgnoreCase(old_service))
											pElement.setAttribute("value", new_service);
										
									}
										 
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			confirm(Constants.getXSD_name_file());
	
		}
		
		/** 
		 * Method for adding a capability to the XSD file.
		**/ 	
		public static void addCapability(String capability){ 	
			
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();
				for(int index=0;index<nList.getLength();index++){
					Node nNode = nList.item(index);
					if(nNode.getNodeType() == Element.ELEMENT_NODE){
						Element eElem = (Element) nNode;
										
						if(eElem.getNodeName().equalsIgnoreCase("xs:simpleType") && eElem.getAttribute("name").equalsIgnoreCase("capability")){
							
							NodeList el_childs = eElem.getElementsByTagName("xs:restriction");
					
							Element el_child = (Element) el_childs.item(0);
							Element el_part = (Element) doc.createElement("xs:enumeration");
							el_part.setAttributeNode(doc.createAttribute("value"));
							el_part.setAttribute("value", capability);

							el_child.appendChild(el_part);
									
						}
						
					}
				} 
				confirm(Constants.getXSD_name_file());

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing a capability from the XSD file.
		**/ 
		public static void removeCapability(String capability){ 		

			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();  
				for(int index=0;index<nList.getLength();index++){
					Node nNode = nList.item(index);
					if(nNode.getNodeType() == Element.ELEMENT_NODE){
						Element eElem = (Element) nNode;
						if(eElem.getAttribute("name").equalsIgnoreCase("capability")){
							
							NodeList capabilitiesList = eElem.getElementsByTagName("xs:enumeration");
							
							for (int x = 0; x < capabilitiesList.getLength(); x++) {
								
								Element pElement = (Element)capabilitiesList.item(x);
								Node pElementNode = (Node)capabilitiesList.item(x);
								
								if(pElement.getAttribute("value").equalsIgnoreCase(capability))
									pElement.getParentNode().removeChild(pElement);
								
							}
							
						}

					}
				} // chiude for index=0 --> ho eliminato tutto Participant_type
				
				confirm(Constants.getXSD_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing all the existing associations between services and a single capability.
		**/ 
		public static void removeAssociationsBetweenServicesAndACapability(String capability){ 		

			try{
				
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes(); 
				
				for(int temp=0; temp<nList.getLength();temp++){
				
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
				
						if(eElement.getNodeName().equalsIgnoreCase("provides") && eElement.getAttribute("value").equalsIgnoreCase("true")){
							
						NodeList nodeList = eElement.getChildNodes();
						boolean toBeRemoved = false;
						
						for (int j = 0; j < nodeList.getLength(); j++) {						
						
							Node childNode = nodeList.item(j);
														
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {

							    	if (childNode.getLastChild().getNodeValue().equalsIgnoreCase(capability))
							    		toBeRemoved = true;
							    }
							}
							
						if(toBeRemoved)			
							root.removeChild(eElement);
							
						}
						
					}
				} 

				   
				confirm(Constants.getXML_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing all the existing associations between capabilities and a single service.
		**/ 
		public static void removeAssociationsBetweenCapabilitiesAndAService(String service){ 		

			try{
				
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes(); 
				
				for(int temp=0; temp<nList.getLength();temp++){
				
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
				
						if(eElement.getNodeName().equalsIgnoreCase("provides") && eElement.getAttribute("value").equalsIgnoreCase("true")){
							
						NodeList nodeList = eElement.getChildNodes();
						boolean toBeRemoved = false;
						
						for (int j = 0; j < nodeList.getLength(); j++) {						
						
							Node childNode = nodeList.item(j);
														
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {

							    	if (childNode.getFirstChild().getNodeValue().equalsIgnoreCase(service))
							    		toBeRemoved = true;
							    }
							}
							
						if(toBeRemoved)			
							root.removeChild(eElement);
							
						}
						
					}
				} 

				   
				confirm(Constants.getXML_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		/** 
		 * Method for adding an association between a service and a capability.
		**/ 	
		public static void addAssociationBetweenAServiceAndACapability(String service, String capability){
			
			try{
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				
				Element provides = (Element) doc.createElement("provides");
				provides.setAttribute("value", "true");
			
				Element provides_service = (Element) doc.createElement("service");
				provides_service.appendChild(doc.createTextNode(service));
				
				Element provides_capability = (Element) doc.createElement("capability");
				provides_capability.appendChild(doc.createTextNode(capability));
				
				provides.appendChild(provides_service);
				provides.appendChild(provides_capability);
				
				root.appendChild(provides);
					
				confirm(Constants.getXML_name_file());

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing an association between a service and a capability.
		 * */ 	
		public static void removeAssociationBetweenAServiceAndACapability(String service, String capability){ 		

			try{
				
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes(); 
				
				for(int temp=0; temp<nList.getLength();temp++){
				
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
				
						if(eElement.getNodeName().equalsIgnoreCase("provides") && eElement.getAttribute("value").equalsIgnoreCase("true")){
							
						NodeList nodeList = eElement.getChildNodes();
						boolean toBeRemoved_1 = false;
						boolean toBeRemoved_2 = false;
						
						for (int j = 0; j < nodeList.getLength(); j++) {						
						
							Node childNode = nodeList.item(j);
														
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {

							    	if (childNode.getFirstChild().getNodeValue().equalsIgnoreCase(service))
							    		toBeRemoved_1 = true;
							    	if (childNode.getLastChild().getNodeValue().equalsIgnoreCase(capability))
							    		toBeRemoved_2 = true;
							    }
							}
							
						if(toBeRemoved_1 && toBeRemoved_2)			
							root.removeChild(eElement);
							
						}
						
					}
				} 

				   
				confirm(Constants.getXML_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for adding an association between a task and a capability.
		 * */ 	
		public static void addAssociationBetweenATaskAndACapability(String task, String capability){
			
			try{
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				
				Element requires = (Element) doc.createElement("requires");
				requires.setAttribute("value", "true");
			
				Element requires_service = (Element) doc.createElement("task");
				requires_service.appendChild(doc.createTextNode(task));
				
				Element requires_capability = (Element) doc.createElement("capability");
				requires_capability.appendChild(doc.createTextNode(capability));
				
				requires.appendChild(requires_service);
				requires.appendChild(requires_capability);
				
				root.appendChild(requires);
					
				confirm(Constants.getXML_name_file());

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing an association between a task and a capability.
		 * */ 	
		public static void removeAssociationBetweenATaskAndACapability(String task, String capability){ 		

			try{
				
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes(); 
				
				for(int temp=0; temp<nList.getLength();temp++){
				
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
				
						if(eElement.getNodeName().equalsIgnoreCase("requires") && eElement.getAttribute("value").equalsIgnoreCase("true")){
							
						NodeList nodeList = eElement.getChildNodes();
						boolean toBeRemoved_1 = false;
						boolean toBeRemoved_2 = false;
						
						for (int j = 0; j < nodeList.getLength(); j++) {						
						
							Node childNode = nodeList.item(j);
														
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {

							    	if (childNode.getFirstChild().getNodeValue().equalsIgnoreCase(task))
							    		toBeRemoved_1 = true;
							    	if (childNode.getLastChild().getNodeValue().equalsIgnoreCase(capability))
							    		toBeRemoved_2 = true;
							    }
							}
							
						if(toBeRemoved_1 && toBeRemoved_2)			
							root.removeChild(eElement);
							
						}
						
					}
				} 

				   
				confirm(Constants.getXML_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
		/** 
		 * Method for removing any existing association between tasks and a specific capability.
		**/ 	
		public static void removeAssociationsBetweenTasksAndSingleCapability(String capability){ 		

			try{	
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes(); 
				
				for(int temp=0; temp<nList.getLength();temp++){
				
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
				
						if(eElement.getNodeName().equalsIgnoreCase("requires") && eElement.getAttribute("value").equalsIgnoreCase("true")){
							
						NodeList nodeList = eElement.getChildNodes();
						boolean toBeRemoved = false;
						
						for (int j = 0; j < nodeList.getLength(); j++) {						
						
							Node childNode = nodeList.item(j);
														
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {

							    	if (childNode.getLastChild().getNodeValue().equalsIgnoreCase(capability))
							    		toBeRemoved = true;
							    }
							}
							
						if(toBeRemoved)			
							root.removeChild(eElement);
							
						}
						
					}
				} 

				   
				confirm(Constants.getXML_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	   /** 
	    * Method for updating the minimum/maximum bounds of the Integer Data type. 
		**/
		public static void setIntegerBounds(Vector<String> i){
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();
				for(int index=0; index<nList.getLength(); index++){
					Node nNode = nList.item(index);
					if(nNode.getNodeType() == Element.ELEMENT_NODE){
						Element eNode = (Element) nNode;
						if(eNode.getNodeName().equalsIgnoreCase("xs:simpleType") && eNode.getAttribute("name").equalsIgnoreCase("Integer_type")){
							Node intNode = eNode;
							NodeList nL = intNode.getChildNodes();
							for(int index1=0;index1<nL.getLength();index1++){
								Node nRestr = nL.item(index1);
								if(nRestr.getNodeType() == Element.ELEMENT_NODE){
									Element eRestr = (Element)nRestr;
									NodeList minL = eRestr.getElementsByTagName("xs:minInclusive");
									for(int index2=0;index2<minL.getLength();index2++){
										Node minValue = minL.item(index2);
										if(minValue.getNodeType() == Element.ELEMENT_NODE){
											Element minV = (Element) minValue;
											minV.setAttribute("value", i.get(0));
										}
									}
									NodeList maxL = eRestr.getElementsByTagName("xs:maxInclusive");
									for(int index3=0;index3<maxL.getLength();index3++){
										Node maxValue = maxL.item(index3);
										if(maxValue.getNodeType() == Element.ELEMENT_NODE){
											Element maxV = (Element) maxValue;
											maxV.setAttribute("value", i.get(1)); 
										}
									}
									
								}
								
							}
							
						}
						
					}
				}
				
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for adding a new user data type on the XSD file.
		 **/
		public static void addUserDataType(Vector<String> v,String url){
			Element oldType = null;
			
			try{
				Document doc=connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();

					Element newType = (Element)doc.createElement("xs:simpleType");
					newType.setAttributeNode(doc.createAttribute("name"));
					newType.setAttribute("name", v.get(0));
					
					if(url!=null) { //If the data type has been generated by a plugin
						newType.setAttributeNode(doc.createAttribute("url"));
						newType.setAttribute("url", url);
					}

					Element el_restr = (Element) doc.createElement("xs:restriction");
					el_restr.setAttributeNode(doc.createAttribute("base"));
					el_restr.setAttribute("base", "xs:string");
					
					newType.appendChild(el_restr);
					
					for(int j=1;j<v.size();j++){
						Element el_obj = (Element) doc.createElement("xs:enumeration");
						el_obj.setAttributeNode(doc.createAttribute("value"));
						el_obj.setAttribute("value", v.get(j));
						
						el_restr.appendChild(el_obj);
					}
					root.appendChild(newType);

				confirm(Constants.getXSD_name_file());
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing a data type from the XSD file
		**/ 	
		public static void removeUserDataType(String datatype){ 		

			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();  //figli della radice
				for(int index=0;index<nList.getLength();index++){
					Node nNode = nList.item(index);
					if(nNode.getNodeType() == Element.ELEMENT_NODE){
						Element eElem = (Element) nNode;
						
						if(eElem.getNodeName().equalsIgnoreCase("xs:simpleType") && eElem.getAttribute("name").equalsIgnoreCase(datatype)){
							
							root.removeChild(eElem);
						}
					}
				} 
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 
		 * Method for updating user defined data types on the XSD file.
		 * It first deletes every user data type present in the XSD file, and then re-insert all updated data types. 
		 **/
		public static void setUserDataTypes(Vector<Vector<String>> v){
			Element oldType = null;
			
			try{
				Document doc=connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();
				for(int index=0;index<nList.getLength();index++){
					Node nNode = nList.item(index);
					if(nNode.getNodeType() == Element.ELEMENT_NODE){
						Element eElement = (Element)nNode;
						if(eElement.getNodeName().equalsIgnoreCase("xs:simpleType") && eElement.getAttribute("name").contains("_type") && !eElement.getAttribute("name").equalsIgnoreCase("Integer_type") && !eElement.getAttribute("name").equalsIgnoreCase("Boolean_type")){
							oldType = eElement;
							root.removeChild(oldType); // rimuovo il nodo
						}
					}
				}
				for(int i=0;i<v.size();i++){
					Element newType = (Element)doc.createElement("xs:simpleType");
					newType.setAttributeNode(doc.createAttribute("name"));
					
					newType.setAttribute("name", v.get(i).get(0));

					Element el_restr = (Element) doc.createElement("xs:restriction");
					el_restr.setAttributeNode(doc.createAttribute("base"));
					el_restr.setAttribute("base", "xs:string");
					
					newType.appendChild(el_restr);
					
					for(int j=1;j<v.get(i).size();j++){
						Element el_obj = (Element) doc.createElement("xs:enumeration");
						el_obj.setAttributeNode(doc.createAttribute("value"));
						el_obj.setAttribute("value", v.get(i).get(j));
						
						el_restr.appendChild(el_obj);
					}
					root.appendChild(newType);
						
				}
				
				confirm(Constants.getXSD_name_file());
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
/*----------------------------------------------------------------------- Atomic Terms -------------------------------------------------------------------*/		
		
		/** 
		 * Method for adding an atomic term to the XSD file. 
		 * Vector v1 contains the name (first component) of the atomic term to be created and if it is static or dynamic (second component).
		 * Vector v2 contains several internal vectors, whose purpose is to record the name and the type of each input parameter associated to the atomic term.
		 * Vector v3 contains the type of the value stored by the atomic term (first component) and a 
		 * boolean value (second component) indicating if the atomic term has to be considered as relevant or not.
		 **/
		public static void addAtomicTerm(Vector<String> v1,Vector<Vector<String>> v2, Vector<String> v3){
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				
				Element newAT = (Element)doc.createElement("xs:element");
				newAT.setAttributeNode(doc.createAttribute("name"));
				newAT.setAttributeNode(doc.createAttribute("id"));
				
				newAT.setAttribute("name", v1.get(0));
				if(v1.get(1).equalsIgnoreCase("dynamic")){
					newAT.setAttribute("id", "dynamic_term-"+v1.get(0));
				}
				else{
					newAT.setAttribute("id", "static_term-"+v1.get(0));
				}
				
				Element complexT = (Element)doc.createElement("xs:complexType");
				newAT.appendChild(complexT);
				
				Element sequenceT = (Element)doc.createElement("xs:sequence");
				complexT.appendChild(sequenceT);
				
				for(int i=0;i<v2.size();i++){
					Element argEl = (Element)doc.createElement("xs:element");
					argEl.setAttributeNode(doc.createAttribute("name"));
					argEl.setAttributeNode(doc.createAttribute("type"));
					
					argEl.setAttribute("name", v2.get(i).get(0));
					argEl.setAttribute("type", v2.get(i).get(1));
					
					sequenceT.appendChild(argEl);
				}
				
				Element retValue_child = (Element)doc.createElement("xs:attribute");
				retValue_child.setAttributeNode(doc.createAttribute("name"));
				retValue_child.setAttribute("name", "value");
				retValue_child.setAttributeNode(doc.createAttribute("type"));
				retValue_child.setAttribute("type", v3.get(0));
				complexT.appendChild(retValue_child);
				
				if(v1.get(1).equalsIgnoreCase("dynamic")){
					Element rel_child = (Element)doc.createElement("xs:attribute");
					rel_child.setAttributeNode(doc.createAttribute("name"));
					rel_child.setAttribute("name", "relevant");
					rel_child.setAttributeNode(doc.createAttribute("type"));
					rel_child.setAttribute("type", "Boolean_type");
					rel_child.setAttributeNode(doc.createAttribute("default"));
					rel_child.setAttribute("default",v3.get(1));
					
					complexT.appendChild(rel_child);
				}
				
				
				root.appendChild(newAT);
				
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/** 
		 * Method for removing a specific atomic term from the XSD file 
		 **/
		public static void deleteAtomicTerm(String atTerm_name){
			
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getChildNodes();
				for(int index=0;index<rChilds.getLength();index++){
					Node n = rChilds.item(index);
					if(n.getNodeType() == Element.ELEMENT_NODE){
						Element eN = (Element)n;
						if(eN.getAttribute("name").equalsIgnoreCase(atTerm_name)){
							root.removeChild(eN);
						}
					}
				}
				confirm(Constants.getXSD_name_file());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		/** 
		 * Method for adding to the "theory" element of the XSD file the atomic term just created.
		**/
		public static void addAtomicTermToTheoryElement(String term_name) {
			Element choice_elem = null;
			
			try {
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getChildNodes();
				for(int index=0;index<rChilds.getLength();index++) {
					Node rChild = rChilds.item(index);
					if(rChild.getNodeType() == Element.ELEMENT_NODE) {
						Element eChild = (Element)rChild;
						if(eChild.getAttribute("name").equalsIgnoreCase("theory")) {
							Node th = eChild;
							if(th.getNodeType() == Element.ELEMENT_NODE) {
								Element eth = (Element)th;
								NodeList thChilds = eth.getElementsByTagName("xs:complexType");
								for(int index1=0;index1<thChilds.getLength();index1++) {
									Node compl = thChilds.item(index1);
									if(compl.getNodeType() == Element.ELEMENT_NODE) {
										Element eCompl = (Element)compl;
										NodeList complList = eCompl.getElementsByTagName("xs:choice");
										for(int index2=0;index2<complList.getLength();index2++) {
											Node choice = complList.item(index2);
											if(choice.getNodeType() == Element.ELEMENT_NODE) {
												Element eChoice = (Element)choice;
												choice_elem = eChoice;
											}
										}
									}
								}
							}
						}
					}
				}
				
				Element term_el = doc.createElement("xs:element");
				term_el.setAttributeNode(doc.createAttribute("minOccurs"));
				term_el.setAttribute("minOccurs", "0");
				term_el.setAttributeNode(doc.createAttribute("ref"));
				term_el.setAttribute("ref", term_name);
				choice_elem.appendChild(term_el);
	
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 
		 * Method for removing to the "theory" element of the XSD file a specific atomic term.
		**/
		public static void deleteTermFromTheoryElement(String term_name){
			
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rootChilds = root.getElementsByTagName("xs:element");
				for(int index=0;index<rootChilds.getLength();index++){
					Node rChild = rootChilds.item(index);
					if(rChild.getNodeType() == Element.ELEMENT_NODE){
						Element eChild = (Element)rChild;
						if(eChild.getAttribute("name").equalsIgnoreCase("theory")){
							Element theory = eChild;
							NodeList compl = theory.getElementsByTagName("xs:complexType");
							for(int index1=0;index1<compl.getLength();index1++){
								Node r = compl.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r;
									NodeList choice = eR.getElementsByTagName("xs:choice");
									for(int index2=0;index2<choice.getLength();index2++) {
										Node ch = choice.item(index2);
										if(ch.getNodeType() == Element.ELEMENT_NODE) {
											Element eCh = (Element)ch; // ELEMENT CHOICE
											NodeList els = eCh.getElementsByTagName("xs:element");
											for(int index3=0;index3<els.getLength();index3++) {
												Node elem = els.item(index3);
												if(elem.getNodeType() == Element.ELEMENT_NODE) {
													Element eElem = (Element)elem;
													if(eElem.getAttribute("ref").equalsIgnoreCase(term_name)){
														eCh.removeChild(eElem);
													}
												}
											}	
										}
									}	
								}
							}
						}
					}
				} 	
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
/*----------------------------------------------------------------------- Task -------------------------------------------------------------------*/
				
		/** 
		 * Method for creating a new task (with the associated parameters, arguments, preconditions, supposed and automatic effects, ecc.) which is inserted into the Constants.getXML_name_file() file.
		**/
		public static void createTask(String name,Vector<Vector<String>> args, Vector<String> precs, Vector<String> supp_effs,Vector<String> auto_effs){
			
			try{
			
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement(); 
				
				Element task = doc.createElement("task_definition");
				task.setAttributeNode(doc.createAttribute("name"));
				task.setAttribute("name", name);
				
				root.appendChild(task); // forse
				
				for(int index=0;index<args.size();index++){
					Element arg = doc.createElement("argument");
					Text argVal = doc.createTextNode(args.get(index).firstElement() + " " + "-" + " " + args.get(index).lastElement());
					
					arg.appendChild(argVal); 
					
					task.appendChild(arg);
				}
				
				for(int index1=0;index1<precs.size();index1++){
					Element prec = doc.createElement("precondition");
					Text precVal = doc.createTextNode(precs.get(index1));
					
					prec.appendChild(precVal); 
					
					task.appendChild(prec);
				}
				
				for(int index2=0;index2<supp_effs.size();index2++){
					Element s_effs = doc.createElement("supposed-effect");
					Text s_effsVal = doc.createTextNode(supp_effs.get(index2));
					
					s_effs.appendChild(s_effsVal);
					
					task.appendChild(s_effs);
					
				}
				
				for(int index3=0;index3<auto_effs.size();index3++){
					Element a_effs = doc.createElement("automatic-effect");
					Text a_effsVal = doc.createTextNode(auto_effs.get(index3));
					
					a_effs.appendChild(a_effsVal);
					
					task.appendChild(a_effs);
				}
				
				confirm(Constants.getXML_name_file());
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for adding the name of a new task into the XSD file (under the "task_repository" element).
		**/
		public static void addTaskToXSD(String task_name){
			
			Element restriction = null;
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rootChilds = root.getElementsByTagName("xs:simpleType");
				for(int index=0;index<rootChilds.getLength();index++){
					Node rChild = rootChilds.item(index);
					if(rChild.getNodeType() == Element.ELEMENT_NODE){
						Element eChild = (Element)rChild;
						if(eChild.getAttribute("name").equalsIgnoreCase("task_repository")){
							Element task_rep = eChild;
							NodeList restr = task_rep.getChildNodes();
							for(int index1=0;index1<restr.getLength();index1++){
								Node r = restr.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r;
									if(eR.getNodeName().equalsIgnoreCase("xs:restriction")){
										restriction = eR;
									}
									else{
										// NIENTE, ma non dovrebbe succedere
									}
								}
							}
						}
						else{
							// Niente
						}
					}
				} // CHIUDE IL PRIMO for
				
				Element task_el = doc.createElement("xs:enumeration");
				task_el.setAttributeNode(doc.createAttribute("value"));
				task_el.setAttribute("value", task_name);
				
				restriction.appendChild(task_el);
				
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 
		 * Method for deleting the name of a task from the XSD file (under the "task_repository" element).
		**/
		public static void deleteTaskFromXSD(String task){
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("xs:simpleType");
				for(int index=0;index<rChilds.getLength();index++){
					Node nC = rChilds.item(index);
					if(nC.getNodeType() == Element.ELEMENT_NODE){
						Element eC = (Element)nC;
						if(eC.getAttribute("name").equalsIgnoreCase("task_repository")){
							NodeList restr = eC.getElementsByTagName("xs:restriction");
							for(int index1=0;index1<restr.getLength();index1++){
								Node r = restr.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r; // Element xs:restriction
									NodeList tasksList = eR.getElementsByTagName("xs:enumeration");
									for(int index2=0;index2<tasksList.getLength();index2++){
										Node t = tasksList.item(index2);
										if(t.getNodeType() == Element.ELEMENT_NODE){
											Element eT = (Element)t;
											if(eT.getAttribute("value").equalsIgnoreCase(task)){
												eR.removeChild(eT);
											}
											
										}
									}
								}
							}
						}
					}
				}
				
				confirm(Constants.getXSD_name_file());

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing a task from the 'repository.xml' file 
		**/
		public static void deleteTask(String task_name){
			try{
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("task_definition");
				for(int index=0;index<rChilds.getLength();index++){
					Node nC = rChilds.item(index);
					if(nC.getNodeType() == Element.ELEMENT_NODE){
						Element eC = (Element)nC;
						if(eC.getAttribute("name").equalsIgnoreCase(task_name)){
							root.removeChild(eC);
						}
					}
				}
				confirm(Constants.getXML_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

/*----------------------------------------------------------------------- Exogenous Event -------------------------------------------------------------------*/
		
		/** 
		 * Method for creating a new exogenous event (with the associated parameters and content) which is inserted into the Constants.getXML_name_file() file.
		**/
		public static void createExogenousEvent(String name_Ev, Vector<Vector<String>> args_Ev, Vector<String> effs_Ev){
			
			try{
			
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement(); 
				
				Element task = doc.createElement("exogenous_event_definition");
				task.setAttributeNode(doc.createAttribute("name"));
				task.setAttribute("name", name_Ev);
				
				root.appendChild(task); 
				
				for(int index=0;index<args_Ev.size();index++){
					Element arg = doc.createElement("argument");
					Text argVal = doc.createTextNode(args_Ev.get(index).firstElement() + " " + "-" + " " + args_Ev.get(index).lastElement());
					arg.appendChild(argVal); 
					task.appendChild(arg);
				}
				
				for(int index2=0;index2<effs_Ev.size();index2++){
					Element effs = doc.createElement("effect");
					Text effsVal = doc.createTextNode(effs_Ev.get(index2));
					
					effs.appendChild(effsVal);
					task.appendChild(effs);
					
				}
				confirm(Constants.getXML_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		/** 
		 * Method for adding the name of a new exogenous event into the XSD file (under the "exogenous_events_repository" element).
		**/
		public static void addExogenousEventToXSD(String event_name){
			
			Element restriction = null;
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rootChilds = root.getElementsByTagName("xs:simpleType");
				for(int index=0;index<rootChilds.getLength();index++){
					Node rChild = rootChilds.item(index);
					if(rChild.getNodeType() == Element.ELEMENT_NODE){
						Element eChild = (Element)rChild;
						if(eChild.getAttribute("name").equalsIgnoreCase("exogenous_event_repository")){
							Element task_rep = eChild;
							NodeList restr = task_rep.getChildNodes();
							for(int index1=0;index1<restr.getLength();index1++){
								Node r = restr.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r;
									if(eR.getNodeName().equalsIgnoreCase("xs:restriction")){
										restriction = eR;
									}
								}
							}
						}
					}
				} 
				
				Element event_el = doc.createElement("xs:enumeration");
				event_el.setAttributeNode(doc.createAttribute("value"));
				event_el.setAttribute("value", event_name);
				
				restriction.appendChild(event_el);
				
				confirm(Constants.getXSD_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing the name of a specific exogenous event from the XSD file (under the "exogenous_events_repository" element).
		**/
		public static void deleteExogenousEventfromXSD(String event_name){
			
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("xs:simpleType");
				for(int index=0;index<rChilds.getLength();index++){
					Node nC = rChilds.item(index);
					if(nC.getNodeType() == Element.ELEMENT_NODE){
						Element eC = (Element)nC;
						if(eC.getAttribute("name").equalsIgnoreCase("exogenous_event_repository")){
							NodeList restr = eC.getElementsByTagName("xs:restriction");
							for(int index1=0;index1<restr.getLength();index1++){
								Node r = restr.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r; // Element xs:restriction
									NodeList tasksList = eR.getElementsByTagName("xs:enumeration");
									for(int index2=0;index2<tasksList.getLength();index2++){
										Node t = tasksList.item(index2);
										if(t.getNodeType() == Element.ELEMENT_NODE){
											Element eT = (Element)t;
											if(eT.getAttribute("value").equalsIgnoreCase(event_name)){
											
												eR.removeChild(eT);
											}
										}
									}
								}
							}
						}
					}
				}
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 
		 * Method for removing an exogenous event from the 'repository.xml' file 
		**/
		public static void deleteExogenousEvent(String event){
			try{
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("exogenous_event_definition");
				for(int index=0;index<rChilds.getLength();index++){
					Node nC = rChilds.item(index);
					if(nC.getNodeType() == Element.ELEMENT_NODE){
						Element eC = (Element)nC;
						if(eC.getAttribute("name").equalsIgnoreCase(event)){
							root.removeChild(eC);
						}
					}
				}
				confirm(Constants.getXML_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
/*----------------------------------------------------------------------- Complex Formulae -------------------------------------------------------------------*/

		/** 
		 * Method for creating a new formula (with the associated parameters and content) which is inserted into the Constants.getXML_name_file() file.
		**/
		public static void createFormula(String formula_name, Vector<Vector<String>> args_vector, String content){
			
			try{
			
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement(); 
				
				Element formula_element = doc.createElement("formula_definition");
				formula_element.setAttributeNode(doc.createAttribute("name"));
				formula_element.setAttribute("name", formula_name);
				
				for(int index=0;index<args_vector.size();index++){
					Element arg = doc.createElement("argument");
					Text argVal = doc.createTextNode(args_vector.get(index).firstElement() + " " + "-" + " " + args_vector.get(index).lastElement());
					
					arg.appendChild(argVal); 
					
					formula_element.appendChild(arg);
				}
	
				Element formula_content = doc.createElement("content");
				Text content_text = doc.createTextNode(content);
				
				formula_content.appendChild(content_text);
				formula_element.appendChild(formula_content);
				
				root.appendChild(formula_element); 				
								
				confirm(Constants.getXML_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 
		 * Method for adding the name of a new formula into the XSD file (under the "formulae_repository" element).
		**/
		public static void addFormulaToXSD(String formula_name){
			
			Element restriction = null;
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rootChilds = root.getElementsByTagName("xs:simpleType");
				for(int index=0;index<rootChilds.getLength();index++){
					Node rChild = rootChilds.item(index);
					if(rChild.getNodeType() == Element.ELEMENT_NODE){
						Element eChild = (Element)rChild;
						if(eChild.getAttribute("name").equalsIgnoreCase("formulae_repository")){
							Element task_rep = eChild;
							NodeList restr = task_rep.getChildNodes();
							for(int index1=0;index1<restr.getLength();index1++){
								Node r = restr.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r;
									if(eR.getNodeName().equalsIgnoreCase("xs:restriction")){
										restriction = eR;
									}
								}
							}
						}
					}
				}
				
				Element event_el = doc.createElement("xs:enumeration");
				event_el.setAttributeNode(doc.createAttribute("value"));
				event_el.setAttribute("value", formula_name);
				
				restriction.appendChild(event_el);
				
				confirm(Constants.getXSD_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/** 
		 * Method for removing the name of a formula from the XSD file (under the "formulae_repository" element).
		**/
		public static void deleteFormulaFromXSD(String formula){
			
			try{
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("xs:simpleType");
				for(int index=0;index<rChilds.getLength();index++){
					Node nC = rChilds.item(index);
					if(nC.getNodeType() == Element.ELEMENT_NODE){
						Element eC = (Element)nC;
						if(eC.getAttribute("name").equalsIgnoreCase("formulae_repository")){
							NodeList restr = eC.getElementsByTagName("xs:restriction");
							for(int index1=0;index1<restr.getLength();index1++){
								Node r = restr.item(index1);
								if(r.getNodeType() == Element.ELEMENT_NODE){
									Element eR = (Element)r; // Element xs:restriction
									NodeList tasksList = eR.getElementsByTagName("xs:enumeration");
									for(int index2=0;index2<tasksList.getLength();index2++){
										Node t = tasksList.item(index2);
										if(t.getNodeType() == Element.ELEMENT_NODE){
											Element eT = (Element)t;
											if(eT.getAttribute("value").equalsIgnoreCase(formula)){
												eR.removeChild(eT);
											}
										}
									}
								}
							}
						}
					}
				}
				confirm(Constants.getXSD_name_file());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		/** 
		 * Method for removing a formula from the 'repository.xml' file 
		**/
		public static void deleteFormula(String formula){
			try{
				Document doc = connect(Constants.getXML_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("formula_definition");
				for(int index=0;index<rChilds.getLength();index++){
					Node nC = rChilds.item(index);
					if(nC.getNodeType() == Element.ELEMENT_NODE){
						Element eC = (Element)nC;
						if(eC.getAttribute("name").equalsIgnoreCase(formula)){
							root.removeChild(eC);
						}
						
					}
				
				}
				confirm(Constants.getXML_name_file());
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
/*------------------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------- END ------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------------------*/	

		
/*------------------------------------------------------Supporting Methods-------------------------------------------------------------------------*/
		
		/** 
		 * It returns a vector with the required operators for setting up an effect 
		 * (given the return type of the atomic term passed as input).
		**/
		public static Vector<String> getEffectOperators(String type){
				Vector<String> op = new Vector<String>();
				
				if(type.equalsIgnoreCase("Integer_type")){
					op.add("=");
					op.add("+=");
					op.add("-=");
				}
				else{ // in any other case, it can only provides an assignement
					op.add("=");
				}

				return op;	
		}
		
		/** 
		 * It returns a vector with the required operators for setting up a precondition 
		 * (given the return type of the atomic term or formula passed as input).
		**/
		public static Vector<String> getPreconditionOperatorsFor(String r){
			Vector<String> op = new Vector<String>();
			
			if(r.equalsIgnoreCase("Integer_type")){
				op.add(">");
				op.add(">=");
				op.add("<");
				op.add("<=");
				op.add("==");
				//op.add("<>"); Actually the planner is not able to handle with ADL problems!
			}
			else if(r.equalsIgnoreCase("Boolean_type")){
				op.add("==");
			}
			else { // Se r � uguale a "Boolean_type" o "functional_type" (che � qualunque altro tipo escluso integer e boolean)
				op.add("==");
				op.add("<>");
			}
			return op;
		}
		
		/** 
		 * Method for retrieving the list of existing plugins from the plugins.xml file.
		**/	
		public static Vector<Vector<String>> getPlugins(){
			
			Vector<Vector<String>> inArgs = new Vector<Vector<String>>();
			Vector<String> app_vec = null;
			
			try{
				Document doc = connect(Constants.getXML_plugin_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("plugin-definition");
				for(int index=0;index<rChilds.getLength();index++){
					Node n = rChilds.item(index);
					if(n.getNodeType()==Element.ELEMENT_NODE){
						
						Vector single_plugin_vector = new Vector();
						
						Element eN = (Element)n;
						
						single_plugin_vector.addElement(eN.getAttribute("name"));
						single_plugin_vector.addElement(eN.getAttribute("url"));
						
						NodeList descriptionList = eN.getElementsByTagName("description"); 
						
						for(int index1=0;index1<descriptionList.getLength();index1++){
							Node argm = descriptionList.item(index1);
							if(argm.getNodeType()== Element.ELEMENT_NODE){
								Element eArgm = (Element)argm;
								String arg_string = eArgm.getTextContent();
								single_plugin_vector.addElement(arg_string);	
							}
						}
					
						inArgs.add(single_plugin_vector);

					}
				}

			}
			catch (Exception e){
				e.printStackTrace();
			}
			return inArgs;
			
		}	
		
		/** 
		 * Method for retrieving the details (name, url and description) of existing plugins from the plugins.xml file.
		**/	
		public static Vector<String> getPluginDetails(String plugin_name){
			
			Vector <String> plugin_details_vector = new Vector<String>();
			
			try{
				Document doc = connect(Constants.getXML_plugin_name_file());
				Element root = doc.getDocumentElement();
				NodeList rChilds = root.getElementsByTagName("plugin-definition");
				for(int index=0;index<rChilds.getLength();index++){
					Node n = rChilds.item(index);
					if(n.getNodeType()==Element.ELEMENT_NODE){
						
						Element eN = (Element)n;
						
						if(eN.getAttribute("name").equalsIgnoreCase(plugin_name)) {
							
							plugin_details_vector.addElement(eN.getAttribute("name"));
							plugin_details_vector.addElement(eN.getAttribute("url"));
							
							NodeList descriptionList = eN.getElementsByTagName("description"); 
							
							for(int index1=0;index1<descriptionList.getLength();index1++){
								Node argm = descriptionList.item(index1);
								if(argm.getNodeType()== Element.ELEMENT_NODE){
									Element eArgm = (Element)argm;
									String arg_string = eArgm.getTextContent();
									plugin_details_vector.addElement(arg_string);	
								}
							}
							break;
						}						

					}
				}

			}
			catch (Exception e){
				e.printStackTrace();
			}
			return plugin_details_vector;
			
		}	
		
		/**
		 * Returns a data type generated by a specific plugin, as a string with the format 'name_of_data_type = <obj1,...,objn>'
		 **/
		public static String parseDataTypeFromPluginFileAsAString(String plugin_file_name){
			
			String plugin_data_type_details = new String();
			
			try{
				Document doc = connect(plugin_file_name);
				Element root = doc.getDocumentElement();
				
				plugin_data_type_details = new String(root.getNodeName() + " = <");
				
				NodeList rChilds = root.getChildNodes();
				
				for(int index=2;index<rChilds.getLength();index++){
					Node n = rChilds.item(index);
					if(n.getNodeType()==Element.ELEMENT_NODE){
						
						Element eN = (Element)n;

						if(index==rChilds.getLength()-1)
								plugin_data_type_details = plugin_data_type_details + (eN.getAttribute("value") + ">");
							else
								plugin_data_type_details = plugin_data_type_details + (eN.getAttribute("value") + ",");

					}
				}

			}
			catch (Exception e){
				e.printStackTrace();
			}
			return plugin_data_type_details;
			
		}	
		
		/**
		 * Returns a data type generated by a specific plugin, as a vector where the first element is the name_of_data_type, and the other elements are the data objects.
		 **/
		public static Vector parseDataTypeFromPluginFileAsAVector(String plugin_file_name){
			
			Vector plugin_data_type_details_vector = new Vector();
			
			try{
				Document doc = connect(plugin_file_name);
				Element root = doc.getDocumentElement();
				
				plugin_data_type_details_vector.addElement(root.getNodeName());
				
				
				NodeList rChilds = root.getChildNodes();
				
				for(int index=2;index<rChilds.getLength();index++){
					Node n = rChilds.item(index);
					if(n.getNodeType()==Element.ELEMENT_NODE){
						
						Element eN = (Element)n;
						
						plugin_data_type_details_vector.addElement(eN.getAttribute("value"));

					}
				}

			}
			catch (Exception e){
				e.printStackTrace();
			}
			return plugin_data_type_details_vector;
			
		}	
		
		/**
		 * Returns the name of a data type generated by a specific plugin.
		 **/
		public static String readTheNameofTheDataTypeFromThePluginFile(String plugin_file_name){
			
			String plugin_data_type_details = new String();
			Element root = null;
			
			try{
				Document doc = connect(plugin_file_name);
				root = doc.getDocumentElement();
				}
			catch (Exception e){
				e.printStackTrace();
			}
			return root.getNodeName();
			
		}	
		
		
		
		/** 
		 * Method for adding a plugin.
		**/ 	
		public static void addPlugin(String plugin_name, String url, String description){
			
			try{
				Document doc = connect(Constants.getXML_plugin_name_file());
				Element root = doc.getDocumentElement();
				
				Element plugin = (Element) doc.createElement("plugin-definition");
				plugin.setAttribute("name", plugin_name);
				plugin.setAttribute("url", url);
			
				Element el_plugin_description = (Element) doc.createElement("description");
				el_plugin_description.appendChild(doc.createTextNode(description));

				plugin.appendChild(el_plugin_description);
				
				root.appendChild(plugin);
					
				confirm(Constants.getXML_plugin_name_file());

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public static void removePlugin(String plugin_name){ 		

			try{
				
				Document doc = connect(Constants.getXML_plugin_name_file());
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes(); 
				
				for(int temp=0; temp<nList.getLength();temp++){
				
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
				
						if(eElement.getNodeName().equalsIgnoreCase("plugin-definition") && eElement.getAttribute("name").equalsIgnoreCase(plugin_name)){
							
							root.removeChild(eElement);
							
						}
					}
				} 

				confirm(Constants.getXML_plugin_name_file());
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
/*------------------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------- END ------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------------------*/	
		
		
		
		
		/** METODO CHE RESTITUISCE TUTTI I PARTICIPANTS CHE HANNO UN CERTO RUOLO 
	 * Prende in ingresso la stringa che rappresenta il nome del ruolo di cui
	 * vogliamo i participants -- PER ADESSO INUTILIZZATO */ 
	public static Vector<String> getParticipantsAtRole(String role){     

		
		Vector<String> _partRoles = new Vector<String>();

		Document doc = XMLParser.connect();
		// Prendo l'elemento radice
		Element root = doc.getDocumentElement();
		// Prendo i figli della radice
		NodeList nList = root.getChildNodes();
	
		for (int temp = 0; temp < nList.getLength(); temp++) {
		
			Node nNode = nList.item(temp);
		
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			
				Element eElement = (Element) nNode;
			
				NodeList childs_eElement = eElement.getElementsByTagName("xs:restriction");
			
				for(int index=0; index<childs_eElement.getLength(); index++){
				
					Node nchild_eElement = (Element) childs_eElement.item(index);
				
					if(nchild_eElement.getNodeType() == Node.ELEMENT_NODE){
					
						Element echild_eElement = (Element) nchild_eElement;
					
						if(echild_eElement.getAttribute("base").equalsIgnoreCase("service")){
						
							Node roles_node = echild_eElement.getParentNode();
						
							if(roles_node.getNodeType() == Node.ELEMENT_NODE){
							
								Element roles_eElement = (Element) roles_node;
							
								if(roles_eElement.getAttribute("name").equalsIgnoreCase(role)){
								
									NodeList figliList = roles_eElement.getElementsByTagName("xs:enumeration");
								
									for (int t = 0; t<figliList.getLength(); t++) {
								
										Element figliElement = (Element)figliList.item(t);
									
										_partRoles.addElement(figliElement.getAttribute("value"));

									}	
								}				
							}			
						}			
					}
				}			
			}			
		}
			return _partRoles;
	}
	
	/** Restituisce un vettore contenente l'espressione con cui vogliamo valutare
	 * la precondizione.Dipende dal tipo di atomic_term scelto, ma in generale pu� essere:
	 * - true/false; - interi compresi nel range; - tutti i "participant" ? - tutti i "data_type (user_defined)" ?*/
	public static Vector<String> getExpression(){
		Vector<String> all = new Vector<String>();
		all.add("true");
		all.add("false");
		
		int minBound = Integer.parseInt(getIntegerType().get(0));
		int maxBound = Integer.parseInt(getIntegerType().get(1));
		for(int i=minBound;i<=maxBound;i++){
			all.add(String.valueOf(i));
		}
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rootChilds = root.getChildNodes();
		for(int index=0;index<rootChilds.getLength();index++){
			Node child = rootChilds.item(index);
			if(child.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)child;
				if(eChild.getAttribute("type").equalsIgnoreCase("participant") || eChild.getAttribute("type").equalsIgnoreCase("data_type")){
					NodeList enumerList = eChild.getElementsByTagName("xs:enumeration");
					for(int index1=0;index1<enumerList.getLength();index1++){
						Node n = enumerList.item(index1);
						if(n.getNodeType() == Element.ELEMENT_NODE){
							Element eN = (Element)n;
							all.add(eN.getAttribute("value"));
						}
					}
				}
				else{
					// NIENTE!
				}
				
			}
		}
		return all;
	
	}
		
	/** Restituisce un vettore di vettori, contenente i "dettagli" dell'atomic term passatogli in ingresso.
	 * In particolare, nella prima componente (di ogni vettore) c'� un vettore contenente i NOMI dei parametri in ingresso 
	 * dell' atomic term (a differenza del metodo precedente, che cattura i tipi degli argomenti); nella seconda componente c'� un vettore (di una sola componente) contenente il
	 * tipo del valore ritornato dall'atomic term. */
	public static Vector<Vector<String>> AtomicTermDetails2(String at){
		Vector<Vector<String>> details = new Vector<Vector<String>>();
		Vector<String>  input_params = new Vector<String>();
		Vector<String>  ret_values = new Vector<String>();
		
		Document doc = connect();
		Element root = doc.getDocumentElement();
		NodeList rChilds = root.getElementsByTagName("xs:element");
		for(int index=0;index<rChilds.getLength();index++){
			Node rChild = rChilds.item(index);
			if(rChild.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)rChild;
				if(eChild.getAttribute("type").equalsIgnoreCase("dynamic_term") || eChild.getAttribute("type").equalsIgnoreCase("static_term") || eChild.getAttribute("type").equalsIgnoreCase("predefined_term")){
					if(eChild.getAttribute("name").equalsIgnoreCase(at)){
						//System.out.println(eChild.getAttribute("name")); // OK
						NodeList seqNode = eChild.getElementsByTagName("xs:sequence");
						for(int index1=0;index1<seqNode.getLength();index1++){
							Node sequence = seqNode.item(index1);
							if(sequence.getNodeType() == Element.ELEMENT_NODE){
								Element eSequence = (Element)sequence;
								NodeList inputParams = eSequence.getElementsByTagName("xs:element");
								for(int index2=0;index2<inputParams.getLength();index2++){
									Node par = inputParams.item(index2);
									if(par.getNodeType() == Element.ELEMENT_NODE){
										Element ePar = (Element)par;
										//System.out.println(ePar.getAttribute("type")); // OK
										input_params.add(ePar.getAttribute("name"));
									}
								}
							}
						}
						//System.out.println(input_params); //OK
						details.add(input_params);
						
						NodeList outParams = eChild.getElementsByTagName("xs:attribute");
						for(int index3=0;index3<outParams.getLength();index3++){
							Node outV = outParams.item(index3);
							if(outV.getNodeType() == Element.ELEMENT_NODE){
								Element eOut = (Element)outV;
								if(eOut.getAttribute("name").equalsIgnoreCase("value")){
									//System.out.println(eOut.getAttribute("type")); // OK
									ret_values.add(eOut.getAttribute("type"));
								}
								else{
									// NIENTE.
								}
							}
						}
						//System.out.println(ret_values); // OK
						details.add(ret_values);
					}
					else{
						// NIENTE.
					}
				}
				else{
					// NIENTE.
				}
			}
		}
		return details;	
	}
	

	

	
	/** Restituisce i dettagli dell'atomic term O DELLA FORMULA passatagli in ingresso in un vettore di vettori.
	 *  In particolare, nella prima componente (di ogni vettore) c'� un vettore contenente i NOMI dei parametri in ingresso 
	 * dell' atomic term (a differenza del metodo precedente, che cattura i tipi degli argomenti); nella seconda componente c'� un vettore (di una sola componente) contenente il
	 * tipo del valore ritornato dall'atomic term.*/
	public static Vector<Vector<String>> AtomicTermOrFormulaDetails2(String nm){
		Vector<Vector<String>> details = new Vector<Vector<String>>();
		Vector<String>  input_params = new Vector<String>();
		Vector<String>  ret_values = new Vector<String>();
		
		Document doc = connect(Constants.getXSD_name_file());
		Element root = doc.getDocumentElement();
		NodeList rChilds = root.getElementsByTagName("xs:element");
		for(int index=0;index<rChilds.getLength();index++){
			Node rChild = rChilds.item(index);
			if(rChild.getNodeType() == Element.ELEMENT_NODE){
				Element eChild = (Element)rChild;
				if(eChild.getAttribute("type").equalsIgnoreCase("dynamic_term") || eChild.getAttribute("type").equalsIgnoreCase("static_term")){
					if(eChild.getAttribute("name").equalsIgnoreCase(nm)){
						//System.out.println(eChild.getAttribute("name")); // OK
						NodeList seqNode = eChild.getElementsByTagName("xs:sequence");
						for(int index1=0;index1<seqNode.getLength();index1++){
							Node sequence = seqNode.item(index1);
							if(sequence.getNodeType() == Element.ELEMENT_NODE){
								Element eSequence = (Element)sequence;
								NodeList inputParams = eSequence.getElementsByTagName("xs:element");
								for(int index2=0;index2<inputParams.getLength();index2++){
									Node par = inputParams.item(index2);
									if(par.getNodeType() == Element.ELEMENT_NODE){
										Element ePar = (Element)par;
										//System.out.println(ePar.getAttribute("type")); // OK
										input_params.add(ePar.getAttribute("name"));
									}
								}
							}
						}
						//System.out.println(input_params); //OK
						details.add(input_params);
						
						NodeList outParams = eChild.getElementsByTagName("xs:attribute");
						for(int index3=0;index3<outParams.getLength();index3++){
							Node outV = outParams.item(index3);
							if(outV.getNodeType() == Element.ELEMENT_NODE){
								Element eOut = (Element)outV;
								if(eOut.getAttribute("name").equalsIgnoreCase("value")){
									//System.out.println(eOut.getAttribute("type")); // OK
									ret_values.add(eOut.getAttribute("type"));
								}
								else{
									// NIENTE.
								}
							}
						}
						//System.out.println(ret_values); // OK
						details.add(ret_values);
					}
					else{
						// NIENTE.
					}
				} // non � un atomi term (dynamic o static)
				else if(eChild.getAttribute("id").contains("formula-")){
						if(eChild.getAttribute("name").equalsIgnoreCase(nm)){
							NodeList chNode = eChild.getElementsByTagName("xs:choice");
							for(int index4=0;index4<chNode.getLength();index4++){
								Node choice = chNode.item(index4);
								if(choice.getNodeType() == Element.ELEMENT_NODE){
									Element eChoice = (Element)choice;
									NodeList elem_list = eChoice.getElementsByTagName("xs:element");
									for(int index5=0;index5<elem_list.getLength();index5++){
										Node elem = elem_list.item(index5);
										if(elem.getNodeType() == Element.ELEMENT_NODE){
											Element eElem = (Element)elem;
											if(!eElem.getAttribute("name").equalsIgnoreCase("content")){ // argomenti in ingresso
												input_params.add(eElem.getAttribute("name"));
											}
											else{
												// nulla...
											}
										}
										
									}
								}
							}
							details.add(input_params);
							
							NodeList outValue = eChild.getElementsByTagName("xs:attribute");
							for(int index7=0;index7<outValue.getLength();index7++){
								Node retValue = outValue.item(index7);
								if(retValue.getNodeType() == Element.ELEMENT_NODE){
									Element eRetValue = (Element)retValue;
									if(eRetValue.getAttribute("name").equalsIgnoreCase("value")){
										ret_values.add(eRetValue.getAttribute("type"));
									}
									else{
										// niente...
									}
								}
							}
							details.add(ret_values);
						}
					
				}
				else{
					// niente...
				}
			}
		}
		return details;
		
	}
	
	
	public static Vector<String> getObjectNoPRTof(String s){
		Vector<String> objs = new Vector<String>();
			
			if(s.equalsIgnoreCase("Boolean_type")){
				objs.add("false");
				objs.add("true");
			}
			else if(s.equalsIgnoreCase("Integer_type")){
				int minBound = Integer.parseInt(getIntegerType().get(0));
				int maxBound = Integer.parseInt(getIntegerType().get(1));
				for(int i=minBound;i<=maxBound;i++){
					objs.add(String.valueOf(i));
				}
				
			}
			else if(s.equalsIgnoreCase("service")){ 
				objs.addAll(getServices(false));
			}
			else{ //dovrebbe entrare qui solo in caso di "role_type","location_type" e "status_type"
				Document doc = connect();
				Element root  = doc.getDocumentElement();
				NodeList rChilds = root.getChildNodes();
				for(int index=0;index<rChilds.getLength();index++){
					Node rNode = rChilds.item(index);
					if(rNode.getNodeType() == Element.ELEMENT_NODE){
						Element eNode = (Element)rNode;
						if(eNode.getAttribute("type").equalsIgnoreCase("role") && eNode.getAttribute("name").equalsIgnoreCase(s)){ // se sono role_type
							NodeList elements = eNode.getElementsByTagName("xs:enumeration");
							for(int index1=0;index1<elements.getLength();index1++){
								Node el = elements.item(index1);
								if(el.getNodeType() == Element.ELEMENT_NODE){
									Element eEl = (Element)el;
									objs.add(eEl.getAttribute("value"));
									
								}
							}
						}
						else if (eNode.getAttribute("name").equalsIgnoreCase(s)){ // non sono role_type
							NodeList els = eNode.getElementsByTagName("xs:enumeration");
							for(int index2=0;index2<els.getLength();index2++){
								Node em = els.item(index2);
								if(em.getNodeType() == Element.ELEMENT_NODE){
									Element eE = (Element)em;
									objs.add(eE.getAttribute("value"));
								}
							}
						}// CHIUDE ELSE
					}
				
				}
			}
			 
			//System.out.println(objs);
			return objs;
		}

	/*------------------------------------ METODI SET ---------------------------------------------*/
	/** METODO PER AGGIORNARE LE CAPABILITIES*/  //-- per ora nessun controllo --
	public static void setCapabilities(Vector<String> v){
		try{
			Vector<String> dcm = getCapabilities(false);
			Vector<String> user = v;
			
			 Vector<String> app1=null;
			 Vector<String> app2=null;
			 Vector<String> app3=null;
			 
			 if(dcm.containsAll(user) && user.containsAll(dcm)){
					// L'UTENTE NON HA CAMBIATO NULLA --> sul documento non faccio niente
					
			}
			else{
				app1 = (Vector<String>) dcm.clone();
				app1.retainAll(user); // in app1 ora ci sono tutti gli elementi in comune fra dcm e user
				
				app2 = (Vector<String>) dcm.clone(); 
				app2.removeAll(app1); // --> Elementi da cancellare dal documento xml
				//System.out.println("elementi presenti in dcm MA NON in user: " + app2);
				
				app3 = (Vector<String>) user.clone();
				app3.removeAll(dcm); // Elementi da aggiungere nel documento xml
				//System.out.println("elementi presenti in user MA NON in dcm: " + app3);  
				
				/* In app2 ci sono gli elementi da cancellare dal documento xml 
				 * In app3 ci sono gli elementi da aggiungere nel documento xml */
				Document doc = connect(Constants.getXSD_name_file());
				Element root = doc.getDocumentElement();
				Node parent=null; 
				NodeList nList = root.getChildNodes();
				for(int index=0;index<nList.getLength();index++){
					Node nNode = nList.item(index);
					if(nNode.getNodeType() == Element.ELEMENT_NODE){
						Element eElem = (Element) nNode;
						if(eElem.getAttribute("name").equalsIgnoreCase("Capability_type")){
							NodeList nNodeList = eElem.getChildNodes();
							for(int index1=0;index1<nNodeList.getLength();index1++){
								Node nRestr = nNodeList.item(index1);
								if(nRestr.getNodeType() == Element.ELEMENT_NODE){
									Element eRestr = (Element)nRestr;
									if(eRestr.getNodeName().equalsIgnoreCase("xs:restriction")){
										parent = eRestr; // � il nodo xs:restriction di capability_type
										//System.out.println("SONO QUI: " + eRestr.getNodeName());
									}
									else{
										//Niente; ma non dovrebbe capitare.
									}
								}
							}
						}
						else{
							// Niente
						}
					}
				}
				// QUI HO parent=xs:restriction di capability_type
				
				// TUTTI QUELLI CHE HO IN APP2 LI ELIMINO DAL DOCUMENTO
				NodeList capList = parent.getChildNodes();
				for(int index2=0;index2<capList.getLength();index2++){
					Node cap = capList.item(index2);
					if(cap.getNodeType() == Element.ELEMENT_NODE){
						Element eCap = (Element)cap;
						for(int i=0;i<app2.size();i++){
							if(eCap.getAttribute("value").equalsIgnoreCase(app2.get(i))){
								parent.removeChild(eCap); 
							}
							else{
								// niente
							}
						}
					}
				}
				
				
				// TUTTI QUELLI CHE HO IN APP3 LI AGGIUNGO NEL DOCUMENTO
				for(int j=0;j<app3.size();j++){
					Element newCap = (Element)doc.createElement("xs:enumeration");
					newCap.setAttributeNode(doc.createAttribute("value"));
					newCap.setAttribute("value", app3.get(j));
					
					parent.appendChild(newCap);   
				}
				
				confirm(Constants.getXSD_name_file());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Metodo per scrivere sul documento InitialState.xml (suppongo che il documento esista gi�!) */
	public static void setInitialState(Vector<Vector<Vector<String>>> inState, Vector<Vector<String>> t ) { 
		
		try {
			
			Document doc = connect("InitialState.xml");
			Element root = doc.getDocumentElement();
			
			for(int i=0;i<inState.size();i++) {
				
				Element term = doc.createElement(inState.get(i).firstElement().firstElement());
				term.setAttributeNode(doc.createAttribute("value"));
				term.setAttribute("value", inState.get(i).lastElement().firstElement());
				for(int j=0;j<t.get(i).size();j++) {
					Element arg = doc.createElement(t.get(i).get(j));
					Text arg_text = doc.createTextNode(inState.get(i).get(1).get(j));
					arg.appendChild(arg_text);
					
					term.appendChild(arg);
				}
				
				root.appendChild(term);
				
			}
			
			confirm("InitialState.xml");
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** Metodo per ripulire tutto il documento "InitialState.xml" */
	public static void resetInitialState() {
		
		try {
			Document doc = connect("InitialState.xml");
			Element root = doc.getDocumentElement();
			NodeList rootChilds = root.getChildNodes();
			for(int i=0;i<rootChilds.getLength();i++) {
				Node child = rootChilds.item(i);
				if(child.getNodeType() == Element.ELEMENT_NODE) {
					Element eChild = (Element)child;
					root.removeChild(eChild);
					// PER RICOMPATTARE IL FILE 
					while(root.hasChildNodes()) {
						root.removeChild(root.getFirstChild());
					}
				}
			}
			
			confirm("InitialState.xml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Metodo per scrivere sul documento Goal.xml (suppongo che il documento esista gi�!) */
	public static void setGoalCondition(Vector<Vector<Vector<String>>> g, Vector<Vector<String>> t ) { 
		
		try {
			Document doc = connect("Goal.xml");
			Element root = doc.getDocumentElement();
			
			for(int i=0;i<g.size();i++) {
				
				Element tr_fr = doc.createElement(g.get(i).firstElement().firstElement());
				tr_fr.setAttributeNode(doc.createAttribute("value"));
				tr_fr.setAttribute("value", g.get(i).lastElement().firstElement());
				for(int j=0;j<t.get(i).size();j++) {
					Element arg = doc.createElement(t.get(i).get(j));
					Text arg_text = doc.createTextNode(g.get(i).get(1).get(j));
					arg.appendChild(arg_text);
					
					tr_fr.appendChild(arg);
				}
				
				root.appendChild(tr_fr);
				
				
			}
			
			confirm("Goal.xml");
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** Metodo per ripulire tutto il documento "Goal.xml" */
	public static void resetGoalCondition() {
		
		try {
			Document doc = connect("Goal.xml");
			Element root = doc.getDocumentElement();
			NodeList rootChilds = root.getChildNodes();
			for(int i=0;i<rootChilds.getLength();i++) {
				Node child = rootChilds.item(i);
				if(child.getNodeType() == Element.ELEMENT_NODE) {
					Element eChild = (Element)child;
					
					root.removeChild(eChild);
					// PER RICOMPATTARE IL FILE
					while(root.hasChildNodes()) {
						root.removeChild(root.getFirstChild());
					}
				}
			}
			
		
			confirm("Goal.xml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void scriviFile(String nomeFile, StringBuffer buffer) {
		 
		  File file = null;
	    FileWriter fw = null;
		   
		   try {
			file = new File(nomeFile);
			fw = new FileWriter(file);
			fw.write(buffer.toString());
			fw.close();
			
		   //fw.flush();
		   //fw.close();
		   }
		   catch(IOException e) {
		   e.printStackTrace();
		   }
	}
	
	public static void copy_the_content_of_a_file_into_another_file(String sourceFile, String destinationFile) {
		 
		FileReader fr = null;
        FileWriter fw = null;
		   
		   try {
		
		File file_s = new File(sourceFile);
	    File file_d = new File(destinationFile);
	    
		   fr = new FileReader(file_s);
	       fw = new FileWriter(file_d);
	            int c = fr.read();
	            while(c!=-1) {
	                fw.write(c);
	                c = fr.read();
	            }
	        } catch(IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
					fr.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        }
			
		   //fw.flush();
		   //fw.close();
		   }

	
	
		
}


