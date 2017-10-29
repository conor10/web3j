package org.web3j.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okio.Buffer;
import org.junit.Before;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class RequestTester {

    private OkHttpClient httpClient;
    private HttpService httpService;

    private RequestInterceptor requestInterceptor;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        requestInterceptor = new RequestInterceptor();
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();
        httpService = new HttpService(httpClient);
        initWeb3Client(httpService);
        objectMapper = new ObjectMapper();
    }

    protected abstract void initWeb3Client(HttpService httpService);

    protected void verifyResult(String expected) throws Exception {
        RequestBody requestBody = requestInterceptor.getRequestBody();
        assertNotNull(requestBody);
        assertThat(requestBody.contentType(), is(HttpService.JSON_MEDIA_TYPE));

        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        org.web3j.protocol.core.Request actualRequest = objectMapper.readValue(buffer.readUtf8(), org.web3j.protocol.core.Request.class);
        org.web3j.protocol.core.Request expectedRequest = objectMapper.readValue(expected, org.web3j.protocol.core.Request.class);
        assertEquals(actualRequest, expectedRequest);
        assertNotNull(actualRequest.getId());
    }

    private class RequestInterceptor implements Interceptor {

        private RequestBody requestBody;

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            this.requestBody = request.body();

            okhttp3.Response response = new okhttp3.Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_2)
                    .code(200)
                    .message("")
                    .build();

            return response;
        }

        public RequestBody getRequestBody() {
            return requestBody;
        }
    }
}
