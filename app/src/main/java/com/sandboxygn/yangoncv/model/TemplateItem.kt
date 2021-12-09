package com.sandboxygn.yangoncv.model

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TemplateItem(
    @DrawableRes val imageResourcesId : Int,
     val  templateName : String
)