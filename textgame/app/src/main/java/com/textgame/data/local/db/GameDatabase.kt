package com.textgame.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.textgame.data.local.db.dao.BackgroundSettingDao
import com.textgame.data.local.db.dao.DialogueDao
import com.textgame.data.local.db.dao.GameStateDao
import com.textgame.data.local.db.dao.NPCDao
import com.textgame.data.local.db.dao.ProtagonistDao
import com.textgame.data.local.db.dao.SessionDao
import com.textgame.data.local.db.dao.StateSnapshotDao
import com.textgame.data.local.db.dao.SummaryDao
import com.textgame.data.local.db.dao.WorldSettingDao
import com.textgame.data.local.db.entity.BackgroundSettingEntity
import com.textgame.data.local.db.entity.DialogueEntity
import com.textgame.data.local.db.entity.GameSessionEntity
import com.textgame.data.local.db.entity.GameStateEntity
import com.textgame.data.local.db.entity.NPCStateEntity
import com.textgame.data.local.db.entity.ProtagonistEntity
import com.textgame.data.local.db.entity.StateSnapshotEntity
import com.textgame.data.local.db.entity.SummaryEntity
import com.textgame.data.local.db.entity.WorldSettingEntity

@Database(
    entities = [
        GameSessionEntity::class,
        ProtagonistEntity::class,
        NPCStateEntity::class,
        GameStateEntity::class,
        SummaryEntity::class,
        WorldSettingEntity::class,
        BackgroundSettingEntity::class,
        DialogueEntity::class,
        StateSnapshotEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun protagonistDao(): ProtagonistDao
    abstract fun npcDao(): NPCDao
    abstract fun gameStateDao(): GameStateDao
    abstract fun summaryDao(): SummaryDao
    abstract fun worldSettingDao(): WorldSettingDao
    abstract fun backgroundSettingDao(): BackgroundSettingDao
    abstract fun dialogueDao(): DialogueDao
    abstract fun stateSnapshotDao(): StateSnapshotDao
}
