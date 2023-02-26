package com.example.gopontext.cipherProcs;

import android.util.Log;

public class ASCII {

    private String encodeASCII(String inputText) {

        return asciiReturn(inputText);

    }

    private String decodeASCII(String inputText) {
        return decode(inputText);
    }

    private String asciiReturn(String inpString) {

        int codePoint = 0;
        StringBuilder str = new StringBuilder();

        if (!inpString.equals("")) {
            for (int i = 0; i < inpString.length(); i++) {

                codePoint = Character.codePointAt(inpString, i);
                i += Character.charCount(codePoint) - 1;
                str.append(codePoint);
                str.append(" ");

            }
        } else {
            return "";
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

    private String decode(String inpString) {

        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
//        for (String s : inpString.split("\\s+")){
//            int a = Integer.parseInt(s);
//            System.out.println("int a: "+a);
//            String string = Character.toString(a);
//            System.out.println("String string: "+string);
//            str.append(string);
//        }
        if (inpString.length() > 0) {

            for (int i = 0; i <= inpString.length() - 1; i++) {
                if (('0' <= inpString.charAt(i) && inpString.charAt(i) <= '9') || inpString.charAt(i) == ' ') {
                    if (inpString.charAt(i) != ' ') {
                        str1.append(inpString.charAt(i));
                    } else {
//                        for (int j = 0; j <= str1.toString().length() - 1; j++) {
//                            if (('A' <= str1.toString().charAt(j) && str1.toString().charAt(j) <= 'Z') || ('a' <= str1.toString().charAt(j) && str1.toString().charAt(j) <= 'z')) {
//                                flag = 1;
//                                break;
//                                return "";
//                            }
//                        }

//                        if (flag == 1){
//                            str2.delete(0, str2.length());
//                            str1.append("");
//                            break;

//                        }else{
                        int a = Integer.parseInt(str1.toString());

                        if (0 <= a && a <= 1114111) {

                            char[] string = Character.toChars(a);
                            str2.append(string);

                        } else {
                            return "";
                        }
                        str1.delete(0, i + 1);

//                        }

                    }
                } else {
                    return "";
                }

            }
        } else {

            return "";
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

    public String encodeProcedureASCII(String inputText) {
        return encodeASCII(inputText);
    }

    public String decodeProcedureASCII(String encode) {
        return decodeASCII(encode);
    }

}
