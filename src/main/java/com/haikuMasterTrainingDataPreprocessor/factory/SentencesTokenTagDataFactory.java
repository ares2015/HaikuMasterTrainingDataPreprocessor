package com.haikuMasterTrainingDataPreprocessor.factory;

import com.haikuMasterTrainingDataPreprocessor.data.SentencesTokenTagData;

/**
 * Created by Oliver on 7/13/2017.
 */
public interface SentencesTokenTagDataFactory {

    SentencesTokenTagData create() throws InterruptedException;

}
