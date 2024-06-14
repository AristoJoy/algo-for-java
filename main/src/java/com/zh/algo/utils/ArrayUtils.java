package com.zh.algo.utils;

import java.util.Arrays;

public class ArrayUtils {

    public static void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        if (maxSize <= 0) {
            return new int[0];
        }
        int[] array = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) ((maxValue + 1) * Math.random()) + (int) (maxValue * Math.random());
        }
        return array;
    }

    public static void comparator(int[] array) {
        Arrays.sort(array);
    }

    public static int[] copyArray(int[] array1) {
        if (array1 == null) {
            return null;
        }
        if (array1.length == 0) {
            return new int[0];
        }
        int[] copy = new int[array1.length];
        for (int i = 0; i < array1.length; i++) {
            copy[i] = array1[i];
        }
        return copy;
    }

    public static boolean isEqual(int[] array1, int[] array2) {
        if ((array1 == null && array2 != null) || (array1 != null && array2 == null)) {
            return false;
        }
        if (array1 == null) {
            return true;
        }
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println("i = " + i + " , " + array[i]);
        }
        System.out.println();
    }

    public static void printArray(Integer[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println("i = " + i + " , " + array[i]);
        }
        System.out.println();
    }
}
