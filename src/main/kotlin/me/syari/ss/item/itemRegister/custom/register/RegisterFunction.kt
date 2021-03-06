package me.syari.ss.item.itemRegister.custom.register

interface RegisterFunction {
    fun register()

    companion object {
        private val list = mutableSetOf<RegisterFunction>()

        fun registerAll() {
            list.forEach {
                it.register()
            }
        }

        fun add(vararg registerFunction: RegisterFunction) {
            list.addAll(registerFunction)
            registerFunction.forEach {
                it.register()
            }
        }
    }
}