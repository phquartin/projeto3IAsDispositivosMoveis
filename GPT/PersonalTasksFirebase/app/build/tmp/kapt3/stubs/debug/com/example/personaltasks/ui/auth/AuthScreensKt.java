package com.example.personaltasks.ui.auth;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.foundation.layout.ColumnScope;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a>\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u001c\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b\n\u00a2\u0006\u0002\b\u000bH\u0003\u001aT\u0010\f\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u0013\b\u0002\u0010\u0011\u001a\r\u0012\u0004\u0012\u00020\u00010\u0010\u00a2\u0006\u0002\b\n2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b\nH\u0003\u001a$\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a+\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0011\u0010\u0019\u001a\r\u0012\u0004\u0012\u00020\u00010\u0010\u00a2\u0006\u0002\b\nH\u0003\u001aT\u0010\u001a\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a\b\u0010\u001f\u001a\u00020\u0001H\u0003\u001a,\u0010 \u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010!\u001a\u00020\u0005H\u0003\u001aL\u0010\"\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u001e\u0010#\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a@\u0010&\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u00a8\u0006("}, d2 = {"AuthContent", "", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "title", "", "subtitle", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "AuthScaffold", "uiState", "Lcom/example/personaltasks/ui/auth/AuthUiState;", "onMessageShown", "Lkotlin/Function0;", "navigationIcon", "EmailField", "value", "onValueChange", "LoadingButtonContent", "isLoading", "", "text", "icon", "LoginScreen", "onLogin", "Lkotlin/Function2;", "onCreateAccount", "onForgotPassword", "LoginScreenPreview", "PasswordField", "label", "RegisterScreen", "onRegister", "Lkotlin/Function3;", "onBackToLogin", "ResetPasswordScreen", "onSendReset", "app_debug"})
public final class AuthScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void LoginScreen(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.ui.auth.AuthUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCreateAccount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onForgotPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMessageShown) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RegisterScreen(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.ui.auth.AuthUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRegister, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMessageShown) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ResetPasswordScreen(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.ui.auth.AuthUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSendReset, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMessageShown) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void AuthScaffold(java.lang.String title, com.example.personaltasks.ui.auth.AuthUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onMessageShown, kotlin.jvm.functions.Function0<kotlin.Unit> navigationIcon, kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.PaddingValues, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AuthContent(androidx.compose.foundation.layout.PaddingValues padding, java.lang.String title, java.lang.String subtitle, kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmailField(java.lang.String value, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PasswordField(java.lang.String value, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, java.lang.String label) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingButtonContent(boolean isLoading, java.lang.String text, kotlin.jvm.functions.Function0<kotlin.Unit> icon) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable()
    private static final void LoginScreenPreview() {
    }
}