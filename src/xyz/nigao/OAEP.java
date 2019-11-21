package xyz.nigao;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class OAEP
{
    static final private int byteNumber=128;
    static final private int SHA1Length=20;

    public static void SHA1(byte[]m,byte[]res)
    {
        byte []temp=new byte[byteNumber];
        temp=m;
        for(int i=0;i<6;i++)
        {
            try
            {
                MessageDigest SHA1=MessageDigest.getInstance("SHA-1");
                byte[]sha1=SHA1.digest(temp);
                for(int j=0;j<SHA1Length;j++)
                {
                    res[8+i*SHA1Length+j]=sha1[j];
                }
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String encrypt(String m)
    {

        Random rand=new Random();
        rand.setSeed(0);
        byte[]r=new byte[byteNumber];
//        rand.nextBytes(r);
        byte[]x=new byte[byteNumber];
        byte[]G=new byte[byteNumber];
        byte[]H=new byte[byteNumber];
        byte[]y1=new byte[byteNumber];
        byte[]y2=new byte[byteNumber];
        if(m.length()<=byteNumber)
        {
            int len=m.length();
            for(int i=0;i<len;i++)
            {
                x[byteNumber-len+i]=(byte)m.charAt(i);
            }
            SHA1(r,G);
            for(int i=0;i<byteNumber;i++)
                y1[i]= (byte) (x[i]^G[i]);
            SHA1(y1,H);
            for(int i=0;i<byteNumber;i++)
                y2[i]=(byte)(r[i]^H[i]);
            String ans="";
            for(int i=0;i<byteNumber;i++)
            {
                String temp=String.format("%02X",y1[i]);
                ans+=temp;
            }
            for(int i=0;i<byteNumber;i++)
            {
                String temp=String.format("%02X",y2[i]);
                ans+=temp;
            }
            return ans;
        }
        return "";
    }

    static public String decrypt(String c)
    {
        if(c.length()!=byteNumber<<2)
            return "";
        byte[]x1=new byte[byteNumber];
        byte[]x2=new byte[byteNumber];
        int start=0;
        for(int i=0;i<byteNumber;i++)
        {
            String temp=c.substring(start,start+2);
            start+=2;
            int x=Integer.valueOf(temp,16);
            x1[i]=(byte)x;
        }
        for(int i=0;i<byteNumber;i++)
        {
            String temp=c.substring(start,start+2);
            start+=2;
            int x=Integer.valueOf(temp,16);
            x2[i]=(byte)x;
        }

        byte[]H=new byte[byteNumber];
        byte[]G=new byte[byteNumber];
        byte[]r=new byte[byteNumber];
        byte[]ans=new byte[byteNumber];
        SHA1(x1,H);
        for(int i=0;i<byteNumber;i++)
        {
            r[i]=(byte)(H[i]^x2[i]);
        }
        SHA1(r,G);
        for(int i=0;i<byteNumber;i++)
        {
            ans[i]=(byte)(x1[i]^G[i]);
        }
        String S=new String();
        for (int i=0;i<byteNumber;i++)
            S+=(char)ans[i];
        return S;
    }
}
