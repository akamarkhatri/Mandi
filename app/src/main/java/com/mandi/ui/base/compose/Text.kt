package com.mandi.ui.base.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.mandi.ui.theme.*
import com.mandi.R

@Composable
fun Text(
    text: String,
    textAlign: TextAlign? = null,
    isBold: Boolean = false,
    textStyle: TextStyle = typography.headlineLarge,
    color: Color = Color.Black,
    allCaps: Boolean = false,
    modifier: Modifier = Modifier
) {
    val fontWeight = if (isBold)
        FontWeight.Bold
    else
        textStyle.fontWeight
    Text(text = text.let { if (allCaps) text.uppercase() else text },
        style = textStyle.copy(
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign
        ),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldNormal(
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    enabled: Boolean = true,
    errorText: String = "",
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    maxCharacters: Int = Int.MAX_VALUE,
    showCharacterCount: Boolean = false,
    focusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    testTag: String = "TextFieldNormal_$labelText"
) {
    Column(modifier = modifier) {
        val colors = getTextFieldColors()
        val isError = errorText.isNotEmpty()

        val textFieldModifier = Modifier
            .indicatorLine(enabled, isError, interactionSource, colors)
            .fillMaxWidth()
            .testTag(testTag)
        focusRequester?.let { textFieldModifier.focusRequester(focusRequester) }
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                if (it.text.length <= maxCharacters) {
                    onValueChange(it)
                } else {
                    onValueChange(textFieldValue)
                }
            },
            modifier = focusRequester?.let { textFieldModifier.focusRequester(focusRequester) } ?: textFieldModifier,
            enabled = enabled,
            textStyle = typography.bodyMedium,
            cursorBrush = SolidColor(if (isError) Grenadier else GreenDark),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = textFieldValue.text,
                    innerTextField = innerTextField,
                    label = { Text(labelText) },
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    isError = isError,
                    colors = colors,
                    contentPadding = PaddingValues(bottom = dimensionResource(com.mandi.R.dimen.margin_half))
                )
            }
        )
        if (errorText.isNotEmpty() || showCharacterCount) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin)))
            Row(
                horizontalArrangement = if (errorText.isNotEmpty()) Arrangement.SpaceBetween else Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (errorText.isNotEmpty()) {
                    Text(
                        text = errorText,
                        color = Grenadier,
                        modifier = Modifier.weight(1f),
                        textStyle = typography.bodySmall
                    )
                }
                if (showCharacterCount) {
                    val description = stringResource(
                        R.string.x_of_x_characters,
                        textFieldValue.text.length,
                        maxCharacters.toString()
                    )
                    Text(
                        text = "${textFieldValue.text.length} / $maxCharacters",
                        textStyle = typography.bodySmall,
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.margin_2x))
                            .semantics {
                                liveRegion = LiveRegionMode.Polite
                                contentDescription = description
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun TextFieldLarge(
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    unitText: String = "",
    placeHolderText: String = "0",
    errorText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    maxCharacters: Int = Int.MAX_VALUE,
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    testTag: String = "TextFieldLarge",
) {
    val isError = errorText.isNotEmpty()
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                if (newValue.text.length <= maxCharacters) {
                    onValueChange(newValue)
                } else {
                    onValueChange(textFieldValue)
                }
            },
            modifier = Modifier
                .testTag(testTag)
                .focusRequester(focusRequester),
            enabled = enabled,
            textStyle = typography.headlineLarge,
            cursorBrush = SolidColor(if (isError) Grenadier else GreenDark),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.margin_half),
                        end = dimensionResource(R.dimen.margin_half)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .alignByBaseline()
                            .width(IntrinsicSize.Min)
                    ) {
                        if (textFieldValue.text.isEmpty()) {
                            Text(
                                text = placeHolderText,
                                textStyle = typography.headlineLarge,
                                color = CharcoalLight,
                                modifier = Modifier.clearAndSetSemantics { })
                        }
                        innerTextField()
                    }
                    if (unitText.isNotEmpty()) {
                        com.mandi.ui.base.compose.Text(
                            text = unitText,

                            modifier = Modifier
                                .alignByBaseline()
                                .padding(start = dimensionResource(R.dimen.margin))
                        )
                    }
                }
            }
        )
        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = Grenadier,
                textStyle = typography.bodySmall,
                modifier = Modifier
                    .semantics { liveRegion = LiveRegionMode.Polite }
                    .padding(
                        start = dimensionResource(R.dimen.margin_2x),
                        end = dimensionResource(R.dimen.margin_2x)
                    )
            )
        }
    }
}