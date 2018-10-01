/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autohideseek;

import static autohideseek.MainJPanel.Map;
import static autohideseek.MainJPanel.MapCopy;
import static autohideseek.MainJPanel.MapInfo;
import static autohideseek.MainJPanel.pX;
import static autohideseek.MainJPanel.pY;
import java.awt.Point;

/**
 *
 * @author Jia Jia Chen
 */
public class Finder {

    public static int distance;

    public static int SmartFinder(int lX, int lY, int target) {
        boolean found = false;
        Point foundLocation = new Point(-1, -1);
        //reset the search
        MapInfo = new int[100][100][10];
        MapInfo[lY][lX][0] = 1;
        distance = 0;

        while (found == false) {
            for (int x = 0; x < pX; x++) {
                for (int y = 0; y < pY; y++) {
                    if (MapInfo[y][x][0] > 0) {
                        //check around
                        //scan left and right
                        for (int x2 = -1; x2 <= 1; x2 += 2) {
                            if (x + x2 < pX && x + x2 >= 0 && Map[y][x + x2][0] != 1) {
                                found = SmartFinderCore(x, y, x2, 0, found);
                            }
                        }
                        //scan up and down
                        for (int y2 = -1; y2 <= 1; y2 += 2) {
                            if (y + y2 < pY && y + y2 >= 0 && Map[y + y2][x][0] != 1) {
                                found = SmartFinderCore(x, y, 0, y2, found);
                            }
                        }
                    }

                }
            }
            MapCopy(1, 0, MapInfo);

        }

        return distance;
    }

    private static boolean SmartFinderCore(int x, int y, int x2, int y2, boolean found) {
        distance = MapInfo[y][x][0];
        if (MapInfo[y + y2][x + x2][0] == 0) {
            MapInfo[y + y2][x + x2][1] = distance + 1;
        }
        if (Map[y + y2][x + x2][0] == 10) {
            System.out.println("Target Acquired at " + (x + x2 + 1) + "," + (y + y2 + 1) + ". Distance is " + (MapInfo[y + y2][x + x2][1] - 2));
            found = true;
        }
        return found;

    }
}
