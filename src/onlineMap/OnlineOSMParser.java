package onlineMap;

import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import map.MapTag;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import osmXml.*;

/**
 * Reads .osm data and converts it to OnlineMap
 *
 * @author preobrazhentsev
 */
public class OnlineOSMParser
{
	/**
	 * Xml parsing events handler for online work
	 */
	private class OnlineSaxHandler extends DefaultHandler
	{
		private ArrayList<OSMFileNode> nodes;
		private ArrayList<OSMFileWay> ways;
		private ArrayList<OSMFileRelation> relations;
		private OSMFileBounds bounds;
		private OSMFileNode tempNode;
		private OSMFileWay tempWay;
		private OSMFileRelation tempRelation;
		public ArrayList<MapTag> tempTags;

		/**
		 * Constructor
		 */
		public OnlineSaxHandler()
		{
			nodes = new ArrayList<OSMFileNode>();
			ways = new ArrayList<OSMFileWay>();
			relations = new ArrayList<OSMFileRelation>();
			bounds = new OSMFileBounds();
			tempTags = new ArrayList<MapTag>();
		}

		/**
		 * XML document begining founded
		 */
		@Override
		public void startDocument()
		{
			nodes.clear();
			ways.clear();
			relations.clear();
			tempTags.clear();
			bounds.setLatitudeMaximum(0);
			bounds.setLatitudeMinimum(0);
			bounds.setLongitudeMaximum(0);
			bounds.setLongitudeMinimum(0);
		}

		/**
		 * Defenition of xml element founded. Найдено начало определения элемента
		 *
		 * @param uri
		 * @param pLocalName
		 * @param pName element name
		 * @param pAttributes element attributes
		 * @throws SAXException
		 */
		@Override
		public void startElement(String uri, String pLocalName, String pName, Attributes pAttributes) throws SAXException
		{
			try
			{
				if (pName.compareTo(OSMXMLNames.BOUNDS) == 0)
				{
					parseBounds(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.NODE) == 0)
				{
					parseNode(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.WAY) == 0)
				{
					parseWay(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.NODE_IN_WAY) == 0)
				{
					parseWayNode(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.RELATION) == 0)
				{
					parseRelation(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.TAG) == 0)
				{
					parseTag(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.RELATION_MEMBER) == 0)
				{
					parseRelationMember(pAttributes);
				}
			}
			catch (Exception ex)
			{
				throw new SAXException();
			}
		}

		/**
		 * Xml element end founded. Найден конец определения элемента
		 *
		 * @param uri
		 * @param localName
		 * @param pName element name
		 * @throws SAXException
		 */
		@Override
		public void endElement(String uri, String localName, String pName) throws SAXException
		{
			try
			{
				if (pName.compareTo(OSMXMLNames.NODE) == 0)
				{
					tempNode.tags = tempTags;
					nodes.add(tempNode);
				}
				if (pName.compareTo(OSMXMLNames.WAY) == 0)
				{
					tempWay.tags = tempTags;
					ways.add(tempWay);
				}
				if (pName.compareTo(OSMXMLNames.RELATION) == 0)
				{
					tempRelation.tags = tempTags;
					relations.add(tempRelation);
				}
			}
			catch (Exception ex)
			{
				throw new SAXException();
			}
		}

		/**
		 * Paring osm tag. Разбор тега
		 *
		 * @param pAttributes attributes of "tag" element
		 * @throws Exception error while parsing
		 */
		private void parseTag(Attributes pAttributes) throws Exception
		{
			try
			{
				tempTags.add(new MapTag(pAttributes.getValue("k"), pAttributes.getValue("v")));
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}

		/**
		 * Parse bounds element. Разобрать аттрибуты границ
		 *
		 * @param pAttributes "bounds" element attributes
		 * @throws Exception error while parsing
		 */
		private void parseBounds(Attributes pAttributes) throws Exception
		{
			try
			{
				bounds.setLatitudeMinimum(Double.valueOf(pAttributes.getValue("minlat")));
				bounds.setLongitudeMinimum(Double.valueOf(pAttributes.getValue("minlon")));
				bounds.setLatitudeMaximum(Double.valueOf(pAttributes.getValue("maxlat")));
				bounds.setLongitudeMaximum(Double.valueOf(pAttributes.getValue("maxlon")));
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}

		/**
		 * Parsing osm xml "node". Разбор точки
		 *
		 * @param pAttributes "node" element attributes
		 * @throws Exception error while parsing
		 */
		private void parseNode(Attributes pAttributes) throws Exception
		{
			try
			{
				tempNode = new OSMFileNode();
				tempNode.id = Long.valueOf(pAttributes.getValue("id"));
				tempNode.latitude = Double.valueOf(pAttributes.getValue("lat"));
				tempNode.longitude = Double.valueOf(pAttributes.getValue("lon"));
				tempTags = new ArrayList<MapTag>();
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}

		/**
		 * Разбор Way
		 *
		 * @param pAttributes
		 * @throws Exception
		 */
		private void parseWay(Attributes pAttributes) throws Exception
		{
			try
			{
				tempWay = new OSMFileWay();
				tempWay.id = Long.valueOf(pAttributes.getValue("id"));
				tempTags = new ArrayList<MapTag>();
			}
			catch (Exception e)
			{
				throw e;
			}
		}

		/**
		 * Разбор id точки в составе линии
		 *
		 * @param pAttributes
		 * @throws Exception
		 */
		private void parseWayNode(Attributes pAttributes) throws Exception
		{
			try
			{
				tempWay.nodesIds.add(Long.valueOf(pAttributes.getValue("ref")));
			}
			catch (Exception e)
			{
				throw e;
			}
		}

		/**
		 * Разбор отношения
		 *
		 * @param pAttributes
		 * @throws Exception
		 */
		private void parseRelation(Attributes pAttributes) throws Exception
		{
			try
			{
				tempRelation = new OSMFileRelation();
				tempRelation.id = Long.valueOf(pAttributes.getValue("id"));
				tempTags = new ArrayList<MapTag>();
			}
			catch (Exception e)
			{
				throw e;
			}
		}

		/**
		 * Разбор member из relation
		 *
		 * @param pAttributes
		 * @throws Exception
		 */
		private void parseRelationMember(Attributes pAttributes) throws Exception
		{
			try
			{
				OSMFileRelationMember tempRelationMember = new OSMFileRelationMember();
				tempRelationMember.ref = Long.valueOf(pAttributes.getValue("ref"));
				tempRelationMember.type = pAttributes.getValue("type");
				tempRelationMember.role = pAttributes.getValue("role");
				tempRelation.members.add(tempRelationMember);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	/**
	 * Объект принимающий сообщения разбора
	 */
	private OnlineSaxHandler handler;

	/**
	 * Конструктор
	 */
	public OnlineOSMParser()
	{
		handler = new OnlineSaxHandler();
	}

	/**
	 * Конвертировать .osm xml в карту | должна возвращать Map
	 *
	 * @param pSource
	 * @throws Exception
	 */
	public void convert(InputSource pSource) throws Exception
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			parser.parse(pSource, handler);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Получить считанные границы карты
	 *
	 * @return
	 */
	public OSMFileBounds getParserBounds()
	{
		return handler.bounds;
	}

	/**
	 * Получить точки
	 *
	 * @return
	 */
	public ArrayList<OSMFileNode> getParserNodes()
	{
		return handler.nodes;
	}

	/**
	 * Получить линии
	 *
	 * @return
	 */
	public ArrayList<OSMFileWay> getParserWays()
	{
		return handler.ways;
	}

	/**
	 * Получить отношения
	 *
	 * @return
	 */
	public ArrayList<OSMFileRelation> getParserRelations()
	{
		return handler.relations;
	}
}
