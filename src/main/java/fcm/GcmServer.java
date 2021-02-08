package fcm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
 
public class GcmServer {
 
    public static void main(String[] args){
    	String tokenInfo = "dH3wz20bT9e4fU9idOQMZG:APA91bFT8UINrTk8Jc2visVlOGvbOHjCLIheOGP4Bw_24GMa5iEbxgoukKDq1BsLRhnY31o56UQQX5g4WDxtd4ZP1BFo4PaJ4w03bTWnjUv103FsJitwT14bDRUn8lYes9zxSa4A22nj";
        String serverKey = "AAAAZzYfxCM:APA91bG9Eq6385gx17z_7PMPr6I89i9StG58UeMhRWAl7SsRy6pQqQnHTWz4E1M_ndC13M5RK0b4ZPZuBgma-YhZqTbDHnHzJVVDEidt5ExTQ21EpXgifZ-5OT5itluGTzXRJBIMPdPL";
		
        Sender sender = new FcmSender(serverKey);
        String regId = tokenInfo;
        Message message = new Message.Builder().addData("result", "push success").build();
        List<String> list = new ArrayList<String>();
        list.add(regId);
        try{
            MulticastResult multiResult = sender.send(message, list, 4);
            if (multiResult != null) {
                List<Result> resultList = multiResult.getResults();
                for (Result result : resultList) {
                    System.out.println(result.getMessageId());
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
 
 
}