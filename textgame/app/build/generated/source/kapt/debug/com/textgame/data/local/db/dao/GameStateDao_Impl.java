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
import com.textgame.data.local.db.entity.GameStateEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GameStateDao_Impl implements GameStateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GameStateEntity> __insertionAdapterOfGameStateEntity;

  private final EntityDeletionOrUpdateAdapter<GameStateEntity> __updateAdapterOfGameStateEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteGameStateBySessionId;

  public GameStateDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGameStateEntity = new EntityInsertionAdapter<GameStateEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `game_states` (`id`,`sessionId`,`currentScene`,`turnCount`,`activeEventsJson`,`flagsJson`,`lastAction`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GameStateEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getCurrentScene() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCurrentScene());
        }
        stmt.bindLong(4, value.getTurnCount());
        if (value.getActiveEventsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getActiveEventsJson());
        }
        if (value.getFlagsJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFlagsJson());
        }
        if (value.getLastAction() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLastAction());
        }
        stmt.bindLong(8, value.getUpdatedAt());
      }
    };
    this.__updateAdapterOfGameStateEntity = new EntityDeletionOrUpdateAdapter<GameStateEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `game_states` SET `id` = ?,`sessionId` = ?,`currentScene` = ?,`turnCount` = ?,`activeEventsJson` = ?,`flagsJson` = ?,`lastAction` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GameStateEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getCurrentScene() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCurrentScene());
        }
        stmt.bindLong(4, value.getTurnCount());
        if (value.getActiveEventsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getActiveEventsJson());
        }
        if (value.getFlagsJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFlagsJson());
        }
        if (value.getLastAction() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLastAction());
        }
        stmt.bindLong(8, value.getUpdatedAt());
        stmt.bindLong(9, value.getId());
      }
    };
    this.__preparedStmtOfDeleteGameStateBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM game_states WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertGameState(final GameStateEntity gameState,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfGameStateEntity.insertAndReturnId(gameState);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateGameState(final GameStateEntity gameState,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGameStateEntity.handle(gameState);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteGameStateBySessionId(final long sessionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteGameStateBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteGameStateBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getGameStateBySessionId(final long sessionId,
      final Continuation<? super GameStateEntity> arg1) {
    final String _sql = "SELECT * FROM game_states WHERE sessionId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GameStateEntity>() {
      @Override
      public GameStateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfCurrentScene = CursorUtil.getColumnIndexOrThrow(_cursor, "currentScene");
          final int _cursorIndexOfTurnCount = CursorUtil.getColumnIndexOrThrow(_cursor, "turnCount");
          final int _cursorIndexOfActiveEventsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "activeEventsJson");
          final int _cursorIndexOfFlagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "flagsJson");
          final int _cursorIndexOfLastAction = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAction");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final GameStateEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpCurrentScene;
            if (_cursor.isNull(_cursorIndexOfCurrentScene)) {
              _tmpCurrentScene = null;
            } else {
              _tmpCurrentScene = _cursor.getString(_cursorIndexOfCurrentScene);
            }
            final int _tmpTurnCount;
            _tmpTurnCount = _cursor.getInt(_cursorIndexOfTurnCount);
            final String _tmpActiveEventsJson;
            if (_cursor.isNull(_cursorIndexOfActiveEventsJson)) {
              _tmpActiveEventsJson = null;
            } else {
              _tmpActiveEventsJson = _cursor.getString(_cursorIndexOfActiveEventsJson);
            }
            final String _tmpFlagsJson;
            if (_cursor.isNull(_cursorIndexOfFlagsJson)) {
              _tmpFlagsJson = null;
            } else {
              _tmpFlagsJson = _cursor.getString(_cursorIndexOfFlagsJson);
            }
            final String _tmpLastAction;
            if (_cursor.isNull(_cursorIndexOfLastAction)) {
              _tmpLastAction = null;
            } else {
              _tmpLastAction = _cursor.getString(_cursorIndexOfLastAction);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new GameStateEntity(_tmpId,_tmpSessionId,_tmpCurrentScene,_tmpTurnCount,_tmpActiveEventsJson,_tmpFlagsJson,_tmpLastAction,_tmpUpdatedAt);
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
