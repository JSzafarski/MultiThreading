import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import static org.junit.Assert.*;
import junit.runner.Version;


//@before runs before each test
//@After is run after each test is executed
//@before class runs this first thing in the code

/**
 * @author 690036000
 * @author 700040943
 * @since v1.0
 *This class does all unit tests on each method of the whole pebble game app.
 */
@RunWith(JUnit4.class)
public class PebblesTest{
    static int testsPassed = 0; // used to count how many tests have been passed.
    static int testsRun = 0;
    static String errorPhrase;
    static boolean error = false;
    static ArrayList<String> errorMsg = new ArrayList<String>();


    @Before
    public void testsCounter(){//counts how many tests have been executed(its run before each @Test method
        testsRun++;
    }
    @After//run after each test method and added error messages in case they occur
    public void testErrorSummary(){
        if(error) {//if this boolean is flagged true then it means the current test method failed the test
            errorMsg.add(errorPhrase);//adds test error summaries if they do happen
            error = false;//resets it for next methods as they may not result in a failed run
        }else{
            testsPassed++; //since this is sun after each test when the recent test passed then we increment the number of tests passed
        }
    }
    @After
    public void reseterror(){
        error=false;
    }
    //testing the player class
    @Test
    public void testgetPlayerID(){
        int playerID = 1000;//any player id is possible to be chosen as long as it's an integer
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(playerID);
        try {//checks if the assertion fails
            assertEquals(playerID, testPlayer.getPlayerID());//compares test input vs what the return method gives
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testgetPlayerID";
        }

    }

    @Test
    public void testSetGetPebbles(){
        int[] testpebbles = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};//a pebble array is chosen to populate players hand
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
        testPlayer.setPebbles(testpebbles);//pebble array setter in player class
        try {
            assertEquals(testpebbles, testPlayer.getPebbles());//compares if both arrays are the same (which they should be),also test if the getter works
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testSetGetPebbles";
        }
    }

    @Test
    public void testgenerateRandomChoiceandgetRandomBag(){
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);//creates a player object
        testPlayer.generateRandomChoice();//generates a random choice which will be used to pick a random bag from with either bag X,Y,Z
        int choice = testPlayer.getRandomBag();//getter method for getting the random choice from the player instance
        if(!(choice == 0 || choice == 1 || choice == 2)){
            try {
                fail();//if the value is not on of those 3 options then the test fails
            }catch(AssertionError e){
                error = true;
                errorPhrase = e +" at testgenerateRandomChoiceandgetRandomBag";
            }
        }else{
            assertTrue(true);//this signifies that the method has worked correctly
        }
    }

    @Test
    public void testlastBagDrawnAndgetLastBagDrawn(){
        String testBag ="A";//choose a letter (the choice isn't too significant here)
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);//creating a player object
        testPlayer.setLastBagDrawn(testBag);//setting last bag drawn
        try {
            assertEquals(testBag, testPlayer.getLastBagDrawn());//checking if the getter works and returns the expected result
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testlastBagDrawnAndgetLastBagDrawn";
        }
    }

    @Test
    public void testcalculateTotalWeight(){
        int[] testpebbles = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};//array used for testing purposes
        int expectedWeight = 55;//this is just a sum of the pebbles in the array above
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);//player instance being made
        testPlayer.setPebbles(testpebbles);//sets the array into player hand(array)
        try {
            assertEquals(testPlayer.getTotalWeight(), expectedWeight);//checks if 1) calculatetotalpebbles() works and 2) getter works
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testcalculateTotalWeight";
        }
    }

    @Test
    public void testreplacePebble(){//should replace a pebble from the array and place the replacement pebble inside the array
        boolean found = false;
        int replacement_pebble = 69;//pebble that will be replacing another pebble in the player array
        int[] testpebbles = new int[]{1,2,3,4,5,6,7,8,9,1,10,90};//a example pebble array that will be assigned to player
        PebbleGame.Player testPlayer = PebbleGame.getPebbleGame().new Player(1);
        //we need to check where the output pebble has been replaced by the replacement pebble
        testPlayer.setPebbles(testpebbles);//sets the array as players current hand
        int testweight = testPlayer.getTotalWeight(); //the total weight of the testpebbles array
        int testdiscardpebble = testPlayer.replacePebble(replacement_pebble);//replaces a pebble for the pebble of value 69
        //we want to check if at the position of the previous discard pebble exists the new replacement pebble
        for(int x = 0;x<=9;x++){
            if (testPlayer.getPebbles()[x] == replacement_pebble){
                assertEquals(testdiscardpebble,testpebbles[x]);//checks if the correct pebble has been replaced at the correct position in the array
                testweight = testweight - testdiscardpebble + replacement_pebble;//the mathematical calculation of new weight based on draw/discards of pebbles
                assertEquals(testweight,testPlayer.getTotalWeight());//check if updated weight is what it should be.
                found = true;
                break;
            }
        }
        if(!found){
            try {
                fail();//if replacement pebble is not found then the test fails as its against the expectation
            }catch(AssertionError e){
                error = true;
                errorPhrase = e +" at testreplacePebble";
            }
        }
    }

    //testing bag class

    @Test
    public void testgetBagPair(){
        Bag testBagPair = new Bag();//creates a bag pair object
        Bag testBag = new Bag();//creates a bag object so it can be manipulated
        testBag.setBagPair(testBagPair);//sets the link between two bags
        try {
            assertSame(testBagPair, testBag.getBagPair());//checks if getting the bag pair of testBag is the same as testBagPair
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testgetBagPair";
        }
    }

    @Test
    public void testsetPebblesdrawPebble(){//checks if pebbles can be set and drawn prom a particular bag object correctly
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10));
        Bag testBag = new Bag();
        testBag.setPebbles(testPebbles);
        for(int x=0;x<testPebbles.size()-1;x++){
            boolean value = testPebbles.contains(testBag.drawPebble());//checks if all the items that are randomly selected from a bag are all in the original list that was passed into the bag in the first place
            try{
                assertTrue(value);//if one of the items is incorrect then assert false will fail the test
            }catch(AssertionError e){
                error = true;
                errorPhrase = e +" at testsetPebblesdrawPebble";
                break;
            }
        }
    }

    @Test
    public void testdiscardPebble(){
        int testreplacementPebble = 66;//replacement pebble that usually come from the player
        Bag testBag = new Bag();
        testBag.discardPebble(testreplacementPebble);//adds the replacement pebble
        try {
            assertEquals(testreplacementPebble, testBag.drawPebble());//since only one pebble was placed it should be the pebble that gets drawn out when drawPebble is called
            //should return the replacement pebble as it was the only pebble in the bag after it's been emptied
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testdiscardPebble";
        }
    }

    @Test
    public void testrefillBag(){//tests if the black bag get refilled from it white bag pair correctly
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(44, 24, 29, 32));
        Bag bagX = new Bag();
        Bag bagA = new Bag();
        bagA.setPebbles(testPebbles);
        bagX.setBagPair(bagA);
        bagX.refillBag();
        try{
            assertEquals(testPebbles, bagX.getPebbles());//compares if the array list from bagA was correctly passed into bagX
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testrefillBag";
        }
    }

    //testing the PebbleGame
    @Test
    public void testsetBagPairs(){
        PebbleGame.setBagPairs();
        try {
            assertSame(PebbleGame.bagX, PebbleGame.bagA);//Checks if the objects are the same since bag object is passed by reference into another bag object
            assertSame(PebbleGame.bagY, PebbleGame.bagB);
            assertSame(PebbleGame.bagZ, PebbleGame.bagC);
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testsetBagPairs";
        }
    }

    @Test
    public void testread_csv(){
        //verify exeptions
        String testfile  = "test.txt";//create the file increase it doesn't exist
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(10, 10, 10, 10, 10, 5, 5, 5, 5, 5));
        try {
            Assert.assertEquals(testPebbles, PebbleGame.pebbleGame.read_csv(testfile));//assert this is the same and handle exceptions later
        }catch(InvalidfileExeption | java.io.IOException IOException){//if an exception is thrown then the assertion is false and the test fails.
            try {
                fail();
            }catch(AssertionError e){//adds the error to the list of errors, so they can be viewed at the end of execution all the test classes
                error = true;
                errorPhrase = e +" at testread_csv";
            }
        }
        //tests for invalid file
        boolean passed = false;
        try {
            PebbleGame.pebbleGame.read_csv("incorrectfile.blah");//assert this is the same and handle exceptions later
        }catch(InvalidfileExeption | java.io.IOException IOException ){//if an exception is thrown then the assertion is false and the test fails.
            passed=true;//if an exception is thrown then that's correct
        }
        try {
            assertTrue(passed);
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testread_csv";
        }
    }

    @Test
    public void testcalculate_minPebbles(){//checks if the function for getting minimum number of players required for the game is correctly working
        int testPlayercount = 10;
        try {
            assertEquals(11 * testPlayercount, PebbleGame.calculate_minPebbles(10));//player number *11 is the number of pebbles as the spec recommended
        }catch(AssertionError e){
            error = true;
            errorPhrase = e +" at testcalculate_minPebbles";
        }
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
            }
        }
        if (!found){
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
            //test failed
        }
        if(changeCount>1){
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
            //test failed as there should be only one change for every draw/discard action
        }
        //checks if the players discard pebble is in bag A and check if player took a pebble form bag x
        try {
            assertEquals(pebbleDiscarded, PebbleGame.bagX.getBagPair().drawPebble());//check if the pebble discarded is the same as the pebble in the white bag
        }catch(AssertionError e){
            error = true;
            errorPhrase += e +" at testdrawAndDiscardFromBagY";
        }
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
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
        }
       while(true){
           int pebbleFromBagX = PebbleGame.bagX.drawPebble();
           if(pebbleFromBagX == -1){
               break;
           }else if(pebbleFromBagX == pebbleRepalced){//if the pebble is still inside the bag then the test failed
               try {
                   fail();
               }catch (AssertionError e){
                   error = true;
                   errorPhrase += e +" at testdrawAndDiscardFromBagY";
               }
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
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
            //test failed
        }
        if(changeCount>1){
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
            //test failed as there should be only one change for every draw/discard action
        }
        //checks if the players discard pebble is in bag A and check if player took a pebble form bagY
        try {
            assertEquals(pebbleDiscarded, PebbleGame.bagY.getBagPair().drawPebble());//check if the pebble discarded is the same as the pebble in the white bag
        }catch(AssertionError e){
            error = true;
            errorPhrase += e +" at testdrawAndDiscardFromBagY";
        }
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
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
        }
        while(true){
            int pebbleFromBagX = PebbleGame.bagY.drawPebble();
            if(pebbleFromBagX == -1){
                break;
            }else if(pebbleFromBagX == pebbleRepalced){//if the pebble is still inside the bag then the test failed
                try {
                    fail();
                }catch (AssertionError e){
                    error = true;
                    errorPhrase += e +" at testdrawAndDiscardFromBagY";
                }
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
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
            //test failed
        }
        if(changeCount>1){
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
            //test failed as there should be only one change for every draw/discard action
        }
        //checks if the players discard pebble is in bag A and check if player took a pebble form bagZ
        try {
            assertEquals(pebbleDiscarded, PebbleGame.bagZ.getBagPair().drawPebble());//check if the pebble discarded is the same as the pebble in the white bag
        }catch(AssertionError e){
            error = true;
            errorPhrase += e +" at testdrawAndDiscardFromBagY";
        }
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
            try {
                fail();
            }catch (AssertionError e){
                error = true;
                errorPhrase += e +" at testdrawAndDiscardFromBagY";
            }
        }
        while(true){
            int pebbleFromBagX = PebbleGame.bagZ.drawPebble();
            if(pebbleFromBagX == -1){
                break;
            }else if(pebbleFromBagX == pebbleRepalced){//if the pebble is still inside the bag then the test failed
                try {
                    fail();
                }catch (AssertionError e){
                    error = true;
                    errorPhrase += e +" at testdrawAndDiscardFromBagY";
                }
                break;
            }
        }
    }

    @Test
    public void testdraw10(){
        ArrayList<Integer> testPebbles = new ArrayList<>(Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10));
        int[] arrtestPebbles = new int[]{1, 2, 3, 4, 5, 6,7,8,9,10};//equal to the arraylist pebbles
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
                try {
                    fail();
                }catch (AssertionError e){
                    error = true;
                    errorPhrase += e +" at  testdraw10";
                }
            }else{
                try {
                    assertSame(testplayer.getPebbles(), arrtestPebbles);
                }catch(AssertionError e){
                    error = true;
                    errorPhrase += e +" at  testdraw10";
                }
                //assert the player has the 10 pebbles in its array
            }
        }else if(Objects.equals(testplayer.getLastBagDrawn(), "Y")){
            if(PebbleGame.bagY.drawPebble()!=-1){//check if its empty after the drawing
                try {
                    fail();
                }catch (AssertionError e){
                    error = true;
                    errorPhrase += e +" at  testdraw10";
                }
            }else{
                try {
                    assertSame(testplayer.getPebbles(), arrtestPebbles);
                }catch(AssertionError e){
                    error = true;
                    errorPhrase += e +" at  testdraw10";
                }
                //assert the player has the 10 pebbles in its array
            }
        }else{//Z
            if(PebbleGame.bagZ.drawPebble()!=-1){//check if its empty after the drawing
                try {
                    fail();
                }catch (AssertionError e){
                    error = true;
                    errorPhrase += e +" at  testdraw10";
                }
            }else{
                try {
                    assertSame(testplayer.getPebbles(), arrtestPebbles);
                }catch(AssertionError e){
                    error = true;
                    errorPhrase += e +" at  testdraw10";
                }
                //assert the player has the 10 pebbles in its array
            }
        }
    }

    @AfterClass//runs after all @Test classes have run
    public static void testSummary(){//run after all @Test have been run successfully
        System.out.println("JUnit version: " + Version.id());//sows the version of jUnit used
        System.out.println("tests passed: "+(testsPassed)+" tests failed: "+(testsRun-testsPassed));
        if(testsRun-testsPassed<testsRun){//if there were fails then output the array that displays those errors
            System.out.println(errorMsg);
        }
    }
}
