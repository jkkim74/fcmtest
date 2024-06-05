package fcm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;

public class PushSenderMain {

	private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";

	public static void main(String[] args) throws Exception {
		String tokenInfo = "d_d3XJ9VTwW8BdPCnZ3-kV:APA91bH4i0DTVT3-tuh7wfN0yyQL_kdiYn5CJXhnsZxVXEFxBIVXlssZQZ5ESc1g_m52s8lHYt4KomUGVQ1Af7BXyqp8zk9nPIwkCRRt5gelYgRCDKG7ayNjGSPtwHLRCbSrM-4rLivf";
		List<String> idList = new ArrayList<String>();
		idList.add(tokenInfo);

		String accessToken = getAccessToken();
		System.out.println("Access Token: " + accessToken);

		Message msg = buildMessage().build();
		IosFcmSender sender = new IosFcmSender(accessToken);
		FcmMulticastResult resultList = sender.sendFcmNoRetry(msg, idList);

		System.out.println(resultList);
		System.out.println("######################### Fcm Send Success #################");
	}

	private static Builder buildMessage() {
		Builder builder = new Message.Builder();
		//builder.addData("pushId", "1212112212212");
		builder.addData("title", "TEST PUSH발송");
		//builder.addData("message", "TEST_message33");
		return builder;
	}

	private static String getAccessToken() throws IOException {
		FileInputStream serviceAccountStream = new FileInputStream("D:\\jboss\\key\\service-account.json");
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccountStream)
				.createScoped(Arrays.asList(SCOPES));
		googleCredentials.refreshIfExpired(); // Ensure token is refreshed if expired

		// Verify if googleCredentials is properly initialized
		if (googleCredentials.getAccessToken() == null) {
			throw new IOException("Failed to obtain access token from GoogleCredentials");
		}

		return googleCredentials.getAccessToken().getTokenValue();
	}
}
