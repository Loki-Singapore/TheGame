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
import com.textgame.data.local.db.entity.NPCStateEntity;
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
public final class NPCDao_Impl implements NPCDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NPCStateEntity> __insertionAdapterOfNPCStateEntity;

  private final EntityDeletionOrUpdateAdapter<NPCStateEntity> __updateAdapterOfNPCStateEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNPC;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNPCsBySessionId;

  public NPCDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNPCStateEntity = new EntityInsertionAdapter<NPCStateEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `npc_states` (`id`,`sessionId`,`name`,`role`,`attributesJson`,`dialogueHistoryJson`,`mood`,`awareness`,`appearance`,`personality`,`backstory`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NPCStateEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getRole() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRole());
        }
        if (value.getAttributesJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAttributesJson());
        }
        if (value.getDialogueHistoryJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDialogueHistoryJson());
        }
        if (value.getMood() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMood());
        }
        if (value.getAwareness() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAwareness());
        }
        if (value.getAppearance() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getAppearance());
        }
        if (value.getPersonality() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPersonality());
        }
        if (value.getBackstory() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getBackstory());
        }
        stmt.bindLong(12, value.getUpdatedAt());
      }
    };
    this.__updateAdapterOfNPCStateEntity = new EntityDeletionOrUpdateAdapter<NPCStateEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `npc_states` SET `id` = ?,`sessionId` = ?,`name` = ?,`role` = ?,`attributesJson` = ?,`dialogueHistoryJson` = ?,`mood` = ?,`awareness` = ?,`appearance` = ?,`personality` = ?,`backstory` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NPCStateEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getRole() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRole());
        }
        if (value.getAttributesJson() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAttributesJson());
        }
        if (value.getDialogueHistoryJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDialogueHistoryJson());
        }
        if (value.getMood() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMood());
        }
        if (value.getAwareness() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAwareness());
        }
        if (value.getAppearance() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getAppearance());
        }
        if (value.getPersonality() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPersonality());
        }
        if (value.getBackstory() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getBackstory());
        }
        stmt.bindLong(12, value.getUpdatedAt());
        stmt.bindLong(13, value.getId());
      }
    };
    this.__preparedStmtOfDeleteNPC = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM npc_states WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteNPCsBySessionId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM npc_states WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertNPC(final NPCStateEntity npc, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfNPCStateEntity.insertAndReturnId(npc);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateNPC(final NPCStateEntity npc, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNPCStateEntity.handle(npc);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteNPC(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNPC.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteNPC.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteNPCsBySessionId(final long sessionId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNPCsBySessionId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteNPCsBySessionId.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<NPCStateEntity>> getNPCsBySessionId(final long sessionId) {
    final String _sql = "SELECT * FROM npc_states WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"npc_states"}, new Callable<List<NPCStateEntity>>() {
      @Override
      public List<NPCStateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfAttributesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attributesJson");
          final int _cursorIndexOfDialogueHistoryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "dialogueHistoryJson");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfAwareness = CursorUtil.getColumnIndexOrThrow(_cursor, "awareness");
          final int _cursorIndexOfAppearance = CursorUtil.getColumnIndexOrThrow(_cursor, "appearance");
          final int _cursorIndexOfPersonality = CursorUtil.getColumnIndexOrThrow(_cursor, "personality");
          final int _cursorIndexOfBackstory = CursorUtil.getColumnIndexOrThrow(_cursor, "backstory");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<NPCStateEntity> _result = new ArrayList<NPCStateEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NPCStateEntity _item;
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
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpAttributesJson;
            if (_cursor.isNull(_cursorIndexOfAttributesJson)) {
              _tmpAttributesJson = null;
            } else {
              _tmpAttributesJson = _cursor.getString(_cursorIndexOfAttributesJson);
            }
            final String _tmpDialogueHistoryJson;
            if (_cursor.isNull(_cursorIndexOfDialogueHistoryJson)) {
              _tmpDialogueHistoryJson = null;
            } else {
              _tmpDialogueHistoryJson = _cursor.getString(_cursorIndexOfDialogueHistoryJson);
            }
            final String _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getString(_cursorIndexOfMood);
            }
            final String _tmpAwareness;
            if (_cursor.isNull(_cursorIndexOfAwareness)) {
              _tmpAwareness = null;
            } else {
              _tmpAwareness = _cursor.getString(_cursorIndexOfAwareness);
            }
            final String _tmpAppearance;
            if (_cursor.isNull(_cursorIndexOfAppearance)) {
              _tmpAppearance = null;
            } else {
              _tmpAppearance = _cursor.getString(_cursorIndexOfAppearance);
            }
            final String _tmpPersonality;
            if (_cursor.isNull(_cursorIndexOfPersonality)) {
              _tmpPersonality = null;
            } else {
              _tmpPersonality = _cursor.getString(_cursorIndexOfPersonality);
            }
            final String _tmpBackstory;
            if (_cursor.isNull(_cursorIndexOfBackstory)) {
              _tmpBackstory = null;
            } else {
              _tmpBackstory = _cursor.getString(_cursorIndexOfBackstory);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new NPCStateEntity(_tmpId,_tmpSessionId,_tmpName,_tmpRole,_tmpAttributesJson,_tmpDialogueHistoryJson,_tmpMood,_tmpAwareness,_tmpAppearance,_tmpPersonality,_tmpBackstory,_tmpUpdatedAt);
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
  public Object getNPCListBySessionId(final long sessionId,
      final Continuation<? super List<NPCStateEntity>> arg1) {
    final String _sql = "SELECT * FROM npc_states WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NPCStateEntity>>() {
      @Override
      public List<NPCStateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfAttributesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attributesJson");
          final int _cursorIndexOfDialogueHistoryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "dialogueHistoryJson");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfAwareness = CursorUtil.getColumnIndexOrThrow(_cursor, "awareness");
          final int _cursorIndexOfAppearance = CursorUtil.getColumnIndexOrThrow(_cursor, "appearance");
          final int _cursorIndexOfPersonality = CursorUtil.getColumnIndexOrThrow(_cursor, "personality");
          final int _cursorIndexOfBackstory = CursorUtil.getColumnIndexOrThrow(_cursor, "backstory");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<NPCStateEntity> _result = new ArrayList<NPCStateEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NPCStateEntity _item;
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
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpAttributesJson;
            if (_cursor.isNull(_cursorIndexOfAttributesJson)) {
              _tmpAttributesJson = null;
            } else {
              _tmpAttributesJson = _cursor.getString(_cursorIndexOfAttributesJson);
            }
            final String _tmpDialogueHistoryJson;
            if (_cursor.isNull(_cursorIndexOfDialogueHistoryJson)) {
              _tmpDialogueHistoryJson = null;
            } else {
              _tmpDialogueHistoryJson = _cursor.getString(_cursorIndexOfDialogueHistoryJson);
            }
            final String _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getString(_cursorIndexOfMood);
            }
            final String _tmpAwareness;
            if (_cursor.isNull(_cursorIndexOfAwareness)) {
              _tmpAwareness = null;
            } else {
              _tmpAwareness = _cursor.getString(_cursorIndexOfAwareness);
            }
            final String _tmpAppearance;
            if (_cursor.isNull(_cursorIndexOfAppearance)) {
              _tmpAppearance = null;
            } else {
              _tmpAppearance = _cursor.getString(_cursorIndexOfAppearance);
            }
            final String _tmpPersonality;
            if (_cursor.isNull(_cursorIndexOfPersonality)) {
              _tmpPersonality = null;
            } else {
              _tmpPersonality = _cursor.getString(_cursorIndexOfPersonality);
            }
            final String _tmpBackstory;
            if (_cursor.isNull(_cursorIndexOfBackstory)) {
              _tmpBackstory = null;
            } else {
              _tmpBackstory = _cursor.getString(_cursorIndexOfBackstory);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new NPCStateEntity(_tmpId,_tmpSessionId,_tmpName,_tmpRole,_tmpAttributesJson,_tmpDialogueHistoryJson,_tmpMood,_tmpAwareness,_tmpAppearance,_tmpPersonality,_tmpBackstory,_tmpUpdatedAt);
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

  @Override
  public Object getNPCById(final long id, final Continuation<? super NPCStateEntity> arg1) {
    final String _sql = "SELECT * FROM npc_states WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NPCStateEntity>() {
      @Override
      public NPCStateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfAttributesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attributesJson");
          final int _cursorIndexOfDialogueHistoryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "dialogueHistoryJson");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfAwareness = CursorUtil.getColumnIndexOrThrow(_cursor, "awareness");
          final int _cursorIndexOfAppearance = CursorUtil.getColumnIndexOrThrow(_cursor, "appearance");
          final int _cursorIndexOfPersonality = CursorUtil.getColumnIndexOrThrow(_cursor, "personality");
          final int _cursorIndexOfBackstory = CursorUtil.getColumnIndexOrThrow(_cursor, "backstory");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final NPCStateEntity _result;
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
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpAttributesJson;
            if (_cursor.isNull(_cursorIndexOfAttributesJson)) {
              _tmpAttributesJson = null;
            } else {
              _tmpAttributesJson = _cursor.getString(_cursorIndexOfAttributesJson);
            }
            final String _tmpDialogueHistoryJson;
            if (_cursor.isNull(_cursorIndexOfDialogueHistoryJson)) {
              _tmpDialogueHistoryJson = null;
            } else {
              _tmpDialogueHistoryJson = _cursor.getString(_cursorIndexOfDialogueHistoryJson);
            }
            final String _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getString(_cursorIndexOfMood);
            }
            final String _tmpAwareness;
            if (_cursor.isNull(_cursorIndexOfAwareness)) {
              _tmpAwareness = null;
            } else {
              _tmpAwareness = _cursor.getString(_cursorIndexOfAwareness);
            }
            final String _tmpAppearance;
            if (_cursor.isNull(_cursorIndexOfAppearance)) {
              _tmpAppearance = null;
            } else {
              _tmpAppearance = _cursor.getString(_cursorIndexOfAppearance);
            }
            final String _tmpPersonality;
            if (_cursor.isNull(_cursorIndexOfPersonality)) {
              _tmpPersonality = null;
            } else {
              _tmpPersonality = _cursor.getString(_cursorIndexOfPersonality);
            }
            final String _tmpBackstory;
            if (_cursor.isNull(_cursorIndexOfBackstory)) {
              _tmpBackstory = null;
            } else {
              _tmpBackstory = _cursor.getString(_cursorIndexOfBackstory);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new NPCStateEntity(_tmpId,_tmpSessionId,_tmpName,_tmpRole,_tmpAttributesJson,_tmpDialogueHistoryJson,_tmpMood,_tmpAwareness,_tmpAppearance,_tmpPersonality,_tmpBackstory,_tmpUpdatedAt);
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

  @Override
  public Object getNPCByName(final long sessionId, final String name,
      final Continuation<? super NPCStateEntity> arg2) {
    final String _sql = "SELECT * FROM npc_states WHERE sessionId = ? AND name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    _argIndex = 2;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NPCStateEntity>() {
      @Override
      public NPCStateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfAttributesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attributesJson");
          final int _cursorIndexOfDialogueHistoryJson = CursorUtil.getColumnIndexOrThrow(_cursor, "dialogueHistoryJson");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfAwareness = CursorUtil.getColumnIndexOrThrow(_cursor, "awareness");
          final int _cursorIndexOfAppearance = CursorUtil.getColumnIndexOrThrow(_cursor, "appearance");
          final int _cursorIndexOfPersonality = CursorUtil.getColumnIndexOrThrow(_cursor, "personality");
          final int _cursorIndexOfBackstory = CursorUtil.getColumnIndexOrThrow(_cursor, "backstory");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final NPCStateEntity _result;
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
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpAttributesJson;
            if (_cursor.isNull(_cursorIndexOfAttributesJson)) {
              _tmpAttributesJson = null;
            } else {
              _tmpAttributesJson = _cursor.getString(_cursorIndexOfAttributesJson);
            }
            final String _tmpDialogueHistoryJson;
            if (_cursor.isNull(_cursorIndexOfDialogueHistoryJson)) {
              _tmpDialogueHistoryJson = null;
            } else {
              _tmpDialogueHistoryJson = _cursor.getString(_cursorIndexOfDialogueHistoryJson);
            }
            final String _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getString(_cursorIndexOfMood);
            }
            final String _tmpAwareness;
            if (_cursor.isNull(_cursorIndexOfAwareness)) {
              _tmpAwareness = null;
            } else {
              _tmpAwareness = _cursor.getString(_cursorIndexOfAwareness);
            }
            final String _tmpAppearance;
            if (_cursor.isNull(_cursorIndexOfAppearance)) {
              _tmpAppearance = null;
            } else {
              _tmpAppearance = _cursor.getString(_cursorIndexOfAppearance);
            }
            final String _tmpPersonality;
            if (_cursor.isNull(_cursorIndexOfPersonality)) {
              _tmpPersonality = null;
            } else {
              _tmpPersonality = _cursor.getString(_cursorIndexOfPersonality);
            }
            final String _tmpBackstory;
            if (_cursor.isNull(_cursorIndexOfBackstory)) {
              _tmpBackstory = null;
            } else {
              _tmpBackstory = _cursor.getString(_cursorIndexOfBackstory);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new NPCStateEntity(_tmpId,_tmpSessionId,_tmpName,_tmpRole,_tmpAttributesJson,_tmpDialogueHistoryJson,_tmpMood,_tmpAwareness,_tmpAppearance,_tmpPersonality,_tmpBackstory,_tmpUpdatedAt);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
