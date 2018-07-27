Idea Plugin Combo Keyshort Demo
===============================

You can press some keys at the same time, the matched words will be shown and put, which makes us coding fast.

The prototype is OK, but it has much of work to do to make it work perfectly.

Run plugin in IDEA
------------------

```
./gradlew runIde
```

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