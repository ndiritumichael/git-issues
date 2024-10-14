package com.devmike.testing.networktestdoubles

import com.devmike.network.dto.IssueDTO
import com.devmike.network.dto.IssueLabel

val fakenetworkissuespage1 =
    listOf(
        IssueDTO(
            id = "issue1",
            title = "🔥 Crash on startup with null pointer exception",
            bodyText =
                """
                App crashes on startup with a null pointer exception.

                **Steps to reproduce:**
                1. Launch the app on a Pixel 6 device.
                2. Observe the crash.

                **Stack trace:**
                (Attached stack trace)
                """.trimIndent(),
            state = "open",
            createdAt = "2023-10-27T10:00:00Z",
            url = "url1",
            author = "james.smith",
            issueCommentsCount = 15,
            repositoryName = "flutter/flutter",
            labels =
                listOf(
                    IssueLabel("bug", "d73a4a"),
                    IssueLabel("crash", "b60205"),
                    IssueLabel("high-priority", "e11d21"),
                    IssueLabel("android", "00b0d7"),
                ),
            assignees = listOf("mary.wilson"),
        ),
        IssueDTO(
            id = "issue2",
            title = "✨ Feature Request: Add support for 3D Touch",
            bodyText =
                """
                It would be great to have support for 3D Touch on iOS devices to provide
                users with quick access to common actions.
                """.trimIndent(),
            state = "open",
            createdAt = "2023-10-26T14:30:00Z",
            url = "url2",
            author = "david.lee",
            issueCommentsCount = 7,
            repositoryName = "flutter/flutter",
            labels =
                listOf(
                    IssueLabel("feature", "c7def8"),
                    IssueLabel("enhancement", "a2eeef"),
                    IssueLabel("ios", "007ec6"),
                ),
            assignees = listOf(),
        ),
        IssueDTO(
            id = "issue3",
            title = "🐛 Text overflow in settings screen",
            bodyText =
                """
                On smaller screens, the text in the settings screen overflows and gets cut off.

                **Device:** iPhone SE (2nd generation)
                """.trimIndent(),
            state = "open",
            createdAt = "2023-10-25T18:00:00Z",
            url = "url3",
            author = "linda.rodriguez",
            issueCommentsCount = 3,
            repositoryName = "flutter/flutter",
            labels =
                listOf(
                    IssueLabel("bug", "d73a4a"),
                    IssueLabel("ui", "bfd4f2"),
                    IssueLabel("text-overflow", "d4c5f9"),
                ),
            assignees = listOf("michael.brown"),
        ),
    )

val fakenetworkissuespage2 =
    listOf(
        IssueDTO(
            id = "issue4",
            title = "✅ Add unit tests for new animation class",
            bodyText =
                """
                Write unit tests to ensure the new animation class functions as expected.
                """.trimIndent(),
            state = "closed",
            createdAt = "2023-10-24T12:00:00Z",
            url = "url4",
            author = "john.doe",
            issueCommentsCount = 10,
            repositoryName = "flutter/flutter",
            labels =
                listOf(
                    IssueLabel("testing", "00b0d7"),
                    IssueLabel("unit-tests", "1d76db"),
                ),
            assignees = listOf("john.doe"),
        ),
        IssueDTO(
            id = "issue5",
            title = "🚀 Performance: Improve scrolling smoothness",
            bodyText =
                """
                Investigate and address performance issues related to scrolling smoothness,
                especially on older devices.
                """.trimIndent(),
            state = "open",
            createdAt = "2023-10-23T09:00:00Z",
            url = "url5",
            author = "peter.jones",
            issueCommentsCount = 28,
            repositoryName = "flutter/flutter",
            labels =
                listOf(
                    IssueLabel("performance", "00b0d7"),
                    IssueLabel("scrolling", "1d76db"),
                ),
            assignees = listOf("jane.smith", "david.lee"),
        ),
    )
