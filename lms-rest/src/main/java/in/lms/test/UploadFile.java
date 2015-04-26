package in.lms.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class UploadFile {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

	    HttpPost httppost = new HttpPost("http://localhost:8080/lms-rest-0.1/upload/course/image/20");
	    File file = new File("C:/Users/adutta/Desktop/office_work/lms/code/file-upload/rest-document-archive/test-images/image1.jpg");

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "multipart/form-data");
	    ContentBody cbFile2 = new StringBody("image1", Charset.defaultCharset());
	    ContentBody cbFile3 = new StringBody("jpg", Charset.defaultCharset());
	    mpEntity.addPart("file", cbFile);
	    mpEntity.addPart("name", cbFile2);
	    mpEntity.addPart("extension", cbFile3);


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


