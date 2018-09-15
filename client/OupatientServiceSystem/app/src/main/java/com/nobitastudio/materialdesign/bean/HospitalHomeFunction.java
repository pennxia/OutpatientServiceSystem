package com.nobitastudio.materialdesign.bean;

public class HospitalHomeFunction {

    private String functionName;

    private int functionImagePath;

    public HospitalHomeFunction(String functionName, int functionImagePath) {
        this.functionName = functionName;
        this.functionImagePath = functionImagePath;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getFunctionImagePath() {
        return functionImagePath;
    }

    public void setFunctionImagePath(int functionImagePath) {
        this.functionImagePath = functionImagePath;
    }
}
