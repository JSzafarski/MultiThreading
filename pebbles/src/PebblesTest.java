import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.experimental.theories.suppliers.TestedOn;
import static org.junit.Assert.*;


public class PebblesTest{

    /**
     *This class does all unit tests on each method of the whole pebble game app.
     *
     *
     */
    //testing the player class
    @Test
    public void testgetPlayerID(int playerID){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(playerID);
        assertEquals(playerID,testPlayer.getPlayerID());//compares test input vs what the return method gives
    }
    @Test
    public void testSetGetPebbles(int[] pebbles){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        testPlayer.setPebbles(pebbles);
        assertEquals(pebbles,testPlayer.getPebbles());
    }
    @Test
    public void testgenerateRandomChoiceandgetRandomBag(){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        testPlayer.generateRandomChoice();
        int choice = testPlayer.getRandomBag();
        assertEquals(0,choice);
    }

    @Test
    public void testlastBagDrawnAndgetLastBagDrawn(String testBag){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        testPlayer.setLastBagDrawn(testBag);
        assertEquals(testBag,testPlayer.getLastBagDrawn());

    }
    @Test
    public void testcalculateTotalWeight(int[] testpebbles,int expectedWeight){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        testPlayer.setPebbles(testpebbles);
        assertEquals(testPlayer.getTotalWeight(),expectedWeight);
    }
    @Test
    public void testreplacePebble(int replacement_pebble){//should replace a pebble from the array and place the replacement pebble inside the array

    }





            //        private void updateWeight(int newPebble, int oldPebble){//private as it only used by the player during run-time
            //            this.setTotalWeight(this.getTotalWeight() - oldPebble + newPebble);
            //            //much more effecicnt than iterating the whole array each time its time complxity is 0(1) instead of O(K)
            //        }
            //
            //
            //        //takes a new pebble randomly adds it to the players hand in place of another pebble and returns the old pebble
            //
            //        public int replacePebble(int replacementPebble) {
            //            int index = rand.nextInt(this.getPebbles().length-1);
            //            int discardPebble = this.getPebbles()[index];
            //            this.getPebbles()[index] = replacementPebble;
            //            this.updateWeight(replacementPebble, discardPebble);
            //            return discardPebble;
            //        }



            //private ArrayList<Integer> getPebbles() {return this.pebbles;}
    //    public void setPebbles(ArrayList<Integer> pebbles) {
    //        int s = 0;
    //        while(s <= pebbles.size()-1){
    //            this.pebbles.add(pebbles.get(s));
    //            s++;
    //        }
    //    }
    //
    //    //get and set methods for the bag pairs of each bag
    //    public Bag getBagPair() {return bagPair;}
    //    public void setBagPair(Bag bagPair) {this.bagPair = bagPair;}//not sure if this will work
    //
    //    //get and set methods for the bag colours of each bag
    //    //    public String getBagColour() {return bagColour;}
    //    //    public void setBagColour(String bagColour) {this.bagColour = bagColour;}
    //
    //    //draw pebble from a chosen bag method
    //    public int drawPebble() {
    //        if (this.getPebbles().size() == 0) {
    //            //return -1 if empty list
    //            return -1;
    //        } else {
    //            //else generate a random index for the list used to randomly select a pebble from the list
    //            int index = rand.nextInt(this.getPebbles().size());
    //            int pebbleWeight = this.getPebbles().get(index);
    //            //remove the pebble from the list
    //            this.getPebbles().remove(index);
    //            //return the weight of the selected pebble
    //            return pebbleWeight;
    //        }
    //    }
    //
    //    //method for discarding pebble into selected bag from a selected player
    //    public void discardPebble(int replacementPebble) {
    //        this.getPebbles().add(replacementPebble);
    //    }
    //
    //    //method to empty a white bag into an empty black bag
    //    public void refillBag() {
    //        this.setPebbles(this.getBagPair().getPebbles());
    //        this.getBagPair().getPebbles().clear();
    //    }



}
