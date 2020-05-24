package util;

import domain.DoubleLinkedList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StopwordsReader {
    public DoubleLinkedList<String> read() {
        Path path = Paths.get("resources/stopwords.txt");
        DoubleLinkedList<String> stopwords = new DoubleLinkedList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            String lineText = reader.readLine();
            while (lineText != null) {
                stopwords.add(lineText.trim());
                lineText = reader.readLine();
            }

            return stopwords;
        } catch (IOException e) {
            e.printStackTrace();
            return stopwords;
        }
    }
}
