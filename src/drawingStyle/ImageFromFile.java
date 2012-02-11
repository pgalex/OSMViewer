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
	public String imageFileName;

	/**
	 * Конструктор
	 */
	public ImageFromFile()
	{
		image = null;
		imageFileName = "";
	}

	/**
	 * Загрузить и выдать изображения из файла
	 * @return изображение
	 */
	public Image getImage()
	{
		if (image == null)
		{
			try
			{
				image = ImageIO.read(new File(imageFileName));
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
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void ReadFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			imageFileName = pInput.readUTF();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
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
			pOutput.writeUTF(imageFileName);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
