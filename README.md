# Goldfish Memory
Android memory-based card game that supports user authentication/login, best score tracking with a cloud database, and leaderboard functionalities.

Login Screen             |  User Profile          |  Game |   Leaderboard
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/eutopi/Goldfish-Memory/blob/master/screenshots/1.png)  |  ![](https://github.com/eutopi/Goldfish-Memory/blob/master/screenshots/3.png) |  ![](https://github.com/eutopi/Goldfish-Memory/blob/master/screenshots/2.png) |  ![](https://github.com/eutopi/Goldfish-Memory/blob/master/screenshots/5.png)

## Details 

**Login page**: authentication handled with Firebase

**Home page**: navigation options include logout, play game, check profile, or view leaderboard

**Profile**: displays user's best (shortest time), number of total games played, and a simple icon. This icon can be changed by selecting from a dialog of 6 custom images.

**Memory game**: shows a grid-like display of face-down cards with a ticking timer
- click on a card to flip it over 
- when two cards are flipped
  - if they are same, both disappear
  - if they are different, both are flipped back over 
- game ends when all cards are flipped

**Leaderboard**: uses RecyclerView and CardView to display the scores of players (stored and loaded using Firebase), sorted from shortest to longest time
