package com.haikuMasterTrainingDataPreprocessor.data;

import java.util.List;

/**
 * Created by Oliver on 7/13/2017.
 */
public class SentencesTokenTagData {

    private List<String> sentences;

    private List<String> tokenTagDataStringRows;

    public SentencesTokenTagData(List<String> sentences, List<String> tokenTagDataStringRows) {
        this.sentences = sentences;
        this.tokenTagDataStringRows = tokenTagDataStringRows;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public List<String> getTokenTagDataStringRows() {
        return tokenTagDataStringRows;
    }

}