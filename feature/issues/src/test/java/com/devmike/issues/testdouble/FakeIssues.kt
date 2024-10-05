package com.devmike.issues.testdouble

import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.LabelModel

val fakeFlutterIssues =
    listOf(
        IssueModel(
            id = "flutterIssue1",
            title = "Problem with rendering widgets on Android 12",
            bodyText = "Widgets are not rendering correctly on devices running Android 12.",
            state = "open",
            createdAt = "2024-03-15T08:00:00Z",
            url = "https://github.com/flutter/flutter/issues/1",
            author = "user1",
            repositoryName = "flutter/flutter",
            assignee = emptyList(),
            label = listOf(LabelModel(name = "bug", color = "#d73a4a")),
        ),
        IssueModel(
            id = "flutterIssue2",
            title = "Performance issues with large lists",
            bodyText =
                """Scrolling performance is poor when
                |rendering lists with a large number of items.
                """.trimMargin(),
            state = "open",
            createdAt = "2024-03-10T14:30:00Z",
            url = "https://github.com/flutter/flutter/issues/2",
            author = "user2",
            repositoryName = "flutter/flutter",
            assignee = emptyList(),
            label = listOf(LabelModel(name = "performance", color = "#fef2c0")),
        ),
        IssueModel(
            id = "flutterIssue3",
            title = "Request for new feature: Dark mode support",
            bodyText = "Add support for dark mode in Flutter applications.",
            state = "open",
            createdAt = "2024-03-05T11:15:00Z",
            url = "https://github.com/flutter/flutter/issues/3",
            author = "user3",
            repositoryName = "flutter/flutter",
            assignee = emptyList(),
            label = listOf(LabelModel(name = "enhancement", color = "#22863a")),
        ),
        IssueModel(
            id = "flutterIssue4",
            title = "NullPointerException when using certain widgets",
            bodyText =
                """App crashes with a NullPointerException when
                |using specific widgets in a particular configuration.
                """.trimMargin(),
            state = "closed",
            createdAt = "2024-02-28T16:45:00Z",
            url = "https://github.com/flutter/flutter/issues/4",
            author = "user4",
            repositoryName = "flutter/flutter",
            assignee = emptyList(),
            label = listOf(LabelModel(name = "bug", color = "#d73a4a")),
        ),
        IssueModel(
            id = "flutterIssue5",
            title = "Documentation improvement suggestion",
            bodyText = "The documentation for widget X is unclear and could be improved.",
            state = "open",
            createdAt = "2024-02-20T09:30:00Z",
            url = "https://github.com/flutter/flutter/issues/5",
            author = "user5",
            repositoryName = "flutter/flutter",
            assignee = emptyList(),
            label = listOf(LabelModel(name = "documentation", color = "#007bff")),
        ),
    )
val fakektorIssues =
    listOf(
        IssueModel(
            id = "MDU6SXNzdWUyNTY0MTM4Nzk=",
            title = "StatusPages cause a reponse sent exception",
            bodyText = "StatusPages feature causes response sent exception.",
            state = "closed",
            createdAt = "2017-09-09T04:31:39Z",
            url = "https://github.com/ktorio/ktor/issues/201",
            author = "ian4hu",
            repositoryName = "ktorio/ktor",
            assignee = emptyList(),
            label = emptyList(),
        ),
        IssueModel(
            id = "MDU6SXNzdWUyNTY1Mjk0MjY=",
            title = "Exception when browser tab with active Websocket connection was closed",
            bodyText =
                "Exception thrown when closing browser tab with " +
                    "active Websocket connection.",
            state = "closed",
            createdAt = "2017-09-10T18:26:43Z",
            url = "https://github.com/ktorio/ktor/issues/202",
            author = "fluidsonic",
            repositoryName = "ktorio/ktor",
            assignee = emptyList(),
            label = listOf(LabelModel(name = "bug", color = "#fc2929")),
        ),
        IssueModel(
            id = "MDU6SXNzdWUyNTY1NDQ2MDY=",
            title = "Heroku deployment",
            bodyText = "Gradle runs out of memory when building Ktor app on Heroku.",
            state = "closed",
            createdAt = "2017-09-10T22:17:22Z",
            url = "https://github.com/ktorio/ktor/issues/203",
            author = "bluemix",
            repositoryName = "ktorio/ktor",
            assignee = emptyList(),
            label = emptyList(),
        ),
        IssueModel(
            id = "MDU6SXNzdWUyNTY1NzMwODU=",
            title = "Any ideas for the model layer?",
            bodyText =
                "Request for suggestions on modeling business logic" +
                    " and DB interaction in Ktor.",
            state = "closed",
            createdAt = "2017-09-11T03:36:03Z",
            url = "https://github.com/ktorio/ktor/issues/204",
            author = "al6x",
            repositoryName = "ktorio/ktor",
            assignee = emptyList(),
            label = emptyList(),
        ),
        IssueModel(
            id = "MDU6SXNzdWUyNTY2NzE0Njk=",
            title = "Any document for receiving file from frontend?",
            bodyText =
                "Request for documentation or sample code on " +
                    "receiving files from frontend in Ktor.",
            state = "closed",
            createdAt = "2017-09-11T11:36:16Z",
            url = "https://github.com/ktorio/ktor/issues/205",
            author = "earendil1412",
            repositoryName = "ktorio/ktor",
            assignee = emptyList(),
            label = emptyList(),
        ),
    )
