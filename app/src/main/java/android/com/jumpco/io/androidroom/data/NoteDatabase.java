package android.com.jumpco.io.androidroom.data;

import android.com.jumpco.io.androidroom.model.Note;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
       }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDao noteDao;

        public PopulateDbAsyncTask (NoteDatabase db){
            noteDao = db.noteDao();
        }
        /**
         * @param voids
         * @deprecated
         */
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Pack Clothes","Jeans,Shirts, Socks",1));
            noteDao.insert(new Note("Book a test drive","Call Administrator ",2));
            noteDao.insert(new Note("Visit Dave","at 15:00",3));

            return null;
        }
    }

}
