package extensions

import java.util.Locale

fun String.capitalizeWord(locale: Locale) =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }