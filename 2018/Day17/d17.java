import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Point;
import java.util.concurrent.TimeUnit;

public class d17 {

    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static char[][] map;
    public static ArrayList<Point> currentUpdate;

    public static void main(String[] args) throws Exception {
        // --- Read input ---
        boolean shouldPrint = true;
        File file = new File("input.txt");
        if(args.length == 1) {
            file = new File(args[0]);
            shouldPrint = true;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));

        Pattern pa = Pattern.compile("([xy])=(\\d+), [xy]=(\\d+)..(\\d+)");
        Matcher m;

        String str;
        ArrayList<Tuple> input = new ArrayList<Tuple>();

        int[] extValues = new int[4];     // 1: x min, 2: x max, 3: y min, 4: y max
        extValues[0] = Integer.MAX_VALUE;
        extValues[2] = Integer.MAX_VALUE;

        // Read input and find min/max x/y
        while((str = br.readLine()) != null) {
             m = pa.matcher(str);
             if(m.find()) {
                 int[] values = new int[3];

                 for(int i = 2; i < 5; i++) {
                     int coord = Integer.parseInt(m.group(i));
                     int offSet = m.group(1).equals("x") ? (i == 2 ? 0 : 2) : (i == 2 ? 2 : 0);
                     if((coord-1) < extValues[offSet]) {
                         extValues[offSet] = coord-1;
                     }
                     if((coord+1) > extValues[offSet+1]) {
                         extValues[offSet+1] = coord+1;
                     }
                     values[i-2] = coord;
                 }
                 input.add(new Tuple(m.group(1), values));
             }
        }

        map = new char[extValues[1] - extValues[0]+1][extValues[3]-extValues[2]];
        for(int y = 0; y < map[0].length; y++) {
            for(int x = 0; x < map.length; x++) {
                map[x][y] = '.';
            }
        }

        // Update coordinates with the min and max values and insert into array
        for(Tuple t : input) {
            // Update offset
            for(int i = 0; i < 3; i++) {
                int num = t.list[i];
                int offSet = t.instr.equals("x") ? (i == 0 ? 0 : 2) : (i == 0 ? 2 : 0);

                t.list[i] -= extValues[offSet];
            }

            // Insert into array
            boolean bool = t.instr.equals("x");
            for(int o = 0; o <= t.list[2] - t.list[1]; o++) {
                map[bool ? t.list[0] : t.list[1]+o][bool ? t.list[1]+o : t.list[0]] = '#';
            }

        }

        //Printing
        if(shouldPrint) printMap();

        currentUpdate = new ArrayList<Point>();
        currentUpdate.add(new Point(500 - extValues[0],0));

        // Water simulation
        while(currentUpdate.size() > 0) {
            ArrayList<Point> nextUpdate = new ArrayList<Point>();

            for(Point p : currentUpdate) {
                if(p.y+1 >= map[0].length) {

                } else if(map[p.x][p.y+1] == '.') {
                    map[p.x][p.y+1] = '|';
                    p.y++;
                    nextUpdate.add(p);
                } else if(map[p.x][p.y+1] == '~' || map[p.x][p.y+1] == '#'){
                    boolean leftWall = false;
                    boolean rightWall = false;

                    int offset = 0;
                    while(map[p.x - offset][p.y] != '#' && map[p.x - offset][p.y + 1] != '.') offset++; // Find left wall
                    int left = p.x - offset;
                    if(map[p.x - offset][p.y] == '#') leftWall = true;

                    offset = 0;
                    while(map[p.x + offset][p.y] != '#' && map[p.x + offset][p.y + 1] != '.') offset++; // Find right wall
                    int right = p.x + offset;
                    if(map[p.x + offset][p.y] == '#') rightWall = true;

                    if(leftWall && rightWall) {
                        for(int x = left+1; x < right; x++) {
                            map[x][p.y] = '~';
                        }
                        p.y--;
                        nextUpdate.add(p);
                    } else {
                        for(int x = left+1; x < right; x++) {
                            map[x][p.y] = '|';
                        }

                        if(!leftWall) {
                            map[left][p.y] = '|';
                            nextUpdate.add(new Point(left,p.y));
                        }
                        if(!rightWall) {
                            map[right][p.y] = '|';
                            nextUpdate.add(new Point(right,p.y));
                        }

                    }

                } else if(map[p.x][p.y+1] == '|') {
                    //updatePoints.remove(p);
                } else {
                    throw (new Exception("Something went wrong " + map[p.x][p.y+1]));
                }

            }
            currentUpdate = nextUpdate;

            //Printing
            if(shouldPrint) printMap();


        }

        int sumWater  = 0;
        int sumStillWater = 0;
        for(int y = 0; y < map[0].length; y++) {
            for(int x = 0; x < map.length; x++) {
                if(map[x][y] == '~' || map[x][y] == '|') sumWater++;
                if(map[x][y] == '~')sumStillWater++;
            }
        }
        System.out.println("Number of watertiles " + sumWater);
        System.out.println("Number of still watertiles: " + sumStillWater);
    }

    public static void printMap() throws Exception {
        TimeUnit.MILLISECONDS .sleep(50);
        //System.out.print("\033[H\033[2J"); // clear screen

        if(map.length < 50) {
            for(int y = 0; y < map[0].length; y++) {
                for(int x = 0; x < map.length; x++) {
                    if(map[x][y] == '.') System.out.print( ANSI_YELLOW_BACKGROUND + ' ' + ANSI_RESET);
                    if(map[x][y] == '#') System.out.print( ANSI_YELLOW_BACKGROUND + '#' + ANSI_RESET);
                    if(map[x][y] == '~') System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLUE + '~' + ANSI_RESET);
                    if(map[x][y] == '|') System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLUE + '|' + ANSI_RESET);

                }
                System.out.println();
            }
        } else if(currentUpdate != null && !currentUpdate.isEmpty()){
            Point p = currentUpdate.get(0);
            int height = 30;
            int width = 100;
            String tot = "";

            int yMin = p.y-height < 0 ? 0 : p.y-height;
            int yMax = p.y+height > map[0].length ? map[0].length : p.y+height;
            int xMin = p.x-width < 0 ? 0 : p.x-width;
            int xMax = p.x+width > map.length ? map.length : p.x + width;
            

            for(int y = yMin; y < yMax; y++) {
                for(int x = xMin; x < xMax; x++) {
                    /*
                    if(map[x][y] == '.') System.out.print( ANSI_YELLOW_BACKGROUND + ' ' + ANSI_RESET);
                    if(map[x][y] == '#') System.out.print( ANSI_YELLOW_BACKGROUND + '#' + ANSI_RESET);
                    if(map[x][y] == '~') System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLUE + '~' + ANSI_RESET);
                    if(map[x][y] == '|') System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLUE + '|' + ANSI_RESET);
                    */
                    if(map[x][y] == '.') tot += ( ANSI_YELLOW_BACKGROUND + ' ' + ANSI_RESET);
                    if(map[x][y] == '#') tot += ( ANSI_YELLOW_BACKGROUND + '#' + ANSI_RESET);
                    if(map[x][y] == '~') tot += (ANSI_YELLOW_BACKGROUND + ANSI_BLUE + '~' + ANSI_RESET);
                    if(map[x][y] == '|') tot += (ANSI_YELLOW_BACKGROUND + ANSI_BLUE + '|' + ANSI_RESET);

                }
                tot += "\n";
            }
            System.out.print("\033[H\033[2J"); // clear screen
            System.out.println(tot);

        }

    }
}
