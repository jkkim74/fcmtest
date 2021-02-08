package fcm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.android.gcm.server.Sender;

public class FcmSender  extends Sender{

	public FcmSender(String key) {
		super(key);
	}

	@Override
	protected HttpURLConnection getConnection(String url) throws IOException {
		
		String fcmUrl = "https://fcm.googleapis.com/fcm/send";
		URL target = new URL(fcmUrl);
		URLConnection connection = target.openConnection();
		connection.setConnectTimeout(300000);
		connection.setReadTimeout(300000);
		// TODO Auto-generated method stub
		return (HttpURLConnection)connection;
	}
	
	

}
