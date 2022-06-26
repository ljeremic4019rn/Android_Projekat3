package com.example.projekat3.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projekat3.data.models.stocks.GroupedStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class StockDao {

    @Query("SELECT * FROM stocks WHERE userId == :userid")
    abstract fun getAllStocksFromUser(userid: Long): Observable<List<LocalStockEntity>>

    @Query("SELECT id, symbol, name, SUM (numberOf) as sum  FROM stocks WHERE userId == :userid GROUP BY name")
    abstract fun getAllStocksFromUserGrouped(userid: Long): Observable<List<GroupedStock>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertStock(localStockEntity: LocalStockEntity): Completable

}