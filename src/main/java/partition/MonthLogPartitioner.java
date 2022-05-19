package partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;
import java.util.Map;

public class MonthLogPartitioner extends Partitioner<Text, Text> {
    Map<String, Integer> months = new HashMap<>();

    public MonthLogPartitioner() {
        this.months.put("Jan", 0);
        this.months.put("Feb", 1);
        this.months.put("Mar", 2);
        this.months.put("Apr", 3);
        this.months.put("May", 4);
        this.months.put("Jun", 5);
        this.months.put("Jul", 6);
        this.months.put("Aug", 7);
        this.months.put("Sep", 8);
        this.months.put("Oct", 9);
        this.months.put("Nov", 10);
        this.months.put("Dec", 11);
    }

    @Override
    public int getPartition(Text key, Text value, int numReduceTasks) {
        return months.get(value.toString());
    }
}
