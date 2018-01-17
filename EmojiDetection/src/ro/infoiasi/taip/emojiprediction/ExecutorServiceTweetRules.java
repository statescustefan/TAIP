package ro.infoiasi.taip.emojiprediction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static ro.infoiasi.taip.emojiprediction.Main.separateTweetsInSeparateFiles;

public class ExecutorServiceTweetRules {

    private void executeTaskWithThreads(int numberOfThreads) throws IOException, InterruptedException {

        separateTweetsInSeparateFiles("us_trial.text", "expected.labels");

        Tweets tweets = new Tweets();

        tweets.readFromFile("us_trial.text");
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for(int i=0; i<numberOfThreads; i++)
        {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        tweets.rules();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.MINUTES);

    }

    private long runningDuration(int numberOfThreads) throws IOException, InterruptedException {

        ExecutorServiceTweetRules executorServiceTweetRules=new ExecutorServiceTweetRules();
        long startTime = System.currentTimeMillis();
        executorServiceTweetRules.executeTaskWithThreads(numberOfThreads);
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        ExecutorServiceTweetRules executorServiceTweetRules=new ExecutorServiceTweetRules();
        long elapsedTime=executorServiceTweetRules.runningDuration(1);
        System.out.println("1 thread: " + (double) elapsedTime / 1000);

        elapsedTime=executorServiceTweetRules.runningDuration(10);
        System.out.println("10 threads: " + (double) elapsedTime / 1000);

        elapsedTime=executorServiceTweetRules.runningDuration(50);
        System.out.println("50 threads: " + (double) elapsedTime / 1000);

        elapsedTime=executorServiceTweetRules.runningDuration(150);
        System.out.println("150 threads: " + (double) elapsedTime / 1000);

    }

}