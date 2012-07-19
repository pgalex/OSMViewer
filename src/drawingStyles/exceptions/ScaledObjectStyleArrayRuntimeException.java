package drawingStyles.exceptions;

import drawingStyles.DrawStyleOnScaleArray;

/**
 * Exception throws in DrawStyleOnScaleArray
 *
 * @author pgalex
 */
public class ScaledObjectStyleArrayRuntimeException extends DrawingStylesRuntimeException
{
	/**
	 * Scaled object style array throwed this exception
	 */
	private DrawStyleOnScaleArray arrayThrowedException;

	/**
	 * Constructor
	 *
	 * @param pArrayThrowedException Scaled object style array throwed this
	 * exception
	 */
	public ScaledObjectStyleArrayRuntimeException(DrawStyleOnScaleArray pArrayThrowedException)
	{
		arrayThrowedException = pArrayThrowedException;
	}

	/**
	 * Get scaled object style array throwed this exception
	 *
	 * @return Scaled object style array throwed this exception
	 */
	public DrawStyleOnScaleArray getArrayThrowedException()
	{
		return arrayThrowedException;
	}
}
