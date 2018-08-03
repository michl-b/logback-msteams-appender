package com.github.michlb.logback.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"@context", "@type", "themeColor", "title", "text", "potentialAction"})
public class MsTeamsCard {

  @JsonProperty("@context")
  private String context;

  @JsonProperty("@type")
  private String type;

  @JsonProperty("themeColor")
  private String themeColor;

  @JsonProperty("title")
  private String title;

  @JsonProperty("text")
  private String text;

  @JsonProperty("@context")
  public String getContext() {
    return context;
  }

  @JsonProperty("@context")
  public void setContext(String context) {
    this.context = context;
  }

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  @JsonProperty("@type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("themeColor")
  public String getThemeColor() {
    return themeColor;
  }

  @JsonProperty("themeColor")
  public void setThemeColor(String themeColor) {
    this.themeColor = themeColor;
  }

  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  @JsonProperty("text")
  public void setText(String text) {
    this.text = text;
  }
}
