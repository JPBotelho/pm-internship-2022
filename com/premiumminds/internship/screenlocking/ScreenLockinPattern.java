package com.premiumminds.internship.screenlocking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.lang.IllegalArgumentException;

/**
 * João Costa 24-05-2022
 */
public class ScreenLockinPattern implements IScreenLockinPattern{

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private static int[][] skip;

    public ScreenLockinPattern() {
        // invalid paths between 2 keys
        // example: skip[1][3] = 2 means that
        // to connect 1 to 3, you need to have previously visited 2.
        // the same applies for connecting 3 to 1, so
        // skip[a][b] = skip[b][a]
        skip = new int[10][10];
        skip[1][3] = skip[3][1] = 2;
        skip[1][7] = skip[7][1] = 4;
        skip[3][9] = skip[9][3] = 6;
        skip[7][9] = skip[9][7] = 8;

        //Connections that pass through the middle (5)
        skip[1][9] = skip[9][1] = 
        skip[2][8] = skip[8][2] = 
        skip[3][7] = skip[7][3] = 
        skip[4][6] = skip[6][4] = 5;
    }
    
    //https://medium.com/@rebeccahezhang/leetcode-351-android-unlock-patterns-d9bae4a8a958
    public Future<Integer> countPatternsFrom(int startNode, int patternLength) {
        if(startNode < 1 || startNode > 9) {
            throw new IllegalArgumentException("Invalid starting node.");
        } else if(patternLength < 1 || patternLength > 9) {
            throw new IllegalArgumentException("Invalid pattern length.");
        }

        if(patternLength == 1) {
            return executor.submit(() -> {
                return 1;
            });
        }

        boolean visited[] = new boolean[10];

        final int result = recursiveCountPatterns(visited, startNode, patternLength - 1);

        return executor.submit(() -> {
            return result;
        });
    }    

    // To find the # of valid patterns with length N:
    // Start at the first node, find the # of valid patterns with length (N - 1)
    // Repeat until length == 1 (end of the pattern)
    // Keep track of previously visited nodes along the way.
    private static int recursiveCountPatterns(boolean[] visited, int startNode, int length) {
        // Base case: out of bounds
        if (length < 0) return 0;
        // Base case: no remaining numbers
        if (length == 0) return 1;
        // Mark number as visited
        visited[startNode] = true;

        int result = 0;
        
        for (int nextNode = 1; nextNode <= 9; nextNode++) {
            // Next key must be unvisited
            if (!visited[nextNode]) {
                // Current key and next key are adjacent OR 
                // the number in-between is already visited
                if((skip[startNode][nextNode] == 0 || 
                    visited[skip[startNode][nextNode]])) {
                    result += recursiveCountPatterns(visited, nextNode, length-1);
                }
            }
        }
        // Mark as unvisited for the rest of recursion calls 
        // after return from the first one  
        visited[startNode] = false;
        return result;
    }
}