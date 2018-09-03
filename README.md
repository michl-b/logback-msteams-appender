This is a simple [Logback](http://logback.qos.ch/) appender which pushes logs to [MsTeams](https://products.office.com/en-us/microsoft-teams/group-chat-software) channel.

[ ![Download](https://api.bintray.com/packages/michl-b/logback-msteams-appender/logback-msteams-appender/images/download.svg) ](https://bintray.com/michl-b/logback-msteams-appender/logback-msteams-appender/_latestVersion)

# How to setup

Add the following settings to you pom.xml.
```xml
    <dependency>
      <groupId>com.github.michl-b</groupId>
      <artifactId>logback-msteams-appender</artifactId>
      <version>1.0.1</version>
    </dependency>

  <repositories>
    <repository>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <id>bintray jcenter</id>
      <name>bintray</name>
      <url>https://jcenter.bintray.com</url>
    </repository>
  </repositories>
```

Add MsTeamsAppender configuration to logback.xml or logback-spring.xml file.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
  ...

  <appender name="MSTEAMS" class="de.smartthingssimple.basar.logback.MsTeamsAppender">
    <appName>AppName</appName>
    <webhookUri>your web hook from ms teams</webhookUri>
  </appender>
  
  <appender name="ASYNC_MSTEAMS" class="ch.qos.logback.classic.AsyncAppender">
    <appender-ref ref="MSTEAMS" />
    <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
      <level>WARN</level>
    </filter>
  </appender>

  <root level="INFO">
    <appender-ref ref="ASYNC_MSTEAMS" />
  </root>

</configuration>
```

# build a release
Publish artifact in a new version to bintray [https://bintray.com/michl-b/logback-msteams-appender/logback-msteams-appender](https://bintray.com/michl-b/logback-msteams-appender/logback-msteams-appender)

```bash
mvn release:prepare
mvn release:perform
```
