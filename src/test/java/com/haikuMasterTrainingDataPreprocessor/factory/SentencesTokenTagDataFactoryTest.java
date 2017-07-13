package com.haikuMasterTrainingDataPreprocessor.factory;

import com.haikuMasterTrainingDataPreprocessor.data.SentencesTokenTagData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Oliver on 7/13/2017.
 */
public class SentencesTokenTagDataFactoryTest {

    @Test
    public void test() throws InterruptedException {
        SentencesTokenTagDataFactory sentencesTokenTagDataFactory = new SentencesTokenTagDataFactoryImpl();
        SentencesTokenTagData sentencesTokenTagData = sentencesTokenTagDataFactory.create();
        assertTrue(sentencesTokenTagData.getSentences().size() > 0);
        assertTrue(sentencesTokenTagData.getTokenTagDataStringRows().size() > 0);
    }
}
