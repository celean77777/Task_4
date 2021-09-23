package ru.gb.lesson4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class xNo {

    private static char[][] map;
    private static final char X_DOT = 'X';
    private static final char O_DOT = 'O';
    private static final char EMPTY_DOT = '•';
    private static final int mapSize = 5;//размерноть массива, м.б. любым
    private static final int checkNum = 4;//Количество фишек подряд, необходимое для победы.2<=checkNum<=mapSize
    private static int a = mapSize;// вспомогательная переменная
    private static int b = mapSize;// вспомогательная переменная
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {

        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            System.out.println();
            if (isWin(X_DOT)) {
                System.out.println("YOU WIN");
                break;
            }
            if (isDraw()) {
                break;
            }
            computerTurn();
            printMap();
            System.out.println();
            if (isWin(O_DOT)) {
                System.out.println("YOU LOSE");
                break;
            }
            if (isDraw()) {
                break;
            }
        }

    }

    // Проверка условия победы при произвольных значениях mapSize и checkNum
    private static boolean isWin(char dot) {

        for (int i = 0; i < mapSize; i++) {
            int countH = 0; // совпадения в строках
            int countL = 0; // совпадения в столбцах
            int countD1 = 0; // совпадения в левой диагонали
            int countD2 = 0; // совпадения в правой диагонали
            for (int j = 0; j < mapSize - 1; j++) {
                if (map[i][j] == dot && map[i][j + 1] == dot) countH += 1;
                if (map[j][i] == dot && map[j + 1][i] == dot) countL += 1;
                if (map[j][j] == dot && map[j + 1][j + 1] == dot) countD1 += 1;
                if (map[j][mapSize - 1 - j] == dot && map[j + 1][mapSize - 2 - j] == dot) countD2 += 1;
            }
            if (countH >= checkNum - 1 || countL >= checkNum - 1 || countD1 >= checkNum - 1 || countD2 >= checkNum - 1)
                return true;
        }
        return false;
    }


    private static void humanTurn() {
        int xCoordinate;
        int yCoordinate;
        System.out.println("Введите координаты в формате \"x пробел y\"");
        do {
            xCoordinate = -1;
            yCoordinate = -1;

            if (SC.hasNextInt()) {
                xCoordinate = SC.nextInt();
            }
            if (SC.hasNextInt()) {
                yCoordinate = SC.nextInt();
            }
            SC.nextLine();
        } while (!isValidHumanTurn(xCoordinate, yCoordinate));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void computerTurn() {
        //Противодействие игроку
        //Обнвружение НОВОГО X игрока/////////////////////////////////////////////////////
        int xCoordinate = 0;
        int yCoordinate = 0;
        Random random = new Random();
        int[] inLineX = {0, 0, 0, 0};
        int[] inLineO = {0, 0, 0, 0};
        int[] targetLine = {0, 0, 0, 0};
        int[] target = {0, 0, 0, 0};
        int max = 0;
        int initLine = 0;
        for (int i = 0; i < mapSize; i++) {
            int flag = 0;
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] == X_DOT && (i != a || j != b)) {
                    a = i;
                    b = j;
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) break;
        }
//////////////////////////////////////////////////////////////////////////////////////////
// Проверка наличия еще Х-ов в целевой окрестности вновь обнаруженного Х
        for (int i = 0; i < mapSize; i++) {
            if (map[a][i] == X_DOT) inLineX[0] += 1;       // есть Х в горизонт строке
            if (map[i][b] == X_DOT) inLineX[1] += 1;       // есть Х в верт строке
            if (map[i][i] == X_DOT) inLineX[2] += 1;                      // есть Х в левой диаг
            if (map[i][mapSize - 1 - i] == X_DOT) inLineX[3] += 1; // есть Х в правой диаг
        }
// Проверка наличия еще O-ов в целевой окрестности вновь обнаруженного Х
        for (int i = 0; i < mapSize; i++) {
            if (map[a][i] == O_DOT) inLineO[0] += 1;       // есть O в горизонт строке
            if (map[i][b] == O_DOT) inLineO[1] += 1;       // есть O в верт строке
            if (map[i][i] == O_DOT) inLineO[2] += 1;                      // есть O в левой диаг
            if (map[i][mapSize - 1 - i] == O_DOT) inLineO[3] += 1; // есть O в правой диаг
        }

        for (int i = 0; i < 4; i++) {
            targetLine[i] = inLineX[i] - inLineO[i];
        }

        for (int i = 0; i < 4; i++) {
            if (targetLine[i] > max) max = targetLine[i];
        }

        for (int i = 0; i < 4; i++) {
            if (targetLine[i] == max) target[i] = 1;
        }

        do {
            initLine = random.nextInt(4);
        } while (target[initLine] == 0);

        do {
            if (initLine == 0) {
                xCoordinate = a;
                yCoordinate = random.nextInt(mapSize);
            }
            if (initLine == 1) {
                xCoordinate = random.nextInt(mapSize);
                yCoordinate = b;
            }
            if (initLine == 2) {
                xCoordinate = random.nextInt(mapSize);
                yCoordinate = xCoordinate;
            }
            if (initLine == 3) {
                xCoordinate = random.nextInt(mapSize);
                yCoordinate = mapSize - 1 - xCoordinate;
            }
        } while (!isValidComputerTurn(xCoordinate, yCoordinate));



  /*      int xCoordinate;
        int yCoordinate;
        Random random = new Random();
        do {
            xCoordinate = random.nextInt(mapSize);
            yCoordinate = random.nextInt(mapSize);
        } while (!isValidComputerTurn(xCoordinate, yCoordinate));*/
    }

    private static boolean isValidHumanTurn(int xCoordinate, int yCoordinate) {
        if (xCoordinate < 1 || yCoordinate < 1 ||
                xCoordinate > mapSize || yCoordinate > mapSize) {
            System.out.println("Вы ввели неправильные координаты. Введите координаты в формате \"x пробел y\"");
            return false;
        }

        if (map[xCoordinate - 1][yCoordinate - 1] == EMPTY_DOT) {
            map[xCoordinate - 1][yCoordinate - 1] = X_DOT;
            return true;
        }
        System.out.println("Вы ввели неправильные координаты. Введите координаты в формате \"x пробел y\"");
        return false;
    }

    private static boolean isValidComputerTurn(int xCoordinate, int yCoordinate) {
        if (map[xCoordinate][yCoordinate] == EMPTY_DOT) {
            map[xCoordinate][yCoordinate] = O_DOT;
            return true;
        }
        return false;
    }

    private static void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void initMap() {
        map = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            Arrays.fill(map[i], EMPTY_DOT);
        }
    }

    private static boolean isDraw() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        System.out.println("DRAW");
        return true;
    }
}
