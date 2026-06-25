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
import com.textgame.data.local.db.entity.WorldSettingEntity;
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
public final class WorldSettingDao_Impl implements WorldSettingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorldSettingEntity> __insertionAdapterOfWorldSettingEntity;

  private final EntityDeletionOrUpdateAdapter<WorldSettingEntity> __updateAdapterOfWorldSettingEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteWorldSettingBySessionId;

  public WorldSettingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorldSettingEntity = new EntityInsertionAdapter<WorldSettingEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `world_settings` (`id`,`sessionId`,`name`,`description`,`worldType`,`timeSetting`,`locationSetting`,`socialStructure`,`specialRulesJson`,`lore`,`factionsJson`,`locationsJson`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorldSettingEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getWorldType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWorldType());
        }
        if (value.getTimeSetting() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTimeSetting());
        }
        if (value.getLocationSetting() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocationSetting());
        }
        if (value.getSocialStructure() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSocialStructure());
        }
        if (value.getSpecialRulesJson() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getSpecialRulesJson());
        }
        if (value.getLore() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getLore());
        }
        if (value.getFactionsJson() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getFactionsJson());
        }
        if (value.getLocationsJson() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getLocationsJson());
        }
        stmt.bindLong(13, value.getUpdatedAt());
      }
    };
    this.__updateAdapterOfWorldSettingEntity = new EntityDeletionOrUpdateAdapter<WorldSettingEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `world_settings` SET `id` = ?,`sessionId` = ?,`name` = ?,`description` = ?,`worldType` = ?,`timeSetting` = ?,`locationSetting` = ?,`socialStructure` = ?,`specialRulesJson` = ?,`lore` = ?,`factionsJson` = ?,`locationsJson` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorldSettingEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getWorldType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWorldType());
        }
        if (value.getTimeSetting() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTimeSetting());
        }
        if (value.getLocationSetting() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocationSetting());
        }
        if (value.getSocialStructure() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSocialStructure());
        }
        if (value.getSpecialRulesJson() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getSpecialRulesJson());
        }
        if (value.getLore() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getLore());
        }
        if (value.getFactionsJson() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getFactionsJson());
        }
        if (value.getLocationsJson() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getLocationsJson());
        }
        stmt.bindLong(13, value.getUpdatedAt());
        stmt.bindLong(14, value.getId());
      }
    };
    this.__preparedStmtOfDeleteWorldSettingBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM world_settings WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWorldSetting(final WorldSettingEntity setting,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWorldSettingEntity.insertAndReturnId(setting);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateWorldSetting(final WorldSettingEntity setting,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorldSettingEntity.handle(setting);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteWorldSettingBySessionId(final long sessionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteWorldSettingBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteWorldSettingBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getWorldSettingBySessionId(final long sessionId,
      final Continuation<? super WorldSettingEntity> arg1) {
    final String _sql = "SELECT * FROM world_settings WHERE sessionId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorldSettingEntity>() {
      @Override
      public WorldSettingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfWorldType = CursorUtil.getColumnIndexOrThrow(_cursor, "worldType");
          final int _cursorIndexOfTimeSetting = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSetting");
          final int _cursorIndexOfLocationSetting = CursorUtil.getColumnIndexOrThrow(_cursor, "locationSetting");
          final int _cursorIndexOfSocialStructure = CursorUtil.getColumnIndexOrThrow(_cursor, "socialStructure");
          final int _cursorIndexOfSpecialRulesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "specialRulesJson");
          final int _cursorIndexOfLore = CursorUtil.getColumnIndexOrThrow(_cursor, "lore");
          final int _cursorIndexOfFactionsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "factionsJson");
          final int _cursorIndexOfLocationsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "locationsJson");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final WorldSettingEntity _result;
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpWorldType;
            if (_cursor.isNull(_cursorIndexOfWorldType)) {
              _tmpWorldType = null;
            } else {
              _tmpWorldType = _cursor.getString(_cursorIndexOfWorldType);
            }
            final String _tmpTimeSetting;
            if (_cursor.isNull(_cursorIndexOfTimeSetting)) {
              _tmpTimeSetting = null;
            } else {
              _tmpTimeSetting = _cursor.getString(_cursorIndexOfTimeSetting);
            }
            final String _tmpLocationSetting;
            if (_cursor.isNull(_cursorIndexOfLocationSetting)) {
              _tmpLocationSetting = null;
            } else {
              _tmpLocationSetting = _cursor.getString(_cursorIndexOfLocationSetting);
            }
            final String _tmpSocialStructure;
            if (_cursor.isNull(_cursorIndexOfSocialStructure)) {
              _tmpSocialStructure = null;
            } else {
              _tmpSocialStructure = _cursor.getString(_cursorIndexOfSocialStructure);
            }
            final String _tmpSpecialRulesJson;
            if (_cursor.isNull(_cursorIndexOfSpecialRulesJson)) {
              _tmpSpecialRulesJson = null;
            } else {
              _tmpSpecialRulesJson = _cursor.getString(_cursorIndexOfSpecialRulesJson);
            }
            final String _tmpLore;
            if (_cursor.isNull(_cursorIndexOfLore)) {
              _tmpLore = null;
            } else {
              _tmpLore = _cursor.getString(_cursorIndexOfLore);
            }
            final String _tmpFactionsJson;
            if (_cursor.isNull(_cursorIndexOfFactionsJson)) {
              _tmpFactionsJson = null;
            } else {
              _tmpFactionsJson = _cursor.getString(_cursorIndexOfFactionsJson);
            }
            final String _tmpLocationsJson;
            if (_cursor.isNull(_cursorIndexOfLocationsJson)) {
              _tmpLocationsJson = null;
            } else {
              _tmpLocationsJson = _cursor.getString(_cursorIndexOfLocationsJson);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new WorldSettingEntity(_tmpId,_tmpSessionId,_tmpName,_tmpDescription,_tmpWorldType,_tmpTimeSetting,_tmpLocationSetting,_tmpSocialStructure,_tmpSpecialRulesJson,_tmpLore,_tmpFactionsJson,_tmpLocationsJson,_tmpUpdatedAt);
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
