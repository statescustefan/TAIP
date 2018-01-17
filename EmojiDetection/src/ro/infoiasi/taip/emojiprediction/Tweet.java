package ro.infoiasi.taip.emojiprediction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class Tweet {
    String text;

    int emoji;

   // float prediction[];
    //double prediction[];
    ArrayList<Double> prediction;

    Tweet(){
        text = null;
        emoji = -1;
        //prediction = new float[20];
        //prediction=new double[20];
        prediction=new ArrayList<>();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getEmoji() {
        return emoji;
    }

    public void setEmoji(int emoji) {
        this.emoji = emoji;
    }

//    public float[] getPrediction() {
//        return prediction;
//    }
//    public double[] getPrediction() {
//    return prediction;
//}

    public ArrayList<Double> getPrediction() {
        return prediction;
}


//    public void setPrediction(float[] prediction) {
//        this.prediction = prediction;
//    }
//    public void setPrediction(double[] prediction)
//    {
//        this.prediction=prediction;
//    }
    public void setPrediction(ArrayList<Double> prediction) {
        this.prediction=prediction;
    }



    public int returnIndexofMax() {
        int index = 0;
        double value = 0;
        Iterator i = prediction.iterator();
        int j = 0;
        while (j < prediction.size()) {
            rvm.HasNextRuntimeMonitor.nextEvent(i);
            double n = (Double) i.next();
            if (n > value) {
                value = n;
                index = j;
            }
            j++;

        }
        return index;
    }

//    public int returnIndexofMax(){
//        int index = 0;
//        double value = 0;
//        for(int i = 0; i < prediction.size(); ++i){
//            if(prediction.get(i) > value){
//                value = prediction.get(i);
//                index = i;
//            }
//        }
//
//        return index;
//    }


//
////        for(int i = 0; i < prediction.size(); ++i){
////            if(prediction.get(i) > value){
////                value = prediction.get(i);
////                index = i;
////            }
////        }
////
////    }
//    public int returnIndexofMax(){
//        int index = 0;
//        double value = 0;
//        for(int i = 0; i < prediction.size(); ++i) {
//            if (prediction.get(i) > value) {
//                value = prediction.get(i);
//                index = i;
//            }
//        }
//            return index;
//    }
//public int returnIndexofMax(){
//    int index = 0;
//    double value = 0;
//    for(int i = 0; i < prediction.length; ++i){
//        if(prediction[i] > value){
//            value = prediction[i];
//            index = i;
//        }
//    }
//
//    return index;
//}

    public int countDigits(){
        int counter = 0;
        for (int i = 0; i < this.text.length(); i++) {
            if (Character.isDigit(this.text.charAt(i))) {
                counter++;
            }
        }
        return counter;
    }

    public int countCapitals(){
        int counter = 0;
        for (int i = 0; i < this.text.length(); i++) {
            if (Character.isUpperCase(this.text.charAt(i))) {
                counter++;
            }
        }
        return counter;
    }
}
