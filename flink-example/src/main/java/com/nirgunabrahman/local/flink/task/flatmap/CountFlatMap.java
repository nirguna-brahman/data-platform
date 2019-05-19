package com.nirgunabrahman.local.flink.task.flatmap;

import com.nirgunabrahman.local.flink.model.Word;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class CountFlatMap implements FlatMapFunction<String, Word> {

    @Override
    public void flatMap(String sentence, Collector<Word> out) throws Exception {
        Arrays.stream(sentence.split(" "))
                .map(key -> Word.builder()
                        .setWord(key)
                        .setCount(1)
                        .build()
                )
                .forEach(out::collect);
    }
}
