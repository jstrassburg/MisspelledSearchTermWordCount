package com.directsupply.MisspelledSearchTermWordCount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        Text outputValue = new Text("1");
        String[] fields = value.toString().split("\t");
        if (fields.length < 2)
            return;
        String jobCode = fields[1];

        String query = QueryMatcher.extractSearchQuery(value.toString());
        if (query != null) {
            Text outputKey = new Text(String.format("All|%s", query));
            Text outputKey2 = new Text(String.format("%s|%s", jobCode, query));
            context.write(outputKey, outputValue);
            context.write(outputKey2, outputValue);
        }
    }
}
