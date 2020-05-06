package me.syari.ss.item.equip.weapon.melee

import me.syari.ss.item.custom.ItemType
import me.syari.ss.item.equip.weapon.WeaponType
import org.bukkit.Material

open class SwordItem(
    override val id: String,
    override val material: Material,
    override val display: String,
    override val description: String
): MeleeItem {
    override val itemType = ItemType.Weapon(WeaponType.Sword)
}