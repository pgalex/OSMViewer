package com.osmviewer.sqliteMapDataSource.exceptions;

/**
 * Can not delete exists database file
 *
 * @author pgalex
 */
public class CanNotDeleteExistsDatabaseFileErrorException extends Exception
{
	public CanNotDeleteExistsDatabaseFileErrorException(String string)
	{
		super(string);
	}
}
