package example

import com.intellij.openapi.components.ApplicationComponent
import java.awt.AWTEvent
import java.awt.Component
import java.awt.Toolkit
import java.awt.event.KeyEvent
import kotlin.concurrent.thread

class HelloComponent : ApplicationComponent {

    private val keyEvents = mutableListOf<KeyEvent>()
    // FIXME non-thread-safe
    private val sendingEvents = mutableListOf<KeyEvent>()

    override fun initComponent() {
        println("idea-plugin-hold-keys-and-send-together-demo:initComponent")
        Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
            when (event) {
                is KeyEvent ->
                    when (event.source) {
                        is com.intellij.openapi.editor.impl.EditorComponentImpl -> {
                            println("--------- ${event.keyChar} : ${event.hashCode()} ---------------")
                            println("keyEvents size: ${keyEvents.size}")
                            val myEvent = sendingEvents.remove(event)
                            if (!myEvent) {
                                event.consume()
                                keyEvents.add(createKeyEvent(event))
                            }
                        }
                    }
            }
        }, AWTEvent.KEY_EVENT_MASK)

        thread(isDaemon = true) {
            while (true) {
                keyEvents.lastOrNull()?.let { event ->
                    val gap = System.currentTimeMillis() - event.`when`
                    if (gap >= 1000) {
                        val todo = keyEvents.toList()
                        sendingEvents.addAll(todo)
                        keyEvents.clear()

                        todo.forEach { event ->
                            Toolkit.getDefaultToolkit().systemEventQueue.postEvent(event)
                        }
                    } else {
                        Thread.sleep(gap)
                    }
                } ?: Thread.sleep(50)
            }
        }
    }

    private fun createKeyEvent(event: KeyEvent): KeyEvent {
        return KeyEvent(
                event.source as Component,
                event.id,
                event.`when`,
                event.modifiers,
                event.keyCode,
                event.keyChar,
                event.keyLocation
        )
    }

}