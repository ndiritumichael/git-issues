import com.devmike.database.entities.AssigneeEntity
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.entities.LabelEntity
import com.devmike.network.dto.IssueDTO
import com.devmike.network.dto.IssueLabel

val fakeissues =
    mutableListOf<IssueWithAssigneesAndLabels>(
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue1",
                    bodyText = "Crash on startup with null pointer exception",
                    state = "open",
                    createdAt = "2023-10-27T10:00:00Z",
                    url = "url1",
                    title = "🔥 Crash on startup",
                    author = "james.smith",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue1", assignee = "mary.wilson"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue1", label = "bug", color = "d73a4a"),
                    LabelEntity(issueId = "issue1", label = "crash", color = "b60205"),
                    LabelEntity(issueId = "issue1", label = "high-priority", color = "e11d21"),
                    LabelEntity(issueId = "issue1", label = "android", color = "00b0d7"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue2",
                    bodyText = "Feature Request: Add support for 3D Touch",
                    state = "open",
                    createdAt = "2023-10-26T14:30:00Z",
                    url = "url2",
                    title = "✨ Feature Request: 3D Touch",
                    author = "david.lee",
                    repositoryName = "flutter/flutter",
                ),
            assignees = emptyList(),
            labels =
                listOf(
                    LabelEntity(issueId = "issue2", label = "feature", color = "c7def8"),
                    LabelEntity(issueId = "issue2", label = "enhancement", color = "a2eeef"),
                    LabelEntity(issueId = "issue2", label = "ios", color = "007ec6"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue3",
                    bodyText = "Text overflow in settings screen on smaller devices",
                    state = "open",
                    createdAt = "2023-10-25T18:00:00Z",
                    url = "url3",
                    title = "🐛 Text overflow in settings",
                    author = "linda.rodriguez",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue3", assignee = "michael.brown"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue3", label = "bug", color = "d73a4a"),
                    LabelEntity(issueId = "issue3", label = "ui", color = "bfd4f2"),
                    LabelEntity(issueId = "issue3", label = "text-overflow", color = "d4c5f9"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue4",
                    bodyText = "Add unit tests for the new animation class",
                    state = "closed",
                    createdAt = "2023-10-24T12:00:00Z",
                    url = "url4",
                    title = "✅ Unit tests for animation class",
                    author = "john.doe",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue4", assignee = "john.doe"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue4", label = "testing", color = "00b0d7"),
                    LabelEntity(issueId = "issue4", label = "unit-tests", color = "1d76db"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue5",
                    bodyText =
                        "Investigate and address performance " +
                            "issues related to scrolling smoothness",
                    state = "open",
                    createdAt = "2023-10-23T09:00:00Z",
                    url = "url5",
                    title = "🚀 Performance: Scrolling smoothness",
                    author = "peter.jones",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue5", assignee = "jane.smith"),
                    AssigneeEntity(issueId = "issue5", assignee = "david.lee"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue5", label = "performance", color = "00b0d7"),
                    LabelEntity(issueId = "issue5", label = "scrolling", color = "1d76db"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue6",
                    bodyText = "Implement dark mode for improved user experience",
                    state = "open",
                    createdAt = "2023-10-22T14:00:00Z",
                    url = "url6",
                    title = "✨ Enhancement: Dark mode",
                    author = "sarah.garcia",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue6", assignee = "robert.davis"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue6", label = "enhancement", color = "a2eeef"),
                    LabelEntity(issueId = "issue6", label = "ui", color = "bfd4f2"),
                    LabelEntity(issueId = "issue6", label = "dark-mode", color = "000000"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue7",
                    bodyText = "Button not clickable on iOS devices - potential layout issue",
                    state = "open",
                    createdAt = "2023-10-21T10:30:00Z",
                    url = "url7",
                    title = "🐛 Bug: Button not clickable on iOS",
                    author = "mary.wilson",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue7", assignee = "james.miller"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue7", label = "bug", color = "d73a4a"),
                    LabelEntity(issueId = "issue7", label = "ios", color = "007ec6"),
                    LabelEntity(issueId = "issue7", label = "button", color = "2196f3"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue8",
                    bodyText = "Update API documentation to reflect the latest changes",
                    state = "closed",
                    createdAt = "2023-10-20T16:00:00Z",
                    url = "url8",
                    title = "✅ Documentation: Update API docs",
                    author = "linda.rodriguez",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue8", assignee = "michael.brown"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue8", label = "documentation", color = "00b0d7"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue9",
                    bodyText = "Allow users to access basic app functionality even when offline",
                    state = "open",
                    createdAt = "2023-10-19T09:45:00Z",
                    url = "url9",
                    title = "🚀 Feature Request: Offline mode",
                    author = "john.doe",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue9", assignee = "peter.jones"),
                    AssigneeEntity(issueId = "issue9", assignee = "jane.smith"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue9", label = "feature", color = "c7def8"),
                    LabelEntity(issueId = "issue9", label = "offline", color = "000000"),
                ),
        ),
        IssueWithAssigneesAndLabels(
            issue =
                CachedIssueEntity(
                    issueId = "issue10",
                    bodyText = "Improve documentation for new API",
                    state = "open",
                    createdAt = "2023-10-15T11:00:00Z",
                    url = "url10",
                    title = "📝 Documentation: New API",
                    author = "alice.johnson",
                    repositoryName = "flutter/flutter",
                ),
            assignees =
                listOf(
                    AssigneeEntity(issueId = "issue10", assignee = "bob.williams"),
                    AssigneeEntity(issueId = "issue10", assignee = "mary.wilson"),
                ),
            labels =
                listOf(
                    LabelEntity(issueId = "issue10", label = "documentation", color = "00b0d7"),
                ),
        ),
    )

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
