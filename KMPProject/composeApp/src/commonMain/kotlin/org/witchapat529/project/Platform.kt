package org.witchapat529.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform