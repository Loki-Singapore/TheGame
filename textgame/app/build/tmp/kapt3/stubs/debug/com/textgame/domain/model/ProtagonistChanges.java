package com.textgame.domain.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BM\u0012\u0016\b\u0002\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\tJ\u0017\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003JQ\u0010\u0015\u001a\u00020\u00002\u0016\b\u0002\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0004H\u00d6\u0001R$\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0018\u0010\b\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2 = {"Lcom/textgame/domain/model/ProtagonistChanges;", "", "attributeChanges", "", "", "inventoryAdd", "", "inventoryRemove", "locationChange", "(Ljava/util/Map;Ljava/util/List;Ljava/util/List;Ljava/lang/String;)V", "getAttributeChanges", "()Ljava/util/Map;", "getInventoryAdd", "()Ljava/util/List;", "getInventoryRemove", "getLocationChange", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ProtagonistChanges {
    @com.google.gson.annotations.SerializedName(value = "attribute_changes")
    @org.jetbrains.annotations.Nullable
    private final java.util.Map<java.lang.String, java.lang.Object> attributeChanges = null;
    @com.google.gson.annotations.SerializedName(value = "inventory_add")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> inventoryAdd = null;
    @com.google.gson.annotations.SerializedName(value = "inventory_remove")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> inventoryRemove = null;
    @com.google.gson.annotations.SerializedName(value = "location_change")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String locationChange = null;
    
    public ProtagonistChanges(@org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> attributeChanges, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> inventoryAdd, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> inventoryRemove, @org.jetbrains.annotations.Nullable
    java.lang.String locationChange) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> getAttributeChanges() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getInventoryAdd() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getInventoryRemove() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getLocationChange() {
        return null;
    }
    
    public ProtagonistChanges() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.textgame.domain.model.ProtagonistChanges copy(@org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> attributeChanges, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> inventoryAdd, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> inventoryRemove, @org.jetbrains.annotations.Nullable
    java.lang.String locationChange) {
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