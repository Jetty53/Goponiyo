package com.example.gopontext.cipherProcs;

public class BASE64 {
    private String encodeBASE64(String inputText){

        StringBuilder str0 = new StringBuilder();
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        StringBuilder str3 = new StringBuilder();

        String completeAscii = asciiReturn(inputText);

        for (int i = 0; i < completeAscii.length(); i++){
            if (completeAscii.charAt(i) != ' '){
                str0.append(completeAscii.charAt(i));
            }else{
                int ascii = Integer.parseInt(str0.toString());
                str0.delete(0,str0.toString().length());

                if (ascii <= 127){
                    int len = Integer.toBinaryString(ascii).length();
                    for (int j = 0; j < (8 - len); j++){
                        str1.append("0");
                    }
//                    str1.append("0".repeat(Math.max(0, (8 - len))));
                    str1.append(Integer.toBinaryString(ascii));
                }else{
                    String extAsciiBinary = extAsciiBinary(ascii);
                    str1.append(extAsciiBinary);
                }
            }
        }

        int endBit = 0;

        if (str1.toString().length() % 6 != 0){
            str1.append(0);
            str1.append(0);
            endBit = 1;
            if (str1.toString().length() % 6 != 0) {
                str1.append(0);
                str1.append(0);
                endBit = 2;
            }
        }

        for (int i = 0; i < str1.toString().length(); i++){
            str2.append(str1.toString().charAt(i));
            if ((i+1) % 6 == 0){
                int sixBitsDec =  Integer.parseInt(str2.toString(), 2);
                str3.append(base64IntToCharTable(sixBitsDec));
                str2.delete(0, i+1);
            }
        }

        if (endBit == 1){
            str3.append('=');
        }else if (endBit == 2){
            str3.append('=');
            str3.append('=');
        }

        return str3.toString();

    }

    private String decodeBASE64(String encode){
        int count = 0;
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        StringBuilder str3 = new StringBuilder();
        StringBuilder str4 = new StringBuilder();

        for(int i = 0; i <= encode.length()-1; i++){

            if (('A' <= encode.charAt(i) && encode.charAt(i) <= 'Z')|| ('a' <= encode.charAt(i) && encode.charAt(i) <= 'z') || ('0' <= encode.charAt(i) && encode.charAt(i) <= '9') || encode.charAt(i) == '+' || encode.charAt(i) == '/' || (encode.charAt(i) == '=' && i > 0)){
//                Log.d("TAG: ","I am here 1");
                if (encode.charAt(i) == '='){
                    count++;
                }else{
                    String sixBitBin = Integer.toBinaryString(base64CharToIntTable(encode.charAt(i)));
                    if (sixBitBin.length() < 6){
                        for (int j = 0; j < (6 - sixBitBin.length()); j++){
                            str1.append("0");
                        }
                        //str1.append("0".repeat(Math.max(0, (6 - sixBitBin.length()))));
                        str1.append(sixBitBin);
                    }else if (sixBitBin.length() == 6){
                        str1.append(sixBitBin);
                    }
                }
            }else{
//                Log.d("TAG: ","I am here 2");
                return "";
            }
        }

        if (count > 0){
            str1.delete(str1.toString().length()-(count * 2), str1.toString().length());
        }
        count = 0;

        if ((encode.length() % 4) == 0){

            int checkForDecodeBase64 = checkForDecodeBase64(str1.toString());

            if (checkForDecodeBase64 == 1){

                for (int i = 0; i <= str1.toString().length()-1; i++){
                    str2.append(str1.toString().charAt(i));
                    if (str2.toString().length() == 8){
                        if (str2.toString().charAt(0) == '0') {

                            str3.append(Integer.parseInt(str2.toString(),2));
                            str2.delete(0,i+1);
                        }else if (str2.toString().charAt(0) == '1'){

                            for (int j = 0; j <= 3; j++){
                                if (str2.toString().charAt(j) == '1'){
                                    count++;
                                }else if (str2.toString().charAt(j) == '0'){
                                    break;
                                }
                            }

                            int k = 1;
                            while(k <= (8*(count - 1))){
                                i++;
                                str2.append(str1.toString().charAt(i));
                                k++;
                            }
                            String extractBin = extractBin(str2.toString(), count);
                            count = 0;
                            str3.append(Integer.parseInt(extractBin, 2));
                            str2.delete(0, str2.length());
                        }
                        str3.append(" ");
                    }

                }

                for (int i = 0; i <= str3.toString().length()-1; i++){
                    if (str3.toString().charAt(i) != ' '){
                        str2.append(str3.toString().charAt(i));
                    }else{
                        int a = Integer.parseInt(str2.toString());
                        char[] string = Character.toChars(a);
                        str4.append(string);
                        str2.delete(0,i+1);
                    }
                }

            }else if (checkForDecodeBase64 == 0){

                str4.append("");

            }
        }else {

            str4.append("");

        }

        return str4.toString();

    }

    private String asciiReturn(String inpString){

        int codePoint = 0;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < inpString.length(); i++){

            codePoint = Character.codePointAt(inpString, i);
            i += Character.charCount(codePoint) - 1;
            str.append(codePoint);
            str.append(" ");

        }

        return str.toString();

    }

    private String extAsciiBinary(int extAscii){

        int byteNum = 0;
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        String asciiToBin = Integer.toBinaryString(extAscii);

        if (128 <= extAscii && extAscii <= 2047){
            byteNum = 2;
            for (int i = 0; i < (11 - asciiToBin.length()); i++){
                str1.append("0");
            }
            //str1.append("0".repeat(Math.max(0, (11 - asciiToBin.length()))));
            str1.append(asciiToBin);

        }else if(2048 <= extAscii && extAscii <= 65535){
            byteNum = 3;
            for (int i = 0; i < (16 - asciiToBin.length()); i++){
                str1.append("0");
            }
            //str1.append("0".repeat(Math.max(0, (16 - asciiToBin.length()))));
            str1.append(asciiToBin);

        }else if(65536 <= extAscii && extAscii <= 1114111){
            byteNum = 4;
            for (int i = 0; i < (21 - asciiToBin.length()); i++){
                str1.append("0");
            }
            //str1.append("0".repeat(Math.max(0, (21 - asciiToBin.length()))));
            str1.append(asciiToBin);

        }

        int byteIndex = 0;
        int byteFrontFlag = 0;

        for (int i = 0; i <= str1.toString().length()-1; i++){

            if (byteFrontFlag == 0){

                if (byteIndex == 0){

                    for (int j = 0; j < byteNum; j++){
                        str2.append("1");
                    }
                    //str2.append("1".repeat(byteNum));
                    str2.append(0);
                }else if (byteIndex > 0){
                    str2.append(1);
                    str2.append(0);
                }
                byteFrontFlag = 1;

            }

            str2.append(str1.toString().charAt(i));
            int len = str2.toString().length();

            if (len % 8 == 0){
                byteFrontFlag = 0;
                byteIndex++;
            }

        }

        return str2.toString();

    }

    private String extractBin(String primaryBin, int byteNum){

        StringBuilder str = new StringBuilder();
        for (int i = byteNum + 1; i <= 7; i++){
            str.append(primaryBin.charAt(i));
        }
        for (int j = 8; j <= primaryBin.length()-1; j++){
            if (j % 8 == 0){
                j += 2;
            }
            str.append(primaryBin.charAt(j));
        }

        return str.toString();

    }

    private int checkForDecodeBase64(String bit8BinaryString) {

        int resToReturn = -1;
        int numberOfOne = 0;
        int firstBitOneCheckResult;

        for (int i = 0; i <= bit8BinaryString.length() - 8; i += 8) {

            if (bit8BinaryString.charAt(i) == '0'){
                resToReturn = 1;
            }else if (bit8BinaryString.charAt(i) == '1'){

                if (bit8BinaryString.charAt(i+1) == '1'){

                    numberOfOne = 2;

                    if (bit8BinaryString.charAt(i+2) == '1'){
                        numberOfOne = 3;
                        if (bit8BinaryString.charAt(i+3) == '1'){
                            numberOfOne = 4;
                            if (bit8BinaryString.charAt(i+4) == '1'){
                                resToReturn = 0;
                                break;
                            }
                        }
                    }

                    firstBitOneCheckResult = checkForBase64RuleFirstBitOne(bit8BinaryString.substring(i+8, ((i+(8*numberOfOne))-1)));

                    if (firstBitOneCheckResult == 0){

                        resToReturn = 1;
                    }else{
                        resToReturn = 0;

                        break;
                    }

                    i = (i + (8 * numberOfOne)) - 8;

                }else{

                    resToReturn = 0;
                    break;
                }
            }

        }

        return resToReturn;

    }

    private int checkForBase64RuleFirstBitOne(String subString) {

        int returnResIfTrue = 0;

//        for (j = i + 1; j <= i + 4; j++) {
//            if (bit8BinaryString.charAt(j) == '0') {
//                numberOfOne = j - i;
//                break;
//            }
//        }
        for (int i = 0; i <= (subString.length() - 8); i += 8){
            if (subString.charAt(i) == '1' && subString.charAt(i + 1) == '0'){
                returnResIfTrue = 0;
            }else{
                returnResIfTrue = 1;
                break;
            }
        }

        return  returnResIfTrue;

    }

    private char base64IntToCharTable(int num){
        char c = '\0';
        if (num == 0){
            c = 'A';
        }else if (num == 1){
            c = 'B';
        }else if (num == 2){
            c = 'C';
        }else if (num == 3){
            c = 'D';
        }else if (num == 4){
            c = 'E';
        }else if (num == 5){
            c = 'F';
        }else if (num == 6){
            c = 'G';
        }else if (num == 7){
            c = 'H';
        }else if (num == 8){
            c = 'I';
        }else if (num == 9){
            c = 'J';
        }else if (num == 10){
            c = 'K';
        }else if (num == 11){
            c = 'L';
        }else if (num == 12){
            c = 'M';
        }else if (num == 13){
            c = 'N';
        }else if (num == 14){
            c = 'O';
        }else if (num == 15){
            c = 'P';
        }else if (num == 16){
            c = 'Q';
        }else if (num == 17){
            c = 'R';
        }else if (num == 18){
            c = 'S';
        }else if (num == 19){
            c = 'T';
        }else if (num == 20){
            c = 'U';
        }else if (num == 21){
            c = 'V';
        }else if (num == 22){
            c = 'W';
        }else if (num == 23){
            c = 'X';
        }else if (num == 24){
            c = 'Y';
        }else if (num == 25){
            c = 'Z';
        }else if (num == 26){
            c = 'a';
        }else if (num == 27){
            c = 'b';
        }else if (num == 28){
            c = 'c';
        }else if (num == 29){
            c = 'd';
        }else if (num == 30){
            c = 'e';
        }else if (num == 31){
            c = 'f';
        }else if (num == 32){
            c = 'g';
        }else if (num == 33){
            c = 'h';
        }else if (num == 34){
            c = 'i';
        }else if (num == 35){
            c = 'j';
        }else if (num == 36){
            c = 'k';
        }else if (num == 37){
            c = 'l';
        }else if (num == 38){
            c = 'm';
        }else if (num == 39){
            c = 'n';
        }else if (num == 40){
            c = 'o';
        }else if (num == 41){
            c = 'p';
        }else if (num == 42){
            c = 'q';
        }else if (num == 43){
            c = 'r';
        }else if (num == 44){
            c = 's';
        }else if (num == 45){
            c = 't';
        }else if (num == 46){
            c = 'u';
        }else if (num == 47){
            c = 'v';
        }else if (num == 48){
            c = 'w';
        }else if (num == 49){
            c = 'x';
        }else if (num == 50){
            c = 'y';
        }else if (num == 51){
            c = 'z';
        }else if (num == 52){
            c = '0';
        }else if (num == 53){
            c = '1';
        }else if (num == 54){
            c = '2';
        }else if (num == 55){
            c = '3';
        }else if (num == 56){
            c = '4';
        }else if (num == 57){
            c = '5';
        }else if (num == 58){
            c = '6';
        }else if (num == 59){
            c = '7';
        }else if (num == 60){
            c = '8';
        }else if (num == 61){
            c = '9';
        }else if (num == 62){
            c = '+';
        }else if (num == 63){
            c = '/';
        }
        return c;
    }

    private int base64CharToIntTable(char ch){
        int res = -1;
        if (ch == 'A'){
            res = 0;
        }else if (ch == 'B'){
            res = 1;
        }else if (ch == 'C'){
            res = 2;
        }else if (ch == 'D'){
            res = 3;
        }else if (ch == 'E'){
            res = 4;
        }else if (ch == 'F'){
            res = 5;
        }else if (ch == 'G'){
            res = 6;
        }else if (ch == 'H'){
            res = 7;
        }else if (ch == 'I'){
            res = 8;
        }else if (ch == 'J'){
            res = 9;
        }else if (ch == 'K'){
            res = 10;
        }else if (ch == 'L'){
            res = 11;
        }else if (ch == 'M'){
            res = 12;
        }else if (ch == 'N'){
            res = 13;
        }else if (ch == 'O'){
            res = 14;
        }else if (ch == 'P'){
            res = 15;
        }else if (ch == 'Q'){
            res = 16;
        }else if (ch == 'R'){
            res = 17;
        }else if (ch == 'S'){
            res = 18;
        }else if (ch == 'T'){
            res = 19;
        }else if (ch == 'U'){
            res = 20;
        }else if (ch == 'V'){
            res = 21;
        }else if (ch == 'W'){
            res = 22;
        }else if (ch == 'X'){
            res = 23;
        }else if (ch == 'Y'){
            res = 24;
        }else if (ch == 'Z'){
            res = 25;
        }else if (ch == 'a'){
            res = 26;
        }else if (ch == 'b'){
            res = 27;
        }else if (ch == 'c'){
            res = 28;
        }else if (ch == 'd'){
            res = 29;
        }else if (ch == 'e'){
            res = 30;
        }else if (ch == 'f'){
            res = 31;
        }else if (ch == 'g'){
            res = 32;
        }else if (ch == 'h'){
            res = 33;
        }else if (ch == 'i'){
            res = 34;
        }else if (ch == 'j'){
            res = 35;
        }else if (ch == 'k'){
            res = 36;
        }else if (ch == 'l'){
            res = 37;
        }else if (ch == 'm'){
            res = 38;
        }else if (ch == 'n'){
            res = 39;
        }else if (ch == 'o'){
            res = 40;
        }else if (ch == 'p'){
            res = 41;
        }else if (ch == 'q'){
            res = 42;
        }else if (ch == 'r'){
            res = 43;
        }else if (ch == 's'){
            res = 44;
        }else if (ch == 't'){
            res = 45;
        }else if (ch == 'u'){
            res = 46;
        }else if (ch == 'v'){
            res = 47;
        }else if (ch == 'w'){
            res = 48;
        }else if (ch == 'x'){
            res = 49;
        }else if (ch == 'y'){
            res = 50;
        }else if (ch == 'z'){
            res = 51;
        }else if (ch == '0'){
            res = 52;
        }else if (ch == '1'){
            res = 53;
        }else if (ch == '2'){
            res = 54;
        }else if (ch == '3'){
            res = 55;
        }else if (ch == '4'){
            res = 56;
        }else if (ch == '5'){
            res = 57;
        }else if (ch == '6'){
            res = 58;
        }else if (ch == '7'){
            res = 59;
        }else if (ch == '8'){
            res = 60;
        }else if (ch == '9'){
            res = 61;
        }else if (ch == '+'){
            res = 62;
        }else if (ch == '/'){
            res = 63;
        }
        return res;
    }

    public String encodeProcedureBASE64(String inputText){
        return encodeBASE64(inputText);
    }

    public String decodeProcedureBASE64(String inputText) {
        return decodeBASE64(inputText);
    }
}
