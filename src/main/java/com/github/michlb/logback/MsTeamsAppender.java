package com.github.michlb.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.michlb.logback.beans.MsTeamsCard;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MsTeamsAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

  private static final String APPLICATION_JSON = "application/json";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String METHOD_POST = "POST";

  private static Layout<ILoggingEvent> defaultLayout =
      new LayoutBase<ILoggingEvent>() {
        public String doLayout(ILoggingEvent event) {
          return "**"
              + event.getLoggerName()
              + "** <br>"
              + event.getFormattedMessage().replaceAll("\n", "\n\t");
        }
      };

  private ObjectMapper objectMapper = new ObjectMapper();
  private String appName;
  private String webhookUri;
  private int timeout = 30_000;
  private Layout<ILoggingEvent> layout = defaultLayout;

  @Override
  protected void append(final ILoggingEvent event) {
    try {
      String[] parts = layout.doLayout(event).split("\n", 1);

      MsTeamsCard msTeamsCard = new MsTeamsCard();
      msTeamsCard.setContext("http://schema.org/extensions");
      msTeamsCard.setType("MessageCard");

      msTeamsCard.setThemeColor(decodeColor(event));
      msTeamsCard.setTitle(appName + " - " + event.getLevel().toString());
      msTeamsCard.setText(parts[0]);

      final byte[] bytes = objectMapper.writeValueAsBytes(msTeamsCard);

      postMessage(webhookUri, bytes);
    } catch (Exception ex) {
      ex.printStackTrace();
      addError("Error posting log to MS Teams: " + event, ex);
    }
  }

  private String decodeColor(ILoggingEvent evt) {
    switch (evt.getLevel().toString()) {
      case "INFO":
        return "0B6623"; // Forest Green
      case "WARN":
        return "F9A602"; // Gold
      case "ERROR":
        return "FF0800"; // Candy Apple Red
      default:
        return "0080FF"; // Azure Blue
    }
  }

  private void postMessage(String uri, byte[] bytes) throws IOException {
    final HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
    conn.setConnectTimeout(timeout);
    conn.setReadTimeout(timeout);
    conn.setDoOutput(true);
    conn.setRequestMethod(METHOD_POST);

    conn.setFixedLengthStreamingMode(bytes.length);
    conn.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);

    final OutputStream os = conn.getOutputStream();
    os.write(bytes);

    os.flush();
    os.close();
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  public String getWebhookUri() {
    return webhookUri;
  }

  public void setWebhookUri(String webhookUri) {
    this.webhookUri = webhookUri;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }
}
