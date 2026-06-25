package com.textgame.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.textgame.data.local.db.entity.SummaryEntity;
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
public final class SummaryDao_Impl implements SummaryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SummaryEntity> __insertionAdapterOfSummaryEntity;

  private final EntityDeletionOrUpdateAdapter<SummaryEntity> __updateAdapterOfSummaryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSummariesBySessionId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSummariesAfterTurn;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSummariesOverlappingTurn;

  private final EntityUpsertionAdapter<SummaryEntity> __upsertionAdapterOfSummaryEntity;

  public SummaryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSummaryEntity = new EntityInsertionAdapter<SummaryEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `summaries` (`id`,`sessionId`,`summaryText`,`keyEventsJson`,`involvedNPCsJson`,`sceneContext`,`turnRangeStart`,`turnRangeEnd`,`generatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SummaryEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getSummaryText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSummaryText());
        }
        if (value.getKeyEventsJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getKeyEventsJson());
        }
        if (value.getInvolvedNPCsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getInvolvedNPCsJson());
        }
        if (value.getSceneContext() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSceneContext());
        }
        stmt.bindLong(7, value.getTurnRangeStart());
        stmt.bindLong(8, value.getTurnRangeEnd());
        stmt.bindLong(9, value.getGeneratedAt());
      }
    };
    this.__updateAdapterOfSummaryEntity = new EntityDeletionOrUpdateAdapter<SummaryEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `summaries` SET `id` = ?,`sessionId` = ?,`summaryText` = ?,`keyEventsJson` = ?,`involvedNPCsJson` = ?,`sceneContext` = ?,`turnRangeStart` = ?,`turnRangeEnd` = ?,`generatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SummaryEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getSummaryText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSummaryText());
        }
        if (value.getKeyEventsJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getKeyEventsJson());
        }
        if (value.getInvolvedNPCsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getInvolvedNPCsJson());
        }
        if (value.getSceneContext() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSceneContext());
        }
        stmt.bindLong(7, value.getTurnRangeStart());
        stmt.bindLong(8, value.getTurnRangeEnd());
        stmt.bindLong(9, value.getGeneratedAt());
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteSummariesBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM summaries WHERE sessionId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSummariesAfterTurn = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM summaries WHERE sessionId = ? AND turnRangeStart > ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSummariesOverlappingTurn = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM summaries WHERE sessionId = ? AND turnRangeEnd > ?";
        return _query;
      }
    };
    this.__upsertionAdapterOfSummaryEntity = new EntityUpsertionAdapter<SummaryEntity>(new EntityInsertionAdapter<SummaryEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT INTO `summaries` (`id`,`sessionId`,`summaryText`,`keyEventsJson`,`involvedNPCsJson`,`sceneContext`,`turnRangeStart`,`turnRangeEnd`,`generatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SummaryEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getSummaryText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSummaryText());
        }
        if (value.getKeyEventsJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getKeyEventsJson());
        }
        if (value.getInvolvedNPCsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getInvolvedNPCsJson());
        }
        if (value.getSceneContext() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSceneContext());
        }
        stmt.bindLong(7, value.getTurnRangeStart());
        stmt.bindLong(8, value.getTurnRangeEnd());
        stmt.bindLong(9, value.getGeneratedAt());
      }
    }, new EntityDeletionOrUpdateAdapter<SummaryEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE `summaries` SET `id` = ?,`sessionId` = ?,`summaryText` = ?,`keyEventsJson` = ?,`involvedNPCsJson` = ?,`sceneContext` = ?,`turnRangeStart` = ?,`turnRangeEnd` = ?,`generatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SummaryEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getSummaryText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSummaryText());
        }
        if (value.getKeyEventsJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getKeyEventsJson());
        }
        if (value.getInvolvedNPCsJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getInvolvedNPCsJson());
        }
        if (value.getSceneContext() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSceneContext());
        }
        stmt.bindLong(7, value.getTurnRangeStart());
        stmt.bindLong(8, value.getTurnRangeEnd());
        stmt.bindLong(9, value.getGeneratedAt());
        stmt.bindLong(10, value.getId());
      }
    });
  }

  @Override
  public Object insertSummary(final SummaryEntity summary,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfSummaryEntity.insertAndReturnId(summary);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSummary(final SummaryEntity summary,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSummaryEntity.handle(summary);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSummariesBySessionId(final long sessionId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSummariesBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSummariesBySessionId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSummariesAfterTurn(final long sessionId, final int turn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSummariesAfterTurn.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, turn);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSummariesAfterTurn.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSummariesOverlappingTurn(final long sessionId, final int turn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSummariesOverlappingTurn.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, turn);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSummariesOverlappingTurn.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertSummary(final SummaryEntity summary,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __upsertionAdapterOfSummaryEntity.upsertAndReturnId(summary);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLatestSummary(final long sessionId,
      final Continuation<? super SummaryEntity> $completion) {
    final String _sql = "SELECT * FROM summaries WHERE sessionId = ? ORDER BY generatedAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SummaryEntity>() {
      @Override
      public SummaryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfSummaryText = CursorUtil.getColumnIndexOrThrow(_cursor, "summaryText");
          final int _cursorIndexOfKeyEventsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "keyEventsJson");
          final int _cursorIndexOfInvolvedNPCsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedNPCsJson");
          final int _cursorIndexOfSceneContext = CursorUtil.getColumnIndexOrThrow(_cursor, "sceneContext");
          final int _cursorIndexOfTurnRangeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "turnRangeStart");
          final int _cursorIndexOfTurnRangeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "turnRangeEnd");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final SummaryEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSummaryText;
            if (_cursor.isNull(_cursorIndexOfSummaryText)) {
              _tmpSummaryText = null;
            } else {
              _tmpSummaryText = _cursor.getString(_cursorIndexOfSummaryText);
            }
            final String _tmpKeyEventsJson;
            if (_cursor.isNull(_cursorIndexOfKeyEventsJson)) {
              _tmpKeyEventsJson = null;
            } else {
              _tmpKeyEventsJson = _cursor.getString(_cursorIndexOfKeyEventsJson);
            }
            final String _tmpInvolvedNPCsJson;
            if (_cursor.isNull(_cursorIndexOfInvolvedNPCsJson)) {
              _tmpInvolvedNPCsJson = null;
            } else {
              _tmpInvolvedNPCsJson = _cursor.getString(_cursorIndexOfInvolvedNPCsJson);
            }
            final String _tmpSceneContext;
            if (_cursor.isNull(_cursorIndexOfSceneContext)) {
              _tmpSceneContext = null;
            } else {
              _tmpSceneContext = _cursor.getString(_cursorIndexOfSceneContext);
            }
            final int _tmpTurnRangeStart;
            _tmpTurnRangeStart = _cursor.getInt(_cursorIndexOfTurnRangeStart);
            final int _tmpTurnRangeEnd;
            _tmpTurnRangeEnd = _cursor.getInt(_cursorIndexOfTurnRangeEnd);
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _result = new SummaryEntity(_tmpId,_tmpSessionId,_tmpSummaryText,_tmpKeyEventsJson,_tmpInvolvedNPCsJson,_tmpSceneContext,_tmpTurnRangeStart,_tmpTurnRangeEnd,_tmpGeneratedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllSummaries(final long sessionId,
      final Continuation<? super List<SummaryEntity>> $completion) {
    final String _sql = "SELECT * FROM summaries WHERE sessionId = ? ORDER BY generatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SummaryEntity>>() {
      @Override
      public List<SummaryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfSummaryText = CursorUtil.getColumnIndexOrThrow(_cursor, "summaryText");
          final int _cursorIndexOfKeyEventsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "keyEventsJson");
          final int _cursorIndexOfInvolvedNPCsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedNPCsJson");
          final int _cursorIndexOfSceneContext = CursorUtil.getColumnIndexOrThrow(_cursor, "sceneContext");
          final int _cursorIndexOfTurnRangeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "turnRangeStart");
          final int _cursorIndexOfTurnRangeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "turnRangeEnd");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final List<SummaryEntity> _result = new ArrayList<SummaryEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final SummaryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSummaryText;
            if (_cursor.isNull(_cursorIndexOfSummaryText)) {
              _tmpSummaryText = null;
            } else {
              _tmpSummaryText = _cursor.getString(_cursorIndexOfSummaryText);
            }
            final String _tmpKeyEventsJson;
            if (_cursor.isNull(_cursorIndexOfKeyEventsJson)) {
              _tmpKeyEventsJson = null;
            } else {
              _tmpKeyEventsJson = _cursor.getString(_cursorIndexOfKeyEventsJson);
            }
            final String _tmpInvolvedNPCsJson;
            if (_cursor.isNull(_cursorIndexOfInvolvedNPCsJson)) {
              _tmpInvolvedNPCsJson = null;
            } else {
              _tmpInvolvedNPCsJson = _cursor.getString(_cursorIndexOfInvolvedNPCsJson);
            }
            final String _tmpSceneContext;
            if (_cursor.isNull(_cursorIndexOfSceneContext)) {
              _tmpSceneContext = null;
            } else {
              _tmpSceneContext = _cursor.getString(_cursorIndexOfSceneContext);
            }
            final int _tmpTurnRangeStart;
            _tmpTurnRangeStart = _cursor.getInt(_cursorIndexOfTurnRangeStart);
            final int _tmpTurnRangeEnd;
            _tmpTurnRangeEnd = _cursor.getInt(_cursorIndexOfTurnRangeEnd);
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _item = new SummaryEntity(_tmpId,_tmpSessionId,_tmpSummaryText,_tmpKeyEventsJson,_tmpInvolvedNPCsJson,_tmpSceneContext,_tmpTurnRangeStart,_tmpTurnRangeEnd,_tmpGeneratedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
