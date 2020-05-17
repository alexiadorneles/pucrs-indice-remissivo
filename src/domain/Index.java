package domain;

public class Index {
    private Book book;
    private DoubleLinkedList<Word> stopwords;

    public Index(Book book, DoubleLinkedList<Word> stopwords) {
        this.book = book;
        this.stopwords = stopwords;
    }

    public void generate() {
        this.parseWords();
    }

    private void parseWords() {
        this.book.getPages().forEach(page -> page.getLines().forEach(line -> line.getWords().forEach(word -> {
            if (this.isStopword(word)) {
                this.removeWord(word, line);
                page.incrementNumberOfStopwords();
            }
            this.normalizeWords(word, line);
        })));
    }

    private boolean isStopword(Word word) {
        return stopwords.contains(word);
    }

    private void normalizeWords(Word word, Line line) {
        if (word.isStopword()) return;
        String originalText = word.getOriginalText();
        String strippedText = originalText.replace(",", "").replace(".", "");
        word.setStripedText(strippedText);
    }

    private void removeWord(Word word, Line line) {
        word.setStripedText("");
        word.setStopword(true);
    }

}
