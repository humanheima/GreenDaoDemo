package com.example.android.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.greendaodemo.bean.Note;
import com.example.android.greendaodemo.bean.NoteDao;

import org.greenrobot.greendao.rx.RxDao;

import java.util.Date;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RxDao<Note, Long> noteLongRxDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_query:
                query();
                break;
            default:
                break;
        }
    }

    private void delete() {
        getNoteDao().deleteByKey(2L);
        query();
    }

    private void update() {
        Note note = new Note();
        //主键一定是要有值的
        note.setId(1L);
        note.setText("modify note");
        note.setDate(new Date());
        note.setType(NoteType.LIST);
        getNoteDao().update(note);
    }

    private void query() {
        List<Note> notes = getNoteDao().queryBuilder().build().list();
        for (Note note : notes) {
            Log.d(TAG, "query: " + note.toString());
        }
    }

    private void insert() {
        Log.d(TAG, "before insert insert");
        Note note = new Note();
        note.setText("hello note");
        note.setDate(new Date());
        note.setType(NoteType.TEXT);
        long rowId = getNoteDao().insert(note);
        Log.d(TAG, "after insert: rodId=" + rowId);
    }

    private void insertAsynchronized() {
        Log.d(TAG, "before insert insert");

        Note note = new Note(null, "haha note", new Date(), NoteType.PICTURE);
        noteLongRxDao=getNoteDao().rx();
    }

    private NoteDao getNoteDao() {
        return App.getDaoSession().getNoteDao();
    }
}
