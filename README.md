A 2D platformer game.

How to start the application ?

	Execute the main method situated into the gameLauncher class (no parameters required)

--------The controls------------

		In the menus:
		
			-> directional arrows : moving in the menu
			-> enter key : select the menu
			-> escape key : go back to the main menu
			
		In the game:
		
			-> directional arrows : move the player on the screen
			-> space key : makes the player jump
			-> escape key : go back to the main menu

--------Adding a new level-------------

If you want to add a new level to the game, you need to follow these steps:
    1- First, go to the folder "Levels"
    2- Create a new file labeled "leveln.txt" where n represent the level index (starting at 1)
    3- It's in this file that you will be writing your levels : 
        3.1- On the next lines, draw you level using the corresponding symbols:
            x -> an obstacle
            s -> a strawberry (collectible)
            p -> player starting position
            e -> end of the level
    4- open the file labeled "LevelNames.txt"
    5- This file contains the level name and its description separated by a coma where each line equals a level, so add a name and description for the new level
    6- Your level will be in the game
    
--------------------------

Members:
-> Tartare Antoine
-> Teyssere Karina
-> Scheidt Sophie
-> Menny Nathan