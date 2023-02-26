package com.example.gopontext.cipherProcs;

import com.example.gopontext.CodedResultActivity;


public class BIN {
    private String encodeBIN(String inputText) {

        return asciiBinReturn(inputText);

    }

    private String decodeBIN(String inputText) {

//        int index = 0;

        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

//        for (int i = 0; i <= inputText.length() - 1; i++) {
//            if (inputText.charAt(i) == 1) {
//                index = i;
//                break;
//            }
//        }

        for (int i = 0; i <= inputText.length() - 1; i++) {

            if ('0' == inputText.charAt(i) || inputText.charAt(i) == '1' || inputText.charAt(i) == ' ') {

                if (inputText.charAt(i) != ' ') {
                    str1.append(inputText.charAt(i));
                } else {
//                    for(int j = 0; j <= str1.toString().length()-1; j++){
//                        if (('A' <= str1.toString().charAt(j) && str1.toString().charAt(j) <= 'Z') || ('a' <= str1.toString().charAt(j) && str1.toString().charAt(j) <= 'z')){
//                            flag = 1;
//                            break;
//                            return "";
//                        }
//                    }
//                    if (str1.length() == 21) {
//                        if (str1.charAt(1) == '1') {
//                            return "";
//                        } else if (str1.charAt(2) == '1') {
//                            return "";
//                        } else if (str1.charAt(3) == '1') {
//                            return "";
//                        } else if (str1.charAt(4) == '1') {
//                            return "";
//                        }
//                    } else if (str1.length() > 21) {
//                        return "";
//                    }

//                    if (0 <= a && a <= 100001111111111111111){
//                        str2.delete(0, str2.length());
//                        str2.append("");
//                        break;
//                    }else{
                    int a = Integer.parseInt(str1.toString(), 2);

                    if (0 <= a && a <= 0B100001111111111111111) {
                        char[] string = Character.toChars(a);
                        str2.append(string);
                    } else {
                        return "";
                    }

//                    }
//                    else{
//                        return "";
//                    }
                    str1.delete(0, i + 1);
                }
            } else {
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

    private String asciiBinReturn(String inpString) {

        int codePoint = 0;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < inpString.length(); i++) {

            codePoint = Character.codePointAt(inpString, i);
            i += Character.charCount(codePoint) - 1;
            String binString = Integer.toBinaryString(codePoint);
            str.append(binString);
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
//        byte[] input = inpString.getBytes(StandardCharsets.UTF_8);
//        //System.out.println(input.length);                       // 3, 1 Chinese character = 3 bytes
//        String binary = convertByteArraysToBinary(input);
//        //System.out.println(binary);
//        //System.out.println(prettyBinary(binary, 8, " "));
//        return binary;

    }

//    private String convertByteArraysToBinary(byte[] input){
//        StringBuilder result = new StringBuilder();
//        for (byte b : input) {
//            int val = b;
//            for (int i = 0; i < 8; i++) {
//                result.append((val & 128) == 0 ? 0 : 1);      // 128 = 1000 0000
//                val <<= 1;
//            }
//        }
//        return result.toString()+" ";
//    }

    public String encodeProcedureBIN(String inputText) {
        return encodeBIN(inputText);
    }

    public String decodeProcedureBIN(String encode) {
        return decodeBIN(encode);
    }
}
