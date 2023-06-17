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
    <version>0.1.2-ALPHA</version>
</dependency>
```

Gradle (Groovy) users:
```gradle
dependencies {
    implementation 'com.github.themightyfrogge:commands:0.1.2-ALPHA'
}
```

You also need to shade the jar, if you don't do that already, follow these instructions:

Maven users:
```xml
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.4.1</version>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```

Gradle (Groovy) users:
```gradle
plugins {
    id 'com.github.johnrengelman.shadow' version '7.1.0'
}

shadowJar {
    ...
    dependencies {
        compile 'com.github.themightyfrogge:commands:0.1.2-ALPHA'
    }
    ...
}

jar.finalizedBy(shadowJar)
```

# Making the CommandManager instance
In order for the command manager to work, you need to make a ``CommandManager`` instance in your onEnable() method.
For example:

```java
// Inside onEnable()'s scope
CommandManager commandManager = new CommandManager(this);
```

# Making a Demo command!
```java
@CommandProperties(allowedExecutor = CommandExecutorType.CONSOLE, usage = "/demo", description = "A simple demo command")
public class DemoCommand extends Command {

    public DemoCommand() {
        super("demo", "frogge.admin.demo", CommandExecutorType.CONSOLE);
    }

    @Override
    public void execution() {
        getSender().sendMessage("This is a demo for themightyfrogge's command manager!");
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
It's straight forward just add your command to the CommandManager's registeredCommands list, and load all the commands.
For example:
```java
/*in onEnable()'s scope*/
CommandManager.getInstance().getRegisteredCommands().addAll(
    List.of(
        new DemoCommand()
    )
);

CommandManager.getInstance().loadCommands();
```
That's all. Just don't forget to add your command to your plugin.yml before going to test it!
