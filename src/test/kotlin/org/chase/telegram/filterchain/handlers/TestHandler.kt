package org.chase.telegram.filterchain.handlers

import org.chase.telegram.filterchain.Handler
import org.chase.telegram.filterchain.UpdateContext

class TestHandler: Handler {
    override fun call(context: UpdateContext): UpdateContext = context
}