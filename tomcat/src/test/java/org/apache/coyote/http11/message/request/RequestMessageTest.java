package org.apache.coyote.http11.message.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.message.common.HttpHeaders;
import org.apache.coyote.http11.message.common.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestMessageTest {

    @DisplayName("HTTP Request Message를 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "",
                "");

        // when
        RequestMessage actual = new RequestMessage(httpRequest);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("HttpRequestLine을 가져온다.")
    @Test
    void getRequestLine() {
        // given
        String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "",
                "");
        RequestMessage requestMessage = new RequestMessage(httpRequest);

        // when
        RequestStartLine actual = requestMessage.getRequestStartLine();

        // then
        assertAll(() -> {
            assertThat(actual.getMethod()).isEqualTo(HttpMethod.GET);
            assertThat(actual.getRequestUri().getPathWithoutQuery()).isEqualTo("/index.html");
            assertThat(actual.getVersion()).isEqualTo("HTTP/1.1");
        });
    }

    @DisplayName("HttpRequestHeaders를 가져온다.")
    @Test
    void getRequestHeaders() {
        // given
        String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "",
                "");
        RequestMessage requestMessage = new RequestMessage(httpRequest);

        // when
        HttpHeaders actual = requestMessage.getHttpHeaders();

        // then
        assertAll(() -> {
            assertThat(actual.getHeader("Host").get()).isEqualTo("localhost:8080");
            assertThat(actual.getHeader("Connection").get()).isEqualTo("keep-alive");
        });
    }

}