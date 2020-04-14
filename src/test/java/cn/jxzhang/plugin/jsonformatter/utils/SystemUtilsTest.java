package cn.jxzhang.plugin.jsonformatter.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * SystemUtilsTest
 *
 * @author zhangjiaxing created on 2020-04-14
 */
public class SystemUtilsTest {

    @Test
    public void testCopyToClipboard() {
        SystemUtils.copyToClipboard("foo");

        // write nothing
        SystemUtils.copyToClipboard(null);
    }
}