import org.junit.Test;

import static org.junit.Assert.*;

public class TweetTest {

    @Test
    public void getText() {
        Tweet tweet = new Tweet();
        tweet.setText("text tweet");
        assertTrue(tweet.getText().equals("text tweet"));
    }

    @Test
    public void setText() {
        Tweet tweet = new Tweet();
        tweet.setText("text tweet");
        assertTrue(tweet.getText().equals("text tweet"));
    }

    @Test
    public void getEmoji() {
        Tweet tweet = new Tweet();
        tweet.setEmoji(1);
        assertTrue(tweet.getEmoji()==1);
    }

    @Test
    public void setEmoji() {
        Tweet tweet = new Tweet();
        tweet.setEmoji(1);
        assertTrue(tweet.getEmoji()==1);
    }

    @Test
    public void getPrediction() {
        Tweet tweet = new Tweet();
        tweet.setPrediction(new float[10]);
        assertTrue(tweet.getPrediction().length == 10);
    }

    @Test
    public void setPrediction() {
        Tweet tweet = new Tweet();
        tweet.setPrediction(new float[10]);
        assertTrue(tweet.getPrediction().length == 10);
    }

    @Test
    public void returnIndexofMax() {
        Tweet tweet = new Tweet();
        float [] x = {1,2,3,4,5,59,20, 30};
        tweet.setPrediction(x);
        assertTrue(tweet.returnIndexofMax() == 5);
    }

    @Test
    public void countDigits() {
        Tweet tweet = new Tweet();
        tweet.setText("123abcwer2");
        assertTrue(tweet.countDigits() == 4);
    }

    @Test
    public void countCapitals() {
        Tweet tweet = new Tweet();
        tweet.setText("ABCafsaa1424WE");
        assertTrue(tweet.countCapitals() == 5);
    }
}