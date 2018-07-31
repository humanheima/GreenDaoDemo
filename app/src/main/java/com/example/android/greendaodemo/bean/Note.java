package com.example.android.greendaodemo.bean;

import com.example.android.greendaodemo.NoteType;
import com.example.android.greendaodemo.NoteTypeConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by DuMingwei on 2018/7/31.
 * Description:
 */
@Entity
public class Note {

    @Id
    private Long id;

    @NotNull
    private String text;
    private Date date;
    @Convert(converter = NoteTypeConvert.class, columnType = String.class)
    private NoteType type;

    @Generated(hash = 2139673067)
    public Note(Long id, @NotNull String text, Date date, NoteType type) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.type = type;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NoteType getType() {
        return this.type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
