/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmXml;

import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import map.MapTag;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Конвертер из .osm xml сразу в карту
 * @author preobrazhentsev
 */
public class OSMFlyConverter
{
	/**
	 * Класс приема событий разбора .osm на лету
	 */
	private class SaxFlyHandler extends DefaultHandler
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
		 * Конструктор
		 */
		public SaxFlyHandler()
		{
			Init();
		}

		/**
		 * Инициализация класса
		 */
		private void Init()
		{
			nodes = new ArrayList<OSMFileNode>();
			ways = new ArrayList<OSMFileWay>();
			relations = new ArrayList<OSMFileRelation>();
			bounds = new OSMFileBounds();
			tempTags = new ArrayList<MapTag>();
		}

		/**
		 * Найдено начало документа
		 */
		@Override
		public void startDocument()
		{
			Init();
		}

		/**
		 * Найден конец документа
		 */
		@Override
		public void endDocument()
		{
		}

		/**
		 * Найдено начало определения элемента
		 * @param uri
		 * @param pLocalName
		 * @param pName
		 * @param pAttributes
		 * @throws SAXException 
		 */
		@Override
		public void startElement(String uri, String pLocalName, String pName, Attributes pAttributes) throws SAXException
		{
			try
			{
				if (pName.compareTo(OSMXMLNames.BOUNDS) == 0)
				{
					ParseBounds(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.NODE) == 0)
				{
					ParseNode(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.WAY) == 0)
				{
					ParseWay(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.NODE_IN_WAY) == 0)
				{
					ParseWayNode(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.RELATION) == 0)
				{
					ParseRelation(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.TAG) == 0)
				{
					ParseTag(pAttributes);
				}
				if (pName.compareTo(OSMXMLNames.RELATION_MEMBER) == 0)
				{
					ParseRelationMember(pAttributes);
				}
			}
			catch (Exception ex)
			{
				throw new SAXException();
			}
		}

		/**
		 * Найден конец определения элемента
		 * @param uri
		 * @param localName
		 * @param pName
		 * @throws SAXException 
		 */
		@Override
		public void endElement(String uri, String localName, String pName) throws SAXException
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

		/**
		 * Разбор тега
		 * @param pAttributes
		 * @throws Exception 
		 */
		private void ParseTag(Attributes pAttributes) throws Exception
		{
			try
			{
				tempTags.add(new MapTag(pAttributes.getValue("k"), pAttributes.getValue("v")));
			}
			catch (Exception e)
			{
				throw e;
			}
		}

		/**
		 * Разобрать аттрибуты границ
		 * @param pAttr
		 * @throws Exception 
		 */
		private void ParseBounds(Attributes pAttributes) throws Exception
		{
			try
			{
				bounds.minLatitude = Double.valueOf(pAttributes.getValue("minlat"));
				bounds.minLongitude = Double.valueOf(pAttributes.getValue("minlon"));
				bounds.maxLatitude = Double.valueOf(pAttributes.getValue("maxlat"));
				bounds.maxLongitude = Double.valueOf(pAttributes.getValue("maxlon"));
			}
			catch (Exception e)
			{
				throw e;
			}
		}

		/**
		 * Разбор точки
		 * @param pAttributes
		 * @throws Exception 
		 */
		private void ParseNode(Attributes pAttributes) throws Exception
		{
			try
			{
				tempNode = new OSMFileNode();
				tempNode.id = Long.valueOf(pAttributes.getValue("id"));
				tempNode.latitude = Double.valueOf(pAttributes.getValue("lat"));
				tempNode.longitude = Double.valueOf(pAttributes.getValue("lon"));
				tempTags = new ArrayList<MapTag>();
			}
			catch (Exception e)
			{
				throw e;
			}
		}

		/**
		 * Разбор Way
		 * @param pAttributes
		 * @throws Exception 
		 */
		private void ParseWay(Attributes pAttributes) throws Exception
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
		 * @param pAttributes
		 * @throws Exception 
		 */
		private void ParseWayNode(Attributes pAttributes) throws Exception
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
		 * @param pAttributes
		 * @throws Exception 
		 */
		private void ParseRelation(Attributes pAttributes) throws Exception
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
		 * @param pAttributes
		 * @throws Exception 
		 */
		private void ParseRelationMember(Attributes pAttributes) throws Exception
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
	private SaxFlyHandler handler;

	/**
	 * Конструктор
	 */
	public OSMFlyConverter()
	{
		handler = new SaxFlyHandler();
	}

	/**
	 * Конвертировать .osm xml в карту | должна возвращать Map
	 * @param pSource
	 * @throws Exception  
	 */
	public void Convert(InputSource pSource) throws Exception
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
	 * @return 
	 */
	public OSMFileBounds getParserBounds()
	{
		return handler.bounds;
	}

	/**
	 * Получить точки
	 * @return 
	 */
	public ArrayList<OSMFileNode> getParserNodes()
	{
		return (ArrayList<OSMFileNode>) handler.nodes.clone();
	}

	/**
	 * Получить линии
	 * @return 
	 */
	public ArrayList<OSMFileWay> getParserWays()
	{
		return (ArrayList<OSMFileWay>) handler.ways.clone();
	}
	
	/**
	 * Получить отношения
	 * @return 
	 */
	public ArrayList<OSMFileRelation> getParserRelations()
	{
		return (ArrayList<OSMFileRelation>) handler.relations.clone();
	}
}
