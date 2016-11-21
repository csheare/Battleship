package edu.clemson.cpsc2150.project2;

import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException {
        /****** UNCOMMENT THE METHOD FOR THE VERSION THAT YOU WANT TO RUN ******/

        //runArrayGridVersion();
        //runBoundedSetGridVersion();
        run100PercentVersion();
    }
    public static void run100PercentVersion() throws IOException {
        final int DEFAULT_GRID_SIZE = 10;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // setup the grid for each player
        Grid[] grids = new Grid[2];
        grids[0] = new ArrayGrid();
        grids[1] = new BoundedSetGrid();
        for (int player = 0; player < 2; ++player) {
            // create the grid
            grids[player].setGridDimensions(DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE);
            System.out.println("PLAYER " + (player + 1) + " TURN");

            // place the ships
            for (ShipType type : ShipType.values()) {
                Ship ship = new ShipImpl(type);
                // read in the coordinates
                System.out.printf("\nPlace your %s: ", type);
                Coordinate c = parseCoordinates(reader.readLine());

                // read in the direction

                System.out.print("Choose direction (DOWN/RIGHT): ");
                String dir = reader.readLine();


                ship.setCoordinates(c, toDirection(dir));
                // can we place the ship here?
                if (!grids[player].isConflictingShipPlacement(ship)) {
                    // place the ship!
                    grids[player].placeShip(ship);
                    //shipPlaced = true;
                } else {
                    while (grids[player].isConflictingShipPlacement(ship)) {
                        System.out.println("Unable to place ship at that location! Please try again.");
                        System.out.printf("\nPlace your %s Again: ", type);
                        Coordinate c2 = parseCoordinates(reader.readLine());

                        // read in the direction

                        System.out.print("Choose direction Again(DOWN/RIGHT): ");
                        String dir2 = reader.readLine();

                        ship.setCoordinates(c2, toDirection(dir2));
                    }
                    grids[player].placeShip(ship);
                }

                grids[player].displayGrid(true);

            }
        }

        // loop until the game is over
        int player = 0, opponent = 1;
        while (!grids[0].isGameOver() && !grids[1].isGameOver()) {
            // display player turn
            System.out.printf("\nPLAYER %d TURN\n", player + 1);
            grids[opponent].displayGrid(false);

            // get the coordinates for the next shot
            boolean isValidShot = false;
            Coordinate coord;
            do {
                System.out.print("Take a shot: ");
                coord = parseCoordinates(reader.readLine());

                // has a shot at this location already been attempted?
                if (grids[opponent].hasBeenAttempted(coord)) {
                    System.out.println("You have already shot at that location! Please try again.");
                } else {
                    isValidShot = true;
                }
            } while (!isValidShot);

            // take the shot at the opponent's grid
            Status result = grids[opponent].shoot(coord);


            // display the result of the shot
            System.out.print(result);
            if (result == Status.SUNK) {
                Ship s = grids[opponent].getLastSunkShip();
                System.out.println(" " + s.getType());
            }

                // switch players!
                player = (player + 1) % 2;
                opponent = (opponent + 1) % 2;

        }

            // game is over... determine who the winner is
            int winner;
            if (grids[1].isGameOver()) {
                winner = 1;
            } else {
                winner = 2;
            }
            int hitcount = grids[player].totalHits();
            System.out.printf("\nGame over! Player %d wins in %d shots!\n", winner, hitcount);


    }
    public static void runArrayGridVersion() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Grid player1 = new ArrayGrid();
        player1.setGridDimensions(10,10);
        System.out.printf("PLAYER 1 TURN\n");
        for(ShipType type : ShipType.values()){
            Ship ship = new ShipImpl(type);
            // read in the coordinates
            System.out.printf("\nPlace your %s: ", type);
            Coordinate c = parseCoordinates(reader.readLine());

            // read in the direction

            System.out.print("Choose direction (DOWN/RIGHT): ");
            String dir = reader.readLine();


            ship.setCoordinates(c,toDirection(dir));
            // can we place the ship here?
           if (!player1.isConflictingShipPlacement(ship)) {
                // place the ship!
                player1.placeShip(ship);
                //shipPlaced = true;
            } else {
                while(player1.isConflictingShipPlacement(ship)){
                    System.out.println("Unable to place ship at that location! Please try again.");
                    System.out.printf("\nPlace your %s Again: ", type);
                    Coordinate c2 = parseCoordinates(reader.readLine());

                    // read in the direction

                    System.out.print("Choose direction Again(DOWN/RIGHT): ");
                    String dir2 = reader.readLine();

                    ship.setCoordinates(c2,toDirection(dir2));
                }
               player1.placeShip(ship);
            }

            player1.displayGrid(true);

        }

        //Shoot stuff
        while(!player1.isGameOver()){
            //Get Shot
            boolean isValidShot = false;
            Coordinate c;
            do {
                System.out.print("Take a shot: ");
                c = parseCoordinates(reader.readLine());
                // has a shot at this location already been attempted?
                if (player1.hasBeenAttempted(c)) {
                    System.out.println("You have already shot at that location! Please try again.");
                } else {
                    isValidShot = true;
                }
            } while (!isValidShot);

            Status stat = player1.shoot(c);
            System.out.print(stat);
            if(stat == Status.SUNK){
                Ship s = player1.getLastSunkShip();
                System.out.println(" " + s.getType());
            }

            player1.displayGrid(false);

        }
        int hitcount = ((ArrayGrid)player1).totalHits();
        System.out.println("Game Over Won in" + hitcount + "hits");




    }
    public static void runBoundedSetGridVersion() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Grid player1 = new BoundedSetGrid();
        player1.setGridDimensions(10,10);
        System.out.printf("PLAYER 1 TURN\n");
        for(ShipType type : ShipType.values()){
            Ship ship = new ShipImpl(type);
            // read in the coordinates
            System.out.printf("\nPlace your %s: ", type);
            Coordinate c = parseCoordinates(reader.readLine());

            // read in the direction

            System.out.print("Choose direction (DOWN/RIGHT): ");
            String dir = reader.readLine();


            ship.setCoordinates(c,toDirection(dir));
            // can we place the ship here?
            if (!player1.isConflictingShipPlacement(ship)) {
                // place the ship!
                player1.placeShip(ship);
                //shipPlaced = true;
            } else {
                while(player1.isConflictingShipPlacement(ship)){
                    System.out.println("Unable to place ship at that location! Please try again.");
                    System.out.printf("\nPlace your %s Again: ", type);
                    Coordinate c2 = parseCoordinates(reader.readLine());

                    // read in the direction

                    System.out.print("Choose direction Again(DOWN/RIGHT): ");
                    String dir2 = reader.readLine();

                    ship.setCoordinates(c2,toDirection(dir2));
                }
                player1.placeShip(ship);
            }

            player1.displayGrid(true);

        }

        //Shoot stuff
        while(!player1.isGameOver()){
            //Get Shot
            boolean isValidShot = false;
            Coordinate c;
            do {
                System.out.print("Take a shot: ");
                c = parseCoordinates(reader.readLine());
                // has a shot at this location already been attempted?
                if (player1.hasBeenAttempted(c)) {
                    System.out.println("You have already shot at that location! Please try again.");
                } else {
                    isValidShot = true;
                }
            } while (!isValidShot);

            Status stat = player1.shoot(c);
            System.out.print(stat);
            if(stat == Status.SUNK){
                Ship s = player1.getLastSunkShip();
                System.out.println(" " + s.getType());
            }

            player1.displayGrid(false);

        }
        int hitcount = ((ArrayGrid)player1).totalHits();
        System.out.println("Game Over Won in" + hitcount + "hits");



    }
    public static Coordinate parseCoordinates(String input) {
        // split the input string into tokens
        String[] tokens = input.split("\\D+");

        // parse each token as an integer
         int x = Integer.parseInt(tokens[0]);
         int y = Integer.parseInt(tokens[1]);
        return new Coordinate(x,y);
    }
    public static Direction toDirection(String s) {
        if(s.equals("DOWN")){
            return Direction.DOWN;
        }
        else{
            return Direction.RIGHT;
        }
    }
}


