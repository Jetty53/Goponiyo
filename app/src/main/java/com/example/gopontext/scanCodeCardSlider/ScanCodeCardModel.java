package com.example.gopontext.scanCodeCardSlider;

public class ScanCodeCardModel {

    private String scanCodeProcedure;
    private int cardPosition;

    public ScanCodeCardModel(String scanCodeProcedure, int cardPosition) {
        this.scanCodeProcedure = scanCodeProcedure;
        this.cardPosition = cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }

    public void setCardPosition(int cardPosition) {
        this.cardPosition = cardPosition;
    }

    public String getScanCodeProcedure() {
        return scanCodeProcedure;
    }

    public void setScanCodeProcedure(String scanCodeProcedure) {
        this.scanCodeProcedure = scanCodeProcedure;
    }
}
