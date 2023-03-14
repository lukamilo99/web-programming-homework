package raf.rs;

import java.util.*;

public class Validator {

    private List<String> forbiddenWordsList;

    public Validator() {
        this.forbiddenWordsList = List.of("idiot", "fool", "punk");
    }

    public String getFormattedMessage(String username, String content){

        String[] wordList = content.split(" ");

        for(String word: wordList){
            if(forbiddenWordsList.contains(word.toLowerCase())){
                content = content.replace(word, getCensoredWord(word));
            }
        }

        return username + ": " + content + " ["
                + new Date(System.currentTimeMillis()).toString().replace(" ", "-") + "]";
    }

    private String getCensoredWord(String word){
        char[] chars = word.toCharArray();

        for (int i = 1; i < chars.length - 1; i++) {
            chars[i] = '*';
        }

        return new String(chars);
    }
}
