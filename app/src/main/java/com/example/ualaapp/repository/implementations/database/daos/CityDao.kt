package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert
    suspend fun insert(cityEntity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listOfCityEntities: List<CityEntity>)

    @Query("DELETE FROM cities")
    suspend fun cleanTable()

    @Query("SELECT * FROM cities ORDER BY name ASC")
    suspend fun getAll(): List<CityEntity>

    /**
     * Este método retorna un flow que emite una lista de CityWithFavorite con un limite de 500 objetos
     * Esto es asi por varios motivos:
     *
     * - Consumo de recursos:
     *     -> Traer toda la tabla completa (aprox > 200 mil registros), genera "lags" de respuesta
     *     -> Se sobrecarga la memoria y/o el procesador, esto puede generar que la aplicacion se cierre inesperadamente
     *               + Aclaracion importante: Independientemente del dispositivo el "SO" asigna recursos finitos y reducidos a cada aplicacion, para no perder fluidez o eficiencia
     *               + Como se cerraria una aplicacion inesperadamente? Internamente el SO gestiona los recursos propios como de las apps instaladas
     *                 si por alguna razon por ej el SO necesita memoria o CPU disponible y no lo tiene, cerrará todo lo que este consumiendo mayor cantidad de recursos
     *                 para obtener el faltante de estos recursos.
     *     -> En las POCs que se hicieron para revisar el rendimiento (sin estos limites), al marcar una ciudad como favorito, tardaba en reflejar el usuario
     *        el resultado (aprox. 3 o 4 segs), esto sucede por la cadena de procesos que tambien se ejecutan en segundo plano como los flows por ej.
     *     -> El impacto de hacer un LEFT JOIN con una query custom, es casi transparente, no se visualizó perdida de fluidez en la app
     *        ni bloqueos por parte de la Base de Datos.
     *
     * - De cara al usuario:
     *     -> Un usuario NO visualiza 200.000 registros, solo se centra en los primeros 20 o 30.. y lo que no encuentra lo filtra.
     *     -> Que un usuario no vea resultados rapidos (en menos de 1 seg) puede generar un "drop" en usabildiad,
     *        rechazo a usar la aplicacion o evaluaciones en el store que sean negativas y afecten a la conversion o kpis, tanto tech como de producto.
     * **/
    @Query("""
        SELECT cities.*, 
           (f._id IS NOT NULL) AS isFavorite 
        FROM cities 
            LEFT JOIN favorites AS f ON cities._id = f._id 
            AND f.id_user = :userId
        WHERE cities.name LIKE :query || '%'
            ORDER BY cities.name ASC 
            LIMIT 500
    """)
    fun getCitiesFilteredFlow(userId: Long, query: String): Flow<List<CityWithFavorite>>

    @Transaction
    suspend fun refreshData(listOfCityEntities: List<CityEntity>) {
        cleanTable()
        insertAll(listOfCityEntities)
    }
}