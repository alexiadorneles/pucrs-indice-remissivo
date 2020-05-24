package util;

import domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static util.TextFormatter.formatLine;
import static util.TextFormatter.formatWord;

public class BookReader {
    private static final int LINE_LIMITS_TO_A_PAGE = 40;
    private final DoubleLinkedList<String> stopWords;
    private final DoubleLinkedList<Word> allWords;

    public BookReader(DoubleLinkedList<String> stopwords) {
        this.allWords = new DoubleLinkedList<>();
        this.stopWords = stopwords;
    }

    public Book read(String fileName) {
        System.out.println("Lendo e indexando o livro " + fileName + "...");
        int lineCount = 1;
        Path path = Paths.get("resources/" + (fileName.endsWith(".txt") ? fileName : fileName + ".txt"));
        Book book = new Book();

        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            Page page = new Page(1);

            String lineText = reader.readLine();
            while (lineText != null) {
                Line line = this.createLineWithAllWords(lineText, page);
                page.addLine(line);

                if (this.isTheEndOfAPage(lineCount)) {
                    book.addPage(page);
                    page = new Page(this.calculatePageNumber(lineCount));
                }

                lineCount++;
                lineText = reader.readLine();
            }

            if (!this.isTheEndOfAPage(lineCount)) {
                book.addPage(page);
            }

            System.out.println("Livro lido e indexado com sucesso!");
            System.out.println("Total de páginas: " + book.getPagesCount());
            System.out.println("Stopwords ignoradas: " + book.getStopwordsCount());
            return book;
        } catch (IOException e) {
            throw new RuntimeException("Erro na leitura do arquivo: ", e);
        }
    }

    private int calculatePageNumber(int lineCount) {
        return (lineCount / LINE_LIMITS_TO_A_PAGE) + 1;
    }

    private boolean isTheEndOfAPage(int lineCount) {
        return lineCount % LINE_LIMITS_TO_A_PAGE == 0;
    }

    private Line createLineWithAllWords(String lineText, Page page) {
        Line line = new Line(lineText);
        String[] words = formatLine(lineText).split(" ");

        for (String wordText : words) {
            this.createAndInsertWord(page, line, formatWord(wordText));
        }

        return line;
    }

    private void createAndInsertWord(Page page, Line line, String text) {
        if (text.isBlank()) return;
        Word wordAlreadyIndexed = this.allWords.find(word -> word.getStripedText().equals(text));
        if (isNull(wordAlreadyIndexed)) {
            Word word = new Word(text, this.stopWords.contains(text));
            word.appearsOn(page);
            line.addWord(word);
            this.allWords.add(word);
        } else {
            wordAlreadyIndexed.incrementNumberOfAppearence();
            wordAlreadyIndexed.appearsOn(page);
        }
    }
}