import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {

	public static String getMD5(String source) {
		// TODO Auto-generated method stub
		String result=null;
		try{
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			result= new BigInteger(1,md.digest()).toString(16);
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(result);
		return result;
	}

}
