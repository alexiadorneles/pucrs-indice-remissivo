import domain.Book;
import domain.DoubleLinkedList;
import domain.Page;
import domain.Word;
import org.junit.jupiter.api.Test;
import util.BookReader;
import util.StopwordsReader;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    public void stopwordsCountInJavaBook() {
        StopwordsReader stopwordsReader = new StopwordsReader();
        DoubleLinkedList<String> stopwords = stopwordsReader.read();
        BookReader reader = new BookReader(stopwords);
        Book java = reader.read("java");
        assertEquals(149, java.getStopwordsCount());
    }

    @Test
    public void allWordsCountInJavaBook() {
        StopwordsReader stopwordsReader = new StopwordsReader();
        DoubleLinkedList<String> stopwords = stopwordsReader.read();
        BookReader reader = new BookReader(stopwords);
        Book java = reader.read("java");
        assertEquals(388, java.getWordsCount());
    }

    @Test
    public void stopwordsPercentageInJavaBook() {
        StopwordsReader stopwordsReader = new StopwordsReader();
        DoubleLinkedList<String> stopwords = stopwordsReader.read();
        BookReader reader = new BookReader(stopwords);
        Book java = reader.read("java");
        assertEquals(38.4, java.getStopwordsPercentage(), 0.1);
    }

    @Test
    public void pagesWhereJavaWordAppearInJavaBook() {
        StopwordsReader stopwordsReader = new StopwordsReader();
        DoubleLinkedList<String> stopwords = stopwordsReader.read();
        BookReader reader = new BookReader(stopwords);
        Book java = reader.read("java");

        Word result = java.getAllWords().find(word -> word.getStripedText().equals("java"));
        DoubleLinkedList<Integer> pageNumbers = result.getPagesWhereItAppears().map(Page::getNumber);

        assertEquals(2, pageNumbers.size());
        assertTrue(pageNumbers.contains(1));
        assertTrue(pageNumbers.contains(2));
    }

}
