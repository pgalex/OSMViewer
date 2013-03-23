package com.osmviewer.sqliteMapDataSource.exceptions;

/**
 * Can not delete exists database file
 *
 * @author pgalex
 */
public class DeletingExistsDatabaseFileErrorException extends Exception
{
	public DeletingExistsDatabaseFileErrorException(String string)
	{
		super(string);
	}
}
