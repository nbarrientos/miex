import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.ArrayList;

class InputParserEx
{

public static void main(String [] args)
{

		ArrayList categorias = new ArrayList();

		try
		{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File f = new File(args[0]);
		Document tree = builder.parse(f);
		recorrerArbolDOM(tree,categorias);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		for(int i=0; i < categorias.size(); i++)
			System.out.println(categorias.get(i));

}

public static void recorrerArbolDOM(Node nodo, ArrayList categorias)
{
		if(nodo != null)
		{
			if(nodo.getNodeName() == "d")
			{
				System.out.println("Categoria: " + nodo.getChildNodes().item(0).getNodeValue());
				categorias.add(nodo.getChildNodes().item(0).getNodeValue());
			}

			if(nodo.getNodeName() == "body")
				System.out.println("Cuerpo: " + nodo.getChildNodes().item(0).getNodeValue());

			// Seguir iterando				
			NodeList hijos = nodo.getChildNodes();
			for(int i=0; i < hijos.getLength(); i++)
			{
				Node hijo = hijos.item(i);
				recorrerArbolDOM(hijo,categorias);
			}

		}

}

}
