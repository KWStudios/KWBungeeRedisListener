# KWBungeeRedisListener
This is the java application which parses the messages for the MinigameCreation Redis channel

This java application listens on the given Redis channels for incoming minigame creation messages.
It parses each request and runs the given command if the requested Server is a local server.

This project is compatible with our KWBungeeLobby and KWBungeeMinigame plugins, but you should only use these projects if you
know what you are doing.

We will ***never*** give you any kind of support for plugins or applications for Minecraft Servers which are not published on
either SpigotMC, BukkitDev or the Minecraft Forums.
