package com.nirgunabrahman.local.flink.task.reduce;

import com.nirgunabrahman.local.flink.model.Word;
import org.apache.flink.api.common.functions.ReduceFunction;

public class CountReducer implements ReduceFunction<Word> {

    @Override
    public Word reduce(Word wordCount1, Word wordCount2) throws Exception {
        return Word.builder()
                .setWord(wordCount1.get(Word.Fields.WORD).toString())
                .setCount((int) wordCount1.get(Word.Fields.COUNT) + (int) wordCount2.get(Word.Fields.COUNT))
                .build();
    }
}
