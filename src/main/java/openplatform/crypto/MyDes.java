package openplatform.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;


/**
 * 
 * Crypt and encrypt using INPUTSTREAM
 * Bench : encrypt 30MB in 10s, decrypt 30MB in 8s
 * 
 * @author x064265
 *
 */
public class MyDes
{

	private Cipher ecipher;
	private Cipher dcipher;
    // Buffer used to transport the bytes from one stream to another
	private byte[] buf = new byte[StreamConverter.DOWNLOAD_BUFFER_SIZE];
	private SecretKey secretKey;
    
	private AlgorithmParameterSpec paramSpec;
	
	public MyDes()
	{
        // Create an 8-byte initialization vector
        byte[] iv = new byte[]{
            (byte)0x8E, 0x12, 0x39, (byte)0x9C,
            0x07, 0x72, 0x6F, 0x5A
        };
        
         paramSpec = new IvParameterSpec(iv);
	}
	

	public Key getSecretKey() {
		return secretKey;
	}
	
	
	/**
	 * Retourne toutes les informations de la clé sous forme d'un tableau de
	 * bytes. Elle peut ainsi être stockée puis reconstruite ultérieurement en
	 * utilisant la méthode setSecretKey(byte[] keyData)
	 */
	public byte[] getSecretKeyInBytes() {
		return secretKey.getEncoded();
	}
	
	
	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}
	
	
	/**
	 * Permet de reconstruire la clé secrète à partir de ses données, stockées 
	 * dans un tableau de bytes.
	 */
	public void setSecretKey(byte[] keyData)
	throws Exception
	{
		//secretKey = new SecretKeySpec(keyData, "DES");
		secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keyData));
	}
	
	

    public void encrypt(InputStream in, OutputStream out)
    {
        try
        {
        	ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        	ecipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        	
            // Bytes written to out will be encrypted
            out = new CipherOutputStream(out, ecipher);

            // Read in the cleartext bytes and write to out to encrypt
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0)
            {
                out.write(buf, 0, numRead);
            }
            out.close();
        }
        catch (java.security.InvalidAlgorithmParameterException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (javax.crypto.NoSuchPaddingException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.security.InvalidKeyException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.io.IOException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
    }

    public void decrypt(InputStream in, OutputStream out)
    {
        try
        {
        	dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        	dcipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        	
            // Bytes read from in will be decrypted
            in = new CipherInputStream(in, dcipher);

            // Read in the decrypted bytes and write the cleartext to out
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0)
            {
                out.write(buf, 0, numRead);
            }
            out.close();
        }
        catch (java.security.InvalidAlgorithmParameterException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (javax.crypto.NoSuchPaddingException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.security.InvalidKeyException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.io.IOException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
    }
	
    
    public InputStream decrypt(InputStream in)
    {
    	/*
    	PipedOutputStream pout = null;
		PipedInputStream pin = new PipedInputStream();
    	*/
    	
        try
        {
        	//pout = new PipedOutputStream(pin);
        	
        	dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        	dcipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        	
            // Bytes read from in will be decrypted
            return new CipherInputStream(in, dcipher);
            /*
            Thread t = new Thread(new MyDesThread(in, pout));
            t.start();
            */
        }
        catch (java.security.InvalidAlgorithmParameterException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (javax.crypto.NoSuchPaddingException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        catch (java.security.InvalidKeyException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        /*
        catch (java.io.IOException e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
        */
        //return pin;
        
        return null;
    }
    
    
    public void generateKey()
    {
    	try
    	{
    		secretKey = KeyGenerator.getInstance("DES").generateKey();
    	}
    	catch(Exception e)
    	{
    		Debug.println(this, Debug.ERROR, e);
    	}
    }
    
  
    
    
    
    public static void main(String[] args)
    throws Exception
    {    
        // Create encrypter/decrypter class
        MyDes encrypter = new MyDes();
        //encrypter.generateKey();
        encrypter.setSecretKey("salutcaroule".getBytes());
    
        //System.out.println(new String(encrypter.getSecretKey().getEncoded()));
        //System.exit(0);
        
        // Encrypt
        long t1 = 0;
        long t2 = 0;
        t1 = System.currentTimeMillis();
        encrypter.encrypt(new FileInputStream("F:/SVN-MODULES/trunk/Testing/cleartext1.txt"),
            new FileOutputStream("F:/SVN-MODULES/trunk/Testing/ciphertext.txt"));
        t2 = System.currentTimeMillis();
        System.out.println("ENCRYPT TIME:"+(t2-t1)+"ms");
        
        
    
        // Decrypt
        t1 = System.currentTimeMillis();
        //encrypter.decrypt(new FileInputStream("F:/SVN-MODULES/trunk/Testing/ciphertext.txt"),
        //    new FileOutputStream("F:/SVN-MODULES/trunk/Testing/cleartext2.txt"));
        
        InputStream in = encrypter.decrypt(new FileInputStream("F:/SVN-MODULES/trunk/Testing/ciphertext.txt"));
        byte[] b = StreamConverter.inputStreamToBytes(in);
        FileOutputStream fos = new FileOutputStream("F:/SVN-MODULES/trunk/Testing/cleartext2.txt");
        fos.write(b);
        fos.close();
        
        t2 = System.currentTimeMillis();
        System.out.println("DECRYPT TIME:"+(t2-t1)+"ms");
    }
	
}


/*
class MyDesThread implements Runnable
{
	private InputStream in;
	private OutputStream out;
	
	public MyDesThread(InputStream in, OutputStream out)
	{
		this.in = in;
		this.out = out;
	}
	
	public void run()
	{
        try
        {       
            int numRead = 0;
            byte[] buf = new byte[1024];
            while ((numRead = in.read(buf)) >= 0)
            {
                out.write(buf, 0, numRead);
            }
            out.close();
        }
        catch(Exception e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
	}
}
*/


