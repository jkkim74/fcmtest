package fcm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class PushSenderMain {

	public static void main(String[] args) throws Exception {
		String tokenInfo = "eho3j9bFQf29-fFZgOK0hY:APA91bG-LmpAGnnDneaLCh13MoINKgoXTOQ10b-_AIMYD0nthrBLEn92ErGntWmRGUrfseFWlfvmw9vMPGJ_apvFMMCv96N6wevmtEqrAG7FmcSsH8gAIQuy1mPB2YQQxz6o_Bd6BnLs";
        // tokenInfo = "dxZV4B3aR_qyNSB8JWB957:APA91bFHyH8eNNOQm73jFUQNdxLmqqW7iDdCm36suMV764GwTLDC0qVlNY59NaQxhyzesa5i7ksqvc_DtatmN98nB_kQ5hNd3X3GcDqqM_Nf4JFZAfnVX8nT7ehKlUTRv4O3_VzgY2xa";
		String serverKey = "AAAAZzYfxCM:APA91bG9Eq6385gx17z_7PMPr6I89i9StG58UeMhRWAl7SsRy6pQqQnHTWz4E1M_ndC13M5RK0b4ZPZuBgma-YhZqTbDHnHzJVVDEidt5ExTQ21EpXgifZ-5OT5itluGTzXRJBIMPdPL";
		// TODO Auto-generated method stub
		List<String> idList = new ArrayList<String>();
		idList.add(tokenInfo);
		
		Message msg = buildMessage().build();
		Sender sender = new FcmSender(serverKey);
		MulticastResult resultList = sender.sendNoRetry(msg, idList);
		
		System.out.println("######################### Fcm Send Succenss #################");

	}
	
	private static Builder buildMessage() throws UnsupportedEncodingException{
		Builder builder = new Message.Builder();
		builder.addData("pushId", "1212112212212");
		builder.addData("title", "TEST_title");
		builder.addData("message", "TEST_message");
		return builder;
		
	}

}
