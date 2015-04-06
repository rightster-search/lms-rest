package in.lms.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class MiscellaneousUtil {

	private static SecureRandom random = new SecureRandom();

	public static synchronized String generatePassword() {
		return new BigInteger(130, random).toString(32);
	}

	public static String encrypt(String plaintext) throws Exception {

		MessageDigest msgDigest = null;
		String hashValue = null;
		try {
			msgDigest = MessageDigest.getInstance("SHA");
			msgDigest.update(plaintext.getBytes("UTF-8"));
			byte rawByte[] = msgDigest.digest();
			hashValue = (new BASE64Encoder()).encode(rawByte);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm Exists");
		} catch (UnsupportedEncodingException e) {
			System.out.println("The Encoding Is Not Supported");
		}
		return hashValue;
	}

	public static String generateSessionID() {
		return UUID.randomUUID().toString();
	}
	
	public static String dateTrial(Date date)
	{
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		//Date date = new Date();
		String str = dateFormatGmt.format(date);
		
		return str;
		
	}
}
