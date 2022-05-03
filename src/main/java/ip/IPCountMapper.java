package ip;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.io.IOException;

public class IPCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        String line = value.toString();

        String ip = "";
        int forCnt = 0;

        for (String word : line.split("\\W+")) {
            boolean ip_check = Pattern.matches("^[0-9]{1,3}$", word);
            if (ip_check) {
                forCnt++;
                ip += (word + ".");
                if (forCnt == 4) {
                    ip = ip.substring(0, ip.length() - 1);
                    context.write(new Text(ip), new IntWritable(1));
                }
            }
        }
    }
}
