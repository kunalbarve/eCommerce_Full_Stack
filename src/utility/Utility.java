package utility;

import java.security.MessageDigest;
import java.util.UUID;


public class Utility {
	
	public static String getUniqueId(){
		String id = "";
		UUID generatedId = UUID.randomUUID();
		id = String.valueOf(generatedId);
		return id;
	}
	
	public static String getEncryptedValue(String plainText){
		String hashedValue = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = plainText.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        hashedValue = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashedValue;
	}
	
}
