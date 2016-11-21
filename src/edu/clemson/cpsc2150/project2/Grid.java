package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/21/2016.
 */
public interface Grid {
    /**
     *
     * @param rows number of rows in the grid
     * @param cols number of cols in the grid
     * @requires rows and cols are both >= 0
     * @ensures grid dimensions set with specified dimensions
     */
    void setGridDimensions(int rows, int cols);

    /**
     *
     * @param ship is the ship to be placed
     * @requires that ship != null and isConflictingShipPlacement is false
     * @ensures the ship has been placed on the grid
     */
    void placeShip(Ship ship);

    /**
     *
     * @param ship is the ship to be checked
     * @requires ship != null
     * @ensures grid not changed
     * @return true iff the ship cannot be placed, false otherwise
     *
     */
    boolean isConflictingShipPlacement(Ship ship);


    /**
     *
     * @param coord is a coordinate on the grid
     * @requires coord != null and coord is a spot on grid
     * @ensures the type of shot for coord is return
     * @return stype of shot
     */
    Status shoot(Coordinate coord);

    /**
     *@requires this != null and a ship has been sunk
     * @ensures the last ship sunk has been returned
     * @return the last sunk ship
     */
    Ship getLastSunkShip();

    /**
     *
     * @param coord a coord on the grid
     * @requires this != null and coord != null and coord is a spot on the grid
     * @ensures return true iff coord already shot at, false otherwise
     * @return if the coord has been attempted
     */
    boolean hasBeenAttempted(Coordinate coord);

    /**
     *
     * @param showShips is true is ships to be shown, false otherwise
     * @requires this != null
     * @ensures a display of this with or without ships
     */
    void displayGrid(boolean showShips);

    /**
     *@requires this != null
     * @ensures grid is not changed and returns state of game
     * @return true iff game is over, flase otherwise
     */
    boolean isGameOver();

    /**
     *@requires this != null
     * @ensures grid is not changed
     * @return the total Hits for a grid
     */
    int totalHits();
}
