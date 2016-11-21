package edu.clemson.cpsc2150.project2;

/**
 * Created by csheare on 9/20/2016.
 */
public class Coordinate {
    public int row,col;

    public Coordinate(int r,int c){
        row = r;
        col = c;

    }

    public boolean equals(Object o){
        if(!(o instanceof Coordinate)){
            return false;
        }
        Coordinate rhs = (Coordinate)o;
        return (rhs.row == row && rhs.col==col);

    }

}