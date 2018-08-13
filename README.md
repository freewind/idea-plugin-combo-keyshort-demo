Idea Plugin Combo Keyshort Demo
===============================

You can press some keys at the same time, the matched words will be shown and input to editor, which makes us coding fast.

The prototype is OK, but it has much of work to do to make it work perfectly.

![demo](./demo.gif)

In the demo screencast, the chars are pressed at the same time, not one by one.
Say, for `pbu`, you use three fingers to press `p`, `b`, `u` at the same time, so the order is not important.

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
