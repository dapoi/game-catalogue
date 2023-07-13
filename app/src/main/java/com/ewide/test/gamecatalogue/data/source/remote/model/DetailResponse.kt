package com.ewide.test.gamecatalogue.data.source.remote.model

import com.squareup.moshi.Json

data class DetailResponse(

	@Json(name="cheapestPriceEver")
	val cheapestPriceEver: CheapestPriceEver? = null,

	@Json(name="deals")
	val deals: List<DealsItem?>? = null,

	@Json(name="info")
	val info: Info? = null
)

data class DealsItem(

	@Json(name="dealID")
	val dealID: String? = null,

	@Json(name="price")
	val price: String? = null,

	@Json(name="savings")
	val savings: String? = null,

	@Json(name="storeID")
	val storeID: String? = null,

	@Json(name="retailPrice")
	val retailPrice: String? = null
)

data class Info(

	@Json(name="steamAppID")
	val steamAppID: Any? = null,

	@Json(name="thumb")
	val thumb: String? = null,

	@Json(name="title")
	val title: String? = null
)

data class CheapestPriceEver(

	@Json(name="date")
	val date: Int? = null,

	@Json(name="price")
	val price: String? = null
)
