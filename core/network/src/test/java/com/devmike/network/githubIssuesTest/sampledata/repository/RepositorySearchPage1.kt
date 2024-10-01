package com.devmike.network.githubIssuesTest.sampledata.repository

val gqlerror =
    """
    {
      "errors": [
        {
          "message": "Some GraphQL error",
          "locations": [
            {
              "line": 2,
              "column": 4
            }
          ],
          "path": [
            "someField"
          ],
          "extensions": {
            "code": "BAD_USER_INPUT",
            "exception": {
              "stacktrace": [
                "GraphQLError: Some GraphQL error",
                "    at resolveFieldValueOrError (../node_modules/graphql/execution/execute.js:467:18)",
                "    at executeFields (../node_modules/graphql/execution/execute.js:275:35)"
              ]
            }
          }
        }
      ],
      "data": null
    }
    """.trimIndent()

val RepositorySearchPage2 =
    """
      {
    "data": {
      "search": {
        "__typename": "SearchResult",
        "repositoryCount": 745092,
        "pageInfo": {
          "__typename": "PageInfo",
          "endCursor": null,
          "hasNextPage": false
        },
        "nodes": [
          {
            "__typename": "Repository",
            "url": "https://github.com/kaina404/FlutterDouBan",
            "name": "FlutterDouBan",
            "nameWithOwner": "kaina404/FlutterDouBan",
            "owner": {
              "__typename": "User",
              "login": "kaina404",
              "avatarUrl": "https://avatars.githubusercontent.com/u/13998159?u=a2443372e9ea8dc3808b6933f17e9a9df9abbf7e&v=4"
            },
            "description": "🔥🔥🔥Flutter豆瓣客户端,Awesome Flutter Project,全网最100%还原豆瓣客户端。首页、书影音、小组、市集及个人中心，一个不拉。（ https://img.xuvip.top/douyademo.mp4）",
            "stargazers": {
              "__typename": "Stargazers",
              "totalCount": 8727
            },
            "forkCount": 1823,
            "issues": {
              "__typename": "Issues",
              "totalCount": 98
            }
          },
          {
            "__typename": "Repository",
            "url": "https://github.com/toly1994328/FlutterUnit",
            "name": "FlutterUnit",
            "nameWithOwner": "toly1994328/FlutterUnit",
            "owner": {
              "__typename": "User",
              "login": "toly1994328",
              "avatarUrl": "https://avatars.githubusercontent.com/u/26687012?u=8bb9abc3a0cfabc896e13ae6ff327e11389658ea&v=4"
            },
            "description": "All Platform Flutter Experience App",
            "stargazers": {
              "__typename": "Stargazers",
              "totalCount": 7814
            },
            "forkCount": 1264,
            "issues": {
              "__typename": "Issues",
              "totalCount": 130
            }
          },
           {
          "__typename": "Repository",
          "url": "https://github.com/kaina404/FlutterDouBan",
          "name": "FlutterDouBan",
          "nameWithOwner": "kaina404/FlutterDouBan",
          "owner": {
            "__typename": "User",
            "login": "kaina404",
            "avatarUrl": "https://avatars.githubusercontent.com/u/13998159?u=a2443372e9ea8dc3808b6933f17e9a9df9abbf7e&v=4"
          },
          "description": "🔥🔥🔥Flutter豆瓣客户端,Awesome Flutter Project,全网最100%还原豆瓣客户端。首页、书影音、小组、市集及个人中心，一个不拉。（ https://img.xuvip.top/douyademo.mp4）",
          "stargazers": {
            "__typename": "Stargazers",
            "totalCount": 8727
          },
          "forkCount": 1823,
          "issues": {
            "__typename": "Issues",
            "totalCount": 98
          }
        },
        {
          "__typename": "Repository",
          "url": "https://github.com/toly1994328/FlutterUnit",
          "name": "FlutterUnit",
          "nameWithOwner": "toly1994328/FlutterUnit",
          "owner": {
            "__typename": "User",
            "login": "toly1994328",
            "avatarUrl": "https://avatars.githubusercontent.com/u/26687012?u=8bb9abc3a0cfabc896e13ae6ff327e11389658ea&v=4"
          },
          "description": "All Platform Flutter Experience App",
          "stargazers": {
            "__typename": "Stargazers",
            "totalCount": 7814
          },
          "forkCount": 1264,
          "issues": {
            "__typename": "Issues",
            "totalCount": 130
          }
        },
        {
          "__typename": "Repository",
          "url": "https://github.com/samarthagarwal/FlutterScreens",
          "name": "FlutterScreens",
          "nameWithOwner": "samarthagarwal/FlutterScreens",
          "owner": {
            "__typename": "User",
            "login": "samarthagarwal",
            "avatarUrl": "https://avatars.githubusercontent.com/u/3234592?u=d4188fcfc68ca1be49c63999e418e92597b0255f&v=4"
          },
          "description": "A collection of Screens and attractive UIs built with Flutter ready to be used in your applications. No external libraries are used. Just download, add to your project and use.",
          "stargazers": {
            "__typename": "Stargazers",
            "totalCount": 5735
          },
          "forkCount": 1339,
          "issues": {
            "__typename": "Issues",
            "totalCount": 27
          }
        },
        {
          "__typename": "Repository",
          "url": "https://github.com/firebase/flutterfire",
          "name": "flutterfire",
          "nameWithOwner": "firebase/flutterfire",
          "owner": {
            "__typename": "Organization",
            "login": "firebase",
            "avatarUrl": "https://avatars.githubusercontent.com/u/1335026?v=4"
          },
          "description": "🔥 A collection of Firebase plugins for Flutter apps.",
          "stargazers": {
            "__typename": "Stargazers",
            "totalCount": 8649
          },
          "forkCount": 3962,
          "issues": {
            "__typename": "Issues",
            "totalCount": 8202
          }
        },
        {
          "__typename": "Repository",
          "url": "https://github.com/wger-project/flutter",
          "name": "flutter",
          "nameWithOwner": "wger-project/flutter",
          "owner": {
            "__typename": "Organization",
            "login": "wger-project",
            "avatarUrl": "https://avatars.githubusercontent.com/u/17430347?v=4"
          },
          "description": "Flutter fitness/workout app for wger",
          "stargazers": {
            "__typename": "Stargazers",
            "totalCount": 536
          },
          "forkCount": 236,
          "issues": {
            "__typename": "Issues",
            "totalCount": 198
          }
        }
      ]
    }
  }
}

    """.trimIndent()

val RepositoryPage1 =
"""
      {
"data": {
  "search": {
    "repositoryCount": 745092,
    "pageInfo": {
      "__typename": "PageInfo",
      "endCursor": null,
      "hasNextPage": false
    },
    "nodes":
  [
    {
      "__typename": "Repository",
      "name": "flutter",
      "url": "https://github.com/flutter/flutter",
      "nameWithOwner": "kaina404/FlutterDouBan",
      "owner": {
        "__typename": "User",
        "login": "flutter",
        "avatarUrl": "https://avatars.githubusercontent.com/u/14101776?v=4"
      },"description": "Flutter makes it easy and fast to build beautiful apps for mobile and beyond",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 152000
      },
      "forkCount": 24500,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 11500
      }
    },
    {
      "__typename": "Repository",
      "name": "awesome-flutter",
      "url": "https://github.com/Solido/awesome-flutter",
      "nameWithOwner": "toly1994328/FlutterUnit",
      "owner": {
        "__typename": "User",
        "login": "Solido",
        "avatarUrl": "https://avatars.githubusercontent.com/u/14030935?v=4"
      },
      "description": "An awesome list that curates the best Flutter libraries, tools, tutorials, articles and more.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 45000
      },
      "forkCount": 7800,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 500
      }
    },
    {
      "__typename": "Repository",
      "name": "flutter_deer",
      "url": "https://github.com/simplezhli/flutter_deer",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "simplezhli",
        "avatarUrl": "https://avatars.githubusercontent.com/u/13776793?v=4"
      },
      "description": "A flutter project for building an e-commerce app.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 14000
      },
      "forkCount": 4800,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 200
      }
    },
    {
      "__typename": "Repository",
      "name": "fluro",
      "url": "https://github.com/theyakka/fluro",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "theyakka",
        "avatarUrl": "https://avatars.githubusercontent.com/u/1402095?v=4"
      },
      "description": "The brightest, hippest, coolest router for Flutter.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 10500
      },
      "forkCount": 1800,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 300
      }
    },
    {
      "__typename": "Repository",
      "name": "flutter-go",
      "url": "https://github.com/alibaba/flutter-go",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "alibaba",
        "avatarUrl": "https://avatars.githubusercontent.com/u/15969563?v=4"
      },
      "description": "flutter 开发者帮助 APP，包含 flutter 常用 140+ 组件的demo 演示与中文文档",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 9800
      },
      "forkCount": 3500,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 150
      }
    },
    {
      "__typename": "Repository",
      "name": "getx","url": "https://github.com/jonataslaw/getx",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "jonataslaw",
        "avatarUrl": "https://avatars.githubusercontent.com/u/25680361?v=4"
      },
      "description": "Open screens/snackbars/dialogs without context, manage states and inject dependencies easily with Get.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 9500
      },
      "forkCount": 2100,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 600
      }
    },
    {
      "__typename": "Repository",
      "name": "flutter_architecture_samples",
      "url": "https://github.com/brianegan/flutter_architecture_samples",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "brianegan",
        "avatarUrl": "https://avatars.githubusercontent.com/u/1013384?v=4"
      },
      "description": "TodoMVC for Flutter",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 9200
      },
      "forkCount": 3800,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 100
      }
    },
    {
      "__typename": "Repository",
      "name": "dio",
      "url": "https://github.com/cfug/dio",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "cfug",
        "avatarUrl": "https://avatars.githubusercontent.com/u/12033224?v=4"
      },
      "description": "A powerful Http client for Dart, which supports Interceptors, FormData, Request Cancellation, File Downloading, Timeout etc.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 9000
      },
      "forkCount": 1500,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 400
      }
    },
    {
      "__typename": "Repository",
      "name": "flutter_wanandroid",
      "url": "https://github.com/Sky24n/flutter_wanandroid",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "Sky24n",
        "avatarUrl": "https://avatars.githubusercontent.com/u/20358868?v=4"
      },
      "description": "Flutter complete project, WanAndroid client.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 8800
      },
      "forkCount": 3200,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 100
      }
    },
    {
      "__typename": "Repository",
      "name": "flutter_redux",
      "url": "https://github.com/johnpryan/flutter_redux",
       "nameWithOwner": "samarthagarwal/FlutterScreens",
      "owner": {
        "__typename": "User",
        "login": "johnpryan",
        "avatarUrl": "https://avatars.githubusercontent.com/u/1284984?v=4"
      },
      "description": "Bind Redux to Flutter widgets.",
      "stargazers": {
        "__typename": "StargazerConnection",
        "totalCount": 8500
      },
      "forkCount": 1700,
      "issues": {
        "__typename": "IssueConnection",
        "totalCount": 250
      }
    }
  ]
  }
  }
  }
""".trimIndent()
