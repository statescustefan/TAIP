import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TweetsTest {

    @Test
    public void getTweets() {
        Tweets tweets = new Tweets();
        tweets.setTweets(new ArrayList<>());
        assertTrue(tweets.getTweets() != null);
    }

    @Test
    public void setTweets() {
        Tweets tweets = new Tweets();
        tweets.setTweets(new ArrayList<>());
        assertTrue(tweets.getTweets() != null);
    }

    @Test
    public void readFromFile() throws IOException {
        Tweets tweets = new Tweets();

        tweets.readFromFile("resources/us_trial.text");

        assertTrue(tweets.getTweets().size() == 50000);
    }

    @Test(expected = FileNotFoundException.class)
    public void readFromFileNotFound() throws IOException {
        Tweets tweets = new Tweets();

        tweets.readFromFile("resources/us_trial22.text");
    }

    @Test
    public void readKeyWordsFromFile() throws IOException {
        Tweets tweets = new Tweets();
        List<String> words = tweets.readKeyWordsFromFile("resources/0keyWords.txt");

        assertTrue(words.get(0).equals("happy"));
    }

    @Test(expected = FileNotFoundException.class)
    public void readKeyWordsFromFileNotFound() throws IOException {
        Tweets tweets = new Tweets();
        List<String> words = tweets.readKeyWordsFromFile("resources/0keyWords222.txt");
    }

    @Test
    public void assignedEmoji() {
        Tweet tweet = new Tweet();
        tweet.setText("sfasfa");
        float [] x = {1,2,3,4,5,59,20, 30};
        tweet.setPrediction(x);
        Tweets tweets = new Tweets();
        List<Tweet> tweetList = new ArrayList<>();
        tweetList.add(tweet);
        tweets.setTweets(tweetList);
        tweets.assignedEmoji();

        assertTrue(tweet.getEmoji() == 5);
    }
}