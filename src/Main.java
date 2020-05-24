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
        int chosen = 0;
        while (chosen != 5) {
            System.out.println();
            printOptions();
            chosen = in.nextInt();
            switch (chosen) {
                case 1:
                    System.out.println(printIndex(book.getAllWords().filter(Word::isNOTStopword)));
                    break;
                case 2:
                    System.out.println(calculateStopwordsPercentage(book) + "% desse livro são stopwords");
                    break;
                case 3:
                    Word mostFrequentWord = findMostFrequentWord(book);
                    System.out.println("A palavra que mais aparece no texto é '" + mostFrequentWord.getStripedText() + "', que aparece "
                            + mostFrequentWord.getNumberOfAppearence() + " vezes"
                    );
                    break;
                case 4:
                    System.out.println("Digite a plavra pra pesquisar: ");
                    String text = in.next();
                    Word found = book.getAllWords().find(word -> word.getStripedText().equals(text));
                    System.out.println(printIndex(DoubleLinkedList.asList(found)));
                    System.out.println("\nEscolha uma página: ");
                    int pageNumber = in.nextInt();
                    Page selectedPage = found.getPagesWhereItAppears().find(page -> page.getNumber() == pageNumber);
                    System.out.println(selectedPage);
            }
        }
    }

    private static StringBuilder printIndex(DoubleLinkedList<Word> allWords) {
        allWords.sort();
        StringBuilder builder = new StringBuilder();
        allWords.forEach(word -> {
            builder.append(word.getStripedText());
            LinkedSet<Page> pages = word.getPagesWhereItAppears();
            pages.forEach(page -> builder.append(" -> ").append(page.getNumber()));
            builder.append("\n");
        });
        return builder;
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
        System.out.println("5. Sair");
    }

    public static String calculateStopwordsPercentage(Book book) {
        return new DecimalFormat("#.##").format(book.getStopwordsPercentage());
    }
}
