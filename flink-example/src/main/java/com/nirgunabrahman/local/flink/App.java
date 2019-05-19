package com.nirgunabrahman.local.flink;

import com.nirgunabrahman.local.flink.model.Word;
import com.nirgunabrahman.local.flink.task.CountTask;
import com.nirgunabrahman.local.flink.task.Task;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.Properties;

public class App {

    public static void collect(String sentence, Collector<Word> out) {
        Arrays.stream(sentence.split(" "))
                .map(key -> Word.builder()
                            .setWord(key)
                            .setCount(1)
                            .build()
                )
                .forEach(out::collect);
    }



    public static Word reduce(Word wordCount1, Word wordCount2) throws Exception {
        return Word.builder()
                .setWord(wordCount1.get(Word.Fields.WORD).toString())
                .setCount(
                        (int) wordCount1.get(Word.Fields.COUNT) + (int) wordCount2.get(Word.Fields.COUNT)
                ).build();
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("zookeeper.connect", "localhost:2181");

        FlinkKafkaConsumer<String> consumer =
            new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties);
        consumer.setStartFromEarliest();

        Task countTask = new CountTask();

        DataStream<String> stream = env.addSource(
                consumer
        );

        countTask.publish(countTask.transform(stream), Task.Sink.NONE, new Properties());

        env.execute();

    }
}
