package com.nirgunabrahman.local.flink.model;

public class Word {

    public enum Fields {
        WORD,
        COUNT
    }

    private String word;

    private Integer count;

    private Word(String word, int count) {
        this.word= word;
        this.count = count;
    }

    public Object get(Fields field) throws Exception {
        switch(field) {
            case WORD: return word;
            case COUNT: return count;
            default: throw new Exception("Incorrect field type");
        }
    }

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("word: %s, count: %d", word, count);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String word;
        private int count;

        public Builder setWord(String word) {
            this.word = word;
            return this;
        }

        public Builder setCount(Integer count) {
            this.count = count;
            return this;
        }

        public Word build() {
            return new Word(word, count);
        }
    }
}