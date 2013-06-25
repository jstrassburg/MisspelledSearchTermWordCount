package com.directsupply.MisspelledSearchTermWordCount;

import junit.framework.TestCase;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MapTest extends TestCase {
    public void testMap() throws Exception {
        final String query = "theQuery";
        LongWritable key = new LongWritable();
        Text value = new Text(String.format("&q=(%s)&", query));

        Mapper.Context context = mock(Mapper.Context.class);

        Text expectedKey = new Text(query);
        Text expectedValue = new Text("1");

        Map map = new Map();
        map.map(key, value, context);

        verify(context).write(expectedKey, expectedValue);
    }
}
