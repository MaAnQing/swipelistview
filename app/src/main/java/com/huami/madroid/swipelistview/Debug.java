package com.huami.madroid.swipelistview;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class Debug {
    public static int DEBUG_NONE = -1;
    public static int DEBUG_INFO = 0;
    public static int DEBUG_WARN = 1;
    public static int DEBUG_ERROR = 2;

    /**
     * -1 不打印log 0 打印所有log 1 打印警告以上的log 2 打印错误的log
     */
    public static int DEBUG_LEVEL = DEBUG_INFO;

    /**
     * 是否写日志到文件
     */
    public static boolean DEBUG_FILE = true;

    /**
     * 根据调用栈关系动态调整STACK_LEVEL的值；
     * 如 0 为最底层，那么打印的将是 Debug.java _FUNC_；
     * 如 1 为直接调用者的信息； 
     */
    public static int STACK_LEVEL = 2;

    private static final int LOG_FILE_MAX_SIZE = 20 * 1024 * 1024; // 20M

    public static void enable(boolean enable) {
        DEBUG_LEVEL = enable ? DEBUG_INFO : DEBUG_NONE;
        DEBUG_FILE = enable;
        enableM(enable);
    }

    public static void enable(boolean logcatEnable, boolean fileLogEnable) {
        DEBUG_LEVEL = logcatEnable ? DEBUG_INFO : DEBUG_NONE;
        enableM(logcatEnable);
        DEBUG_FILE = fileLogEnable;
    }

    public static boolean isEnabled() {
        return DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_ERROR;
    }

    public static boolean isEnaledFile() {
        return DEBUG_FILE;
    }

    private static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[STACK_LEVEL];
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        String className = traceElement.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        sb.append(className);
        sb.append(":");
        sb.append(traceElement.getMethodName());
        sb.append(":");
        sb.append(traceElement.getLineNumber());
        sb.append("> ");
        return sb.toString();
    }

    private static String _TIME_() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }

    public static void line() {
        if (DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_WARN) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[STACK_LEVEL - 1];

            StringBuilder sb = new StringBuilder();
            sb.append(traceElement.getMethodName());
            sb.append(":");
            sb.append(traceElement.getLineNumber());

            String className = traceElement.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            Log.i(className, sb.toString());
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_WARN) {
	        Log.i(tag, _FUNC_() + message);
        }
    }

    public static void l(String tag, String message) {
        if (DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_WARN) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.i(tag, _FUNC_() + message.substring(start, end));
            }
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_ERROR) {
	        Log.w(tag, _FUNC_() + message);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_ERROR) {
	        Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG_LEVEL > DEBUG_NONE) {
	        Log.e(tag, _FUNC_() + message);
        }
    }

    /**
     * 把日志写到文件
     * @param tag
     * @param message
     */
    public static void f(String tag, String message) {
        if (!DEBUG_FILE) {
	        return;
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/mili_log.txt");
        if (file.exists() && file.length() > LOG_FILE_MAX_SIZE) {
            file.delete();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            String formatMsg = _TIME_() + "  " + tag + "  " + message + "\n";
            fw.write(formatMsg);
            fw.close();
        } catch (IOException e) {}
    }

    public static void fi(String tag, String s) {
        if (DEBUG_LEVEL > DEBUG_NONE && DEBUG_LEVEL < DEBUG_WARN) {
	        Log.i(tag, _FUNC_() + s);
        }
        f(tag, s);
    }

    private static boolean ENABLE_LOG = false;

    private static final boolean ENABLE_VERBOSE = true;
    private static final boolean ENABLE_DEBUG = true;
    private static final boolean ENABLE_INFO = true;
    private static final boolean ENABLE_WARN = true;
    private static final boolean ENABLE_ERROR = true;

    private static boolean ENABLE_TRACE = true;
    private static boolean ENABLE_ASSERT = true;
    private static boolean ENABLE_DEBUG_LOCK = true;

    private static final boolean ENABLE_LOG_META = true;
    private static final boolean ENABLE_LOG_TRACE_INFO = false;

    private static final String DEFAULT_TAG = "DEBUG";

    public static void enableM(boolean enable) {
        ENABLE_LOG = enable;
        ENABLE_TRACE = enable;
        ENABLE_ASSERT = enable;
        ENABLE_DEBUG_LOCK = enable;
    }

    public static void ENABLE(final boolean enable_trace, final boolean enable_assert, final boolean enable_debug_lock) {
        ENABLE_LOG = true;
        ENABLE_TRACE = enable_trace;
        ENABLE_ASSERT = enable_assert;
        ENABLE_DEBUG_LOCK = enable_debug_lock;

        SHOW_LOGO();

        INFO("         ENABLE_VERBOSE: " + (ENABLE_VERBOSE ? "TRUE" : "FALSE"));
        INFO("           ENABLE_DEBUG: " + (ENABLE_DEBUG ? "TRUE" : "FALSE"));
        INFO("            ENABLE_INFO: " + (ENABLE_INFO ? "TRUE" : "FALSE"));
        INFO("            ENABLE_WARN: " + (ENABLE_WARN ? "TRUE" : "FALSE"));
        INFO("           ENABLE_ERROR: " + (ENABLE_ERROR ? "TRUE" : "FALSE"));
        INFO("           ENABLE_TRACE: " + (ENABLE_TRACE ? "TRUE" : "FALSE"));
        INFO("          ENABLE_ASSERT: " + (ENABLE_ASSERT ? "TRUE" : "FALSE"));
        INFO("      ENABLE_DEBUG_LOCK: " + (ENABLE_DEBUG_LOCK ? "TRUE" : "FALSE"));
        INFO("        ENABLE_LOG_META: " + (ENABLE_LOG_META ? "TRUE" : "FALSE"));
        INFO("  ENABLE_LOG_TRACE_INFO: " + (ENABLE_LOG_TRACE_INFO ? "TRUE" : "FALSE"));
    }

    private static void SHOW_LOGO() {
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MM.:  .:'   `:::  .:`MMMMMMMMMMM|`MMM'|MMMMMMMMMMM':  .:'   `:::  .:'.MM");
        INFO("MMMM.     :          `MMMMMMMMMM  :*'  MMMMMMMMMM'        :        .MMMM");
        INFO("MMMMM.    ::    .     `MMMMMMMM'  ::   `MMMMMMMM'   .     ::   .  .MMMMM");
        INFO("MMMMMM. :   :: ::'  :   :: ::'  :   :: ::'      :: ::'  :   :: ::.MMMMMM");
        INFO("MMMMMMM    ;::         ;::         ;::         ;::         ;::   MMMMMMM");
        INFO("MMMMMMM .:'   `:::  .:'   `:::  .:'   `:::  .:'   `:::  .:'   `::MMMMMMM");
        INFO("MMMMMM'     :           :           :           :           :    `MMMMMM");
        INFO("MMMMM'______::____      ::    .     ::    .     ::     ___._::____`MMMMM");
        INFO("MMMMMMMMMMMMMMMMMMM`---._ :: ::'  :   :: ::'  _.--::MMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMM::.         ::  .--MMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMM-.     ;::-MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM. .:' .MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM.   .MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\\ /MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMVMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM .:ZylvanaS:. MM");
        INFO("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
    }

    private static void LOG(final String TAG, final String msg, final int traceLevel, final char logLevel) {
        if (!ENABLE_LOG) {
	        return;
        }

        final String threadName = Thread.currentThread().getName();
        final int DEFAULT_TRACE_LEVEL = 4;
        final StackTraceElement trace = Thread.currentThread().getStackTrace()[DEFAULT_TRACE_LEVEL + traceLevel];
        final String fullClassName = trace.getClassName();
        final String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        final String methodName = trace.getMethodName();
        final int lineNo = trace.getLineNumber();
        final String meta = ENABLE_LOG_META ? "[" + threadName + "]" + "<" + className + ":" + methodName + ":"
                        + lineNo + "> " : "";
        final String traceInfo = ENABLE_LOG_TRACE_INFO ? " >>> at " + trace : "";
        switch (logLevel) {
            case ('v'):
                Log.v(TAG, meta + msg + traceInfo);
                break;
            case ('d'):
                Log.d(TAG, meta + msg + traceInfo);
                break;
            case ('i'):
                Log.i(TAG, meta + msg + traceInfo);
                break;
            case ('w'):
                Log.w(TAG, meta + msg + traceInfo);
                break;
            case ('e'):
                Log.e(TAG, meta + msg + traceInfo);
                break;
        }
    }

    public static void TRACE() {
        if (ENABLE_TRACE) {
	        LOG(DEFAULT_TAG, "<<<<====", 0, 'v');
        }
    }

    public static void VERBOSE(final String msg) {
        if (ENABLE_VERBOSE) {
	        LOG(DEFAULT_TAG, msg, 0, 'v');
        }
    }

    public static void DEBUG(final String msg) {
        if (ENABLE_DEBUG) {
	        LOG(DEFAULT_TAG, msg, 0, 'd');
        }
    }

    public static void INFO(final String msg) {
        if (ENABLE_INFO) {
	        LOG(DEFAULT_TAG, msg, 0, 'i');
        }
    }

    public static void WARN(final String msg) {
        if (ENABLE_WARN) {
	        LOG(DEFAULT_TAG, msg, 0, 'w');
        }
    }

    public static void ERROR(final String msg) {
        if (ENABLE_ERROR) {
	        LOG(DEFAULT_TAG, msg, 0, 'e');
        }
    }

    public static void ASSERT_TRUE(final boolean cond) {
        if (ENABLE_ASSERT && !cond) {
	        LOG(DEFAULT_TAG, ">>> `TRUE` ASSERTION FAILED <<<", 0, 'e');
        }
    }

    public static void ASSERT_NOT_NULL(final Object obj) {
        if (ENABLE_ASSERT && obj == null) {
	        LOG(DEFAULT_TAG, ">>> `NOT NULL` ASSERTION FAILED <<<", 0, 'e');
        }
    }

    public static void ASSERT_NULL(final Object obj) {
        if (ENABLE_ASSERT && obj != null) {
	        LOG(DEFAULT_TAG, ">>> `NULL` ASSERTION FAILED <<<", 0, 'e');
        }
    }

    public static void ASSERT_RUN_ON_THREAD(final Thread thread) {
        if (ENABLE_ASSERT && thread != null && Thread.currentThread().getId() != thread.getId()) {
	        LOG(DEFAULT_TAG, ">>> `RUN ON THREAD` ASSERTION FAILED <<<", 0, 'e');
        }
    }

    public static void DEBUG_LOCK(final String msg) {
        if (ENABLE_DEBUG_LOCK) {
	        LOG(DEFAULT_TAG, "LOCK#" + msg, 0, 'v');
        }
    }

}
