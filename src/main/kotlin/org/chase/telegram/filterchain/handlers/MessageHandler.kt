package org.chase.telegram.filterchain.handlers

import io.undertow.util.AttachmentKey
import org.chase.telegram.filterchain.Handler
import org.chase.telegram.filterchain.UpdateContext
import org.telegram.telegrambots.meta.api.objects.Message

/**
 * A handler to set the message of the current update as an attachment. Best used with the MessageFilterHandler
 * @see MessageFilterHandler
 */
class MessageHandler: Handler {
    lateinit var next: Handler

    override fun call(context: UpdateContext): UpdateContext {
        context.update.message?.let {
            context.putAttachment(messageAttachmentKey, it)
        }
        if (this::next.isInitialized) {
            return next.call(context)
        }
        return context
    }

    companion object {
        val messageAttachmentKey: AttachmentKey<Message> = AttachmentKey.create(Message::class.java)
    }
}
