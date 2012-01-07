/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 * Тэг
 * @author preobrazhentsev
 */
public class MapTag
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
		if ((pTag.key.compareTo(key) == 0) && (pTag.value.compareTo(value) == 0))
			return true;
		else
			return false;
	}
}
