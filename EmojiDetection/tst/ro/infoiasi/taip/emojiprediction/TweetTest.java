package ro.infoiasi.taip.emojiprediction;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TweetTest {

    @Test
    public void getText() {
        Tweet tweet = new Tweet();
        tweet.setText("text tweet");
        assertEquals("text tweet", tweet.getText());
    }

    @Test
    public void setText() {
        Tweet tweet = new Tweet();
        tweet.setText("text tweet");
        assertEquals("text tweet", tweet.getText());
    }

    @Test
    public void getEmoji() {
        Tweet tweet = new Tweet();
        tweet.setEmoji(1);
        assertEquals(1, tweet.getEmoji());
    }

    @Test
    public void setEmoji() {
        Tweet tweet = new Tweet();
        tweet.setEmoji(1);
        assertEquals(1, tweet.getEmoji());
    }

    @Test
    public void getPrediction() {
        Tweet tweet = new Tweet();
        ArrayList<Double> prediction = new ArrayList<>();
        for(int i=0; i<10;i++)
        {
            prediction.add(0.0);
        }
        tweet.setPrediction(prediction);
        assertEquals(10, tweet.getPrediction().size());
    }

    @Test
    public void returnIndexofMax() {
        Tweet tweet = new Tweet();
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
        assertEquals(5, tweet.returnIndexofMax());
    }

    @Test
    public void countDigits() {
        Tweet tweet = new Tweet();
        tweet.setText("123abcwer2");
        assertEquals(4, tweet.countDigits());
    }

    @Test
    public void countCapitals() {
        Tweet tweet = new Tweet();
        tweet.setText("ABCafsaa1424WE");
        assertEquals(5, tweet.countCapitals());
    }
}