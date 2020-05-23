package me.syari.ss.item.compass

import me.syari.ss.item.ItemRarity
import me.syari.ss.item.custom.CustomItem
import me.syari.ss.item.custom.ItemType
import me.syari.ss.item.custom.register.ItemRegister
import org.bukkit.Location
import org.bukkit.Material

data class CompassItem(
    override val id: String, val teleportToName: String, val teleportTo: Location
): CustomItem {
    override val itemType = ItemType.Compass
    override val material = Material.COMPASS
    override val display = "&6コンパス &a$teleportTo"
    override val description = "船を借りることで出来れば、$teleportTo へ行くことが出来る"
    override val rarity: ItemRarity? = null

    override fun register() {
        register(id, this)
    }

    companion object: ItemRegister<CompassItem>() {
        val allCompass get() = idToList.values.toList()
    }
}