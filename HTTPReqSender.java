package per.hzg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HttpReqSender {
	public static void main(String []args) {
//		HttpReqSender.sendPutReq();
	//	sendMultiPart();
		//sendPostReq();
		long l=Integer.MAX_VALUE+78;
		System.out.println((int)l);
		byte b1 = 18 ^ 3;
		System.out.println(b1);
		byte b2 = 18 ^ 18 ^ 18;
		
		//byte b14 = 18 << 3; // [evaluated to 144] - cannot be stored
		byte b24 = 1024 >> 2 >> 2; // [evaluated to 64] - can be stored
		//short s15 = 127 * 127 * 3; // [evaluated to 48387] - cannot be stored
	}
	
	public static void sendPutReq() {
		HttpHost proxy = new HttpHost("localhost", 80, "http");
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
		                    .setRoutePlanner(routePlanner)
		                    .build();
		
		//HttpRequest h=new HttpRequest();
		HttpPut httpPut = new HttpPut(
				"http://localhost/edit/services/rest/edit/Images/Results");

		/*String JSON_STRING="{\r\n" + 
				"\"PhotosRecvArgu\":\r\n" + 
				"{\r\n" + 
				"\"photoIds\":\r\n" + 
				"[\r\n" + 
				"\"2016101010101010101018447\", \r\n" + 
				"\"2016101010101010101018448\", //同上\r\n" + 
				"\"2016101010101010101018449\"  //同上\r\n" + 
				"]\r\n" + 
				"	}\r\n" + 
				"}";*/

		


		class PhotoId{
			public String photoId;
		}

		/*List<PhotoId> ids = new ArrayList<PhotoId>();
		ArrayNode array = mapper.valueToTree(ids);
		ObjectNode companyNode = mapper.valueToTree(company);
		companyNode.putArray("Employee").addAll(array);
		JsonNode result = mapper.createObjectNode().set("company", companyNode);
		*/
		
		/*ObjectMapper mapper = new ObjectMapper();
		Employees e = new Employees();
		List<Employee> employees = new ArrayList<Employee>();
		e.setEmployees(employees);
		ObjectNode objectNode = mapper.valueToTree(e);
		*/
		
		
		List<String> ids = new ArrayList<String>();
		for(int i = 1; i < 4; i++){
		    String id="id"+i;
		    ids.add(id);
		}
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode photoIds = mapper.createObjectNode();
		ArrayNode idArray=mapper.valueToTree(ids);
		photoIds.putArray("photoIds").addAll(idArray);
		
		ObjectNode node = mapper.createObjectNode();
		HttpEntity stringEntity = new StringEntity(
				node.set("PhotosRecvArgu", photoIds).toString(),
				ContentType.APPLICATION_JSON);
		httpPut.setEntity(stringEntity);
		try {
			CloseableHttpResponse response = httpclient.execute(httpPut);
			System.out.println(response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void sendPostReq() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost/edit/FileUpload");
		String JSON_STRING="";
		HttpEntity stringEntity = new StringEntity(JSON_STRING,ContentType.APPLICATION_JSON);
		httpPost.setEntity(stringEntity);
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendMultiPart() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("ip", "value");
		node.put("port", 0);
		node.put("id", "id_value");
		node.put("fileName", "id_value");
		node.put("filename", "id_value");
		node.put("timestamp", 0);
		node.put("filesize", 0);
		String JSON_STRING = node.toString();
		HttpEntity entity = MultipartEntityBuilder
			    .create()
			    .addTextBody("FileUpload", JSON_STRING)
			    .addBinaryBody("file", new File("D:\\pic.jpg"), 
			    		ContentType.create("application/octet-stream"), "filename")
			    .build();

		HttpPost httpPost = new HttpPost("http://localhost:65535/edit/FileUpload");
		httpPost.setEntity(entity);
		//HttpHost proxy = new HttpHost("localhost", 8888, "http");
		//DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
		                    //.setRoutePlanner(routePlanner)
		                    .build();
		try {
			HttpResponse response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
