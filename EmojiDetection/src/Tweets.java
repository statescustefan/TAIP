import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tweets {
    List<Tweet> tweets;

    Tweets(){
        tweets = new ArrayList<>();
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void readFromFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                Tweet tweet = new Tweet();
                tweet.setText(line);
                this.tweets.add(tweet);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> readKeyWordsFromFile(String filePath) throws IOException {
        List<String> output = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        } catch (Exception e) {
            throw e;
        }
        return output;
    }

    public void assignedEmoji(){
        for(Tweet tweet : this.tweets){
            tweet.setEmoji(tweet.returnIndexofMax());
        }
    }

    public void generateResults() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("resources/actual.labels");
        for(Tweet tweet : this.tweets){
            printWriter.println(tweet.getEmoji());
        }
        printWriter.close();
        printWriter.flush();
    }

    public void findMostUsedWords(int numberOfWords) throws IOException {
        this.tweets.clear();
        List<String> uniqueWords = new ArrayList<>();
        for(int i = 0; i < 20; ++i){
            readFromFile("resources/" + i + "emoji.txt");
            for (Tweet tweet : this.tweets) {
                String[] splitWordsTweet = tweet.getText().toLowerCase().split(" ");
                for (String word : splitWordsTweet) {
                    if (!uniqueWords.contains(word)) {
                        uniqueWords.add(word);
                    }
                }
            }

            int count[] = new int [uniqueWords.size()];
            for(Tweet tweet : this.tweets){
                String[] splitWordsTweet = tweet.getText().toLowerCase().split(" ");

                for (String word : splitWordsTweet) {
                   if(uniqueWords.contains(word)){
                       count[uniqueWords.indexOf(word.toString())]++;
                   }
                }

            }

            for(int x = 0; x < count.length - 1; ++x){
                for(int y = x + 1; y < count.length; ++y){
                    if(count[x] < count[y]){
                        String aux = uniqueWords.get(x);
                        uniqueWords.set(x, uniqueWords.get(y));
                        uniqueWords.set(y , aux);

                        int aux2 = count[x];
                        count[x] = count[y];
                        count[y] = aux2;
                    }
                }
            }

            PrintWriter printWriter = new PrintWriter("resources/" + i + "keyWords.txt");
            int contor = 0;
            for(String word : uniqueWords){
                if( word.length() > 4 && !word.equals("@user")) {
                    printWriter.println(word);
                    contor++;
                }
                if(contor == numberOfWords){
                    break;
                }
            }
            printWriter.close();
            printWriter.flush();

            this.tweets.clear();
        }
    }

    public void rules() throws IOException {
        int count = 0;
        for(Tweet tweet : this.tweets) {
            for (int i = 0; i < 20; ++i) {
                List<String> words = readKeyWordsFromFile("resources/" + i + "keyWords.txt");

                for(String word : words){
                    if(tweet.getText().toLowerCase().contains(word)){
                        tweet.prediction[i]+= 1;
                    }
                }
            }
            count++;
            if(count % 2500 == 0){
                System.out.println(count / 500 + "%");
            }
        }
      /*  int i = 1;
        for(Tweet tweet : this.tweets){
            if(tweet.getText().toLowerCase().contains("fun")){
                tweet.prediction[0] += 5;
                tweet.prediction[1] += 2.5;
                tweet.prediction[2] += 2.5;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 5;
                tweet.prediction[6] += 5;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 2.5;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 2.5;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 5;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 7.5;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("love")){
                tweet.prediction[0] += 10;
                tweet.prediction[1] += 10;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 10;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 10;
                tweet.prediction[9] += 10;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 10;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("cool") || tweet.getText().toLowerCase().contains("sunglasses")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 10;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.countCapitals() > 20){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 7.5;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 10;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.countDigits() > 10){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 5;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 2.5;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("#joy")){
                tweet.prediction[0] += 5;
                tweet.prediction[1] += 2.5;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 7.5;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("holiday")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 2.5;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 7.5;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 10;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("birthday")){
                tweet.prediction[0] += 5;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().contains("Trump") || tweet.getText().contains("Clinton") || tweet.getText().toLowerCase().contains("president") || tweet.getText().toLowerCase().contains("america") || tweet.getText().contains("vote")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 10;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("photo")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 10;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("memories")){
                tweet.prediction[0] += 5;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 5;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("christmas") || tweet.getText().toLowerCase().contains("tree")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 10;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("sun")){
                tweet.prediction[0] += 5;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 10;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("kiss")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 10;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("angry")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 7.5;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("fire") || tweet.getText().toLowerCase().contains("burn") || tweet.getText().toLowerCase().contains("flames")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 10;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }

            if(tweet.getText().toLowerCase().contains("blue")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 10;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }

            if(tweet.getText().toLowerCase().contains("purple")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 10;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().contains("...") || tweet.getText().toLowerCase().contains("night")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 10;
                //System.out.println( i + ")" + tweet.getText());
            }

            if(tweet.getText().toLowerCase().contains("cry") || tweet.getText().toLowerCase().contains("laugh")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 10;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("dream") || tweet.getText().toLowerCase().contains("shit") || tweet.getText().toLowerCase().contains("fuck")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 10;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("heart") || tweet.getText().toLowerCase().contains("family")){
                tweet.prediction[0] += 10;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("beach") || tweet.getText().toLowerCase().contains("warm")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 10;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            if(tweet.getText().toLowerCase().contains("thank")){
                tweet.prediction[0] += 0;
                tweet.prediction[1] += 0;
                tweet.prediction[2] += 0;
                tweet.prediction[3] += 0;
                tweet.prediction[4] += 0;
                tweet.prediction[5] += 0;
                tweet.prediction[6] += 0;
                tweet.prediction[7] += 0;
                tweet.prediction[8] += 0;
                tweet.prediction[9] += 0;
                tweet.prediction[10] += 0;
                tweet.prediction[11] += 0;
                tweet.prediction[12] += 0;
                tweet.prediction[13] += 0;
                tweet.prediction[14] += 0;
                tweet.prediction[15] += 0;
                tweet.prediction[16] += 0;
                tweet.prediction[17] += 0;
                tweet.prediction[18] += 0;
                tweet.prediction[19] += 0;
                //System.out.println( i + ")" + tweet.getText());
            }
            i++;
        }*/
    }
}
