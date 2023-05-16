# commands
A light-weight Bukkit/Spigot command manager that supports annotation-based sub-commands inspired by aikar's Annotation Command Framework.

This project was made so I can improve on my Java & programming skills in general, so please, point out my mistakes.

# Adding the repository (JitPack)
You need to have JitPack in your project:

Maven users:
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

Gradle (Groovy) users:
```gradle
allprojects {
	repositories {
		...
	    maven { url 'https://jitpack.io' }
	}
}
```

After that, you need to add the dependency:

Maven users:
```xml
<dependency>
    <groupId>com.github.themightyfrogge</groupId>
	<artifactId>commands</artifactId>
	<version>v0.1.0-ALPHA</version>
</dependency>
```

Gradle (Groovy) users:
```gradle
dependencies {
    implementation 'com.github.themightyfrogge:commands:v0.1.0-ALPHA'
}
```


# Making the CommandManager instance
In order for the command manager to work, you need to use ``CommandManager.makeInstance(/*Your main instance*/)`` in your onEnable() method.
For example:

```java
// Inside onEnable()'s scope
CommandManager.makeInstance(this);
```

# Making a Demo command!
The demo command is also available in source in commands/plugincommands
```java
public class DemoCommand extends Command {

    public DemoCommand() {
        super("demo", "io.github.themightyfrogge.admin.demo", CommandExecutorType.CONSOLE);
    }

    @Override
    public void execution() {
        getSender().sendMessage("This is a demo for io.github.themightyfrogge's command manager!");
    }
    
    @SubCommand(handle = "info")
    public void info() {
        getSender().sendMessage(
            String.format(
                "Command info:\n" +
                " - Handle: %s\n" +
                " - Permission: %s\n" + 
                " - Allowed Executor Type: %s",
                getHandle(),
                getPermission(),
                getAllowedExecutor().toString().toUpperCase()
            )
        );

        getSender().sendMessage(" - Sub-commands:");
        getSubCommands().forEach(subCommand -> {
            getSender().sendMessage(" o /" + getHandle() + subCommand.getAnnotation(SubCommand.class).handle()); 
            // Hacky way, I know. I store the sub-command methods, so you'd have to get the annotation to get it's name as shown...
        });
    }

}
```

# Registering commands
It's straight forward just add your command to the CommandManager's registeredCommands list.
For example:
```java
/*in onEnable()'s scope*/
CommandManager.getInstance().getRegisteredCommands().addAll(
    List.of(
        new DemoCommand()
    )
);
```
That's all. Just don't forget to add your command to your plugin.yml before going to test it!
