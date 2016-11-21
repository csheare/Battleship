package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/21/2016.
 */
public class BoundedSetGrid implements Grid {

    public static final int DEFAULT_SHIP_COUNT = 5;
    public static final int DEFAULT_NUM_SHIP_COORDS = 17;
    public static final int DEFAULT_GRID_SIZE = 10;

    private int myRowCount, myColCount;

    private BoundedSet AttemptedCoordinates;
    private BoundedSet myShipCoords;
    private Ship[] myShips;

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

        myShips = new Ship[DEFAULT_SHIP_COUNT];
        myShipCoords = new BoundedSet(DEFAULT_NUM_SHIP_COORDS);
        AttemptedCoordinates = new BoundedSet(rows*cols);

        myTotalHitsRemaining = 0;
        myShipsPlaced = 0;
    }
    public void placeShip(Ship ship){
        //a ship will have been created and conflict wil have been checked

        //Place Ship
        myShips[myShipsPlaced] = ship;
        myShipsPlaced++;

        myTotalHitsRemaining += ship.getType().size;
        //Update Coordinates for Ships
        Coordinate[] newShipCoords = ship.getCoordinates();
        int newCoordsIndex = 0;
        int range = myShipCoordsCount + newShipCoords.length;
        for(int i = myShipCoordsCount; i < range;i++) {
            Coordinate c = new Coordinate(newShipCoords[newCoordsIndex].row,newShipCoords[newCoordsIndex].col);
            myShipCoords.insert(c);
            myShipCoordsCount++;
            newCoordsIndex++;
        }

    }
    public boolean isConflictingShipPlacement(Ship ship){
        Coordinate[] coordsToCheck = ship.getCoordinates();
        // loop through each coordinate of the ship to be placed
        for (int i = 0; i < coordsToCheck.length; i++) {
            Coordinate c = new Coordinate(coordsToCheck[i].row, coordsToCheck[i].col);

            // does the ship go off the grid?
            if (c.row < 0 || c.col < 0 || c.row >= myRowCount || c.col >= myColCount) {
                return true;
            }

            // does the ship overlap with another ship?
            if(myShipCoordsCount != 0) {
                if (myShipCoords.contains(c)) {
                    return true;
                }
            }
        }
        return false;

    }
    // see Ship.shoot()
    public Status shoot(Coordinate coord){
        Coordinate c = coord;
        Status stat = Status.MISS;//Returned if not found
        AttemptedCoordinates.insert(coord);
        myAttemptedCoordsCount++;

        //Does this coord exist in my ships coordinates
        if(myShipCoords.contains(c)){//exists either HIT or SUNK, need to find ship
                for(Ship s: myShips){
                    for(Coordinate b : s.getCoordinates()){
                        if(b.equals(c)){//It is a hit or a sink

                            stat = s.shoot(c);
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
        return stat;
    }
    // returns Ship object representing the last ship
    // which was sunk (null if no ship has been sunk)
    public Ship getLastSunkShip(){
        return myLastSunkShip;
    }
    public boolean hasBeenAttempted(Coordinate coord){
        if(myAttemptedCoordsCount != 0) {
            if(AttemptedCoordinates.contains(coord)){
                return true;
            }
        }
        return false;
    }
    public void displayGrid(boolean showShips){
        char[][] grid = new char[myRowCount][myColCount];

        for(int i = 0;i<myRowCount;i++){
            for(int j = 0;j<myColCount;j++){
                grid[i][j] = '-';
            }
        }

        if (myAttemptedCoordsCount != 0) {
            for(int i = 0;i<myRowCount;i++){
                for(int j = 0;j<myColCount;j++){
                    Coordinate c = new Coordinate(i,j);
                    if(AttemptedCoordinates.contains(c)){
                        grid[i][j] = '+';
                        if(myShipCoords.contains(c)){
                            grid[i][j] = 'X';

                        }
                    }
                }
            }
        }
        if (myAttemptedCoordsCount != 0) {
            if (showShips) {
                for (int i = 0; i < myRowCount; i++) {
                    for (int j = 0; j < myColCount; j++) {
                        Coordinate c = new Coordinate(i, j);
                        if ((!AttemptedCoordinates.contains(c)) && myShipCoords.contains(c)) {
                            grid[i][j] = '@';
                        }
                    }
                }
            }
        }
        else{
            if(showShips){
                for (int i = 0; i < myRowCount; i++) {
                    for (int j = 0; j < myColCount; j++) {
                        Coordinate c = new Coordinate(i, j);
                        if(myShipCoords.contains(c)) {
                            grid[i][j] = '@';
                        }
                    }
                }
            }
        }

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
