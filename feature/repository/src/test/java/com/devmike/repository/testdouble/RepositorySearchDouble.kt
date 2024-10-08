package com.devmike.repository.testdouble

import androidx.paging.PagingData
import com.devmike.domain.models.RepositoryModel
import com.devmike.domain.repository.IRepoSearchRepository
import com.devmike.repository.utils.createTestPagingFlow
import kotlinx.coroutines.flow.Flow

class RepositorySearchDouble : IRepoSearchRepository {
    private val fakeRepositories: List<RepositoryModel>
        get() = fakeFlutterRepositories + fakeRustRepositories

    override fun searchRepositories(query: String): Flow<PagingData<RepositoryModel>> {
        val items = fakeRepositories.filter { it.nameWithOwner.contains(query, ignoreCase = true) }

        return createTestPagingFlow(items)
    }
}

val fakeFlutterRepositories =
    listOf(
        RepositoryModel(
            url = "https://github.com/flutter/flutter",
            name = "flutter",
            nameWithOwner = "flutter/flutter",
            owner = "flutter",
            description =
                "Flutter makes it easy and fast to " +
                    "build beautiful apps for mobile and beyond",
            stargazers = 165315,
            forkCount = 27277,
            issueCount = 99037,
            avatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        ),
        RepositoryModel(
            url = "https://github.com/flutter/plugins",
            name = "plugins",
            nameWithOwner = "flutter/plugins",
            owner = "flutter",
            description = "Plugins for Flutter maintained by the Flutter team",
            stargazers = 17467,
            forkCount = 9806,
            issueCount = 0,
            avatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        ),
        RepositoryModel(
            url = "https://github.com/iampawan/FlutterExampleApps",
            name = "FlutterExampleApps",
            nameWithOwner = "iampawan/FlutterExampleApps",
            owner = "iampawan",
            description = "[Example APPS] Basic Flutter apps, for flutter devs.",
            stargazers = 20489,
            forkCount = 3768,
            issueCount = 41,
            avatarUrl =
                "https://avatars.githubusercontent.com/u" +
                    "/12619420?u=a49ba4b7f5ae93afc2febc86a021b42d2f5b5858&v=4",
        ),
        RepositoryModel(
            url = "https://github.com/Solido/awesome-flutter",
            name = "awesome-flutter",
            nameWithOwner = "Solido/awesome-flutter",
            owner = "Solido",
            description =
                "An awesome list that curates the best Flutter libraries," +
                    " tools, tutorials, articles and more.",
            stargazers = 53256,
            forkCount = 6647,
            issueCount = 0,
            avatarUrl =
                "https://avatars.githubusercontent.com/u/" +
                    "1295961?u=b85eaeb98c4c24aeec8046b7839a5ddf2d504289&v=4",
        ),
        RepositoryModel(
            url = "https://github.com/flutter/engine",
            name = "engine",
            nameWithOwner = "flutter/engine",
            owner = "flutter",
            description = "The Flutter engine",
            stargazers = 7376,
            forkCount = 5948,
            issueCount = 0,
            avatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        ),
    )

val fakeRustRepositories =
    listOf(
        RepositoryModel(
            url = "https://github.com/rust-lang/rust",
            name = "rust",
            nameWithOwner = "rust-lang/rust",
            owner = "rust-lang",
            description = "Empowering everyone to build reliable and efficient software.",
            stargazers = 97402,
            forkCount = 12591,
            issueCount = 54709,
            avatarUrl = "https://avatars.githubusercontent.com/u/5430905?v=4",
        ),
        RepositoryModel(
            url = "https://github.com/TheAlgorithms/Rust",
            name = "Rust",
            nameWithOwner = "TheAlgorithms/Rust",
            owner = "TheAlgorithms",
            description = " All Algorithms implemented in Rust ",
            stargazers = 22465,
            forkCount = 2188,
            issueCount = 69,
            avatarUrl = "https://avatars.githubusercontent.com/u/20487725?v=4",
        ),
        RepositoryModel(
            url = "https://github.com/rustdesk/rustdesk",
            name = "rustdesk",
            nameWithOwner = "rustdesk/rustdesk",
            owner = "rustdesk",
            description =
                "An open-source remote desktop application designed for self-hosting," +
                    " as an alternative to TeamViewer.",
            stargazers = 73719,
            forkCount = 8945,
            issueCount = 3071,
            avatarUrl =
                "https://avatars.githubusercontent.com/" +
                    "u/71636191?u=fcdfa5bbe724bd4ec02f6c3b2419ff25b7f5eb07&v=4",
        ),
        RepositoryModel(
            url = "https://github.com/rust-lang/rustlings",
            name = "rustlings",
            nameWithOwner = "rust-lang/rustlings",
            owner = "rust-lang",
            description = "Small exercises to get you used to reading and writing Rust code!",
            stargazers = 53227,
            forkCount = 10061,
            issueCount = 663,
            avatarUrl = "https://avatars.githubusercontent.com/u/5430905?v=4",
        ),
        RepositoryModel(
            url = "https://github.com/tensorflow/rust",
            name = "rust",
            nameWithOwner = "tensorflow/rust",
            owner = "tensorflow",
            description = "Rust language bindings for TensorFlow",
            stargazers = 5140,
            forkCount = 422,
            issueCount = 189,
            avatarUrl = "https://avatars.githubusercontent.com/u/15658638?v=4",
        ),
    )
