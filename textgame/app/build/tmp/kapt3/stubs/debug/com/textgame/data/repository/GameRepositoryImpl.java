package com.textgame.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ac\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b!\b\u0007\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\u0019\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ\u0019\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u0019\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ!\u0010\"\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010#\u001a\u00020$H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J\u0019\u0010&\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u0019\u0010\'\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ!\u0010(\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010#\u001a\u00020$H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J!\u0010)\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010*\u001a\u00020$H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J!\u0010+\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010*\u001a\u00020$H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J\u0014\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020/0.0-H\u0016J\u001b\u00100\u001a\u0004\u0018\u0001012\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001f\u00102\u001a\b\u0012\u0004\u0012\u0002030.2\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001b\u00104\u001a\u0004\u0018\u0001052\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001b\u00106\u001a\u0004\u0018\u0001072\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010!\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ#\u0010:\u001a\u0004\u0018\u0001092\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010;J\u001f\u0010<\u001a\b\u0012\u0004\u0012\u0002090.2\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001c\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090.0-2\u0006\u0010\u001e\u001a\u00020\u0018H\u0016J\u001b\u0010>\u001a\u0004\u0018\u00010?2\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001b\u0010@\u001a\u0004\u0018\u00010/2\u0006\u0010!\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ#\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010C\u001a\u00020$H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J\u001b\u0010D\u001a\u0004\u0018\u00010E2\u0006\u0010\u001e\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u0019\u0010F\u001a\u00020\u00182\u0006\u0010G\u001a\u000201H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010HJ\u0019\u0010I\u001a\u00020\u00182\u0006\u0010J\u001a\u000203H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010KJ\u0019\u0010L\u001a\u00020\u00182\u0006\u0010M\u001a\u000205H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010NJ\u0019\u0010O\u001a\u00020\u00182\u0006\u0010P\u001a\u000209H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\u0019\u0010R\u001a\u00020\u00182\u0006\u0010S\u001a\u00020?H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010TJ\u0019\u0010U\u001a\u00020\u00182\u0006\u0010V\u001a\u00020BH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010WJ\u0019\u0010X\u001a\u00020\u00182\u0006\u0010Y\u001a\u000207H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010ZJ\u0019\u0010[\u001a\u00020\u00182\u0006\u0010G\u001a\u00020EH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\\J\u0019\u0010]\u001a\u00020\u001d2\u0006\u0010G\u001a\u000201H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010HJ!\u0010^\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010*\u001a\u00020$H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%J!\u0010_\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u00182\u0006\u0010`\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010;J\u0019\u0010a\u001a\u00020\u001d2\u0006\u0010M\u001a\u000205H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010NJ\u0019\u0010b\u001a\u00020\u001d2\u0006\u0010P\u001a\u000209H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\u0019\u0010c\u001a\u00020\u001d2\u0006\u0010Y\u001a\u000207H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010ZJ\u0019\u0010d\u001a\u00020\u001d2\u0006\u0010G\u001a\u00020EH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\\J\u0019\u0010e\u001a\u00020\u00182\u0006\u0010Y\u001a\u000207H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010ZR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006f"}, d2 = {"Lcom/textgame/data/repository/GameRepositoryImpl;", "Lcom/textgame/domain/repository/GameRepository;", "sessionDao", "Lcom/textgame/data/local/db/dao/SessionDao;", "protagonistDao", "Lcom/textgame/data/local/db/dao/ProtagonistDao;", "npcDao", "Lcom/textgame/data/local/db/dao/NPCDao;", "gameStateDao", "Lcom/textgame/data/local/db/dao/GameStateDao;", "summaryDao", "Lcom/textgame/data/local/db/dao/SummaryDao;", "worldSettingDao", "Lcom/textgame/data/local/db/dao/WorldSettingDao;", "backgroundSettingDao", "Lcom/textgame/data/local/db/dao/BackgroundSettingDao;", "dialogueDao", "Lcom/textgame/data/local/db/dao/DialogueDao;", "stateSnapshotDao", "Lcom/textgame/data/local/db/dao/StateSnapshotDao;", "(Lcom/textgame/data/local/db/dao/SessionDao;Lcom/textgame/data/local/db/dao/ProtagonistDao;Lcom/textgame/data/local/db/dao/NPCDao;Lcom/textgame/data/local/db/dao/GameStateDao;Lcom/textgame/data/local/db/dao/SummaryDao;Lcom/textgame/data/local/db/dao/WorldSettingDao;Lcom/textgame/data/local/db/dao/BackgroundSettingDao;Lcom/textgame/data/local/db/dao/DialogueDao;Lcom/textgame/data/local/db/dao/StateSnapshotDao;)V", "gson", "Lcom/google/gson/Gson;", "createGameSession", "", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllNPCs", "", "sessionId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDialogueById", "id", "deleteDialoguesFromTurn", "fromTurn", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNPC", "deleteSession", "deleteStateSnapshotsFromTurn", "deleteSummariesAfterTurn", "turn", "deleteSummariesOverlappingTurn", "getAllSessions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/textgame/domain/model/GameSession;", "getBackgroundSetting", "Lcom/textgame/domain/model/BackgroundSetting;", "getDialogues", "Lcom/textgame/domain/model/Dialogue;", "getGameState", "Lcom/textgame/domain/model/GameState;", "getLatestSummary", "Lcom/textgame/domain/model/Summary;", "getNPCById", "Lcom/textgame/domain/model/NPC;", "getNPCByName", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNPCList", "getNPCs", "getProtagonist", "Lcom/textgame/domain/model/Protagonist;", "getSessionById", "getStateSnapshotByTurn", "Lcom/textgame/domain/model/StateSnapshot;", "turnNumber", "getWorldSetting", "Lcom/textgame/domain/model/WorldSetting;", "saveBackgroundSetting", "setting", "(Lcom/textgame/domain/model/BackgroundSetting;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveDialogue", "dialogue", "(Lcom/textgame/domain/model/Dialogue;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGameState", "gameState", "(Lcom/textgame/domain/model/GameState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveNPC", "npc", "(Lcom/textgame/domain/model/NPC;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveProtagonist", "protagonist", "(Lcom/textgame/domain/model/Protagonist;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveStateSnapshot", "snapshot", "(Lcom/textgame/domain/model/StateSnapshot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSummary", "summary", "(Lcom/textgame/domain/model/Summary;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveWorldSetting", "(Lcom/textgame/domain/model/WorldSetting;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBackgroundSetting", "updateCurrentTurn", "updateDialogueContent", "content", "updateGameState", "updateNPC", "updateSummary", "updateWorldSetting", "upsertSummary", "app_debug"})
public final class GameRepositoryImpl implements com.textgame.domain.repository.GameRepository {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.SessionDao sessionDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.ProtagonistDao protagonistDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.NPCDao npcDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.GameStateDao gameStateDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.SummaryDao summaryDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.WorldSettingDao worldSettingDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.BackgroundSettingDao backgroundSettingDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.DialogueDao dialogueDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.local.db.dao.StateSnapshotDao stateSnapshotDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    public GameRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.SessionDao sessionDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.ProtagonistDao protagonistDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.NPCDao npcDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.GameStateDao gameStateDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.SummaryDao summaryDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.WorldSettingDao worldSettingDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.BackgroundSettingDao backgroundSettingDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.DialogueDao dialogueDao, @org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.dao.StateSnapshotDao stateSnapshotDao) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<java.util.List<com.textgame.domain.model.GameSession>> getAllSessions() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getSessionById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.GameSession> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object createGameSession(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteSession(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateCurrentTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getProtagonist(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Protagonist> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveProtagonist(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Protagonist protagonist, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<java.util.List<com.textgame.domain.model.NPC>> getNPCs(long sessionId) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getNPCList(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.domain.model.NPC>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getNPCById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.NPC> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getNPCByName(long sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.NPC> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveNPC(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.NPC npc, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateNPC(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.NPC npc, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteNPC(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getGameState(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.GameState> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveGameState(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateGameState(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.GameState gameState, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getLatestSummary(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.Summary> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object upsertSummary(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Summary summary, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteSummariesAfterTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteSummariesOverlappingTurn(long sessionId, int turn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteAllNPCs(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getWorldSetting(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.WorldSetting> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveWorldSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateWorldSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.WorldSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getBackgroundSetting(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.BackgroundSetting> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveBackgroundSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateBackgroundSetting(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.BackgroundSetting setting, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getDialogues(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.domain.model.Dialogue>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveDialogue(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.Dialogue dialogue, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateDialogueContent(long id, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteDialogueById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteDialoguesFromTurn(long sessionId, int fromTurn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object saveStateSnapshot(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.StateSnapshot snapshot, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getStateSnapshotByTurn(long sessionId, int turnNumber, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.textgame.domain.model.StateSnapshot> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteStateSnapshotsFromTurn(long sessionId, int fromTurn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}