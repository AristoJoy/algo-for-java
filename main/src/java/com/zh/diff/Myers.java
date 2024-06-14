package com.zh.diff;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Myers未优化实现
 */
public class Myers<T> {

    public Myers() {
    }

    public void diff(T[] src, T[] dst) {
        List<Operation> script = shortestEdit(src, dst);
        int srcIndex = 0;
        int dstIndex = 0;
        for (int i = 0; i < script.size(); i++) {
            switch (script.get(i)) {
                case INSERT:
                    System.out.println(script.get(i).getColor() + script.get(i).getOp() + " " + dst[dstIndex++]);
                    break;
                case MOVE:
                    System.out.println(script.get(i).getColor() + script.get(i).getOp() + " " + src[srcIndex++]);
                    dstIndex++;
                    break;
                case DELETE:
                    System.out.println(script.get(i).getColor() + script.get(i).getOp() + " " + src[srcIndex++]);
                    break;
            }
        }

    }

    private List<Operation> shortestEdit(T[] text1, T[] text2) {
        int n = text1.length;
        int m = text2.length;
        int max = n + m;


        List<Map<Integer, Integer>> trace = new ArrayList<>();
        Map<Integer, Integer> lastV;
        Map<Integer, Integer> v;
        int x, y;
        v = new TreeMap<>();
        v.put(1, 0);
        loop:
            for (int d = 0; d <= max; d++) {
                trace.add(mapClone(v));
                for (int k = -d; k <= d; k += 2) {
                    if (k == -d || (k != d && v.get(k - 1) < v.get(k + 1))) {
                        x = v.get(k + 1);
                    } else {
                        x = v.get(k - 1) + 1;
                    }

                    y = x - k;
                    while (x < n && y < m && text1[x].equals(text2[y])) {
                        x++;
                        y++;
                    }
                    v.put(k, x);

                    if (x >= n && y >= m) {
                        break loop;
                    }
                }
            }
        List<Operation> script = new ArrayList<>();
        x = n;
        y = m;
        int k;
        int prevK;
        int prevX;
        int prevY;
        for (int d = trace.size() - 1; d > 0; d--) {
            k = x - y;
            lastV = trace.get(d);
            if (k == -d || (k != d && lastV.get(k - 1) < lastV.get(k + 1))) {
                prevK = k + 1;
            } else {
                prevK = k - 1;
            }
            prevX = lastV.get(prevK);
            prevY = prevX - prevK;
            while (x > prevX && y > prevY) {
                script.add(Operation.MOVE);
                x--;
                y--;
            }
            if (x == prevX) {
                script.add(Operation.INSERT);
            } else {
                script.add(Operation.DELETE);
            }
            x = prevX;
            y = prevY;
        }

        if (trace.get(0).get(0) != null && trace.get(0).get(0) != 0) {
            for (int i = 0; i < trace.get(0).get(0); i++) {
                script.add(Operation.MOVE);
            }
        }
        Collections.reverse(script);
        return script;
    }

    private Map<Integer, Integer> mapClone(Map<Integer, Integer> src) {
        Map<Integer, Integer> clone = new TreeMap<>();
        clone.putAll(src);
        return clone;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
//        Character[] src = toWrapper("ABCABBA");
//        Character[] dst = toWrapper("CBABAC");
//        Myers<Character> myers = new Myers<>();
//        myers.diff(src, dst);


        List<String> txt1 = Files.readAllLines(Paths.get("out/production/algo/1.txt"));
        String[] src = txt1.toArray(new String[0]);

        Myers<String> myers = new Myers<>();

        List<String> txt2 = Files.readAllLines(Paths.get("out/production/algo/2.txt"));
        String[] dst = txt2.toArray(new String[0]);
        myers.diff(src, dst);
    }

    private static Character[] toWrapper(String text) {
        char[] chs = text.toCharArray();
        Character[] characters = new Character[chs.length];
        for (int i = 0; i < chs.length; i++) {
            characters[i] = chs[i];
        }
        return characters;
    }
    private File getFIle(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }
}
