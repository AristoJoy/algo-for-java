package com.zh.algo.link;

/**
 * 体系班class3
 * 用数组实现栈
 * @param <T>
 */
public class ArrayStack<T> {
    Object[] elems;
    int top;

    public ArrayStack(int limit) {
        elems = new Object[limit];
        top = 0;
    }

    public void push(T value) {
        if (top == elems.length) {
            throw new IndexOutOfBoundsException("stack full!");
        }
        elems[top++] = value;
    }

    public T pop() {
        if (top == 0) {
            throw new IndexOutOfBoundsException("stack empty!");
        }
        return (T) elems[--top];
    }

    public T peek() {
        if (top == 0) {
            throw new IndexOutOfBoundsException("stack empty!");
        }
        return (T) elems[(top - 1)];
    }

    public boolean isEmpty() {
        return top == 0;
    }


}
