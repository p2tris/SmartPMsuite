package Devices;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class SchemaUpload {
	
	public static void upload() throws Exception {
//		System.out.println("*** SchemaUpload -- Starting to upload!");
        String userHome=System.getProperty("user.dir");
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost("http://smartpm.cloudapp.net/fileUpload.php");
        File file = new File(userHome+"/resources/process_schema.xsd");
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody contentFile = new FileBody(file);
        mpEntity.addPart("userfile", contentFile);
        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity(); 
 
//        if(!(response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")){
//	           System.out.println("*** SchemaUpload -- Successful upload!");
//
//        }
//        else{
//            // Did not upload. Add your logic here. Maybe you want to retry.
//        	System.out.println("*** SchemaUpload -- Unsuccessful upload! :( ");
//        }
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
