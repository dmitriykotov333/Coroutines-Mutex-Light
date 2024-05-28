package com.laba.lab.data

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ConcurrentHash<K, V> {

    private val map = HashMap<K, V>()
    private val mutex = Mutex()

    suspend fun put(key: K, value: V) {
        mutex.withLock {
            map[key] = value
        }
    }


    suspend fun get(key: K): V? {
        return mutex.withLock {
            map[key]
        }
    }

    suspend fun findKeyByValue(value: V): K? {
        return mutex.withLock {
            map.entries.find { it.value == value }?.key
        }
    }

    suspend fun remove(key: K): V? {
        return mutex.withLock {
            map.remove(key)
        }
    }

    suspend fun change(key: K, value: V) {
        mutex.withLock {
            if (map.containsKey(key)) {
                map[key] = value
            }
        }
    }

    suspend fun size(): Int {
        return mutex.withLock {
            map.size
        }
    }

    suspend fun getMap(): Map<K, V> {
        return mutex.withLock {
            map.toMap()
        }
    }

    suspend fun clear() {
        mutex.withLock {
            map.clear()
        }
    }
}