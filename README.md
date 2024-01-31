# A 2D platformer game.

You play as a green square and you must reach the blue square in each level which allows you to go to the next level. On your way, you will find red squares representing strawberries; if you collect 10 of them you have one more life !

## Requirements

The application requires java JDK 1.8 and maven to be built.

## Build

To build the project using maven, enter the following command : 

```
mvn compile
```

## Launch

To start the application, the entry class is `game.GameLauncher`. You can enter the following command to start the application with maven : 

```
mvn exec:java
```

## --------The controls------------

In the menus:

	-> directional arrows : moving in the menu
	-> enter key : select the menu
	-> escape key : go back to the main menu (if you are on the main menu it allows you to close the game)
	
In the game:

	-> directional arrows : move the player on the screen
	-> space key : makes the player jump
	-> escape key : go back to the main menu

## --------Adding a new level-------------

If you want to add a new level to the game, you need to follow these steps:
1. First, go to the folder _"Levels"_   
2. Create a new file labeled _"leveln.txt"_ where _n_ represents the level index (starting at 1)   
3. It's in this file that you will be writing your levels :    
4. On the next lines, draw you level using the corresponding symbols:   
	- x -> an obstacle   
	- s -> a strawberry (collectible)   
	- p -> player starting position   
	- e -> end of the level   
5. open the file labeled _"LevelNames.txt"_   
6. This file contains the level name and its description separated by a coma where each line equals a level, so add a name and description for the new level   
7. Your level will be in the game   
   
--------------------------

## Authors:
-> Tartare Antoine  
-> Teyssere Karina  
-> Scheidt Sophie  
-> Menny Nathan  