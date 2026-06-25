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
import com.textgame.data.local.db.entity.StateSnapshotEntity;
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
public final class StateSnapshotDao_Impl implements StateSnapshotDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StateSnapshotEntity> __insertionAdapterOfStateSnapshotEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSnapshotsFromTurn;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSnapshotsBySessionId;

  public StateSnapshotDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStateSnapshotEntity = new EntityInsertionAdapter<StateSnapshotEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `state_snapshots` (`id`,`sessionId`,`turnNumber`,`protagonistJson`,`npcsJson`,`gameStateJson`,`worldSettingJson`,`backgroundSettingJson`,`summaryJson`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, StateSnapshotEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        stmt.bindLong(3, value.getTurnNumber());
        if (value.getProtagonistJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProtagonistJson());
        }
        if (value.getNpcsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNpcsJson());
        }
        if (value.getGameStateJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGameStateJson());
        }
        if (value.getWorldSettingJson() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getWorldSettingJson());
        }
        if (value.getBackgroundSettingJson() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getBackgroundSettingJson());
        }
        if (value.getSummaryJson() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getSummaryJson());
        }
        stmt.bindLong(10, value.getCreatedAt());
      }
    };
    this.__preparedStmtOfDeleteSnapshotsFromTurn = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM state_snapshots WHERE sessionId = ? AND turnNumber >= ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSnapshotsBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM state_snapshots WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSnapshot(final StateSnapshotEntity snapshot,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfStateSnapshotEntity.insertAndReturnId(snapshot);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteSnapshotsFromTurn(final long sessionId, final int fromTurn,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSnapshotsFromTurn.acquire();
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
          __preparedStmtOfDeleteSnapshotsFromTurn.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object deleteSnapshotsBySessionId(final long sessionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSnapshotsBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSnapshotsBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getSnapshotByTurn(final long sessionId, final int turnNumber,
      final Continuation<? super StateSnapshotEntity> arg2) {
    final String _sql = "SELECT * FROM state_snapshots WHERE sessionId = ? AND turnNumber = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, turnNumber);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StateSnapshotEntity>() {
      @Override
      public StateSnapshotEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfTurnNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "turnNumber");
          final int _cursorIndexOfProtagonistJson = CursorUtil.getColumnIndexOrThrow(_cursor, "protagonistJson");
          final int _cursorIndexOfNpcsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "npcsJson");
          final int _cursorIndexOfGameStateJson = CursorUtil.getColumnIndexOrThrow(_cursor, "gameStateJson");
          final int _cursorIndexOfWorldSettingJson = CursorUtil.getColumnIndexOrThrow(_cursor, "worldSettingJson");
          final int _cursorIndexOfBackgroundSettingJson = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundSettingJson");
          final int _cursorIndexOfSummaryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "summaryJson");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final StateSnapshotEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final int _tmpTurnNumber;
            _tmpTurnNumber = _cursor.getInt(_cursorIndexOfTurnNumber);
            final String _tmpProtagonistJson;
            if (_cursor.isNull(_cursorIndexOfProtagonistJson)) {
              _tmpProtagonistJson = null;
            } else {
              _tmpProtagonistJson = _cursor.getString(_cursorIndexOfProtagonistJson);
            }
            final String _tmpNpcsJson;
            if (_cursor.isNull(_cursorIndexOfNpcsJson)) {
              _tmpNpcsJson = null;
            } else {
              _tmpNpcsJson = _cursor.getString(_cursorIndexOfNpcsJson);
            }
            final String _tmpGameStateJson;
            if (_cursor.isNull(_cursorIndexOfGameStateJson)) {
              _tmpGameStateJson = null;
            } else {
              _tmpGameStateJson = _cursor.getString(_cursorIndexOfGameStateJson);
            }
            final String _tmpWorldSettingJson;
            if (_cursor.isNull(_cursorIndexOfWorldSettingJson)) {
              _tmpWorldSettingJson = null;
            } else {
              _tmpWorldSettingJson = _cursor.getString(_cursorIndexOfWorldSettingJson);
            }
            final String _tmpBackgroundSettingJson;
            if (_cursor.isNull(_cursorIndexOfBackgroundSettingJson)) {
              _tmpBackgroundSettingJson = null;
            } else {
              _tmpBackgroundSettingJson = _cursor.getString(_cursorIndexOfBackgroundSettingJson);
            }
            final String _tmpSummaryJson;
            if (_cursor.isNull(_cursorIndexOfSummaryJson)) {
              _tmpSummaryJson = null;
            } else {
              _tmpSummaryJson = _cursor.getString(_cursorIndexOfSummaryJson);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new StateSnapshotEntity(_tmpId,_tmpSessionId,_tmpTurnNumber,_tmpProtagonistJson,_tmpNpcsJson,_tmpGameStateJson,_tmpWorldSettingJson,_tmpBackgroundSettingJson,_tmpSummaryJson,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg2);
  }

  @Override
  public Object getLatestSnapshot(final long sessionId,
      final Continuation<? super StateSnapshotEntity> arg1) {
    final String _sql = "SELECT * FROM state_snapshots WHERE sessionId = ? ORDER BY turnNumber DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StateSnapshotEntity>() {
      @Override
      public StateSnapshotEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfTurnNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "turnNumber");
          final int _cursorIndexOfProtagonistJson = CursorUtil.getColumnIndexOrThrow(_cursor, "protagonistJson");
          final int _cursorIndexOfNpcsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "npcsJson");
          final int _cursorIndexOfGameStateJson = CursorUtil.getColumnIndexOrThrow(_cursor, "gameStateJson");
          final int _cursorIndexOfWorldSettingJson = CursorUtil.getColumnIndexOrThrow(_cursor, "worldSettingJson");
          final int _cursorIndexOfBackgroundSettingJson = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundSettingJson");
          final int _cursorIndexOfSummaryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "summaryJson");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final StateSnapshotEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final int _tmpTurnNumber;
            _tmpTurnNumber = _cursor.getInt(_cursorIndexOfTurnNumber);
            final String _tmpProtagonistJson;
            if (_cursor.isNull(_cursorIndexOfProtagonistJson)) {
              _tmpProtagonistJson = null;
            } else {
              _tmpProtagonistJson = _cursor.getString(_cursorIndexOfProtagonistJson);
            }
            final String _tmpNpcsJson;
            if (_cursor.isNull(_cursorIndexOfNpcsJson)) {
              _tmpNpcsJson = null;
            } else {
              _tmpNpcsJson = _cursor.getString(_cursorIndexOfNpcsJson);
            }
            final String _tmpGameStateJson;
            if (_cursor.isNull(_cursorIndexOfGameStateJson)) {
              _tmpGameStateJson = null;
            } else {
              _tmpGameStateJson = _cursor.getString(_cursorIndexOfGameStateJson);
            }
            final String _tmpWorldSettingJson;
            if (_cursor.isNull(_cursorIndexOfWorldSettingJson)) {
              _tmpWorldSettingJson = null;
            } else {
              _tmpWorldSettingJson = _cursor.getString(_cursorIndexOfWorldSettingJson);
            }
            final String _tmpBackgroundSettingJson;
            if (_cursor.isNull(_cursorIndexOfBackgroundSettingJson)) {
              _tmpBackgroundSettingJson = null;
            } else {
              _tmpBackgroundSettingJson = _cursor.getString(_cursorIndexOfBackgroundSettingJson);
            }
            final String _tmpSummaryJson;
            if (_cursor.isNull(_cursorIndexOfSummaryJson)) {
              _tmpSummaryJson = null;
            } else {
              _tmpSummaryJson = _cursor.getString(_cursorIndexOfSummaryJson);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new StateSnapshotEntity(_tmpId,_tmpSessionId,_tmpTurnNumber,_tmpProtagonistJson,_tmpNpcsJson,_tmpGameStateJson,_tmpWorldSettingJson,_tmpBackgroundSettingJson,_tmpSummaryJson,_tmpCreatedAt);
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
