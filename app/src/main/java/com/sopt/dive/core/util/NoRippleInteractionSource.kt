package com.sopt.dive.core.util

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.emptyFlow

// 버튼과 같은 Modifier.clickable을 사용하지 않을 때 물결 효과를 지우기용
class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions = emptyFlow<Interaction>()
    
    override suspend fun emit(interaction: Interaction) { }
 
    override fun tryEmit(interaction: Interaction): Boolean = true
}