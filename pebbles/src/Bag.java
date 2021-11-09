import java.util.ArrayList;
import java.util.Random;

public class Bag {

    Random rand = new Random();
    private ArrayList<Integer> pebbles = new ArrayList<Integer>();
    private Bag bagPair;

    public ArrayList<Integer> getPebbles() {return this.pebbles;}

    public void setPebbles(ArrayList<Integer> pebbles) {
        int pebblePos = 0;
        while(pebblePos <= pebbles.size()-1){
            this.pebbles.add(pebbles.get(pebblePos));
            pebblePos++;
        }
    }

    //get and set methods for the bag pairs of each bag
    public Bag getBagPair() {return bagPair;}
    public void setBagPair(Bag bagPair) {this.bagPair = bagPair;}

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

    //method for discarding pebble into selected bag from a selected player
    public void discardPebble(int replacementPebble) {
        this.getPebbles().add(replacementPebble);
    }

    //method to empty a white bag into an empty black bag
    public void refillBag() {
        this.setPebbles(this.getBagPair().getPebbles());
        this.getBagPair().getPebbles().clear();
    }
}
