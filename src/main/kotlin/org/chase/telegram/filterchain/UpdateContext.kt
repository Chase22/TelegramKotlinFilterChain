package org.chase.telegram.filterchain

import io.undertow.util.AbstractAttachable
import org.telegram.telegrambots.meta.api.objects.Update

class UpdateContext(val update: Update): AbstractAttachable()
