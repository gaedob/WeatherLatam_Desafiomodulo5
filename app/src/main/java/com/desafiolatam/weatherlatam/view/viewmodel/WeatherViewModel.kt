package com.desafiolatam.weatherlatam.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiolatam.weatherlatam.data.WeatherRepositoryImp
import com.desafiolatam.weatherlatam.data.local.WeatherEntity
import com.desafiolatam.weatherlatam.model.WeatherDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


/*
class WeatherViewModel(private val repository: WeatherRepositoryImp) : ViewModel() {

    private val _data: MutableStateFlow<List<WeatherDto>> = MutableStateFlow(emptyList())
    val weatherListStateFlow: StateFlow<List<WeatherDto>> = _data.asStateFlow()
    private val dispatcherIO : CoroutineDispatcher = Dispatchers.IO

    suspend fun getWeatherById(id: Int) = repository.getWeatherDataById()



    fun saveCityName(cityName:String){

    }


    //Este ejemplo muestra como se puede llamar a la clase repository
     suspend fun getWeather() = repository.getWeatherData().stateIn(viewModelScope)
    //

    init {
        viewModelScope.launch {
            repository.getWeatherData().collectLatest {
                if (it != null) {
                    _data.value = it
                }
            }
        }
    }
    // Función para agregar una nueva tarea
    suspend fun addTask(weatherEntity: WeatherEntity) {
        viewModelScope.launch(dispatcherIO) {
            repository.insertData(weatherEntity)
            // Volver a cargar la lista después de agregar
           // loadTasks()
        }
    }


}

*/



class WeatherViewModel(private val repository: WeatherRepositoryImp) : ViewModel() {

    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    suspend fun insertData() {
        viewModelScope.launch(dispatcherIO) {
            repository.getWeatherData().collectLatest {
                it?.let { list ->
                    if (list.isEmpty()) repository.insertData(datosIniciales())
                }
            }
        }
    }


    suspend fun getWeather() = repository.getWeatherData().stateIn(viewModelScope)

    suspend fun getWeatherDataById(id:Int) = repository.getWeatherDataById(id).stateIn(viewModelScope)

    suspend fun saveCityName(cityName:String){
        viewModelScope.launch(dispatcherIO) {
            repository.clearAll()
            repository.insertData(datosIniciales(cityName))
        }
    }

    private fun datosIniciales(cityName: String? = null): WeatherDto = WeatherDto(
        id = 0,
        currentTemp = 20.0,
        maxTemp = 26.0,
        minTemp = 11.0,
        pressure = 1014.2,
        humidity = 63.0,
        windSpeed = 13.0,
        sunrise = 1661834187,
        sunset = 1681882248,
        cityName = cityName?: "Santiago, Chile",
    )


}

