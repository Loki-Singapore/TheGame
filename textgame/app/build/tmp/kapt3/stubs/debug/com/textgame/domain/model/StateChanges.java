package com.textgame.domain.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0016\b\u0002\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0017\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\tH\u00c6\u0003J9\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0016\b\u0002\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0006H\u00d6\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001f\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001b"}, d2 = {"Lcom/textgame/domain/model/StateChanges;", "", "protagonist", "Lcom/textgame/domain/model/ProtagonistChanges;", "npc", "", "", "Lcom/textgame/domain/model/NPCChanges;", "game", "Lcom/textgame/domain/model/GameChanges;", "(Lcom/textgame/domain/model/ProtagonistChanges;Ljava/util/Map;Lcom/textgame/domain/model/GameChanges;)V", "getGame", "()Lcom/textgame/domain/model/GameChanges;", "getNpc", "()Ljava/util/Map;", "getProtagonist", "()Lcom/textgame/domain/model/ProtagonistChanges;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class StateChanges {
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.ProtagonistChanges protagonist = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.Map<java.lang.String, com.textgame.domain.model.NPCChanges> npc = null;
    @org.jetbrains.annotations.Nullable
    private final com.textgame.domain.model.GameChanges game = null;
    
    public StateChanges(@org.jetbrains.annotations.Nullable
    com.textgame.domain.model.ProtagonistChanges protagonist, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, com.textgame.domain.model.NPCChanges> npc, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.GameChanges game) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.ProtagonistChanges getProtagonist() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, com.textgame.domain.model.NPCChanges> getNpc() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.GameChanges getGame() {
        return null;
    }
    
    public StateChanges() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.ProtagonistChanges component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, com.textgame.domain.model.NPCChanges> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.textgame.domain.model.GameChanges component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.model.StateChanges copy(@org.jetbrains.annotations.Nullable
    com.textgame.domain.model.ProtagonistChanges protagonist, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, com.textgame.domain.model.NPCChanges> npc, @org.jetbrains.annotations.Nullable
    com.textgame.domain.model.GameChanges game) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}