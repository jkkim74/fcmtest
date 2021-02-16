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
		String tokenInfo = "d_d3XJ9VTwW8BdPCnZ3-kV:APA91bH4i0DTVT3-tuh7wfN0yyQL_kdiYn5CJXhnsZxVXEFxBIVXlssZQZ5ESc1g_m52s8lHYt4KomUGVQ1Af7BXyqp8zk9nPIwkCRRt5gelYgRCDKG7ayNjGSPtwHLRCbSrM-4rLivf";
        // tokenInfo = "dxZV4B3aR_qyNSB8JWB957:APA91bFHyH8eNNOQm73jFUQNdxLmqqW7iDdCm36suMV764GwTLDC0qVlNY59NaQxhyzesa5i7ksqvc_DtatmN98nB_kQ5hNd3X3GcDqqM_Nf4JFZAfnVX8nT7ehKlUTRv4O3_VzgY2xa";
		String serverKey = "AAAAZzYfxCM:APA91bG9Eq6385gx17z_7PMPr6I89i9StG58UeMhRWAl7SsRy6pQqQnHTWz4E1M_ndC13M5RK0b4ZPZuBgma-YhZqTbDHnHzJVVDEidt5ExTQ21EpXgifZ-5OT5itluGTzXRJBIMPdPL";
		// TODO Auto-generated method stub
		List<String> idList = new ArrayList<String>();
		idList.add(tokenInfo);
		
		Message msg = buildMessage().build();
		IosFcmSender sender = new IosFcmSender(serverKey);
		FcmMulticastResult resultList = sender.sendFcmNoRetry(msg, idList);
		
		System.out.println(resultList);
		System.out.println("######################### Fcm Send Succenss #################");

	}
	
	private static Builder buildMessage() throws UnsupportedEncodingException{
		Builder builder = new Message.Builder();
		builder.addData("pushId", "1212112212212");
		builder.addData("title", "TEST_title33");
		builder.addData("message", "TEST_message33");
		return builder;
		
	}

}
