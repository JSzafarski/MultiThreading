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

@RunWith(JUnit4.class)
public class PebblesTest{
    /**
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
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(1);
        testPebbles.add(2);
        testPebbles.add(3);
        testPebbles.add(4);
        testPebbles.add(5);
        testPebbles.add(6);
        testPebbles.add(7);
        testPebbles.add(8);
        testPebbles.add(9);
        testPebbles.add(10);
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
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(44);
        testPebbles.add(24);
        testPebbles.add(29);
        testPebbles.add(32);
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
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(44);
        testPebbles.add(24);
        testPebbles.add(29);
        testPebbles.add(32);
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
        String testfile  = "test.txt";
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        assertSame(testPebbles,PebbleGame.pebbleGame.read_csv(testfile));
    }

    @Test
    public void testcalculate_minPebbles(){
        int testPlayercount = 10;
        assertEquals(11*testPlayercount,PebbleGame.calculate_minPebbles(10));
    }

    @Test
    public void testdrawAndDiscardFromBagX(){
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);

        PebbleGame.bagX.setPebbles(testPebbles);
        PebbleGame.setBagPairs();
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbble game
        testplayer.setPebbles(new int[]{1, 2, 3, 4, 5, 6,7,8,9,10});
        PebbleGame.drawAndDiscardFromBagX(testplayer,false);//assert just draw twn and assert picking one pebble
        //check if player removed a pebble
        //check if the players discard pebbel is in bag A and chck if player took a pebble form bag x



    }

    @Test
    public void testdrawAndDiscardFromBagY(){
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);

        PebbleGame.bagY.setPebbles(testPebbles);
        PebbleGame.setBagPairs();
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbble game
        testplayer.setPebbles(new int[]{1, 2, 3, 4, 5, 6,7,8,9,10});
        PebbleGame.drawAndDiscardFromBagY(testplayer,false);//assert just draw twn and assert picking one pebble
        //check if player removed a pebble
        //check if the players discard pebbel is in bag A and chck if player took a pebble form bag x

    }

    @Test
    public void testdrawAndDiscardFromBagZ(){
        ArrayList<Integer> testPebbles = new ArrayList<>();
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(10);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);
        testPebbles.add(5);

        PebbleGame.bagZ.setPebbles(testPebbles);
        PebbleGame.setBagPairs();
        PebbleGame testGame = new PebbleGame();//creates an instance of pebble game
        PebbleGame.Player testplayer = testGame.new Player(1000);//creates an instance of player in pebbble game
        testplayer.setPebbles(new int[]{1, 2, 3, 4, 5, 6,7,8,9,10});
        PebbleGame.drawAndDiscardFromBagZ(testplayer,false);//assert just draw twn and assert picking one pebble
        //check if player removed a pebble
        //check if the players discard pebbel is in bag A and chck if player took a pebble form bag x

    }
    @Test
    public void testdraw10(){

    }






}
