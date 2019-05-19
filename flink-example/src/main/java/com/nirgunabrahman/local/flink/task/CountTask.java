package com.nirgunabrahman.local.flink.task;

import com.nirgunabrahman.local.flink.model.Word;
import com.nirgunabrahman.local.flink.task.flatmap.CountFlatMap;
import com.nirgunabrahman.local.flink.task.reduce.CountReducer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.slf4j.Logger;

import java.util.Properties;

public class CountTask extends Task<String, Word> {

    private static final Logger LOG = null;

    @Override
    public DataStream<Word> transform(DataStream<String> source) {
        return source.flatMap(new CountFlatMap())
                .returns(Word.class)
                .keyBy(wc -> ((Word) wc).get(Word.Fields.WORD))
                .countWindow(2)
                .reduce(new CountReducer());
    }

    @Override
    public boolean publish(DataStream<String> source, Sink sink, Properties props) {
        try {
            source.print();
        } catch (Exception ex) {
            LOG.error("Something has gone wrong : {}", ex);
            return false;
        }

        return true;
    }

}
