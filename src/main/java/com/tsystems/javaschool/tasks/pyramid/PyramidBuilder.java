package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        for (int x = 0; x<inputNumbers.size(); x++){
            if (inputNumbers.get(x) == null){
                throw new CannotBuildPyramidException();
            }
        }
        int size = inputNumbers.size();
        boolean isMatrix = false;
        int row = 1;
        int col = 0;
        while (size>0){
            size = size-row;
            row++;
        }
        if (size==0){
            isMatrix = true;
            row = row-1;
            col = row+(row-1);
        }
        if (isMatrix){
            List<Integer> sortedList = inputNumbers.stream().sorted().collect(Collectors.toList());
            int[][]matrix = new int[row][col];

            for (int[] rows : matrix) {
                Arrays.fill(rows, 0);
            }

            int middle = (col / 2), count = 1;
            int idx = 0;

            for (int i = 0, offset = 0; i < row; i++, offset++, count++) {
                int start = middle - offset;
                for (int j = 0; j < count * 2; j +=2, idx++) {
                    matrix[i][start + j] = sortedList.get(idx);
                }
            }
            return matrix;
        }
        else{
            throw new CannotBuildPyramidException();
        }
    }


}
