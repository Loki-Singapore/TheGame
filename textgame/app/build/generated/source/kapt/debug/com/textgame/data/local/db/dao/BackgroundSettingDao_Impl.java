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
import com.textgame.data.local.db.entity.BackgroundSettingEntity;
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
public final class BackgroundSettingDao_Impl implements BackgroundSettingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BackgroundSettingEntity> __insertionAdapterOfBackgroundSettingEntity;

  private final EntityDeletionOrUpdateAdapter<BackgroundSettingEntity> __updateAdapterOfBackgroundSettingEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBackgroundSettingBySessionId;

  public BackgroundSettingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBackgroundSettingEntity = new EntityInsertionAdapter<BackgroundSettingEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `background_settings` (`id`,`sessionId`,`protagonistBackground`,`npcBackgroundsJson`,`worldHistory`,`relationshipHistory`,`majorPlotThreadsJson`,`unlockedLoreJson`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BackgroundSettingEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getProtagonistBackground() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getProtagonistBackground());
        }
        if (value.getNpcBackgroundsJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNpcBackgroundsJson());
        }
        if (value.getWorldHistory() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWorldHistory());
        }
        if (value.getRelationshipHistory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getRelationshipHistory());
        }
        if (value.getMajorPlotThreadsJson() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMajorPlotThreadsJson());
        }
        if (value.getUnlockedLoreJson() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getUnlockedLoreJson());
        }
        stmt.bindLong(9, value.getUpdatedAt());
      }
    };
    this.__updateAdapterOfBackgroundSettingEntity = new EntityDeletionOrUpdateAdapter<BackgroundSettingEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `background_settings` SET `id` = ?,`sessionId` = ?,`protagonistBackground` = ?,`npcBackgroundsJson` = ?,`worldHistory` = ?,`relationshipHistory` = ?,`majorPlotThreadsJson` = ?,`unlockedLoreJson` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BackgroundSettingEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getProtagonistBackground() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getProtagonistBackground());
        }
        if (value.getNpcBackgroundsJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNpcBackgroundsJson());
        }
        if (value.getWorldHistory() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWorldHistory());
        }
        if (value.getRelationshipHistory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getRelationshipHistory());
        }
        if (value.getMajorPlotThreadsJson() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMajorPlotThreadsJson());
        }
        if (value.getUnlockedLoreJson() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getUnlockedLoreJson());
        }
        stmt.bindLong(9, value.getUpdatedAt());
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteBackgroundSettingBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM background_settings WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBackgroundSetting(final BackgroundSettingEntity setting,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfBackgroundSettingEntity.insertAndReturnId(setting);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateBackgroundSetting(final BackgroundSettingEntity setting,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBackgroundSettingEntity.handle(setting);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteBackgroundSettingBySessionId(final long sessionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBackgroundSettingBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteBackgroundSettingBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getBackgroundSettingBySessionId(final long sessionId,
      final Continuation<? super BackgroundSettingEntity> arg1) {
    final String _sql = "SELECT * FROM background_settings WHERE sessionId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BackgroundSettingEntity>() {
      @Override
      public BackgroundSettingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfProtagonistBackground = CursorUtil.getColumnIndexOrThrow(_cursor, "protagonistBackground");
          final int _cursorIndexOfNpcBackgroundsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "npcBackgroundsJson");
          final int _cursorIndexOfWorldHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "worldHistory");
          final int _cursorIndexOfRelationshipHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "relationshipHistory");
          final int _cursorIndexOfMajorPlotThreadsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "majorPlotThreadsJson");
          final int _cursorIndexOfUnlockedLoreJson = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedLoreJson");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final BackgroundSettingEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpProtagonistBackground;
            if (_cursor.isNull(_cursorIndexOfProtagonistBackground)) {
              _tmpProtagonistBackground = null;
            } else {
              _tmpProtagonistBackground = _cursor.getString(_cursorIndexOfProtagonistBackground);
            }
            final String _tmpNpcBackgroundsJson;
            if (_cursor.isNull(_cursorIndexOfNpcBackgroundsJson)) {
              _tmpNpcBackgroundsJson = null;
            } else {
              _tmpNpcBackgroundsJson = _cursor.getString(_cursorIndexOfNpcBackgroundsJson);
            }
            final String _tmpWorldHistory;
            if (_cursor.isNull(_cursorIndexOfWorldHistory)) {
              _tmpWorldHistory = null;
            } else {
              _tmpWorldHistory = _cursor.getString(_cursorIndexOfWorldHistory);
            }
            final String _tmpRelationshipHistory;
            if (_cursor.isNull(_cursorIndexOfRelationshipHistory)) {
              _tmpRelationshipHistory = null;
            } else {
              _tmpRelationshipHistory = _cursor.getString(_cursorIndexOfRelationshipHistory);
            }
            final String _tmpMajorPlotThreadsJson;
            if (_cursor.isNull(_cursorIndexOfMajorPlotThreadsJson)) {
              _tmpMajorPlotThreadsJson = null;
            } else {
              _tmpMajorPlotThreadsJson = _cursor.getString(_cursorIndexOfMajorPlotThreadsJson);
            }
            final String _tmpUnlockedLoreJson;
            if (_cursor.isNull(_cursorIndexOfUnlockedLoreJson)) {
              _tmpUnlockedLoreJson = null;
            } else {
              _tmpUnlockedLoreJson = _cursor.getString(_cursorIndexOfUnlockedLoreJson);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new BackgroundSettingEntity(_tmpId,_tmpSessionId,_tmpProtagonistBackground,_tmpNpcBackgroundsJson,_tmpWorldHistory,_tmpRelationshipHistory,_tmpMajorPlotThreadsJson,_tmpUnlockedLoreJson,_tmpUpdatedAt);
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
