package com.directsupply.MisspelledSearchTermWordCount;

import junit.framework.TestCase;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReduceTest extends TestCase {
    public void testReduce() throws Exception {
        Text key = new Text("something");

        ArrayList<Text> values = new ArrayList<Text>() { { add(new Text()); add(new Text()); } };
        Integer valuesSize = values.size();

        Reducer.Context context = mock(Reducer.Context.class);

        Reduce reduce = new Reduce();
        reduce.reduce(key, values, context);

        Text expectedOutputValue = new Text(valuesSize.toString());
        verify(context).write(key, expectedOutputValue);
    }

}
