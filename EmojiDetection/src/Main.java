import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static double evalutionPrecision(String filePathExcepted, String filePathActual) throws FileNotFoundException {
        File actual = new File(filePathActual);
        File excepted = new File(filePathExcepted);
        Scanner scanner1 = new Scanner(actual);
        Scanner scanner2 = new Scanner(excepted);
        double total = 0;
        double countEquals = 0;
        while(scanner1.hasNext() && scanner2.hasNext()){
            if(scanner1.nextInt() == scanner2.nextInt()){
                countEquals++;
            }
            total++;
        }

        return countEquals/total*100;
    }

    public static int skippedTweets(String filePath) throws FileNotFoundException {
        int count = 0;
        Scanner scanner = new Scanner(new File(filePath));
        while(scanner.hasNext()){
            if(scanner.nextInt() == - 1){
                count++;
            }
        }
        return count;
    }

    public static void separateTweetsInSeparateFiles(String filePathTweets, String filePathResults) throws IOException {

        for(int i = 0; i < 20; i++) {

            PrintWriter printWriter = new PrintWriter("resources/"+ i + "emoji.txt");
            File tweets = new File(filePathTweets);
            File results = new File(filePathResults);
            Scanner scanner1 = new Scanner(tweets);
            Scanner scanner2 = new Scanner(results);

            while (scanner1.hasNext() && scanner2.hasNext()) {
                int x = scanner2.nextInt();
                String line = scanner1.nextLine();
                if(x == i) {
                    printWriter.println(line);
                }
            }

            printWriter.close();
            printWriter.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        //separateTweetsInSeparateFiles("resources/us_trial.text", "resources/expected.labels");

        Tweets tweets = new Tweets();

        tweets.readFromFile("resources/us_trial.text");

        //System.out.println(tweets.getTweets().get(25).getText());

        tweets.rules();
        tweets.assignedEmoji();

        try {
            tweets.generateResults();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Precision: " + evalutionPrecision("resources/expected.labels", "resources/actual.labels"));

        System.out.println("Tweets skipped: " + skippedTweets("resources/actual.labels"));

       /* System.out.println("Find words.....");
        tweets.findMostUsedWords(20);
        System.out.println("DONE!");
*/
        /*System.out.println(tweets.getTweets().get(0).countCapitals());
        System.out.println(tweets.getTweets().get(18).countDigits());*/
    }
}
