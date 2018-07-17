package example

import com.intellij.openapi.components.ApplicationComponent
import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.KeyEvent

class HelloComponent : ApplicationComponent {
    override fun initComponent() {
        println("idea-plugin-key-pressing-listener-demo: initComponent")
        Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
            when (event) {
                is KeyEvent -> {
                    println("----------------- key event -----------------")
                    println("event.id: ${event.id}, ${typeOf(event)}")
                    println("event.isActionKey: ${event.isActionKey}")
                    println("event.keyChar: ${event.keyChar}")
                    println("event.keyCode: ${event.keyCode}")
                    println("event.extendedKeyCode: ${event.extendedKeyCode}")
                    println("event.isShiftDown: ${event.isShiftDown}")
                    println("event.isAltDown: ${event.isAltDown}")
                    println("event.isControlDown: ${event.isControlDown}")
                    println("event.isMetaDown: ${event.isMetaDown}")
                    println("event.isAltGraphDown: ${event.isAltGraphDown}")
                    println("event.keyLocation: ${event.keyLocation}")
                    println("event.source: ${event.source.javaClass}")
                }
            }
        }, AWTEvent.KEY_EVENT_MASK)
    }

    private fun typeOf(event: KeyEvent): String {
        return when (event.id) {
            KeyEvent.KEY_TYPED -> "KEY_TYPED"
            KeyEvent.KEY_PRESSED -> "KEY_PRESSED"
            KeyEvent.KEY_RELEASED -> "KEY_RELEASED"
            else -> "<other>"
        }
    }
}