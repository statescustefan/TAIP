public class Tweet {
    String text;

    int emoji;

    float prediction[];

    Tweet(){
        text = null;
        emoji = -1;
        prediction = new float[20];
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

    public float[] getPrediction() {
        return prediction;
    }

    public void setPrediction(float[] prediction) {
        this.prediction = prediction;
    }

    public int returnIndexofMax(){
        int index = 0;
        float value = 0;
        for(int i = 0; i < prediction.length; ++i){
            if(prediction[i] > value){
                value = prediction[i];
                index = i;
            }
        }

        return index;
    }

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
