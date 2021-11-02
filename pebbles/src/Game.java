import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//we need to counteract starvation
public class Game {
    static Player[] playerList;
    static Thread[] threadList;

    //instantiating black bags
    static Bag bagX = new Bag("BLACK");
    static Bag bagY = new Bag("BLACK");
    static Bag bagZ = new Bag("BLACK");
    //instantiating white bags
    static Bag bagA = new Bag("WHITE");
    static Bag bagB = new Bag("WHITE");
    static Bag bagC = new Bag("WHITE");

    //method for setting bag pairs of bags
    public static void setBagPairs() {
        bagX.setBagPair(bagA);
        bagY.setBagPair(bagB);
        bagZ.setBagPair(bagC);
        bagA.setBagPair(bagX);
        bagB.setBagPair(bagY);
        bagC.setBagPair(bagZ);
    }

    public static void  start_game(){//if the under eneters e then the program must exit.
        int numPlayers;//number of players
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        setBagPairs();
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated inger values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over.");
        System.out.println("");
        while (true){//loops until use exits or enters correct information so the program can proceed
            System.out.println("Please enter the number of players:");
            numPlayers = Integer.parseInt(System.console().readLine());
            int totalPebbles = 0;
            try {
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i <= 3; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");
                        file_input = System.console().readLine();//verify data type
                        if (Objects.equals(file_input, "X")||(Objects.equals(file_input, "E"))){
                            if((Objects.equals(file_input, "E"))){
                                System.exit(0);
                            }
                            break;//exit the loop
                        } else { //check if the input is valid using try catch block
                            try{
                                pebbles.addAll(read_csv(file_input));//populates the pebbles temp array
                                if(pebbles.size()<10){//if some files contains less than 10 pebbles each then throw error as its below spec
                                    throw new InvalidfileExeption("file number" + i + " has to have at least 10 pebbles");
                                }else{
                                    switch (i) {//uses the for loop to identify which bag is being added to
                                        case 1 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagX.setPebbles(pebbles);
                                            }
                                        case 2 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagY.setPebbles(pebbles);
                                            }
                                        case 3 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagZ.setPebbles(pebbles);
                                        }
                                        default -> {
                                            pebbles.clear();
                                        }
                                    }
                                }
                            } catch (InvalidfileExeption e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (totalPebbles < calculate_minPebbles(numPlayers)) {//not enough total pebbles to execute the game
                        System.out.println("there is not enough pebbles in total form each csv file,please increase that amount");
                    } else {
                        RunPlayers(numPlayers);//creates players for the game to execute
                        break;//all conditions have been met so the program my proceed
                    }
                } else {
                    System.out.println("invalid input ,enter again!");//shows error
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println(e.getMessage());//shows error
            }
        }
    }

    public static ArrayList<Integer> read_csv (String filename) throws IOException,InvalidfileExeption {//validate file name when calling the method
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        String StringOfNumbers;
        Scanner scanner;
        int errorCount = 0;
        int temporaryint  = 0;
        String text = "";
        String ErrorString ="We have detected problems inside the file as follows: ";
        try {
            File file = new File(filename); // java.io.File(source file with text)
            scanner = new Scanner(file);    // java.util.Scanner
            while(scanner.hasNextLine()){//this loop is going to be used to read a text file (each line)
                StringOfNumbers = scanner.nextLine();
                int x = 0;
                StringBuilder CurrentString = new StringBuilder();
                while(x < StringOfNumbers.length() + 1) {//might need fixing
                    if(!String.valueOf(StringOfNumbers.charAt(x)).equals(",")){//fix
                        if(Character.isDigit(StringOfNumbers.charAt(x))){//check if it's a digit
                            CurrentString.append(StringOfNumbers.charAt(x));
                        }else{
                            errorCount++;
                            ErrorString=ErrorString.concat("type error on line: " + x +" ," );
                            //throw error as it's not a character
                        }
                    }else{
                        if(CurrentString.toString().equals("")){
                            ErrorString=ErrorString.concat("empty entry on line: " + x +" ," );
                            errorCount++;
                        }else{
                            temporaryint = Integer.parseInt(CurrentString.toString());
                            if(temporaryint>0){
                                pebbles.add(temporaryint);//adds the item to the list
                            }else{
                                ErrorString=ErrorString.concat("range error on line : " + x +" ," );
                                errorCount++;
                            }
                            CurrentString.setLength(0);
                        }
                        //save the number into an array and proceed gathering more numbers
                    }
                    CurrentString.append(StringOfNumbers.charAt(x));
                    x++;
                }
            }
            if(errorCount>0){
                //throw an exception
                throw new InvalidfileExeption(ErrorString.concat("total errors:" + errorCount));
            }else{
                return pebbles;
            }
        } catch (IOException e) {
            System.out.println("We Could not find the file and : " + e);
            throw new InvalidfileExeption("we could not find the file at this file path");
        }
    }

    public int calculate_minPebbles(int players){
        return 11*players;
    }

    public static synchronized void drawAndDiscard(Player thisPlayer, boolean JustDrawTen) {//method that draws a pebble and then discards the pebble into the next bag in the discard queue will also refill a bag if found to be empty
        //has to be atomic(MAKE IT NEXT)
        //made th fucntion also draw out
        Random rand = new Random();
        int num = rand.nextInt(3);//if it picks 10 it will pick 10 frm the same bag 10 times othwise its a random bag each time its called
        int replacementpebble = -1;
        int NumberOfIterations = 0;
        int[] TenPebbles = new int[10];
        if (JustDrawTen) {
            NumberOfIterations = 10;
        } else {
            NumberOfIterations = 1;
        }
        for (int i = 1; i <= NumberOfIterations; i++) {
            switch (num) {//num randomly generates a number to randomly enter a case which represent randomly picking a bag
                case 0 -> {
                    replacementpebble = bagX.drawPebble();
                    if (replacementpebble == -1) {//when bag is empty refill and can call this function recursively to continue the process of attempting to draw from a random bag
                        bagX.refillBag();
                        drawAndDiscard(thisPlayer,false);
                    } else {//the drawPebble method was successful and a pointer to a bag that is in line to be discarded into is added to the queue
                        if(JustDrawTen){
                            TenPebbles[i] = bagX.drawPebble();
                        }else{
                            bagX.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                            thisPlayer.lastBagDrawn("X");
                        }
                        //make the user pick up a replacement pebble from black bag
                        //discard a pebble from player into the right white bag
                        //update the new pebble array in player
                    }
                }
                case 1 -> {
                    replacementpebble = bagY.drawPebble();
                    if (replacementpebble == -1) {//when bag is empty refill and can call this function recursively to continue the process of attempting to draw from a random bag
                        bagY.refillBag();
                        drawAndDiscard(thisPlayer,false);
                    } else {//the drawPebble method was successful and a pointer to a bag that is in line to be discarded into is added to the queue
                        if(JustDrawTen){
                            TenPebbles[i] = bagX.drawPebble();
                        }else{
                            bagY.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                            thisPlayer.lastBagDrawn("Y");
                        }
                    }
                }
                case 2 -> {
                    replacementpebble = bagZ.drawPebble();
                    if (replacementpebble == -1) {//when bag is empty refill and can call this function recursively to continue the process of attempting to draw from a random bag
                        bagZ.refillBag();
                        drawAndDiscard(thisPlayer,false);
                    } else {//the drawPebble method was successful and a pointer to a bag that is in line to be discarded into is added to the queue
                        if(JustDrawTen){
                            TenPebbles[i] = bagX.drawPebble();
                        }else{
                            bagZ.getBagPair().discardPebble(thisPlayer.replacePebble(replacementpebble));
                            thisPlayer.lastBagDrawn("Z");
                        }
                    }
                }
            }
         }
        if(JustDrawTen){
            thisPlayer.setPebbles(TenPebbles);
        }

    }

    public static synchronized void draw10(Player thisPlayer){//function for drawing a player's first 10 pebbles
        drawAndDiscard(thisPlayer,true);
    }

    public void RunPlayers(int numPlayers){
        threadList = new Thread[numPlayers-1];
        //creates each player object and thread for the specified number of players
        for(int i = 0; i <= numPlayers-1; i++){
            playerList[i] = new Player(1000+i);
            threadList[i] = new Playerthread(playerList[i]);//pass aparamater (a player)
            threadList[i].start();
        }
    }

}
