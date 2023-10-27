package io.github.biezhi.ohmyemail;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import io.github.biezhi.ome.OhMyEmail;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import static io.github.biezhi.ome.OhMyEmail.SMTP_QQ;

/**
 * @author biezhi
 * 2017/5/30
 */
public class OhMyEmailTest {

    @Before
    public void before() throws GeneralSecurityException {
        // 配置，一次即可
        OhMyEmail.config(SMTP_QQ(false), "xxx@qq.com", "*******");
    }

    @Test
    public void testSendText() throws MessagingException {
        OhMyEmail.subject("这是一封测试TEXT邮件")
                .from("王爵的QQ邮箱")
                .to("921293209@qq.com")
                .text("信件内容");
//                .send();
    }

    @Test
    public void testSendHtml() throws MessagingException {
        OhMyEmail.subject("这是一封测试HTML邮件")
                .from("王爵的QQ邮箱")
                .to("921293209@qq.com")
                .html("<h1 font=red>信件内容</h1>");
//                .send();
    }

    @Test
    public void testSendAttach() throws MessagingException {
        OhMyEmail.subject("这是一封测试附件邮件")
                .from("王爵的QQ邮箱")
                .to("921293209@qq.com")
                .html("<h1 font=red>信件内容</h1>")
                .attach(new File("/Users/biezhi/Downloads/hello.jpeg"), "测试图片.jpeg");
//                .send();
    }

    @Test
    public void testPebble() throws IOException, PebbleException, MessagingException {
        PebbleEngine   engine           = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("register.html");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "biezhi");
        context.put("email", "admin@java-china.org");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();
        System.out.println(output);

        OhMyEmail.subject("这是一封测试Pebble模板邮件")
                .from("王爵的QQ邮箱")
                .to("921293209@qq.com")
                .html(output);
//                .send();
    }


}