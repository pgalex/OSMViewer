/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Класс изображения, при необходимости создаваемого из файла
 *
 * @author abc
 */
public class ImageFromFile implements ReadableMapData, WritableMapData
{
	/**
	 * Объект изображения, создаваемый динамически при запросе
	 */
	private Image image;
	/**
	 * Имя файла изображения
	 */
	private String imageFileName;

	/**
	 * Конструктор
	 */
	public ImageFromFile()
	{
		image = null;
		imageFileName = "";
	}

	/**
	 * Контруктор
	 *
	 * @param pFileName Имя файла изображения
	 */
	public ImageFromFile(String pFileName)
	{
		image = null;
		imageFileName = pFileName;
	}

	/**
	 * Загрузить и выдать изображения из файла
	 *
	 * @return изображение
	 */
	public Image getImage()
	{
		if (image == null)
		{
			try
			{
				image = ImageIO.read(new File(getImageFileName()));
			}
			catch (IOException ex)
			{
				image = null;
			}
		}
		return image;
	}

	/**
	 * Считать из потока
	 *
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			setImageFileName(pInput.readUTF());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Записать в поток
	 *
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeUTF(getImageFileName());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Получить имя файла изображения
	 *
	 * @return the imageFileName
	 */
	public String getImageFileName()
	{
		return imageFileName;
	}

	/**
	 * Установить новое имя файла изображения
	 *
	 * @param pNewFileName новое имя файла изображения
	 */
	public void setImageFileName(String pNewFileName)
	{
		imageFileName = pNewFileName;
		image = null;
	}
}
