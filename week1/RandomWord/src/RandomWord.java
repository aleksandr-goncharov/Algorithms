import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String championWord = null;
        var wordsCount = 0;

        while (!StdIn.isEmpty()) {
            var word = StdIn.readString();

            wordsCount++;

            if (StdRandom.bernoulli(1.0/wordsCount)) {
                championWord = word;
            }
        }

        StdOut.println(championWord);
    }
}