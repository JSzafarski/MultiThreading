import java.util.ArrayList;
import java.util.Random;

public class game {
    int numPlayers;

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



}
