package com.myai.smartreplyai.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.myai.smartreplyai.data.local.entity.UserSettingsEntity;
import java.lang.Class;
import java.lang.Exception;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserSettingsDao_Impl implements UserSettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserSettingsEntity> __insertionAdapterOfUserSettingsEntity;

  private final EntityDeletionOrUpdateAdapter<UserSettingsEntity> __updateAdapterOfUserSettingsEntity;

  public UserSettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserSettingsEntity = new EntityInsertionAdapter<UserSettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_settings` (`id`,`geminiApiKey`,`geminiModel`,`customSystemPrompt`,`overlayEnabled`,`languagePreference`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettingsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGeminiApiKey());
        statement.bindString(3, entity.getGeminiModel());
        statement.bindString(4, entity.getCustomSystemPrompt());
        final int _tmp = entity.getOverlayEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getLanguagePreference());
      }
    };
    this.__updateAdapterOfUserSettingsEntity = new EntityDeletionOrUpdateAdapter<UserSettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_settings` SET `id` = ?,`geminiApiKey` = ?,`geminiModel` = ?,`customSystemPrompt` = ?,`overlayEnabled` = ?,`languagePreference` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettingsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGeminiApiKey());
        statement.bindString(3, entity.getGeminiModel());
        statement.bindString(4, entity.getCustomSystemPrompt());
        final int _tmp = entity.getOverlayEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getLanguagePreference());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final UserSettingsEntity settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserSettingsEntity.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final UserSettingsEntity settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserSettingsEntity.handle(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserSettingsEntity> observe() {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_settings"}, new Callable<UserSettingsEntity>() {
      @Override
      @Nullable
      public UserSettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGeminiApiKey = CursorUtil.getColumnIndexOrThrow(_cursor, "geminiApiKey");
          final int _cursorIndexOfGeminiModel = CursorUtil.getColumnIndexOrThrow(_cursor, "geminiModel");
          final int _cursorIndexOfCustomSystemPrompt = CursorUtil.getColumnIndexOrThrow(_cursor, "customSystemPrompt");
          final int _cursorIndexOfOverlayEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "overlayEnabled");
          final int _cursorIndexOfLanguagePreference = CursorUtil.getColumnIndexOrThrow(_cursor, "languagePreference");
          final UserSettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpGeminiApiKey;
            _tmpGeminiApiKey = _cursor.getString(_cursorIndexOfGeminiApiKey);
            final String _tmpGeminiModel;
            _tmpGeminiModel = _cursor.getString(_cursorIndexOfGeminiModel);
            final String _tmpCustomSystemPrompt;
            _tmpCustomSystemPrompt = _cursor.getString(_cursorIndexOfCustomSystemPrompt);
            final boolean _tmpOverlayEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOverlayEnabled);
            _tmpOverlayEnabled = _tmp != 0;
            final String _tmpLanguagePreference;
            _tmpLanguagePreference = _cursor.getString(_cursorIndexOfLanguagePreference);
            _result = new UserSettingsEntity(_tmpId,_tmpGeminiApiKey,_tmpGeminiModel,_tmpCustomSystemPrompt,_tmpOverlayEnabled,_tmpLanguagePreference);
          } else {
            _result = null;
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
  public Object get(final Continuation<? super UserSettingsEntity> $completion) {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserSettingsEntity>() {
      @Override
      @Nullable
      public UserSettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGeminiApiKey = CursorUtil.getColumnIndexOrThrow(_cursor, "geminiApiKey");
          final int _cursorIndexOfGeminiModel = CursorUtil.getColumnIndexOrThrow(_cursor, "geminiModel");
          final int _cursorIndexOfCustomSystemPrompt = CursorUtil.getColumnIndexOrThrow(_cursor, "customSystemPrompt");
          final int _cursorIndexOfOverlayEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "overlayEnabled");
          final int _cursorIndexOfLanguagePreference = CursorUtil.getColumnIndexOrThrow(_cursor, "languagePreference");
          final UserSettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpGeminiApiKey;
            _tmpGeminiApiKey = _cursor.getString(_cursorIndexOfGeminiApiKey);
            final String _tmpGeminiModel;
            _tmpGeminiModel = _cursor.getString(_cursorIndexOfGeminiModel);
            final String _tmpCustomSystemPrompt;
            _tmpCustomSystemPrompt = _cursor.getString(_cursorIndexOfCustomSystemPrompt);
            final boolean _tmpOverlayEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOverlayEnabled);
            _tmpOverlayEnabled = _tmp != 0;
            final String _tmpLanguagePreference;
            _tmpLanguagePreference = _cursor.getString(_cursorIndexOfLanguagePreference);
            _result = new UserSettingsEntity(_tmpId,_tmpGeminiApiKey,_tmpGeminiModel,_tmpCustomSystemPrompt,_tmpOverlayEnabled,_tmpLanguagePreference);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
