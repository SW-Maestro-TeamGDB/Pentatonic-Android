package com.team_gdb.pentatonic.ui.create_cover

import com.team_gdb.pentatonic.R

enum class SessionSetting(val sessionName: String, val icon: Int) {
    VOCAL("vocal", R.drawable.ic_vocal),
    ACOUSTIC_GUITAR("acoustic_guitar", R.drawable.ic_acoustic_guitar),
    ELECTRIC_GUITAR("electric_guitar", R.drawable.ic_electric_guitar),
    BASS("bass", R.drawable.ic_bass),
    DRUM("drum", R.drawable.ic_drum),
    KEYBOARD("keyboard", R.drawable.ic_keyboard),
    VIOLIN("violin", R.drawable.ic_violin),
    CELLO("cello", R.drawable.ic_cello),
    GAYAGEUM("gayaguem", R.drawable.ic_gayaguem),
    HAEGUEM("haeguem", R.drawable.ic_haeguem),
    LYRE("lyre", R.drawable.ic_gayaguem)
}