package resolvers;

import java.util.List;
import java.util.ArrayList;

public class CallRateAdmin
{
    public static boolean authouriseRequst()
    {

        List<TokenBucket> tokenBuckets = TokenBucket.deserializeTokenBucket("Transistor\\src\\main\\resources\\TokenBuckets.txt");
        boolean canRequest = true;
        for (TokenBucket tokenBucket : tokenBuckets)
        {
            canRequest = tokenBucket.tryConsume();
            if (!canRequest)
            {
                break;
            }
        }
        TokenBucket.serializeTokenBuckets(tokenBuckets, "Transistor\\src\\main\\resources\\TokenBuckets.txt");
        return canRequest;

    }

//    public static void main(String[] args) {
//         TokenBucket tokenBucket = new TokenBucket(1, 1, 5*1000);
//         TokenBucket tokenBucket1 = new TokenBucket(5, 5, 60*1000);
//         TokenBucket tokenBucket2 = new TokenBucket(40, 40, 60*60*1000);
//         TokenBucket tokenBucket3 = new TokenBucket(100, 100, 24*60*60*1000);
//         List<TokenBucket> tokenBuckets = new ArrayList<>();
//         tokenBuckets.add(tokenBucket);
//         tokenBuckets.add(tokenBucket1);
//         tokenBuckets.add(tokenBucket2);
//         tokenBuckets.add(tokenBucket3);
//         TokenBucket.serializeTokenBuckets(tokenBuckets, "Transistor\\src\\main\\resources\\TokenBuckets.txt");
//    }
}
