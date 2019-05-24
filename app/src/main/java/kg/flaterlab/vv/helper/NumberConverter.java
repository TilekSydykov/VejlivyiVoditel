package kg.flaterlab.vv.helper;

import java.util.ArrayList;

import kg.flaterlab.vv.data.model.Number;

public class NumberConverter {

    static public boolean isSearch(String n){
        if(!n.isEmpty()){
            return true;
        }
        return false;
    }

    static public boolean isNumber(String s){
        s = formatSearchString(s);
        if(s.length() < 4){
            return false;
        }

        ArrayList<String> n = separateNumsAndLetters(s);

        if(n.size() < 2){
            return false;
        }

        if(n.get(0).length() < 1 || n.get(1).length() < 2){
            return false;
        }

        String formattedNum = formatNumber(s);

        return true;
    }

    static public String formatSearchString(String s){
        String result = "";
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(isInt(c) || isLetter(c)){
                result += c;
            }
        }
        return result;
    }

    static public String formatNumber(String num){
        ArrayList<String> arr = separateNumsAndLetters(num);

        String result = "";
        for (String s: arr){
            String lr = "";
            if(isInts(s)){
                if(s.length() == 5 || s.length() == 6){
                    lr = s.substring(0, 2) + " " + s.substring(2);
                }else {
                    lr = s;
                }
            }else {
                lr = s;
            }
            result += " " + lr;
        }
        return result.trim();
    }

    static ArrayList<String> separateNumsAndLetters(String s){
        ArrayList<String> result = new ArrayList<>();
        String val = "";
        if(s.isEmpty()){
            return result;
        }
        boolean isNum = isInt(s.charAt(0));

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            c = Character.toUpperCase(c);
            if(!isLetter(c)&& !isInt(c)){
                continue;
            }
            if(isNum){
                if(isLetter(c)){
                    isNum = false;
                    val = val + " ";
                }
                val += c;
            }else {
                if(isInt(c)){
                    isNum = true;
                    val = val + " ";
                }

                val += c;
            }
        }
        for (String n : val.split(" ")){
            result.add(n.trim());
        }
        return result;
    }

    static boolean isInts(String s){
        return isInt(s.charAt(0));
    }

    static boolean isInt(char c){
        return c > 47 && c < 58;
    }

    static boolean isLetter(char c){
        return c > 64 && c < 91 || c > 96 && c < 123;
    }

}
