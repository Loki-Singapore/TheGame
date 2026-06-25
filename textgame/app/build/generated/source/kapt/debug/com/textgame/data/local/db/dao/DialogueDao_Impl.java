package com.textgame.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.textgame.data.local.db.entity.DialogueEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DialogueDao_Impl implements DialogueDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DialogueEntity> __insertionAdapterOfDialogueEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDialogueContent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDialogueById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDialoguesBySessionId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDialoguesFromTurn;

  public DialogueDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDialogueEntity = new EntityInsertionAdapter<DialogueEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `dialogues` (`id`,`sessionId`,`turnNumber`,`speaker`,`content`,`isPlayer`,`isNarrative`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DialogueEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        stmt.bindLong(3, value.getTurnNumber());
        if (value.getSpeaker() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSpeaker());
        }
        if (value.getContent() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getContent());
        }
        final int _tmp = value.isPlayer() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        final int _tmp_1 = value.isNarrative() ? 1 : 0;
        stmt.bindLong(7, _tmp_1);
        stmt.bindLong(8, value.getCreatedAt());
      }
    };
    this.__preparedStmtOfUpdateDialogueContent = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dialogues SET content = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteDialogueById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM dialogues WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteDialoguesBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM dialogues WHERE sessionId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteDialoguesFromTurn = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM dialogues WHERE sessionId = ? AND turnNumber >= ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertDialogue(final DialogueEntity dialogue,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfDialogueEntity.insertAndReturnId(dialogue);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateDialogueContent(final long id, final String content,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDialogueContent.acquire();
        int _argIndex = 1;
        if (content == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, content);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfUpdateDialogueContent.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object deleteDialogueById(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDialogueById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteDialogueById.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteDialoguesBySessionId(final long sessionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDialoguesBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteDialoguesBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteDialoguesFromTurn(final long sessionId, final int fromTurn,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDialoguesFromTurn.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, fromTurn);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteDialoguesFromTurn.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object getDialoguesBySessionId(final long sessionId,
      final Continuation<? super List<DialogueEntity>> arg1) {
    final String _sql = "SELECT * FROM dialogues WHERE sessionId = ? ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DialogueEntity>>() {
      @Override
      public List<DialogueEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfTurnNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "turnNumber");
          final int _cursorIndexOfSpeaker = CursorUtil.getColumnIndexOrThrow(_cursor, "speaker");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfIsPlayer = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlayer");
          final int _cursorIndexOfIsNarrative = CursorUtil.getColumnIndexOrThrow(_cursor, "isNarrative");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<DialogueEntity> _result = new ArrayList<DialogueEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DialogueEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final int _tmpTurnNumber;
            _tmpTurnNumber = _cursor.getInt(_cursorIndexOfTurnNumber);
            final String _tmpSpeaker;
            if (_cursor.isNull(_cursorIndexOfSpeaker)) {
              _tmpSpeaker = null;
            } else {
              _tmpSpeaker = _cursor.getString(_cursorIndexOfSpeaker);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final boolean _tmpIsPlayer;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlayer);
            _tmpIsPlayer = _tmp != 0;
            final boolean _tmpIsNarrative;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNarrative);
            _tmpIsNarrative = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new DialogueEntity(_tmpId,_tmpSessionId,_tmpTurnNumber,_tmpSpeaker,_tmpContent,_tmpIsPlayer,_tmpIsNarrative,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
