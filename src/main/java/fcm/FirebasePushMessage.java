package fcm;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class FirebasePushMessage {

	
	void init() {
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("C:/fcm/secrect/fcm-message-f6d13-firebase-adminsdk-191ti-78ab0bc83b.json");
					FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();

					FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void sendMessage() {
		Notification notification = new Notification("TEST_message2","TEST_message2");
		String tokenInfo = "dH3wz20bT9e4fU9idOQMZG:APA91bFT8UINrTk8Jc2visVlOGvbOHjCLIheOGP4Bw_24GMa5iEbxgoukKDq1BsLRhnY31o56UQQX5g4WDxtd4ZP1BFo4PaJ4w03bTWnjUv103FsJitwT14bDRUn8lYes9zxSa4A22nj";

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
	}

	public static void main(String[] args) {
		FirebasePushMessage fpm = new FirebasePushMessage();
		fpm.init();
		fpm.sendMessage();
	}
}
