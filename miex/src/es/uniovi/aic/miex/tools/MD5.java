/*
 * Grabbed from:
 *  http://www.bombaydigital.com/arenared/2003/10/10/1
 */

package es.uniovi.aic.miex.tools;

import java.security.*;

public class MD5
{
	public static String gen(String clearTextPassword)
	throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(clearTextPassword.getBytes());

		return HexString.bufferToHex(md.digest());
	}
}
