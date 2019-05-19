package com.nirgunabrahman.local.flink.task;

import org.apache.flink.streaming.api.datastream.DataStream;

import java.util.Properties;

public abstract class Task<IN, OUT> {

    public enum Sink {
        KAFKA,
        CASSANDRA,
        GRAPHITE,
        NONE
    }

    public abstract DataStream<OUT> transform(DataStream<IN> source);

    /**
     * Define the data sink here. This will be used to publish feeds to the
     * appropriate sink. Override this method. Supposed to be abstract.
     *
     * NOTE: Muddled thinking - will revisit this later !
     *
     * @param source
     * @param sink
     * @param props
     * @return
     */
    public boolean publish(DataStream<IN> source, Sink sink, Properties props) {
        if (sink == Sink.NONE) {
            return false;
        }

        return true;
    }

}
