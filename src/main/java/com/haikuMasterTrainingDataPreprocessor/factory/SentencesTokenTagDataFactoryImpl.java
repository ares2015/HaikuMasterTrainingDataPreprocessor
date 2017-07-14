package com.haikuMasterTrainingDataPreprocessor.factory;

import com.haikuMasterTrainingDataPreprocessor.data.SentencesTokenTagData;
import com.haikuMasterTrainingDataPreprocessor.writer.DataWriter;
import com.haikuMasterTrainingDataPreprocessor.writer.DataWriterImpl;
import com.postagger.main.PosTagger;
import com.postagger.main.PosTaggerImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Oliver on 7/13/2017.
 */
public class SentencesTokenTagDataFactoryImpl implements SentencesTokenTagDataFactory {

    private static final String TOKEN_TAG_DATA_PATH = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\TokenTagData.txt";

    private static final String FILTERED_SENTENCES_DATA_PATH = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\FilteredHaikuMasterTextData.txt";

    private PosTagger posTagger = new PosTaggerImpl();

    @Override
    public SentencesTokenTagData create() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> filteredSentences = new ArrayList<>();
        List<String> tokenTagDataStringRows = new ArrayList<>();
        Map<String, Set<String>> tokenTagDataCache = new HashMap<String, Set<String>>();
        int numberOfTaggedWords = 0;
        int numberOfSentences = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\MergedWikiGuthenbergWord2VecData.txt"));
//            br = new BufferedReader(new FileReader("C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\DummyData.txt"));
//            br = new BufferedReader(new FileReader("C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\WikiWord2VecFile.txt"));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String sentence = br.readLine();
            while (sentence != null) {
                if (!"".equals(sentence)) {
                    numberOfSentences++;
                    StringBuilder stringBuilder = new StringBuilder();
                    String[] tokensArray = sentence.split("\\ ");
                    if (tokensArray.length <= 30) {
                        try {
                            List<List<String>> tagsMultiList = posTagger.tag(sentence);
                            String tagsAsString = tagsMultiList.get(0).get(0);
                            String[] tagsArray = tagsAsString.split("\\ ");
                            for (int i = 0; i < tagsArray.length; i++) {
                                String tag = tagsArray[i];
                                String token = tokensArray[i];
                                if (tokenTagDataCache.containsKey(token)) {
                                    if (!tokenTagDataCache.get(token).contains(tag)) {
                                        String tokenTagDataStringRow = token + "#" + tag;
                                        tokenTagDataStringRows.add(tokenTagDataStringRow);
                                        numberOfTaggedWords++;
                                        tokenTagDataCache.get(token).add(tag);
                                    }
                                } else {
                                    Set<String> tags = new HashSet<String>();
                                    tags.add(tag);
                                    tokenTagDataCache.put(token, tags);
                                    String tokenTagDataStringRow = token + "#" + tag;
                                    tokenTagDataStringRows.add(tokenTagDataStringRow);
                                    numberOfTaggedWords++;
                                }
                                if ("N".equals(tag) || "AJ".equals(tag) || "V".equals(tag) || "AV".equals(tag)) {
                                    stringBuilder.append(token);
                                    stringBuilder.append(" ");
                                }
                            }
                            String filteredSentence = stringBuilder.toString();
                            System.out.println("Sentence number: " + numberOfSentences);
                            System.out.println("Filtered sentence: " + filteredSentence);
                            filteredSentences.add(filteredSentence);
                        } catch (Exception e) {
                            sentence = br.readLine();
                        }
                    }
                }
                if (filteredSentences.size() % 20000 == 0) {
                    DataWriter filteredSentencesDataWriter = new DataWriterImpl(filteredSentences, FILTERED_SENTENCES_DATA_PATH);
                    filteredSentencesDataWriter.write();
                    filteredSentences.clear();
                }
                if (tokenTagDataStringRows.size() % 20000 == 0) {
                    DataWriter tokenTagDataStringRowsDataWriter = new DataWriterImpl(tokenTagDataStringRows, TOKEN_TAG_DATA_PATH);
                    tokenTagDataStringRowsDataWriter.write();
                    tokenTagDataStringRows.clear();
                }
                sentence = br.readLine();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(filteredSentences.size() + " sentences processed in " + (elapsedTime / 1000) / 60 + " minutes and "
                + +(elapsedTime / 1000) % 60 + " seconds");
        System.out.println(numberOfTaggedWords + " token tags were added into TokenTags model");
        return new SentencesTokenTagData(filteredSentences, tokenTagDataStringRows);
    }
}