package com.myai.smartreplyai.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.myai.smartreplyai.data.local.entity.AnalyticsEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AnalyticsDao_Impl implements AnalyticsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AnalyticsEntity> __insertionAdapterOfAnalyticsEntity;

  public AnalyticsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnalyticsEntity = new EntityInsertionAdapter<AnalyticsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `analytics_events` (`id`,`eventType`,`eventValue`,`metadata`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AnalyticsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEventType());
        statement.bindString(3, entity.getEventValue());
        statement.bindString(4, entity.getMetadata());
        statement.bindLong(5, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insert(final AnalyticsEntity event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAnalyticsEntity.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object countByType(final String type, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM analytics_events WHERE eventType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object topQuestions(final int limit,
      final Continuation<? super List<QuestionCount>> $completion) {
    final String _sql = "\n"
            + "        SELECT eventValue, COUNT(*) as cnt FROM analytics_events \n"
            + "        WHERE eventType = 'common_question' \n"
            + "        GROUP BY eventValue ORDER BY cnt DESC LIMIT ?\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QuestionCount>>() {
      @Override
      @NonNull
      public List<QuestionCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEventValue = 0;
          final int _cursorIndexOfCnt = 1;
          final List<QuestionCount> _result = new ArrayList<QuestionCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuestionCount _item;
            final String _tmpEventValue;
            _tmpEventValue = _cursor.getString(_cursorIndexOfEventValue);
            final int _tmpCnt;
            _tmpCnt = _cursor.getInt(_cursorIndexOfCnt);
            _item = new QuestionCount(_tmpEventValue,_tmpCnt);
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

  @Override
  public Object totalTimeSavedMinutes(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(SUM(CAST(metadata AS INTEGER)), 0) FROM analytics_events WHERE eventType = 'time_saved'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
