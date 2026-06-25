package com.textgame.presentation.ui.creator;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a*\u0010\u0007\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a$\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a*\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u0016\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u001e\u0010\u0017\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0010\u0010\u0019\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0010\u0010\u001b\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\u001c"}, d2 = {"AddAttributeDialog", "", "onDismiss", "Lkotlin/Function0;", "onAdd", "Lkotlin/Function1;", "Lcom/textgame/domain/model/AttributeCategory;", "AddNPCDialog", "Lcom/textgame/domain/model/NPC;", "AttributeStep", "viewModel", "Lcom/textgame/presentation/viewmodel/CreatorViewModel;", "onAddAttribute", "AttributeTypeDropdown", "selected", "Lcom/textgame/domain/model/AttributeType;", "onSelected", "BasicInfoStep", "CreatorScreen", "onBack", "onGameCreated", "", "FinalStep", "NPCStep", "onAddNPC", "SpecialRulesSection", "WorldSettingStep", "WorldTypeDropdown", "app_debug"})
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
    public static final void AddAttributeDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.AttributeCategory, kotlin.Unit> onAdd) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AttributeTypeDropdown(@org.jetbrains.annotations.NotNull
    com.textgame.domain.model.AttributeType selected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.AttributeType, kotlin.Unit> onSelected) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AddNPCDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.textgame.domain.model.NPC, kotlin.Unit> onAdd) {
    }
}