package com.devmike.network.githubIssuesTest.sampledata.repository

val sampleIssueResultSearch =
    """
    {
      "data": {
        "search": {
          "__typename": "SearchResultItemConnection",
          "pageInfo": {"__typename": "PageInfo",
            "endCursor": "Y3Vyc29yOjM=",
            "hasNextPage": true
          },
          "nodes": [
            {
              "__typename": "Issue",
              "repository": {
                "__typename": "Repository",
                "nameWithOwner": "flutter/flutter"
              },
              "id": "I_kwDOAeUeuM6Ykwp0",
              "title": "[bug report] image_picker compress the video when using ImagePicker().pickVideo(source: ImageSource.gallery) on iOS and there is no option to get video metadata (like picking images) or uncompressed videos",
              "createdAt": "2024-10-01T17:04:03Z",
              "url": "https://github.com/flutter/flutter/issues/156009",
              "state": "OPEN",
              "bodyText": "trim.B33EB6A4-5D9F-42D4-8DFD-85965EAC607C.MOV",
              "author": {
                "__typename": "Actor",
                "login": "stephane-archer"
              },
              "labels": {
                "__typename": "LabelConnection",
                "edges": []
              },
              "comments": {
                "__typename": "IssueCommentConnection",
                "totalCount": 0
              },
              "assignees": {
                "__typename": "UserConnection",
                "nodes": []
              }
            },
            {
              "__typename": "Issue",
              "repository": {
                "__typename": "Repository",
                "nameWithOwner": "flutter/flutter"
              },
              "id": "I_kwDOAeUeuM6YkrQA",
              "title": "launchUrl not working on Linux Ubuntu 20.04 despite canLaunchUrl returning true",
              "createdAt": "2024-10-01T16:51:14Z",
              "url": "https://github.com/flutter/flutter/issues/156008",
              "state": "OPEN",
              "bodyText": "Steps to reproduce\nI am trying to launch a url via a button on Flutter (Channel stable, 3.16.4, on Ubuntu 20.04.6 LTS 5.15.0-122-generic, locale en_US.UTF-8) for a Linux application.",
              "author": {
                "__typename": "Actor",
                "login": "nsrazdan"
              },
              "labels": {
                "__typename": "LabelConnection",
                "edges": []
              },
              "comments": {
                "__typename": "IssueCommentConnection",
                "totalCount": 0
              },
              "assignees": {
                "__typename": "UserConnection",
                "nodes": []
              }
            },
            {
              "__typename": "Issue",
              "repository": {
                "__typename": "Repository",
                "nameWithOwner": "flutter/flutter"
              },
              "id": "I_kwDOAeUeuM6YkkDn",
              "title": "mac-23 lost external connection from phone device.",
              "createdAt": "2024-10-01T16:34:30Z",
              "url": "https://github.com/flutter/flutter/issues/156007",
              "state": "CLOSED",
              "bodyText": "Type of Request\nbug\nInfrastructure Environment\nLUCI, Github, Cocoon scheduler, Autosubmit, etc...\nWhat is happening?\nmac-23 lost external connection from phone device.\n\nSteps to reproduce\nStep 1:\nStep 2:\n..\nStep n:\nExpected results\nI expect to see X when Y is finished.",
              "author": {
                "__typename": "Actor",
                "login": "kentnguyen99"
              },
              "labels": {
                "__typename": "LabelConnection",
                "edges": [
                  {
                    "__typename": "LabelEdge",
                    "node": {
                      "__typename": "Label",
                      "name": "team-infra",
                      "color": "198022"
                    }
                  },
                  {
                    "__typename": "LabelEdge",
                    "node": {
                      "__typename": "Label",
                      "name": "infra: device lab",
                      "color": "d4c5f9"
                    }
                  }
                ]
              },
              "comments": {
                "__typename": "IssueCommentConnection",
                "totalCount": 1
              },
              "assignees": {
                "__typename": "UserConnection",
                "nodes": [
                  {
                    "__typename": "User",
                    "name": null,
                    "login": "kentnguyen99"
                  }
                ]
              }}
          ]
        }
      }
    }
    """.trimIndent()
