import java.util.*;

public class project1Bot2 {
    private static final char BLOCKED_CELL = 'X';
    private static final char OPEN_CELL = '_';
    private static final char FIRE_CELL = 'F';

    private static final char BUTTON_CELL = 'B';
    private static final char BUTTON_PRESSED = 'P';


    private static final char BOT_LOCATION = '2';
    

    public static char[][] generateShip(int size){
        char[][] grid = new char[size][size];
        Random random = new Random();


        //create ship and block all cells
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                grid[i][j] = BLOCKED_CELL;
            }
        }

        //Open first square at random
        int firstX = random.nextInt(size - 2) + 1;
        int firstY = random.nextInt(size - 2) + 1;
        grid[firstX][firstY] = OPEN_CELL;

        while(true){
        List <int[]> candidates = new ArrayList<>();

        //Find blocked cells with one neighbor
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(grid[i][j] == BLOCKED_CELL){
                int openNeighbors = 0;

                if(i == size - 1){
                    if(j == 0){
                        if(grid[i-1][j] == OPEN_CELL)openNeighbors++;
                        if(grid[i][j+1] == OPEN_CELL)openNeighbors++;
                    }
                    else{
                        if(grid[i-1][j] == OPEN_CELL)openNeighbors++;
                        if(grid[i][j-1] == OPEN_CELL)openNeighbors++;

                    }
                }
                else if(j == size - 1){
                    if(i == 0){
                        if(grid[i][j-1] == OPEN_CELL)openNeighbors++;
                        if(grid[i+1][j] == OPEN_CELL)openNeighbors++;
                    }
                    else{
                        if(grid[i][j-1] == OPEN_CELL)openNeighbors++;
                        if(grid[i-1][j] == OPEN_CELL)openNeighbors++;

                }

                }

                else if(i == 0){
                    if(grid[i+1][j] == OPEN_CELL) openNeighbors++;
                    if(grid[i][j+1] == OPEN_CELL) openNeighbors++;
                    if(j > 0){
                        if(grid[i][j-1] == OPEN_CELL) openNeighbors++;
                    }
                    
                }
                else if(j == 0){
                   if(grid[i][j+1] == OPEN_CELL) openNeighbors++; 
                   if(grid[i+1][j] == OPEN_CELL) openNeighbors++;
                    if(i > 0){
                        if(grid[i-1][j] == OPEN_CELL) openNeighbors++;
                    }
                }
                
                else{

                if(grid[i-1][j] == OPEN_CELL) openNeighbors++;
                if(grid[i+1][j] == OPEN_CELL) openNeighbors++;
                if(grid[i][j-1] == OPEN_CELL) openNeighbors++;
                if(grid[i][j+1] == OPEN_CELL) openNeighbors++;

                }

                if(openNeighbors == 1) candidates.add(new int[]{i, j});


                }
            }
        }

        if (candidates.isEmpty()){
            break;
        }

        //Open random cell
        int[] randomCell = candidates.get(random.nextInt(candidates.size()));
        grid[randomCell[0]][randomCell[1]] = OPEN_CELL;

    }


        //open dead ends
        List<int[]> blockedCellsToOpen = new ArrayList<>();

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(grid[i][j] == BLOCKED_CELL){
                    blockedCellsToOpen.add(new int[]{i, j});
                }
            }
        }
        int sizeOfBlockedCells = blockedCellsToOpen.size()/2;
        while(sizeOfBlockedCells != 0){
            int[] randomCell = blockedCellsToOpen.get(random.nextInt(blockedCellsToOpen.size()));
            grid[randomCell[0]][randomCell[1]] = OPEN_CELL;
            blockedCellsToOpen.remove(randomCell);
            sizeOfBlockedCells--;
        }

        return grid;

    }

    
    public static void addButtonBotFire(char[][] array, int size){
        List<int[]> openCells = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(array[i][j] == OPEN_CELL){
                    openCells.add(new int[]{i, j});
                }
            }
        }
        int x = random.nextInt(openCells.size());
        int [] randomCell = openCells.get(x);
        array[randomCell[0]][randomCell[1]] = BUTTON_CELL;
        openCells.remove(x);

        x = random.nextInt(openCells.size());
        randomCell = openCells.get(x);
        array[randomCell[0]][randomCell[1]] = BOT_LOCATION;
        openCells.remove(x);
        
        x = random.nextInt(openCells.size());
        randomCell = openCells.get(x);
        array[randomCell[0]][randomCell[1]] = FIRE_CELL;
    }


    //finds a specific fire cell
    public static int[] findFire (char[][] array){
        int[] finder = new int[2];
        int i,j;
        for( i = 0; i < array.length; i++){
            for( j = 0; j < array.length; j++){
                if(array[i][j] == FIRE_CELL){
                    finder[0] = i+1;
                    finder[1] = j+1;
                    return finder;
                }
            }

        }
        return finder;
    }
    public static int[] findButton (char[][] array){
        int[] finder = new int[2];
        int i,j;
        for( i = 0; i < array.length; i++){
            for( j = 0; j < array.length; j++){
                if(array[i][j] == BUTTON_CELL){
                    finder[0] = i;
                    finder[1] = j;
                    //System.out.println(finder[0] + " " + finder[1]);
                    return finder;
                }
            }

        }
        return finder;
    }
    public static int[] findBot (char[][] array){
        int[] finder = new int[2];
        int i,j;
        for( i = 0; i < array.length; i++){
            for( j = 0; j < array.length; j++){
                if(array[i][j] == BOT_LOCATION){
                    finder[0] = i;
                    finder[1] = j;
                    //System.out.println(finder[0] + " " + finder[1]);
                    return finder;
                }
            }

        }
        return finder;
    }
    

    public static boolean notBlocked(char[][] grid, int size, int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size && grid[x][y] != BLOCKED_CELL && grid[x][y] != FIRE_CELL;
    }
    

    public static ArrayList<int[]> BFSBOT2(char[][] array, int size, int[] bot, int[] button) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[size][size];
        int[][] parent = new int[size][size];

        int botX = bot[0];
        int botY = bot[1];

        queue.add(bot);
        visited[botX][botY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.remove();
            int x = current[0];
            int y = current[1];

            if (x == button[0] && y == button[1]) {
                ArrayList<int[]> shortestPath = new ArrayList<>();
                while (x != botX || y != botY) {
                    shortestPath.add(new int[]{x, y});
                    int newX = parent[x][y] / 1000;
                    int newY = parent[x][y] % 1000;
                    x = newX;
                    y = newY;
                }
                Collections.reverse(shortestPath);
                return shortestPath;
            }

            int[] dx = {0, 0, -1, 1};
            int[] dy = {-1, 1, 0, 0};

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (notBlocked(array, size, newX, newY) && !visited[newX][newY]) {
                    parent[newX][newY] = x * 1000 + y;
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        return null;
    }

    public static double probabilityOfCatchingFire(int k, double q) {
        return 1 - Math.pow(1 - q, k);
    }


    //New grid to make sure the open cells don't update immediately and then the grid is updated at the end.
    //Initial problem was that the fire would update and then after each time step, random open cells would turn to fire due to the neighbors
    public static void spreadFire(char[][] grid, int size, double flammability) {
        char[][] newGrid = new char[size][size];
    
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y] == OPEN_CELL || grid[x][y] == BOT_LOCATION) {
                    int burningNeighbors = countBurningNeighbors(grid, x, y);
                    double fireProbability = probabilityOfCatchingFire(burningNeighbors, flammability);
                    if (Math.random() <= fireProbability) {
                        newGrid[x][y] = FIRE_CELL;
                    }
                }
            }
        }
    
        // Update the original grid with the new fire cell locations
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (newGrid[x][y] == FIRE_CELL) {
                    grid[x][y] = FIRE_CELL;
                }
            }
        }
    }

    public static int countBurningNeighbors(char[][] grid, int x, int y) {
        int count = 0;
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length && grid[newX][newY] == FIRE_CELL) {
                count++;
            }
        }

        return count;
    }


    private static void printGrid(char[][] array, int size){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                StdOut.print(array[i][j] + " ");
            }
            StdOut.println();
        }

    }

    public static void main(String[] args) {
        StdOut.setFile(args[0]);

        int size = 25;
        char[][] grid = generateShip(size);
        double flammability = .9;

        int[] botLoc = new int[]{0,0};
        int[] buttonLoc = new int[]{0,0};

        for (int timeStep = 0; timeStep < size * size; timeStep++) {
            if(timeStep == 0){
                addButtonBotFire(grid, size);
                botLoc = findBot(grid);
                buttonLoc = findButton(grid);
                printGrid(grid, size);

                StdOut.println("Previous ^^^^^");
                for(int i = 0; i < size; i++){
                StdOut.print("- ");
                }
                StdOut.println();
                StdOut.println("After vvvvv");




            }
            else{
            ArrayList<int[]> bot1Path = BFSBOT2(grid, size, botLoc, buttonLoc);
            if (bot1Path != null && !bot1Path.isEmpty()) {
                int[] nextMove = bot1Path.remove(0);
                grid[botLoc[0]][botLoc[1]] = OPEN_CELL;
                grid[nextMove[0]][nextMove[1]] = BOT_LOCATION;
                
                printGrid(grid, size);
                StdOut.println("Previous ^^^^^");
                for(int i = 0; i < size; i++){
                    StdOut.print("- ");
                }
                StdOut.println();
                StdOut.println("After vvvvv");


            botLoc = nextMove;

            }

                if(countBurningNeighbors(grid, botLoc[0], botLoc[1]) > 2){
                    System.out.println("FAILURE");
                    break;
                }
                if(countBurningNeighbors(grid, buttonLoc[0], buttonLoc[1]) == 3){
                    System.out.println("BUTTON IS BLOCKED");
                    break;
                }
            spreadFire(grid, size, flammability);



            if (botLoc[0] == buttonLoc[0] && botLoc[1] == buttonLoc[1]) {
                grid[buttonLoc[0]][buttonLoc[1]] = BUTTON_PRESSED;
                System.out.println("Complete");
                for(int i = 0; i < size; i++){
                    for(int j = 0; j < size; j++){
                        if(grid[i][j] == FIRE_CELL) grid[i][j] = OPEN_CELL;
                    }
                }
                break;

            }
        }
        }

        printGrid(grid, size);
    }
}