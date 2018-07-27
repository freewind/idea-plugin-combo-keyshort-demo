package example

import com.intellij.openapi.components.ApplicationComponent
import org.apache.commons.lang.builder.ToStringBuilder
import java.awt.AWTEvent
import java.awt.Component
import java.awt.Toolkit
import java.awt.event.KeyEvent

class HelloComponent : ApplicationComponent {

    private val keyEvents = mutableListOf<KeyEvent>()
    private val sentEvents = mutableListOf<KeyEvent>()

    override fun initComponent() {
        println("idea-plugin-hold-keys-and-send-together-demo:initComponent")
        Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
            when (event) {
                is KeyEvent ->
                    when (event.source) {
                        is com.intellij.openapi.editor.impl.EditorComponentImpl -> {

                            println("--------- ${event.keyChar} : ${event.hashCode()} ---------------")
                            println("keyEvents size: ${keyEvents.size}")
                            val myEvent = sentEvents.any { it === event }
                            if (!myEvent) {
                                event.consume()
                                keyEvents.add(createKeyEvent(event))

                                // each key has 3 events:
                                // KEY_PRESSED
                                // KEY_TYPED
                                // KEY_RELEASED
                                // so the size is 15, and the chars are only 5
                                if (keyEvents.size >= 15) {
                                    val todo = keyEvents.take(15)
                                    repeat(15) { keyEvents.removeAt(0) }

                                    sentEvents.addAll(todo)

                                    todo.forEach { event ->
                                        Toolkit.getDefaultToolkit().systemEventQueue.postEvent(event)
                                    }
                                }
                            }
                        }
                    }
            }
        }, AWTEvent.KEY_EVENT_MASK)
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