# Rock Paper Scissors Game!
### Introduction:
A turn-based game of rock-paper-scissors that takes user input via REST API, plays for the computer, and then declares the winner. For more information about the game and how to play it please visit: https://en.wikipedia.org/wiki/Rock_paper_scissors

Note: The machine moves are selected randomly.



## Running the service locally:
- Clone the repository
- Build the application using the following command on the root project: `mvn clean install`
- Two ways to run the application:
	1. A jar file will be create at `./target` folder, copy the file and deploy it to a web server
	2. Simply you can either run the application by executing `/src/main/java/com/callenge/game/application/MainApplication.java` or use the following command: `mvn spring-boot:run`
- From your localhost (http://localhost:8080) you can starting using the API using the below end-point(s).

## RESTful Web Service
### End-Points
##### Description: Takes the user input, plays for the computer and returns the winner and score
##### Mehod: POST
##### Endpoint: /api/v1/game
##### Request Body:
```
{
  "hand": "...", # Required: String with any of the following (PAPER, ROCK, SCISSORS)
  "userId": "..." # Optional: String and optional
}
```
##### Example:
###### Request
```
{"hand": "PAPER"}
```
###### Response
```
{
    "result": "WON",
    "computerHand": "ROCK",
    "score": {
        "humanPoints": 1,
        "machinePoints": 0
    }
}
```
