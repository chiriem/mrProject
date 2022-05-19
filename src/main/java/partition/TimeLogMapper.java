package partition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TimeLogMapper extends Mapper<LongWritable, Text, Text, Text> {

    List<String> times = null;

    public TimeLogMapper() {
        this.times = Arrays.asList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] fields = value.toString().split(" ");

        if (fields.length > 0) {
            String ip = fields[0];

            String[] dtFields = fields[3].split("/");

            if (dtFields.length > 1) {
                String time = dtFields[2].substring(5, 7);

                if (times.contains(time)) {
                    context.write(new Text(ip), new Text(time));
                }
            }
        }

    }

}
