package drawingStyles.exceptions;

import drawingStyles.DrawSettingsOnScaleArray;

/**
 * Exception throws in DrawSettingsOnScaleArray
 *
 * @author pgalex
 */
public class ScaledObjectStyleArrayRuntimeException extends DrawingStylesRuntimeException
{
	/**
	 * Scaled object style array throwed this exception
	 */
	private DrawSettingsOnScaleArray arrayThrowedException;

	/**
	 * Constructor
	 *
	 * @param pArrayThrowedException Scaled object style array throwed this
	 * exception
	 */
	public ScaledObjectStyleArrayRuntimeException(DrawSettingsOnScaleArray pArrayThrowedException)
	{
		arrayThrowedException = pArrayThrowedException;
	}

	/**
	 * Get scaled object style array throwed this exception
	 *
	 * @return Scaled object style array throwed this exception
	 */
	public DrawSettingsOnScaleArray getArrayThrowedException()
	{
		return arrayThrowedException;
	}
}
