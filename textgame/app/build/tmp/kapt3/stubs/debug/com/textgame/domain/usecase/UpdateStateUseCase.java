package com.textgame.domain.usecase;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002J<\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u00182\b\u0010\u001b\u001a\u0004\u0018\u00010\fH\u0002J&\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00102\u0006\u0010\u001e\u001a\u00020\u0018H\u0002J9\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u00102\u0006\u0010\u0007\u001a\u00020\b2\u000e\u0010!\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010\u00102\u0006\u0010#\u001a\u00020\bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$J*\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020&2\b\u0010!\u001a\u0004\u0018\u00010(2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010#\u001a\u00020\bH\u0002J0\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010!\u001a\u00020,2\u000e\u0010-\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u00102\u0006\u0010#\u001a\u00020\bH\u0002J0\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/2\u0006\u0010!\u001a\u0002012\u000e\u0010-\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u00102\u0006\u0010#\u001a\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00062"}, d2 = {"Lcom/textgame/domain/usecase/UpdateStateUseCase;", "", "gameRepository", "Lcom/textgame/domain/repository/GameRepository;", "(Lcom/textgame/domain/repository/GameRepository;)V", "execute", "", "sessionId", "", "aiResponse", "Lcom/textgame/domain/model/AIResponse;", "userInput", "", "(JLcom/textgame/domain/model/AIResponse;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateWorldRuleId", "existingRules", "", "Lcom/textgame/domain/model/WorldRule;", "mergeTableColumns", "Lcom/textgame/domain/model/TableColumn;", "newColumns", "Lcom/textgame/domain/model/TableColumnChange;", "existingColumns", "oldType", "Lcom/textgame/domain/model/AttributeType;", "newType", "parseAttributeType", "typeStr", "parseTableColumns", "columns", "parentType", "processAttributeCategoryChanges", "Lcom/textgame/domain/model/AttributeCategory;", "changes", "Lcom/textgame/domain/model/AttributeCategoryChange;", "now", "(JLjava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGameState", "Lcom/textgame/domain/model/GameState;", "gameState", "Lcom/textgame/domain/model/GameChanges;", "updateNPC", "Lcom/textgame/domain/model/NPC;", "npc", "Lcom/textgame/domain/model/NPCChanges;", "validCategories", "updateProtagonist", "Lcom/textgame/domain/model/Protagonist;", "protagonist", "Lcom/textgame/domain/model/ProtagonistChanges;", "app_debug"})
public final class UpdateStateUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.textgame.domain.repository.GameRepository gameRepository = null;
    
    public UpdateStateUseCase(@org.jetbrains.annotations.NotNull
    com.textgame.domain.repository.GameRepository gameRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object execute(long sessionId, @org.jetbrains.annotations.NotNull
    com.textgame.domain.model.AIResponse aiResponse, @org.jetbrains.annotations.NotNull
    java.lang.String userInput, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * 处理属性类目变更。返回更新后的类目列表；若没有变更则返回 null（调用方按原列表校验）。
     */
    private final java.lang.Object processAttributeCategoryChanges(long sessionId, java.util.List<com.textgame.domain.model.AttributeCategoryChange> changes, long now, kotlin.coroutines.Continuation<? super java.util.List<com.textgame.domain.model.AttributeCategory>> $completion) {
        return null;
    }
    
    private final com.textgame.domain.model.AttributeType parseAttributeType(java.lang.String typeStr) {
        return null;
    }
    
    /**
     * 解析新增 TABLE 类目时的列定义。仅当目标类型是 TABLE 时生效，否则返回空列表。
     * 列类型不允许嵌套 TABLE。
     */
    private final java.util.List<com.textgame.domain.model.TableColumn> parseTableColumns(java.util.List<com.textgame.domain.model.TableColumnChange> columns, com.textgame.domain.model.AttributeType parentType) {
        return null;
    }
    
    /**
     * 合并 TABLE 类目的列定义。AI 可以按列名匹配进行部分更新：
     * - 已有列：按提供的字段部分更新
     * - 新列名：追加
     * - 未提及的列：保留原值
     * 仅当最终类型是 TABLE 时才保留列；若类型被改为非 TABLE，清空列。
     */
    private final java.util.List<com.textgame.domain.model.TableColumn> mergeTableColumns(java.util.List<com.textgame.domain.model.TableColumnChange> newColumns, java.util.List<com.textgame.domain.model.TableColumn> existingColumns, com.textgame.domain.model.AttributeType oldType, com.textgame.domain.model.AttributeType newType) {
        return null;
    }
    
    private final java.lang.String generateWorldRuleId(java.util.List<com.textgame.domain.model.WorldRule> existingRules) {
        return null;
    }
    
    private final com.textgame.domain.model.Protagonist updateProtagonist(com.textgame.domain.model.Protagonist protagonist, com.textgame.domain.model.ProtagonistChanges changes, java.util.List<com.textgame.domain.model.AttributeCategory> validCategories, long now) {
        return null;
    }
    
    private final com.textgame.domain.model.NPC updateNPC(com.textgame.domain.model.NPC npc, com.textgame.domain.model.NPCChanges changes, java.util.List<com.textgame.domain.model.AttributeCategory> validCategories, long now) {
        return null;
    }
    
    private final com.textgame.domain.model.GameState updateGameState(com.textgame.domain.model.GameState gameState, com.textgame.domain.model.GameChanges changes, java.lang.String userInput, long now) {
        return null;
    }
}