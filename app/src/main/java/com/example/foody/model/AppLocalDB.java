package com.example.foody.model;
import com.example.foody.FoodyApp;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class}, version = 7)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
}

public class AppLocalDB {

    static public AppLocalDbRepository db =
            Room.databaseBuilder(
                    FoodyApp.context,
                    AppLocalDbRepository.class,
                    "AppLocalDb.db")
                    .fallbackToDestructiveMigration()
                    .build();

}
