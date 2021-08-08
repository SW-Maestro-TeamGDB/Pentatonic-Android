package com.team_gdb.pentatonic.data.model

import com.team_gdb.pentatonic.ui.create_cover.session_setting.SessionSetting
import java.io.Serializable

data class SessionSettingEntity(val sessionSetting: SessionSetting, var count: Int): Serializable
