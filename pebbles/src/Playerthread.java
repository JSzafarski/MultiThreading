import java.util.Arrays;
import java.util.Objects;

//for data output
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Playerthread extends Thread {
    PebbleGame.Player ThisPlayer;//we need to pass instance eof player from game into thread

    public Playerthread(PebbleGame.Player PlayerfromGame) {
        ThisPlayer = PlayerfromGame;
    }//passes instance eof player into the thread

    public void CreateFile() {
        try {
            File PlayerFile = new File("Player "+ThisPlayer.getPlayerID()+".txt");
           if(PlayerFile.createNewFile()){
               System.out.println("File created! for player :"+ThisPlayer.getPlayerID());
           }else{
               System.out.println("File does already exist for player : "+ThisPlayer.getPlayerID());
           }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void run() {//this will run each player
        CreateFile();//generate a text file for each player
        ThisPlayer.calculateTotalWeight();
        ThisPlayer.GenerateRandomChoice();//randomly the bag from which 10 pebbles will be chosen from
        PebbleGame.draw10(ThisPlayer);//draws first 10 pebbeles
        boolean Won = false;
        FileWriter WriteToPlayerFile = null;
        try {
            WriteToPlayerFile = new FileWriter("Player "+ThisPlayer.getPlayerID()+".txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!Won) {
            if (ThisPlayer.getTotalWeight() == 100){
                Won = true;
                System.out.println("player: "+ ThisPlayer.getPlayerID()+" Has won!");
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
                TempPebbleArray[i] = ThisPlayer.getPebbles()[i];
            }
            ThisPlayer.GenerateRandomChoice();
            if(ThisPlayer.getRandomBag()==0){//then go to X
                PebbleGame.drawAndDiscardFromBagX(ThisPlayer,false);
            }else if(ThisPlayer.getRandomBag()==1){//GO TO Y
                PebbleGame.drawAndDiscardFromBagY(ThisPlayer,false);
            }else{//GO TO Z
                PebbleGame.drawAndDiscardFromBagZ(ThisPlayer,false);
            }
            int NewPebble = 0;
            int OldPebble = 0;
            String LastBagDrawn = "";
            String LastBagDiscarded = "" ;
            for (int i = 0 ;i<=9;i++){
                if(TempPebbleArray[i]!=ThisPlayer.getPebbles()[i]){
                    NewPebble = ThisPlayer.getPebbles()[i];
                    OldPebble = TempPebbleArray[i];
                    break;//as there can only be one change in the array in a given iteration.
                }
            }
            if(Objects.equals(ThisPlayer.getLastBagDrawn(), "X")){
                LastBagDrawn = "X";
                LastBagDiscarded = "A";
            }else if(Objects.equals(ThisPlayer.getLastBagDrawn(), "Y")){
                LastBagDrawn = "Y";
                LastBagDiscarded = "B";
            }else{//MUST BE Z THEN
                LastBagDrawn = "Z";
                LastBagDiscarded = "C";
            }
            //output this into some texts files
            String String1=("player "+ThisPlayer.getPlayerID()+" has drawn a " + NewPebble + " from bag " + LastBagDrawn);
            String String2=("player "+ThisPlayer.getPlayerID()+" hand is "+ Arrays.toString(TempPebbleArray));
            String String3=("player "+ThisPlayer.getPlayerID()+" has discarded a " + OldPebble + " from bag " + LastBagDiscarded);
            String String4=("player "+ThisPlayer.getPlayerID()+" hand is "+ Arrays.toString(ThisPlayer.getPebbles()));
            try {
                assert WriteToPlayerFile != null;
                WriteToPlayerFile.write(String1 + "\n");
                WriteToPlayerFile.write(String2+ "\n");
                WriteToPlayerFile.write(String3+ "\n");
                WriteToPlayerFile.write(String4+ "\n");
                System.out.println(String1);
                System.out.println(String2);
                System.out.println(String3);
                System.out.println(String4);
                WriteToPlayerFile.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
