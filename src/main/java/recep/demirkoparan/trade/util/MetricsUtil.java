package recep.demirkoparan.trade.util;

/**
 *
 * @author recepd
 */
public class MetricsUtil {
    
    private MetricsUtil() {}
    
    private static class LazyInstanceCreator {
		private static final MetricsUtil INSTANCE = new MetricsUtil();
	}

	// get the singleton instance
	public static MetricsUtil getInstance() {
		return LazyInstanceCreator.INSTANCE;
	}
    
    private double average;
    private double min;
    private double max;
    private double last;
    private int totalRequest;

    public void gauge (double totalTimeSeconds, long lastTaskTimeMillis, int linesProcessed) {
        totalRequest += linesProcessed;
        average = totalTimeSeconds / totalRequest ;
        last = lastTaskTimeMillis;
        if (min == 0 || min > lastTaskTimeMillis) {
            min = lastTaskTimeMillis;
        }
        if (max == 0 || max < lastTaskTimeMillis) {
            max = lastTaskTimeMillis;
        }
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetricsUtil [aveTime=");
		builder.append(average);
		builder.append(", minTime=");
		builder.append(min/1000.0);
		builder.append(", maxTime=");
		builder.append(max/1000.0);
		builder.append(", lastTime=");
		builder.append(last/1000.0);
		builder.append(", totalRequest=");
		builder.append(totalRequest);
		builder.append("]");
		return builder.toString();
	}
}
