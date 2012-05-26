package onlineMap;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
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
		private ArrayList<OsmNode> nodes;
		private ArrayList<OsmWay> ways;
		private ArrayList<OsmRelation> relations;
		private OsmBounds bounds;
		private OsmNode tempNode;
		private OsmWay tempWay;
		private OsmRelation tempRelation;
		public ArrayList<OsmTag> tempTags;

		/**
		 * Constructor
		 */
		public OnlineSaxHandler()
		{
			nodes = new ArrayList<OsmNode>();
			ways = new ArrayList<OsmWay>();
			relations = new ArrayList<OsmRelation>();
			bounds = new OsmBounds();
			tempTags = new ArrayList<OsmTag>();
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
				if (pName.compareTo(OsmXMLNames.BOUNDS) == 0)
				{
					parseBounds(pAttributes);
				}
				if (pName.compareTo(OsmXMLNames.NODE) == 0)
				{
					parseNode(pAttributes);
				}
				if (pName.compareTo(OsmXMLNames.WAY) == 0)
				{
					parseWay(pAttributes);
				}
				if (pName.compareTo(OsmXMLNames.NODE_IN_WAY) == 0)
				{
					parseWayNode(pAttributes);
				}
				if (pName.compareTo(OsmXMLNames.RELATION) == 0)
				{
					parseRelation(pAttributes);
				}
				if (pName.compareTo(OsmXMLNames.TAG) == 0)
				{
					parseTag(pAttributes);
				}
				if (pName.compareTo(OsmXMLNames.RELATION_MEMBER) == 0)
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
				if (pName.compareTo(OsmXMLNames.NODE) == 0)
				{
					tempNode.setTags(tempTags);
					nodes.add(tempNode);
				}
				if (pName.compareTo(OsmXMLNames.WAY) == 0)
				{
					tempWay.setTags(tempTags);
					ways.add(tempWay);
				}
				if (pName.compareTo(OsmXMLNames.RELATION) == 0)
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
				tempTags.add(new OsmTag(pAttributes.getValue("k"), pAttributes.getValue("v")));
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
				tempNode = new OsmNode();
				tempNode.setId(Long.valueOf(pAttributes.getValue("id")));
				tempNode.setLatitude(Double.valueOf(pAttributes.getValue("lat")));
				tempNode.setLongitude(Double.valueOf(pAttributes.getValue("lon")));
				tempTags = new ArrayList<OsmTag>();
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
				tempWay = new OsmWay();
				tempWay.setId(Long.valueOf(pAttributes.getValue("id")));
				tempTags = new ArrayList<OsmTag>();
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
				tempWay.addNodeId(Long.valueOf(pAttributes.getValue("ref")));
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
				tempRelation = new OsmRelation();
				tempRelation.id = Long.valueOf(pAttributes.getValue("id"));
				tempTags = new ArrayList<OsmTag>();
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
				OsmRelationMember tempRelationMember = new OsmRelationMember();
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
	 * @param pInput
	 * @throws Exception
	 */
	public void convert(InputStream pInput) throws Exception
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			parser.parse(pInput, handler);
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
	public OsmBounds getBounds()
	{
		return handler.bounds;
	}

	/**
	 * Получить точки
	 *
	 * @return
	 */
	public ArrayList<OsmNode> getNodes()
	{
		return handler.nodes;
	}

	/**
	 * Получить линии
	 *
	 * @return
	 */
	public ArrayList<OsmWay> getWays()
	{
		return handler.ways;
	}

	/**
	 * Получить отношения
	 *
	 * @return
	 */
	public ArrayList<OsmRelation> getRelations()
	{
		return handler.relations;
	}
}
