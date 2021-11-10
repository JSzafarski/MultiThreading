import java.util.ArrayList;
import java.util.Random;

public class Bag {
    /**
     * This class allows us to create multiple bag instances for each bag colour and letter
     * @since v1.0
     */

    Random rand = new Random();
    private ArrayList<Integer> pebbles = new ArrayList<Integer>();
    private Bag bagPair;

    public ArrayList<Integer> getPebbles() {
        /**
         * This class allows us to create multiple bag instances for each bag colour and letter
         * @since v1.0
         */
        return this.pebbles;}

    public void setPebbles(ArrayList<Integer> pebbles) {
        /**
         * @param pebbles ,is an integer array that represents the pebbles that are offloaded into the bag integer array
         * @since v1.0
         */
        int pebblePos = 0;
        while(pebblePos <= pebbles.size()-1){//populates the bag with pebbles
            this.pebbles.add(pebbles.get(pebblePos));
            pebblePos++;
        }
    }

    //get and set methods for the bag pairs of each bag
    public Bag getBagPair() {
        /**
         * @param pebbles ,is an integer array that represents the pebbles that are offloaded into the bag integer array
         * @since v1.0
         */
        return bagPair;
    }
    public void setBagPair(Bag bagPair) {
        /**
         * @param pebbles ,is an integer array that represents the pebbles that are offloaded into the bag integer array
         * @since v1.0
         */
        this.bagPair = bagPair;
    }

    //draw pebble from a chosen bag method
    public int drawPebble() {
        /**
         * @return int ,return the pebble that someone drawn
         * the pebble returned is erased form the integer list array as its removed form the bag so it's somewhat an abstraction
         * @since v1.0
         */
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
        /**
         * @param replecemenPebble, is an int which is the pebble a user has discarded and goes into a bag(this method is only used for white bag instances
         * @since v1.0
         */
        this.getPebbles().add(replacementPebble);
    }

    //method to empty a white bag into an empty black bag
    public void refillBag() {
        /**
         *used to repopulate the bag with pebbles from the white bag once the black bag is empty
         * @since v1.0
         */
        this.setPebbles(this.getBagPair().getPebbles());//checks which bag pair the pebbles have to come from
        this.getBagPair().getPebbles().clear();//clears the corresponding bag pair as it's been refilled
    }
}
