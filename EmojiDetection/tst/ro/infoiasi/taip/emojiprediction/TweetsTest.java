package ro.infoiasi.taip.emojiprediction;

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
        assertNotNull(tweets.getTweets());
    }

    @Test
    public void readFromFile() throws IOException {
        Tweets tweets = new Tweets();

        tweets.readFromFile("resources/us_trial.text");

        assertEquals(50000, tweets.getTweets().size());
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

        assertEquals("happy", words.get(0));
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
        ArrayList<Double> list=new ArrayList<>();
        list.add(1.0);
        list.add(2.0);
        list.add(3.0);
        list.add(4.0);
        list.add(5.0);
        list.add(59.0);
        list.add(20.0);
        list.add(30.0);
        tweet.setPrediction(list);
        Tweets tweets = new Tweets();
        List<Tweet> tweetList = new ArrayList<>();
        tweetList.add(tweet);
        tweets.setTweets(tweetList);
        tweets.assignedEmoji();

        assertEquals(5, tweet.getEmoji());
    }
}