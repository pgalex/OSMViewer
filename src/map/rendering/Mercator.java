package map.rendering;

/**
 * Uses for computing mercator projection
 * http://wiki.openstreetmap.org/wiki/Mercator
 *
 * @author Moshe Sayag
 */
public class Mercator
{
	final private static double R_MAJOR = 6378137.0;
	final private static double R_MINOR = 6356752.3142;

	public static double mercX(double lon)
	{
		return R_MAJOR * Math.toRadians(lon);
	}

	public static double mercY(double lat)
	{
		if (lat > 89.5)
		{
			lat = 89.5;
		}
		if (lat < -89.5)
		{
			lat = -89.5;
		}
		double temp = R_MINOR / R_MAJOR;
		double es = 1.0 - (temp * temp);
		double eccent = Math.sqrt(es);
		double phi = Math.toRadians(lat);
		double sinphi = Math.sin(phi);
		double con = eccent * sinphi;
		double com = 0.5 * eccent;
		con = Math.pow(((1.0 - con) / (1.0 + con)), com);
		double ts = Math.tan(0.5 * ((Math.PI * 0.5) - phi)) / con;
		double y = 0 - R_MAJOR * Math.log(ts);
		return y;
	}
}
