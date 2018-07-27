Idea Plugin Hold Keys and Send Them Together when not Busy Demo
===============================================================

When you typing in an editor fast, they will not show, and when you stop typing for a while, they will show together.

Two problems in this demo:

1. The `sendingEvents` and `keyEvents` is are thread-safe, I didn't handle it
2. In the new thread, it uses while loop to check if the queue has keys, which has bad performance

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