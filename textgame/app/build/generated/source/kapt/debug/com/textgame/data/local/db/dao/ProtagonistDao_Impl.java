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
import com.textgame.data.local.db.entity.ProtagonistEntity;
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
public final class ProtagonistDao_Impl implements ProtagonistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProtagonistEntity> __insertionAdapterOfProtagonistEntity;

  private final EntityDeletionOrUpdateAdapter<ProtagonistEntity> __updateAdapterOfProtagonistEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteProtagonistBySessionId;

  public ProtagonistDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProtagonistEntity = new EntityInsertionAdapter<ProtagonistEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `protagonist_states` (`id`,`sessionId`,`name`,`attributesJson`,`inventoryJson`,`relationshipsJson`,`location`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProtagonistEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getAttributesJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAttributesJson());
        }
        if (value.getInventoryJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getInventoryJson());
        }
        if (value.getRelationshipsJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getRelationshipsJson());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocation());
        }
        stmt.bindLong(8, value.getCreatedAt());
        stmt.bindLong(9, value.getUpdatedAt());
      }
    };
    this.__updateAdapterOfProtagonistEntity = new EntityDeletionOrUpdateAdapter<ProtagonistEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `protagonist_states` SET `id` = ?,`sessionId` = ?,`name` = ?,`attributesJson` = ?,`inventoryJson` = ?,`relationshipsJson` = ?,`location` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProtagonistEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getAttributesJson() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAttributesJson());
        }
        if (value.getInventoryJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getInventoryJson());
        }
        if (value.getRelationshipsJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getRelationshipsJson());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocation());
        }
        stmt.bindLong(8, value.getCreatedAt());
        stmt.bindLong(9, value.getUpdatedAt());
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteProtagonistBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM protagonist_states WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertProtagonist(final ProtagonistEntity protagonist,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfProtagonistEntity.insertAndReturnId(protagonist);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateProtagonist(final ProtagonistEntity protagonist,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProtagonistEntity.handle(protagonist);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteProtagonistBySessionId(final long sessionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteProtagonistBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteProtagonistBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getProtagonistBySessionId(final long sessionId,
      final Continuation<? super ProtagonistEntity> arg1) {
    final String _sql = "SELECT * FROM protagonist_states WHERE sessionId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ProtagonistEntity>() {
      @Override
      public ProtagonistEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAttributesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attributesJson");
          final int _cursorIndexOfInventoryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "inventoryJson");
          final int _cursorIndexOfRelationshipsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "relationshipsJson");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ProtagonistEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpAttributesJson;
            if (_cursor.isNull(_cursorIndexOfAttributesJson)) {
              _tmpAttributesJson = null;
            } else {
              _tmpAttributesJson = _cursor.getString(_cursorIndexOfAttributesJson);
            }
            final String _tmpInventoryJson;
            if (_cursor.isNull(_cursorIndexOfInventoryJson)) {
              _tmpInventoryJson = null;
            } else {
              _tmpInventoryJson = _cursor.getString(_cursorIndexOfInventoryJson);
            }
            final String _tmpRelationshipsJson;
            if (_cursor.isNull(_cursorIndexOfRelationshipsJson)) {
              _tmpRelationshipsJson = null;
            } else {
              _tmpRelationshipsJson = _cursor.getString(_cursorIndexOfRelationshipsJson);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ProtagonistEntity(_tmpId,_tmpSessionId,_tmpName,_tmpAttributesJson,_tmpInventoryJson,_tmpRelationshipsJson,_tmpLocation,_tmpCreatedAt,_tmpUpdatedAt);
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
