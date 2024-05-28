package resolvers;

import java.util.ArrayList;
import java.util.List;

public class Bucket
{
    private final List<TokenBucket> tokenBuckets;

    public Bucket()
    {
        tokenBuckets = new ArrayList<>();
        TokenBucket tokenBucket = new TokenBucket(1, 1, 5*1000);
        TokenBucket tokenBucket1 = new TokenBucket(5, 5, 60*1000);
        TokenBucket tokenBucket2 = new TokenBucket(40, 40, 60*60*1000);
        TokenBucket tokenBucket3 = new TokenBucket(100, 100, 24*60*60*1000);

        tokenBuckets.add(tokenBucket);
        tokenBuckets.add(tokenBucket1);
        tokenBuckets.add(tokenBucket2);
        tokenBuckets.add(tokenBucket3);
    }

    public List<TokenBucket> getTokenBuckets()
    {
        return tokenBuckets;
    }
}