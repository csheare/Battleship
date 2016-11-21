package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/20/2016.
 */
public interface Ship {
    // sets the placement of this ship starting at
    // coordinate "coord" and proceeding in direction "dir"

    /**
     * @requires this != null
     * @ensures ship had been placed over valid coordinates, not overlapping another ship or running off grid
     * @param coord to start the ship
     * @param dir direction to place the ship starting at coord
     */
    void setCoordinates(Coordinate coord, Direction dir);
    // returns an array of the ship’s coordinates

    /**
     * @requires this != null
     * @ensures this is unchanged
     * @return the type of the ship
     */
    Coordinate[] getCoordinates();

    /**
     * @requires this != null
     * @ensures this is not changes
     * @return type of ship
     */
    ShipType getType();
    // takes a shot at this ship’s coordinates and
    // returns Status.MISS, Status.HIT, or STATUS.SUNK

    /**
     * @requires that the coord is a ship  and this != null
     * @ensures a HIT is returned if just a hit, or a SUNK if the this is SUNK
     * @param coord to shoot at
     * @return
     */
    Status shoot(Coordinate coord);
    // returns true if all of the ship’s coordinates are hit
    // otherwise, false

    /**
     * @requires this != null
     * @ensures this is unchanged
     * @return true iff this is sunk, false otherwise
     */
    boolean isSunk();
}
