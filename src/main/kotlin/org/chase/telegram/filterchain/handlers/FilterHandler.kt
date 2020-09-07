package org.chase.telegram.filterchain.handlers

import org.chase.telegram.filterchain.Handler
import org.chase.telegram.filterchain.UpdateContext

/**
 * Handler that will reject updates and end the chain if {@link #filter(UpdateContext)} returns false
 */
abstract class FilterHandler: Handler {
    lateinit var next: Handler

    override fun call(context: UpdateContext): UpdateContext {
        if (filter(context) && this::next.isInitialized) {
            return next.call(context)
        }

        return context
    }

    abstract fun filter(context: UpdateContext): Boolean

}

/**
 * A Filter handler to reject updates that do not contain a message
 */
class MessageFilterHandler: FilterHandler() {
    override fun filter(context: UpdateContext): Boolean {
        return context.update.hasMessage()
    }
}
