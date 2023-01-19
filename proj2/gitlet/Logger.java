package gitlet;
/*
 * @File:   Logger.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2022/11/24 下午12:11
 * @Version:0.0
 * @reference:
 *      the design of log level: https://github.com/LearningOS/rust-based-os-comp2022/blob/main/os2-ref/src/logging.rs
 *      singleton:  https://www.cnblogs.com/cielosun/p/6582333.html
 *                  https://www.jianshu.com/p/d35f244f3770
 *                  https://www.cnblogs.com/makefile/p/enum-singleton.html
 */

public enum Logger {
    INSTANCE;

    LogLevel logLevel = LogLevel.Info;

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void error(String message) {
        log(LogLevel.Error, message);
    }
    public void error(String message, Object...args) {
        log(LogLevel.Error, String.format(message, args));
    }

    public void warn(String message) {
        log(LogLevel.Warn, message);
    }
    public void warn(String message, Object...args) {
        log(LogLevel.Warn, String.format(message, args));
    }

    public void info(String message) {
        log(LogLevel.Info, message);
    }
    public void info(String message, Object...args) {
        log(LogLevel.Info, String.format(message, args));
    }

    public void debug(String message) {
        log(LogLevel.Debug, message);
    }
    public void debug(String message, Object...args) {
        log(LogLevel.Debug, String.format(message, args));
    }

    public void trace(String message) {
        log(LogLevel.Trace, message);
    }
    public void trace(String message, Object...args) {
        log(LogLevel.Trace, String.format(message, args));
    }

    /**
     * A stupid way to convert log level into number
     *
     * @param logLevel Logger Level
     * @return error: 0, from error to trace from 1 to 5
     */
    private int levelIntoNum(LogLevel logLevel) {
        if (logLevel == LogLevel.OFF) {
            return 0;
        } else if (logLevel == LogLevel.Error) {
            return 1;
        } else if (logLevel == LogLevel.Warn) {
            return 2;
        } else if (logLevel == LogLevel.Info) {
            return 3;
        } else if (logLevel == LogLevel.Debug) {
            return 4;
        } else if (logLevel == LogLevel.Trace) {
            return 5;
        } else {
            return 0;
        }
    }

    @Deprecated
    private String levelIntoString(LogLevel logLevel) {
        if (logLevel == LogLevel.OFF) {
            return "OFF  ";
        } else if (logLevel == LogLevel.Error) {
            return "ERROR";
        } else if (logLevel == LogLevel.Warn) {
            return "WARN ";
        } else if (logLevel == LogLevel.Info) {
            return "INFO ";
        } else if (logLevel == LogLevel.Debug) {
            return "DEBUG";
        } else if (logLevel == LogLevel.Trace) {
            return "TRACE";
        } else {
            return "NONE ";
        }
    }

    private void printColoredString(LogLevel out_level, String message) {
        if (out_level != LogLevel.OFF) {
            int num = 30;
            if (out_level == LogLevel.Error) {
                message = "[ERROR]" + message;
                num = 31;
            } else if (out_level == LogLevel.Warn) {
                message = "[WARN ]" + message;
                num = 33;
            } else if (out_level == LogLevel.Info) {
                message = "[INFO ]" + message;
                num = 34;
            } else if (out_level == LogLevel.Debug) {
                message = "[DEBUG]" + message;
                num = 32;
            } else if (out_level == LogLevel.Trace) {
                message = "[TRACE]" + message;
                num = 36;
            }
            System.out.printf("\u001B[%d;1m%s\u001B[0m%n", num, message);
        }
    }

    private void log(LogLevel out_level, String message) {
        if (levelIntoNum(out_level) != 0) {
            if (levelIntoNum(out_level) <= levelIntoNum(this.logLevel)) {
                Thread currentThread = Thread.currentThread();
                StackTraceElement[] ste = currentThread.getStackTrace();
//                StackTraceElement stackTrace = currentThread.getStackTrace()[4];
//                StackTraceElement stackTraceElement = ste[ste.length-1];
                StackTraceElement stackTraceElement = ste[3];
                String output = String.format("[%s](%s:%s): %s",
//                        levelIntoString(out_level),
//                        currentThread.getName(),
                        stackTraceElement.getMethodName(),
                        stackTraceElement.getFileName(),
                        stackTraceElement.getLineNumber(),
                        message);
                printColoredString(out_level, output);
            }
        }
    }

}