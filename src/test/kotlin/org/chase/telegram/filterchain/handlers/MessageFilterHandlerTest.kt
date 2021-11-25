package org.chase.telegram.filterchain.handlers

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.*
import org.chase.telegram.filterchain.Handler
import org.chase.telegram.filterchain.UpdateContext
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class MessageFilterHandlerTest : ShouldSpec({
    val updateMock: Update = mockk()
    val handlerMock: Handler = spyk(TestHandler())

    val senderMock: AbsSender = mockk()

    val context = UpdateContext(updateMock, senderMock)

    every { updateMock.hasMessage() } returns true

    val sut = MessageFilterHandler()

    context("MessageHandlerFilter") {
        should("call next handler if update has a message") {
            sut.next = handlerMock
            sut.call(context)

            verify(exactly = 1) { handlerMock.call(context) }
            confirmVerified()
        }

        should("not call next handler if no handler was set") {
            sut.call(context)

            verify(exactly = 0) { handlerMock.call(context) }
            confirmVerified()
        }

        should("not call next handler if update has no message") {
            sut.next = handlerMock

            every { updateMock.hasMessage() } returns false
            sut.call(context)

            verify(exactly = 0) { handlerMock.call(context) }
            confirmVerified()
        }
    }

    isolationMode = IsolationMode.InstancePerTest
})