package example

import com.intellij.openapi.components.ApplicationComponent
import org.apache.commons.lang.StringUtils
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import kotlin.concurrent.thread

data class KeyTyped(val char: Char, val timestamp: Long)

class HelloComponent : ApplicationComponent {

    private val fakeSource = Canvas()

    private val keyTyped = mutableListOf<KeyTyped>()
    // FIXME non-thread-safe
    private val sending = mutableListOf<KeyEvent>()

    private val keywords = listOf(
            "class",
            "Hello",
            "public",
            "static",
            "void",
            "main",
            "System",
            "out",
            "println",
            "world",
            "args",
            "Array",
            "String"
    )

    override fun initComponent() {
        println("idea-plugin-hold-keys-and-send-together-demo:initComponent")
        Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
            when (event) {
                is KeyEvent -> {
                    println("event.source: ${event.source.javaClass}")
                    val type = when (event.id) {
                        KEY_PRESSED -> "KEY_PRESSED"
                        KEY_RELEASED -> "KEY_RELEASED"
                        KEY_TYPED -> "KEY_TYPED"
                        else -> "other"
                    }
                    println("id: ${event.id}, type: $type")
                    when (event.source) {
                        is com.intellij.openapi.editor.impl.EditorComponentImpl -> {
                            println("--------- ${event.keyChar} : ${event.hashCode()} ---------------")
                            println("keyEvents size: ${keyTyped.size}")
                            if (sending.contains(event)) {
                                sending.remove(event)
                            } else {
                                if (Character.isAlphabetic(event.keyChar.toInt())) {
                                    event.consume()
                                    if (event.id == KEY_RELEASED) {
                                        keyTyped.add(KeyTyped(event.keyChar, event.`when`))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK)

        thread(isDaemon = true) {
            while (true) {
                keyTyped.lastOrNull()?.let { event ->
                    val gap = System.currentTimeMillis() - event.timestamp
                    if (gap >= 100) {
                        val todo = keyTyped.toList()
                        keyTyped.clear()

                        if (todo.size >= 3) {
                            val matchedKeyword = keywords.find {
                                val chars = todo.map { it.char }.toSet()
                                it.containsAllCharsOf(chars)
                            }
                            println("matchedKeyword: $matchedKeyword")
                            if (matchedKeyword != null) {
                                matchedKeyword.forEach { c ->
                                    createKeyEvent(KeyTyped(c, System.currentTimeMillis())).forEach { event ->
                                        sending.add(event)
                                        Toolkit.getDefaultToolkit().systemEventQueue.postEvent(event)
                                    }

                                }
                            } else {
                                todo.forEach { charTyped ->
                                    createKeyEvent(charTyped).forEach { event ->
                                        sending.add(event)
                                        Toolkit.getDefaultToolkit().systemEventQueue.postEvent(event)
                                    }
                                }
                            }
                        } else {
                            todo.forEach { charTyped ->
                                createKeyEvent(charTyped).forEach { event ->
                                    sending.add(event)
                                    Toolkit.getDefaultToolkit().systemEventQueue.postEvent(event)
                                }
                            }
                        }
                    } else {
                        Thread.sleep(gap)
                    }
                } ?: Thread.sleep(50)
            }
        }
    }

    private fun createKeyEvent(keyTyped: KeyTyped): List<KeyEvent> {
        val key1 = KeyEvent(
                fakeSource,
                KEY_PRESSED,
                keyTyped.timestamp,
                0,
                KeyEvent.getExtendedKeyCodeForChar(keyTyped.char.toInt()),
                keyTyped.char
        )
        val key2 = KeyEvent(
                fakeSource,
                KEY_TYPED,
                keyTyped.timestamp,
                0,
                KeyEvent.getExtendedKeyCodeForChar(keyTyped.char.toInt()),
                keyTyped.char
        )
        val key3 = KeyEvent(
                fakeSource,
                KEY_RELEASED,
                keyTyped.timestamp,
                0,
                KeyEvent.getExtendedKeyCodeForChar(keyTyped.char.toInt()),
                keyTyped.char
        )
        return listOf(key1, key2, key3)
    }


//    private fun createKeyEvent(event: KeyEvent): KeyEvent {
//        return KeyEvent(
//                fakeSource,
//                event.id,
//                event.`when`,
//                event.modifiers,
//                event.keyCode,
//                event.keyChar,
//                event.keyLocation
//        )
//    }

}


private fun String.containsAllCharsOf(chars: Set<Char>): Boolean {
    return chars.all { this.toLowerCase().contains(it.toLowerCase()) }
}
