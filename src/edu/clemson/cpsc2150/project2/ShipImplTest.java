package edu.clemson.cpsc2150.project2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by csheare on 10/5/2016.
 */
public class ShipImplTest {
    Ship battleship;
    Ship carrier;
    Ship cruiser;
    Ship sub;
    Ship destroyer;


    @Before
    public void setUp() {
        battleship = new ShipImpl(ShipType.BATTLESHIP);
        carrier = new ShipImpl(ShipType.CARRIER);
        cruiser = new ShipImpl(ShipType.CRUISER);
        sub = new ShipImpl(ShipType.SUBMARINE);
        destroyer = new ShipImpl(ShipType.DESTROYER);
    }


    @Test
    public void coordTest(){
        Coordinate a = new Coordinate(0,0);
        Coordinate b = new Coordinate(1,0);
        Coordinate c = new Coordinate(2,0);

        Coordinate[] coordArray = {a,b,c};
        cruiser.setCoordinates(a, Direction.DOWN);
        Assert.assertArrayEquals(cruiser.getCoordinates(),coordArray);

    }

    @Test
    public void shootTest(){
        Coordinate a = new Coordinate(0,0);
        Coordinate b = new Coordinate(1,0);
        Coordinate c = new Coordinate(2,0);
        cruiser.setCoordinates(a, Direction.DOWN);

        //shoot in ship assumes that it is a hit or sunk based on shoot in grid
        //u would never shoot a ship if it wasn't in a grid for this project
        Status x = cruiser.shoot(a);
        Assert.assertEquals(x, Status.HIT);

        Status y = cruiser.shoot(b);
        Status z = cruiser.shoot(c);
        Assert.assertEquals(z, Status.SUNK);
        Assert.assertTrue(cruiser.isSunk());

    }


    @Test
    public void TypeTest(){
        Assert.assertEquals(battleship.getType(),ShipType.BATTLESHIP);
        Assert.assertEquals(cruiser.getType(),ShipType.CRUISER);
        Assert.assertEquals(carrier.getType(),ShipType.CARRIER);
        Assert.assertEquals(sub.getType(),ShipType.SUBMARINE);
        Assert.assertEquals(destroyer.getType(),ShipType.DESTROYER);
    }


}