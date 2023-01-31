package com.mandi.ui.base.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import com.mandi.R
import com.mandi.ui.theme.SemiTransparent

@Composable
fun BottomSheet(
    outSideOnClick: () -> Unit,
    containerShape: Shape = RoundedCornerShape(topStart = dimensionResource(id = R.dimen.margin_2x), topEnd = dimensionResource(id = R.dimen.margin_2x)),
    containerBg: Color = Color.White,
    bottomContent: @Composable (ColumnScope.() -> Unit),
) {
    //  TODO("Need to replace with BottomSheetScaffold once its available in material3")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SemiTransparent)
            .clickable { outSideOnClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerBg, containerShape)
                .align(Alignment.BottomCenter)
        ) {
            bottomContent(this)
        }
    }
}