package com.devmike.issues.testdouble

import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.LabelModel

@Suppress("ktlint:standard:max-line-length")
val fakeFlutterAssignees =
    listOf(
        AssigneeModel(
            username = "0xZOne",
            name = "Rulong Chen（陈汝龙）",
            avatarUrl =
                "https://avatars.githubusercontent.com" +
                    "/u/26625149?u=5687c75636971f9a688f2a5e5148316053e108ee&v=4",
        ),
        AssigneeModel(
            username = "a-siva",
            name = "Siva",
            avatarUrl =
                "https://avatars.githubusercontent.com" +
                    "/u/8633293?u=d6bc5b98b8ed13f38be510d649c4fd628f4463e4&v=4",
        ),
        AssigneeModel(
            username = "a14n",
            name = "Alexandre Ardhuin",
            avatarUrl =
                "https://avatars.githubusercontent.com/u" +
                    "/1206632?u=03fdea11e0b8388205905680595bf98e9aa37a93&v=4",
        ),
        AssigneeModel(
            username = "aam",
            name = "Alexander Aprelev",
            avatarUrl =
                "https://avatars.githubusercontent.com/u/381137?u=" +
                    "26f3369ff091108d8ca8c853a4f97d7d33f6ebae&v=4",
        ),
        AssigneeModel(
            username = "alestiago",
            name = "Alejandro Santiago",
            avatarUrl =
                "https://avatars.githubusercontent.com/u/445" +
                    "24995?u=ed469adb7b523e5a5d90edd042dd8026f0887a40&v=4",
        ),
    )

val fakeFlutterLabels =
    listOf(
        LabelModel(name = "bug", color = "#d73a4a"),
        LabelModel(name = "enhancement", color = "#22863a"),
        LabelModel(name = "question", color = "#007bff"),
        LabelModel(name = "documentation", color = "#006b75"),
        LabelModel(name = "performance", color = "#fef2c0"),
        LabelModel(name = "design", color = "#b60205"),
    )
