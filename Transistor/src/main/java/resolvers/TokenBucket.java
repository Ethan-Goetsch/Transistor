package resolvers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TokenBucket implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int availableTokens;
    private int numberOfRequests;
    private int windowSizeForRateLimitInMilliseconds;;
    private long lastRefillTime;
    private long nextRefillTime;
    private int maxBucketSize;

    public TokenBucket(int maxBucketSize, int numberOfRequests, int windowSizeForRateLimitInMilliseconds)
    {
        this.maxBucketSize = maxBucketSize;
        this.numberOfRequests = numberOfRequests;
        this.windowSizeForRateLimitInMilliseconds = windowSizeForRateLimitInMilliseconds;
    }

    public boolean tryConsume()
    {
        refill();
        if (this.availableTokens > 0)
        {
            this.availableTokens--;
            return true;
        }
        return false;
    }

    private void refill()
    {
        if (System.currentTimeMillis() < this.nextRefillTime)
        {
            return;
        }
        this.lastRefillTime = System.currentTimeMillis();
        this.nextRefillTime = this.lastRefillTime + this.windowSizeForRateLimitInMilliseconds;
        this.availableTokens = Math.min(this.maxBucketSize, this.availableTokens + this.numberOfRequests);
    }

    // Method to serialize a TokenBucket object
    public static void serializeTokenBuckets(List<TokenBucket> tokenBuckets, String filename)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(filename, false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (TokenBucket tokenBucket : tokenBuckets)
            {
                out.writeObject(tokenBucket);
            }
            out.close();
            fileOut.close();
//            System.out.println("Serialized data is saved in " + filename);
        }
        catch (IOException i)
        {
            i.printStackTrace();
        }

    }

    // Method to deserialize a TokenBucket object
    public static List<TokenBucket> deserializeTokenBucket(String filename)
    {
        List<TokenBucket> tokenBuckets = new ArrayList<TokenBucket>();
        try
        {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while (true)
            {
                try
                {
                    TokenBucket tokenBucket = (TokenBucket) in.readObject();
                    tokenBuckets.add(tokenBucket);
                }
                catch (EOFException e)
                {
                    break;
                }

            }
            in.close();
            fileIn.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return tokenBuckets;
    }

}