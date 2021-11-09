import java.util.Arrays;
import java.util.Objects;

//for data output
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Playerthread extends Thread {
    PebbleGame.Player thisPlayer;//we need to pass instance eof player from game into thread
    static boolean wonGame = false;

    public Playerthread(PebbleGame.Player playerfromGame) {
        thisPlayer = playerfromGame;
    }//passes instance eof player into the thread

    public void CreateFile() {
        try {
            File playerFile = new File("Player "+thisPlayer.getPlayerID()+".txt");
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
        System.out.println("INFO: Starting Player: "+ thisPlayer.getPlayerID() + "...");
        CreateFile();//generate a text file for each player
        thisPlayer.generateRandomChoice();//randomly the bag from which 10 pebbles will be chosen from
        PebbleGame.draw10(thisPlayer);//draws first 10 pebbeles
        FileWriter writeToPlayerFile = null;
        try {
            writeToPlayerFile = new FileWriter("Player "+thisPlayer.getPlayerID()+".txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!wonGame) {
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
            //compare two arrays before and after to see what pebble has been discarded and drawn!
            int[] TempPebbleArray = new int[10];
            for (int i = 0;i<=9;i++) {
                TempPebbleArray[i] = thisPlayer.getPebbles()[i];
            }
            thisPlayer.generateRandomChoice();
            if(thisPlayer.getRandomBag()==0){//then go to X
                PebbleGame.drawAndDiscardFromBagX(thisPlayer,false);
            }else if(thisPlayer.getRandomBag()==1){//GO TO Y
                PebbleGame.drawAndDiscardFromBagY(thisPlayer,false);
            }else{//GO TO Z
                PebbleGame.drawAndDiscardFromBagZ(thisPlayer,false);
            }
            int newPebble = 0;
            int oldPebble = 0;
            String lastBagDrawn = "";
            String lastBagDiscarded = "" ;
            for (int i = 0 ;i<=9;i++){
                if(TempPebbleArray[i]!=thisPlayer.getPebbles()[i]){
                    newPebble = thisPlayer.getPebbles()[i];
                    oldPebble = TempPebbleArray[i];
                    break;//as there can only be one change in the array in a given iteration.
                }
            }
            if(Objects.equals(thisPlayer.getLastBagDrawn(), "X")){
                lastBagDrawn = "X";
                lastBagDiscarded = "A";
            }else if(Objects.equals(thisPlayer.getLastBagDrawn(), "Y")){
                lastBagDrawn = "Y";
                lastBagDiscarded = "B";
            }else{//MUST BE Z THEN
                lastBagDrawn = "Z";
                lastBagDiscarded = "C";
            }
            //output this into some texts files
            String string1=("player "+thisPlayer.getPlayerID()+" has drawn a " + newPebble + " from bag " + lastBagDrawn);
            String string2=("player "+thisPlayer.getPlayerID()+" hand is "+ Arrays.toString(TempPebbleArray).replace("[","").replace("]",""));
            String string3=("player "+thisPlayer.getPlayerID()+" has discarded a " + oldPebble + " from bag " + lastBagDiscarded);
            String string4=("player "+thisPlayer.getPlayerID()+" hand is "+ Arrays.toString(thisPlayer.getPebbles()).replace("[","").replace("]",""));
            try {
                assert writeToPlayerFile != null;
                writeToPlayerFile.write(string1 + "\n");
                writeToPlayerFile.write(string2+ "\n");
                writeToPlayerFile.write(string3+ "\n");
                writeToPlayerFile.write(string4+ "\n");

                System.out.println("INFO: "+string1);
                System.out.println("INFO: "+string2);
                System.out.println("INFO: "+string3);
                System.out.println("INFO: "+string4);
                writeToPlayerFile.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
