import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.experimental.theories.suppliers.TestedOn;
import java.util.*;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class PebblesTest{

    /**
     *This class does all unit tests on each method of the whole pebble game app.
     *
     *make it count errors idk??
     */
    //testing the player class
    @Test
    public void testgetPlayerID(int playerID){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(playerID);
        assertEquals(playerID,testPlayer.getPlayerID());//compares test input vs what the return method gives
    }
    @Test
    public void testSetGetPebbles(int[] testpebbles){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        testPlayer.setPebbles(testpebbles);
        assertEquals(testpebbles,testPlayer.getPebbles());
    }
    @Test
    public void testgenerateRandomChoiceandgetRandomBag(){
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        testPlayer.generateRandomChoice();
        int choice = testPlayer.getRandomBag();
        assertEquals(0,choice);//need to make sure this is done
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
    public void testreplacePebble(int replacement_pebble,int[] testpebbles){//should replace a pebble from the array and place the replacement pebble inside the array
        PebbleGame testPebbleGme = new PebbleGame();
        PebbleGame.Player testPlayer = testPebbleGme.new Player(1);
        //we need to check where the output pebble has been replaced by the replacement pebble
        testPlayer.setPebbles(testpebbles);
        int testweight = testPlayer.getTotalWeight();
        int testdiscardpebble = testPlayer.replacePebble(replacement_pebble);
        //we want to check if at the position of the previous discard pebble exists the new replacement pebble
        for(int x = 0;x<=9;x++){
            if (testPlayer.getPebbles()[x] == replacement_pebble){
                assertEquals(testdiscardpebble,testpebbles[x]);//checks if the correct pebble has been replaced at the correct position int the array
                assertEquals(testweight,testPlayer.getTotalWeight()-testdiscardpebble+replacement_pebble);//check if updated weight is what it should be.
                break;
            }
        }
    }
    //testing bag class

    //udr jsvs reflrction for private stuff
    @Test
    public void testgetBagPair(Bag testBagPair){
        Bag testBag = new Bag();
        testBag.setBagPair(testBagPair);
        assertSame(testBagPair,testBag.getBagPair());
    }
    @Test
    public void testsetPebblesdrawPebble(ArrayList<Integer> testPebbles){
        Bag testBag = new Bag();
        testBag.setPebbles(testPebbles);
        ArrayList<Integer> testList = new ArrayList<>();
        for(int x=0;x<testPebbles.size()-1;x++){
            testList.add(testBag.drawPebble());
        }
        Assert.assertEquals(testPebbles,testList);//checks if the both lists contain same items
    }

    @Test
    public void testdiscardPebble(int testreplacementPebble,ArrayList<Integer> testPebbles){
        //count how many times this item was before discard into the bag and after and it should be n+1;
        Bag testBag = new Bag();
        testBag.setPebbles(testPebbles);
        int testItemCount = 0;
        boolean finished = false;
        while (finished){
            int testPebble = testBag.drawPebble();
            if(testPebble==-1){
                finished = true;
            }else{
                if(testPebble == testreplacementPebble){
                    testItemCount++;
                }
            }
        }
        testBag.discardPebble(testreplacementPebble);
        //finish this offf!



    }

    @Test
    public void testrefillBag(){

    }



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

    //testing the thread class

    //testing the Pebbles test class methods





}
