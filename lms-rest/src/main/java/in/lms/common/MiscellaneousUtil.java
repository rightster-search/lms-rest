package in.lms.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

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
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		/*Long id = 1L;
		String str = id+"";
		
		Long a = Long.parseLong("1324");
		System.out.println(a);*/
		
		HttpClient httpclient = new DefaultHttpClient();
	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

	    HttpPost httppost = new HttpPost("http://localhost:8080/lms-rest-0.1/upload");
	    File file = new File("C:\\Users\\adutta\\Desktop\\office_work\\lms\\upload\\BDB_Installation.pdf");

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "multipart/form-data");
	    mpEntity.addPart("file", cbFile);


	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost);
	    HttpEntity resEntity = response.getEntity();

	    System.out.println(response.getStatusLine());
	    if (resEntity != null) {
	      System.out.println(EntityUtils.toString(resEntity));
	    }
	    if (resEntity != null) {
	      resEntity.consumeContent();
	    }

	    httpclient.getConnectionManager().shutdown();
	}
}
