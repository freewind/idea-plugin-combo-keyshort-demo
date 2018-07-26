Idea Plugin Block Key Pressing Demo
===================================

Some of your keys(`x` in this demo) is blocked, you can't type it to editor.

Run plugin in IDEA
------------------

```
./gradlew runIde
```

Create any file, and type something into it, you will find you can't type char `x`, because it's consumed by this plugin.

Build the plugin to a zip
-------------------------

```
./gradlew buildPlugin
```

It will generate the plugin file in `./build/distributions`

Notice
-------

- Use `version 'IC-2018.1.5'` every time to reduce download. (about 500M)
- Don't forget to change `id` and `name` in `resources/META-INF/plugin.xml` to current project