package xyz.nigao;
import java.math.BigInteger;

public class RSA
{
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger lambda_n;
    private BigInteger d;
    private BigInteger e;

    static private BigInteger MAX_VALUE=new BigInteger("7FFFFFFF",16);

    public static BigInteger getInverse(BigInteger e,BigInteger n)
    {
        BigInteger b1=BigInteger.ZERO;
        BigInteger b2=BigInteger.ONE;
        BigInteger p1=n;
        BigInteger p2=e;
        BigInteger q;
        while(p2.intValue()!=1)
        {
            q=p1.divide(p2);
            BigInteger temp=b1.subtract(q.multiply(b2));
            b1=b2;p1=p2;
            b2=temp;
            p2=b2.multiply(e).mod(n);
        }
        return b2.mod(n);
    }

    public static BigInteger pow(BigInteger base,BigInteger exp,BigInteger mod)
    {
        if(mod.compareTo(BigInteger.ZERO)<=0)
            throw new ArithmeticException("non-positive modulo");
        if(exp.compareTo(BigInteger.ZERO)<0)
            return pow(getInverse(base,mod),exp,mod);
        if(exp.equals(BigInteger.ONE))
            return base.mod(mod);

        BigInteger s=BigInteger.ONE;
        BigInteger u=exp;
        BigInteger t=base;

        while(!u.equals(BigInteger.ZERO))
        {
            //u is odd
            if(u.and(BigInteger.ONE).equals(BigInteger.ONE))
                s=s.multiply(t).mod(mod);
            u=u.shiftRight(1);
            t=t.multiply(t).mod(mod);
        }
        return s;
    }

    public RSA(BigInteger P,BigInteger Q,BigInteger E)
    {
        this.p=P;
        this.q=Q;
        this.e=E;
        this.n=this.p.multiply(q);
        BigInteger p_1=p.subtract(BigInteger.ONE);
        BigInteger q_1=q.subtract(BigInteger.ONE);
        BigInteger gcd=p_1.gcd(q_1);
        this.lambda_n=p_1.divide(gcd).multiply(q_1);
        this.d=getInverse(e,lambda_n);
    }

    public BigInteger encrypt(BigInteger m)
    {
        return m.modPow(this.e,this.n);
//        return pow(m,this.e,this.n);
    }

    public BigInteger decrypt(BigInteger c)
    {
        return c.modPow(this.d,this.n);
//        return pow(c,this.d,this.n);
    }
}
