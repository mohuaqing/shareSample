package com.lazadazal.android;

import androidx.core.content.FileProvider;

/**
 * @author: Ryan.LC
 * @date: 19/04/2018
 * @version: v1.0
 * @description: custom FileProvider ,avoid confict
 */
public class ShareFileProvider extends FileProvider {

    public static String AUTHORITIES = ".share.FileProvider";
}
