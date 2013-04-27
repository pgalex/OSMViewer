package com.osmviewer.forms;

import com.osmviewer.mapDefenitionUtilities.Location;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Using to save user-set information between application runnings. Singleton
 *
 * @author pgalex
 */
public class UserSettings
{
	/**
	 * Name of file using to store settings
	 */
	private static final String SETTINS_FILE_NAME = "userSettings.dat";
	/**
	 * Singleton instance of settings
	 */
	private static UserSettings instance = null;
	/**
	 * Saved view position
	 */
	private Location viewPosition;

	/**
	 * Create with default settings
	 */
	private UserSettings()
	{
		viewPosition = new Location();

		try
		{
			readFromSettingsFile();
		}
		catch (IOException ex)
		{
			// can not read - default settings
			viewPosition = new Location(55.0905, 38.7788);
		}
	}

	/**
	 * Get user settings
	 *
	 * @return user settings instance
	 */
	public static UserSettings getInstance()
	{
		if (instance == null)
		{
			instance = new UserSettings();
		}

		return instance;
	}

	/**
	 * Read from standart settings file
	 *
	 * @throws IllegalArgumentException file is null
	 * @throws IOException error while reading
	 */
	private void readFromSettingsFile() throws IOException
	{
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(SETTINS_FILE_NAME));
			viewPosition.readFromStream(input);
			input.close();
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Save to settings file
	 *
	 * @throws IOException error while saving
	 */
	public void saveToSettingsFile() throws IOException
	{
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(SETTINS_FILE_NAME));
			viewPosition.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Get saved view position
	 *
	 * @return saved view position
	 */
	public Location getViewPosition()
	{
		return viewPosition;
	}

	/**
	 * Set view position to save
	 *
	 * @param viewPositionToSave saving view position
	 * @throws IllegalArgumentException viewPositionToSave is null
	 */
	public void setViewPosition(Location viewPositionToSave) throws IllegalArgumentException
	{
		if (viewPositionToSave == null)
		{
			throw new IllegalArgumentException("viewPositionToSave is null");
		}

		viewPosition = viewPositionToSave;
	}
}
