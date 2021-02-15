package fcm;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

public class FirebasePushMessage {

	
	void init() {
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("C:/fcm/secrect/fcm-message-f6d13-firebase-adminsdk-191ti-78ab0bc83b.json");
					FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();

					FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void sendMessage() throws FirebaseMessagingException {
		/*Notification notification = Notification.builder().setTitle("TEST_message2").setBody("TEST_message2").build();
		String tokenInfo = "d_d3XJ9VTwW8BdPCnZ3-kV:APA91bH4i0DTVT3-tuh7wfN0yyQL_kdiYn5CJXhnsZxVXEFxBIVXlssZQZ5ESc1g_m52s8lHYt4KomUGVQ1Af7BXyqp8zk9nPIwkCRRt5gelYgRCDKG7ayNjGSPtwHLRCbSrM-4rLivf";

		Message message = Message.builder()
		.putData("score", "850")
		.putData("time", "2:45")
		.setToken(tokenInfo)
		.setNotification(notification)
		.build();

		String response = null;
		try {
		    response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
		    System.out.println("Firebase FirebaseMessagingException" + e.getMessage());
		    e.printStackTrace();
		}
		System.out.println("Successfully sent message: " + response);
		*/
		
		// These registration tokens come from the client FCM SDKs.
		List<String> registrationTokens = Arrays.asList(
		    "d_d3XJ9VTwW8BdPCnZ3-kV:APA91bH4i0DTVT3-tuh7wfN0yyQL_kdiYn5CJXhnsZxVXEFxBIVXlssZQZ5ESc1g_m52s8lHYt4KomUGVQ1Af7BXyqp8zk9nPIwkCRRt5gelYgRCDKG7ayNjGSPtwHLRCbSrM-4rLivf",
		    "YOUR_REGISTRATION_TOKEN_1",
		    "YOUR_REGISTRATION_TOKEN_2"
		);
		Notification notification = Notification.builder().setTitle("TEST_message2").setBody("TEST_message2").build();
		MulticastMessage message = MulticastMessage.builder()
		    .putData("score", "850")
		    .putData("time", "2:45")
		    .addAllTokens(registrationTokens)
		    .setNotification(notification)
		    .build();
		
		System.out.println(message.builder().toString());
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		if (response.getFailureCount() > 0) {
		  List<SendResponse> responses = response.getResponses();
		  List<String> failedTokens = new ArrayList<String>();
		  List<String> succTokens = new ArrayList<String>();
		  for (int i = 0; i < responses.size(); i++) {
		    if (!responses.get(i).isSuccessful()) {
		      // The order of responses corresponds to the order of the registration tokens.
		      failedTokens.add(registrationTokens.get(i));
		    }else {
		       succTokens.add(registrationTokens.get(i));
		    }
		  }

		  System.out.println("List of tokens that caused failures: " + failedTokens);
		  System.out.println("List of tokens that caused success: " + succTokens);
		}
	}

	public static void main(String[] args) throws FirebaseMessagingException {
		FirebasePushMessage fpm = new FirebasePushMessage();
		fpm.init();
		fpm.sendMessage();
	}
}
