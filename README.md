# BetterQuizzesForEvents
BetterQuizzesForEvents is a Minecraft plugin for Bukkit/Spigot servers. It allows server administrators to create and manage quizzes for in-game events. Players answer quiz questions by standing on colored blocks that correspond to their chosen answer.

## Features
Add quiz questions with multiple choices and colored blocks representing each choice.
Display questions as titles on the screen for all players.
Exempt specific players from quizzes.
Display a timer counting down the quiz duration.
Toggle between survival and spectator mode on death.
Set a custom death ban message.
## Commands
/addquestion <question> <choice1> <choice2> <choice3> <choice4> <question-number> <choice1color> <choice2color> <choice3color> <choice4color> <correct-answer>: Adds a question to the quiz.
/question <question-number>: Displays a question with the specified question number to all players.
/deathban: Toggles death bans.
/setdeathbanmessage <message>: Sets the death ban message.
/togglespectatorondeath: Toggles between survival and spectator mode on death.
/exempt <player>: Exempts a player from quizzes.
/help: Shows help and usage information for BetterQuizzesForEvents.
## Permissions
betterquizsforevents.addquestion: Allows access to the /addquestion command (default: op).
betterquizsforevents.question: Allows access to the /question command (default: op).
betterquizsforevents.deathban: Allows access to the /deathban command (default: op).
betterquizsforevents.setdeathbanmessage: Allows access to the /setdeathbanmessage command (default: op).
betterquizsforevents.togglespectatorondeath: Allows access to the /togglespectatorondeath command (default: op).
betterquizsforevents.exempt: Allows access to the /exempt command (default: op).
betterquizsforevents.help: Allows access to the /help command (default: op).
## Installation
Compile the plugin or download the pre-compiled JAR file.
Place the JAR file in the plugins folder of your Bukkit/Spigot server.
Restart the server.
Configure the plugin using the generated config.yml file.
## Configuration
The config.yml file contains the quiz questions, choices, colors, and other settings. Refer to the config.yml file comments and documentation for a complete guide on configuring the plugin.

## Usage
Use the /addquestion command to add quiz questions.
Use the /question command to display questions to players during events.
Use the /exempt command to exempt specific players from quizzes.
Use the /deathban, /setdeathbanmessage, and /togglespectatorondeath commands to manage death-related settings.
Support
For support, please open an issue on the plugin's repository or contact the plugin developer.