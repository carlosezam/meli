package com.ezam.melichallenge.search.presentation.search_form

import com.ezam.melichallenge.search.presentation.search_form.SearchEvent
import com.ezam.melichallenge.search.presentation.search_form.SearchViewModel
import com.google.common.truth.Truth
import org.junit.Test

class SearchViewModelTest {

    @Test
    fun `buscador vacio y boton desactivado al principio`() {
        // given
        val viewModel = SearchViewModel()
        val state = viewModel.getState().value

        //when

        //then
        Truth.assertThat(state.search).isEmpty()
        Truth.assertThat(state.isSearchButtonEnabled).isFalse()
    }

    @Test
    fun `actualiza el buscador cuando el usuario teclea`() {
        //given
        val viewModel = SearchViewModel()

        //when
        viewModel.onSearchChange("lorem")

        //the
        val state = viewModel.getState().value
        Truth.assertThat(state.search).isEqualTo("lorem")
    }

    @Test
    fun `el boton NO se habilita cuando solo hay caracteres blancos`() {
        //given
        val viewModel = SearchViewModel()

        //when
        viewModel.onSearchChange("  ")

        //the
        val state = viewModel.getState().value
        Truth.assertThat(state.isSearchButtonEnabled).isFalse()
    }

    @Test
    fun `el boton SI se habilita cuando hay una texto válido`() {
        //given
        val viewModel = SearchViewModel()

        //when
        viewModel.onSearchChange("lorem")

        //the
        val state = viewModel.getState().value
        Truth.assertThat(state.isSearchButtonEnabled).isTrue()
    }

    @Test
    fun `lanza evento SearchProducts con el texto del buscador, cuando recibe llamado a onSearchClick`() {
        //given
        val viewModel = SearchViewModel()

        //when
        viewModel.onSearchChange("lorem")
        viewModel.onSearchClick()

        //the
        val state = viewModel.getState().value
        Truth.assertThat(state.uiEvent).isEqualTo( SearchEvent.SearchProducts("lorem") )
    }
}