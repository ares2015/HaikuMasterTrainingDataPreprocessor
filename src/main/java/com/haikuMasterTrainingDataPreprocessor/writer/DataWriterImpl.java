package com.haikuMasterTrainingDataPreprocessor.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oliver on 7/14/2017.
 */
public class DataWriterImpl implements DataWriter, Runnable {

    private List<String> dataList;

    private String path;

    public DataWriterImpl(List<String> dataList, String path) {
        this.dataList = dataList;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write() throws IOException {
        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String data : dataList) {
            System.out.println(data);
            bw.write(data);
            bw.newLine();
        }
        bw.close();
        fw.close();
    }

}
