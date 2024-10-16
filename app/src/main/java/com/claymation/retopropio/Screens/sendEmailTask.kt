package com.claymation.retopropio.Screens

import android.os.AsyncTask
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendEmailTask(
    private val senderEmail: String,
    private val password: String,
    private val recipientEmail: String,
    private val subject: String,
    private val body: String
) : AsyncTask<Void, Void, Void>() {

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Void? {
        val properties = Properties()

        // SMTP server configuration for Gmail
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.socketFactory.port"] = "465"
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.port"] = "465"

        // Creating a session
        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(senderEmail, password)
            }
        })

        try {
            // Creating a MimeMessage
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(senderEmail))
            mimeMessage.addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
            mimeMessage.subject = subject
            mimeMessage.setText(body)

            // Sending the email
            Transport.send(mimeMessage)

        } catch (e: MessagingException) {
            e.printStackTrace()
        }

        return null
    }
}