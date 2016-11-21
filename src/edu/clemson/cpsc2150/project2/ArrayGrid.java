package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/21/2016.
 */
public class ArrayGrid implements Grid {
    // Default number of ships
    public static final int DEFAULT_SHIP_COUNT = 5;
    public static final int DEFAULT_NUM_SHIP_COORDS = 17;
    public static final int DEFAULT_GRID_SIZE = 10;

    private int myRowCount, myColCount;
    private Status[][] myStatusGrid;
    private Ship[] myShips;
    private Coordinate[] myShipCoords;
    private Coordinate[] myAttemptedCoords;
    private int myTotalHitsRemaining;
    private int myShipsPlaced;
    private int myShipCoordsCount;
    private int myAttemptedCoordsCount;
    private Ship myLastSunkShip;


    public void setGridDimensions(int rows, int cols){
        myRowCount = rows;
        myColCount = cols;

        myShipCoordsCount = 0;
        myAttemptedCoordsCount = 0;

        myStatusGrid = new Status[myRowCount][myColCount];
        for(int i = 0;i<myRowCount;i++){
            for(int j = 0;j<myColCount;j++) {
                myStatusGrid[i][j] = Status.EMPTY;
            }
        }


        myShips = new Ship[DEFAULT_SHIP_COUNT];
        myShipCoords = new Coordinate[DEFAULT_NUM_SHIP_COORDS];
        myAttemptedCoords= new Coordinate[rows*cols];

        myTotalHitsRemaining = 0;
        myShipsPlaced = 0;

    }
    public void placeShip(Ship ship){
        //Place Ship
        myShips[myShipsPlaced] = ship;
        myShipsPlaced++;

        myTotalHitsRemaining += ship.getType().size;
        //Update Coordinates for Ships
        Coordinate[] newShipCoords = ship.getCoordinates();
        int newCoordsIndex = 0;
        int range = myShipCoordsCount + newShipCoords.length;
        for(int i = myShipCoordsCount; i < range;i++) {
            myShipCoords[i] = new Coordinate(newShipCoords[newCoordsIndex].row,newShipCoords[newCoordsIndex].col);

            myShipCoordsCount++;
            myStatusGrid[myShipCoords[i].row][myShipCoords[i].col] = Status.SHIP;
            newCoordsIndex++;
        }
    }
    public boolean isConflictingShipPlacement(Ship ship){
        Coordinate[] coordsToCheck = new Coordinate[ship.getType().size];
        coordsToCheck= ship.getCoordinates();
        // loop through each coordinate of the ship to be placed
      for (int i = 0; i < coordsToCheck.length; i++) {
          Coordinate c = new Coordinate(coordsToCheck[i].row, coordsToCheck[i].col);

          // does the ship go off the grid?
          if (c.row < 0 || c.col < 0 || c.row >= myRowCount || c.col >= myColCount) {
              return true;
          }

          // does the ship overlap with another ship?
          if(myShipCoordsCount != 0) {
              for (int j = 0;j<myShipCoordsCount;j++){
                  Coordinate a = myShipCoords[j];
                  if (a.equals(c)) {
                      return true;
                  }
              }
          }
      }
          return false;
    }

    public Status shoot(Coordinate coord){
        Coordinate c = coord;
        Status stat = Status.MISS;//Returned if not found
        myAttemptedCoords[myAttemptedCoordsCount] = coord;
        myAttemptedCoordsCount++;
        //Does this coord exist in my ships coordinates
        for (int i = 0;i<myShipCoordsCount;i++) {
            Coordinate a = myShipCoords[i];
            if(a.equals(c)){//exists either HIT or SUNK, need to find ship
                for(Ship s: myShips){
                    for(Coordinate b : s.getCoordinates()){
                        if(b.equals(c)){//It is a hit or a sink

                            stat = s.shoot(c);
                            myStatusGrid[b.row][b.col] = stat;
                            if(stat==Status.SUNK){
                                myLastSunkShip = new ShipImpl(s.getType());
                            }
                            //need to set ship status to SUNK
                            myTotalHitsRemaining--;
                            return stat;
                        }
                    }
                }
            }

        }

        return stat;

    }
    // which was sunk (null if no ship has been sunk)
    public Ship getLastSunkShip(){

        return myLastSunkShip;
    }


    public boolean hasBeenAttempted(Coordinate coord){
        if(myAttemptedCoordsCount != 0) {
            for (int j = 0;j<myAttemptedCoordsCount;j++) {
                Coordinate c = myAttemptedCoords[j];
                if (c.equals(coord)){
                    if((myStatusGrid[c.row][c.col] == Status.SHIP)){
                        return false;
                    }

                    return true;
                }
            }
        }
        return false;

    }
    public void displayGrid(boolean showShips){
        char[][] grid = new char[myRowCount][myColCount];

        //Start as all empty
        for(int i = 0;i<myRowCount;i++){
            for(int j = 0;j<myColCount;j++){
                grid[i][j] = '-';
            }
        }

        //fill in attempted shots as misses
        if (myAttemptedCoordsCount != 0) {

            for (int i = 0;i<myAttemptedCoordsCount;i++) {
                Coordinate c = myAttemptedCoords[i];
                grid[c.row][c.col] = '+';
            }
        }

        //iterate over ships
        if(myShipsPlaced != 0) {
            for (int i = 0;i<myShipCoordsCount;i++) {
                 Coordinate c = myShipCoords[i];
                    if(myAttemptedCoordsCount!=0){
                        for (int j = 0;j<myAttemptedCoordsCount;j++) {
                            Coordinate a = myAttemptedCoords[j];
                            if (a.equals(c)) {//the attempted coordinate is a ship
                                grid[a.row][a.col] = 'X';
                            } else {
                                if (showShips) {
                                    grid[a.row][a.col] = '@';

                                }
                            }
                        }
                    }else{
                        if (showShips) {
                            grid[c.row][c.col] = '@';
                        }

                    }
                }
            }


        //Print Coords

        System.out.println();
        for (int i = -1; i < myRowCount; ++i) {
            if (i == -1) {
                System.out.print("   ");
                for (int j = 0; j < myColCount; ++j) {
                    System.out.printf("%4d", j);
                }
                System.out.println();
            } else {
                System.out.print("\n  " + i);
                for (int j = 0; j < myColCount; ++j) {
                    System.out.printf("%4s", grid[i][j]);
                }
                System.out.println();
            }
        }

    }
    public boolean isGameOver(){
        if(myTotalHitsRemaining == 0){
            return true;
        }
        return false;

    }

    public int totalHits(){
        return myAttemptedCoordsCount;
    }
}
