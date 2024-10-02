package com.devmike.network.githubIssuesTest.sampledata.repository

val FakeAssignees =
    """
    {
      "data": {
        "repository": {
          "__typename": "Repository",
          "assignableUsers": {"__typename": "UserConnection",
            "pageInfo": {
              "__typename": "PageInfo",
              "endCursor": "Y3Vyc29yOnYyOpKkYTE0bs4AEmlo",
              "hasNextPage": true
            },
            "edges":[
              {
                "__typename": "UserEdge",
                "node": {
                  "__typename": "User",
                  "name": "Rulong Chen（陈汝龙）",
                  "login": "0xZOne",
                  "avatarUrl": "https://avatars.githubusercontent.com/u/26625149?u=5687c75636971f9a688f2a5e5148316053e108ee&v=4"}
              },
              {
                "__typename": "UserEdge",
                "node": {
                  "__typename": "User",
                  "name": "Siva",
                  "login": "a-siva",
                  "avatarUrl": "https://avatars.githubusercontent.com/u/8633293?u=d6bc5b98b8ed13f38be510d649c4fd628f4463e4&v=4"
                }
              },
              {
                "__typename": "UserEdge",
                "node": {
                  "__typename": "User",
                  "name": "Alexandre Ardhuin",
                  "login": "a14n",
                  "avatarUrl": "https://avatars.githubusercontent.com/u/1206632?u=03fdea11e0b8388205905680595bf98e9aa37a93&v=4"
                }
              }
            ]
          }
        }}
    }
    """.trimIndent()

val FakeLabels =
    """
    {
      "data": {
        "repository": {
          "__typename": "Repository",
          "labels": {"__typename": "LabelConnection",
            "pageInfo": {
              "__typename": "PageInfo",
              "endCursor": "Mw",
              "hasNextPage": true
            },
            "edges": [
              {
                "__typename": "LabelEdge",
                "node": {"__typename": "Label",
                  "name": "a: tests",
                  "color": "55ddbb"
                }
              },
              {
                "__typename": "LabelEdge",
                "node": {
                  "__typename": "Label",
                  "name": "a: text input",
                  "color": "55ddbb"
                }
              },
              {
                "__typename": "LabelEdge",
                "node": {
                  "__typename": "Label",
                  "name": "c: new feature","color": "e02000"
                }
              }
            ]
          }
        }
      }
    }
    """.trimIndent()
