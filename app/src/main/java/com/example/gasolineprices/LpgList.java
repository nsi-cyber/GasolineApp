package com.example.gasolineprices;
import java.util.ArrayList;

 class Price{
    public String lpg;
    public String marka;
}

 class LpgResult{
    public String lastupdate;
    public ArrayList<Price> result;
}

public class LpgList{
    public boolean success;
    public ArrayList<LpgResult> result;
}

