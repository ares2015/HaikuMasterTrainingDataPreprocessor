package com.haikuMasterTrainingDataPreprocessor.main;

import com.haikuMasterTrainingDataPreprocessor.data.SentencesTokenTagData;
import com.haikuMasterTrainingDataPreprocessor.factory.SentencesTokenTagDataFactory;
import com.haikuMasterTrainingDataPreprocessor.factory.SentencesTokenTagDataFactoryImpl;

/**
 * Created by Oliver on 7/14/2017.
 */
public class HaikuMasterTrainingDataPreprocessor {


    public static void main(String[] args) throws InterruptedException {
        SentencesTokenTagDataFactory sentencesTokenTagDataFactory = new SentencesTokenTagDataFactoryImpl();
        SentencesTokenTagData sentencesTokenTagData = sentencesTokenTagDataFactory.create();
    }


}