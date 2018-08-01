package com.example.android.greendaodemo;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.greendaodemo.bean.Note;
import com.example.android.greendaodemo.bean.NoteDao;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.Date;
import java.util.List;
import java.util.Queue;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RxDao<Note, Long> noteLongRxDao;
    private RxQuery<Note> noteRxQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteRxQuery = getNoteDao().queryBuilder().rx();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                //insert();
                insertAsynchronized();
                break;
            case R.id.btn_delete:
                //delete();
                deleteAsynchronized();
                break;
            case R.id.btn_update:
                //update();
                updateAsynchronized();
                break;
            case R.id.btn_query:
                //query();
                queryAsynchronized();
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
        noteLongRxDao = getNoteDao().rx();
        noteLongRxDao.insert(note).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Note>() {
                    @Override
                    public void call(Note note) {
                        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void updateAsynchronized() {
        Note note1 = new Note(1L, "hahahhhhh note", new Date(), NoteType.PICTURE);
        Note note2 = new Note(2L, "hahahhhhh note", new Date(), NoteType.PICTURE);
        noteLongRxDao = getNoteDao().rx();
        noteLongRxDao.updateInTx(note1, note2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object[]>() {
                    @Override
                    public void call(Object[] objects) {
                        for (Object object : objects) {
                            Log.d(TAG, "call: "+ object.toString());
                        }
                    }
                });
    }

    private void deleteAsynchronized() {
        noteLongRxDao = getNoteDao().rx();
        noteLongRxDao.deleteByKey(7L)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        query();
                    }
                });
    }

    private void queryAsynchronized() {
        noteRxQuery.list().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Note>>() {
                    @Override
                    public void call(List<Note> notes) {
                        for (Note note : notes) {
                            Log.d(TAG, "call: " + note.toString());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        Note note = new Note(null, "haha note", new Date(), NoteType.PICTURE);
        noteLongRxDao = getNoteDao().rx();
        noteLongRxDao.insert(note).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Note>() {
                    @Override
                    public void call(Note note) {
                        Log.d(TAG, "Inserted new note, ID: " + note.getId());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private NoteDao getNoteDao() {
        return App.getDaoSession().getNoteDao();
    }
}
