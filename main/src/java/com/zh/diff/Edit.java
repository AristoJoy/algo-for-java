package com.zh.diff;

public class Edit<T> {
    private Operation op;
    private T a;
    private T b;

    public Edit(Operation op, T a, T b) {
        this.op = op;
        this.a = a;
        this.b = b;
    }

    public Operation getOp() {
        return op;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }
}
