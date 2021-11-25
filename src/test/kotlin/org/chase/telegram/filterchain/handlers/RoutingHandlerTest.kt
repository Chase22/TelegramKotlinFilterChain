package org.chase.telegram.filterchain.handlers

import io.kotest.core.spec.style.ShouldSpec
import io.mockk.*
import org.chase.telegram.filterchain.Handler
import org.chase.telegram.filterchain.UpdateContext
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class RoutingHandlerTest : ShouldSpec({
    lateinit var updateMock: Update
    lateinit var handlerMock: Handler
    lateinit var otherHandlerMock: Handler

    val senderMock: AbsSender = mockk()

    lateinit var context: UpdateContext

    beforeTest {
        updateMock = mockk()
        handlerMock = spyk(TestHandler())
        otherHandlerMock = spyk(TestHandler())

        context = UpdateContext(updateMock, senderMock)

        every { updateMock.hasMessage() } returns true
        every { updateMock.hasChannelPost() } returns false
    }

    context("Routing Handler") {
        should("route to a handler if the predicate matched") {
            val handler = RoutingHandler(setOf(Route(handlerMock) { it.hasMessage() }))
            handler.call(context)

            verify(exactly = 1) { handlerMock.call(context) }
            confirmVerified(handlerMock)
        }

        should("not route to a handler if the predicate didn't match") {
            val handler = RoutingHandler(setOf(Route(handlerMock) { it.hasChannelPost() }))
            handler.call(context)
            verify(exactly = 0) { handlerMock.call(context) }
            confirmVerified(handlerMock)
        }

        should("call the handler with the highest order") {
            val priorityRoute = Route(handlerMock, 1) { it.hasMessage() }
            val route = Route(otherHandlerMock, 0) { it.hasMessage() }

            val handler = RoutingHandler(setOf(route, priorityRoute))

            handler.call(context)
            verify(exactly = 1) { handlerMock.call(context) }
            verify(exactly = 0) { otherHandlerMock.call(any()) }
            confirmVerified(handlerMock)
        }

        should("not call the handler with the higher order if they do not match") {
            val priorityRoute = Route(handlerMock, 1) { it.hasChannelPost() }
            val route = Route(otherHandlerMock, 0) { it.hasMessage() }

            val handler = RoutingHandler(setOf(route, priorityRoute))

            handler.call(context)
            verify(exactly = 0) { handlerMock.call(context) }
            verify(exactly = 1) { otherHandlerMock.call(any()) }
            confirmVerified(handlerMock)
        }
    }
})