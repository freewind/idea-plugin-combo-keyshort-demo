package example

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import org.apache.commons.lang.builder.ToStringBuilder
import org.jetbrains.java.generate.GenerateToStringUtils
import java.awt.AWTEvent
import java.awt.Component
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.KeyEvent

class HelloComponent : ApplicationComponent {

    override fun initComponent() {
        println("idea-plugin-block-key-pressing-demo: initComponent222")
//        Toolkit.getDefaultToolkit()
        Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
            when (event) {
                is KeyEvent -> {
                    println("event.keyChar: ${event.keyChar}, keyCode: `${event.keyCode}`")
                    if (event.keyChar == 'x') {
                        event.consume()
                        println("${event.keyChar} is consumed")

                        repeat(5) {
                            Toolkit.getDefaultToolkit().systemEventQueue.postEvent(createKeyEvent(event))
                        }
                    }

                    if (event.keyChar == 'y') {
                        event.consume()
                        println("${event.keyChar} is consumed")

                        val robot = Robot()

                        // FIXME not sure why it prints 'c' 10 times
                        repeat(5) {
                            robot.keyPress(67)
                            robot.keyRelease(67)
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
                83,
                's',
                event.keyLocation
        )
    }

}