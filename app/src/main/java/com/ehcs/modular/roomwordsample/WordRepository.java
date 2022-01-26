package com.ehcs.modular.roomwordsample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    // to unit test the repository, you have to remove the Application dependency.
    // This adds complexity and much more code, this sample is not about testing

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    // Room executes all queries
    // Observed LiveData will notify the observer
    LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread
    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
