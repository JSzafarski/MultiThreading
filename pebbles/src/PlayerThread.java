import java.util.Arrays;
import java.util.Objects;

//for data output
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
/**
 * @since v1.6
 * This is a class where the actual threads are run
 * The method instances all individually create a file based on the player id and output the player pebble choices in those txt files
 * @author 690036000
 * @author 700040943
 */
public class PlayerThread extends Thread {
    PebbleGame.Player thisPlayer;//declares a player instance which will be assigned with the instance that's passed into each thread
    static boolean wonGame = false;//static as we want to share this between all player threads since once player winning stops the game for all other players/threads

    public PlayerThread(PebbleGame.Player playerfromGame) {
        /**
         * @since V1.8
         * @param playerfromGame ,used to pass an instance of a player into the thread instance to represent that player
         */
        thisPlayer = playerfromGame;//sets the link between player instance and thread instance
    }//passes instance of player into the thread

    public void createFile(){
        /**
         * creates a file for that particular player as it need to write player choices into that txt file so the threads can be later traced in the txt file
         * @since V1.8
         */
        try {
            File playerFile = new File("Player "+thisPlayer.getPlayerID()+".txt");//file named after each player's ID
           if(playerFile.createNewFile()){
               System.out.println("INFO: File created! for player :"+thisPlayer.getPlayerID());
           }else{
               System.out.println("INFO: File does already exist for player(We will override the file) : "+thisPlayer.getPlayerID());
           }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void run() {//this will run each player
        /**
         *This method executes each thread
         * @since V1.8
         */
        System.out.println("INFO: Starting Player: "+ thisPlayer.getPlayerID() + "...");
        createFile();//generate a text file for each player
        thisPlayer.generateRandomChoice();//randomly the bag from which 10 pebbles will be chosen from
        PebbleGame.draw10(thisPlayer);//draws first 10 pebbles
        FileWriter writeToPlayerFile = null;
        try {
            writeToPlayerFile = new FileWriter("Player "+thisPlayer.getPlayerID()+".txt");
            //initialises a file write for a txt file and names the file based on the players id
        } catch (IOException e) {//catches issues
            System.out.println(e.getMessage());
        }
        while (!wonGame) {//iterates until player wins o their hand of pebbles is a sum equal to 100
            if (thisPlayer.getTotalWeight() == 100){
                wonGame = true;
                try {
                    assert writeToPlayerFile != null;
                    writeToPlayerFile.write("player: "+ thisPlayer.getPlayerID()+" Has won!"+ "\n");
                    writeToPlayerFile.flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("INFO: player: "+ thisPlayer.getPlayerID()+" Has won!");
                break;
                //have each thread if one stopped it did then stop all(maybe use a local variable in the Game class (boolean)
            }
            //player discards a pebble to a white bag
            //player chooses a black bag at random
            //player selects a pebble and if its empty then the player chooses another random back that's refilled
            //cycle repeats until a winner is announced
            //compare two arrays before and after to see what pebble has been discarded and drawn
            int[] TempPebbleArray = new int[10];
            for (int i = 0;i<=9;i++) {
                TempPebbleArray[i] = thisPlayer.getPebbles()[i];//array used to compare changes in players array after draw discard atomic action
            }
            thisPlayer.generateRandomChoice();
            if(thisPlayer.getRandomBag()==0){//then go to X
                PebbleGame.drawAndDiscardFromBagX(thisPlayer,false);
                Thread.yield();//allows other threads to have more chance at choosing a bag
                //after each a bag is picked by the thread suspend the execution to help other threads access the bag
                //is a way to tell the JVM that the current thread has paused its execution and the JVM can allow other threads to use the CPU and other resources.
            }else if(thisPlayer.getRandomBag()==1){//GO TO Y
                PebbleGame.drawAndDiscardFromBagY(thisPlayer,false);
                Thread.yield();//allows other threads to have more chance at choosing a bag
            }else{//GO TO Z
                PebbleGame.drawAndDiscardFromBagZ(thisPlayer,false);
                Thread.yield();//allows other threads to have more chance at choosing a bag
            }
            //initialises the necessary variables used to display player states in the output text files
            int newPebble = 0;
            int oldPebble = 0;
            String lastBagDrawn = "";
            String lastBagDiscarded = "" ;
            for (int i = 0 ;i<=9;i++){
                if(TempPebbleArray[i]!=thisPlayer.getPebbles()[i]){
                    newPebble = thisPlayer.getPebbles()[i];//used for outputting what pebble was discarded
                    oldPebble = TempPebbleArray[i];//used for outputting what pebble was drawn
                    break;//as there can only be one change in the array in a given iteration.
                }
            }
            if(Objects.equals(thisPlayer.getLastBagDrawn(), "X")){//checks which pag pair was used for the draw discard action
                lastBagDrawn = "X";
                lastBagDiscarded = "A";
            }else if(Objects.equals(thisPlayer.getLastBagDrawn(), "Y")){
                lastBagDrawn = "Y";
                lastBagDiscarded = "B";
            }else{//MUST BE Z THEN
                lastBagDrawn = "Z";
                lastBagDiscarded = "C";
            }
            //outputs necessary data into  texts files
            String string1=("player "+thisPlayer.getPlayerID()+" has drawn a " + newPebble + " from bag " + lastBagDrawn);
            String string2=("player "+thisPlayer.getPlayerID()+" hand is "+ Arrays.toString(TempPebbleArray).replace("[","").replace("]",""));
            String string3=("player "+thisPlayer.getPlayerID()+" has discarded a " + oldPebble + " from bag " + lastBagDiscarded);
            String string4=("player "+thisPlayer.getPlayerID()+" hand is "+ Arrays.toString(thisPlayer.getPebbles()).replace("[","").replace("]",""));
            try {
                assert writeToPlayerFile != null;
                writeToPlayerFile.write(string1 + "\n");//Write outputs into the test file to describe player actions and pebble array state
                writeToPlayerFile.write(string2+ "\n");
                writeToPlayerFile.write(string3+ "\n");
                writeToPlayerFile.write(string4+ "\n");

                System.out.println("INFO: "+string1);
                System.out.println("INFO: "+string2);
                System.out.println("INFO: "+string3);
                System.out.println("INFO: "+string4);
                writeToPlayerFile.flush();
            } catch (IOException e) {//errors caught here
                System.out.println(e.getMessage());
            }
        }
    }
}
