package fcm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Sender;

public class PushSenderMain {

	public static void main(String[] args) throws Exception {
		String tokenInfo = "dH3wz20bT9e4fU9idOQMZG:APA91bFT8UINrTk8Jc2visVlOGvbOHjCLIheOGP4Bw_24GMa5iEbxgoukKDq1BsLRhnY31o56UQQX5g4WDxtd4ZP1BFo4PaJ4w03bTWnjUv103FsJitwT14bDRUn8lYes9zxSa4A22nj";
        String serverKey = "AAAAZzYfxCM:APA91bG9Eq6385gx17z_7PMPr6I89i9StG58UeMhRWAl7SsRy6pQqQnHTWz4E1M_ndC13M5RK0b4ZPZuBgma-YhZqTbDHnHzJVVDEidt5ExTQ21EpXgifZ-5OT5itluGTzXRJBIMPdPL";
		// TODO Auto-generated method stub
		List<String> idList = new ArrayList<String>();
		idList.add(tokenInfo);
		
		Message msg = buildMessage().build();
		Sender sender = new FcmSender(serverKey);
		sender.sendNoRetry(msg, idList);
		
		System.out.println("######################### Fcm Send Succenss #################");

	}
	
	private static Builder buildMessage() throws UnsupportedEncodingException{
		Builder builder = new Message.Builder();
		builder.addData("priority", "high");
		builder.addData("title", "TEST_title1");
		builder.addData("body", "TEST_body");
		return builder;
		
	}

}
