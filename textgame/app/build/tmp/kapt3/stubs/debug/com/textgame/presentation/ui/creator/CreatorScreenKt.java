package com.textgame.presentation.ui.creator;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aD\u0010\u0007\u001a\u00020\u00012\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a$\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a*\u0010\u0017\u001a\u00020\u00012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a2\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00062\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u001e\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0010\u0010!\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a0\u0010\"\u001a\u00020\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0018\u0010&\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0$\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\'\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010(\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u0014H\u0002\u00a8\u0006+"}, d2 = {"AddNPCDialog", "", "onDismiss", "Lkotlin/Function0;", "onAdd", "Lkotlin/Function1;", "Lcom/textgame/domain/model/NPC;", "AttributeDialog", "initial", "Lcom/textgame/domain/model/AttributeCategory;", "title", "", "confirmText", "onConfirm", "AttributeStep", "viewModel", "Lcom/textgame/presentation/viewmodel/CreatorViewModel;", "onAddAttribute", "AttributeTypeDropdown", "selected", "Lcom/textgame/domain/model/AttributeType;", "onSelected", "BasicInfoStep", "CreatorScreen", "onBack", "onGameCreated", "", "EditNPCDialog", "npc", "onSave", "FinalStep", "NPCStep", "onAddNPC", "SpecialRulesSection", "TableColumnsEditor", "columns", "", "Lcom/textgame/domain/model/TableColumn;", "onColumnsChange", "WorldSettingStep", "WorldTypeDropdown", "attributeTypeLabel", "type", "app_debug"})
public final class CreatorScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void CreatorScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onGameCreated) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void BasicInfoStep(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void WorldSettingStep(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void WorldTypeDropdown(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SpecialRulesSection(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AttributeStep(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddAttribute) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void NPCStep(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddNPC) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void FinalStep(@org.jetbrains.annotations.NotNull
    com.textgame.presentation.viewmodel.CreatorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AttributeDialog(@org.jetbrains.annotations.Nullable
    com.textgame.domain.model.AttributeCategory initial, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String confirmText, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.AttributeCategory, kotlin.Unit> onConfirm) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AttributeTypeDropdown(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.AttributeType selected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.AttributeType, kotlin.Unit> onSelected) {
    }
    
    private static final java.lang.String attributeTypeLabel(com.textgame.domain.model.AttributeType type) {
        return null;
    }
    
    /**
     * TABLE 属性的列定义编辑器。每列含名称、类型（不可为 TABLE）、ENUM 列的可选项。
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void TableColumnsEditor(@org.jetbrains.annotations.NotNull
    java.util.List<com.textgame.domain.model.TableColumn> columns, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.util.List<com.textgame.domain.model.TableColumn>, kotlin.Unit> onColumnsChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AddNPCDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.NPC, kotlin.Unit> onAdd) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EditNPCDialog(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.NPC npc, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.NPC, kotlin.Unit> onSave) {
    }
}