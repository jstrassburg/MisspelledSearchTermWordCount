package com.directsupply.MisspelledSearchTermWordCount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        Text outputValue = new Text();
        outputValue.set("1");

        String query = QueryMatcher.extractSearchQuery(value.toString());
        if (query != null) {
            Text outputKey = new Text();
            outputKey.set(query);
            context.write(outputKey, outputValue);
        }
    }
}
