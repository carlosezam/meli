package com.ezam.melichallenge.search.data.remote

import com.ezam.melichallenge.search.data.remote.model.DetailsDTO
import com.ezam.melichallenge.search.data.remote.model.SearchDTO
import com.google.common.truth.Truth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.HttpURLConnection

class SearchApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var searchApi: SearchApi

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val jsonConverter = Json{
            ignoreUnknownKeys = true
            coerceInputValues = true
        }.asConverterFactory("application/json".toMediaType())
        val okHttpClient = OkHttpClient().newBuilder().build()

        searchApi = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(jsonConverter)
            .client(okHttpClient)
            .build()
            .create(SearchApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    fun enqueueSuccessResponse(json: String){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)

        mockWebServer.enqueue(response)
    }

    fun enqueueNotFoundResponse(json: String){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            .setBody(json)

        mockWebServer.enqueue(response)
    }

    @Test
    fun `valida el parseo exitoso del json`() = runTest {
        // given
        enqueueSuccessResponse(SEARCH_JSON)

        // when
        val result = searchApi.search("lorem" )

        // then
        val expected = SearchDTO(
            paging = SearchDTO.PagingDTO(
                total = 2,
                offset = 1,
                limit = 1
            ),
            results = listOf(
                SearchDTO.ResultDTO(
                    id = "MLM870963191",
                    title = "Juego De Mesa Thinkblot De Los Creadores De Pictionary Usado",
                    thumbnail = "http://http2.mlstatic.com/D_995140-MLM44785495231_022021-I.jpg",
                    price = 350f
                )
            )
        )

        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `valida el parseo exitoso del json de detalles`() = runTest {
        // given
        enqueueNotFoundResponse(DETAILS_JSON)

        // when
        val result = try {
            searchApi.details("lorem" )
        }catch (e: Exception){
            e.printStackTrace()
            return@runTest
        }

        // then
        val expected = DetailsDTO(
            id = "MLM1746015974",
            title = "Lampara De Cabeza Usb Linterna Iluminacion Doble Recargable",
            price = 68.88F,
            condition = "new",
            pictures = listOf(
                DetailsDTO.PictureDTO(
                    secureUrl = "https://http2.mlstatic.com/D_630723-MLM53260390741_012023-O.jpg"
                )
            ),
            attributes = listOf(
                DetailsDTO.AttributeDTO(
                    name = "Alcance de proyección",
                    valueName = "50 m"
                ),
                DetailsDTO.AttributeDTO(
                    name = "Marca",
                    valueName = "TGW"
                ),
                DetailsDTO.AttributeDTO(
                    name = "Diámetro",
                    valueName = ""
                )
            )
        )

        Truth.assertThat(result).isEqualTo(expected)
    }
}

private const val DETAILS_JSON = """
{
    "id": "MLM1746015974",
    "site_id": "MLM",
    "title": "Lampara De Cabeza Usb Linterna Iluminacion Doble Recargable",
    "seller_id": 801048640,
    "category_id": "MLM47781",
    "official_store_id": null,
    "price": 68.88,
    "base_price": 68.88,
    "original_price": 168,
    "currency_id": "MXN",
    "initial_quantity": 3239,
    "sale_terms": [
        {
            "id": "WARRANTY_TIME",
            "name": "Tiempo de garantía",
            "value_id": null,
            "value_name": "1 meses",
            "value_struct": {
                "number": 1,
                "unit": "meses"
            },
            "values": [
                {
                    "id": null,
                    "name": "1 meses",
                    "struct": {
                        "number": 1,
                        "unit": "meses"
                    }
                }
            ],
            "value_type": "number_unit"
        },
        {
            "id": "WARRANTY_TYPE",
            "name": "Tipo de garantía",
            "value_id": "2230280",
            "value_name": "Garantía del vendedor",
            "value_struct": null,
            "values": [
                {
                    "id": "2230280",
                    "name": "Garantía del vendedor",
                    "struct": null
                }
            ],
            "value_type": "list"
        }
    ],
    "buying_mode": "buy_it_now",
    "listing_type_id": "gold_special",
    "condition": "new",
    "permalink": "https://articulo.mercadolibre.com.mx/MLM-1746015974-lampara-de-cabeza-usb-linterna-iluminacion-doble-recargable-_JM",
    "thumbnail_id": "630723-MLM53260390741_012023",
    "thumbnail": "http://http2.mlstatic.com/D_630723-MLM53260390741_012023-I.jpg",
    "pictures": [
        {
            "id": "630723-MLM53260390741_012023",
            "url": "http://http2.mlstatic.com/D_630723-MLM53260390741_012023-O.jpg",
            "secure_url": "https://http2.mlstatic.com/D_630723-MLM53260390741_012023-O.jpg",
            "size": "500x472",
            "max_size": "1200x1133",
            "quality": ""
        }
    ],
    "video_id": null,
    "descriptions": [],
    "accepts_mercadopago": true,
    "non_mercado_pago_payment_methods": [],
    "shipping": {
        "mode": "me2",
        "methods": [],
        "tags": [],
        "dimensions": null,
        "local_pick_up": false,
        "free_shipping": false,
        "logistic_type": "fulfillment",
        "store_pick_up": false
    },
    "international_delivery_mode": "none",
    "seller_address": {
        "city": {
            "id": "TUxNQ0NVQTczMTI",
            "name": "Cuauhtémoc"
        },
        "state": {
            "id": "MX-DIF",
            "name": "Distrito Federal"
        },
        "country": {
            "id": "MX",
            "name": "Mexico"
        },
        "search_location": {
            "neighborhood": {
                "id": "TUxNQkNFTjM1ODM",
                "name": "Centro De La Ciudad De México Area 1"
            },
            "city": {
                "id": "TUxNQ0NVQTczMTI",
                "name": "Cuauhtémoc"
            },
            "state": {
                "id": "TUxNUERJUzYwOTQ",
                "name": "Distrito Federal"
            }
        },
        "id": 1185277369
    },
    "seller_contact": null,
    "location": {},
    "coverage_areas": [],
    "attributes": [
        {
            "id": "BEAM_DISTANCE",
            "name": "Alcance de proyección",
            "value_id": null,
            "value_name": "50 m",
            "values": [
                {
                    "id": null,
                    "name": "50 m",
                    "struct": {
                        "number": 50,
                        "unit": "m"
                    }
                }
            ],
            "value_type": "number_unit"
        },
        {
            "id": "BRAND",
            "name": "Marca",
            "value_id": "1025369",
            "value_name": "TGW",
            "values": [
                {
                    "id": "1025369",
                    "name": "TGW",
                    "struct": null
                }
            ],
            "value_type": "string"
        },
        {
            "id": "DIAMETER",
            "name": "Diámetro",
            "value_id": "-1",
            "value_name": null,
            "values": [
                {
                    "id": "-1",
                    "name": null,
                    "struct": null
                }
            ],
            "value_type": "number_unit"
        }
    ],
    "listing_source": "",
    "variations": [
        {
            "id": 176295276904,
            "price": 68.88,
            "attribute_combinations": [
                {
                    "id": "FLASHLIGHT_COLOR",
                    "name": "Color de la linterna",
                    "value_id": "52049",
                    "value_name": "Negro",
                    "values": [
                        {
                            "id": "52049",
                            "name": "Negro",
                            "struct": null
                        }
                    ],
                    "value_type": "string"
                },
                {
                    "id": "LIGHT_COLOR",
                    "name": "Color de la luz",
                    "value_id": "52055",
                    "value_name": "Blanco",
                    "values": [
                        {
                            "id": "52055",
                            "name": "Blanco",
                            "struct": null
                        }
                    ],
                    "value_type": "string"
                }
            ],
            "sale_terms": [],
            "picture_ids": [
                "630723-MLM53260390741_012023",
                "922848-MLM53171840339_012023",
                "831554-MLM53171789654_012023",
                "851056-MLM53171956973_012023",
                "822767-MLM53259913853_012023",
                "644733-MLM53171715859_012023",
                "748059-MLM53171821436_012023",
                "976207-MLM53171868235_012023",
                "749231-MLM53171821439_012023",
                "824394-MLM53171867235_012023"
            ],
            "catalog_product_id": null
        }
    ],
    "status": "active",
    "sub_status": [],
    "tags": [
        "good_quality_picture",
        "good_quality_thumbnail",
        "immediate_payment",
        "cart_eligible"
    ],
    "warranty": "Garantía del vendedor: 1 meses",
    "catalog_product_id": null,
    "domain_id": "MLM-FLASHLIGHTS",
    "parent_item_id": null,
    "deal_ids": [
        "MLM34147",
        "MLM28355"
    ],
    "automatic_relist": false,
    "date_created": "2023-01-05T09:35:02.000Z",
    "last_updated": "2023-11-07T05:31:59.000Z",
    "health": 0.85,
    "catalog_listing": false
}
"""

private const val SEARCH_JSON = """
{
    "site_id": "MLM",
    "country_default_time_zone": "GMT-05:00",
    "query": "1qw",
    "paging": {
        "total": 2,
        "primary_results": 2,
        "offset": 1,
        "limit": 1
    },
    "results": [
        {
            "id": "MLM870963191",
            "title": "Juego De Mesa Thinkblot De Los Creadores De Pictionary Usado",
            "condition": "used",
            "thumbnail_id": "995140-MLM44785495231_022021",
            "catalog_product_id": null,
            "listing_type_id": "gold_special",
            "permalink": "https://articulo.mercadolibre.com.mx/MLM-870963191-juego-de-mesa-thinkblot-de-los-creadores-de-pictionary-usado-_JM",
            "buying_mode": "buy_it_now",
            "site_id": "MLM",
            "category_id": "MLM1161",
            "domain_id": "MLM-BOARD_GAMES",
            "thumbnail": "http://http2.mlstatic.com/D_995140-MLM44785495231_022021-I.jpg",
            "currency_id": "MXN",
            "order_backend": 1,
            "price": 350,
            "original_price": null,
            "sale_price": null,
            "sold_quantity": 0,
            "available_quantity": 1,
            "official_store_id": null,
            "use_thumbnail_id": true,
            "accepts_mercadopago": true,
            "tags": [
                "good_quality_picture",
                "immediate_payment",
                "cart_eligible"
            ],
            "shipping": {
                "store_pick_up": false,
                "free_shipping": false,
                "logistic_type": "xd_drop_off",
                "mode": "me2",
                "tags": [],
                "benefits": null,
                "promise": null
            },
            "stop_time": "2041-01-28T04:00:00.000Z",
            "seller": {
                "id": 83798089,
                "nickname": "ECCOLLECTOR",
                "car_dealer": false,
                "real_estate_agency": false,
                "_": false,
                "registration_date": "2005-08-08T12:50:02.000-04:00",
                "tags": [
                    "normal",
                    "credits_profile",
                    "mshops",
                    "messages_as_seller"
                ],
                "car_dealer_logo": "",
                "permalink": "http://perfil.mercadolibre.com.mx/ECCOLLECTOR",
                "seller_reputation": {
                    "level_id": "4_light_green",
                    "power_seller_status": null,
                    "transactions": {
                        "canceled": 7,
                        "completed": 130,
                        "period": "historic",
                        "ratings": {
                            "negative": 0,
                            "neutral": 0.02,
                            "positive": 0.98
                        },
                        "total": 137
                    },
                    "metrics": {
                        "sales": {
                            "period": "365 days",
                            "completed": 130
                        },
                        "claims": {
                            "period": "365 days",
                            "rate": 0,
                            "value": 0,
                            "excluded": {
                                "real_value": 3,
                                "real_rate": 0.0218
                            }
                        },
                        "delayed_handling_time": {
                            "period": "365 days",
                            "rate": 0,
                            "value": 0,
                            "excluded": {
                                "real_value": 4,
                                "real_rate": 0.0303
                            }
                        },
                        "cancellations": {
                            "period": "365 days",
                            "rate": 0,
                            "value": 0,
                            "excluded": {
                                "real_value": 1,
                                "real_rate": 0.0072
                            }
                        }
                    }
                }
            },
            "seller_address": {
                "comment": "",
                "address_line": "",
                "id": null,
                "latitude": null,
                "longitude": null,
                "country": {
                    "id": "MX",
                    "name": "Mexico"
                },
                "state": {
                    "id": "MX-JAL",
                    "name": "Jalisco"
                },
                "city": {
                    "id": "TUxNQ1pBUDM4NzE",
                    "name": "Zapopan"
                }
            },
            "address": {
                "state_id": "MX-JAL",
                "state_name": "Jalisco",
                "city_id": "TUxNQ1pBUDM4NzE",
                "city_name": "Zapopan"
            },
            "attributes": [
                {
                    "id": "ALPHANUMERIC_MODEL",
                    "name": "Modelo alfanumérico",
                    "value_id": null,
                    "value_name": "1qws",
                    "attribute_group_id": "OTHERS",
                    "attribute_group_name": "Otros",
                    "value_struct": null,
                    "values": [
                        {
                            "id": null,
                            "name": "1qws",
                            "struct": null,
                            "source": 7092
                        }
                    ],
                    "source": 7092,
                    "value_type": "string"
                },
                {
                    "id": "BRAND",
                    "name": "Marca",
                    "value_id": "428616",
                    "value_name": "Mattel games",
                    "attribute_group_id": "OTHERS",
                    "attribute_group_name": "Otros",
                    "value_struct": null,
                    "values": [
                        {
                            "id": "428616",
                            "name": "Mattel games",
                            "struct": null,
                            "source": 7092
                        }
                    ],
                    "source": 7092,
                    "value_type": "string"
                },
                {
                    "id": "ITEM_CONDITION",
                    "name": "Condición del ítem",
                    "value_id": "2230581",
                    "value_name": "Usado",
                    "attribute_group_id": "OTHERS",
                    "attribute_group_name": "Otros",
                    "value_struct": null,
                    "values": [
                        {
                            "id": "2230581",
                            "name": "Usado",
                            "struct": null,
                            "source": 7092
                        }
                    ],
                    "source": 7092,
                    "value_type": "list"
                }
            ],
            "installments": {
                "quantity": 24,
                "amount": 21.15,
                "rate": 45.03,
                "currency_id": "MXN"
            },
            "winner_item_id": null,
            "catalog_listing": false,
            "discounts": null,
            "promotions": [],
            "inventory_id": null
        }
    ],
    "sort": {
        "id": "relevance",
        "name": "Más relevantes"
    },
    "available_sorts": [
        {
            "id": "price_asc",
            "name": "Menor precio"
        },
        {
            "id": "price_desc",
            "name": "Mayor precio"
        }
    ],
    "filters": [],
    "available_filters": [
        {
            "id": "category",
            "name": "Categorías",
            "type": "text",
            "values": [
                {
                    "id": "MLM432988",
                    "name": "Juegos de Mesa y Cartas",
                    "results": 1
                },
                {
                    "id": "MLM192051",
                    "name": "Repuestos para Celulares",
                    "results": 1
                }
            ]
        },
        {
            "id": "accepts_mercadopago",
            "name": "Filtro por MercadoPago",
            "type": "boolean",
            "values": [
                {
                    "id": "yes",
                    "name": "Con MercadoPago",
                    "results": 2
                }
            ]
        },
        {
            "id": "installments",
            "name": "Pago",
            "type": "text",
            "values": [
                {
                    "id": "yes",
                    "name": "En mensualidades",
                    "results": 2
                },
                {
                    "id": "no_interest",
                    "name": "Meses sin intereses",
                    "results": 1
                }
            ]
        },
        {
            "id": "shipping",
            "name": "Tipo de envío",
            "type": "text",
            "values": [
                {
                    "id": "mercadoenvios",
                    "name": "Mercado Envíos",
                    "results": 2
                }
            ]
        },
        {
            "id": "power_seller",
            "name": "Filtro por calidad de vendedores",
            "type": "boolean",
            "values": [
                {
                    "id": "yes",
                    "name": "Mejores vendedores",
                    "results": 1
                }
            ]
        },
        {
            "id": "has_pictures",
            "name": "Filtro por publicaciones con imágenes",
            "type": "boolean",
            "values": [
                {
                    "id": "yes",
                    "name": "Con fotos",
                    "results": 2
                }
            ]
        },
        {
            "id": "shipping_cost",
            "name": "Costo de envío",
            "type": "text",
            "values": [
                {
                    "id": "free",
                    "name": "Gratis",
                    "results": 1
                }
            ]
        },
        {
            "id": "BRAND",
            "name": "Marca",
            "type": "STRING",
            "values": [
                {
                    "id": "428616",
                    "name": "Mattel games",
                    "results": 1
                },
                {
                    "id": "206",
                    "name": "Samsung",
                    "results": 1
                }
            ]
        },
        {
            "id": "ITEM_CONDITION",
            "name": "Condición",
            "type": "STRING",
            "values": [
                {
                    "id": "2230581",
                    "name": "Usado",
                    "results": 1
                },
                {
                    "id": "2230284",
                    "name": "Nuevo",
                    "results": 1
                }
            ]
        },
        {
            "id": "SHIPPING_ORIGIN",
            "name": "Tipo de compra",
            "type": "STRING",
            "values": [
                {
                    "id": "10215068",
                    "name": "Local",
                    "results": 2
                }
            ]
        },
        {
            "id": "state",
            "name": "Ubicación",
            "type": "text",
            "values": [
                {
                    "id": "TUxNUE1JQzg2Nzc",
                    "name": "Michoacán",
                    "results": 1
                },
                {
                    "id": "TUxNUEpBTDM3MTg",
                    "name": "Jalisco",
                    "results": 1
                }
            ]
        }
    ],
    "pads_info": {
        "tracelog": [
            "mclics_off_cause=no_d2id;mclics_off"
        ]
    }
}
"""