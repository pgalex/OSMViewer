/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Класс оберкта изображения, с возможностью чтения записи
 *
 * @author abc
 */
public class IOIcon implements ReadableMapData, WritableMapData
{
	/**
	 * Формат в котором изображение записывается в файл
	 */
	private static final String IMAGE_FORMAT = "png";
	/**
	 * Изображение
	 */
	private BufferedImage image;

	/**
	 * Конструктор
	 */
	public IOIcon()
	{
		image = null;
	}

	/**
	 * Контруктор
	 *
	 * @param pImage Изображение
	 */
	public IOIcon(BufferedImage pImage)
	{
		image = pImage;
	}

	/**
	 * Конструктор создания изображения из файла
	 *
	 * @param pFileName имя файла
	 * @throws IOException ошибка чтения
	 */
	public IOIcon(String pFileName) throws IOException
	{
		try
		{
			image = ImageIO.read(new File(pFileName));
		}
		catch (Exception e)
		{
			image = null;
			throw new IOException(e);
		}
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
			boolean isValidImage = pInput.readBoolean();
			if (isValidImage)
				image = ImageIO.read(pInput);
			else
				image = null;
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
			boolean isValidImage = (image != null);
			pOutput.writeBoolean(isValidImage);
			if (isValidImage)
				ImageIO.write(image, IMAGE_FORMAT, pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Получить изображение
	 *
	 * @return изображение
	 */
	public BufferedImage getImage()
	{
		return image;
	}
}
