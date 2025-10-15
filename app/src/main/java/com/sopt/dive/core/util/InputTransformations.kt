package com.sopt.dive.core.util

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.delete

// Todo : 일단은 길이 제한
object IdInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (length > 10) delete(10, length)
    }
}

object PasswordInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (length > 12) delete(12, length)

    }
}

object NicknameInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (length > 20) delete(20, length)
    }
}