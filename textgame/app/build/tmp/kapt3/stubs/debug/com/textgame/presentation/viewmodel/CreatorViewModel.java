package com.textgame.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0018\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u000fJ\u0006\u0010\u0019\u001a\u00020\u000fJ\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u0017J\u000e\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u0017J\u000e\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u0017J\u000e\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u0017J\u000e\u0010\'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u0017J\u000e\u0010)\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u0017J\u000e\u0010*\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u0017J\u000e\u0010,\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u0017J\u000e\u0010-\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u0017J\u000e\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u0017J\u000e\u00101\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u0017J\u000e\u00102\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u0017R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u00064"}, d2 = {"Lcom/textgame/presentation/viewmodel/CreatorViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/textgame/presentation/viewmodel/CreatorUiState;", "aiService", "Lcom/textgame/data/remote/ai/AIService;", "createGameUseCase", "Lcom/textgame/domain/usecase/CreateGameUseCase;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addAttributeCategory", "", "category", "Lcom/textgame/domain/model/AttributeCategory;", "addNPC", "npc", "Lcom/textgame/domain/model/NPC;", "addSpecialRule", "rule", "", "createGame", "generateWorldFromPrompt", "removeAttributeCategory", "index", "", "removeNPC", "removeSpecialRule", "updateGameName", "name", "updateGenerationPrompt", "prompt", "updateLocationSetting", "setting", "updateLore", "lore", "updateProtagonistBackground", "bg", "updateProtagonistName", "updateSocialStructure", "structure", "updateTimeSetting", "updateWorldDescription", "desc", "updateWorldHistory", "history", "updateWorldName", "updateWorldType", "type", "app_debug"})
public final class CreatorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.usecase.CreateGameUseCase createGameUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final com.textgame.data.remote.ai.AIService aiService = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.textgame.presentation.viewmodel.CreatorUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.viewmodel.CreatorUiState> uiState = null;
    
    public CreatorViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.textgame.presentation.viewmodel.CreatorUiState> getUiState() {
        return null;
    }
    
    public final void updateGameName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void updateProtagonistName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void updateWorldName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void updateWorldType(@org.jetbrains.annotations.NotNull
    java.lang.String type) {
    }
    
    public final void updateWorldDescription(@org.jetbrains.annotations.NotNull
    java.lang.String desc) {
    }
    
    public final void updateTimeSetting(@org.jetbrains.annotations.NotNull
    java.lang.String setting) {
    }
    
    public final void updateLocationSetting(@org.jetbrains.annotations.NotNull
    java.lang.String setting) {
    }
    
    public final void updateSocialStructure(@org.jetbrains.annotations.NotNull
    java.lang.String structure) {
    }
    
    public final void addSpecialRule(@org.jetbrains.annotations.NotNull
    java.lang.String rule) {
    }
    
    public final void removeSpecialRule(int index) {
    }
    
    public final void updateLore(@org.jetbrains.annotations.NotNull
    java.lang.String lore) {
    }
    
    public final void updateProtagonistBackground(@org.jetbrains.annotations.NotNull
    java.lang.String bg) {
    }
    
    public final void updateWorldHistory(@org.jetbrains.annotations.NotNull
    java.lang.String history) {
    }
    
    public final void addNPC(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.NPC npc) {
    }
    
    public final void removeNPC(int index) {
    }
    
    public final void addAttributeCategory(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.AttributeCategory category) {
    }
    
    public final void removeAttributeCategory(int index) {
    }
    
    public final void updateGenerationPrompt(@org.jetbrains.annotations.NotNull
    java.lang.String prompt) {
    }
    
    public final void generateWorldFromPrompt() {
    }
    
    public final void createGame() {
    }
}