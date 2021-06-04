
package com.summit.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {


  /*  private val _data = MutableLiveData<CharacterDetail>()
    val data: LiveData<CharacterDetail>
        get() = _data

    private val _state = MutableLiveData<CharacterDetailViewState>()
    val state: LiveData<CharacterDetailViewState>
        get() = _state

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Fetch selected character detail info.
     *
     * @param characterId Character identifier.
     */
    fun loadCharacterDetail(characterId: Long) {
        _state.postValue(CharacterDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val result = marvelRepository.getCharacter(characterId)
                _data.postValue(characterDetailMapper.map(result))

                characterFavoriteRepository.getCharacterFavorite(characterId)?.let {
                    _state.postValue(CharacterDetailViewState.AlreadyAddedToFavorite)
                } ?: run {
                    _state.postValue(CharacterDetailViewState.AddToFavorite)
                }
            } catch (e: Exception) {
                _state.postValue(CharacterDetailViewState.Error)
            }
        }
    }

    /**
     * Store selected character to database favorite list.
     */
    fun addCharacterToFavorite() {
        _data.value?.let {
            viewModelScope.launch {
                characterFavoriteRepository.insertCharacterFavorite(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl
                )
                _state.postValue(CharacterDetailViewState.AddedToFavorite)
            }
        }
    }

    /**
     * Send interaction event for dismiss character detail view.
     */
    fun dismissCharacterDetail() {
        _state.postValue(CharacterDetailViewState.Dismiss)
    }*/
}
