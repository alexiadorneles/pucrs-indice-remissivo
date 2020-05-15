import domain.Book;
import util.BookReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo para ser processado: ");
        String fileName = in.nextLine();
        Book book =new BookReader().read(fileName);
        System.out.println(book);
    }
}
