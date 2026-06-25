package com.textgame.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.textgame.data.local.db.dao.BackgroundSettingDao;
import com.textgame.data.local.db.dao.BackgroundSettingDao_Impl;
import com.textgame.data.local.db.dao.DialogueDao;
import com.textgame.data.local.db.dao.DialogueDao_Impl;
import com.textgame.data.local.db.dao.GameStateDao;
import com.textgame.data.local.db.dao.GameStateDao_Impl;
import com.textgame.data.local.db.dao.NPCDao;
import com.textgame.data.local.db.dao.NPCDao_Impl;
import com.textgame.data.local.db.dao.ProtagonistDao;
import com.textgame.data.local.db.dao.ProtagonistDao_Impl;
import com.textgame.data.local.db.dao.SessionDao;
import com.textgame.data.local.db.dao.SessionDao_Impl;
import com.textgame.data.local.db.dao.StateSnapshotDao;
import com.textgame.data.local.db.dao.StateSnapshotDao_Impl;
import com.textgame.data.local.db.dao.SummaryDao;
import com.textgame.data.local.db.dao.SummaryDao_Impl;
import com.textgame.data.local.db.dao.WorldSettingDao;
import com.textgame.data.local.db.dao.WorldSettingDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GameDatabase_Impl extends GameDatabase {
  private volatile SessionDao _sessionDao;

  private volatile ProtagonistDao _protagonistDao;

  private volatile NPCDao _nPCDao;

  private volatile GameStateDao _gameStateDao;

  private volatile SummaryDao _summaryDao;

  private volatile WorldSettingDao _worldSettingDao;

  private volatile BackgroundSettingDao _backgroundSettingDao;

  private volatile DialogueDao _dialogueDao;

  private volatile StateSnapshotDao _stateSnapshotDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `game_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `currentTurn` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `protagonist_states` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `name` TEXT NOT NULL, `attributesJson` TEXT NOT NULL, `inventoryJson` TEXT NOT NULL, `relationshipsJson` TEXT NOT NULL, `location` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `npc_states` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `name` TEXT NOT NULL, `role` TEXT NOT NULL, `attributesJson` TEXT NOT NULL, `dialogueHistoryJson` TEXT NOT NULL, `mood` TEXT NOT NULL, `awareness` TEXT NOT NULL, `appearance` TEXT NOT NULL, `personality` TEXT NOT NULL, `backstory` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `game_states` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `currentScene` TEXT NOT NULL, `turnCount` INTEGER NOT NULL, `activeEventsJson` TEXT NOT NULL, `flagsJson` TEXT NOT NULL, `lastAction` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `summaries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `summaryText` TEXT NOT NULL, `keyEventsJson` TEXT NOT NULL, `involvedNPCsJson` TEXT NOT NULL, `sceneContext` TEXT NOT NULL, `turnRangeStart` INTEGER NOT NULL, `turnRangeEnd` INTEGER NOT NULL, `generatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `world_settings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `worldType` TEXT NOT NULL, `timeSetting` TEXT NOT NULL, `locationSetting` TEXT NOT NULL, `socialStructure` TEXT NOT NULL, `specialRulesJson` TEXT NOT NULL, `lore` TEXT NOT NULL, `factionsJson` TEXT NOT NULL, `locationsJson` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `background_settings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `protagonistBackground` TEXT NOT NULL, `npcBackgroundsJson` TEXT NOT NULL, `worldHistory` TEXT NOT NULL, `relationshipHistory` TEXT NOT NULL, `majorPlotThreadsJson` TEXT NOT NULL, `unlockedLoreJson` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `dialogues` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `turnNumber` INTEGER NOT NULL, `speaker` TEXT NOT NULL, `content` TEXT NOT NULL, `isPlayer` INTEGER NOT NULL, `isNarrative` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `state_snapshots` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `turnNumber` INTEGER NOT NULL, `protagonistJson` TEXT NOT NULL, `npcsJson` TEXT NOT NULL, `gameStateJson` TEXT NOT NULL, `worldSettingJson` TEXT NOT NULL, `backgroundSettingJson` TEXT NOT NULL, `summaryJson` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '6970d5e5a2ebff4b664760cae1fc5bd7')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `game_sessions`");
        _db.execSQL("DROP TABLE IF EXISTS `protagonist_states`");
        _db.execSQL("DROP TABLE IF EXISTS `npc_states`");
        _db.execSQL("DROP TABLE IF EXISTS `game_states`");
        _db.execSQL("DROP TABLE IF EXISTS `summaries`");
        _db.execSQL("DROP TABLE IF EXISTS `world_settings`");
        _db.execSQL("DROP TABLE IF EXISTS `background_settings`");
        _db.execSQL("DROP TABLE IF EXISTS `dialogues`");
        _db.execSQL("DROP TABLE IF EXISTS `state_snapshots`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsGameSessions = new HashMap<String, TableInfo.Column>(4);
        _columnsGameSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("currentTurn", new TableInfo.Column("currentTurn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGameSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGameSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGameSessions = new TableInfo("game_sessions", _columnsGameSessions, _foreignKeysGameSessions, _indicesGameSessions);
        final TableInfo _existingGameSessions = TableInfo.read(_db, "game_sessions");
        if (! _infoGameSessions.equals(_existingGameSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "game_sessions(com.textgame.data.local.db.entity.GameSessionEntity).\n"
                  + " Expected:\n" + _infoGameSessions + "\n"
                  + " Found:\n" + _existingGameSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsProtagonistStates = new HashMap<String, TableInfo.Column>(9);
        _columnsProtagonistStates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("attributesJson", new TableInfo.Column("attributesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("inventoryJson", new TableInfo.Column("inventoryJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("relationshipsJson", new TableInfo.Column("relationshipsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtagonistStates.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProtagonistStates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProtagonistStates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProtagonistStates = new TableInfo("protagonist_states", _columnsProtagonistStates, _foreignKeysProtagonistStates, _indicesProtagonistStates);
        final TableInfo _existingProtagonistStates = TableInfo.read(_db, "protagonist_states");
        if (! _infoProtagonistStates.equals(_existingProtagonistStates)) {
          return new RoomOpenHelper.ValidationResult(false, "protagonist_states(com.textgame.data.local.db.entity.ProtagonistEntity).\n"
                  + " Expected:\n" + _infoProtagonistStates + "\n"
                  + " Found:\n" + _existingProtagonistStates);
        }
        final HashMap<String, TableInfo.Column> _columnsNpcStates = new HashMap<String, TableInfo.Column>(12);
        _columnsNpcStates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("attributesJson", new TableInfo.Column("attributesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("dialogueHistoryJson", new TableInfo.Column("dialogueHistoryJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("mood", new TableInfo.Column("mood", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("awareness", new TableInfo.Column("awareness", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("appearance", new TableInfo.Column("appearance", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("personality", new TableInfo.Column("personality", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("backstory", new TableInfo.Column("backstory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNpcStates.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNpcStates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNpcStates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNpcStates = new TableInfo("npc_states", _columnsNpcStates, _foreignKeysNpcStates, _indicesNpcStates);
        final TableInfo _existingNpcStates = TableInfo.read(_db, "npc_states");
        if (! _infoNpcStates.equals(_existingNpcStates)) {
          return new RoomOpenHelper.ValidationResult(false, "npc_states(com.textgame.data.local.db.entity.NPCStateEntity).\n"
                  + " Expected:\n" + _infoNpcStates + "\n"
                  + " Found:\n" + _existingNpcStates);
        }
        final HashMap<String, TableInfo.Column> _columnsGameStates = new HashMap<String, TableInfo.Column>(8);
        _columnsGameStates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("currentScene", new TableInfo.Column("currentScene", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("turnCount", new TableInfo.Column("turnCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("activeEventsJson", new TableInfo.Column("activeEventsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("flagsJson", new TableInfo.Column("flagsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("lastAction", new TableInfo.Column("lastAction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGameStates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGameStates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGameStates = new TableInfo("game_states", _columnsGameStates, _foreignKeysGameStates, _indicesGameStates);
        final TableInfo _existingGameStates = TableInfo.read(_db, "game_states");
        if (! _infoGameStates.equals(_existingGameStates)) {
          return new RoomOpenHelper.ValidationResult(false, "game_states(com.textgame.data.local.db.entity.GameStateEntity).\n"
                  + " Expected:\n" + _infoGameStates + "\n"
                  + " Found:\n" + _existingGameStates);
        }
        final HashMap<String, TableInfo.Column> _columnsSummaries = new HashMap<String, TableInfo.Column>(9);
        _columnsSummaries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("summaryText", new TableInfo.Column("summaryText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("keyEventsJson", new TableInfo.Column("keyEventsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("involvedNPCsJson", new TableInfo.Column("involvedNPCsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("sceneContext", new TableInfo.Column("sceneContext", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("turnRangeStart", new TableInfo.Column("turnRangeStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("turnRangeEnd", new TableInfo.Column("turnRangeEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSummaries.put("generatedAt", new TableInfo.Column("generatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSummaries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSummaries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSummaries = new TableInfo("summaries", _columnsSummaries, _foreignKeysSummaries, _indicesSummaries);
        final TableInfo _existingSummaries = TableInfo.read(_db, "summaries");
        if (! _infoSummaries.equals(_existingSummaries)) {
          return new RoomOpenHelper.ValidationResult(false, "summaries(com.textgame.data.local.db.entity.SummaryEntity).\n"
                  + " Expected:\n" + _infoSummaries + "\n"
                  + " Found:\n" + _existingSummaries);
        }
        final HashMap<String, TableInfo.Column> _columnsWorldSettings = new HashMap<String, TableInfo.Column>(13);
        _columnsWorldSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("worldType", new TableInfo.Column("worldType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("timeSetting", new TableInfo.Column("timeSetting", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("locationSetting", new TableInfo.Column("locationSetting", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("socialStructure", new TableInfo.Column("socialStructure", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("specialRulesJson", new TableInfo.Column("specialRulesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("lore", new TableInfo.Column("lore", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("factionsJson", new TableInfo.Column("factionsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("locationsJson", new TableInfo.Column("locationsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorldSettings.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorldSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorldSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorldSettings = new TableInfo("world_settings", _columnsWorldSettings, _foreignKeysWorldSettings, _indicesWorldSettings);
        final TableInfo _existingWorldSettings = TableInfo.read(_db, "world_settings");
        if (! _infoWorldSettings.equals(_existingWorldSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "world_settings(com.textgame.data.local.db.entity.WorldSettingEntity).\n"
                  + " Expected:\n" + _infoWorldSettings + "\n"
                  + " Found:\n" + _existingWorldSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsBackgroundSettings = new HashMap<String, TableInfo.Column>(9);
        _columnsBackgroundSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("protagonistBackground", new TableInfo.Column("protagonistBackground", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("npcBackgroundsJson", new TableInfo.Column("npcBackgroundsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("worldHistory", new TableInfo.Column("worldHistory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("relationshipHistory", new TableInfo.Column("relationshipHistory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("majorPlotThreadsJson", new TableInfo.Column("majorPlotThreadsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("unlockedLoreJson", new TableInfo.Column("unlockedLoreJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackgroundSettings.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBackgroundSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBackgroundSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBackgroundSettings = new TableInfo("background_settings", _columnsBackgroundSettings, _foreignKeysBackgroundSettings, _indicesBackgroundSettings);
        final TableInfo _existingBackgroundSettings = TableInfo.read(_db, "background_settings");
        if (! _infoBackgroundSettings.equals(_existingBackgroundSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "background_settings(com.textgame.data.local.db.entity.BackgroundSettingEntity).\n"
                  + " Expected:\n" + _infoBackgroundSettings + "\n"
                  + " Found:\n" + _existingBackgroundSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsDialogues = new HashMap<String, TableInfo.Column>(8);
        _columnsDialogues.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("turnNumber", new TableInfo.Column("turnNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("speaker", new TableInfo.Column("speaker", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("isPlayer", new TableInfo.Column("isPlayer", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("isNarrative", new TableInfo.Column("isNarrative", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDialogues.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDialogues = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDialogues = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDialogues = new TableInfo("dialogues", _columnsDialogues, _foreignKeysDialogues, _indicesDialogues);
        final TableInfo _existingDialogues = TableInfo.read(_db, "dialogues");
        if (! _infoDialogues.equals(_existingDialogues)) {
          return new RoomOpenHelper.ValidationResult(false, "dialogues(com.textgame.data.local.db.entity.DialogueEntity).\n"
                  + " Expected:\n" + _infoDialogues + "\n"
                  + " Found:\n" + _existingDialogues);
        }
        final HashMap<String, TableInfo.Column> _columnsStateSnapshots = new HashMap<String, TableInfo.Column>(10);
        _columnsStateSnapshots.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("turnNumber", new TableInfo.Column("turnNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("protagonistJson", new TableInfo.Column("protagonistJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("npcsJson", new TableInfo.Column("npcsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("gameStateJson", new TableInfo.Column("gameStateJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("worldSettingJson", new TableInfo.Column("worldSettingJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("backgroundSettingJson", new TableInfo.Column("backgroundSettingJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("summaryJson", new TableInfo.Column("summaryJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateSnapshots.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStateSnapshots = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStateSnapshots = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStateSnapshots = new TableInfo("state_snapshots", _columnsStateSnapshots, _foreignKeysStateSnapshots, _indicesStateSnapshots);
        final TableInfo _existingStateSnapshots = TableInfo.read(_db, "state_snapshots");
        if (! _infoStateSnapshots.equals(_existingStateSnapshots)) {
          return new RoomOpenHelper.ValidationResult(false, "state_snapshots(com.textgame.data.local.db.entity.StateSnapshotEntity).\n"
                  + " Expected:\n" + _infoStateSnapshots + "\n"
                  + " Found:\n" + _existingStateSnapshots);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "6970d5e5a2ebff4b664760cae1fc5bd7", "a3e6f0a437230694aa1900fe09364bc3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "game_sessions","protagonist_states","npc_states","game_states","summaries","world_settings","background_settings","dialogues","state_snapshots");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `game_sessions`");
      _db.execSQL("DELETE FROM `protagonist_states`");
      _db.execSQL("DELETE FROM `npc_states`");
      _db.execSQL("DELETE FROM `game_states`");
      _db.execSQL("DELETE FROM `summaries`");
      _db.execSQL("DELETE FROM `world_settings`");
      _db.execSQL("DELETE FROM `background_settings`");
      _db.execSQL("DELETE FROM `dialogues`");
      _db.execSQL("DELETE FROM `state_snapshots`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SessionDao.class, SessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProtagonistDao.class, ProtagonistDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NPCDao.class, NPCDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GameStateDao.class, GameStateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SummaryDao.class, SummaryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorldSettingDao.class, WorldSettingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BackgroundSettingDao.class, BackgroundSettingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DialogueDao.class, DialogueDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StateSnapshotDao.class, StateSnapshotDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public SessionDao sessionDao() {
    if (_sessionDao != null) {
      return _sessionDao;
    } else {
      synchronized(this) {
        if(_sessionDao == null) {
          _sessionDao = new SessionDao_Impl(this);
        }
        return _sessionDao;
      }
    }
  }

  @Override
  public ProtagonistDao protagonistDao() {
    if (_protagonistDao != null) {
      return _protagonistDao;
    } else {
      synchronized(this) {
        if(_protagonistDao == null) {
          _protagonistDao = new ProtagonistDao_Impl(this);
        }
        return _protagonistDao;
      }
    }
  }

  @Override
  public NPCDao npcDao() {
    if (_nPCDao != null) {
      return _nPCDao;
    } else {
      synchronized(this) {
        if(_nPCDao == null) {
          _nPCDao = new NPCDao_Impl(this);
        }
        return _nPCDao;
      }
    }
  }

  @Override
  public GameStateDao gameStateDao() {
    if (_gameStateDao != null) {
      return _gameStateDao;
    } else {
      synchronized(this) {
        if(_gameStateDao == null) {
          _gameStateDao = new GameStateDao_Impl(this);
        }
        return _gameStateDao;
      }
    }
  }

  @Override
  public SummaryDao summaryDao() {
    if (_summaryDao != null) {
      return _summaryDao;
    } else {
      synchronized(this) {
        if(_summaryDao == null) {
          _summaryDao = new SummaryDao_Impl(this);
        }
        return _summaryDao;
      }
    }
  }

  @Override
  public WorldSettingDao worldSettingDao() {
    if (_worldSettingDao != null) {
      return _worldSettingDao;
    } else {
      synchronized(this) {
        if(_worldSettingDao == null) {
          _worldSettingDao = new WorldSettingDao_Impl(this);
        }
        return _worldSettingDao;
      }
    }
  }

  @Override
  public BackgroundSettingDao backgroundSettingDao() {
    if (_backgroundSettingDao != null) {
      return _backgroundSettingDao;
    } else {
      synchronized(this) {
        if(_backgroundSettingDao == null) {
          _backgroundSettingDao = new BackgroundSettingDao_Impl(this);
        }
        return _backgroundSettingDao;
      }
    }
  }

  @Override
  public DialogueDao dialogueDao() {
    if (_dialogueDao != null) {
      return _dialogueDao;
    } else {
      synchronized(this) {
        if(_dialogueDao == null) {
          _dialogueDao = new DialogueDao_Impl(this);
        }
        return _dialogueDao;
      }
    }
  }

  @Override
  public StateSnapshotDao stateSnapshotDao() {
    if (_stateSnapshotDao != null) {
      return _stateSnapshotDao;
    } else {
      synchronized(this) {
        if(_stateSnapshotDao == null) {
          _stateSnapshotDao = new StateSnapshotDao_Impl(this);
        }
        return _stateSnapshotDao;
      }
    }
  }
}
