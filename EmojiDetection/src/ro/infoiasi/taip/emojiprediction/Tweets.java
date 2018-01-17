package ro.infoiasi.taip.emojiprediction;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
        Tweet tweetPred=new Tweet();
        //double[] predictionElem=tweetPred.getPrediction();

        for(Tweet tweet : this.tweets) {
            ArrayList<Double> prediction = new ArrayList<>();
            for(int i=0; i<20;i++)
            {
                prediction.add(0.0);
            }
            tweet.setPrediction(prediction);
//            double predictionElement;
            for (int i = 0; i < 20; ++i) {
                List<String> words = readKeyWordsFromFile("resources/" + i + "keyWords.txt");
                double predictionElement;

                for(String word : words){
                    if(tweet.getText().toLowerCase().contains(word)){
                        //tweet.prediction[i]+= 1;
                        predictionElement=tweet.getPrediction().get(i);
                        //predictionElement=predictionElem[i];
//                        predictionElement=tweet.prediction[i];
                        predictionElement+=1;
                        //predictionElem.set(i,predictionElement);
                        //predictionElem[i]=predictionElement;
                        //tweet.prediction=predictionElem;
                        //tweet.prediction[i]=predictionElement;
                        tweet.prediction.set(i,predictionElement);
                    }
                }
            }
            count++;
            if(count % 2500 == 0){
                System.out.println(count / 500 + "%");
            }
        }


    }
//public void rules() throws IOException {
//    int count = 0;
//    for(Tweet tweet : this.tweets) {
//        System.out.println("Prediction:" + tweet.prediction[1]);
//        for (int i = 0; i < 20; ++i) {
//            List<String> words = readKeyWordsFromFile(i + "keyWords.txt");
//
//            for(String word : words){
//                if(tweet.getText().toLowerCase().contains(word)){
//                    tweet.prediction[i]+= 1;
//                }
//            }
//        }
//        count++;
//        if(count % 2500 == 0){
//            System.out.println(count / 500 + "%");
//        }
//    }
//
// }

}

