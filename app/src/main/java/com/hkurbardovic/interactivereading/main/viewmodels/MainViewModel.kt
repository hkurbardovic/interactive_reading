package com.hkurbardovic.interactivereading.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkurbardovic.interactivereading.app.InteractiveReading
import com.hkurbardovic.interactivereading.main.repositories.MainRepository
import com.hkurbardovic.interactivereading.models.Book
import com.hkurbardovic.interactivereading.polygon.Polygon
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val polygonsMutableLiveData = MutableLiveData<List<Polygon>?>()

    private val bookMutableLiveData = MutableLiveData<Book?>()

    val polygonsLiveData: LiveData<List<Polygon>?> = polygonsMutableLiveData

    private val bookLiveData: LiveData<Book?> = bookMutableLiveData

    fun getPolygons(bookId: String, page: Int) {
        viewModelScope.launch {
            polygonsMutableLiveData.value = repository.getPolygons(getBook(bookId), page)
        }
    }

    private fun getBook(bookId: String): Book? {
        if (bookLiveData.value != null) return bookLiveData.value

        val books = InteractiveReading.INSTANCE.getData()?.books ?: return null

        for (book in books) {
            if (bookId == book?.id ?: "") {
                bookMutableLiveData.value = book
                return book
            }
        }

        return null
    }
}