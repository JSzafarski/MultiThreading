import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class game {
    int numPlayers;

    public void start_game(){
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
                int numPlayers = Integer.parseInt(player_input);
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i < 4; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");
                        file_input = System.console().readLine();//verify data type
                        if (Objects.equals(file_input, "X")) {
                            break;//exit the loop
                        }else { //check if the input is valid using try catch block
                            try () {//reading a file

                            } catch () {
                                System.out.println("invalid input ,we will ask you to repeat your input.");
                                //if the file read is invalid then repeat the fir loop
                            }
                        }
                        // validate
                    }
                    //calculate the necessary amounts of pebbles for the players
                    if (!Objects.equals(file_input, "X")){//if X was pressed then the program should repeat everything as the user requested to change inoputs
                        break;
                    }
                } else {
                    System.out.println("invalid input ,enter again!");//shows error
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid input ,enter again!");//shows error
            }
    }

    public enum blackBags{
        bagX(whiteBags.bagA),
        bagY(whiteBags.bagB),
        bagZ(whiteBags.bagC);
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        Random rand = new Random();
        whiteBags bagPair;
        //constructors
        blackBags(whiteBags bagPair) {this.bagPair = bagPair;}
        blackBags(){}
        //get and set methods for the list of pebbles in the bags
        public ArrayList<Integer> getPebbles() {return pebbles;}
        public void setPebbles(ArrayList<Integer> pebbles) {this.pebbles = pebbles;}
        //get and set methods for the bag pairs of each bag
        public whiteBags getBagPair() {return bagPair;}
        public void setBagPair(whiteBags bagPair) {this.bagPair = bagPair;}

        //draw pebble from a chosen bag method
        public int drawPebble() {
            if (this.getPebbles().size() == 0) {
                //return -1 if empty list
                return -1;
            } else {
                //else generate a random index for the list used to randomly select a pebble from the list
                int index = rand.nextInt(this.getPebbles().size());
                int pebbleWeight = this.getPebbles().get(index);
                //remove the pebble from the list
                this.getPebbles().remove(index);
                //return the weight of the selected pebble
                return pebbleWeight;
            }
        }

    }
    public enum whiteBags{
        bagA(blackBags.bagX),
        bagB(blackBags.bagY),
        bagC(blackBags.bagZ);
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        Random rand = new Random();
        blackBags bagPair;
        //constructors
        whiteBags(blackBags bagPair){this.bagPair = bagPair;}
        whiteBags(){}
        //get and set methods for the list of pebbles in the bags
        public ArrayList<Integer> getPebbles() {return pebbles;}
        public void setPebbles(ArrayList<Integer> pebbles) {this.pebbles = pebbles;}
        //get and set methods for the bag pairs of each bag
        public blackBags getBagPair() {return bagPair;}
        public void setBagPair(blackBags bagPair) {this.bagPair = bagPair;}

        //discard pebble into chosen bag method
        //public discardPebble(whiteBags thisBag)
    }

    public void read_csv(){}





}
