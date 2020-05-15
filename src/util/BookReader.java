package util;

import domain.Book;
import domain.Line;
import domain.Page;
import domain.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookReader {
    private static final int LINE_LIMITS_TO_A_PAGE = 40;

    public Book read(String fileName) {
        int lineCount = 1;
        Path path = Paths.get("resources/" + (fileName.endsWith(".txt") ? fileName : fileName + ".txt"));
        Book book = new Book();

        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            Page page = new Page(1);

            String lineText = reader.readLine();
            while (lineText != null) {
                Line line = this.getLine(lineText);
                page.addLine(line);

                if (this.isTheEndOfAPage(lineCount)) {
                    book.addPage(page);
                    page = new Page(this.calculatePageNumber(lineCount));
                }

                lineCount++;
                lineText = reader.readLine();
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

    private Line getLine(String lineText) {
        Line line = new Line();
        String[] words = lineText.replaceAll("\\t", " ").split(" ");
        for (String wordText : words) {
            Word word = new Word(wordText, this.formatText(wordText));
            line.addWord(word);
        }
        return line;
    }

    private String formatText(String wordText) {
        return wordText.toLowerCase();
    }
}
