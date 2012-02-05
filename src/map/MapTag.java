/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import drawingStyle.ReadableMapData;
import drawingStyle.WriteableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Тэг
 * @author preobrazhentsev
 */
public class MapTag implements WriteableMapData, ReadableMapData
{
	/**
	 * ключ
	 */
	private String key;
	/**
	 * значение
	 */
	private String value;

	/**
	 * Конструктор
	 */
	public MapTag()
	{
		key = "";
		value = "";
	}

	/**
	 * Контсруктор
	 * @param pKey ключ
	 * @param pValue значение
	 */
	public MapTag(String pKey, String pValue)
	{
		key = pKey;
		value = pValue;
	}

	/**
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @param pKey the pKey to set
	 */
	public void setKey(String pKey)
	{
		key = pKey;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param pValue the pValue to set
	 */
	public void setValue(String pValue)
	{
		value = pValue;
	}

	/**
	 * Сравнить теги
	 * @param pTag тег для сравнения
	 * @return  
	 */
	public boolean compareTo(MapTag pTag)
	{
		if ((pTag.key.compareToIgnoreCase(key) == 0) && (pTag.value.compareToIgnoreCase(value) == 0))
			return true;
		else
			return false;
	}

	/**
	 * Записать в поток
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void WriteToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeUTF(key);
			pOutput.writeUTF(value);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Считать из потока
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void ReadFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			key = pInput.readUTF();
			value = pInput.readUTF();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
