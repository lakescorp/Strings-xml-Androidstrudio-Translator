package translatorforandroidapps;

	import com.darkprograms.speech.translator.GoogleTranslate;

	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Scanner;

	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.ParserConfigurationException;
	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.transform.Transformer;
	import javax.xml.transform.TransformerFactory;
	import javax.xml.transform.dom.DOMSource;
	import javax.xml.transform.stream.StreamResult;
	import org.w3c.dom.Attr;
	import org.w3c.dom.Document;
	import org.w3c.dom.Element;
	import org.w3c.dom.NamedNodeMap;
	import org.w3c.dom.Node;
	import org.w3c.dom.NodeList;

	import java.io.File;


	public class translationandroidstudioorientatted {
		 static ArrayList<String> input = new ArrayList<String>();
		 static ArrayList<String> alllenguages = new ArrayList<String>();
		 static ArrayList<String> namecode = new ArrayList<String>();
		 static ArrayList<String> xmlarray = new ArrayList<String>();
		 static String inputPath = "programfiles/strings.xml"; // Path of the input file
		 static String lenguagePath = "programfiles/LenguageCodesToBeTranslated.txt"; // Path of the input file
		 static String outputPath = ""; // Path of the output path
		 static int waitime = 3000; // Waittime between translations
		 
		 

		
		 public static void main( String[] args ) throws IOException, InterruptedException, ParserConfigurationException {

			 

			 	/* Ask for lenguage path */
			 
			    Scanner myObj = new Scanner(System.in);  // Create scanner
			    
			    
			    System.out.println("The default lenguage path is \""+lenguagePath+"\"");
			    System.out.println("Is your lenguage file in the default path?(Y/n)");

			    String customlenguage = myObj.next().toLowerCase();  
			    
			    if(customlenguage.matches("[Nn]")) { // If they want a different path
			    	
			    	  System.out.println("Please introduce your custom lenguage path");

			    	  lenguagePath = myObj.next();
			    	  
			    	  System.out.println("Your custom lenguage path is: " + lenguagePath);
			    }
			    
				 alllenguages();
			    
			 	/* Ask for input path */
			    
			    System.out.println("The default input path is \""+inputPath+"\"");
			    System.out.println("Is your input file in the default path?(Y/n)");

			    String custominput = myObj.next().toLowerCase();  
			    
			    if(custominput.matches("[Nn]")) { // If they want a different path
			    	
			    	  System.out.println("Please introduce your custom input path");

			    	  inputPath = myObj.next();
			    	  
			    	  System.out.println("Your custom input path is: " + inputPath);
			    }
			    readXml(inputPath);
				 
				 /* Ask for output path */
				    
				 System.out.println("The default output path is \" the project path \"");
				 System.out.println("Do you want this default path?(Y/n)");

				 String customoutput = myObj.next().toLowerCase();  // Read user input
				 if(customoutput.matches("[Nn]")) {
					 System.out.println("Please introduce your custom output path");

					 outputPath = myObj.next();
				    	  
					 System.out.println("Your custom output path is: " + outputPath);
				 }
			    
			 	/* Ask for waitime path */
			    
			    System.out.println("The default Waittime is \""+waitime+"\"");
			    System.out.println("Do you want this recommended Waittime?(Y/n)");
			    
			    String waitTime = myObj.next().toLowerCase();  // Read user input
			    
			    if(waitTime.matches("[Nn]")) {
			    	  System.out.println("Please introduce your custom Waittime");

			    	  waitime = myObj.nextInt();
			    	  
			    	  System.out.println("Your custom waitime is: " + waitime);
			    }
			    
			    myObj.close(); // close scanner
			    

			 /* Create all translations */
			 for(String lenguage: alllenguages) {
				 translatearray(lenguage);
				 System.out.println("File created: " + lenguage);
				 Thread.sleep(waitime);
			 }

			 System.out.println("Program has finished");
			 
		 }


		public static String translatorfunction(String input, String finalidioma) throws IOException { // Translate the strings

			return 	GoogleTranslate.translate(finalidioma, input);
		}
		
		static void readXml(String path) { // Read original xml
			try {

				File file = new File(path);

				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
			                             .newDocumentBuilder();

				Document doc = dBuilder.parse(file);

				//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				if (doc.hasChildNodes()) {

					printNote(doc.getChildNodes());

				}

			    } catch (Exception e) {
				System.out.println(e.getMessage());
			    }
		}
		
	    static void translatearray(String lenguage)  throws IOException, ParserConfigurationException  // Create the xml
	    {
	    	
	          int tempnumber = 0; // Temp number for (<string name="...">)
	          new File(outputPath+"values-"+lenguage+"/").mkdirs(); // Create path
	   		  DocumentBuilderFactory dbFactory;
			  DocumentBuilder dBuilder;
			  Document doc;
			 
	          dbFactory = DocumentBuilderFactory.newInstance();
	          dBuilder = dbFactory.newDocumentBuilder();
	          doc = dBuilder.newDocument();
	            
			 
		      try {
     
		          // root element
		          Element rootElement = doc.createElement("resources"); // Create resources in document
		          doc.appendChild(rootElement); // Add resources in document

		          // string element


					 for(String mo : input) { // automated addition of string element

						 String temp = translatorfunction(mo, lenguage);
				          
				          Element stringelement = doc.createElement("string"); // Create string in document
				          Attr attrType1 = doc.createAttribute("name"); // Create atribute name
				          attrType1.setValue(namecode.get(tempnumber)); // Add to "name" the word code
				          stringelement.setAttributeNode(attrType1); // Add atribute to string elemt
				          stringelement.appendChild(doc.createTextNode(temp)); // Add translated word to string
				          rootElement.appendChild(stringelement); // Add string element to document
				          
				          tempnumber++;
					 }



		          // write the content into xml file
		          TransformerFactory transformerFactory = TransformerFactory.newInstance();
		          Transformer transformer = transformerFactory.newTransformer();
		          DOMSource source = new DOMSource(doc);
		          StreamResult result = new StreamResult(new File(outputPath+"values-"+lenguage+"/strings.xml")); // Write file
		          transformer.transform(source, result);
		          
		          // Output to console for testing//
		          
		         // StreamResult consoleResult = new StreamResult(System.out);
		          //transformer.transform(source, consoleResult);
		          
		       } catch (Exception e) {
		          e.printStackTrace();
		       }


	    }
	
	    
	    static void alllenguages() { // Read all lenguages
	    	   try (FileReader reader = new FileReader(lenguagePath);
	    	              BufferedReader br = new BufferedReader(reader)) {

	    	             // read line by line
	    	             String line;
	    	             while ((line = br.readLine()) != null) {
	    	                 alllenguages.add(line);
	    	             }
	    	     System.out.println("Lenguage file readed");
				// System.out.println(alllenguages);

	    	         } catch (IOException e) {
	    	             System.err.format("IOException: %s%n", e);
	    	         }
	    			
	    	
	    }
		   private static void printNote(NodeList nodeList) { // Get info of the nodes

			    for (int count = 0; count < nodeList.getLength(); count++) { // Pass program for all the nodes

				Node tempNode = nodeList.item(count); // Take the node

				// make sure it's element node.
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

					// get node name and value
				//	System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
					//System.out.println("VAlue =" + tempNode.getTextContent());
					if(tempNode.getNodeName()=="string") { // add only info of string nodes

						input.add(tempNode.getTextContent());

					if (tempNode.hasAttributes()) { // see if it has atributes

						// get attributes names and values
						NamedNodeMap nodeMap = tempNode.getAttributes();
						Boolean untranslatable = false; // boolean for discard de untranslatable words
						for (int i = 0; i < nodeMap.getLength(); i++) { // see all atributes in node

							Node node = nodeMap.item(i);

							//System.out.println("atributo : " + node.getNodeName());
							//System.out.println("namecode : " + node.getNodeValue());
							namecode.add(node.getNodeValue()); // add value of node
							if(node.getNodeName()=="translatable") { // if its untranslatable discard

								untranslatable=true;
							}


						}
						if(untranslatable==true) { // delete the discarded
							//System.out.println(namecode.size());
							//System.out.println(input.size());

							input.remove(namecode.size()-1);	
							namecode.remove(input.size()-1);							
							namecode.remove(input.size()-1);
							
						}

					}

					}

					if (tempNode.hasChildNodes()) {

						// loop again if has child nodes
						printNote(tempNode.getChildNodes());

					}

					//System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

				}

			    }
		   }

	}

