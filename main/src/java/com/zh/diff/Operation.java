package com.zh.diff;

public enum Operation {
    INSERT("\u001B[32m", "+"),
    DELETE("\u001B[31m", "-"),
    MOVE("\u001B[0m", " ");

    private String color;

    private String op;

    Operation(String color, String op) {
        this.color = color;
        this.op = op;
    }

    public String getColor() {
        return color;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toString() {
        return color + op;
    }
}
