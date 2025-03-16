package com.krypton.flashbeam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Battery
import compose.icons.tablericons.Battery1
import compose.icons.tablericons.Battery2
import compose.icons.tablericons.Battery3
import compose.icons.tablericons.Battery4
import compose.icons.tablericons.BatteryCharging

class BatteryView : ViewModel() {
    private val _percentage = mutableIntStateOf(0)
    private val _charging = mutableStateOf(false)
    val percentage: State<Int> = _percentage
    fun updateBattery(level: Int, charging: Boolean) {
        _percentage.intValue = level
        _charging.value = charging
    }

    fun icon(): ImageVector {
        return when {
            _charging.value -> TablerIcons.BatteryCharging
            _percentage.intValue > 74 -> TablerIcons.Battery4
            _percentage.intValue > 49 -> TablerIcons.Battery3
            _percentage.intValue > 24 -> TablerIcons.Battery2
            _percentage.intValue > 0 -> TablerIcons.Battery1
            else -> TablerIcons.Battery
        }
    }
}

class BatteryBroadcast(private val viewModel: BatteryView) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val charging = status == BatteryManager.BATTERY_STATUS_CHARGING
            viewModel.updateBattery(level, charging)
        }
    }
}