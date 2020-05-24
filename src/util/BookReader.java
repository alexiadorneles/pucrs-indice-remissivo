package util;

import domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookReader {
    private static final int LINE_LIMITS_TO_A_PAGE = 40;
    private final DoubleLinkedList<Word> stopWords;
    private final DoubleLinkedList<Word> allWords;

    public BookReader(DoubleLinkedList<Word> stopwords) {
        this.allWords = new DoubleLinkedList<>();
        this.stopWords = stopwords;
    }

    public Book read(String fileName) {
        int lineCount = 1;
        Path path = Paths.get("resources/" + (fileName.endsWith(".txt") ? fileName : fileName + ".txt"));
        Book book = new Book();

        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            Page page = new Page(1);

            String lineText = reader.readLine();
            while (lineText != null) {
                Line line = this.getLine(lineText, page);
                line.setOriginalText(lineText);
                page.addLine(line);

                if (this.isTheEndOfAPage(lineCount)) {
                    book.addPage(page);
                    page = new Page(this.calculatePageNumber(lineCount) + 1);
                }

                lineCount++;
                lineText = reader.readLine();
            }

            if (!this.isTheEndOfAPage(lineCount)) {
                book.addPage(page);
            }

            return book;
        } catch (IOException e) {
            throw new RuntimeException("Erro na leitura do arquivo: ", e);
        }
    }

    private int calculatePageNumber(int lineCount) {
        return lineCount / LINE_LIMITS_TO_A_PAGE;
    }

    private boolean isTheEndOfAPage(int lineCount) {
        return lineCount % LINE_LIMITS_TO_A_PAGE == 0;
    }

    private Line getLine(String lineText, Page page) {
        Line line = new Line(page);
        String[] words = lineText.replaceAll("\\t", " ").replaceAll("-", " ").replaceAll("/", " ").split(" ");
        for (String wordText : words) {
            String formattedText = this.formatText(wordText);
            if (formattedText.isBlank()) continue;
            Word wordFromList = this.allWords.find(word -> word.getStripedText().equals(formattedText));

            if (wordFromList != null) {
                wordFromList.incrementNumber();
                wordFromList.appearsOn(page);
                continue;
            }

            Word word = new Word(wordText, formattedText);
            if (this.stopWords.contains(word)) {
                word.setStopword(true);
                line.addWord(word);
                page.incrementNumberOfStopwords();
                page.incrementNumberOfWords();
                continue;
            }

            word.appearsOn(page);
            page.incrementNumberOfWords();
            line.addWord(word);
            this.allWords.add(word);
        }
        return line;
    }

    private String formatText(String wordText) {
        return wordText.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
    }
}
