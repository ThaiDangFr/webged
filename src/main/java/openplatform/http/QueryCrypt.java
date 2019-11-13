package openplatform.http;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;

import openplatform.crypto.MyBlowfish;
import openplatform.tools.Base64;

public class QueryCrypt {

	private MyBlowfish bf = new MyBlowfish();
	
	
	public QueryCrypt() 
	{
		bf.setSecretKey(new byte[] {1,1,0,7,1,9,7,6});
	}
	
	public String decodeQuery(String q)
	{
		/*
		byte[] b = Base64.decodeBytes(q);
		String p = bf.decryptInString(b);
		
		return p;
		*/
		BigInteger bi = new BigInteger(q);
		String p = bf.decryptInString(bi.toByteArray());
		return p;
	}
	 
	public String encodeQuery(String q)
	{
		/*
		byte[] b = bf.crypt(q);
		String b64d = Base64.encodeBytes(b);
		return b64d;
		*/
		byte[] b = bf.crypt(q);
		return new BigInteger(b).toString();
	}
	
	/*
	public static void main(String[] args)
	{
		QueryCrypt qc = new QueryCrypt();
		String c = qc.encodeQuery("/dsds/truc.jsp?in=abc&out=def");
		System.out.println(c);
		System.out.println(qc.decodeQuery(c));
	}
	*/
}
