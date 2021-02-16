package fcm;

import static fcm.FcmConstants.FCM_SEND_ENDPOINT;
import static fcm.FcmConstants.JSON_CANONICAL_IDS;
import static fcm.FcmConstants.JSON_ERROR;
import static fcm.FcmConstants.JSON_FAILURE;
import static fcm.FcmConstants.JSON_MESSAGE_ID;
import static fcm.FcmConstants.JSON_MULTICAST_ID;
import static fcm.FcmConstants.JSON_PAYLOAD;
import static fcm.FcmConstants.JSON_REGISTRATION_IDS;
import static fcm.FcmConstants.JSON_RESULTS;
import static fcm.FcmConstants.JSON_SUCCESS;
import static fcm.FcmConstants.TOKEN_CANONICAL_REG_ID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;

public class IosFcmSender{
    String key = "";
	public IosFcmSender(String key) {
		this.key = key;
		// TODO Auto-generated constructor stub
	}
	protected HttpURLConnection getConnection(String url) throws IOException {
		URL target = new URL(url);
		URLConnection connection = target.openConnection();
		connection.setConnectTimeout(300000);
		connection.setReadTimeout(300000);
		// TODO Auto-generated method stub
		return (HttpURLConnection)connection;
	}

	public FcmMulticastResult sendFcmNoRetry(Message message, List<String> registrationIds) throws IOException {
		if (nonNull(registrationIds).isEmpty()) {
		      throw new IllegalArgumentException("registrationIds cannot be empty");
		}
		 Map<Object, Object> jsonRequest = new HashMap<Object, Object>();
		 jsonRequest.put(JSON_REGISTRATION_IDS, registrationIds);
	     Map<String, String> payload = message.getData();
	     if (!payload.isEmpty()) {
	       jsonRequest.put(JSON_PAYLOAD, payload);
	       jsonRequest.put("notification", payload);
	     }
	     String requestBody = JSONValue.toJSONString(jsonRequest);
	     System.out.println("JSON request: " + requestBody);
	     HttpURLConnection conn =
	         post(FCM_SEND_ENDPOINT, "application/json", requestBody);
	     
	     int status = conn.getResponseCode();
	     String responseBody;
	     if (status != 200) {
	       responseBody = getString(conn.getErrorStream());
	       System.out.println("JSON error response: " + responseBody);
	       throw new InvalidRequestException(status, responseBody);
	     }
	     responseBody = getString(conn.getInputStream());
	     System.out.println("JSON response: " + responseBody);
	     JSONParser parser = new JSONParser();
	     JSONObject jsonResponse;
	     try {
	       jsonResponse = (JSONObject) parser.parse(responseBody);
	       int success = getNumber(jsonResponse, JSON_SUCCESS).intValue();
	       int failure = getNumber(jsonResponse, JSON_FAILURE).intValue();
	       int canonicalIds = getNumber(jsonResponse, JSON_CANONICAL_IDS).intValue();
	       long multicastId = getNumber(jsonResponse, JSON_MULTICAST_ID).longValue();
	       FcmMulticastResult.Builder builder = new FcmMulticastResult.Builder(success,
	           failure, canonicalIds, multicastId);
	       @SuppressWarnings("unchecked")
	       List<Map<String, Object>> results =
	           (List<Map<String, Object>>) jsonResponse.get(JSON_RESULTS);
	       if (results != null) {
	         for (Map<String, Object> jsonResult : results) {
	           String messageId = (String) jsonResult.get(JSON_MESSAGE_ID);
	           String canonicalRegId =
	               (String) jsonResult.get(TOKEN_CANONICAL_REG_ID);
	           String error = (String) jsonResult.get(JSON_ERROR);
	           FcmResult result = new FcmResult.Builder()
	               .messageId(messageId)
	               .canonicalRegistrationId(canonicalRegId)
	               .errorCode(error)
	               .build();
	           builder.addResult(result);
	         }
	       }
	       FcmMulticastResult multicastResult = builder.build();
	       return multicastResult;
	     } catch (ParseException e) {
	       throw newIoException(responseBody, e);
	     } catch (CustomParserException e) {
	       throw newIoException(responseBody, e);
	     }
	}
	
	static <T> T nonNull(T argument) {
		    if (argument == null) {
		      throw new IllegalArgumentException("argument cannot be null");
		    }
		    return argument;
	}
	
  /**
   * Sets a JSON field, but only if the value is not {@literal null}.
   */
  private void setJsonField(Map<Object, Object> json, String field,
      Object value) {
    if (value != null) {
      json.put(field, value);
    }
  }
  
  private Number getNumber(Map<?, ?> json, String field) {
	    Object value = json.get(field);
	    if (value == null) {
	      throw new CustomParserException("Missing field: " + field);
	    }
	    if (!(value instanceof Number)) {
	      throw new CustomParserException("Field " + field +
	          " does not contain a number: " + value);
	    }
	    return (Number) value;
}
  
  private IOException newIoException(String responseBody, Exception e) {
	    // log exception, as IOException constructor that takes a message and cause
	    // is only available on Java 6
	    String msg = "Error parsing JSON response (" + responseBody + ")";
	    return new IOException(msg + ":" + e);
	  }
	
  class CustomParserException extends RuntimeException {
	    CustomParserException(String message) {
	      super(message);
	    }
	  }
  
  protected HttpURLConnection post(String url, String contentType, String body)
	      throws IOException {
	    if (url == null || body == null) {
	      throw new IllegalArgumentException("arguments cannot be null");
	    }
	    byte[] bytes = body.getBytes();
	    HttpURLConnection conn = getConnection(url);
	    conn.setDoOutput(true);
	    conn.setUseCaches(false);
	    conn.setFixedLengthStreamingMode(bytes.length);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", contentType);
	    conn.setRequestProperty("Authorization", "key=" + key);
	    OutputStream out = conn.getOutputStream();
	    out.write(bytes);
	    out.close();
	    return conn;
	  }
  
  /**
   * Convenience method to convert an InputStream to a String.
   *
   * <p>
   * If the stream ends in a newline character, it will be stripped.
   */
  protected static String getString(InputStream stream) throws IOException {
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(nonNull(stream)));
    StringBuilder content = new StringBuilder();
    String newLine;
    do {
      newLine = reader.readLine();
      if (newLine != null) {
        content.append(newLine).append('\n');
      }
    } while (newLine != null);
    if (content.length() > 0) {
      // strip last newline
      content.setLength(content.length() - 1);
    }
    return content.toString();
  }

}
