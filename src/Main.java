import domain.*;
import util.BookReader;
import util.StopwordsReader;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo para ser processado: ");
        String fileName = in.nextLine();
        DoubleLinkedList<Word> stopwords = new StopwordsReader().read();
        Book book = new BookReader(stopwords).read(fileName);
        Index index = new Index(book);
        printOptions();
        int chosen = in.nextInt();
        switch (chosen) {
            case 1:
                index.generate();
                System.out.println(index);
                break;
            case 2:
                System.out.println(calculateStopwordsPercentage(book) + "% desse livro são stopwords");
                break;
            case 3:
                Word found = findMostFrequentWord(book);
                System.out.println("A palavra que mais aparece no texto é '" + found.getStripedText() + "', que aparece "
                        + found.getNumberOfAppearence() + " vezes"
                );
                break;
            case 4:
                break;
        }
    }

    private static Word findMostFrequentWord(Book book) {
        return book.getAllWords()
                .filter(Word::isNOTStopword)
                .reduce((acc, word) -> word.getNumberOfAppearence() > acc.getNumberOfAppearence() ? word : acc);
    }

    public static void printOptions() {
        System.out.println("1. Exibir todo o índice remissivo (em ordem alfabética);\n" +
                "2. Exibir o percentual de stopwords do texto (quanto % do texto é formado por stopwords);\n" +
                "3. Encontrar a palavra mais frequente, isto é, com maior número de ocorrências;\n" +
                "4. Pesquisar palavra (o usuário informa uma palavra; o sistema lista os números das páginas em que a\n" +
                "palavra ocorre; na sequência, o usuário escolhe uma página e o sistema exibe a página na tela).");
    }

    public static String calculateStopwordsPercentage(Book book) {
        double percentage = (book.getTotalStopwords() * 100.0) / book.getTotalWordsCount();
        return new DecimalFormat("#.##").format(percentage);
    }
}
