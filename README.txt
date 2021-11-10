The test suite utilised JUnit4 to perform testing on each method of the code for the pebble game.
The code coverage is high as only a few methods are not tested;Those method name not tested are as follows:
-->start_game() in PebbleGame.
The methods are tested in the order the PebbleGame code is run so if the dependency method works then we can test other moethods which have verified dependencies that we alredy tested and know work
This means the order of testing of methods is important as it makes testing more chornologically sound and easier/more logically strucutured since we test dependencies befroe methods on the most outer layers of the program code.


