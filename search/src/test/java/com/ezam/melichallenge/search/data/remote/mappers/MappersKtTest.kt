package com.ezam.melichallenge.search.data.remote.mappers

import com.ezam.melichallenge.search.data.remote.model.DetailsDTO
import com.ezam.melichallenge.search.data.remote.model.SearchDTO
import com.ezam.melichallenge.search.domain.model.Product
import com.ezam.melichallenge.search.domain.model.ProductAttribute
import com.ezam.melichallenge.search.domain.model.ProductDetails
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class MappersKtTest {


    @Test
    fun `ResultDTO to Product copia todos los campos correctamente`(){
        val dto = SearchDTO.ResultDTO(
            id = "id",
            title = "title",
            thumbnail = "thumb",
            price = 100f
        )

        val expected = Product(
            id = "id",
            name = "title",
            imageUrl = "thumb",
            price = "100.0"
        )

        Truth.assertThat( dto.toProduct()).isEqualTo(expected)
    }

    @Test
    fun `DetailsDTO to ProductDetails correctamente`(){
        val dto = DetailsDTO(
            id = "id",
            title = "title",
            price = 1.0f,
            condition = "condition",
            pictures = listOf(
                DetailsDTO.PictureDTO("secureUrl")
            ),
            attributes = listOf(
                DetailsDTO.AttributeDTO(name = "name", "valueName" )
            )
        )

        val expected = ProductDetails(
            id = "id",
            name = "title",
            imageUrls = listOf("secureUrl"),
            attributes = listOf(ProductAttribute("name", "valueName"))
        )

        Truth.assertThat(dto.toProductDetails()).isEqualTo(expected)
    }
}