package com.textgame.data.local.db;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&\u00a8\u0006\u0015"}, d2 = {"Lcom/textgame/data/local/db/GameDatabase;", "Landroidx/room/RoomDatabase;", "()V", "backgroundSettingDao", "Lcom/textgame/data/local/db/dao/BackgroundSettingDao;", "dialogueDao", "Lcom/textgame/data/local/db/dao/DialogueDao;", "gameStateDao", "Lcom/textgame/data/local/db/dao/GameStateDao;", "npcDao", "Lcom/textgame/data/local/db/dao/NPCDao;", "protagonistDao", "Lcom/textgame/data/local/db/dao/ProtagonistDao;", "sessionDao", "Lcom/textgame/data/local/db/dao/SessionDao;", "stateSnapshotDao", "Lcom/textgame/data/local/db/dao/StateSnapshotDao;", "summaryDao", "Lcom/textgame/data/local/db/dao/SummaryDao;", "worldSettingDao", "Lcom/textgame/data/local/db/dao/WorldSettingDao;", "app_debug"})
@androidx.room.Database(entities = {com.textgame.data.local.db.entity.GameSessionEntity.class, com.textgame.data.local.db.entity.ProtagonistEntity.class, com.textgame.data.local.db.entity.NPCStateEntity.class, com.textgame.data.local.db.entity.GameStateEntity.class, com.textgame.data.local.db.entity.SummaryEntity.class, com.textgame.data.local.db.entity.WorldSettingEntity.class, com.textgame.data.local.db.entity.BackgroundSettingEntity.class, com.textgame.data.local.db.entity.DialogueEntity.class, com.textgame.data.local.db.entity.StateSnapshotEntity.class}, version = 3, exportSchema = false)
public abstract class GameDatabase extends androidx.room.RoomDatabase {
    
    public GameDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.SessionDao sessionDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.ProtagonistDao protagonistDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.NPCDao npcDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.GameStateDao gameStateDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.SummaryDao summaryDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.WorldSettingDao worldSettingDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.BackgroundSettingDao backgroundSettingDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.DialogueDao dialogueDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.textgame.data.local.db.dao.StateSnapshotDao stateSnapshotDao();
}