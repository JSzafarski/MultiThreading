import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Game {
    int numPlayers;
    ArrayList<Integer> pebbles = new ArrayList<Integer>();
    //instantiating black bags
    Bag bagX = new Bag(pebbles, "BLACK");
    Bag bagY = new Bag(pebbles, "BLACK");
    Bag bagZ = new Bag(pebbles, "BLACK");
    //instantiating white bags
    Bag bagA = new Bag(pebbles, "WHITE");
    Bag bagB = new Bag(pebbles, "WHITE");
    Bag bagC = new Bag(pebbles, "WHITE");

    //method for setting bag pairs of bags
    public void setBagPairs() {
        bagX.setBagPair(bagA);
        bagY.setBagPair(bagB);
        bagZ.setBagPair(bagC);
        bagA.setBagPair(bagX);
        bagB.setBagPair(bagY);
        bagC.setBagPair(bagZ);
    }

    public void start_game() {
        setBagPairs();
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated inger values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over.");
        System.out.println("");
        while (true) {//loops until use exits or enters correct information so the program can proceed
            System.out.println("Please enter the number of players:");
            String player_input = System.console().readLine();//verify data type
            try {
                @SuppressWarnings("unused")//
                numPlayers = Integer.parseInt(player_input);
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i < 4; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");
                        file_input = System.console().readLine();//verify data type
                        if (Objects.equals(file_input, "X")) {
                            break;//exit the loop
                        } else { //check if the input is valid using try catch block
                            try () {//reading a file

                            } catch () {
                                System.out.println("invalid input ,we will ask you to repeat your input.");
                                //if the file read is invalid then repeat the for loop
                            }
                        }
                        // validate
                    }
                    //calculate the necessary amounts of pebbles for the players
                    if (!Objects.equals(file_input, "X")) {//if X was pressed then the program should repeat everything as the user requested to change inputs
                        break;
                    }
                } else {
                    System.out.println("invalid input ,enter again!");//shows error
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid input ,enter again!");//shows error
            }
        }


    }


    public void read_csv () {
    }

}