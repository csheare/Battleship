package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/20/2016.
 */
public enum ShipType {
    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);

    public final int size;
    ShipType(int i){
        size = i;
    }


}
