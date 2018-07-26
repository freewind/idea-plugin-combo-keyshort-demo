package example

import com.intellij.openapi.components.ApplicationComponent
import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.KeyEvent

class HelloComponent : ApplicationComponent {
    
    override fun initComponent() {
        println("idea-plugin-block-key-pressing-demo: initComponent")
        Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
            when (event) {
                is KeyEvent -> {
                    println("event.keyChar: ${event.keyChar}")
                    if (event.keyChar == 'x') {
                        event.consume()
                        println("${event.keyChar} is consumed")
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK)
    }

}