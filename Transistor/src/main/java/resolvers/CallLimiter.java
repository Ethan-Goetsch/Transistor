package resolvers;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
public class CallLimiter
{
    // Constants for limits
    private static final int FIVE_SECONDS_LIMIT = 1;
    private static final int ONE_MINUTE_LIMIT = 5;
    private static final int ONE_HOUR_LIMIT = 40;
    private static final int ONE_DAY_LIMIT = 100;

    // Data structure to store request counts for each IP
    private Map<String, Queue<Long>> requestCounts;

    public CallLimiter() {
        requestCounts = new HashMap<>();
    }

    public boolean allowRequest(String ip)
    {
        long currentTime = System.currentTimeMillis();

        // Remove old timestamps
        cleanupExpiredRequests(ip, currentTime);

        // Check request limits
        if (getRequestCount(ip, currentTime, 5000) >= FIVE_SECONDS_LIMIT ||
                getRequestCount(ip, currentTime, 60000) >= ONE_MINUTE_LIMIT ||
                getRequestCount(ip, currentTime, 3600000) >= ONE_HOUR_LIMIT ||
                getRequestCount(ip, currentTime, 86400000) >= ONE_DAY_LIMIT)
        {
            // Request exceeds limit
            return false;
        }

        // Request allowed, update request count
        updateRequestCount(ip, currentTime);
        return true;
    }

    private void cleanupExpiredRequests(String ip, long currentTime)
    {
        Queue<Long> requests = requestCounts.get(ip);
        if (requests == null) return;

        // Remove timestamps older than 1 day
        while (!requests.isEmpty() && currentTime - requests.peek() > 86400000)
        {
            requests.poll();
        }

        if (requests.isEmpty())
        {
            requestCounts.remove(ip);
        }
    }

    private int getRequestCount(String ip, long currentTime, long timePeriod)
    {
        Queue<Long> requests = requestCounts.get(ip);
        if (requests == null) return 0;

        // Remove timestamps older than the time period
        while (!requests.isEmpty() && currentTime - requests.peek() > timePeriod)
        {
            requests.poll();
        }

        return requests.size();
    }

    private void updateRequestCount(String ip, long currentTime)
    {
        Queue<Long> requests = requestCounts.getOrDefault(ip, new LinkedList<>());
        requests.offer(currentTime);
        requestCounts.put(ip, requests);
    }
}