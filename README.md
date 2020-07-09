# Artifice
[![Discord](https://img.shields.io/discord/219787567262859264?color=blue&label=Discord)](https://discord.gg/CFaCu97)
[![Bintray](https://api.bintray.com/packages/natanfudge/libs/artifice/images/download.svg)](https://bintray.com/beta/#/natanfudge/libs/artifice?tab=overview)
[![Latest Commit](https://img.shields.io/github/last-commit/natanfudge/artifice)](https://github.com/natanfudge/artifice/commits/master)

A fork of a Minecraft 1.14 library mod for 1.15, for programmatically generated resource files.

- [API Javadoc](https://htmlpreview.github.io/?https://github.com/artificemc/artifice/blob/master/doc/index.html)
- [Project Wiki](https://github.com/natanfudge/artifice/blob/1.16/src/testmod/java/com/swordglowsblue/artifice/test/ArtificeTestMod.java)

Installation: 

```gradle
repositories { 
    // [...]
    jcenter() 
}

dependencies {
  modImplementation "com.lettuce.fudge:artifice:0.8.1+20w28a"
  include "com.lettuce.fudge:artifice:0.8.1+20w28a"
}
```