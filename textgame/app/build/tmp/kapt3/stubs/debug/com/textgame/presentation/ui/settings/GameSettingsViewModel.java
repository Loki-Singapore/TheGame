package com.textgame.presentation.ui.settings;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0012\u001a\u00020\u000fH\u0002J\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0016J\u0016\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0016J\u0016\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u0016J\u0016\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0016J\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u0016J\u000e\u0010\"\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0016J\u000e\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u0016J\u000e\u0010%\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0016J\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006("}, d2 = {"Lcom/textgame/presentation/ui/settings/GameSettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionId", "", "(J)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/textgame/presentation/ui/settings/GameSettingsUiState;", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteNPC", "", "index", "", "loadSettings", "saveSettings", "updateBackgroundProtagonist", "background", "", "updateNPCMood", "mood", "updateNPCName", "name", "updateNPCRole", "role", "updateProtagonistAttribute", "key", "value", "updateProtagonistLocation", "location", "updateProtagonistName", "updateWorldDescription", "desc", "updateWorldName", "updateWorldType", "type", "app_debug"})
public final class GameSettingsViewModel extends androidx.lifecycle.ViewModel {
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.textgame.presentation.ui.settings.GameSettingsUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.ui.settings.GameSettingsUiState> uiState = null;
    
    public GameSettingsViewModel(long sessionId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.ui.settings.GameSettingsUiState> getUiState() {
        return null;
    }
    
    private final void loadSettings() {
    }
    
    public final void updateWorldName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void updateWorldDescription(@org.jetbrains.annotations.NotNull
    java.lang.String desc) {
    }
    
    public final void updateWorldType(@org.jetbrains.annotations.NotNull
    java.lang.String type) {
    }
    
    public final void updateBackgroundProtagonist(@org.jetbrains.annotations.NotNull
    java.lang.String background) {
    }
    
    public final void updateProtagonistName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void updateProtagonistLocation(@org.jetbrains.annotations.NotNull
    java.lang.String location) {
    }
    
    public final void updateProtagonistAttribute(@org.jetbrains.annotations.NotNull
    java.lang.String key, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
    }
    
    public final void updateNPCName(int index, @org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void updateNPCRole(int index, @org.jetbrains.annotations.NotNull
    java.lang.String role) {
    }
    
    public final void updateNPCMood(int index, @org.jetbrains.annotations.NotNull
    java.lang.String mood) {
    }
    
    public final void deleteNPC(int index) {
    }
    
    public final void saveSettings() {
    }
}