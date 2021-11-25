package org.chase.telegram.filterchain.handlers

import org.chase.telegram.filterchain.Handler
import org.chase.telegram.filterchain.UpdateContext
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.function.Predicate

/**
 * A handler that routes the given context based on a predicate and the given order
 */
class RoutingHandler(routes: Set<Route>) : Handler {
    val routes = routes.sortedDescending()

    override fun call(context: UpdateContext): UpdateContext {
        return routes.firstOrNull { it.predicate.test(context.update) }?.handler?.call(context) ?: context
    }
}

data class Route @JvmOverloads constructor(
    val handler: Handler,
    val order: Int = 0,
    val predicate: Predicate<Update>
) : Comparable<Route> {
    override fun compareTo(other: Route): Int {
        return this.order.compareTo(other.order)
    }
}