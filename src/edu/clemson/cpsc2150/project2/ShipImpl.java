package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/20/2016.
 */
public class ShipImpl implements Ship {

    private int length = 0;
    private ShipType type;
    private Coordinate startcoord;
    private Coordinate[] coordinates;

    private Direction direction;


    ShipImpl(ShipType t) {
        type = t;
        length = t.size;

    }

    public void setCoordinates(Coordinate coord, Direction dir) {
        startcoord = new Coordinate(coord.row,coord.col);
        coordinates = new Coordinate[length];
        direction = dir;
        if (dir == Direction.RIGHT) {
            int j = 0;//to place coordinates in a coordinate array
            for (int i = startcoord.col; i < startcoord.col + length; i++) {
                Coordinate c = new Coordinate(startcoord.row,i);
                coordinates[j] = c;
                j++;
            }

        } else {//Direction is down
            int j = 0;//to place coordinates in a coordinate array
            for (int i = startcoord.row; i < startcoord.row + length; i++) {
                Coordinate c = new Coordinate(i,startcoord.col);
                coordinates[j] = c;
                j++;
            }

       }

    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    // returns the type of the ship
    public ShipType getType(){
        return type;
    }

    //does this assume the coordinate is the coordinates array?
    //I think since shoot is also in grid class, it will know which ship to call
    //if you get to shoot you merely are checking if it is a hit or sink
    public Status shoot(Coordinate coord) {
        length--;
        if(isSunk()) {
            return Status.SUNK;
        }
        else{
            return Status.HIT;
        }
    }

    public boolean isSunk() {
        if(length == 0){
            return true;
        }
        return false;

    }
}