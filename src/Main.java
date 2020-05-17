import domain.Book;
import domain.DoubleLinkedList;
import domain.Index;
import domain.Word;
import util.BookReader;
import util.StopwordsReader;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(1.0 / 2);
        System.out.println("Digite o nome do arquivo para ser processado: ");
        String fileName = in.nextLine();
        Book book = new BookReader().read(fileName);
        DoubleLinkedList<Word> stopwords = new StopwordsReader().read();
        System.out.println(stopwords);
        System.out.println(book);

        new Index(book, stopwords).generate();

        System.out.println(calculateStopwordsPercentage(book));
    }

    public static String calculateStopwordsPercentage(Book book) {
        double percentage = (book.getTotalStopwords() * 100.0) / book.getTotalWordsCount();
        return new DecimalFormat("#.##").format(percentage);
    }
}
