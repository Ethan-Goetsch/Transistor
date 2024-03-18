package resolvers;

import java.util.List;

public class CallRateAdmin
{
    public static boolean authouriseRequst()
    {
        // TokenBucket tokenBucket = new TokenBucket(1, 1, 5*1000);
        // TokenBucket tokenBucket1 = new TokenBucket(5, 5, 60*1000);
        // TokenBucket tokenBucket2 = new TokenBucket(40, 40, 60*60*1000);
        // TokenBucket tokenBucket3 = new TokenBucket(100, 100, 24*60*60*1000);
        // List<TokenBucket> tokenBuckets = new ArrayList<>();
        // tokenBuckets.add(tokenBucket);
        // tokenBuckets.add(tokenBucket1);
        // tokenBuckets.add(tokenBucket2);
        // tokenBuckets.add(tokenBucket3);
        // TokenBucket.serializeTokenBuckets(tokenBuckets, "TokenBuckets.txt");

        List<TokenBucket> tokenBuckets = TokenBucket.deserializeTokenBucket("TokenBuckets.txt");
        boolean canRequest = true;
        for (TokenBucket tokenBucket : tokenBuckets)
        {
            canRequest = tokenBucket.tryConsume();
            if (!canRequest)
            {
                break;
            }
        }
        TokenBucket.serializeTokenBuckets(tokenBuckets, "TokenBuckets.txt");
        return canRequest;

    }
}
