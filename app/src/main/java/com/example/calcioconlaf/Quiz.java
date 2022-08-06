package com.example.calcioconlaf;

public class Quiz {
    private String urlImage;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String country;
    private String city;

    public Quiz(){}

    public Quiz(String urlImage, String option1, String option2, String option3, String option4, String answer, String country, String city) {
        this.urlImage = urlImage;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.city=city;
        this.country=country;
    }

    public String getCountry(){
        return country;
    }

    public String getCity(){
        return city;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
