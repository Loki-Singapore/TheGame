package com.textgame.di;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\bJ\u0006\u0010\u001c\u001a\u00020\u0004J\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u0006J\u0006\u0010 \u001a\u00020\bJ\b\u0010!\u001a\u00020\nH\u0002J\u0006\u0010\"\u001a\u00020\fJ\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u000eJ\u0006\u0010&\u001a\u00020\'J\u0006\u0010(\u001a\u00020\u0010J\u0006\u0010)\u001a\u00020\u0012J\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020-J\u0006\u0010.\u001a\u00020\u0014J\u0006\u0010/\u001a\u000200J\u0006\u00101\u001a\u000202J\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020\u0016J\u0006\u00106\u001a\u00020\u0018J\u0006\u00107\u001a\u000208J\u000e\u00109\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020;R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Lcom/textgame/di/AppModule;", "", "()V", "aiService", "Lcom/textgame/data/remote/ai/AIService;", "createGameUseCase", "Lcom/textgame/domain/usecase/CreateGameUseCase;", "currentSettings", "Lcom/textgame/data/local/SettingsPreferences;", "database", "Lcom/textgame/data/local/db/GameDatabase;", "deleteSessionUseCase", "Lcom/textgame/domain/usecase/DeleteSessionUseCase;", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "generateSummaryUseCase", "Lcom/textgame/domain/usecase/GenerateSummaryUseCase;", "getAllSessionsUseCase", "Lcom/textgame/domain/usecase/GetAllSessionsUseCase;", "sendDialogueUseCase", "Lcom/textgame/domain/usecase/SendDialogueUseCase;", "syncSettingsUseCase", "Lcom/textgame/domain/usecase/SyncSettingsUseCase;", "updateStateUseCase", "Lcom/textgame/domain/usecase/UpdateStateUseCase;", "configureAI", "", "settings", "getAIService", "getBackgroundSettingDao", "Lcom/textgame/data/local/db/dao/BackgroundSettingDao;", "getCreateGameUseCase", "getCurrentSettings", "getDatabase", "getDeleteSessionUseCase", "getDialogueDao", "Lcom/textgame/data/local/db/dao/DialogueDao;", "getGameRepository", "getGameStateDao", "Lcom/textgame/data/local/db/dao/GameStateDao;", "getGenerateSummaryUseCase", "getGetAllSessionsUseCase", "getNPCDao", "Lcom/textgame/data/local/db/dao/NPCDao;", "getProtagonistDao", "Lcom/textgame/data/local/db/dao/ProtagonistDao;", "getSendDialogueUseCase", "getSessionDao", "Lcom/textgame/data/local/db/dao/SessionDao;", "getStateSnapshotDao", "Lcom/textgame/data/local/db/dao/StateSnapshotDao;", "getSummaryDao", "Lcom/textgame/data/local/db/dao/SummaryDao;", "getSyncSettingsUseCase", "getUpdateStateUseCase", "getWorldSettingDao", "Lcom/textgame/data/local/db/dao/WorldSettingDao;", "initialize", "context", "Landroid/content/Context;", "app_debug"})
public final class AppModule {
    @org.jetbrains.annotations.Nullable
    private static com.textgame.data.local.db.GameDatabase database;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.repository.GameRepository gameRepository;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.data.remote.ai.AIService aiService;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.CreateGameUseCase createGameUseCase;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.SendDialogueUseCase sendDialogueUseCase;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.UpdateStateUseCase updateStateUseCase;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.GenerateSummaryUseCase generateSummaryUseCase;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.SyncSettingsUseCase syncSettingsUseCase;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.GetAllSessionsUseCase getAllSessionsUseCase;
    @org.jetbrains.annotations.Nullable
    private static com.textgame.domain.usecase.DeleteSessionUseCase deleteSessionUseCase;
    @org.jetbrains.annotations.NotNull
    private static com.textgame.data.local.SettingsPreferences currentSettings;
    @org.jetbrains.annotations.NotNull
    public static final com.textgame.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    public final void initialize(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    public final void configureAI(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.SettingsPreferences settings) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.SettingsPreferences getCurrentSettings() {
        return null;
    }
    
    private final com.textgame.data.local.db.GameDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.SessionDao getSessionDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.ProtagonistDao getProtagonistDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.NPCDao getNPCDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.GameStateDao getGameStateDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.SummaryDao getSummaryDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.WorldSettingDao getWorldSettingDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.BackgroundSettingDao getBackgroundSettingDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.DialogueDao getDialogueDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.local.db.dao.StateSnapshotDao getStateSnapshotDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.repository.GameRepository getGameRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.data.remote.ai.AIService getAIService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.CreateGameUseCase getCreateGameUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.UpdateStateUseCase getUpdateStateUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.GenerateSummaryUseCase getGenerateSummaryUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.SyncSettingsUseCase getSyncSettingsUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.SendDialogueUseCase getSendDialogueUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.GetAllSessionsUseCase getGetAllSessionsUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.usecase.DeleteSessionUseCase getDeleteSessionUseCase() {
        return null;
    }
}