package com.textgame.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.textgame.data.local.db.entity.GameSessionEntity;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SessionDao_Impl implements SessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GameSessionEntity> __insertionAdapterOfGameSessionEntity;

  private final EntityDeletionOrUpdateAdapter<GameSessionEntity> __updateAdapterOfGameSessionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSession;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCurrentTurn;

  public SessionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGameSessionEntity = new EntityInsertionAdapter<GameSessionEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `game_sessions` (`id`,`name`,`createdAt`,`currentTurn`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GameSessionEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getCreatedAt());
        stmt.bindLong(4, value.getCurrentTurn());
      }
    };
    this.__updateAdapterOfGameSessionEntity = new EntityDeletionOrUpdateAdapter<GameSessionEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `game_sessions` SET `id` = ?,`name` = ?,`createdAt` = ?,`currentTurn` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GameSessionEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getCreatedAt());
        stmt.bindLong(4, value.getCurrentTurn());
        stmt.bindLong(5, value.getId());
      }
    };
    this.__preparedStmtOfDeleteSession = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM game_sessions WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCurrentTurn = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE game_sessions SET currentTurn = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSession(final GameSessionEntity session,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfGameSessionEntity.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateSession(final GameSessionEntity session,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGameSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteSession(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSession.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSession.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updateCurrentTurn(final long id, final int turn,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCurrentTurn.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, turn);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfUpdateCurrentTurn.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Flow<List<GameSessionEntity>> getAllSessions() {
    final String _sql = "SELECT * FROM game_sessions ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"game_sessions"}, new Callable<List<GameSessionEntity>>() {
      @Override
      public List<GameSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCurrentTurn = CursorUtil.getColumnIndexOrThrow(_cursor, "currentTurn");
          final List<GameSessionEntity> _result = new ArrayList<GameSessionEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final GameSessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpCurrentTurn;
            _tmpCurrentTurn = _cursor.getInt(_cursorIndexOfCurrentTurn);
            _item = new GameSessionEntity(_tmpId,_tmpName,_tmpCreatedAt,_tmpCurrentTurn);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSessionById(final long id, final Continuation<? super GameSessionEntity> arg1) {
    final String _sql = "SELECT * FROM game_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GameSessionEntity>() {
      @Override
      public GameSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCurrentTurn = CursorUtil.getColumnIndexOrThrow(_cursor, "currentTurn");
          final GameSessionEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpCurrentTurn;
            _tmpCurrentTurn = _cursor.getInt(_cursorIndexOfCurrentTurn);
            _result = new GameSessionEntity(_tmpId,_tmpName,_tmpCreatedAt,_tmpCurrentTurn);
          } else {
            _result = null;
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
