package me.syari.ss.item.itemRegister.custom

import me.syari.ss.core.item.CustomItemStack
import me.syari.ss.item.ItemRarity
import me.syari.ss.item.Main.Companion.itemPlugin
import me.syari.ss.item.itemRegister.custom.register.ItemRegister
import me.syari.ss.item.itemRegister.custom.register.RegisterFunction
import org.bukkit.Material
import org.bukkit.persistence.PersistentDataType

interface CustomItem {
    val id: String
    val material: Material
    val display: String
    val itemType: ItemType
    val description: String
    val rarity: ItemRarity?

    val itemStack: CustomItemStack
        get() = CustomItemStack.create(
            material,
            display,
            "&6アイテムタイプ: ${itemType.color}${itemType.display}",
            "",
            *description.lines().map { "&7$it" }.toTypedArray()
        ).apply {
            editPersistentData(itemPlugin) {
                set(itemIdPersistentKey, PersistentDataType.STRING, id)
            }
        }

    fun register()

    companion object {
        private const val itemIdPersistentKey = "ss-item-id"

        fun getId(item: CustomItemStack): String? {
            return item.getPersistentData(itemPlugin)?.get(itemIdPersistentKey, PersistentDataType.STRING)
        }

        fun from(id: String): CustomItem? {
            return ItemRegister.getCustomItem(id)
        }

        fun from(item: CustomItemStack): CustomItem? {
            return getId(item)?.let {
                from(it)
            }
        }

        fun reload() {
            ItemRegister.clearAll()
            RegisterFunction.registerAll()
        }
    }
}