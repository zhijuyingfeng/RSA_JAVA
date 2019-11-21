package xyz.nigao;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class Main
{

    public static void main(String[] args)
    {
//        long start=System.currentTimeMillis();
//
        String p_string="4737757330991114580959764028" +
                "92151243213040090086618881031224856589919883111175" +
                "43218221078400448470719252449954252902768286719811" +
                "61426294630267161984710098194946874197172469264371" +
                "03127982384979803193954685843127713049424082085738" +
                "65389917813080428875445340124082677328892574545183" +
                "253774970952621120608285211907";
        String q_string="1797631080859628070800939196" +
                "57307409975718428269269435318693176520197803112687" +
                "95272224894649702984270795509861658416105918213289" +
                "98579857912596219596193873536644244493588304964135" +
                "57281341725462360322134010042282012642274593164792" +
                "15539468389162692067640977153731819696856156691452" +
                "8483955014073455660937229038263";
        BigInteger e=new BigInteger("16303069");

        RSA r=new RSA(new BigInteger(p_string),new BigInteger(q_string),e);

        String oaep_c=OAEP.encrypt("Sun Yat-sen University");
        System.out.println(oaep_c);
        BigInteger rsa_m=new BigInteger(oaep_c,16);
        BigInteger rsa_encrypted=r.encrypt(rsa_m);
        System.out.println(rsa_encrypted);
        BigInteger rsa_decrypted=r.decrypt(rsa_encrypted);
        String oaep_encrypted=rsa_decrypted.toString(16).toUpperCase();

        int len=oaep_encrypted.length();
        String formatter="%0"+String.valueOf(512-len)+"d";
        oaep_encrypted=String.format(formatter,0)+oaep_encrypted;

        String oaep_m=OAEP.decrypt(oaep_encrypted);
        System.out.println(oaep_m);

    }
}
