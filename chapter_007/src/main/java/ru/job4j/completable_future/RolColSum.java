package ru.job4j.completable_future;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Example works 'Completable Future'
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 20.10.2020
 */
public class RolColSum {
    public static class Sums {
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        private int rowSum;
        private int colSum;
        // Getter and Setter

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int y = 0; y < matrix.length; y++) {
            sums[y] = new Sums();
            for (int x = 0; x < matrix.length; x++) {
                int sumRow = matrix[y][x] + sums[y].getRowSum();
                int sumCol = matrix[x][y] + sums[y].getColSum();
                sums[y].setRowSum(sumRow);
                sums[y].setColSum(sumCol);
            }
        }
        return sums;
    }


    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        int len = matrix.length;
        Sums[] sums = new Sums[len];
        for (int j = 0; j <= len / 2; j++) {
            map.put(j, tasksum(matrix, j));
            if (j < len - 1 - j) {
                map.put(len - 1 - j, tasksum(matrix, len - 1 - j));
            }

        }
        for (Integer key : map.keySet()) {
            sums[key] = map.get(key).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> tasksum(int[][] matrix, int j) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                int sumRow = matrix[j][i] + sums.getRowSum();
                int sumCol = matrix[i][j] + sums.getColSum();
                sums.setRowSum(sumRow);
                sums.setColSum(sumCol);
            }
            return sums;
        });
    }
}
