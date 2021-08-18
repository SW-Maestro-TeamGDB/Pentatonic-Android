package com.team_gdb.pentatonic.data.model

import com.team_gdb.pentatonic.data.session.SessionSetting
import java.io.Serializable

data class SessionSettingEntity(val sessionSetting: SessionSetting, var count: Int): Serializable
