package com.zh.diff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MyersLinear<T> {

    private final T[] src;
    private final T[] dst;

    private int[] vFw;

    private int[] vBw;

    public MyersLinear(T[] src, T[] dst) {
        this.src = src;
        this.dst = dst;
    }

    public List<Edit<T>> diff() {
        List<Edit<T>> edits = new ArrayList<>();
        walkSnakes(edits);
        return edits;
    }

    public void showDiff() {
        List<Edit<T>> diff = diff();
        for (int i = 0; i < diff.size(); i++) {
            Edit<T> edit = diff.get(i);
            Operation op = edit.getOp();
            switch (op) {
                case DELETE : System.out.println(op.getColor() + op.getOp() + " " + edit.getA());break;
                case INSERT : System.out.println(op.getColor() + op.getOp() + " " + edit.getB());break;
                case MOVE : System.out.println(op.getColor() + op.getOp() + " " + edit.getA());break;
            }
        }
    }

    public void walkSnakes(List<Edit<T>> edits) {
        List<Point> paths = findPath(0, 0, src.length, dst.length);
        if (paths == null || paths.size() < 2) {
            return;
        }
        int i = 0;
        for (int j = 1; j < paths.size(); j++, i++) {
            Point p1 = paths.get(i);
            Point p2 = paths.get(j);
            Point p3 = walkDiagonal(edits, p1.x, p1.y, p2.x, p2.y);
            int x1 = p3.x;
            int y1 = p3.y;
            int x2 = p2.x;
            int y2 = p2.y;
            int e1 = x2 - x1;
            int e2 = y2 - y1;
            if (e1 < e2) {
                edits.add(buildEdit(x1, y1, x1, y1 + 1));
                y1++;
            } else if (e1 > e2) {
                edits.add(buildEdit(x1, y1, x1 + 1, y1));
                x1++;
            }
            walkDiagonal(edits, x1, y1, x2, y2);
        }
    }

    public Point walkDiagonal(List<Edit<T>> edits, int x1, int y1, int x2, int y2) {
        while (x1 < x2 && y1 < y2 && src[x1].equals(dst[y1])) {
            edits.add(buildEdit(x1, y1, x2, y2));
            x1++;
            y1++;
        }
        return new Point(x1, y1);
    }

    public Edit<T> buildEdit(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return new Edit<>(Operation.INSERT, null, dst[y1]);
        } else if (y1 == y2) {
            return new Edit<>(Operation.DELETE, src[x1], null);
        } else {
            return new Edit<>(Operation.MOVE, src[x1], dst[y1]);
        }
    }

    private List<Point> findPath(int left, int top, int right, int bottom) {
        Box box = new Box(left, top, right, bottom);
        Snake snake = midPoint(box);
        if (snake == null) {
            return null;
        }
        List<Point> head = findPath(box.left, box.top, snake.left, snake.top);
        List<Point> tail = findPath(snake.right, snake.bottom, box.right, box.bottom);
        List<Point> total = new ArrayList<>();
        if (head != null) {
            total.addAll(head);
        } else {
            total.add(new Point(snake.left, snake.top));
        }
        if (tail != null) {
            total.addAll(tail);
        } else {
            total.add(new Point(snake.right, snake.bottom));
        }
        return total;
    }

    private Snake midPoint(Box box) {
        if (box.size() == 0) {
            return null;
        }
        int offset = (box.size() + 1) / 2;
        int delta = box.delta();
        vFw = new int[2 * offset + 1];
        vBw = new int[2 * offset + 1];
        vFw[1 + offset] = box.left;
        vBw[1 + offset] = box.bottom;
        for (int d = 0; d <= offset; d++) {
            // forward
            for (int k = d; k >= -d; k -= 2) {
                final int i = k + offset;
                int c = k - delta;
                int x;
                int y;
                int px;
                int py;
                if (k == -d || (k != d && vFw[i - 1] < vFw[i + 1])) {
                    px = x = vFw[i + 1];
                } else {
                    px = vFw[i - 1];
                    x = px + 1;
                }
                y = box.top + (x - box.left) - k;
                py = (d == 0 || x != px) ? y : y - 1;
                while (x < box.right && y < box.bottom && src[x].equals(dst[y])) {
                    x++;
                    y++;
                }
                vFw[i] = x;
                if (Math.abs(delta) % 2 == 1 && -(d - 1) <= c && c <= (d - 1) && y >= vBw[c + offset]) {
                    return new Snake(px, py, x, y);
                }
            }
            // backward
            for (int c = d; c >= -d; c -= 2) {
                int k = c + delta;
                final int i = c + offset;
                int x;
                int y;
                int px;
                int py;
                if (c == -d || (c != d && vBw[i - 1] > vBw[i + 1])) {
                    py = y = vBw[i + 1];
                } else {
                    py = vBw[i - 1];
                    y = py - 1;
                }
                x = box.left + (y - box.top) + k;
                px = (d == 0 || y != py) ? x : x + 1;
                while (x > box.left && y > box.top && src[x - 1].equals(dst[y - 1])) {
                    x--;
                    y--;
                }
                vBw[i] = y;
                if (Math.abs(delta) % 2 == 0 && -d <= k && k <= d && x <= vFw[k + offset]) {
                    return new Snake(x, y, px, py);
                }
            }
        }
        return null;
    }

    private int[] buildArea(int left, int top, int right, int bottom) {
        return new int[]{left, top, right, bottom};
    }

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }

    static class Box {
        private int left;
        private int top;
        private int right;
        private int bottom;

        public Box(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int width() {
            return right - left;
        }

        public int height() {
            return bottom - top;
        }

        public int size() {
            return width() + height();
        }

        public int delta() {
            return width() - height();
        }

        @Override
        public String toString() {
            return "Box{" +
                    "left=" + left +
                    ", top=" + top +
                    ", right=" + right +
                    ", bottom=" + bottom +
                    '}';
        }
    }

    static class Snake {
        private int left;
        private int top;
        private int right;
        private int bottom;

        public Snake(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int width() {
            return right - left;
        }

        public int height() {
            return bottom - top;
        }

        public int size() {
            return width() + height();
        }

        public int delta() {
            return width() - height();
        }

        @Override
        public String toString() {
            return "[["+left + "," + top +"],[" + right + "," + bottom +"]]";
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> txt1 = Files.readAllLines(Paths.get("out/production/algo/1.txt"));
        String[] src = txt1.toArray(new String[0]);
        List<String> txt2 = Files.readAllLines(Paths.get("out/production/algo/2.txt"));
        String[] dst = txt2.toArray(new String[0]);

        MyersLinear<String> myers = new MyersLinear<>(src, dst);
        myers.showDiff();
    }
}
