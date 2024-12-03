package db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import controller.AlumnoDAO;
import entities.Alumno;

@Database(entities = {Alumno.class}, version = 1)
public abstract class EscuelaDB extends RoomDatabase {

    private static EscuelaDB INSTANCE;

    public abstract AlumnoDAO alumnoDAO();
    public static EscuelaDB getDB(Context context){
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                EscuelaDB.class, "escuela").build();
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
