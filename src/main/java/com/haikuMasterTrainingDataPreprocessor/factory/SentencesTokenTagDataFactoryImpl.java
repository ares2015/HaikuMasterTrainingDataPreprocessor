package com.haikuMasterTrainingDataPreprocessor.factory;

import com.haikuMasterTrainingDataPreprocessor.data.SentencesTokenTagData;
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

    private PosTagger posTagger = new PosTaggerImpl();

    @Override
    public SentencesTokenTagData create() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> filteredSentences = new ArrayList<>();
        List<String> tokenTagDataStringRows = new ArrayList<>();
        Map<String, Set<String>> tokenTagDataCache = new HashMap<String, Set<String>>();
        int numberOfTaggedWords = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\MergedWikiGuthenbergWord2VecData.txt"));
//            br = new BufferedReader(new FileReader("C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\WikiWord2VecFile.txt"));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String sentence = br.readLine();
            while (sentence != null) {
                if (!"".equals(sentence)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String[] tokensList = sentence.split("\\ ");
                    List<List<String>> tagsMultiList = posTagger.tag(sentence);
                    for (int i = 0; i < tokensList.length; i++) {
                        for (List<String> tagsList : tagsMultiList) {
                            String tag = tagsList.get(i);
                            String token = tokensList[i];
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
                    }
                    String filteredSentence = stringBuilder.toString();
                    System.out.println("Filtered sentence: " + filteredSentence);
                    filteredSentences.add(filteredSentence);
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