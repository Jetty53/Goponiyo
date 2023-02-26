package com.example.gopontext.cipherProcs;

import android.util.Log;

public class HEX {
    private String encodeHEX(String inputText) {

        return asciiHexReturn(inputText);

    }

    private String decodeHEX(String inputText) {

        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

        for (int i = 0; i <= inputText.length()-1; i++){
            if (('0' <= inputText.charAt(i) && inputText.charAt(i) <= '9') || ('A' <= inputText.charAt(i) && inputText.charAt(i) <= 'F') || ('a' <= inputText.charAt(i) && inputText.charAt(i) <= 'f') || inputText.charAt(i) == ' '){
                if (inputText.charAt(i) != ' '){
                    str1.append(inputText.charAt(i));
                }else{
//                    for(int j = 0; j <= str1.toString().length()-1; j++){
//                        if (('G' <= str1.toString().charAt(j) && str1.toString().charAt(j) <= 'Z') || ('g' <= str1.toString().charAt(j) && str1.toString().charAt(j) <= 'z')){
//                            flag = 1;
//                            break;
//                            return "";
//                        }
//                    }
                    int a = Integer.parseInt(str1.toString(),16);

                    if (0 <= a && a <= 0x10FFFFF){
                        Log.d("TAG:",""+a);
//                        str2.delete(0, str2.length());
//                        str1.append("");
//                        break;
//                    }else{
                        char[] string = Character.toChars(a);
                        str2.append(string);
                    }else{
                        return "";
                    }

                    str1.delete(0, i + 1);
                }
            }else{

                return "";
            }

        }

//        if (str2.length() <= 1000){
//            Thread.sleep(100);
//        }else if (str2.length() >= 1000 && str2.length() <= 10000 ){
//            Thread.sleep(5000);
//        }else if (str2.length() >= 10000 && str2.length() <= 100000 ){
//            Thread.sleep(20000);
//        }else if (str2.length() >= 100000 && str2.length() <= 1000000 ){
//            Thread.sleep(40000);
//        }

        return str2.toString();
    }

    private String asciiHexReturn(String inpString) {

        int codePoint = 0;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < inpString.length(); i++){

            codePoint = Character.codePointAt(inpString, i);
            i += Character.charCount(codePoint) - 1;
            String octString = Integer.toHexString(codePoint);
            str.append(octString);
            str.append(" ");

        }

//        if (str.length() <= 1000){
//            Thread.sleep(100);
//        }else if (str.length() >= 1000 && str.length() <= 10000 ){
//            Thread.sleep(5000);
//        }else if (str.length() >= 10000 && str.length() <= 100000 ){
//            Thread.sleep(20000);
//        }else if (str.length() >= 100000 && str.length() <= 1000000 ){
//            Thread.sleep(40000);
//        }



        return str.toString();

    }

    public String encodeProcedureHEX(String inputText) {
        return encodeHEX(inputText);
    }

    public String decodeProcedureHEX(String encode) {
        return decodeHEX(encode);
    }
}
