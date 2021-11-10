import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

//@before runs before each test
//@After is run after each test is executed
//@before class runs this first thing in the code

@RunWith(JUnit4.class)
public class PebblesTest{
    /**
     * @author 690036000
     * @author ........
     * @since v1.0
     *This class does all unit tests on each method of the whole pebble game app.
     *
     *make it count errors idk??
     */
    //testing the player class
    @Test
    public void testgetPlayerID(){
        int playerID = 1000;
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(playerID);
        assertEquals(playerID,testPlayer.getPlayerID());//compares test input vs what the return method gives
    }
    @Test
    public void testSetGetPebbles(){
        int[] testpebbles = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
        testPlayer.setPebbles(testpebbles);
        assertEquals(testpebbles,testPlayer.getPebbles());
    }
    @Test
    public void testgenerateRandomChoiceandgetRandomBag(){
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
        testPlayer.generateRandomChoice();
        int choice = testPlayer.getRandomBag();
        assertEquals(0,choice);//need to make sure this is done
    }

    @Test
    public void testlastBagDrawnAndgetLastBagDrawn(){
        String testBag ="A";
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
        testPlayer.setLastBagDrawn(testBag);
        assertEquals(testBag,testPlayer.getLastBagDrawn());

    }
    @Test
    public void testcalculateTotalWeight(){
        int[] testpebbles = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int expectedWeight =55;
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
        testPlayer.setPebbles(testpebbles);
        assertEquals(testPlayer.getTotalWeight(),expectedWeight);
    }
    @Test
    public void testreplacePebble(){//should replace a pebble from the array and place the replacement pebble inside the array
        int replacement_pebble = 69;
        int[] testpebbles = new int[]{1,2,3,4,5,6,7,8,9,1,10,122};
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
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

    @Test
    public void testgetBagPair(){
        Bag testBagPair =new Bag();
        Bag testBag = new Bag();
        testBag.setBagPair(testBagPair);
        assertSame(testBagPair,testBag.getBagPair());
    }
    @Test
    public void testsetPebblesdrawPebble(){
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10);
        Bag testBag = new Bag();
        testBag.setPebbles(testPebbles);
        ArrayList<Integer> testList = new ArrayList<>();
        for(int x=0;x<testPebbles.size()-1;x++){
            testList.add(testBag.drawPebble());
        }
        Assert.assertEquals(testPebbles,testList);//checks if the both lists contain same items
    }

    @Test
    public void testdiscardPebble(){
        int testreplacementPebble = 66;
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(44, 24, 29, 32));
        Bag testBag = new Bag();
        testBag.setPebbles(testPebbles);
        boolean finished = false;
        while (finished){//empties the bag so its empty
            int testPebble = testBag.drawPebble();
            if(testPebble==-1){
                finished = true;
            }
        }
        testBag.discardPebble(testreplacementPebble);//adds the replacement pebble
        assertEquals(testreplacementPebble,testBag.drawPebble());
        //should return the replacement pebble as it was the only pebble in the bag after it's been emptied
    }

    @Test
    public void testrefillBag(){
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(44, 24, 29, 32));
        Bag bagX = new Bag();
        Bag bagA = new Bag();
        bagA.setPebbles(testPebbles);
        bagX.setBagPair(bagA);
        bagX.refillBag();
        assertEquals(testPebbles,bagX.getPebbles());
    }

    //testing the thread class

    //testing the PebbleGame
    @Test
    public void testsetBagPairs(){
        PebbleGame.setBagPairs();
        assertSame(PebbleGame.bagX,PebbleGame.bagA);
        assertSame(PebbleGame.bagY,PebbleGame.bagB);
        assertSame(PebbleGame.bagZ,PebbleGame.bagC);
    }

    @Test
    public void testread_csv() throws InvalidfileExeption, IOException {
        //verify exeptions
        String testfile  = "test.txt";//create the file increase it doesn't exist
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10,11));
        assertSame(testPebbles,PebbleGame.pebbleGame.read_csv(testfile));//assert this is the same and handle exceptions later
    }

    @Test
    public void testcalculate_minPebbles(){
        int testPlayercount = 10;
        assertEquals(11*testPlayercount,PebbleGame.calculate_minPebbles(10));
    }

    @Test
    public void testdrawAndDiscardFromBagX(){//bagX
        //the data set for the array lists must be all unique for every index to avoid repetitions otherwise the testing inst conclusive
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10,11));
        int[] testArray = new int[]{11, 12, 13, 14, 15, 16,17,18,19,20};//data chosen is important
        boolean found = false;
        int pebbleRepalced = 0;
        int pebbleDiscarded = 0;
        int changeCount = 0;//used to count how many changes have been made in the array
        PebbleGame.setBagPairs();
        PebbleGame.bagX.setPebbles(testPebbles);
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbble game
        testplayer.setPebbles(testArray);
        PebbleGame.drawAndDiscardFromBagX(testplayer,false);//assert just draw twn and assert picking one pebble
        for(int i = 0 ;i<=9;i++) {
            //iterate over the player array and compare changes in original array vs the new player array
            if (testplayer.getPebbles()[i] != testArray[i]) {
                pebbleRepalced = testplayer.getPebbles()[i];//pebble that was take from bagX (BLACK BAG)
                pebbleDiscarded = testArray[i];//Pebble that was put into bag A (WHITE BAG)
                changeCount++;
                found = true;
                //if the position at the two array is different then we found the
            }
        }
        if (!found){
            fail();
            //test failed
        }
        if(changeCount>1){
            fail();
            //test failed as there should be only one change for every draw/discard action
        }
        //checks if the players discard pebble is in bag A and check if player took a pebble form bag x
        assertEquals(pebbleDiscarded,PebbleGame.bagX.getBagPair().drawPebble());//check if the pebble discarded is the same as the pebble in the white bag
        //check if we can find the pebble draw in th original bag z array, but now it's not in the bagX
        //checks if the players drawn  pebble used to exist in the bagX before the drawing of pebble
        found = false;//boolean is this time used in a different check
        for(int j = 0 ;j<=testPebbles.size()-1;j++){//checks if player removed a pebble
            if(pebbleRepalced==testPebbles.get(j)){
                found = true;
                break;
            }
        }
        if(!found){
            fail();
        }
       while(true){
           int pebbleFromBagX = PebbleGame.bagX.drawPebble();
           if(pebbleFromBagX == -1){
               break;
           }else if(pebbleFromBagX == pebbleRepalced){//if the pebble is still inside the bag then the test failed
               fail();
               break;
           }
       }
    }

    @Test
    public void testdrawAndDiscardFromBagY(){//bagY
        //the data set for the array lists must be all unique for every index to avoid repetitions otherwise the testing inst conclusive
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10,11));
        int[] testArray = new int[]{11, 12, 13, 14, 15, 16,17,18,19,20};//data chosen is important
        boolean found = false;
        int pebbleRepalced = 0;
        int pebbleDiscarded = 0;
        int changeCount = 0;//used to count how many changes have been made in the array
        PebbleGame.setBagPairs();
        PebbleGame.bagY.setPebbles(testPebbles);
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbleGame
        testplayer.setPebbles(testArray);
        PebbleGame.drawAndDiscardFromBagY(testplayer,false);//assert just draw twn and assert picking one pebble
        for(int i = 0 ;i<=9;i++) {
            //iterate over the player array and compare changes in original array vs the new player array
            if (testplayer.getPebbles()[i] != testArray[i]) {
                pebbleRepalced = testplayer.getPebbles()[i];//pebble that was take from bagY (BLACK BAG)
                pebbleDiscarded = testArray[i];//Pebble that was put into bag B (WHITE BAG)
                changeCount++;
                found = true;
            }
        }
        if (!found){
            fail();
            //test failed
        }
        if(changeCount>1){
            fail();
            //test failed as there should be only one change for every draw/discard action
        }
        //checks if the players discard pebble is in bag A and check if player took a pebble form bagY
        assertEquals(pebbleDiscarded,PebbleGame.bagY.getBagPair().drawPebble());//check if the pebble discarded is the same as the pebble in the white bag
        //check if we can find the pebble draw in th original bagY array, but now it's not in the bagY
        //checks if the players drawn  pebble used to exist in the bagX before the drawing of pebble
        found = false;//boolean is this time used in a different check
        for(int j = 0 ;j<=testPebbles.size()-1;j++){//checks if player removed a pebble
            if(pebbleRepalced==testPebbles.get(j)){
                found = true;
                break;
            }
        }
        if(!found){
            fail();
        }
        while(true){
            int pebbleFromBagX = PebbleGame.bagY.drawPebble();
            if(pebbleFromBagX == -1){
                break;
            }else if(pebbleFromBagX == pebbleRepalced){//if the pebble is still inside the bag then the test failed
                fail();
                break;
            }
        }

    }

    @Test
    public void testdrawAndDiscardFromBagZ(){//bagZ
        //the data set for the array lists must be all unique for every index to avoid repetitions otherwise the testing inst conclusive
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10,11));
        int[] testArray = new int[]{11, 12, 13, 14, 15, 16,17,18,19,20};//data chosen is important
        boolean found = false;
        int pebbleRepalced = 0;
        int pebbleDiscarded = 0;
        int changeCount = 0;//used to count how many changes have been made in the array
        PebbleGame.setBagPairs();
        PebbleGame.bagZ.setPebbles(testPebbles);
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbble game
        testplayer.setPebbles(testArray);
        PebbleGame.drawAndDiscardFromBagZ(testplayer,false);//assert just draw twn and assert picking one pebble
        for(int i = 0 ;i<=9;i++) {
            //iterate over the player array and compare changes in original array vs the new player array
            if (testplayer.getPebbles()[i] != testArray[i]) {
                pebbleRepalced = testplayer.getPebbles()[i];//pebble that was take from bagZ (BLACK BAG)
                pebbleDiscarded = testArray[i];//Pebble that was put into bag C (WHITE BAG)
                changeCount++;
                found = true;
            }
        }
        if (!found){
            fail();
            //test failed
        }
        if(changeCount>1){
            fail();
            //test failed as there should be only one change for every draw/discard action
        }
        //checks if the players discard pebble is in bag A and check if player took a pebble form bagZ
        assertEquals(pebbleDiscarded,PebbleGame.bagZ.getBagPair().drawPebble());//check if the pebble discarded is the same as the pebble in the white bag
        //check if we can find the pebble draw in th original bag z array, but now it's not in the bagZ
        //checks if the players drawn  pebble used to exist in the bagX before the drawing of pebble
        found = false;//boolean is this time used in a different check
        for(int j = 0 ;j<=testPebbles.size()-1;j++){//checks if player removed a pebble
            if(pebbleRepalced==testPebbles.get(j)){
                found = true;
                break;
            }
        }
        if(!found){
            fail();
        }
        while(true){
            int pebbleFromBagX = PebbleGame.bagZ.drawPebble();
            if(pebbleFromBagX == -1){
                break;
            }else if(pebbleFromBagX == pebbleRepalced){//if the pebble is still inside the bag then the test failed
                fail();
                break;
            }
        }
    }

    @Test
    public void testdraw10(){
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10));
        boolean found = false;
        int pebbleRepalced = 0;
        int pebbleDiscarded = 0;
        int changeCount = 0;//used to count how many changes have been made in the array
        PebbleGame.setBagPairs();
        PebbleGame.bagX.setPebbles(testPebbles);
        PebbleGame.bagY.setPebbles(testPebbles);
        PebbleGame.bagZ.setPebbles(testPebbles);
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbble game
        PebbleGame.draw10(testplayer);
        if(Objects.equals(testplayer.getLastBagDrawn(), "X")){
            if(PebbleGame.bagX.drawPebble()!=-1){//check if its empty after the drawing
                fail();
            }else{

                //assert the player has the 10 pebbles in it's array
            }
        }else if(Objects.equals(testplayer.getLastBagDrawn(), "Y")){
            if(PebbleGame.bagY.drawPebble()!=-1){//check if its empty after the drawing
                fail();
            }else{
                int[] pebblearray = new int[testPebbles.size()];
                pebblearray = testPebbles.toArray();
                assertSame(testplayer.getPebbles(),);
                //assert the player has the 10 pebbles in it's array
            }
        }else{//Z
            if(PebbleGame.bagZ.drawPebble()!=-1){//check if its empty after the drawing
                fail();
            }else{
                //assert the player has the 10 pebbles in it's array
            }
        }


    }

    //testing the PlayerThread Class methods
//    @Test
//    public testCreateFile(){
//
//    }
//
//    @Test
//    public testrun(){
//
//    }


}
