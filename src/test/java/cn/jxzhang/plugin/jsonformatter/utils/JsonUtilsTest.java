package cn.jxzhang.plugin.jsonformatter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

/**
 * JsonUtilTest
 *
 * @author zhangjiaxing created on 2020-04-14
 */
public class JsonUtilsTest {

    @Test
    public void testFormatJson() throws JsonProcessingException {
        JsonUtils.formatJson("{\"age\":29,\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"name\":\"mkyong\"}");
    }

    @Test
    public void testMinifyJson() throws JsonProcessingException {
        JsonUtils.minifyJson("{\"age\":29    ,\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"name\":\"mkyong\"}");
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testEscapeJson() {
      String s = "{\\\"sources\\\":[{\\\"type\\\":\\\"Kafka\\\",\\\"id\\\":\\\"bigdata-pandora-test-01\\\",\\\"componentId\\\":1031,\\\"config\\\":{\\\"brokers\\\":\\\"kafka01-test.lianjia.com:9092,kafka02-test.lianjia.com:9092,kafka03-test.lianjia.com:9092\\\",\\\"topic\\\":\\\"bigdata-pandora-test-01\\\",\\\"group\\\":\\\"s-26\\\"},\\\"schema\\\":[{\\\"name\\\":\\\"a\\\",\\\"type\\\":\\\"LONG\\\",\\\"path\\\":\\\"$.a\\\",\\\"primary\\\":true},{\\\"name\\\":\\\"b\\\",\\\"type\\\":\\\"LONG\\\",\\\"path\\\":\\\"$.b\\\",\\\"primary\\\":false},{\\\"name\\\":\\\"ctime\\\",\\\"type\\\":\\\"STRING\\\",\\\"path\\\":\\\"$.ctime\\\",\\\"primary\\\":false},{\\\"name\\\":\\\"_data\\\",\\\"type\\\":\\\"STRING\\\",\\\"path\\\":\\\"$\\\",\\\"primary\\\":false}],\\\"watermark\\\":{\\\"field\\\":\\\"a\\\",\\\"delay\\\":\\\"0s\\\",\\\"format\\\":\\\"\\\"}}],\\\"operators\\\":[{\\\"type\\\":\\\"FILTER\\\",\\\"id\\\":\\\"filter\\\",\\\"componentId\\\":1038,\\\"config\\\":{\\\"condition\\\":\\\"\\\",\\\"fields\\\":[\\\"a\\\",\\\"b\\\",\\\"ctime\\\"]},\\\"deps\\\":[\\\"bigdata-pandora-test-01\\\"]},{\\\"type\\\":\\\"NOP\\\",\\\"id\\\":\\\"61-p-1605595197502\\\",\\\"componentId\\\":1035,\\\"config\\\":{},\\\"deps\\\":[\\\"filter\\\"]}],\\\"sinks\\\":[{\\\"type\\\":\\\"service-invoke\\\",\\\"id\\\":\\\"61-s-1605595197515\\\",\\\"componentId\\\":1036,\\\"config\\\":{\\\"brokers\\\":\\\"jx-op-queue03.zeus.lianjia.com:9092,jx-op-queue05.zeus.lianjia.com:9092,jx-op-queue04.zeus.lianjia.com:9092\\\",\\\"topic\\\":\\\"111\\\",\\\"serviceInvokeId\\\":2},\\\"deps\\\":[\\\"61-p-1605595197502\\\"]}],\\\"parallelism\\\":1}";
      String s1 = StringEscapeUtils.unescapeJava("{\\\"sources\\\":[{\\\"type\\\":\\\"Kafka\\\",\\\"id\\\":\\\"bigdata-pandora-test-01\\\",\\\"componentId\\\":1031,\\\"config\\\":{\\\"brokers\\\":\\\"kafka01-test.lianjia.com:9092,kafka02-test.lianjia.com:9092,kafka03-test.lianjia.com:9092\\\",\\\"topic\\\":\\\"bigdata-pandora-test-01\\\",\\\"group\\\":\\\"s-26\\\"},\\\"schema\\\":[{\\\"name\\\":\\\"a\\\",\\\"type\\\":\\\"LONG\\\",\\\"path\\\":\\\"$.a\\\",\\\"primary\\\":true},{\\\"name\\\":\\\"b\\\",\\\"type\\\":\\\"LONG\\\",\\\"path\\\":\\\"$.b\\\",\\\"primary\\\":false},{\\\"name\\\":\\\"ctime\\\",\\\"type\\\":\\\"STRING\\\",\\\"path\\\":\\\"$.ctime\\\",\\\"primary\\\":false},{\\\"name\\\":\\\"_data\\\",\\\"type\\\":\\\"STRING\\\",\\\"path\\\":\\\"$\\\",\\\"primary\\\":false}],\\\"watermark\\\":{\\\"field\\\":\\\"a\\\",\\\"delay\\\":\\\"0s\\\",\\\"format\\\":\\\"\\\"}}],\\\"operators\\\":[{\\\"type\\\":\\\"FILTER\\\",\\\"id\\\":\\\"filter\\\",\\\"componentId\\\":1038,\\\"config\\\":{\\\"condition\\\":\\\"\\\",\\\"fields\\\":[\\\"a\\\",\\\"b\\\",\\\"ctime\\\"]},\\\"deps\\\":[\\\"bigdata-pandora-test-01\\\"]},{\\\"type\\\":\\\"NOP\\\",\\\"id\\\":\\\"61-p-1605595197502\\\",\\\"componentId\\\":1035,\\\"config\\\":{},\\\"deps\\\":[\\\"filter\\\"]}],\\\"sinks\\\":[{\\\"type\\\":\\\"service-invoke\\\",\\\"id\\\":\\\"61-s-1605595197515\\\",\\\"componentId\\\":1036,\\\"config\\\":{\\\"brokers\\\":\\\"jx-op-queue03.zeus.lianjia.com:9092,jx-op-queue05.zeus.lianjia.com:9092,jx-op-queue04.zeus.lianjia.com:9092\\\",\\\"topic\\\":\\\"111\\\",\\\"serviceInvokeId\\\":2},\\\"deps\\\":[\\\"61-p-1605595197502\\\"]}],\\\"parallelism\\\":1}");
      String s2 = StringEscapeUtils.unescapeJava(s1);
      System.out.println(s);
      System.out.println(s1);
      System.out.println(s2);
    }
}
