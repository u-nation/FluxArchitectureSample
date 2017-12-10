package com.ogaclejapan.flux.events

import com.ogaclejapan.flux.models.User

class SearchResultListChangedEvent(val userId: String, val followers: List<User>, val nextPage: Int)
