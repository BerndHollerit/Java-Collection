/*
 * Copyright (c) 2011, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package jdk.jpackage.internal;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Log
 *
 * General purpose logging mechanism.
 */
public class Log {
    public static class Logger {
        private boolean verbose = false;
        private PrintWriter out = null;
        private PrintWriter err = null;

        // verbose defaults to true unless environment variable JPACKAGE_DEBUG
        // is set to true.
        // Then it is only set to true by using --verbose jpackage option

        public Logger() {
            verbose = ("true".equals(System.getenv("JPACKAGE_DEBUG")));
        }

        public void setVerbose() {
            verbose = true;
        }

        public boolean isVerbose() {
            return verbose;
        }

        public void setPrintWriter(PrintWriter out, PrintWriter err) {
            this.out = out;
            this.err = err;
        }

        public void flush() {
            if (out != null) {
                out.flush();
            }

            if (err != null) {
                err.flush();
            }
        }

        public void info(String msg) {
            if (out != null) {
                out.println(msg);
            } else {
                System.out.println(msg);
            }
        }

        public void fatalError(String msg) {
            if (err != null) {
                err.println(msg);
            } else {
                System.err.println(msg);
            }
        }

        public void error(String msg) {
            msg = addTimestamp(msg);
            if (err != null) {
                err.println(msg);
            } else {
                System.err.println(msg);
            }
        }

        public void verbose(Throwable t) {
            if (out != null && verbose) {
                out.print(addTimestamp(""));
                t.printStackTrace(out);
            } else if (verbose) {
                System.out.print(addTimestamp(""));
                t.printStackTrace(System.out);
            }
        }

        public void verbose(String msg) {
            msg = addTimestamp(msg);
            if (out != null && verbose) {
                out.println(msg);
            } else if (verbose) {
                System.out.println(msg);
            }
        }

        public void verbose(List<String> strings,
                List<String> output, int returnCode) {
            if (verbose) {
                StringBuffer sb = new StringBuffer("Command:\n   ");
                for (String s : strings) {
                    sb.append(" " + s);
                }
                verbose(new String(sb));
                if (output != null && !output.isEmpty()) {
                    sb = new StringBuffer("Output:");
                    for (String s : output) {
                        sb.append("\n    " + s);
                    }
                    verbose(new String(sb));
                }
                verbose("Returned: " + returnCode + "\n");
            }
        }

        private String addTimestamp(String msg) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            Date time = new Date(System.currentTimeMillis());
            return String.format("[%s] %s", sdf.format(time), msg);
        }
    }

    private static Logger delegate = null;

    public static void setLogger(Logger logger) {
        delegate = (logger != null) ? logger : new Logger();
    }

    public static void flush() {
        if (delegate != null) {
            delegate.flush();
        }
    }

    public static void info(String msg) {
        if (delegate != null) {
           delegate.info(msg);
        }
    }

    public static void fatalError(String msg) {
        if (delegate != null) {
            delegate.fatalError(msg);
        }
    }

    public static void error(String msg) {
        if (delegate != null) {
            delegate.error(msg);
        }
    }

    public static void setVerbose() {
        if (delegate != null) {
            delegate.setVerbose();
        }
    }

    public static boolean isVerbose() {
        return (delegate != null) ? delegate.isVerbose() : false;
    }

    public static void verbose(String msg) {
        if (delegate != null) {
           delegate.verbose(msg);
        }
    }

    public static void verbose(Throwable t) {
        if (delegate != null) {
           delegate.verbose(t);
        }
    }

    public static void verbose(List<String> strings, List<String> out, int ret) {
        if (delegate != null) {
           delegate.verbose(strings, out, ret);
        }
    }

}
