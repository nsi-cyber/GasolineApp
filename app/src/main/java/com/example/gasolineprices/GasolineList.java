package com.example.gasolineprices;


import java.util.ArrayList;

class GasolineResult{

    public double benzin;
    public String marka;
}

public class GasolineList{
    public boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<GasolineResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<GasolineResult> result) {
        this.result = result;
    }

    public ArrayList<GasolineResult> result;
}

