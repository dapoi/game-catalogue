package com.ewide.test.gamecatalogue.data.source.remote.model

import com.squareup.moshi.Json

data class GamesResponse(

	@Json(name="gameID")
	val gameID: String? = null,

	@Json(name="steamAppID")
	val steamAppID: Any? = null,

	@Json(name="cheapestDealID")
	val cheapestDealID: String? = null,

	@Json(name="internalName")
	val internalName: String? = null,

	@Json(name="external")
	val external: String? = null,

	@Json(name="thumb")
	val thumb: String? = null,

	@Json(name="cheapest")
	val cheapest: String? = null
)
