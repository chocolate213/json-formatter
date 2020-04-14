package cn.jxzhang.plugin.jsonformatter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

/**
 * JsonUtilTest
 *
 * @author zhangjiaxing created on 2020-04-14
 */
public class JsonUtilTest {

    @Test
    public void testFormatJson() throws JsonProcessingException {
        JsonUtil.formatJson("{\"age\":29,\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"name\":\"mkyong\"}");
    }

    @Test
    public void testMinifyJson() throws JsonProcessingException {
        String minifiedJson = JsonUtil.minifyJson("{\"age\":29    ,\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"name\":\"mkyong\"}");
    }
}