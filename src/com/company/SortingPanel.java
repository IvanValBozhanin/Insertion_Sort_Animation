package com.company;

import javax.swing.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.*;
import java.util.Collections;

public class SortingPanel extends JPanel {
    private final ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(5,3,2,4,1));

    private boolean isAvailable = false;
    private static final int noSortingStage = -1;
    private static final int incrementJStage = 1;
    private static final int compareStage = 2; // show j and j+1
    private static final int switchStage = 3;
    private static final int isSortedStage = 4;

    private int stage = noSortingStage;

    int currentI = 1;
    int currentJ = 0;

    public SortingPanel() {

    }

    public void nextStepInSorting() {
        if(isAvailable){
            stage = switchStage;
        }
        if (stage == isSortedStage) {
            return;
        }
        else if (stage == noSortingStage) {
            stage = incrementJStage; // Initializing stage, continue directly to next stage
        }
        else if (stage == incrementJStage) {
            currentJ = currentI;
            stage = compareStage;
        }
        else if (stage == compareStage) {
            if (currentJ > 0 && numbers.get(currentJ-1) > numbers.get(currentJ)) {
                stage = switchStage;
                Collections.swap(numbers, currentJ - 1, currentJ);
            }
            else{
                stage = incrementJStage;
                ++currentI;
                if(currentI == numbers.size()){
                    stage = isSortedStage;
                }
            }
        }
        else if(stage == switchStage){
            stage = compareStage;
            isAvailable = true;
        }
        repaint();
        if(isAvailable){
            isAvailable = false;
            --currentJ;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int towerWidth = ((getWidth() - 20) / numbers.size()) - 2;
        int towerHeightIndex = (getHeight() - 20) / maxElement(numbers);
        int downY = getHeight() - 5;
        for (int j = 0; j < numbers.size(); j++) {

            if (stage == noSortingStage || stage == isSortedStage) {
                g.setColor(Color.BLACK);
            } else if (j == currentJ - 1 && (stage == compareStage || stage == switchStage)) {
                g.setColor(Color.RED);
            } else if (j == currentJ) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }

            int leftX = j * towerWidth + 5 * (j + 1);
            int rightX = leftX + towerWidth;
            int upperY = downY - numbers.get(j) * towerHeightIndex;
            g.drawLine(leftX, downY, leftX, upperY); // left side line
            g.drawLine(leftX, upperY, rightX, upperY); // top line
            g.drawLine(rightX, downY, rightX, upperY); // right line

            g.drawString(numbers.get(j).toString(), leftX + towerWidth / 2, downY - 5);
        }

    }

    public static Integer maxElement(ArrayList<Integer> numbers) {
        Integer max = numbers.get(0);
        for (Integer a : numbers) {
            if (a > max) {
                max = a;
            }
        }

        return max;
    }
}
