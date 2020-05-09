package me.syari.ss.item.equip

import me.syari.ss.battle.status.player.StatusType
import me.syari.ss.core.item.CustomItemStack
import me.syari.ss.item.Main.Companion.itemPlugin
import org.bukkit.persistence.PersistentDataType

open class EnhancedEquipItem(
    open val data: EquipItem, val enhance: Int, var enhancePlus: Int
) {
    open val statusChange = mapOf<StatusType, Pair<String, Float>>()

    private val sumEnhance
        get(): Int {
            val sumEnhance = enhance + enhancePlus
            return if (100 < sumEnhance) {
                100
            } else {
                sumEnhance
            }
        }

    val enhanceRate
        get(): Float {
            val rateBegin = 0.8F
            val rateEnd = 1.0F
            return ((rateEnd - rateBegin) * (sumEnhance / 100F)) + rateBegin
        }

    val itemStack: CustomItemStack
        get() = data.itemStack.apply {
            display += "&7| &6$sumEnhance(+$enhancePlus)"
            lore.add("")
            statusChange.forEach { (statusType, value) ->
                lore.add("${statusType.display}: ${value.first}")
            }
            editPersistentData(itemPlugin) {
                set(enhancePersistentDataKey, PersistentDataType.INTEGER, enhance)
                set(enhancePlusPersistentDataKey, PersistentDataType.INTEGER, enhancePlus)
            }
        }

    companion object {
        const val enhancePersistentDataKey = "ss-item-equip-enhance"
        const val enhancePlusPersistentDataKey = "ss-item-equip-enhance-plus"

        val CustomItemStack.enhance
            get() = getPersistentData(itemPlugin)?.get(enhancePersistentDataKey, PersistentDataType.INTEGER) ?: 0

        val CustomItemStack.enhancePlus
            get() = getPersistentData(itemPlugin)?.get(enhancePlusPersistentDataKey, PersistentDataType.INTEGER) ?: 0
    }
}