package com.textgame.data.local.db.dao;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J!\u0010\t\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J!\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/textgame/data/local/db/dao/DialogueDao;", "", "deleteDialogueById", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDialoguesBySessionId", "sessionId", "deleteDialoguesFromTurn", "fromTurn", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDialoguesBySessionId", "", "Lcom/textgame/data/local/db/entity/DialogueEntity;", "insertDialogue", "dialogue", "(Lcom/textgame/data/local/db/entity/DialogueEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateDialogueContent", "content", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface DialogueDao {
    
    @androidx.room.Query(value = "SELECT * FROM dialogues WHERE sessionId = :sessionId ORDER BY createdAt ASC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getDialoguesBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.textgame.data.local.db.entity.DialogueEntity>> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertDialogue(@org.jetbrains.annotations.NotNull
    com.textgame.data.local.db.entity.DialogueEntity dialogue, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "UPDATE dialogues SET content = :content WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateDialogueContent(long id, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM dialogues WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteDialogueById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM dialogues WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteDialoguesBySessionId(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM dialogues WHERE sessionId = :sessionId AND turnNumber >= :fromTurn")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteDialoguesFromTurn(long sessionId, int fromTurn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}