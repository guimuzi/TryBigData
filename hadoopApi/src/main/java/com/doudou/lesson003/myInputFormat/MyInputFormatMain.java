package com.doudou.lesson003.myInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @ClassName: MyInputFormatMain
 * @Author: doudou
 * @Datetime: 2019/12/4
 * @Description: 自定义InputFormat，输出成为一个SequenceFile的大文件，进行文件的合并
 */
public class MyInputFormatMain extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(super.getConf(), "mergeSmallFile");
        job.setInputFormatClass(MyInputFormat.class);
        MyInputFormat.addInputPath(job, new Path("file:///E:\\大数据\\第三次课1202\\1202_课前资料_mr与yarn\\3、第三天\\5、自定义InputFormat实现小文件合并\\文件数据"));
        job.setMapperClass(MyInputFormatMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);

        // 没有reduce，但是要设置reduce的输出k3-v3的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        // 将我们的文件输出成为sequenceFile这种格式
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        SequenceFileOutputFormat.setOutputPath(job, new Path("file:///E:\\大数据\\第三次课1202\\1202_课前资料_mr与yarn\\3、第三天\\5、自定义InputFormat实现小文件合并\\文件数据\\output"));
        boolean b = job.waitForCompletion(true);
        return b?0:1;
    }

    public static void main(String[] args) throws Exception {
        int run = ToolRunner.run(new Configuration(), new MyInputFormatMain(), args);
        System.exit(run);
    }
}
