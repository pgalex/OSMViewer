package drawingStyles.exceptions;

import drawingStyles.ScaledObjectStyleArray;

/**
 * Exception throws in ScaledObjectStyleArray
 *
 * @author pgalex
 */
public class ScaledObjectStyleArrayRuntimeException extends DrawingStylesRuntimeException
{
	/**
	 * Scaled object style array throwed this exception
	 */
	private ScaledObjectStyleArray arrayThrowedException;

	/**
	 * Constructor
	 *
	 * @param pArrayThrowedException Scaled object style array throwed this
	 * exception
	 */
	public ScaledObjectStyleArrayRuntimeException(ScaledObjectStyleArray pArrayThrowedException)
	{
		arrayThrowedException = pArrayThrowedException;
	}

	/**
	 * Get scaled object style array throwed this exception
	 *
	 * @return Scaled object style array throwed this exception
	 */
	public ScaledObjectStyleArray getArrayThrowedException()
	{
		return arrayThrowedException;
	}
}
