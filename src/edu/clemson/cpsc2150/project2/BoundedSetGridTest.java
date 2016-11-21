package edu.clemson.cpsc2150.project2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by csheare on 10/1/2016.
 */
public class BoundedSetGridTest {

    Grid array;

    @Before
    public void setUp() {
        array = new BoundedSetGrid();
        array.setGridDimensions(10,10);
    }

    @Test
    public void placeShipsTest() {
        Ship ship = new ShipImpl(ShipType.DESTROYER);
        Coordinate c = new Coordinate(9, 0);
        ship.setCoordinates(c, Direction.RIGHT);
        //TEST1
        array.placeShip(ship);
        Assert.assertEquals(array.shoot(c), Status.HIT);
        Coordinate c2 = new Coordinate(9, 1);
        //TEST2
        Assert.assertEquals(array.shoot(c2), Status.SUNK);
        Coordinate c3 = new Coordinate(9, 2);
        //Test3
        Assert.assertEquals(array.shoot(c3), Status.MISS);
        //Test 4
        Coordinate c4 = new Coordinate(0, 0);
        Assert.assertEquals(array.shoot(c4), Status.MISS);
    }

    @Test
    public void ConflictingPlacementTest(){
        Ship ship = new ShipImpl(ShipType.BATTLESHIP);
        Coordinate c =  new Coordinate(9,0);
        ship.setCoordinates(c,Direction.RIGHT);
        array.placeShip(ship);

        //Test 1 ship overlap
        Ship ship2 = new ShipImpl(ShipType.CARRIER);
        Coordinate c2 =  new Coordinate(9,1);
        ship2.setCoordinates(c2,Direction.RIGHT);

        Assert.assertTrue(array.isConflictingShipPlacement(ship2));

        //Test2 not ship
        Ship ship3 = new ShipImpl(ShipType.CARRIER);
        Coordinate c3 =  new Coordinate(0,0);
        ship3.setCoordinates(c3,Direction.RIGHT);

        Assert.assertFalse(array.isConflictingShipPlacement(ship3));

        //Test 3 off grid
        Ship ship4 = new ShipImpl(ShipType.CARRIER);
        Coordinate c4 =  new Coordinate(9,9);
        ship4.setCoordinates(c4,Direction.RIGHT);
        Assert.assertTrue(array.isConflictingShipPlacement(ship4));
    }

    @Test//really the same as placing tests
    public void shootTest(){
        Ship ship = new ShipImpl(ShipType.DESTROYER);
        Coordinate c = new Coordinate(9, 0);
        ship.setCoordinates(c, Direction.RIGHT);
        //TEST1
        array.placeShip(ship);
        Assert.assertEquals(array.shoot(c), Status.HIT);
        Coordinate c2 = new Coordinate(9, 1);
        //TEST2
        Assert.assertEquals(array.shoot(c2), Status.SUNK);
        Coordinate c3 = new Coordinate(9, 2);
        //Test3
        Assert.assertEquals(array.shoot(c3), Status.MISS);
        //Test 4
        Coordinate c4 = new Coordinate(0, 0);

    }

    @Test
    public void getLastSunkShipTest(){
        Ship ship = new ShipImpl(ShipType.DESTROYER);
        Coordinate c1 = new Coordinate(9, 0);
        Coordinate c2 = new Coordinate(9, 1);
        ship.setCoordinates(c1, Direction.RIGHT);
        array.placeShip(ship);
        Assert.assertEquals(array.getLastSunkShip(),null);

        Assert.assertEquals(array.shoot(c1),Status.HIT);
        array.shoot(c2);

        Ship sunkShip = array.getLastSunkShip();
        Assert.assertEquals(sunkShip.getType(),ShipType.DESTROYER);

    }

    @Test
    public void hasBeenAttemptedTest(){
        Ship ship = new ShipImpl(ShipType.DESTROYER);
        Coordinate c = new Coordinate(9, 0);
        ship.setCoordinates(c, Direction.RIGHT);
        array.placeShip(ship);

        array.shoot(c);

        Assert.assertTrue(array.hasBeenAttempted(c));

    }

    @Test
    public void gameOverTest(){
        int i = 0;

        for(ShipType type : ShipType.values()) {
            Ship ship = new ShipImpl(type);
            Coordinate c = new Coordinate(0, i);
            ship.setCoordinates(c, Direction.DOWN);
            array.placeShip(ship);
            i++;
        }

        for(ShipType type : ShipType.values()) {
            for (int j = 0; j < type.size; j++) {
                Coordinate c = new Coordinate(j, 0);
                array.shoot(c);

            }
        }

        Assert.assertTrue(array.isGameOver());

    }


}