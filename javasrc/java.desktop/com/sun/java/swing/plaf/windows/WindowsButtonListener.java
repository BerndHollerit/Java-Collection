/*
 * Copyright (c) 1998, 2020, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.java.swing.plaf.windows;

import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonListener;

/**
 * Button Listener.
 *
 * @author Rich Schiavi
 */
public class WindowsButtonListener extends BasicButtonListener {
    public WindowsButtonListener(AbstractButton b) {
        super(b);
    }
    /*
     This class is currently not used, but exists in case customers
     were subclassing it.
     */
}
