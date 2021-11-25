package org.chase.telegram.filterchain

import io.undertow.util.AbstractAttachable
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class UpdateContext(val update: Update, val sender: AbsSender): AbstractAttachable()
