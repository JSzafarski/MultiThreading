import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
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



            //         public int getRandomBag() {
            //            return this.randomBag;
            //        }
            //
            //        public int getTotalWeight() {return this.totalWeight;}
            //
            //        public void GenerateRandomChoice(){
            //            this.randomBag = rand.nextInt(3);
            //        }
            //
            //        public void setTotalWeight(int totalWeight) {this.totalWeight = totalWeight;}
            //
            //        public int getPlayerID() {
            //            return this.playerID;
            //        }
            //
            //        public int[] getPebbles() {
            //            return this.pebbles;
            //        }
            //
            //        public void lastBagDrawn(String Bag){
            //            this.lastBagDrawn = Bag;
            //        }
            //
            //        public void setPebbles(int[] pebbles) {
            //            for (int i = 0;i<=9;i++){
            //                this.pebbles[i] = pebbles[i];
            //            }
            //        }
            //
            //        private  void updateWeight(int newPebble, int oldPebble){//private as it only used by the player during run-time
            //            this.setTotalWeight(this.getTotalWeight() - oldPebble + newPebble);
            //            //much more effecicnt than iterating the whole array each time its time complxity is 0(1) instead of O(K)
            //        }
            //
            //        public Player(int playerID) {
            //            this.playerID = playerID;
            //        }
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
            //
            //        //calculates the total weight of a player's hand
            //        public void calculateTotalWeight(){//make private
            //            int totalWeight = 0;
            //            for (int i = 0;i<=9;i++){
            //                totalWeight += this.pebbles[i] ;
            //            }
            //            this.setTotalWeight(totalWeight);
            //        }
            //
            //        public String getLastBagDrawn() {
            //            return lastBagDrawn;
            //        }

}
