package maponly;

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

@Log4j
public class ImageCount extends Configuration implements Tool {

    // 맵리듀스 실행 함수
    public static void main(String[] args) throws Exception {

        // 파라미터는 분석할 파일(폴더)과 분석 결과가 저장될 파일(폴더)로 2개를 받음
        if (args.length != 1) {
            System.out.printf("분석결과가 저장될 폴더를 입력해야 합니다.");
            System.exit(-1);
        }

        int exitCode = ToolRunner.run(new ImageCount(), args);

        System.exit(exitCode);
    }

    @Override
    public void setConf(Configuration configuration) {
        configuration.set("AppName", "Map Only Test");
    }

    @Override
    public Configuration getConf() {
        Configuration conf = new Configuration();

        this.setConf(conf);

        return conf;
    }

    @Override
    public int run(String[] args) throws Exception {
        String analysisFile = "/access_log";

        Configuration conf = this.getConf();
        String appName = conf.get("AppName");

        System.out.println("appName : " + appName);

        Job job = Job.getInstance(conf);

        job.addCacheFile(new Path(analysisFile).toUri());

        job.setJarByClass(ImageCount.class);

        job.setJobName(appName);

        FileInputFormat.setInputPaths(job, new Path(analysisFile));

        FileOutputFormat.setOutputPath(job, new Path(args[0]));

        job.setMapperClass(ImageCountMapper.class);

        job.setNumReduceTasks(0);

        boolean success = job.waitForCompletion(true);

        if(success) {
            long jpg = job.getCounters().findCounter("imageCount","jpg").getValue();
            long gif = job.getCounters().findCounter("imageCount","gif").getValue();
            long other = job.getCounters().findCounter("imageCount","other").getValue();

            log.info("jpg : " + jpg);
            log.info("gif : " + gif);
            log.info("other : " + other);

            return 0;
        } else {
            return 1;
        }
    }
}
