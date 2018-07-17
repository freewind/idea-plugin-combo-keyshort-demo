Idea Plugin Listen Any Key Pressing Demo
========================================

Listen on all kinds of key events, like:

```
event: java.awt.event.KeyEvent[
           KEY_RELEASED,
           keyCode=65,
           keyText=A,
           keyChar='a',
           keyLocation=KEY_LOCATION_STANDARD,
           rawCode=0,
           primaryLevelUnicode=0,
           scancode=0,
           extendedKeyCode=0x41]
       on EditorComponent file=file:///Users/freewind/IdeaProjects/untitled5/src/a.java
```

or

```
event: java.awt.event.KeyEvent[
           KEY_PRESSED,
           keyCode=86,
           keyText=V,
           keyChar='v',
           keyLocation=KEY_LOCATION_STANDARD,
           rawCode=0,
           primaryLevelUnicode=0,
           scancode=0,
           extendedKeyCode=0x56]
       on javax.swing.JTextField[
           ,
           0,
           21,
           346x26,
           layout=javax.swing.plaf.basic.BasicTextUI$UpdateHandler,
           alignmentX=0.0,
           alignmentY=0.0,
           border=com.intellij.ide.ui.laf.intellij.MacIntelliJTextBorder@100e7194,
           flags=296,
           maximumSize=,
           minimumSize=,
           preferredSize=,
           caretColor=javax.swing.plaf.ColorUIResource[r=0,g=0,b=0],
           disabledTextColor=javax.swing.plaf.ColorUIResource[r=119,g=119,b=119],
           editable=true,margin=com.intellij.util.ui.JBInsets$JBInsetsUIResource[top=0,left=0,bottom=0,right=0],
           selectedTextColor=com.apple.laf.AquaImageFactory$SystemColorProxy[r=0,g=0,b=0],
           selectionColor=com.apple.laf.AquaImageFactory$SystemColorProxy[r=164,g=205,b=255],
           columns=30,
           columnWidth=11,
           command=,
           horizontalAlignment=LEADING]
```

Very powerful :)

You can press keys in anywhere of IDEA (menu, file, editor) to see the output of console.

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