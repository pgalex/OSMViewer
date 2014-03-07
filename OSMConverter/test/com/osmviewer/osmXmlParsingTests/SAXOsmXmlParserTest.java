package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.osmXmlSAXParsing.SAXOsmXmlParser;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of SAXOsmXmlParser class
 *
 * @author pgalex
 */
public class SAXOsmXmlParserTest
{
	/**
	 * Test parsing normal work
	 *
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws ParsingOsmErrorException
	 * @throws java.io.UnsupportedEncodingException
	 */
	@Test
	public void parsingNormalWorkTest() throws FileNotFoundException, IllegalArgumentException, ParsingOsmErrorException, UnsupportedEncodingException
	{
		final String testOSMDocument = "﻿<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "\n"
						+ "<osm version=\"0.6\" generator=\"CGImap 0.0.2\">\n"
						+ " \n"
						+ "\n"
						+ "<bounds minlat=\"10.1000000\" minlon=\"20.1000000\" maxlat=\"10.3000000\" maxlon=\"20.3000000\"/>\n"
						+ " \n"
						+ "\n"
						+ "<node id=\"123456789\" lat=\"55.55\" lon=\"38.38\" user=\"Kostik\" uid=\"384084\" visible=\"true\" version=\"3\" changeset=\"7297715\" timestamp=\"2011-02-15T19:14:26Z\">\n"
						+ "  <tag k=\"name\" v=\"Осёнка\" /> \n"
						+ "  <tag k=\"railway\" v=\"halt\" /> \n"
						+ "  </node>\n"
						+ "\n"
						+ "<node id=\"1233435413\" lat=\"55.2090925\" lon=\"38.6016506\" user=\"pgalex\" uid=\"414096\" visible=\"true\" version=\"1\" changeset=\"7773525\" timestamp=\"2011-04-05T10:37:48Z\" />\n"
						+ "<node id=\"1233435417\" lat=\"55.2086321\" lon=\"38.6015777\" user=\"pgalex\" uid=\"414096\" visible=\"true\" version=\"1\" changeset=\"7773525\" timestamp=\"2011-04-05T10:37:48Z\" />\n"
						+ "<node id=\"1233435465\" lat=\"55.2048968\" lon=\"38.6050110\" user=\"pgalex\" uid=\"414096\" visible=\"true\" version=\"1\" changeset=\"7773525\" timestamp=\"2011-04-05T10:37:49Z\" />\n"
						+ "\n"
						+ "<way id=\"107289909\" user=\"AMDmi3\" uid=\"133332\" visible=\"true\" version=\"3\" changeset=\"8103960\" timestamp=\"2011-05-10T16:42:32Z\">\n"
						+ "  <nd ref=\"1233435465\" /> \n"
						+ "  <nd ref=\"1233435417\" /> \n"
						+ "  <nd ref=\"1233435413\" /> \n"
						+ "  <tag k=\"highway\" v=\"residential\" /> \n"
						+ "  <tag k=\"name\" v=\"Луговая улица\" /> \n"
						+ "  </way>\n"
						+ "\n"
						+ "<relation id=\"1693664\" user=\"347931\" uid=\"347929\" visible=\"true\" version=\"3\" changeset=\"9131098\" timestamp=\"2011-08-26T20:30:01Z\">\n"
						+ "  <member type=\"node\" ref=\"123456789\" role=\"admin_centre\" /> \n"
						+ "  <tag k=\"type\" v=\"boundary\" /> \n"
						+ "  </relation>\n"
						+ "\n"
						+ "\n"
						+ "</osm>";
		SAXOsmXmlParser parser = new SAXOsmXmlParser();
		TestOsmXmlParsingResultsHandler resultsHandler = new TestOsmXmlParsingResultsHandler();
		parser.parse(new ByteArrayInputStream(testOSMDocument.getBytes("UTF-8")), resultsHandler);

		assertEquals(4, resultsHandler.nodes.size());

		assertEquals(123456789, resultsHandler.nodes.get(0).getId());
		assertEquals(55.55, resultsHandler.nodes.get(0).getLatitude(), 0.001);
		assertEquals(38.38, resultsHandler.nodes.get(0).getLongitude(), 0.001);
		assertEquals(2, resultsHandler.nodes.get(0).getTagsCount());

		assertEquals(1233435413, resultsHandler.nodes.get(1).getId());
		assertEquals(55.2090925, resultsHandler.nodes.get(1).getLatitude(), 0.00001);
		assertEquals(38.6016506, resultsHandler.nodes.get(1).getLongitude(), 0.00001);

		assertEquals(1233435417, resultsHandler.nodes.get(2).getId());
		assertEquals(55.2086321, resultsHandler.nodes.get(2).getLatitude(), 0.00001);
		assertEquals(38.6015777, resultsHandler.nodes.get(2).getLongitude(), 0.00001);

		assertEquals(1233435465, resultsHandler.nodes.get(3).getId());
		assertEquals(55.2048968, resultsHandler.nodes.get(3).getLatitude(), 0.00001);
		assertEquals(38.6050110, resultsHandler.nodes.get(3).getLongitude(), 0.00001);

		assertEquals(1, resultsHandler.ways.size());
		assertEquals(3, resultsHandler.ways.get(0).getNodesIdsCount());
		assertEquals(2, resultsHandler.ways.get(0).getTagsCount());
	}
}
