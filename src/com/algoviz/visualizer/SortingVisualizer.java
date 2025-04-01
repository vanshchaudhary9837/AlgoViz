package com.algoviz.visualizer;

import java.util.ArrayList;
import java.util.Collections;

import com.algoviz.visualizer.sorts.*;

public class SortingVisualizer {

    private static Thread sortingThread;

    public static VisualizerFrame frame;
    public static Integer[] toBeSorted;
    public static boolean isSorting = false;
    public static int sortDataCount = 20;
    public static int sleep = 20;
    public static int blockWidth;
    // Stepped depicts whether the values are incremental or random. True is incremental.
    public static boolean stepped = false;

    public static void main(String[] args) {
        frame = new VisualizerFrame();
        resetArray();
        frame.setLocationRelativeTo(null);
    }

    public static void resetArray(){
        // If we are currently in a sorting method, then isSorting should be true
        // We do not want to reinitialize/reset the array mid-sort.
        if (isSorting) return;
        toBeSorted = new Integer[sortDataCount];
        blockWidth = (int) Math.max(Math.floor(500/sortDataCount), 1);
        for(int i = 0; i<toBeSorted.length; i++){
            if (stepped) {
                toBeSorted[i] = i;
            } else {
                toBeSorted[i] = (int) (sortDataCount*Math.random());
            }
        }
        // If we're using incremental values, they are already sorted. This shuffles it.
        if (stepped) {
            ArrayList<Integer> shuffleThis = new ArrayList<>();
            for (int i = 0; i < toBeSorted.length; i++) {
                shuffleThis.add(toBeSorted[i]);
            }
            Collections.shuffle(shuffleThis);
            toBeSorted = shuffleThis.toArray(toBeSorted);
        }
        frame.preDrawArray(toBeSorted);
    }

    public static void startSort(String type){

        if (sortingThread == null || !isSorting){

            resetArray();

            isSorting = true;

            switch(type){
                case "Bubble":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.BubbleSort(toBeSorted, frame, false));
                    break;

                case "Selection":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.SelectionSort(toBeSorted, frame, false));
                    break;

                case "Insertion":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.InsertionSort(toBeSorted, frame, false));
                    break;

                case "Merge":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.MergeSort());
                    break;

                case "Bubble(fast)":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.BubbleSort(toBeSorted, frame, true));
                    break;

                case "Selection(fast)":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.SelectionSort(toBeSorted, frame, true));
                    break;

                case "Insertion(fast)":
                    sortingThread = new Thread(new com.algoviz.visualizer.sorts.InsertionSort(toBeSorted, frame, true));
                    break;

                default:
                    isSorting = false;
                    return;
            }

            sortingThread.start();

        }

    }

}
