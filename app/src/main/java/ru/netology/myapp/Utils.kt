package ru.netology.myapp

object Utils {
    fun figures(quantityContainer: Int): String {
        return when (quantityContainer) {
            in 0..999 -> quantityContainer.toString()
            in 1000..9999 -> String.format("%.1f", (quantityContainer / 1000.0)) + "K"
            in 10_000..999_999 -> "${(quantityContainer / 1000)}K"
            in 1_000_000..999_999_999 -> String.format("%.1f", (quantityContainer / 1_000_000.0)) + "M"
            else -> "Недопустимое число"
        }
    }
}

