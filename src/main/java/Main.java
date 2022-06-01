import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static final String LINK =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static CloseableHttpClient clientBuilder() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        return  httpClient;
    }

    public static void main(String[] args) throws IOException {
        HttpGet request = new HttpGet(LINK);
        CloseableHttpResponse response = clientBuilder().execute(request);

        List<Cat> cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {});
        cats.stream()
                .filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                .forEach(System.out::println);



    }
}
