package com.sopt.dive.core.util

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

object PasswordOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        originalText.indices.forEach { index ->
            replace(index, index + 1, "*")
        }
    }
}