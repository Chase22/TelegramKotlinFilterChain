package org.chase.telegram.filterchain

/**
 * A handler in the telegram filter chain. Each handler should either end the chain by returning or call another handler
 */
interface Handler {

    /**
     * The method called by the previous handler to advance the chain. Should call the next handler or return the context directly
     */
    fun call(context: UpdateContext): UpdateContext
}
