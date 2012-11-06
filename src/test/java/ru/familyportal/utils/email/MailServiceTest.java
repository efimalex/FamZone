package ru.familyportal.utils.email;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.jvnet.mock_javamail.Mailbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 06.10.12
 * Time: 13:36
 */
public class MailServiceTest extends AbstractUtilTest {
    @Qualifier("mailService")
    @Autowired
    MailServiceInt mailService;

    @Before
    public void setUp() throws Exception {
        Mailbox.clearAll();
    }


    @After
    public void tearDown() throws Exception {

    }

   /* @Test
    public void testSendMail() throws Exception {
        Mailbox mailbox = Mailbox.get("to@email.com");
        mailService.sendMail("efim@mail.com", "to@email.com", "Subject: test send", "Body: testSend");
        assertEquals(1, mailbox.getNewMessageCount());
        assertFromEquals(mailbox.get(0), "efim@mail.com");
        assertToEquals(mailbox.get(0), "to@email.com");
        assertSubjectEquals(mailbox.get(0), "Subject: test send");
        assertBodyEquals(mailbox.get(0), "Body: testSend");
    }    */

    private void assertFromEquals(Message message, String... expectedFrom) throws MessagingException {
        Set<String> expectedFromSet = new HashSet<String>(Arrays.asList(expectedFrom));
        for (Address actualFrom : message.getFrom()) {
            expectedFromSet.remove(actualFrom.toString());
        }
        if (expectedFromSet.size() > 0)
            fail("From should contain these addresses: " + expectedFromSet);
    }

    private void assertToEquals(Message message, String... expectedTo) throws MessagingException {
        Set<String> expectedToSet = new HashSet<String>(Arrays.asList(expectedTo));

        for (Address actualTo : message.getRecipients(Message.RecipientType.TO)) {
            expectedToSet.remove(actualTo.toString());
        }
        if (expectedToSet.size() > 0)
            fail("From should contain these addresses: " + expectedToSet);
    }

    private void assertSubjectEquals(Message message, String expectedSubject) throws MessagingException {
        String actualSubject = message.getSubject();
        assertEquals(expectedSubject, actualSubject);
    }

    private void assertBodyEquals(Message message, String expectedBody) throws MessagingException, IOException {
        String contentType = message.getContentType();

        if (contentType.contains("text/plain")){
            String actualBody = (String) message.getContent();
            assertEquals(expectedBody,actualBody);
            return;
        }
        fail("Unsupported content-type: " + contentType);
    }
}
