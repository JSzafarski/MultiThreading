import java.util.ArrayList;
import java.util.Random;
/**
 * This class allows us to create multiple bag instances for each bag colour and letter
 * @since v1.0
 * @author 690036000
 * @author 700040943
 */
public class Bag {
    Random rand = new Random();
    private ArrayList<Integer> pebbles = new ArrayList<Integer>();
    private Bag bagPair;
    /**
     * This class allows us to create multiple bag instances for each bag colour and letter
     * @since v1.0
     */
    public ArrayList<Integer> getPebbles() {

        return this.pebbles;}
    /**
     * @param pebbles ,is an integer array that represents the pebbles that are offloaded into the bag integer array
     * @since v1.0
     */
    public void setPebbles(ArrayList<Integer> pebbles) {
        int pebblePos = 0;
        while(pebblePos <= pebbles.size()-1){//populates the bag with pebbles
            this.pebbles.add(pebbles.get(pebblePos));
            pebblePos++;
        }
    }
    /**
     * @param pebbles ,is an integer array that represents the pebbles that are offloaded into the bag integer array
     * @since v1.0
     */
    //get and set methods for the bag pairs of each bag
    public Bag getBagPair() {

        return bagPair;
    }
    /**
     * @param pebbles ,is an integer array that represents the pebbles that are offloaded into the bag integer array
     * @since v1.0
     */
    public void setBagPair(Bag bagPair) {

        this.bagPair = bagPair;
    }
    /**
     * @return int ,return the pebble that someone drawn
     * the pebble returned is erased form the integer list array as its removed form the bag so it's somewhat an abstraction
     * @since v1.0
     */
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
    /**
     * @param replecemenPebble, is an int which is the pebble a user has discarded and goes into a bag(this method is only used for white bag instances
     * @since v1.0
     */
    //method for discarding pebble into selected bag from a selected player
    public void discardPebble(int replacementPebble) {
        this.getPebbles().add(replacementPebble);
    }
    /**
     *used to repopulate the bag with pebbles from the white bag once the black bag is empty
     * @since v1.0
     */
    //method to empty a white bag into an empty black bag
    public void refillBag() {
        this.setPebbles(this.getBagPair().getPebbles());//checks which bag pair the pebbles have to come from
        this.getBagPair().getPebbles().clear();//clears the corresponding bag pair as it's been refilled
    }
}
