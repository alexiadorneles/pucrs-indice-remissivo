package util;

import domain.DoubleLinkedList;
import domain.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StopwordsReader {
    public DoubleLinkedList<Word> read() {
        Path path = Paths.get("resources/stopwords.txt");
        DoubleLinkedList<Word> stopwords = new DoubleLinkedList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            String lineText = reader.readLine();
            while (lineText != null) {
                Word word = new Word(lineText.trim(), true);
                stopwords.add(word);
                lineText = reader.readLine();
            }

            return stopwords;
        } catch (IOException e) {
            e.printStackTrace();
            return stopwords;
        }
    }
}
