package kr.co.korean.util

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480", showBackground = true, backgroundColor = 0xFFFFFF)
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480", showBackground = true, backgroundColor = 0xFFFFFF)
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480", showBackground = true, backgroundColor = 0xFFFFFF)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480", showBackground = true, backgroundColor = 0xFFFFFF)
annotation class DevicePreviews