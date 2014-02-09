package com.osmviewer.sqliteMapDataSource.exceptions;

/**
 * Error while working with database
 *
 * @author pgalex
 */
public class DatabaseErrorExcetion extends Exception
{
	public DatabaseErrorExcetion(Throwable thrwbl)
	{
		super(thrwbl);
	}
}
